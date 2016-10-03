package juli.controller.weixin.contoller;

import juli.domain.Elevator;
import juli.domain.User;
import juli.infrastructure.exception.JuliException;
import juli.repository.ElevatorRepository;
import juli.service.ElevatorService;
import juli.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pangfei on 2016/9/7.
 */
@Controller
@RequestMapping("weixin/achieve")
public class WeixinAchiveController
{
    @Autowired
    private ElevatorRepository elevatorRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ElevatorService elevatorService;
    //遊客訪問

    //获取离乘客最近的电梯列表
    @RequestMapping("/elevators")
    public ModelAndView getElevators( @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                      @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
                                      @RequestParam(value = "lat", defaultValue = "31") double lat,
                                      @RequestParam(value = "lng", defaultValue = "120") double lng
                                      ) throws JuliException {




        PageRequest page=new PageRequest(pageNumber-1,pageSize);
        Page<Elevator> entries= elevatorRepository.findBylatlng(lat, lng, page);
        List<Elevator> list=entries.getContent();
//        userService.login("admin","000000");
        User user=userService.getCurrentUser();
        List<Elevator> favlist=elevatorRepository.findByUserId(user.getId(),page).getContent();
        if(user==null)
        {
            favlist=new ArrayList<>();
            if(list.size()>0)
             favlist.add(list.get(0));
        }
        else
        {
            //favlist=userElevatorRepository.findByUserId(user.getId());
            favlist=elevatorRepository.findByUserId(user.getId(),page).getContent();
        }
        ModelAndView mv=new ModelAndView();
        mv.addObject("elevators",list);
        mv.addObject("favlist",favlist);
        mv.addObject("userid",user.getId());
        mv.setViewName("weixin/visitor/" + "visitorSearch");
        System.out.println("sdsd");
        return mv;
    }

    //獲取電梯的詳細信息
    @RequestMapping("/elevatorDetail/{id}")
    public ModelAndView achieveDetail(@PathVariable(value = "id") String id)
    {
        ModelAndView mv=new ModelAndView();
        mv.addObject("detail", elevatorService.getDetailElevator(id).getData());
        mv.addObject("elevatorid",id);
        mv.setViewName("weixin/visitor/elevatorDetail");
        return mv;
    }
    //顯示電梯列表
    @RequestMapping("/elevatorManage")
    public ModelAndView achieveliftManage() throws JuliException {
        ModelAndView mv=new ModelAndView();
//        User user=userService.getCurrentUser();
        mv.addObject("userid","11");
        mv.setViewName("weixin/liftManage");
        return mv;
    }
    @RequestMapping("/maintancePlan/{id}")
    public ModelAndView achieveMaintancePlan(@PathVariable(value = "id") String id) throws JuliException {
        ModelAndView mv=new ModelAndView();
//        User user=userService.getCurrentUser();
        mv.addObject("userid", "1111");
        mv.addObject("elevatorid",id);
        mv.setViewName("weixin/visitor/mplan");
        return mv;
    }
}
