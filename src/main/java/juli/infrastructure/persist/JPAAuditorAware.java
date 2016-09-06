package juli.infrastructure.persist;

import juli.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component
public class JPAAuditorAware implements AuditorAware<String> {

    @Override
    public String getCurrentAuditor() {
        try {
            Subject subject = SecurityUtils.getSubject();
            if (subject != null) {
                return ((User) subject.getPrincipal()).getNick();
            }
        } catch (Exception e) {
        }

        return "";
    }
}