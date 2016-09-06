package juli.api;

import com.alibaba.fastjson.JSONObject;
import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.PushMsgToAllRequest;
import com.baidu.yun.push.model.PushMsgToAllResponse;
import com.baidu.yun.push.model.PushMsgToSingleDeviceRequest;
import com.baidu.yun.push.model.PushMsgToSingleDeviceResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import juli.api.core.APIResponse;
import juli.api.dto.WorkBillDto;
import juli.domain.*;
import juli.domain.enums.ElevatorFaultStatus;
import juli.domain.enums.FaultHandledStatus;
import juli.infrastructure.DateUtil;
import juli.infrastructure.exception.JuliException;
import juli.repository.ElevatorRepository;
import juli.repository.MaintenanceRepository;
import juli.repository.WorkBillRepository;
import juli.service.ElevatorService;
import juli.service.MaintenanceService;
import juli.service.UserService;
import juli.service.WorkBillService;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

/**
 * Created by pf on 2016/2/21.
 */
@Api(value = "网络救援API", description = " ")
@RestController
@RequestMapping("/api/netRescue")
public class NetworkRescueAPIController {

    @Autowired
    private MaintenanceRepository maintenanceRepository;
    @Autowired
    private ElevatorRepository elevatorRepository;

    @Autowired
    private WorkBillService workBillService;
    @Autowired
    private WorkBillRepository workBillRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private MaintenanceService maintenanceService;

    Map<String, Object> returnMap = new HashMap<>();
    private static String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";

    public  final static String ANDROID_APIKEY = "3Lfb4BW9t40CGYZMWqeWby5l";//百度推送申请应用后产生的
    public final static String ANDROID_SECRETKEY = "LjTHEjoyr5HwvEv1DP813HI6BkTNWaSE";//百度推送申请应用后产生的

    public  final static String IOS_APIKEY = "9rasniw6kCHLLrbT9Bsg69fh"; //百度推送申请应用后产生的
    public  final static String IOS_SECRETKEY = "MMhhIMPAOVCbv61fVss9QtaDCIBGwQD2";//百度推送申请应用后产生的

    @ApiOperation(value = "根据电梯工号定位")
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public APIResponse get(@ApiParam("number") String number) {
        try {
            User user = userService.getCurrentUser();
            Elevator elevator;
            if (user.getCompanyId() != null && !user.getCompanyId().equals("")) {
                elevator = elevatorRepository.findByNumber(number.trim(),user.getCompanyId());
            }
           else
            {
                elevator = elevatorRepository.findByNumber(number.trim());
            }
            if (elevator != null) {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("id", elevator.getId());
                paramMap.put("number", elevator.getNumber());
                paramMap.put("projectName",elevator.getProjectName());
                paramMap.put("lng", elevator.getLng());
                paramMap.put("lat", elevator.getLat());
                paramMap.put("address", elevator.getAddress());
                paramMap.put("type", elevator.getControllerType());  //暂时用控制器类型
                return APIResponse.success(paramMap);
            }
            return APIResponse.error("没有相关电梯");
        } catch (JuliException e) {
            return APIResponse.error(e.getMessage());
        }
    }

    @ApiOperation(value = "手机短信推送")
    @RequestMapping(value = "/sendSms/{id}", method = RequestMethod.POST)
    public APIResponse sendSms(@ApiParam("电梯id") @PathVariable("id") String id,
                               @ApiParam("左上角经度") @RequestParam("lng1") double lng1,
                               @ApiParam("右下角经度") @RequestParam("lng2") double lng2,
                               @ApiParam("左上角纬度") @RequestParam("lat1") double lat1,
                               @ApiParam("右上角纬度") @RequestParam("lat2") double lat2) {
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(Url);

        //client.getParams().setContentCharset("GBK");
        client.getParams().setContentCharset("UTF-8");
        method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");
        int mobile_code = (int) ((Math.random() * 9 + 1) * 100000);
        //System.out.println(mobile);
        String content = new String("您的验证码是：" + mobile_code + "。请不要把验证码泄露给其他人。");
        //批量发送
        for (MaintenancePersonnel personnel : getMaintenancePersonnels(id, lng1, lng2, lat1, lat2)) {
            NameValuePair[] data = {//提交短信
                    new NameValuePair("account", "cf_695149142"),
                    new NameValuePair("password", "123456"), //密码可以使用明文密码或使用32位MD5加密
                    //new NameValuePair("password", util.StringUtil.MD5Encode("密码")),
                    new NameValuePair("mobile", personnel.getTelephone()),
                    new NameValuePair("content", content),
            };

            method.setRequestBody(data);


            try {
                client.executeMethod(method);

                String SubmitResult = method.getResponseBodyAsString();

                //System.out.println(SubmitResult);

                Document doc = DocumentHelper.parseText(SubmitResult);
                Element root = doc.getRootElement();

                String code = root.elementText("code");
                String msg = root.elementText("msg");
                String smsid = root.elementText("smsid");

                System.out.println(code);
                System.out.println(msg);
                System.out.println(smsid);

                if ("2".equals(code)) {
                    System.out.println(personnel.getTelephone() + "短信发送成功");
                }

            } catch (org.apache.commons.httpclient.HttpException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (DocumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return APIResponse.success("短信发送成功");

    }

    @ApiOperation(value = "百度推送")
    @RequestMapping(value = "/push/{id}", method = RequestMethod.POST)
    public APIResponse baiduPush(@ApiParam("电梯id") @PathVariable("id") String id,
                                 @ApiParam("左上角经度") @RequestParam("lng1") double lng1,
                                 @ApiParam("右下角经度") @RequestParam("lng2") double lng2,
                                 @ApiParam("左上角纬度") @RequestParam("lat1") double lat1,
                                 @ApiParam("右上角纬度") @RequestParam("lat2") double lat2,
                                 @ApiParam("推送信息") @RequestParam("message") String description) throws PushClientException, PushServerException {
        APIResponse re = androidPush(id, lng1, lng2, lat1, lat2, description);
        APIResponse re1 = IOSPush(id, lng1, lng2, lat1, lat2, description);
        if (re.isSuccess() && re1.isSuccess()) {
            return APIResponse.success();
        } else {
            return APIResponse.error("推送失败");
        }

    }

    @ApiOperation(value = "新建急修工单并发送")
    @RequestMapping(value = "/sendJX", method = RequestMethod.POST)
    public APIResponse sendJX(@ApiParam("新建并推送急修工单") WorkBillDto workBillDto) throws Exception {
        WorkBills bills = workBillDto.mapTo();
        bills.setBillstatus(10);
        workBillService.setForeignFieldFromDto(bills, workBillDto);
        bills.setBillModel(40);
        String billnumber = DateUtil.dateToString(new Date(), "yyyy-MM-dd") + "-" + (new Random().nextInt(89999) + 10000);
        bills.setBillNumber("JX" + billnumber);
        bills.setBillCategory(30);//急修
        elevatorRepository.findElevatorById(workBillDto.getElevatorId()).setFaultStatus(ElevatorFaultStatus.MALFUNCTION.getCode());
        elevatorRepository.findElevatorById(workBillDto.getElevatorId()).setFaultTime(new Date());
        elevatorRepository.findElevatorById(workBillDto.getElevatorId()).setFaultCode("人为报障");
        elevatorRepository.findElevatorById(workBillDto.getElevatorId()).setIsHandled(FaultHandledStatus.HANDLED.getCode());
        workBillRepository.save(bills);
        return APIResponse.success();
    }

    @RequestMapping(value="/searchPersonName",method=RequestMethod.POST)
    public APIResponse searchPerson(@ApiParam("name")String name,@ApiParam("elevatorId")String elevatorId){
        return maintenanceService.searchPersonName(name,elevatorId);
    }

    @RequestMapping(value="/getPerson/{otherMan}",method=RequestMethod.POST)
    public APIResponse getPerson(@ApiParam("人员姓名") @PathVariable("otherMan")String otherMan,@ApiParam("elevatorId") @RequestParam("elevatorId") String elevatorId){
        return maintenanceService.getPerson(otherMan,elevatorId);
    }


    @ApiOperation(value = "根据电梯获取满足条件的急修人员相关信息")
    @RequestMapping(value = "/locate/{elevatorId}", method = RequestMethod.POST)
    public APIResponse gets(@ApiParam("电梯工号") @PathVariable("elevatorId") String elevatorId,
                            @ApiParam("左下角经度") @RequestParam("lng1") double lng1,
                            @ApiParam("右上角经度") @RequestParam("lng2") double lng2,
                            @ApiParam("左下角纬度") @RequestParam("lat1") double lat1,
                            @ApiParam("右上角纬度") @RequestParam("lat2") double lat2) {
        Elevator elevator = elevatorRepository.findElevatorById(elevatorId);
        Map<String, Object> paramMap = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();
        List<MaintenancePersonnel> maintenancePersonnellist = getMaintenancePersonnels(elevator.getId(), lng1, lng2, lat1, lat2);
        for (MaintenancePersonnel personnel : maintenancePersonnellist) {
            paramMap = new HashMap<>();
            paramMap.put("id", personnel.getId());
            paramMap.put("name", personnel.getName());
            paramMap.put("level", getLevelName(elevator, personnel).get("level"));
            paramMap.put("telephone", personnel.getTelephone() == null ? "" : personnel.getTelephone());
            paramMap.put("time", personnel.getLastModifiedDate() == null ? "" : personnel.getLastModifiedDate().toDateTime().toString());
            paramMap.put("lng", personnel.getLng());
            paramMap.put("lat", personnel.getLat());
            list.add(paramMap);
        }


        return APIResponse.success(list);
    }

    //判断人员是否有资格对某电梯品牌急修
    public Map<String, Object> getLevelName(Elevator elevator, MaintenancePersonnel personnel) {
        Iterator<RepairLevel> list = personnel.getLevelList().iterator();
        Map<String, Object> map = new HashMap<>();
        while (list.hasNext()) {
            RepairLevel level = list.next();
            if (level.getElevatorBrand().getId().equals(elevator.getBrandId())) {
                if (level.getName().equals("高") || level.getName().equals("中")) {
                    map.put("result", true);
                    map.put("level", level.getName());
                } else {
                    map.put("result", false);
                    map.put("level", level.getName());
                }
                return map;
            } else {
                map.put("result", false);
                map.put("level", level.getName());
                return map;
            }

        }
        map.put("result", false);
        map.put("level", "");
        return map;
    }

    //获取满足某电梯的急修条件并且在一定范围内的员工
    public List<MaintenancePersonnel> getMaintenancePersonnels(String id, double lng1, double lng2, double lat1, double lat2) {
        double lat;
        double lng;
        List<MaintenancePersonnel> personnelList = maintenanceRepository.findByCurrentStateLessThan(30);
        List<MaintenancePersonnel> list = new ArrayList<>();
        Elevator elevator = elevatorRepository.findElevatorById(id);
        for (MaintenancePersonnel personnel : personnelList) {
            if (personnel.getLat() != null && personnel.getLng() != null) {
                lat = personnel.getLat();
                lng = personnel.getLng();
                //人员是否在其中
                if (lat1 < lat && lat < lat2 && lng1 < lng && lng < lng2) {
                    if ((Boolean) getLevelName(elevator, personnel).get("result")) {
                        list.add(personnel);
                    }


                }
            }
        }
        Comparator comparator=new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                MaintenancePersonnel m1=(MaintenancePersonnel)o1;
                MaintenancePersonnel m2=(MaintenancePersonnel)o2;
                double distance1 = GetDistance(m1, elevator.getLat(), elevator.getLng());
                double distance2 = GetDistance(m2, elevator.getLat(), elevator.getLng());
                if (distance1 >distance2)
                    return 1;
                else if(distance1 < distance2)
                    return -1;
                else
                    return 0;
            }
        };
        Collections.sort(list,comparator);
        return list;
    }


    @ApiOperation(value = "获取急修电梯员工经纬度")
    @RequestMapping(value = "/getMaintenancePosition", method = RequestMethod.GET)
    public APIResponse getMaintenancePosition(String id, String number) {

        MaintenancePersonnel maintenancePersonnel = maintenanceRepository.findOne(id);
        Map<String, Double> map = new HashMap<>();
        map.put("lat", maintenancePersonnel.getLat());
        map.put("lng", maintenancePersonnel.getLng());

        Elevator elevator = elevatorRepository.findByNumber(number);
        map.put("elat", elevator.getLat());
        map.put("elng", elevator.getLng());

        return APIResponse.success(map);
    }

    @ApiOperation(value = "获取电梯与维修人员的实时状况")
    @RequestMapping(value = "/getElevatorAndMaintenance", method = RequestMethod.POST)
    public APIResponse getElevatorAndMaintenance(@ApiParam("工单id") String id) {

        WorkBills workBills = workBillRepository.findOne(id);
        Elevator elevator = workBills.getElevator();
        MaintenancePersonnel maintenancePersonnel = workBills.getMaintenancePersonnel();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("elevator_lat", elevator.getLat());
        jsonObject.put("elevator_lng", elevator.getLng());
        jsonObject.put("alias", elevator.getAlias());  //地址别名
        jsonObject.put("elevatorNumber", elevator.getNumber());  //电梯工号

        jsonObject.put("maintenance_lat", maintenancePersonnel.getLat());
        jsonObject.put("maintenance_lng", maintenancePersonnel.getLng());
        jsonObject.put("maintenance_number", maintenancePersonnel.getNumber());  //维保人员工号
        jsonObject.put("maintenance_name", maintenancePersonnel.getName());
        jsonObject.put("maintenance_phone", maintenancePersonnel.getTelephone());

        return APIResponse.success(jsonObject);
    }


    //获取满足某电梯的急修条件并且在一定范围内的员工手机号
    public List<String> getTelephone(String id, double lng1, double lng2, double lat1, double lat2) {
        List<MaintenancePersonnel> list = maintenanceRepository.findByCurrentStateLessThan(30);
        List<String> telephones = new ArrayList<>();
        for (MaintenancePersonnel p : list)
            telephones.add(p.getTelephone());
        return telephones;
    }


    /**
     * Android 推送
     *
     * @param description 需要推送的消息
     * @return
     * @throws PushClientException
     * @throws PushServerException
     */
    public APIResponse androidPush(@ApiParam("电梯id") @PathVariable("id") String id,
                                   @ApiParam("左上角经度") @RequestParam("lng1") double lng1,
                                   @ApiParam("右下角经度") @RequestParam("lng2") double lng2,
                                   @ApiParam("左上角纬度") @RequestParam("lat1") double lat1,
                                   @ApiParam("右上角纬度") @RequestParam("lat2") double lat2,
                                   String description) throws PushClientException, PushServerException {
        PushKeyPair pair = new PushKeyPair(ANDROID_APIKEY, ANDROID_SECRETKEY);
        // 2. build a BaidupushClient object to access released interfaces
        BaiduPushClient pushClient = new BaiduPushClient(pair,
                BaiduPushConstants.CHANNEL_REST_URL);

        // 3. register a YunLogHandler to get detail interacting information
        // in this request.
        pushClient.setChannelLogHandler(new YunLogHandler() {
            @Override
            public void onHandle(YunLogEvent event) {
                System.out.println(event.getMessage());
            }
        });

            // 4. specify request arguments
            JSONObject jo = new JSONObject();
            JSONObject jo1 = new JSONObject();
            jo.put("title", "JULI_TEST");
            jo.put("description", description);
            jo.put("notification_builder_id", 0);
            jo.put("notification_basic_style", 7);
            jo.put("open_type", 3);
            jo.put("custom_content", jo1.put("key", "value"));
            List<MaintenancePersonnel> list = getMaintenancePersonnels(id, lng1, lng2, lat1, lat2);
            for (MaintenancePersonnel personnel : list) {
                if (personnel.getUuid() != null&&!personnel.getUuid().isEmpty() && personnel.getDeviceType() == 2) {
                    workBillService.pushSingleAndroid(personnel, jo, pushClient);
                }

            }

            return APIResponse.success();
    }


    /**
     * IOS 推送
     *
     * @param description 需要推送的消息
     * @return
     * @throws PushClientException
     * @throws PushServerException
     */
    public APIResponse IOSPush(@ApiParam("电梯id") @PathVariable("id") String id,
                               @ApiParam("左上角经度") @RequestParam("lng1") double lng1,
                               @ApiParam("右下角经度") @RequestParam("lng2") double lng2,
                               @ApiParam("左上角纬度") @RequestParam("lat1") double lat1,
                               @ApiParam("右上角纬度") @RequestParam("lat2") double lat2,
                               String description) throws PushClientException, PushServerException {
        PushKeyPair pair = new PushKeyPair(IOS_APIKEY, IOS_SECRETKEY);
        // 2. build a BaidupushClient object to access released interfaces
        BaiduPushClient pushClient = new BaiduPushClient(pair,
                BaiduPushConstants.CHANNEL_REST_URL);

        // 3. register a YunLogHandler to get detail interacting information
        // in this request.
        pushClient.setChannelLogHandler(new YunLogHandler() {
            @Override
            public void onHandle(YunLogEvent event) {
                System.out.println(event.getMessage());
            }
        });

        try {
            // 4. specify request arguments
            JSONObject jo = new JSONObject();
            JSONObject jo1 = new JSONObject();
            jo1.put("alert", description);
            jo1.put("sound", "default");
            jo.put("aps", jo1);
            jo.put("key1", "");
            jo.put("key2", "");
            List<MaintenancePersonnel> list = getMaintenancePersonnels(id, lng1, lng2, lat1, lat2);
            for (MaintenancePersonnel personnel : list) {
                if (personnel.getUuid() != null && personnel.getDeviceType() == 1) {
                    if(personnel.getBadgeNumber()==null)
                        personnel.setBadgeNumber(0);
                    jo1.put("badge", personnel.getBadgeNumber()+1);
                    workBillService.pushSingleIOS(personnel, jo, pushClient);
                    personnel.setBadgeNumber(personnel.getBadgeNumber()+1);
                    maintenanceRepository.save(personnel);

                }
            }

            return APIResponse.success();
        } catch (PushClientException e) {
            if (BaiduPushConstants.ERROROPTTYPE) {
                throw e;
            } else {
                e.printStackTrace();
            }
            return APIResponse.error("IOS推送失败");
        } catch (PushServerException e) {
            if (BaiduPushConstants.ERROROPTTYPE) {
                throw e;
            } else {
                System.out.println(String.format(
                        "requestId: %d, errorCode: %d, errorMessage: %s ,erorMessage: %s",
                        e.getRequestId(), e.getErrorCode(), e.getErrorMsg(), e.getMessage()));
            }
            return APIResponse.error("IOS推送失败");
        }

    }

    //计算两经纬度直线距离
    public double GetDistance(MaintenancePersonnel Degree1, double lat, double lng) {
        double EARTH_RADIUS = 6378137.0;
        double radLat1 = radians(Degree1.getLat());
        double radLat2=radians(lat);
        double a = radLat1 - radLat2;
        double b = radians(Degree1.getLng()) - radians(lng);

        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
                Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    private double radians(double d) {
        return d * Math.PI / 180.0;
    }


}
