package juli.api;

import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.Api;
import juli.api.core.APIResponse;
import juli.api.dto.faultDto.FaultTwoDto;
import juli.controller.webservice.hesu.bpa.lerc.business.extend.SetDevFaultForIOT;
import juli.controller.webservice.hesu.bpa.lerc.business.extend.SetDevFaultForIOTResponse;
import juli.service.BpaLercLiftMonitorService;
import juli.service.FaultElevator.ElevatorFaultService;
import net.sf.json.JSONObject;
//import org.apache.axis.client.Call;
//import org.apache.axis.client.Service;
//import org.apache.axis.encoding.XMLType;
//import org.apache.axis2.AxisFault;
//import org.apache.axis2.client.ServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "电梯故障API", description = " ")
@RestController
@RequestMapping("/api/hc/elevator")
public class ElevatorFaultAPIController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ElevatorFaultService elevatorFaultService;
    @Autowired
    BpaLercLiftMonitorService bpaLercLiftMonitorService;

    /**
     * 接收故障信息
     * @param jsonString
     * @return
     */
    @RequestMapping(value = "/fault",method =RequestMethod.POST)
    public APIResponse getFaultTwoDto(@RequestBody String jsonString) throws RemoteException {
        JSONObject obj = new JSONObject().fromObject(jsonString);
        FaultTwoDto faultTwoDto = (FaultTwoDto)JSONObject.toBean(obj,FaultTwoDto.class);
        //其他逻辑（判断是否困人）
        //保存故障信息（困人）
        elevatorFaultService.saveElevatorFault(faultTwoDto);
        //推送故障信息给96333
        logger.info("推送故障信息给96333");
        SetDevFaultForIOT setDevFaultForIOT = new SetDevFaultForIOT();
        List<Map> mapList = new ArrayList<>();
        Map map = new HashMap();
        map.put( "registration_code",faultTwoDto.getRegistrationCode());
        map.put( "lift_id", null);
        map.put( "fault_id","10");
        map.put( "fault_code",faultTwoDto.getFaultCode());
        map.put( "fault_time",faultTwoDto.getFaultTime());
        map.put( "service_mode",faultTwoDto.getServiceMode());
        map.put( "car_status",faultTwoDto.getCarStatus());
        map.put( "registration_code",faultTwoDto.getRegistrationCode());
        map.put( "car_direction",faultTwoDto.getCarDirection());
        map.put( "door_zone",faultTwoDto.getDoorZone());
        map.put( "car_position",faultTwoDto.getCarDirection());
        map.put( "door_status",faultTwoDto.getDoorStatus());
        map.put( "passenger_status",faultTwoDto.getPassengerStatus());
        map.put( "run_num",faultTwoDto.getRunNum());
        map.put( "operation_status",faultTwoDto.getOperationStatus());
        map.put("operation_direction", faultTwoDto.getOperationDirection());
        map.put("status_time", faultTwoDto.getStatusTime());
        mapList.add(map);
        JSONArray json = new JSONArray();
        json.addAll(mapList);
        json.listIterator();
        setDevFaultForIOT.setDevFaultInfo(json.toString());
        setDevFaultForIOT.setToken("381D4B51333C46489F71CDBEA5EA25E5");
        SetDevFaultForIOTResponse setDevFaultForIOTResponse=bpaLercLiftMonitorService.setDevFaultForIOT(setDevFaultForIOT);
        logger.info("推送故障信息给96333结束,返回结果是："+setDevFaultForIOTResponse.get_return());
//            BpaLercLiftMonitorServiceStub bpaLercLiftMonitorServiceStub = new BpaLercLiftMonitorServiceStub();
//            bpaLercLiftMonitorServiceStub.setDevFaultForIOT(new SetDevFaultForIOT());
//
//            bpaLercLiftMonitorServiceStub._setServiceClient(new ServiceClient());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            String endpoint = "http://localhost:8080/ca3/services/caSynrochnized?wsdl";
//            //直接引用远程的wsdl文件
//            //以下都是套路
//            Service service = new Service();
//            Call call = (Call) service.createCall();
//            call.setTargetEndpointAddress(endpoint);
//            call.setOperationName("addUser");//WSDL里面描述的接口名称
//            call.addParameter("userName", XMLType.XSD_DATE,
//                    javax.xml.ws.ParameterMode.IN);//接口的参数
//            call.setReturnType(XMLType.XSD_STRING);//设置返回类型
//
//            String temp = "测试人员";
//            String result = (String)call.invoke(new Object[]{temp});
//            //给方法传递参数，并且调用方法
//            System.out.println("result is "+result);
//        }
//        catch (Exception e) {
//            System.err.println(e.toString());
//        }
//    }
        return APIResponse.success();
    }

    /**
     *
     * @param number 电梯工号
     * @param registrationCode 注册代码
     * @return
     */
    @RequestMapping(value = "/faultRelieve",method =RequestMethod.GET)
    public APIResponse faultRelieve(@RequestParam("number") String number,
                                    @RequestParam("registrationCode") String registrationCode) {
        elevatorFaultService.faultRelieve(number,registrationCode);
        return APIResponse.success();
    }
}