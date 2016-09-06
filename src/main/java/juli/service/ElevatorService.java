package juli.service;

import io.swagger.annotations.ApiParam;
import juli.api.core.APIResponse;
import juli.api.dto.CompanyDto;
import juli.api.dto.ElevatorDto;
import juli.domain.*;
import juli.domain.enums.ElevatorFaultStatus;
import juli.domain.enums.ElevatorMaintennanceStatus;
import juli.domain.enums.ElevatorStatus;
import juli.infrastructure.ServletUtil;
import juli.infrastructure.excel.ExcelUtil;
import juli.infrastructure.exception.JuliException;
import juli.infrastructure.persist.DynamicSpecification;
import juli.repository.*;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class ElevatorService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ElevatorRepository elevatorRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    MaintenanceRepository maintenanceRepository;

    @Autowired
    UserService userService;
    @Autowired
    ElevatorBrandRepository elevatorBrandRepository;
    @Autowired
    MaintenancePersonnelRepository maintenancePersonnelRepository;

    public Elevator findByNumber(String number) {
        return elevatorRepository.findByNumber(number);
    }

    // special user


    public long getElevatorCount(ElevatorStatus status) {
        return elevatorRepository.countByStatus(status.getCode());
    }

    public long getElevatorCount(ElevatorMaintennanceStatus status) {
        return elevatorRepository.countByMaintenanceStatus(status.getCode());
    }

    public long getElevatorCount(ElevatorFaultStatus status) {
        return elevatorRepository.countByFaultStatus(status.getCode());
    }
    public long getElevatorCount(ElevatorStatus status,String companyId) {
        return elevatorRepository.countByStatusUser(status.getCode(), companyId);
    }

    public long getElevatorCount(ElevatorMaintennanceStatus status,String companyId) {
        return elevatorRepository.countByMaintenanceStatusUser(status.getCode(), companyId);
    }

    public long getElevatorCount(ElevatorFaultStatus status,String companyId) {
        return elevatorRepository.countByFaultStatusUser(status.getCode(), companyId);
    }
    public long getAllElevatorCount(String companyId){
    return elevatorRepository.countByAllStatusUser(companyId);
    }
    public long getAllElevatorCount() {
        return elevatorRepository.count();
    }


    public long getPremiseCount(Premise premise) {
        return elevatorRepository.countByPremise(premise);
    }


    /**
     * 获得控制类型统计信息
     * 返回KEY VALUDE的数组
     */
    public List<Map<String, Object>> getControllerTypeGroup() {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (Object[] obj : elevatorRepository.findGroupControllerType()) {
            Map<String, Object> testMap = new HashMap<>();
            testMap.put("controllerType", obj[0]);
            testMap.put("count", obj[1]);
            mapList.add(testMap);
        }
        return mapList;
    }

    public Map<String, Integer> groupControllerSystem(List<Elevator> elevators) {
        Map<String, Integer> groupMap = new TreeMap<>();
        for (Elevator elevator : elevators) {
            if (elevator.getControllerType() != null) {
                if (groupMap.containsKey(elevator.getControllerType())) {
                    Integer count = groupMap.get(elevator.getControllerType());
                    groupMap.put(elevator.getControllerType(), count + 1);
                } else {
                    groupMap.put(elevator.getControllerType(), 1);
                }
            }
        }
        return groupMap;
    }

    public Map<String, Integer> groupStatus(List<Elevator> elevators) {
        Map<String, Integer> groupMap = new TreeMap<>();
        for (Elevator elevator : elevators) {
            String key = ElevatorStatus.get(elevator.getStatus()).getName();
            if (groupMap.containsKey(key)) {
                Integer count = groupMap.get(key);
                groupMap.put(key, count + 1);
            } else {
                groupMap.put(key, 1);
            }
        }

        return groupMap;
    }

    /**
     * 查询出所有 未设置智能硬件注册码的电梯信息
     */
    public List<Object[]> findElevatorInfoForCollector() {
        return null;
        //return elevatorRepository.findElevatorInfoForCollector();
    }

    public APIResponse getDetailElevator(String id){
        ElevatorDto elevatorDto=null;
        Elevator elevator = elevatorRepository.findElevatorById(id);

        if (elevator!=null) {
            try{
                elevatorDto=toDTOS(elevator);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return APIResponse.success(elevatorDto);
    }

    public APIResponse editBaseInfoElevator(String id,ElevatorDto elevatorDto){
        try{
            Elevator existingEntity = elevatorRepository.findElevatorById(id);
            if (existingEntity == null) {
                return APIResponse.error("不存在此对象：" + id);
            }
            existingEntity.setNumber(elevatorDto.getNumber());
            existingEntity.setRegCode(elevatorDto.getRegCode());
            existingEntity.setBrandId(elevatorDto.getBrandId());
            existingEntity.setAddress(elevatorDto.getAddress());
            existingEntity.setPremise(elevatorDto.getPremise());
            existingEntity.setAlias(elevatorDto.getAlias());
            existingEntity.setLng(elevatorDto.getLng());
            existingEntity.setLat(elevatorDto.getLat());
            existingEntity.setRatedWeight(elevatorDto.getRatedWeight());
            existingEntity.setRatedSpeed(elevatorDto.getRatedSpeed());
            existingEntity.setHoistingHeight(elevatorDto.getHoistingHeight());
            existingEntity.setStation(elevatorDto.getStation());
            existingEntity.setControlMode(elevatorDto.getControlMode());
            existingEntity.setIntelHardwareNumber(elevatorDto.getIntelHardwareNumber());
            existingEntity.setControllerType(elevatorDto.getControllerType());
            if(!elevatorDto.getElevatorType().equals("")){
                existingEntity.setElevatorType(elevatorDto.getElevatorType());
            }else {
                existingEntity.setElevatorType(null);
            }

            existingEntity.setProjectName(elevatorDto.getProjectName());
            existingEntity.setProductionTime(elevatorDto.getProductionTime());
            existingEntity.setDeliverTime(elevatorDto.getDeliverTime());
            existingEntity.setEquipmentNumber(elevatorDto.getEquipmentNumber());
            if (!elevatorDto.getBrandId().equals("-1")) {
                existingEntity.setBrandId(elevatorDto.getBrandId());
            } else {
                existingEntity.setBrandId(null);
            }
            elevatorRepository.save(existingEntity);
            return APIResponse.success();
        }catch (Exception e){
            return APIResponse.error(e.getMessage());
        }

    }

    public APIResponse editCompanyInfoElevator(String id,ElevatorDto elevatorDto){
        Elevator existingEntity = elevatorRepository.findElevatorById(id);
        if (existingEntity == null) {
            return APIResponse.error("不存在此对象：" + id);
        }
        setForeignFieldFromDto(existingEntity, elevatorDto);
        elevatorRepository.save(existingEntity);
        return APIResponse.success();
    }
    private void setForeignFieldFromDto(Elevator entity, ElevatorDto dto) {
        if (StringUtils.isNotEmpty(dto.getInstallCompanyId())||StringUtils.isNotEmpty(dto.getInstallCompanyName())) {

            if(StringUtils.isNotEmpty(dto.getInstallCompanyId())){
                entity.setInstallCompanyId(dto.getInstallCompanyId());
            }
            else if(StringUtils.isNotEmpty(dto.getInstallCompanyName())){
                entity.setInstallCompanyId(companyRepository.findIdByNameAndType(10,dto.getInstallCompanyName()));
            }
        }
        else{
            entity.setInstallCompanyId(null);
        }
        if (StringUtils.isNotEmpty(dto.getUserCompanyId())||StringUtils.isNotEmpty(dto.getUserCompanyName())) {

            if(StringUtils.isNotEmpty(dto.getUserCompanyId())) {
                entity.setUserCompanyId(dto.getUserCompanyId());
            }
            else if(StringUtils.isNotEmpty(dto.getUserCompanyName())){
                entity.setUserCompanyId(companyRepository.findIdByNameAndType(30, dto.getUserCompanyName()));
            }
        }
        else{
            entity.setUserCompanyId(null);
        }

        if (StringUtils.isNotEmpty(dto.getManufacturerId())||StringUtils.isNotEmpty(dto.getManufacturerName())) {

            if(StringUtils.isNotEmpty(dto.getManufacturerId())) {
                entity.setManufacturerId(dto.getManufacturerId());
            }
            else if(StringUtils.isNotEmpty(dto.getManufacturerName())){
                entity.setManufacturerId(companyRepository.findIdByNameAndType(40, dto.getManufacturerName()));
            }
        }
        else{
            entity.setManufacturerId(null);
        }

        if (StringUtils.isNotEmpty(dto.getMaintainerId())||StringUtils.isNotEmpty(dto.getMaintainerName())) {

            if(StringUtils.isNotEmpty(dto.getMaintainerId())) {
                entity.setMaintainerId(dto.getMaintainerId());
            }
            else if(StringUtils.isNotEmpty(dto.getMaintainerName())){
                entity.setMaintainerId(companyRepository.findIdByNameAndType(20, dto.getMaintainerName()));
            }
            if(dto.getMaintenanceName()!=null&&!dto.getMaintenanceName().equals("-1")){
                String maintenanceId=maintenanceRepository.findIdByName(dto.getMaintenanceName());
                entity.setMaintenanceId(maintenanceId);
            }else {
                entity.setMaintenanceId(null);
            }
        }
        else{
            entity.setMaintainerId(null);
            entity.setMaintenanceId(null);
        }
        if (StringUtils.isNotEmpty(dto.getOwnerCompanyId())||StringUtils.isNotEmpty(dto.getOwnerCompanyName())) {

            if(StringUtils.isNotEmpty(dto.getOwnerCompanyId())) {
                entity.setOwnerCompanyId(dto.getOwnerCompanyId());
            }
            else if(StringUtils.isNotEmpty(dto.getOwnerCompanyName())){
                entity.setOwnerCompanyId(companyRepository.findIdByNameAndType(50, dto.getOwnerCompanyName()));
            }
        }
        else{
            entity.setOwnerCompanyId(null);
        }
        if (StringUtils.isNotEmpty(dto.getRegulatorId())||StringUtils.isNotEmpty(dto.getRegulatorName())) {

            if(StringUtils.isNotEmpty(dto.getRegulatorId())) {
                entity.setRegulatorId(dto.getRegulatorId());
            }
            else if(StringUtils.isNotEmpty(dto.getRegulatorName())) {
                entity.setRegulatorId(companyRepository.findIdByNameAndType(60, dto.getRegulatorName()));
            }
        }
        else{
            entity.setRegulatorId(null);
        }
        if (StringUtils.isNotEmpty(dto.getPersonalId())||StringUtils.isNotEmpty(dto.getPersonalName())) {

            if(StringUtils.isNotEmpty(dto.getPersonalId())) {
                entity.setPersonalId(dto.getPersonalId());
            }
            else if(StringUtils.isNotEmpty(dto.getPersonalName())) {
                entity.setPersonalId(companyRepository.findIdByNameAndType(70, dto.getPersonalName()));
            }
        }
        else{
            entity.setPersonalId(null);
        }
        if (StringUtils.isNotEmpty(dto.getOthersId())||StringUtils.isNotEmpty(dto.getOthersName())) {

            if(StringUtils.isNotEmpty(dto.getOthersId())) {
                entity.setOthersId(dto.getOthersId());
            }
            else if(StringUtils.isNotEmpty(dto.getOthersName())) {
                entity.setOthersId(companyRepository.findIdByNameAndType(80, dto.getOthersName()));
            }
        }
        else{
            entity.setOthersId(null);
        }
    }

    private ElevatorDto toDTOS(Elevator elevator)throws Exception{
        ElevatorDto dto=new ElevatorDto();
        dto.setId(elevator.getId());
        dto.setAddress(elevator.getAddress());
        dto.setAlias(elevator.getAlias());
        dto.setControllerType(elevator.getControllerType());
        dto.setControlMode(elevator.getControlMode());
        dto.setDeliverTime(elevator.getDeliverTime());
        dto.setEquipmentNumber(elevator.getEquipmentNumber());
        dto.setHoistingHeight(elevator.getHoistingHeight());
        dto.setIntelHardwareNumber(elevator.getIntelHardwareNumber());
        dto.setLat(elevator.getLat());
        dto.setLng(elevator.getLng());
        dto.setNumber(elevator.getNumber());
        dto.setProjectName(elevator.getProjectName());
        dto.setRatedSpeed(elevator.getRatedSpeed());
        dto.setRatedWeight(elevator.getRatedWeight());
        dto.setRegCode(elevator.getRegCode());
        dto.setStation(elevator.getStation());
        dto.setProductionTime(elevator.getProductionTime());
        dto.setInstallCompanyId(elevator.getInstallCompanyId());
        dto.setMaintainerId(elevator.getMaintainerId());
        dto.setMaintenanceId(elevator.getMaintenanceId());
        dto.setManufacturerId(elevator.getManufacturerId());
        dto.setOwnerCompanyId(elevator.getOwnerCompanyId());
        dto.setUserCompanyId(elevator.getUserCompanyId());
        dto.setRegulatorId(elevator.getRegulatorId());
        dto.setPersonalId(elevator.getPersonalId());
        dto.setOthersId(elevator.getOthersId());
        dto.setBrandId(elevator.getBrandId());
        dto.setElevatorType(elevator.getElevatorType());
        if(elevator.getBrandId()!=null){
            dto.setBrandName(elevatorBrandRepository.findName(elevator.getBrandId()));
        }
        if(elevator.getMaintenanceId()!=null)
        {
            dto.setMaintenanceManager(maintenancePersonnelRepository.findManager(elevator.getMaintenanceId()));
            dto.setMaintenanceName(maintenancePersonnelRepository.findName(elevator.getMaintenanceId()));
        }
        if (elevator.getInstallCompanyId() != null) {
            dto.setInstallCompanyName(companyRepository.findName(elevator.getInstallCompanyId()));
        }
        if(elevator.getMaintainerId()!=null)
        {
            dto.setMaintainerName(companyRepository.findName(elevator.getMaintainerId()));
        }
        if(elevator.getUserCompanyId()!=null){
            dto.setUserCompanyName(companyRepository.findName(elevator.getUserCompanyId()));
        }
        if (elevator.getManufacturerId() != null) {
            dto.setManufacturerName(companyRepository.findName(elevator.getManufacturerId()));
        }
        if (elevator.getOwnerCompanyId() != null) {
            dto.setOwnerCompanyName(companyRepository.findName(elevator.getOwnerCompanyId()));
        }
        if (elevator.getRegulatorId() != null) {
            dto.setRegulatorName(companyRepository.findName(elevator.getRegulatorId()));
        }
        if(elevator.getPersonalId()!=null){
            dto.setPersonalName(companyRepository.findName(elevator.getPersonalId()));
        }
        if(elevator.getOthersId()!=null ){
            dto.setOthersName(companyRepository.findName(elevator.getOthersId()));
        }
        return dto;
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

    public APIResponse getElevatorsForSearch (ServletRequest request,int pageNumber,int pageSize){
        try {
            User user = userService.getCurrentUser();
            Map<String, Object> searchParams = ServletUtil.getParametersStartingWith(request, "search_");
            List<String> idList = new ArrayList<>();
            if (user.getCompanyId() != null && !user.getCompanyId().equals("")) {
                switch (user.getCompanyType()) {
                    case "10"://安装单位
                        idList = elevatorRepository.findElevatorIdsByCompanyIdInstall(user.getCompanyId());
                        break;
                    case "20"://维保单位
                        idList = elevatorRepository.findElevatorIdsByCompanyIdMaintainer(user.getCompanyId());
                        break;
                    case "30"://使用单位
                        idList = elevatorRepository.findElevatorIdsByCompanyIdUser(user.getCompanyId());
                        break;
                    case "40"://制造单位
                        idList = elevatorRepository.findElevatorIdsByCompanyIdManufacturer(user.getCompanyId());
                        break;
                    case "50"://物业单位
                        idList = elevatorRepository.findElevatorIdsByCompanyIdOwner(user.getCompanyId());
                        break;
                    case "60"://监管机构
                        idList = elevatorRepository.findElevatorIdsByCompanyIdRegulator(user.getCompanyId());
                        break;
                    case "70"://个人用户
                        idList = elevatorRepository.findElevatorIdsByCompanyIdPersonal(user.getCompanyId());
                        break;
                    case "80"://其他类型
                        idList = elevatorRepository.findElevatorIdsByCompanyIdOthers(user.getCompanyId());
                        break;
                }
                searchParams.put("id_in", getIds(idList));

            } else {
                if(!user.getRoles().get(0).getName().equals("超级管理员"))
                {
                    searchParams.put("id_in","none");
                }
            }
            Sort sort = null;
            String sortName = request.getParameter("sortName");
            String sortOrder = request.getParameter("sortOrder");
            if (sortName == null) {
                sort = new Sort(Sort.Direction.DESC, "createdDate");
            } else {
                if ("number".equals(sortName)) {
                    sortName = "number";
                }
                if ("asc".equals(sortOrder)) {
                    sort = new Sort(Sort.Direction.ASC, sortName);
                } else {
                    sort = new Sort(Sort.Direction.DESC, sortName);
                }
            }
            Map<String, Object> searchParams_new = new HashMap<>();

            searchParams_new.put("number(TEXT)_LIKE", searchParams.get("number(TEXT)_LIKE") != null ? searchParams.get("number(TEXT)_LIKE").toString().trim() : "");
            searchParams_new.put("address(TEXT)_LIKE", searchParams.get("address(TEXT)_LIKE") != null ? searchParams.get("address(TEXT)_LIKE").toString().trim() : "");
            searchParams_new.put("projectName(TEXT)_LIKE", searchParams.get("projectName(TEXT)_LIKE") != null ? searchParams.get("projectName(TEXT)_LIKE").toString().trim() : "");//项目名称
            searchParams_new.put("id_in", searchParams.get("id_in") != null ? searchParams.get("id_in").toString() : "");
            Specification specification = DynamicSpecification.buildSpecification(searchParams_new);
            PageRequest pageRequest=new PageRequest(pageNumber - 1, pageSize,sort);
            Page entities = elevatorRepository.findAll(specification,pageRequest);
            List<Elevator> elevators = entities.getContent();
            List<ElevatorDto> elevatorDtos=new ArrayList<>();
            for(Elevator e :elevators){
                elevatorDtos.add(fromElevatorDto(e));
            }
            PageImpl<ElevatorDto> elevatorPage=new PageImpl<ElevatorDto>(elevatorDtos,pageRequest,entities.getTotalElements());

            return APIResponse.success(elevatorPage);
        } catch (JuliException e) {
            return APIResponse.error(e.getMessage());
        }
    }

    private ElevatorDto fromElevatorDto(Elevator e) {
        ElevatorDto dto = new ElevatorDto();
        dto.setId(e.getId());
        dto.setNumber(e.getNumber());
        dto.setAddress(e.getAddress());
        dto.setAlias(e.getAlias());
        dto.setElevatorType(e.getElevatorType());
        dto.setControllerType(e.getControllerType());
        dto.setStation(e.getStation());
        dto.setProductionTime(e.getProductionTime());
        dto.setRatedWeight(e.getRatedWeight());
        dto.setRatedSpeed(e.getRatedSpeed());
        dto.setProjectName(e.getProjectName());
        dto.setEquipmentNumber(e.getEquipmentNumber());
        dto.setRegCode(e.getRegCode());
        dto.setControlMode(e.getControlMode());
        dto.setHoistingHeight(e.getHoistingHeight());
        dto.setLastCheckDate(e.getLastCheckDate());
        return dto;
    }
    public LinkedHashMap<String,List<?>> getElevators(ServletRequest request)throws Exception{
        try{
            User user = userService.getCurrentUser();
            Map<String, Object> searchParams = ServletUtil.getParametersStartingWith(request, "search_");
            List<String> idList = new ArrayList<>();
            if (user.getCompanyId() != null && !user.getCompanyId().equals("")) {
                switch (user.getCompanyType()) {
                    case "10"://安装单位
                        idList = elevatorRepository.findElevatorIdsByCompanyIdInstall(user.getCompanyId());
                        break;
                    case "20"://维保单位
                        idList = elevatorRepository.findElevatorIdsByCompanyIdMaintainer(user.getCompanyId());
                        break;
                    case "30"://使用单位
                        idList = elevatorRepository.findElevatorIdsByCompanyIdUser(user.getCompanyId());
                        break;
                    case "40"://制造单位
                        idList = elevatorRepository.findElevatorIdsByCompanyIdManufacturer(user.getCompanyId());
                        break;
                    case "50"://物业单位
                        idList = elevatorRepository.findElevatorIdsByCompanyIdOwner(user.getCompanyId());
                        break;
                    case "60"://监管机构
                        idList = elevatorRepository.findElevatorIdsByCompanyIdRegulator(user.getCompanyId());
                        break;
                    case "70"://个人用户
                        idList = elevatorRepository.findElevatorIdsByCompanyIdPersonal(user.getCompanyId());
                        break;
                    case "80"://其他类型
                        idList = elevatorRepository.findElevatorIdsByCompanyIdOthers(user.getCompanyId());
                        break;
                }
                searchParams.put("id_in", getIds(idList));

            } else {
                if(!user.getRoles().get(0).getName().equals("超级管理员"))
                {
                    searchParams.put("id_in","none");
                }
            }
            Map<String, Object> searchParams_new = new HashMap<>();
            searchParams_new.put("number(TEXT)_LIKE", searchParams.get("number(TEXT)_LIKE") != null ? searchParams.get("number(TEXT)_LIKE").toString().trim() : "");
            searchParams_new.put("address(TEXT)_LIKE", searchParams.get("address(TEXT)_LIKE") != null ? searchParams.get("address(TEXT)_LIKE").toString().trim() : "");
            searchParams_new.put("projectName(TEXT)_LIKE", searchParams.get("projectName(TEXT)_LIKE") != null ? searchParams.get("projectName(TEXT)_LIKE").toString().trim() : "");//项目名称
            searchParams_new.put("id_in", searchParams.get("id_in") != null ? searchParams.get("id_in").toString() : "");
            Specification specification = DynamicSpecification.buildSpecification(searchParams_new);
            List<Elevator> elevators = elevatorRepository.findAll(specification);
            elevators= IteratorUtils.toList(elevators.iterator());
            List<ElevatorDto> elevatorDtos=new ArrayList<>();
            for(Elevator e :elevators){
                elevatorDtos.add(fromElevatorDto(e));
            }
            LinkedHashMap<String, List<?>> map = new LinkedHashMap<>();
            map.put("电梯数据", elevatorDtos);
            return map;
        }catch (JuliException e)
        {
            return null;
        }
    }

    public void export(HttpServletResponse response,ServletRequest request) throws Exception
    {
        LinkedHashMap<String,List<?>> map=getElevators(request);
        ExcelUtil.ExcelExportData setInfo = new ExcelUtil.ExcelExportData();
        List<String[]> fieldNames = new ArrayList<>();
        fieldNames.add(new String[]{"number", "address", "projectName", "equipmentNumber","elevatorType","controllerType", "station", "productionTime","ratedWeight", "ratedSpeed", "lastCheckDate"});
        List<String[]> columNames = new ArrayList<>();
        columNames.add(new String[]{"电梯合同号", "详细地址", "项目名称", "设备编号","电梯类型","控制器类型", "层/站/门", "生产日期",  "额定载重", "额定速度", "上次年检时间"});
        setInfo.setDataMap(map);
        setInfo.setFieldNames(fieldNames);
        setInfo.setTitles(new String[]{"电梯数据"});
        setInfo.setColumnNames(columNames);
        ExcelUtil.export2Browser(response, setInfo, "电梯数据");
    }

    public APIResponse getElevatorNumber(String number){
        String signNumber = "%" + number + "%";
        List<String> list = elevatorRepository.findNumbers(signNumber);
        return APIResponse.success(list);
    }

    public APIResponse getElevatorAddress(String address){
        String signNumber = "%" + address + "%";
        List<String> list = elevatorRepository.findAddress(signNumber);
        return APIResponse.success(list);
    }

    public APIResponse getElevatorProjectName(String projectName){
        String signNumber = "%" + projectName + "%";
        List<String> list = elevatorRepository.findProjectNames(signNumber);
        return APIResponse.success(list);
    }

    public APIResponse searchCompanyName(String name,int type){
        String name1="%"+name+"%";
        List<Company> companies=companyRepository.findByNameAndType(type,name1);
        List<CompanyDto> list = new ArrayList<>();
        for (Company c : companies) {
            CompanyDto companyDto = new CompanyDto();
            companyDto.setId(c.getId());
            companyDto.setName(c.getName());
            list.add(companyDto);
        }
        return APIResponse.success(list);
    }

}