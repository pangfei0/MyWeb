package juli.api;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import juli.api.core.APIResponse;
import juli.domain.ParticularElevator;
import juli.repository.ParticularElevatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "实时状态读取数据库的电梯", description = " ")
@RestController
@RequestMapping("/api/particularElevator")
public class ParticularElevatorAPIController{

    @Autowired
    ParticularElevatorRepository particularElevatorRepository;


    @ApiOperation("硬件读取的电梯状态")
    @RequestMapping(value = "/{type}", method = RequestMethod.GET)
    public APIResponse getData(@ApiParam("电梯控制器类型") @PathVariable("type") String type) {
        String typeId = new String("");
        if (type.equals("TONGLI1")){
            typeId = "100000000001";
        }else if (type.equals("TONGYONG")){
            typeId = "100000000003";
        }else if (type.equals("DIHAO")){
            typeId = "100000000004";
        }else if (type.equals("TONGLI2")){
            typeId = "100000000005";
        }else if (type.equals("TONGLI3")){
            typeId = "100000000006";
        }
        ParticularElevator particularElevator = particularElevatorRepository.getLastOneByType(typeId);
        return APIResponse.success(particularElevator);
    }

}
