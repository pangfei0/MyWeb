package juli.api.core;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 重定向/swagger-ui.html
 */
@Controller
@ApiIgnore
public class SwaggerController {

    @RequestMapping("/api")
    public String index() {
        return "redirect:/swagger-ui.html";
    }

    @RequestMapping("/doc")
    public String docIndex() {
        return "redirect:/swagger-ui.html";
    }
}