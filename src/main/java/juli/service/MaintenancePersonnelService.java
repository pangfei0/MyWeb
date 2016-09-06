package juli.service;

import io.swagger.annotations.ApiParam;
import juli.api.core.APIResponse;
import juli.api.dto.MaintenancePersonnelDto;
import juli.domain.MaintenancePersonnel;
import juli.repository.MaintenancePersonnelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by admin on 2016/7/11.
 */

@Service
public class MaintenancePersonnelService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    MaintenancePersonnelRepository maintenancePersonnelRepository;

    public APIResponse getInfor(String id){
        MaintenancePersonnel entity = maintenancePersonnelRepository.findById(id);
        MaintenancePersonnelDto maintenancePersonnelDto=null;

        if (entity == null) {
            return APIResponse.error("不存在此对象：" + id);
        }else
        {
            try{
                maintenancePersonnelDto=toDTOS(entity);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return APIResponse.success(maintenancePersonnelDto);
    }

    private MaintenancePersonnelDto toDTOS(MaintenancePersonnel maintenancePersonnel)
    {
        MaintenancePersonnelDto maintenancePersonnelDto=new MaintenancePersonnelDto();
        maintenancePersonnelDto.setName(maintenancePersonnel.getName());
        maintenancePersonnelDto.setId(maintenancePersonnel.getId());
        maintenancePersonnelDto.setNumber(maintenancePersonnel.getNumber());
        maintenancePersonnelDto.setTelephone(maintenancePersonnel.getTelephone());
        maintenancePersonnelDto.setRegion(maintenancePersonnel.getRegion());
        maintenancePersonnelDto.setStation(maintenancePersonnel.getStation());
        if(maintenancePersonnel.getMaintainer()!=null){
            maintenancePersonnelDto.setMaintainerId(maintenancePersonnel.getMaintainer().getId());
            maintenancePersonnelDto.setMaintainerName(maintenancePersonnel.getMaintainer().getName());
        }
        if(maintenancePersonnel.getParentId()!=null)
        {
            maintenancePersonnelDto.setParentId(maintenancePersonnel.getParentId());
            maintenancePersonnelDto.setManager(maintenancePersonnel.getManager());
        }
        if(maintenancePersonnel.getLevelList()!=null)
        {
            maintenancePersonnelDto.setLevelList(maintenancePersonnel.getLevelList());
        }

        return maintenancePersonnelDto;
    }
}
