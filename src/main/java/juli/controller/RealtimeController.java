package juli.controller;

import juli.api.dto.ElevatorTypeDto;
import juli.domain.Elevator;
import juli.domain.ElevatorBrand;
import juli.domain.User;
import juli.domain.enums.ElevatorFaultStatus;
import juli.domain.enums.ElevatorMaintennanceStatus;
import juli.domain.enums.ElevatorStatus;
import juli.infrastructure.exception.JuliException;
import juli.repository.ElevatorBrandRepository;
import juli.repository.ElevatorRepository;
import juli.service.DataServer.DataServerSyncService;
import juli.service.ElevatorService;
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
public class RealtimeController {

    @Autowired
    UserService userService;

    @Autowired
    DataServerSyncService syncService;

    @Autowired
    ElevatorService elevatorService;

    @Autowired
    ElevatorBrandRepository brandRepository;

    @Autowired
    ElevatorRepository elevatorRepository;

    @RequestMapping("/realTime")
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
        /*User user=userService.getCurrentUser();
        if(user.getCompanyId()!=null){
            mav.getModel().put("elevatorCountAll", elevatorService.getAllElevatorCount(user.getCompanyId()));
            mav.getModel().put("elevatorCountOnline", elevatorService.getElevatorCount(ElevatorStatus.ONLINE, user.getCompanyId()));
            mav.getModel().put("elevatorCountMalfunction", elevatorService.getElevatorCount(ElevatorFaultStatus.MALFUNCTION, user.getCompanyId()));
            mav.getModel().put("elevatorCountRecondition", elevatorService.getElevatorCount(ElevatorMaintennanceStatus.RECONDITION, user.getCompanyId()));
            mav.getModel().put("elevatorCountOffline", elevatorService.getElevatorCount(ElevatorStatus.OFFLINE, user.getCompanyId()));
        }
        else{
            mav.getModel().put("elevatorCountAll", elevatorService.getAllElevatorCount());
            mav.getModel().put("elevatorCountOnline", elevatorService.getElevatorCount(ElevatorStatus.ONLINE));
            mav.getModel().put("elevatorCountMalfunction", elevatorService.getElevatorCount(ElevatorFaultStatus.MALFUNCTION));
            mav.getModel().put("elevatorCountRecondition", elevatorService.getElevatorCount(ElevatorMaintennanceStatus.RECONDITION));
            mav.getModel().put("elevatorCountOffline", elevatorService.getElevatorCount(ElevatorStatus.OFFLINE));
        }*/

        List<ElevatorTypeDto> elevatorTypeDtos = new ArrayList<>();
        ElevatorTypeDto elevatorTypeDto = new ElevatorTypeDto();
        elevatorTypeDto.setName("未连接智能系统");
        elevatorTypeDto.setId("0");
        ElevatorTypeDto elevatorTypeDto1 = new ElevatorTypeDto();
        elevatorTypeDto1.setName("已连接智能系统");
        elevatorTypeDto1.setId("1");
        ElevatorTypeDto elevatorTypeDto2 = new ElevatorTypeDto();
        elevatorTypeDto2.setName("多品牌电梯");
        elevatorTypeDto2.setId("2");
        elevatorTypeDtos.add(elevatorTypeDto);
        elevatorTypeDtos.add(elevatorTypeDto1);
        elevatorTypeDtos.add(elevatorTypeDto2);
        mav.addObject("eTypes", elevatorTypeDtos);
        mav.addObject("brands", brands);
        mav.addObject("isFav","0");
        mav.addObject("sid",sid);
        mav.addObject("uId",userService.getCurrentUser().getId());
        mav.setViewName("realTime");
        return mav;
    }

    @RequestMapping("/realtime/detail/{id}")
    public ModelAndView showDetailMonitor(@PathVariable("id")String id) throws IOException, JuliException {
        String sid="";
        if(syncService.isLogined()){
            sid=syncService.getSid();
        }else{
            sid=syncService.login();
        }
        Elevator elevator = elevatorRepository.findOne(id);
        ModelAndView mav=new ModelAndView();
        mav.addObject("id",id);
        mav.addObject("sid",sid);
        if(null!=elevator){
            mav.addObject("elevatorNumbers",elevator.getNumber());
        }
        mav.setViewName("realTimeSpec");
        return mav;
    }
}
