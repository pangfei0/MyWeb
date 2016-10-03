package juli.controller.weixin.contoller;

import juli.api.core.APIResponse;
import juli.domain.MaintenancePlan;
import juli.repository.MaintenancePersonnelRepository;
import juli.repository.MaintenancePlanRepository;
import juli.service.MaintenancePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pangfei on 2016/9/29.
 */
@RestController
@RequestMapping("/weixin/mplan")
public class WexinMpalanComtroller {
    @Autowired
    private MaintenancePlanService  maintenancePlanService;

    @Autowired
    private MaintenancePlanRepository maintenancePlanRepository;


    @RequestMapping("/getAllPlan/{id}")
    public APIResponse getPlanByElevatorId(@PathVariable("id") String id)
    {
        List<MaintenancePlan> banyue= maintenancePlanRepository.findByElevatorIdAndPlanType(id,10);
        List<MaintenancePlan> jidu= maintenancePlanRepository.findByElevatorIdAndPlanType(id, 20);
        List<MaintenancePlan> bannian= maintenancePlanRepository.findByElevatorIdAndPlanType(id,30);
        List<MaintenancePlan> niandu= maintenancePlanRepository.findByElevatorIdAndPlanType(id,40);
        Map<String,List<MaintenancePlan>> map=new HashMap<>();
        map.put("banyue",banyue);
        map.put("jidu",jidu);
        map.put("bannian",bannian);
        map.put("niandu", niandu);
        return APIResponse.success(map);
    }
}
