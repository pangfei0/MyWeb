package juli.api;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import juli.api.core.APIResponse;
import juli.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Api(value = "国际化API", description = " ")
@RestController
@RequestMapping("/api/translation")
public class TranslationAPIController {

    @Autowired
    TranslationService translationService;

    @RequestMapping("/data.js")
    public String getAllJsTranslation() {
        String json = JSON.toJSONString(translationService.getAllTranslations());
        return "define(function () { return " +
                json +
                "});";
    }

    @ApiOperation("更改网站显示语言")
    @RequestMapping(value = "/change/{code}", method = RequestMethod.GET)
    public APIResponse getMenusByCategory(@PathVariable("code") String code) {
        translationService.changeLanguage(code);
        return APIResponse.success();
    }
}
