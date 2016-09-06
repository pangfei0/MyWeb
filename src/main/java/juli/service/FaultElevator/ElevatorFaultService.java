package juli.service.FaultElevator;

import juli.api.dto.faultDto.FaultTwoDto;
import juli.domain.Elevator;
import juli.domain.fault.ElevatorFault;
import juli.repository.ElevatorFaultRepository;
import juli.repository.ElevatorRepository;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

/**
 * 电梯故障
 */
@Service
public class ElevatorFaultService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ElevatorFaultRepository elevatorFaultRepository;

    @Autowired
    ElevatorRepository elevatorRepository;

    /**
     * 保存故障信息（困人）
     */
    public void saveElevatorFault(FaultTwoDto faultTwoDto) {
        logger.info("保存故障信息,电梯工号:" + faultTwoDto.getNumber());
        ElevatorFault elevatorFault = new ElevatorFault();
        elevatorFault.setCarDirection(faultTwoDto.getCarDirection());
        elevatorFault.setCarPosition(faultTwoDto.getCarPosition());
        elevatorFault.setCarStatus(faultTwoDto.getCarStatus());
        elevatorFault.setDoorStatus(faultTwoDto.getDoorStatus());
        elevatorFault.setDoorZone(faultTwoDto.getDoorZone());
        elevatorFault.setFaultCode(faultTwoDto.getFaultCode());
        elevatorFault.setFaultTime(faultTwoDto.getFaultTime());
        elevatorFault.setNumber(faultTwoDto.getNumber());
        elevatorFault.setOperationDirection(faultTwoDto.getOperationDirection());
        elevatorFault.setOperationStatus(faultTwoDto.getOperationStatus());
        elevatorFault.setPassengerStatus(faultTwoDto.getPassengerStatus());
        elevatorFault.setRegistrationCode(faultTwoDto.getRegistrationCode());
        elevatorFault.setRunNum(faultTwoDto.getRunNum());
        elevatorFault.setServiceMode(faultTwoDto.getServiceMode());
        elevatorFault.setStatusTime(faultTwoDto.getStatusTime());
        elevatorFault.setIsTrapp(faultTwoDto.getIsTrapp());
        elevatorFaultRepository.save(elevatorFault);
        if(null!=faultTwoDto && StringUtils.isNotEmpty(faultTwoDto.getNumber())){
            List<Elevator> elevatorList = elevatorRepository.findElevatorByNumber(faultTwoDto.getNumber().trim());
            if(CollectionUtils.isNotEmpty(elevatorList)){
                Elevator e = elevatorList.get(0);
                e.setFaultStatus(20);
                e.setIsHandled(10);
                e.setFaultTime(new Date());
                elevatorRepository.save(e);
            }
        }


    }

    /**
     * 解除故障电梯（将电梯故障状态改为正常）
     * @param number 电梯工号
     * @param registrationCode 电梯注册代码
     */
    public void faultRelieve(String number,String registrationCode) {
        logger.info("解除电梯故障,电梯工号:"+number);
        if(StringUtils.isNotEmpty(number)){
            List<Elevator> elevatorList = elevatorRepository.findElevatorByNumber(number.trim());
            if(CollectionUtils.isNotEmpty(elevatorList)){
                Elevator e = elevatorList.get(0);
                e.setFaultStatus(10);
                e.setIsHandled(null);
                elevatorRepository.save(e);
            }
        }
    }
}
