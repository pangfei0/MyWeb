package juli.controller.weixin.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by pangfei on 2016/9/7.
 */
@Controller
@RequestMapping("weixin/achieve")
public class WeixinAchiveController {
    @RequestMapping("/elevatorSearch")
    public String elevatorSearch()
    {

        System.out.println("wqewqeqweqwewq");
        return "weixin/visitorSearch";
    }
}
