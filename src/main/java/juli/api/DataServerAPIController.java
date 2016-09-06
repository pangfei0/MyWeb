package juli.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import juli.api.core.APIResponse;
import juli.service.DataServer.DataServerSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "DataServer API", description = " ")
@RestController
@RequestMapping("/api/dataserver")
public class DataServerAPIController {

    @Autowired
    DataServerSyncService dataServerSyncService;

    @ApiOperation("同步DataServer数据")
    @RequestMapping("/sync")
    public APIResponse sync() {
        try {
            dataServerSyncService.sync();
            return APIResponse.success();
        } catch (Exception e) {
            return APIResponse.error(e.getMessage());
        }
    }

}
