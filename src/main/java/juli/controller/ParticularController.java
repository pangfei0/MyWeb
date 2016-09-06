package juli.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ParticularController {

    @RequestMapping("/multiElevator")
    public String particular() {
        return "multiElevator";
    }



}
