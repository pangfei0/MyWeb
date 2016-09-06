package juli.api;

import io.swagger.annotations.Api;
import juli.api.core.APIResponse;
import juli.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "省份城市API", description = " ")
@RestController
@RequestMapping("/api/area")
public class AreaAPIController {

    @Autowired
    AreaService provinceCityService;

    /**
     * 获得所有省信息
     */
    @RequestMapping("/province")
    public APIResponse getProvince() {
        return APIResponse.success(provinceCityService.getAllProvince());
    }

    /**
     * 获得某个父区域下面所有的子区域
     */
    @RequestMapping("/children/{parent}")
    public APIResponse getChildren(@PathVariable("parent") String parent) {
        return APIResponse.success(provinceCityService.getChildren(parent));
    }
}
