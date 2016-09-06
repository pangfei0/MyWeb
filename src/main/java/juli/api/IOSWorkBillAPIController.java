package juli.api;

import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import juli.api.core.APIController;
import juli.api.core.APIResponse;
import juli.api.dto.BillStaticsDto;
import juli.api.dto.WorkBillDto;
import juli.domain.Elevator;
import juli.domain.WorkBills;
import juli.domain.enums.BillStatus;
import juli.infrastructure.DateUtil;
import juli.repository.WorkBillRepository;
import juli.service.WorkBillService;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "工单API", description = " ")
@RestController
@RequestMapping("/api/workBill")
public class IOSWorkBillAPIController extends APIController<WorkBills, WorkBillRepository> {

    @Autowired
    WorkBillRepository workBillRepository;

    @Autowired
    WorkBillService workBillService;


    Map paramMap=new HashMap<String,Object>();

//IOS端接口
    @ApiOperation("获得工单对象")
    @RequestMapping(value = "/ios/getPersonalBills", method = RequestMethod.GET)
    public APIResponse getBills(HttpSession session,@ApiParam("唯一客户端")@RequestParam("sid")String sid,@ApiParam("对象ID")@RequestParam("id")String id) {
        String ssid = (String) session.getAttribute("sid");
        if(ssid!=null) {
            if (ssid.equals(sid)) {
                WorkBills workBills = workBillRepository.findOne(id);
                if (workBills == null) {
                    return APIResponse.error("此工单对象不存在" + id);
                }
                paramMap.put("id", workBills.getId());
                paramMap.put("status", BillStatus.get(workBills.getBillstatus()).getName());
                if (workBills.getElevator() != null) {
                    Elevator elevator = workBills.getElevator();
                    paramMap.put("elevator", elevator.getNumber() + "/" + elevator.getAddress());
                }
                return APIResponse.success(paramMap);
            } else {
                return APIResponse.error("请重新登录");
            }
        }
        else
        {
            return APIResponse.error("请重新登录");
        }
    }

    @ApiOperation("获取当前维修人员的所有工单")
    @RequestMapping(value="/ios/getBillByStatusAndCategory",method =RequestMethod.GET)
    public APIResponse getBillByStatusAndCategory(
            HttpSession session,
            @ApiParam("唯一客户端")@RequestParam("sid")String sid,
            @ApiParam(value="员工编号")@RequestParam(value = "id")String id,
            @ApiParam(value="工单状态")@RequestParam(value = "status1") Integer status1,
            @ApiParam(value="工单状态")@RequestParam(value = "status2") Integer status2,
            @ApiParam(value="工单类别")@RequestParam(value = "category") Integer  category,
            @ApiParam(value="从当前页开始",defaultValue ="1")@RequestParam(value = "pageNumber",defaultValue = "1") Integer pageNumber,
            @ApiParam(value="每页条数",defaultValue ="1000")@RequestParam(value = "pageSize",defaultValue = "1000") Integer pageSize)
    {
        String ssid = (String) session.getAttribute("sid");
        if(ssid!=null) {
            if (ssid.equals(sid)) {
               PageImpl<Map> billsPage = workBillService.findPersonalBillByCategroyAndStatus(id, category, status1, status2, pageNumber, pageSize);
                return APIResponse.success(billsPage);
            } else {
                return APIResponse.error("请重新登录");
            }

        }
        else
        {
            return  APIResponse.error("请重新登录");
        }
    }

    @ApiOperation("获取工单详情")
    @RequestMapping(value = "/ios/getBillById")
    public APIResponse getDetailById(HttpSession session,@ApiParam("唯一客户端")@RequestParam("sid")String sid,@ApiParam(value="员工编号")@RequestParam(value = "id")String id) throws Exception {
        String ssid = (String) session.getAttribute("sid");
        if(ssid!=null) {
            if (ssid.equals(sid)) {
                return workBillService.getBillById(id);
            } else {
                return APIResponse.error("请重新登录");
            }

        }
        else
        {
            return  APIResponse.error("请重新登录");
        }

    }

    @ApiOperation("执行（维保维修工单）或者拒绝（急修工单）")
    @RequestMapping(value = "/ios/implementBill")
    public APIResponse implementBill(HttpSession session,
                                     @ApiParam("唯一客户端")@RequestParam("sid")String sid,@ApiParam(value="工单id")@RequestParam(value = "id")String id,@ApiParam(value="修改状态")@RequestParam(value = "status")Integer status) throws Exception {
        String ssid = (String) session.getAttribute("sid");
        if(ssid!=null) {
            if (ssid.equals(sid)) {
                return workBillService.changeBillStatus(id, status);
            } else {
                return APIResponse.error("请重新登录");
            }

        }
        else
        {
            return  APIResponse.error("请重新登录");
        }
    }

    @ApiOperation("维保/维修拒绝工单操作")
    @RequestMapping(value = "/ios/refuseBill")
    public APIResponse refuseBill(HttpSession session,
                                  @ApiParam("唯一客户端")@RequestParam("sid")String sid,
                                  @ApiParam("工单id")@RequestParam("id")String id,
                                  @ApiParam("拒绝接受原因")@RequestParam("refuseReason")String refuseReason)
    {
        String ssid = (String) session.getAttribute("sid");
        if(ssid!=null) {
            if (ssid.equals(sid)) {
                return workBillService.refuseBill(id, refuseReason);
            } else {
                return APIResponse.error("请重新登录");
            }

        }
        else
        {
            return  APIResponse.error("请重新登录");
        }
    }

    @ApiOperation("维修工单挂起")
    @RequestMapping(value = "/ios/hangWXBill")
    public APIResponse hangWXBill(HttpSession session,
                                @ApiParam("唯一客户端")@RequestParam("sid") String sid,
                                @ApiParam("工单id")@RequestParam("id") String id,
                                @ApiParam("现场描述")@RequestParam("localDescription") String localDescription,
                                @ApiParam("采取措施")@RequestParam("makesteps")String makesteps,
                                @ApiParam("故障性质")@RequestParam("faultQuality")String faultQuality,
                                @ApiParam("处理结果")@RequestParam("result")String result,
                                  @ApiParam(value="图片路径")@RequestParam(value = "picPath") String picPath,
                                @ApiParam("30表示挂起,50表示完成")@RequestParam("status") Integer status,@ApiParam(value="协助人员")@RequestParam(value = "assists")String assists)
    {
        String ssid = (String) session.getAttribute("sid");
        if(ssid!=null) {
            if (ssid.equals(sid)) {
                return workBillService.hangWXBill(id, localDescription, makesteps, faultQuality, result, status,assists,picPath);
            } else {
                return APIResponse.error("请重新登录");
            }

        }
        else
        {
            return  APIResponse.error("请重新登录");
        }
    }

    @ApiOperation("维保工单挂起")
    @RequestMapping(value = "/ios/hangWBBill")
    public APIResponse hangWBBill(HttpSession session,
                                  @ApiParam("唯一客户端")@RequestParam("sid") String sid,
                                  @ApiParam("工单id")@RequestParam("id") String id,
                                  @ApiParam(value="图片路径")@RequestParam(value = "picPath") String picPath,
                                  @ApiParam(name = "维保项目主键集合",defaultValue = "")@RequestParam("maintainPrograms")String maintainPrograms,
                                  @ApiParam(name = "处理结果",defaultValue = "")@RequestParam("result")String result,
                                  @ApiParam(name = "30表示挂起,50表示完成",defaultValue ="30")@RequestParam("status") Integer status,@ApiParam(value="协助人员")@RequestParam(value = "assists")String assists) throws Exception {
        String ssid = (String) session.getAttribute("sid");
        if(ssid!=null) {
            if (ssid.equals(sid)) {
                return workBillService.hangWBBill(id, maintainPrograms, result, status,assists,picPath);
            } else {
                return APIResponse.error("请重新登录");
            }

        }
        else
        {
            return  APIResponse.error("请重新登录");
        }
    }

    @ApiOperation("报告图片提交")
    @RequestMapping(value="/ios/commitWorBillPic",method = RequestMethod.POST)
    public APIResponse commitReport(
            HttpServletRequest request,
            @ApiParam(value="工单编号")@RequestParam(value = "id") String id,
            @ApiParam(value="图片顺序")@RequestParam(value = "sort") Integer  sort

    ) throws IOException {



        return workBillService.commitReportPicture(request,id,sort);

    }
    @ApiOperation("急修工单挂起/完成")
    @RequestMapping(value = "/ios/hangJXBill")
    public APIResponse hangJXBill(HttpSession session,
                                  @ApiParam("唯一客户端")@RequestParam("sid") String sid,
                                  @ApiParam("工单id")@RequestParam("id") String id,
                                  @ApiParam("现场描述")@RequestParam("localDescription") String localDescription,
                                  @ApiParam("采取措施")@RequestParam("makesteps")String makesteps,
                                  @ApiParam("故障性质")@RequestParam("faultQuality")String faultQuality,
                                  @ApiParam("处理结果")@RequestParam("result")String result,
                                  @ApiParam(value="图片路径")@RequestParam(value = "picPath") String picPath,
                                  @ApiParam("30表示挂起,50表示完成")@RequestParam("status") Integer status,@ApiParam(value="协助人员")@RequestParam(value = "assists")String assists) throws Exception {
        String ssid = (String) session.getAttribute("sid");
        if(ssid!=null) {
            if (ssid.equals(sid)) {
                return workBillService.hangJXBill(id,localDescription, makesteps, faultQuality, result, status,assists,picPath);
            } else {
                return APIResponse.error("请重新登录");
            }

        }
        else
        {
            return  APIResponse.error("请重新登录");
        }
    }

    @ApiOperation("获取某电梯的历史维修记录")
    @RequestMapping(value="/ios/getElevatorHistoryBills",method =RequestMethod.GET)
   public APIResponse findBillsByElevatorAndBillCategoryAndBillstatus( HttpSession session,
                                                                       @ApiParam("唯一客户端")@RequestParam("sid") String sid,
                                                                       @ApiParam("电梯id")@RequestParam("id") String id,
                                                                       @ApiParam(value="工单类别",defaultValue ="10")@RequestParam(value="category",defaultValue ="10")Integer category,
                                                                       @ApiParam(value="从当前页开始",defaultValue ="1")@RequestParam(value = "pageNumber",defaultValue = "1") Integer pageNumber,
                                                                       @ApiParam(value="每页条数",defaultValue ="15")@RequestParam(value = "pageSize",defaultValue = "15") Integer pageSize)
   {
       String ssid = (String) session.getAttribute("sid");
       if(ssid!=null) {
           if (ssid.equals(sid)) {
               return workBillService.findBillsByElevatorAndBillCategoryAndBillstatus(id, category, pageNumber, pageSize);
           } else {
               return APIResponse.error("请重新登录");
           }

       }
       else
       {
           return  APIResponse.error("请重新登录");
       }

   }
    //获取不同工单的类别的未处理数量
    @ApiOperation("获取某员工的不同类型工单未处理的数量")
    @RequestMapping(value="/ios/getUndoWorkBills",method =RequestMethod.GET)
    public APIResponse getCountByStatus(HttpSession session,
                                        @ApiParam(value="工单类别",defaultValue ="10")@RequestParam(value="category",defaultValue ="10") Integer category,
                                        @ApiParam("唯一客户端")@RequestParam("sid")String sid,
                                        @ApiParam("员工id")@RequestParam("id") String id)
    {
        String ssid = (String) session.getAttribute("sid");
        if(ssid!=null) {
            if (ssid.equals(sid)) {
                System.out.print("获取某员工的不同类型工单未处理的数量");
                return workBillService.getCountByStatus(category, id);
            } else {
                return APIResponse.error("请重新登录");
            }

        }
        else
        {
            return  APIResponse.error("请重新登录");
        }

    }
    //web端接口

    @ApiOperation("根据条件搜索（分页）")
    @RequestMapping(value="/new/search/page",method =RequestMethod.POST)
    public APIResponse searchNewPage(ServletRequest request,
                                 @ApiParam(value="从当前页开始",defaultValue ="1")@RequestParam(value = "pageNumber",defaultValue = "1") Integer pageNumber,
                                 @ApiParam(value="每页条数",defaultValue ="15")@RequestParam(value = "pageSize",defaultValue = "15") Integer pageSize)
    {
           return workBillService.newsearch(request,pageNumber,pageSize);
    }
    @ApiOperation("删除工单")
    @RequestMapping(value="/delete/{id}",method =RequestMethod.DELETE)
    public APIResponse deleteWorkBill( @ApiParam("工单id") @PathVariable("id") String id)
    {
        return workBillService.deleteBill(id);
    }
    @ApiOperation("获取未完成（分页）")
    @RequestMapping(value="/getJXBills",method =RequestMethod.POST)
    public APIResponse getJXBills(
                                  @ApiParam(value="从当前页开始",defaultValue ="1")@RequestParam(value = "pageNumber",defaultValue = "1") Integer pageNumber,
                                  @ApiParam(value="每页条数",defaultValue ="5")@RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize)
    {
          return workBillService.getJXBills(pageNumber, pageSize);
    }

    @ApiOperation("获取各类工单所有工单")
    @RequestMapping(value="/getBills/{status}",method =RequestMethod.POST)
    public APIResponse getBills(
            HttpSession session,
            @ApiParam("工单状态名称") @PathVariable("status") String status,
            @ApiParam(value="从当前页开始",defaultValue ="1")@RequestParam(value = "pageNumber",defaultValue = "1") Integer pageNumber,
            @ApiParam(value="每页条数",defaultValue ="15")@RequestParam(value = "pageSize",defaultValue = "15") Integer pageSize)
    {
        if(status.equals("all"))
        {
            return workBillService.findBills(0, 60, pageNumber, pageSize);
        }
        if(status.equals("history"))
            return workBillService.findBills(46, 60, pageNumber, pageSize);
        if(status.equals("unhistory"))
        {
            return workBillService.findBills(0, 45, pageNumber, pageSize);
        }
        else
        {
                return APIResponse.error("非法的状态");
        }

    }
    @ApiOperation("导出工单")
    @RequestMapping(value="/exportExcel",method =RequestMethod.GET)
    public APIResponse exportExcel(String startTime1,String startTime2,String completeTime1,String  completeTime2,Integer billCategory, String elevatorNumber,HttpServletResponse response) throws Exception {
        Date time1=null;
        Date time2=null;
        if(StringUtils.isNotEmpty(startTime1)&&StringUtils.isNotEmpty(startTime2)){
             time1= DateUtil.stringToFullDateformat(startTime1);
             time2=DateUtil.stringToFullDateformat(startTime2);
        }

//        Date time3=DateUtil.stringToFullDateformat(completeTime1);
//        Date time4=DateUtil.stringToFullDateformat(completeTime2);
        if(billCategory==null){
            billCategory = 20;
        }
        workBillService.exportExcel(time1,time2,billCategory,elevatorNumber,response);
        return APIResponse.success();
    }
    @ApiOperation("查询工单详细")
    @RequestMapping(value="/detail/{id}",method =RequestMethod.GET)
    public APIResponse get(
            HttpSession session,
            @ApiParam("工单id")@PathVariable("id") String id) throws Exception {

        return workBillService.getBillById(id);

    }
    @ApiOperation("查询Dto工单详细")
    @RequestMapping(value="/dto/{id}",method =RequestMethod.GET)
    public APIResponse getDto(
            HttpSession session,
            @ApiParam("工单id")@PathVariable("id") String id) throws Exception {

        return workBillService.getDto(id);

    }
    @ApiOperation("判断电梯是否存在")
    @RequestMapping(value="/existElevator",method = RequestMethod.POST)
    public APIResponse existElevator(@ApiParam("num")String num){
        return workBillService.existElevator(num);
    }
    @ApiOperation("新建工单")
    @RequestMapping(value="/create",method = RequestMethod.POST)
    public APIResponse createBill(
                                    @ApiParam("立即派发还是稍后派发 1为立即派发 2为稍后派发")Integer actiontype,
                                    WorkBillDto dto) throws Exception {
       return  workBillService.create(actiontype, dto);
    }

    @ApiOperation("更新工单")
    @RequestMapping(value="/{id}",method = RequestMethod.POST)
    public APIResponse updateBill(@ApiParam("工单ID")@PathVariable("id")String id,
                                  @ApiParam("立即派发还是稍后派发 1为立即派发 2为稍后派发")Integer actiontype,
                                  @ApiParam("需要更新的对象")WorkBillDto dto) throws InvocationTargetException, IllegalAccessException {
        return  workBillService.update(id, actiontype, dto);
    }

    @ApiOperation("派发工单")
    @RequestMapping(value = "/sendBill/{id}")
    public APIResponse sendBill(@ApiParam("工单id")@PathVariable("id")String id) throws PushClientException, PushServerException {
        return  workBillService.sendBill(id);
    }

    @ApiOperation("拒绝工单改派")
    @RequestMapping(value = "/sendJXBill/{id}",method = RequestMethod.PATCH)
    public APIResponse sendBill(@ApiParam("拒绝工单ID")@PathVariable("id")String id,
                                @ApiParam("立即派发还是稍后派发 1为立即派发 2为稍后派发")Integer actiontype,
                                WorkBillDto dto) throws Exception {
        return  workBillService.sendRefuseBill(id, actiontype, dto);
    }

}