package juli.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import juli.api.core.APIController;
import juli.api.core.APIResponse;
import juli.domain.AppVersion;
import juli.repository.AppVersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by pf on 2016/3/3.
 */
@Api(value = "APP版本API", description = " ")
@RestController
@RequestMapping("/api/appVersion")
public class AppVersionController extends APIController<AppVersion,AppVersionRepository> {

    @Autowired
    private AppVersionRepository appVersionRepository;

    @ApiOperation("获取最新版本")
    @RequestMapping(value = "/ios/getNewAppVersion",method = RequestMethod.GET)
    public APIResponse getNewAppVersion(@ApiParam("版本号")@RequestParam("number")String number)
    {
        AppVersion appVersion=appVersionRepository.findByIsNew(1);
        if(appVersion.getVsersionNumber().equals(number)) {
            return APIResponse.error("当前已经是最新版本");
        }
        return  APIResponse.success(appVersion);
    }
    @ApiOperation("添加最新版本")
    @RequestMapping(value = "/addNewAppVersion",method = RequestMethod.POST)
    public APIResponse addNewAppVersion(@ApiParam("待添加版本对象")@RequestParam("appVersion")AppVersion appVersion)
    {
        AppVersion av=appVersionRepository.findByIsNew(1);
        av.setIsNew(0);
        appVersionRepository.save(av);
        appVersionRepository.save(appVersion);
        return APIResponse.success();
    }

    @ApiOperation("更新版本信息")
    @RequestMapping(value = "/updateAppVersion",method = RequestMethod.POST)
    public APIResponse updateAppVersion(@ApiParam("带更新版本对象")AppVersion appVersion)
    {
        appVersionRepository.save(appVersion);
        return APIResponse.success();
    }

    @ApiOperation("删除版本")
    @RequestMapping(value = "/deleteAppVersion",method = RequestMethod.GET)
    public APIResponse deleteAppVersion(@ApiParam("待删除的版本的id")@RequestParam("id")String id)
    {
        appVersionRepository.delete(id);
        return APIResponse.success();
    }

}
