package juli.api;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import juli.api.core.APIController;
import juli.api.core.APIResponse;
import juli.domain.Collector;
import juli.repository.CollectorRepository;
import juli.service.CollectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;

@Api(value = "采集器API", description = " ")
@RestController
@RequestMapping("/api/collector")
public class CollectorAPIController extends APIController<Collector, CollectorRepository> {

    @Autowired
    CollectorService collectorService;

    @Autowired
    CollectorRepository collectorRepository;

    @ApiOperation("搜索（分页）")
    @RequestMapping(value = "/new/search/page", method = RequestMethod.POST)
    public APIResponse searchByPage (ServletRequest request,
                                     @ApiParam(value = "当前页，从1开始", defaultValue = "1")
                                     @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                     @ApiParam(value = "每页条数", defaultValue = "15")
                                     @RequestParam(value = "pageSize", defaultValue = "15") int pageSize) {
        return collectorService.newsearch(request, pageNumber, pageSize);
    }

    @ApiOperation("添加采集器")
    @RequestMapping(method = RequestMethod.POST)
    public APIResponse create(Collector collector) {
        collectorRepository.save(collector);
        return APIResponse.success();
    }


    @ApiOperation("删除采集器")
    @RequestMapping(method = RequestMethod.DELETE)
    public APIResponse delete(Collector collector) {
        if (collector.getElevatorId() != null){
            return APIResponse.error("删除失败");
        }else {
            return APIResponse.success();
        }
    }




}
