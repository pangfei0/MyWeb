package juli.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RescueController {

    @RequestMapping("/gridRescue")
    public ModelAndView rescue(){

        ModelAndView mav=new ModelAndView();
        mav.setViewName("rescue");
        return mav;
    }








}
