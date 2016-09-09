package juli.controller.weixin.contoller;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import suda.wxSDK.message.handler.IMessageHandler;
import suda.wxSDK.util.AesException;
import suda.wxSDK.util.TokenUtil;
import suda.wxSDK.util.WXBizMsgCrypt;
import suda.wxSDK.weixin.Configuration;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping("weixin/initAndOAuth")
public class InitController {
	@Autowired
	private IMessageHandler messageHandler;
	//微信初始化验证
	@RequestMapping(value="/init",method=RequestMethod.GET)
	public void init(HttpServletRequest request,HttpServletResponse response) throws IOException, AesException
	{
		String signature = request.getParameter("signature");  
        // 时间戳  
        String timestamp = request.getParameter("timestamp");  
        // 随机数  
        String nonce = request.getParameter("nonce");  
        // 随机字符串  
        String echostr = request.getParameter("echostr");  
  
        PrintWriter out = response.getWriter();  
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败  
        if (TokenUtil.checkSignature(Configuration.getProperty("sudaWx.token"), signature, timestamp, nonce)) {  
            out.print(echostr);  
        }  
        out.close();  
        out = null;  
	}
	//微信获取消息处理
	@RequestMapping(value="/init",method=RequestMethod.POST)
	public void init1(HttpServletRequest request,HttpServletResponse response) throws IOException, DocumentException
	{
	
		try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/xml");
            //获取POST流
            ServletInputStream in = request.getInputStream();
            if (Configuration.isDebug()) {
                System.out.println("接受的Post消息");
            }
//            IMessageHandler messageHandler = HandlerFactory.getMessageHandler();
            //处理输入消息，返回结果
            String xml = messageHandler.invoke(in,request.getSession().getServletContext());
            //返回结果
            response.getWriter().write(xml);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.getWriter().write("");
        }
		finally {
			
		}
//		ThreadPoolManage tm=ThreadPoolManage.newInstance();
//		tm.addTask(request, response);
	}
	@RequestMapping(value="/qyinit",method=RequestMethod.GET)
	public void qyinit(HttpServletRequest request,HttpServletResponse response) throws IOException, AesException
	{
		String sToken ="7udbstMUasJ2GQ";
		String sCorpID = "wx3c9ff6fb1af8dc25";
		String sEncodingAESKey = "jWmYm7qr5nMoAUwZRjGtBxmz3KA1tkAj3ykkR6q2B2C";
		
		WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(sToken, sEncodingAESKey, sCorpID);
		
		String msg_signature = request.getParameter("msg_signature");  
        // 时间戳  
        String timestamp = request.getParameter("timestamp");  
        // 随机数  
        String nonce = request.getParameter("nonce");  
        // 随机字符串  
        String echostr = request.getParameter("echostr");  
  
        PrintWriter out = response.getWriter();  
        String sEchoStr; //需要返回的明文
		try {
			sEchoStr = wxcpt.VerifyURL(msg_signature, timestamp,
					nonce, echostr);
			System.out.println("verifyurl echostr: " + sEchoStr);
            out.write(sEchoStr);
			// 验证URL成功，将sEchoStr返回
			// HttpUtils.SetResponse(sEchoStr);
		} catch (Exception e) {
			//验证URL失败，错误原因请查看异常
			e.printStackTrace();
		}
        out.close();  
        out = null;  
	}
	
}
