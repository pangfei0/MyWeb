package juli.infrastructure.freemarker;

import juli.service.TranslationService;
import juli.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 定义一些freemarker在前端能够调用到的方法
 */
@Component
public class FreemarkerFunc {
    @Autowired
    UserService userService;

    @Autowired
    TranslationService translationService;

    public boolean hasPermission(String permissionCode) {
        return SecurityUtils.getSubject().isPermitted(permissionCode);
    }

    public String getTranslation(String code) {
        return translationService.getTranslation(code);
    }
}
