package juli.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import juli.api.core.APIController;
import juli.api.core.APIResponse;
import juli.api.dto.MaintenancePlanDto;
import juli.domain.MaintenancePlan;
import juli.repository.MaintenancePlanRepository;
import juli.service.MaintenancePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;

@Api(value = "维保计划API", description = " ")
@RestController
@RequestMapping("/api/maintenancePlan")
public class MaintenancePlanAPIController extends APIController<MaintenancePlan,MaintenancePlanRepository> {

    @Autowired
    MaintenancePlanService maintenancePlanService;

    @ApiOperation("搜索（分页）")
    @RequestMapping(value = "/new/search/page", method = RequestMethod.POST)
    public APIResponse searchByPage (ServletRequest request,
                                     @ApiParam(value = "当前页，从1开始", defaultValue = "1")
                                     @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                     @ApiParam(value = "每页条数", defaultValue = "15")
                                     @RequestParam(value = "pageSize", defaultValue = "15") int pageSize) {
        return maintenancePlanService.newsearch(request, pageNumber, pageSize);
    }

    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    public APIResponse findById(@PathVariable("id") String id){
        return maintenancePlanService.findById(id);
    }


    @ApiOperation("新增")
    @RequestMapping(method = RequestMethod.POST)
    public APIResponse create(MaintenancePlanDto maintenancePlanDto) {
        return maintenancePlanService.addMaintenancePlan(maintenancePlanDto);
    }


    @ApiOperation("更新")
    @RequestMapping(value = "/upd/{id}", method = RequestMethod.POST)
    public APIResponse update(@PathVariable("id") String id,MaintenancePlanDto maintenancePlanDto) throws InvocationTargetException, IllegalAccessException {
        return maintenancePlanService.updateMaintenancePlan(id, maintenancePlanDto);
    }

    @ApiOperation("删除")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public APIResponse delete(@ApiParam("对象ID") @PathVariable("id") String id) {
       return maintenancePlanService.deleteMaintenancePlan(id);
    }

    @ApiOperation("获取工单计划")
    @RequestMapping(value="/ios/findPlanById",method =RequestMethod.GET)
    public APIResponse getPlanByMaintenancePersonnel(HttpSession session,
                                        @ApiParam("唯一客户端")@RequestParam("sid")String sid,
                                        @ApiParam("员工id")@RequestParam("id") String id,
                                                     @ApiParam("开始时间")@RequestParam("beginTime") String beginTime,
                                                     @ApiParam("结束时间")@RequestParam("endTime") String endTime

    )
    {
        String ssid = (String) session.getAttribute("sid");
        if(ssid!=null) {
            if (ssid.equals(sid)) {
                System.out.print("获取工单计划");
                try {
                    java.text.SimpleDateFormat sf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date b = sf.parse(beginTime);//不是yyyy-MM-dd格式的date型
                    java.util.Date be = new java.sql.Date(b.getTime());//转换成了2009-11-04型了
                    java.util.Date e = sf.parse(endTime);//不是yyyy-MM-dd格式的date型
                    java.util.Date en = new java.sql.Date(e.getTime());//转换成了2009-11-04型了
                    return maintenancePlanService.getPlanByMaintenancePersonnel(id,be,en);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                return APIResponse.error("请重新登录");
            }

        }
        else
        {
            return  APIResponse.error("请重新登录");
        }

        return null;
    }

    @ApiOperation("保存计划备注")
    @RequestMapping(value="/ios/savePlanContent",method =RequestMethod.GET)
    public APIResponse savePlanContent(HttpSession session,
                                                     @ApiParam("唯一客户端")@RequestParam("sid")String sid,
                                                     @ApiParam("计划id")@RequestParam("id") String id,
                                       @ApiParam("计划备注")@RequestParam("content") String content)
    {
        String ssid = (String) session.getAttribute("sid");
        if(ssid!=null) {
            if (ssid.equals(sid)) {
//            if (true) {
                System.out.print("保存计划备注");
                return maintenancePlanService.savePlanContent(id,content);
            } else {
                return APIResponse.error("请重新登录");
            }

        }
        else
        {
            return  APIResponse.error("请重新登录");
        }

    }

}
