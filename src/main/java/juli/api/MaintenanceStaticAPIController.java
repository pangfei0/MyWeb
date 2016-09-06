package juli.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import juli.api.core.APIController;
import juli.api.core.APIResponse;
import juli.domain.*;
import juli.infrastructure.DateUtil;
import juli.repository.*;
import juli.service.MaintenancePersonnelService;
import juli.service.MaintenanceStaticService;
import juli.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Api(value = "维保人员API", description = " ")
@RestController
@RequestMapping("/api/static")
public class MaintenanceStaticAPIController extends APIController<MaintenancePersonnel, MaintenanceRepository> {
    @Autowired
    private MaintenanceStaticService maintenanceService;
    @Autowired
    UserService userService;
    @Autowired
    MaintenancePersonnelService maintenancePersonnelService;

    @ApiOperation("分页查询")
    @RequestMapping(value = "/workBillStatic/search/page",method = RequestMethod.POST)
    public APIResponse workBillStatic(ServletRequest request,
                                     @ApiParam(value = "当前页，从1开始", defaultValue = "1")
                                     @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                     @ApiParam(value = "每页条数", defaultValue = "15") @RequestParam(value = "pageSize", defaultValue = "15") int pageSize)
    {
        return  maintenanceService.findMaintenancePersonal(request, pageNumber, pageSize);
    }

    @ApiOperation("分页查询")
    @RequestMapping(value = "/satisfaction/search/page",method = RequestMethod.POST)
    public APIResponse satisfaction(ServletRequest request,
                                     @ApiParam(value = "当前页，从1开始", defaultValue = "1")
                                     @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                     @ApiParam(value = "每页条数", defaultValue = "15") @RequestParam(value = "pageSize", defaultValue = "15") int pageSize)
    {
        return  maintenanceService.findMaintenancePersonal(request, pageNumber, pageSize);
    }

    @ApiOperation("分页查询")
    @RequestMapping(value = "/result/search/page",method = RequestMethod.POST)
    public APIResponse result(ServletRequest request,
                                    @ApiParam(value = "当前页，从1开始", defaultValue = "1")
                                    @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                    @ApiParam(value = "每页条数", defaultValue = "15") @RequestParam(value = "pageSize", defaultValue = "15") int pageSize)
    {
        return  maintenanceService.findMaintenancePersonal(request, pageNumber, pageSize);
    }

    @ApiOperation("分页查询")
    @RequestMapping(value = "/fault/search/page",method = RequestMethod.POST)
    public APIResponse fault(ServletRequest request,
                              @ApiParam(value = "当前页，从1开始", defaultValue = "1")
                              @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                              @ApiParam(value = "每页条数", defaultValue = "15") @RequestParam(value = "pageSize", defaultValue = "15") int pageSize)
    {
        return  maintenanceService.findMaintenancePersonal(request, pageNumber, pageSize);
    }

    //excel导出

//    @RequiresPermissions("export")
    @RequestMapping(value = "/workBillExport",method = RequestMethod.GET)
    public APIResponse workBillExport(HttpServletResponse response,String startTime,String endTime) throws Exception {
        maintenanceService.workBillExport(response,startTime,endTime);
        return APIResponse.success();
    }

    @RequestMapping(value = "/satisfactionExport",method = RequestMethod.GET)
    public APIResponse satisfactionExport(HttpServletResponse response,String startTime,String endTime) throws Exception {
        maintenanceService.satisfactionExport(response, startTime, endTime);
        return APIResponse.success();
    }

    @RequestMapping(value = "/resultExport",method = RequestMethod.GET)
    public APIResponse resultExport(HttpServletResponse response,String startTime,String endTime) throws Exception {
        maintenanceService.resultExport(response, startTime, endTime);
        return APIResponse.success();
    }

    @RequestMapping(value = "/faultExport",method = RequestMethod.GET)
    public APIResponse faultExport(HttpServletResponse response,String startTime,String endTime) throws Exception {
        maintenanceService.faultExport(response, startTime, endTime);
        return APIResponse.success();
    }

    /**
     * app统计首页
     * @param personnelId
     * @param type
     * @return
     * @throws Exception
     */
    @ApiOperation("工单统计")
    @RequestMapping(value="/ios/billStatistic",method =RequestMethod.GET)
    public APIResponse billStatistic(@RequestParam("personnelId") String personnelId,@RequestParam("type") String type) throws Exception {//type  0：年  1:月  2：周 3：天
        //0表示当月，1表示上个月，2表示上两个月，以此类推
        List<Map> mapList = new ArrayList<>();
        if("1".equals(type)){//按月统计
            Map map0 =maintenanceService.billStatistic(DateUtil.stringToFormatDate(DateUtil.getFirstDayOfSomeMonth(0)), DateUtil.stringToFormatDate(DateUtil.getEndDayOfSomeMonth(0)), personnelId);//当月
            Map map1 =maintenanceService.billStatistic(DateUtil.stringToFormatDate(DateUtil.getFirstDayOfSomeMonth(1)), DateUtil.stringToFormatDate(DateUtil.getEndDayOfSomeMonth(1)), personnelId);//上个月
            Map map2 =maintenanceService.billStatistic(DateUtil.stringToFormatDate(DateUtil.getFirstDayOfSomeMonth(2)), DateUtil.stringToFormatDate(DateUtil.getEndDayOfSomeMonth(2)), personnelId);
            Map map3 =maintenanceService.billStatistic(DateUtil.stringToFormatDate(DateUtil.getFirstDayOfSomeMonth(3)), DateUtil.stringToFormatDate(DateUtil.getEndDayOfSomeMonth(3)), personnelId);
            Map map4 =maintenanceService.billStatistic(DateUtil.stringToFormatDate(DateUtil.getFirstDayOfSomeMonth(4)), DateUtil.stringToFormatDate(DateUtil.getEndDayOfSomeMonth(4)), personnelId);
            Map map5 =maintenanceService.billStatistic(DateUtil.stringToFormatDate(DateUtil.getFirstDayOfSomeMonth(5)), DateUtil.stringToFormatDate(DateUtil.getEndDayOfSomeMonth(5)), personnelId);
            mapList.add(map0);
            mapList.add(map1);
            mapList.add(map2);
            mapList.add(map3);
            mapList.add(map4);
            mapList.add(map5);
        }else if("2".equals(type)){//按周统计
            Map map0 =maintenanceService.billStatistic(DateUtil.stringToFormatDate(DateUtil.getMonday(0)),DateUtil.stringToFormatDate(DateUtil.getSunday(0)), personnelId);//本周
            Map map1 =maintenanceService.billStatistic(DateUtil.stringToFormatDate(DateUtil.getMonday(1)),DateUtil.stringToFormatDate(DateUtil.getSunday(1)), personnelId);//上周
            Map map2 =maintenanceService.billStatistic(DateUtil.stringToFormatDate(DateUtil.getMonday(2)),DateUtil.stringToFormatDate(DateUtil.getSunday(2)), personnelId);
            Map map3 =maintenanceService.billStatistic(DateUtil.stringToFormatDate(DateUtil.getMonday(3)),DateUtil.stringToFormatDate(DateUtil.getSunday(3)), personnelId);
            Map map4 =maintenanceService.billStatistic(DateUtil.stringToFormatDate(DateUtil.getMonday(4)),DateUtil.stringToFormatDate(DateUtil.getSunday(4)), personnelId);
            Map map5 =maintenanceService.billStatistic(DateUtil.stringToFormatDate(DateUtil.getMonday(5)),DateUtil.stringToFormatDate(DateUtil.getSunday(5)), personnelId);
            mapList.add(map0);
            mapList.add(map1);
            mapList.add(map2);
            mapList.add(map3);
            mapList.add(map4);
            mapList.add(map5);
        }else if("0".equals(type)){//按年统计
            Map map0 =maintenanceService.billStatistic(DateUtil.stringToFormatDate(DateUtil.getYearFirst(2016)),DateUtil.stringToFormatDate(DateUtil.getYearLast(2016)), personnelId);//今天
            Map map1 =maintenanceService.billStatistic(DateUtil.stringToFormatDate(DateUtil.getYearFirst(2015)),DateUtil.stringToFormatDate(DateUtil.getYearLast(2015)), personnelId);//昨天
            Map map2 =maintenanceService.billStatistic(DateUtil.stringToFormatDate(DateUtil.getYearFirst(2014)),DateUtil.stringToFormatDate(DateUtil.getYearLast(2014)), personnelId);
            Map map3 =maintenanceService.billStatistic(DateUtil.stringToFormatDate(DateUtil.getYearFirst(2013)),DateUtil.stringToFormatDate(DateUtil.getYearLast(2013)), personnelId);
            Map map4 =maintenanceService.billStatistic(DateUtil.stringToFormatDate(DateUtil.getYearFirst(2012)),DateUtil.stringToFormatDate(DateUtil.getYearLast(2012)), personnelId);
            Map map5 =maintenanceService.billStatistic(DateUtil.stringToFormatDate(DateUtil.getYearFirst(2011)),DateUtil.stringToFormatDate(DateUtil.getYearLast(2011)), personnelId);
            mapList.add(map0);
            mapList.add(map1);
            mapList.add(map2);
            mapList.add(map3);
            mapList.add(map4);
            mapList.add(map5);
        }else if("3".equals(type)){//按天计算
            Map map0 =maintenanceService.billStatistic(DateUtil.stringToFormatDate(DateUtil.getSomeDayFromToday(0)),DateUtil.stringToFormatDate(DateUtil.getSomeDayFromToday(0)), personnelId);//今天
            Map map1 =maintenanceService.billStatistic(DateUtil.stringToFormatDate(DateUtil.getSomeDayFromToday(1)),DateUtil.stringToFormatDate(DateUtil.getSomeDayFromToday(1)), personnelId);//昨天
            Map map2 =maintenanceService.billStatistic(DateUtil.stringToFormatDate(DateUtil.getSomeDayFromToday(2)),DateUtil.stringToFormatDate(DateUtil.getSomeDayFromToday(2)), personnelId);
            Map map3 =maintenanceService.billStatistic(DateUtil.stringToFormatDate(DateUtil.getSomeDayFromToday(3)),DateUtil.stringToFormatDate(DateUtil.getSomeDayFromToday(3)), personnelId);
            Map map4 =maintenanceService.billStatistic(DateUtil.stringToFormatDate(DateUtil.getSomeDayFromToday(4)),DateUtil.stringToFormatDate(DateUtil.getSomeDayFromToday(4)), personnelId);
            Map map5 =maintenanceService.billStatistic(DateUtil.stringToFormatDate(DateUtil.getSomeDayFromToday(5)),DateUtil.stringToFormatDate(DateUtil.getSomeDayFromToday(5)), personnelId);
            mapList.add(map0);
            mapList.add(map1);
            mapList.add(map2);
            mapList.add(map3);
            mapList.add(map4);
            mapList.add(map5);
        }else{
            //其他
        }
        return APIResponse.success(mapList);
    }

    @ApiOperation("根据时间和类别分页查询不同的工单")
    @RequestMapping(value="/ios/getBillsByType",method =RequestMethod.GET)
    public APIResponse getBillsByType(@RequestParam("personnelId") String personnelId,
                                      @RequestParam("type") String type, //type 1:维保 2：急修 3：维修
                                      @RequestParam("startTime") String startTime,
                                      @RequestParam("endTime")String endTime,
                                      @ApiParam(value="从当前页开始",defaultValue ="1")@RequestParam(value = "pageNumber",defaultValue = "1") Integer pageNumber,
                                      @ApiParam(value="每页条数",defaultValue ="15")@RequestParam(value = "pageSize",defaultValue = "15") Integer pageSize) throws Exception {
        return maintenanceService.getBillsByType(DateUtil.stringToFormatDate(startTime + " 00:00:00"), DateUtil.stringToFormatDate(endTime + " 23:59:59"), personnelId, type, pageNumber, pageSize);
    }

    @ApiOperation("根据时间和优良中差分页查询不同的工单")
    @RequestMapping(value="/ios/getBillsByCommet",method =RequestMethod.GET)
    public APIResponse getBillsByComment (@RequestParam("personnelId") String personnelId,
                                      @RequestParam("type") String type, //type 1:优秀 2：良好 3：差
                                      @RequestParam("startTime") String startTime,
                                      @RequestParam("endTime")String endTime,
                                      @ApiParam(value="从当前页开始",defaultValue ="1")@RequestParam(value = "pageNumber",defaultValue = "1") Integer pageNumber,
                                      @ApiParam(value="每页条数",defaultValue ="15")@RequestParam(value = "pageSize",defaultValue = "15") Integer pageSize) throws Exception {
        return maintenanceService.getBillsByComment(DateUtil.stringToFormatDate(startTime+" 00:00:00"),DateUtil.stringToFormatDate(endTime+" 23:59:59"),personnelId,type,pageNumber,pageSize);
    }

}