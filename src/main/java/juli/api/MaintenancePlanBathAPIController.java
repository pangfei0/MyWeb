package juli.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import juli.api.core.APIController;
import juli.api.core.APIResponse;
import juli.domain.MaintenancePlan;
import juli.repository.MaintenancePlanRepository;
import juli.service.MaintenancePlanBathService;
import juli.service.MaintenancePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;

@Api(value = "维保批次API", description = " ")
@RestController
@RequestMapping("/api/maintenancePlanBath")
public class MaintenancePlanBathAPIController extends APIController<MaintenancePlan,MaintenancePlanRepository> {

    @Autowired
    MaintenancePlanBathService maintenancePlanService;

    @ApiOperation("搜索（分页）")
    @RequestMapping(value = "/new/search/page", method = RequestMethod.POST)
    public APIResponse searchByPage (ServletRequest request,
                                     @ApiParam(value = "当前页，从1开始", defaultValue = "1")
                                     @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                     @ApiParam(value = "每页条数", defaultValue = "15")
                                     @RequestParam(value = "pageSize", defaultValue = "15") int pageSize) {
        return maintenancePlanService.newsearch(request,pageNumber,pageSize);
    }


    @ApiOperation("删除批次")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public APIResponse deleteBath (@ApiParam("批次id") @PathVariable("id") String id ) {
        return maintenancePlanService.deleteBath(id);
    }


}
