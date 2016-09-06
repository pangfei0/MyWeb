package juli.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {

    @RequestMapping("/")
    public String index() {
        return "all";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/registerVisitor")
    public String registerVisitor(){
        return "registerVisitor";
    }

    @RequestMapping("/welcome")
    public String welcome(){
        return "welcome";
    }

    @RequestMapping("/faultElevators")
    public String fault() {
        return "faultElevators";
    }

    @RequestMapping("/all")
    public String all(){
        return "all";
    }

}