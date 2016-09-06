package juli.filter;

import juli.domain.enums.ElevatorFaultStatus;
import juli.domain.enums.ElevatorMaintennanceStatus;
import juli.domain.enums.ElevatorStatus;
import juli.infrastructure.exception.JuliException;
import juli.infrastructure.freemarker.FreemarkerFunc;
import juli.service.DataServer.DataServerSyncService;
import juli.service.ElevatorService;
import juli.service.MenuService;
import juli.service.TranslationService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * spring web controller 拦截器
 */
public class WebMVCInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    MenuService menuService;

    @Autowired
    TranslationService translationService;

    @Autowired
    ElevatorService elevatorService;

    @Autowired
    FreemarkerFunc freemarkerFunc;

    @Autowired
    DataServerSyncService syncService;

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        if (modelAndView == null || modelAndView.getViewName().startsWith("redirect:")) return;

        addLoginUser(modelAndView);
        addTranslations(modelAndView);
        addElevatorStatus(modelAndView);
        addFreemarkerFunctions(modelAndView);
    }

    /**
     * 注册一些freemarker使用的functions
     */
    private void addFreemarkerFunctions(ModelAndView modelAndView) {
        modelAndView.getModel().put("freemarkerFunc", freemarkerFunc);
    }

    private void addElevatorStatus(ModelAndView modelAndView) throws JuliException, IOException {
        String sid = "";
        if (syncService.isLogined()) {
            sid = syncService.getSid();
        } else {
            sid = syncService.login();
        }
        modelAndView.getModel().put("sid", sid);
        modelAndView.getModel().put("elevatorCountAll", elevatorService.getAllElevatorCount());
        modelAndView.getModel().put("elevatorCountOnline", elevatorService.getElevatorCount(ElevatorStatus.ONLINE));
        modelAndView.getModel().put("elevatorCountMalfunction", elevatorService.getElevatorCount(ElevatorFaultStatus.MALFUNCTION));
        modelAndView.getModel().put("elevatorCountRecondition", elevatorService.getElevatorCount(ElevatorMaintennanceStatus.RECONDITION));
        modelAndView.getModel().put("elevatorCountOffline", elevatorService.getElevatorCount(ElevatorStatus.OFFLINE));
    }

    private void addTranslations(ModelAndView modelAndView) {
        modelAndView.getModel().put("currentLanguage", translationService.getCurrentLanguage());
    }

    private void addLoginUser(ModelAndView modelAndView) {
        try {
            Subject subject = SecurityUtils.getSubject();
            modelAndView.getModel().put("loginUser", subject.getPrincipal());
        } catch (UnavailableSecurityManagerException e) {
            //no-op
        }
    }
}
