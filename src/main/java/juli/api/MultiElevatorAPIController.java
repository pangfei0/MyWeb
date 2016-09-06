package juli.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import juli.api.core.APIController;
import juli.api.core.APIResponse;
import juli.domain.Elevator;
import juli.repository.ElevatorBrandRepository;
import juli.repository.ElevatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;

@Api(value = "多品牌电梯API", description = " ")
@RestController
@RequestMapping("/api/multiElevator")
public class MultiElevatorAPIController extends APIController<Elevator, ElevatorRepository> {

    @Autowired
    ElevatorRepository elevatorRepository;

    @Autowired
    ElevatorBrandRepository brandRepository;

    @ApiOperation("搜索(带分页)")
    @RequestMapping(value = "/search/page", method = RequestMethod.POST)
    public APIResponse searchPage(ServletRequest request,
                                  @ApiParam(value = "当前页，从1开始", defaultValue = "1")
                                  @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                  @ApiParam(value = "每页条数", defaultValue = "15")
                                  @RequestParam(value = "pageSize", defaultValue = "15") int pageSize){
        try {
            Page entities = elevatorRepository.findByProtocolTypeNotNull(new PageRequest(pageNumber - 1, pageSize));

            return APIResponse.success(entities);
        } catch (Exception e) {
            return APIResponse.error(e.getMessage());
        }
    }





}
