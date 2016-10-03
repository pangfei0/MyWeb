package juli.controller.weixin.contoller;

import juli.api.core.APIResponse;
import juli.controller.weixin.common.CommonUtils;
import juli.domain.Role;
import juli.infrastructure.exception.JuliException;
import juli.repository.RoleRepository;
import juli.repository.UserRepository;
import juli.service.UserService;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import suda.wxSDK.base.oauth.OAuth2;
import suda.wxSDK.base.oauth.OAuth2Token;
import suda.wxSDK.base.userGroupManager.User;
import suda.wxSDK.base.userGroupManager.UserManager;
import suda.wxSDK.util.http.HttpsClient;
import suda.wxSDK.weixin.Configuration;
import suda.wxSDK.weixin.WeixinException;
import suda.wxSDK.weixin.WeixinSupport;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
@RequestMapping("weixin")
public class OAuthController {
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UserService  userService;
	@Autowired
   private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@RequestMapping("/oAuthInfo")
      public void showInfo(HttpServletRequest request,HttpServletResponse response,Model model) throws UnsupportedEncodingException, WeixinException, ServletException, JuliException {

		    juli.domain.User loginUser = (juli.domain.User) SecurityUtils.getSubject().getPrincipal();
			if(loginUser==null)
			{
				request.setCharacterEncoding("gb2312");
				response.setCharacterEncoding("gb2312");
				String code=request.getParameter("code");
				if(!"authdeny".equals(code))
				{
					OAuth2 o2=new OAuth2();
					OAuth2Token token=o2.login(Configuration.getOAuthAppId(), Configuration.getOAuthSecret(), code);

					User user1=new UserManager().getUserInfo(token.getOpenid(), null);
					logger.info("微信用户：" + user1.getNickname()+","+user1.getOpenid() + " 尝试登录系统");
					juli.domain.User user = userRepository.findByUserName(user1.getOpenid());
					if (user == null) {
						user= CommonUtils.changeToMyUser(user1);
						List<Role> list=user.getRoles();
						Role role=roleRepository.findById("role-06bab60d-e3b0-4cb2-be06-df573eb00031");
						list.add(role);
						user.setRoles(list);
						userRepository.save(user);
						SecurityUtils.getSubject().login(new UsernamePasswordToken(user.getUserName(), user.getPassword()));
						HttpSession session=request.getSession();
						session.setAttribute("userName",user.getNick());
					}
					else if (!userService.getPermissions(user).contains("user:login")) {
						throw new JuliException("没有权限登录");
					}

					try {
						SecurityUtils.getSubject().login(new UsernamePasswordToken(user.getUserName(), user.getPassword()));
					} catch (AuthenticationException e) {
						throw new JuliException("登录失败");
					}

				}

		}
	   try {
		   String state=request.getParameter("state");
		   if(state.equals("elevator"))
		   {
			   RequestDispatcher rd= request.getRequestDispatcher("/weixin/achieve/elevators");
			   rd.forward(request, response);
		   }
           else if(state.equals("chatrecord"))
           {
        	   RequestDispatcher rd= request.getRequestDispatcher("chatrecord.html");
			   rd.forward(request, response);
           }
			
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      }
	
	
	@RequestMapping("/getUserInfo")
	@ResponseBody
	public APIResponse getUser(HttpSession session)
	{
		User user=(User) session.getAttribute("user");
		
	    
		return APIResponse.success(user);
	}
	public User getUserInfo(OAuth2Token token) throws NumberFormatException, WeixinException
	{
		HttpsClient http=new HttpsClient();
	   	String url="https://api.weixin.qq.com/sns/userinfo?access_token=="+token.getAccess_token()+"&openid="+token.getOpenid()+"&lang=zh_CN";
        JSONObject jsonObj=http.get(url).asJSONObject();
	    if (jsonObj != null) {
            if (Configuration.isDebug()) {
                System.out.println("updateRemark返回json：" + jsonObj.toString());
            }
            Object errcode = jsonObj.get("errcode");
            if (errcode != null && !errcode.toString().equals("0")) {
                //返回异常信息
                throw new WeixinException(WeixinSupport.getCause(Integer.parseInt(errcode.toString())));
            }
        }
	    return (User) JSONObject.toBean(jsonObj, User.class);
   }
}
