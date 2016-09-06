package juli.service;

import juli.api.core.APIResponse;
import juli.api.dto.CollectorDto;
import juli.domain.Collector;
import juli.domain.Elevator;
import juli.domain.Role;
import juli.domain.User;
import juli.infrastructure.ServletUtil;
import juli.infrastructure.exception.JuliException;
import juli.infrastructure.persist.DynamicSpecification;
import juli.repository.CollectorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CollectorService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CollectorRepository collectorRepository;

    @Autowired
    private ElevatorService elevatorService;

    @Autowired
    private UserService userService;

    public APIResponse newsearch(ServletRequest request, Integer pageNumber, Integer pageSize) {


        try {
            Map<String, Object> searchParams = ServletUtil.getParametersStartingWith(request, "search_");
            User user = userService.getCurrentUser();
            List<String> idList = new ArrayList<>();
            if(user.getCompanyId() != null && !user.getCompanyId().equals("")){
                String cId=user.getCompanyId();
                switch (user.getCompanyType()){
                    case "10": idList=collectorRepository.findByCompanyIdInstall(cId);break;
                    case "20": idList=collectorRepository.findByCompanyIdMaintainer(cId);break;
                    case "30": idList=collectorRepository.findByCompanyIdUser(cId);break;
                    case "40": idList=collectorRepository.findByCompanyIdManufacturer(cId);break;
                    case "50": idList=collectorRepository.findByCompanyIdOwner(cId);break;
                    case "60": idList=collectorRepository.findByCompanyIdRegualtor(cId);break;
                    case "70": idList=collectorRepository.findByCompanyIdPersonal(cId);break;
                    case "80": idList=collectorRepository.findByCompanyIdOthers(cId);
                }
                searchParams.put("id_in", getIds(idList));
            }
            else {
                Role role=user.getRoles().get(0);
                if(!role.getName().equals("超级管理员"))
                {
                    searchParams.put("id_in", "none");
                }
            }

            if (searchParams.get("intelHardwareNumber(TEXT)_LIKE") != null) {
                searchParams.put(("intelHardwareNumber(TEXT)_LIKE"), searchParams.get("intelHardwareNumber(TEXT)_LIKE").toString().trim());
            } else {
                searchParams.put("intelHardwareNumber(TEXT)_LIKE", "");
            }
            if (searchParams.get("alias(TEXT)_LIKE") != null) {
                searchParams.put(("alias(TEXT)_LIKE"), searchParams.get("alias(TEXT)_LIKE").toString().trim());
            } else {
                searchParams.put("alias(TEXT)_LIKE", "");
            }
            if (searchParams.get("number(TEXT)_LIKE") != null) {
                searchParams.put(("number(TEXT)_LIKE"), searchParams.get("number(TEXT)_LIKE").toString().trim());
            } else {
                searchParams.put("number(TEXT)_LIKE", "");
            }
            Specification specification = DynamicSpecification.buildSpecification(searchParams);
            PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize);
            Page<Collector> collectors = collectorRepository.findAll(specification, pageRequest);
            List<CollectorDto> list = toDto(collectors.getContent());
            PageImpl<CollectorDto> billsPage = new PageImpl<CollectorDto>(list, pageRequest, collectors.getTotalElements());
            return APIResponse.success(billsPage);
        } catch (JuliException e) {
            return APIResponse.error(e.getMessage());
        }
    }
    private String getIds(List<String> list) {
        StringBuffer sb = new StringBuffer();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (i != list.size() - 1) {
                    sb.append(list.get(i) + ",");
                } else {
                    sb.append(list.get(i));
                }
            }
        } else {
            sb.append("none");
        }
        return sb.toString();
    }


    public Collector findByIntelHardwareNumber(String intelHardwareNumber) {
        return collectorRepository.findByIntelHardwareNumber(intelHardwareNumber);
    }

    /**
     * 添加指定电梯编号的采集器
     *
     * @param collector      采集器
     * @param elevatorNumber 电梯工号
     */
    public void addCollector(Collector collector, String elevatorNumber) {
        Elevator elevator = elevatorService.findByNumber(elevatorNumber);
        if (elevator != null) {
            collector.setId("collector" + UUID.randomUUID().toString());
        }
        collectorRepository.save(collector);
    }

    public void save(Collector collector) {
        collectorRepository.save(collector);
    }


    public List<CollectorDto> toDto(List<Collector> collectorList) {
        List<CollectorDto> dtos = new ArrayList<>();
        for (Collector collector : collectorList) {
            CollectorDto dto = new CollectorDto();
            dto.setId(collector.getId());
            dto.setAlias(collector.getAlias());
            dto.setElevatorId(collector.getElevatorId());
            dto.setIntelHardwareNumber(collector.getIntelHardwareNumber());
            dto.setNumber(collector.getNumber());
            dtos.add(dto);
        }
        return dtos;
    }
}
