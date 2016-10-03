package juli.controller.weixin.contoller;

import juli.api.core.APIResponse;
import juli.api.dto.ElevatorDto;
import juli.domain.Elevator;
import juli.domain.User;
import juli.domain.UserElevator;
import juli.repository.ElevatorRepository;
import juli.repository.UserRepository;
import juli.service.ElevatorService;
import juli.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by pangfei on 2016/9/10.
 */
@RestController
@RequestMapping("/weixin/elevator")
public class WeixinElevatorController {
    @Autowired
    public ElevatorRepository elevatorRepository;
    @Autowired
    private ElevatorService elevatorService;
    @Autowired
    private UserRepository userRepository;

    //添加关注电梯
    @RequestMapping("/addFavorite/{elevatorid}")
    public APIResponse elevatorFavorite(String userid, @PathVariable(value ="elevatorid")String elevatorid)
    {
        User user=userRepository.findById(userid);
        Elevator elevator=elevatorRepository.findElevatorById(elevatorid);
        List<User> list=elevator.getUsers();
        if(!list.contains(user))
        {
            list.add(user);
            elevatorRepository.save(elevator);
            return APIResponse.success("关注成功！");
        }
        else
        {

            return APIResponse.success("您已关注该电梯，无需重复关注！");
        }

    }
    //获取某用户的关注的电梯
    @RequestMapping("/getFavorite/{userid}")
    public APIResponse getFavritorElevatorByUserId(@PathVariable(value ="userid")String userid,@RequestParam(defaultValue ="1") int pageNumber ,@RequestParam(defaultValue ="5") int pageSize)
    {
        List<Elevator> list=elevatorRepository.findByUserId(userid,new PageRequest(pageNumber-1,pageSize)).getContent();
        List<ElevatorDto> listDto= ElevatorDto.mapFromElevatorList(list);
        return APIResponse.success(listDto);
    }
    //某电梯的详细信息

    public ModelAndView getDetailElevator()
    {
        ModelAndView mv=new ModelAndView();
        return mv;
    }
    //取消关注电梯
    @RequestMapping("/delFavorite")
    public APIResponse unfav(String userid,String elevatorid)
    {
        User user=userRepository.findById(userid);
        Elevator elevator=elevatorRepository.findElevatorById(elevatorid);
        List<User> list=elevator.getUsers();
        list.remove(user);
        elevatorRepository.save(elevator);
        List<Elevator> elevatorList=elevatorRepository.findByUserId(userid,new PageRequest(0,5)).getContent();
        List<ElevatorDto> listDto= ElevatorDto.mapFromElevatorList(elevatorList);
        return APIResponse.success(listDto);
    }
    //根据电梯编号查询电梯
    @RequestMapping("/elevatorSearch/{number}")
    public APIResponse elevatorSearch(@PathVariable(value = "number")String number)
    {

       return APIResponse.success(elevatorService.findByNumber(number));
    }
}
