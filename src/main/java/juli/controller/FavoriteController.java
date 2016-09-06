package juli.controller;

import juli.domain.ElevatorBrand;
import juli.infrastructure.exception.JuliException;
import juli.repository.ElevatorBrandRepository;
import juli.service.DataServer.DataServerSyncService;
import juli.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FavoriteController {

    @Autowired
    UserService userService;
    @Autowired
    DataServerSyncService syncService;
    @Autowired
    ElevatorBrandRepository brandRepository;

    @RequestMapping("/favorite")
    public ModelAndView realTimeMonitor() throws JuliException, IOException {
        String sid="";
        if(syncService.isLogined()){
            sid=syncService.getSid();
        }else{
            sid=syncService.login();
        }
        ModelAndView mav=new ModelAndView();
        List<ElevatorBrand> brands = new ArrayList<>();
        for (ElevatorBrand brand : brandRepository.findAll()) {
            brands.add(brand);
        }
        mav.addObject("brands", brands);
        mav.addObject("isFav","1");
        mav.addObject("sid",sid);
        mav.addObject("uId",userService.getCurrentUser().getId());
        mav.setViewName("realtime");
        return mav;
    }

    @RequestMapping("/favorite/detail/{id}")
    public ModelAndView showDetailMonitor(@PathVariable("id")String id) throws IOException, JuliException {
        String sid="";
        if(syncService.isLogined()){
            sid=syncService.getSid();
        }else{
            sid=syncService.login();
        }
        ModelAndView mav=new ModelAndView();
        mav.addObject("id",id);
        mav.addObject("sid",sid);
        mav.setViewName("realTimeDetail");
        return mav;
    }
}
