package juli.api;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import juli.api.core.APIController;
import juli.api.core.APIResponse;
import juli.api.dto.MaintenancePersonnelDto;
import juli.domain.MaintenancePersonnel;
import juli.domain.User;
import juli.infrastructure.AesUtils;
import juli.infrastructure.DateUtil;
import juli.infrastructure.MD5Util;
import juli.repository.AppVersionRepository;
import juli.repository.CompanyRepository;
import juli.repository.MaintenanceRepository;
import juli.repository.UserRepository;
import juli.service.MaintenanceService;
import juli.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpRequest;
import org.h2.security.AES;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.applet.Main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pf on 2016/2/1.
 */
@Api(value = "维保人员手机API", description = " ")
@RestController
@RequestMapping("/api/maintenance")
public class IOSMaintenancePersonnelAPIController extends APIController<MaintenancePersonnel, MaintenanceRepository> {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private MaintenanceService maintenanceService;

    @Autowired
    private MaintenanceRepository maintenanceRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private AppVersionRepository versionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;


    @ApiOperation("单点推平台，添加维保人员及平台系统用户")
    @RequestMapping(value = "/ios/systemAdd",method = RequestMethod.POST)
    public APIResponse create (HttpServletRequest request) throws Exception {

        String username=request.getParameter("username");
        String realName=request.getParameter("realname");
        String uid=request.getParameter("systemId");
        String token=request.getParameter("token");
        String password=request.getParameter("password");
        String password1=AesUtils.aesDecoder(password, "1234567890123456");//密码明文
        int a=isLegal(token);
        if(a==0)
        {
            return APIResponse.error("非法请求。");
        }
        else if(a==1)
        {
            return APIResponse.error("请求过期。");
        }
        else
        {
            if(uid==null||username==null)
            {
                return APIResponse.error("用户名或者uid不存在");
            }
            MaintenancePersonnel personnel=maintenanceRepository.findByUid(uid);
            if(personnel!=null)
            {
                personnel.setActive("1");
                personnel.setCurrentState(30);
            }
            else
            {
                personnel=new MaintenancePersonnel();
//                Random random=new Random();
//                String number="RZ"+random.nextInt(100000);
                personnel.setNumber(username);
                personnel.setName(realName);
                personnel.setUid(uid);
                personnel.setPassword(MD5Util.parseStrToMd5L32(password1));
                personnel.setActive("1");
                personnel.setCurrentState(30);

                User user=new User();
                user.setUserName(username);//单点系统登录用户名,也是后台登录用户名
                user.setNick(realName);
                user.setPassword(userService.getEncryptedPassword(user.getUserName(), password1));//初始值为6个0
                user.setInUse("1");
                userRepository.save(user);
            }
            maintenanceRepository.save(personnel);
            return  APIResponse.success();
        }

    }

    @ApiOperation("平台推单点新增人员时，回传uid，更新维保人员")
    @RequestMapping(value = "/ios/systemUpdate",method = RequestMethod.POST)
    public APIResponse updatePerson (HttpServletRequest request) throws Exception {
//平台上维保人员的密码暂时无用
        String number=request.getParameter("username");
        String name=request.getParameter("realname");
        String uid=request.getParameter("systemId");
        String token=request.getParameter("token");
        int a=isLegal(token);
        if(a==0)
        {
            return APIResponse.error("非法请求。");
        }
        else if(a==1)
        {
            return APIResponse.error("请求过期。");
        }
        else
        {
            if(uid==null||number==null)
            {
                return APIResponse.error("用户名或者uid不存在");
            }
            MaintenancePersonnel personnel=maintenanceRepository.findByNumber(number);
            if(personnel!=null)
            {
                personnel.setActive("1");
                personnel.setCurrentState(30);
                personnel.setUid(uid);
                maintenanceRepository.save(personnel);
                return  APIResponse.success();
            }
            else
            {
                personnel=new MaintenancePersonnel();
                personnel.setName(name);
                personnel.setNumber(number);
                personnel.setActive("1");
                personnel.setCurrentState(30);
                personnel.setUid(uid);
                maintenanceRepository.save(personnel);
                return  APIResponse.success();
            }
        }

    }

    @ApiOperation("删除员工")
    @RequestMapping(value = "/ios/systemDelete",method = RequestMethod.POST)
    public APIResponse delete(HttpServletRequest request) throws Exception {
        String uid=request.getParameter("systemId");
        String token=request.getParameter("token");
        int a=isLegal(token);
        if(a==0)
        {
            return APIResponse.error("非法请求。");
        }
        else if(a==1)
        {
            return APIResponse.error("请求过期。");
        }
        else
        {
            if(uid==null||uid.equals(""))
            {
                return APIResponse.error("uid不存在");
            }
            MaintenancePersonnel personnel=maintenanceRepository.findByUid(uid);
            if(personnel==null)
            {
                return  APIResponse.success();
            }
            else
            {
                personnel.setActive("0");
                maintenanceRepository.save(personnel);
                return  APIResponse.success();
            }

        }

    }
    @ApiOperation(value = "维修人员统一登入")
    @RequestMapping(value = "/ios/systemlogin",method = RequestMethod.POST)
    public JSONObject systemlogin(HttpServletRequest request)throws Exception {

        String uid=request.getParameter("systemId");
        String token=request.getParameter("token");
        int a=isLegal(token);
        if(a==0)
        {
            JSONObject jo=new JSONObject();
            jo.put("success",false);
            jo.put("description","非法请求。");
            return jo;
        }
        else if(a==1)
        {
            JSONObject jo=new JSONObject();
            jo.put("success",false);
            jo.put("description","请求过期。");
            return jo;
        }
        else
        {
            if(uid==null||uid.equals(""))
            {
                JSONObject jo=new JSONObject();
                jo.put("success",false);
                jo.put("description","用户不存在！");
                return jo;
            }
            HttpSession session=request.getSession();
            return maintenanceService.systemLogin(session,uid);

        }

    }

    @ApiOperation(value = "维修人角标清零")
    @RequestMapping(value = "/ios/initBadgeBumber")
    public void initBageNumber(@ApiParam(value = "维修人员id") @RequestParam(value = "id") String id)
    {
          MaintenancePersonnel personnel= maintenanceRepository.findById(id);
          personnel.setBadgeNumber(0);
          maintenanceRepository.save(personnel);
    }
    @ApiOperation(value = "维修人员登入")
    @RequestMapping(value = "/ios/login")
    public APIResponse maintencelogin(HttpSession session, @ApiParam(value = "维修人员编号") @RequestParam(value = "number") String number,
                                      @ApiParam(value = "登录密码") @RequestParam(value = "password") String password, String uuid) {
        return maintenanceService.getMaintenanceByNumberAndPassword(session, number, password);
    }

    @ApiOperation(value = "维修人员签到")
    @RequestMapping(value = "/ios/changeStatus")
    public APIResponse changeStatus(HttpSession session,
                                    @ApiParam(value = "唯一客户端") @RequestParam(value = "sid") String sid,
                                    @ApiParam(value = "维修员id") @RequestParam(value = "id") String id,
                                    @ApiParam(value = "修改状态") @RequestParam(value = "status") Integer status,
                                    @ApiParam(value = "唯一手机端标识") @RequestParam(value = "uuid") String uuid,
                                    @ApiParam(value = "设备类型标识 1 ISO 2 Android") @RequestParam(value = "deviceType") int deviceType  ) {
        String ssid = (String) session.getAttribute("sid");
        if (ssid != null) {
            if (ssid.equals(sid)) {
                return maintenanceService.changeStatus(id, status, uuid,deviceType);

            } else {
                return APIResponse.error("请重新登录");
            }
        } else {
            return APIResponse.error("请重新登录");
        }


    }

    @ApiOperation(value = "APP后台更新经纬度")
    @RequestMapping(value = "/ios/changeLatAndLng")
    public APIResponse updateLatAndLng(@ApiParam(value = "员工id") @RequestParam(value = "id") String id,
                                       @ApiParam(value = "纬度") @RequestParam(value = "lat") double lat,
                                       @ApiParam(value = "经度") @RequestParam(value = "lng") double lng) {
        logger.info("员工ID:"+id+",经度:"+lng+",经度:"+lat);
            MaintenancePersonnel maintenancePersonnel = maintenanceRepository.findOne(id);
            if (null!=maintenancePersonnel && null!=maintenancePersonnel.getCurrentState() && maintenancePersonnel.getCurrentState() != 30) {
                maintenancePersonnel.setLat(lat);
                maintenancePersonnel.setLng(lng);
                maintenanceRepository.save(maintenancePersonnel);
            }

        return APIResponse.success();
    }

    private void setForeignFieldFromDto(MaintenancePersonnel personnel, MaintenancePersonnelDto dto) {
        if (StringUtils.isNotEmpty(dto.getMaintainerId())) {
            personnel.setMaintainer(companyRepository.findOne(dto.getMaintainerId()));
        } else {
            personnel.setMaintainer(null);
        }
    }
    /**
     * 获取post参数
     * @param is
     * @param charset
     * @return
     */
    public  String getContent(InputStream is, String charset) throws IOException {
        String pageString = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        StringBuffer sb = null;
        try {
            isr = new InputStreamReader(is, charset);
            br = new BufferedReader(isr);
            sb = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            pageString = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null){
                    is.close();
                }
                if(isr!=null){
                    isr.close();
                }
                if(br!=null){
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            sb = null;
        }
        return pageString;
    }
    public int  isLegal(String token) throws Exception {
         String str= AesUtils.aesDecoder(token, "1234567890123456");
         String key_time[]=str.split(",");
         Date date= DateUtil.stringToFullDate(key_time[1]);
         if(!key_time[0].equals("juli_auth"))//不合法
         {
             return 0;
         }
        else if((new Date().getTime()-date.getTime())>120000)//过期
         {
             return 1;
         }
        else//OK
         {
             return 2;
         }

    }

}
