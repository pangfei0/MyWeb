package juli.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import juli.api.core.APIController;
import juli.api.core.APIResponse;
import juli.api.dto.CompanyDto;
import juli.api.dto.ElevatorDto;
import juli.domain.Company;
import juli.domain.Elevator;
import juli.domain.MaintenancePersonnel;
import juli.domain.User;
import juli.domain.enums.CompanyType;
import juli.infrastructure.ServletUtil;
import juli.infrastructure.excel.ExcelUtil;
import juli.infrastructure.exception.JuliException;
import juli.infrastructure.persist.DynamicSpecification;
import juli.repository.CompanyRepository;
import juli.repository.ElevatorRepository;
import juli.repository.MaintenancePersonnelRepository;
import juli.service.CompanyService;
import juli.service.UserService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.IteratorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Api(value = "公司API", description = " ")
@RestController
@RequestMapping("/api/companyMaintain")
public class CompanyMaintainAPIController extends APIController<Company, CompanyRepository> {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    MaintenancePersonnelRepository maintenancePersonnelRepository;

    @Autowired
    CompanyService companyService;

    @Autowired
    UserService userService;

    @Autowired
    ElevatorRepository elevatorRepository;

    @ApiOperation("新增公司")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public APIResponse create(Company company) {
        String name = company.getName();
        String companyId = companyRepository.findCompanyIdByNameAndType(company.getType(), name);
        if (companyId != null && companyId != "") {
            return APIResponse.error("该公司已存在该类型的数据：" + name);
        }
        companyRepository.save(company);
        return APIResponse.success();
    }


    @ApiOperation("搜索")
    @RequestMapping(value = "/new/search/page", method = RequestMethod.POST)
    public APIResponse Search(ServletRequest request,
                              @ApiParam(value = "当前页，从1开始", defaultValue = "1")
                              @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                              @ApiParam(value = "每页条数", defaultValue = "15")
                              @RequestParam(value = "pageSize", defaultValue = "15") int pageSize) {
        try {


            List<String> idList = new ArrayList<>();
            Map<String, Object> searchParams = ServletUtil.getParametersStartingWith(request, "search_");
            Map<String, Object> searchParams_new = new HashMap<>();
            User user = userService.getCurrentUser();
            if (user.getCompanyId() != null && !user.getCompanyId().equals("")) {
                idList.add(user.getCompanyId());
                searchParams.put("id_in", getIds(idList));
            } else {
                if(user.getRoles()!=null)
                {
                    if (!user.getRoles().get(0).getName().equals("超级管理员")){
                        searchParams.put("id_in", "none");
                    }
                }
            }
            if (searchParams.get("name(TEXT)_LIKE") != null) {
                searchParams_new.put(("name(TEXT)_LIKE"), searchParams.get("name(TEXT)_LIKE").toString().trim());
            } else {
                searchParams_new.put(("name(TEXT)_LIKE"), "");
            }
            if (searchParams.get("address(TEXT)_LIKE") != null) {
                searchParams_new.put(("address(TEXT)_LIKE"), searchParams.get("address(TEXT)_LIKE").toString().trim());
            } else {
                searchParams_new.put(("address(TEXT)_LIKE"), "");
            }
            if(searchParams.get("companyType(TEXT)_LIKE")!=null&&!searchParams.get("companyType(TEXT)_LIKE").equals("-1")){
                searchParams_new.put(("type_eq"), searchParams.get("companyType(TEXT)_LIKE").toString());
            }else
            {
                searchParams_new.put(("type_eq"), "");
            }
            searchParams_new.put("id_in", searchParams.get("id_in") != null ? searchParams.get("id_in").toString() : "");
            Specification specification = DynamicSpecification.buildSpecification(searchParams_new);
            Page entities = companyRepository.findAll(specification, new PageRequest(pageNumber - 1, pageSize));
            return APIResponse.success(entities);
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


    @ApiOperation("查询可用的项目名称")
    @RequestMapping(value = "/searchAllBuildingName/{type}", method = RequestMethod.POST)
    public APIResponse searchAllBuildingName(@PathVariable("type") String companyType) {
        List<String> buildingNames = new ArrayList<>();
        switch (companyType) {
            case "10":
                buildingNames = companyRepository.getInstallCompanyUseProjectNames();
                break;
            case "20":
                buildingNames = companyRepository.getMaintainerCompanyUseProjectNames();
                break;
            case "30":
                buildingNames = companyRepository.getUserCompanyUseProjectNames();
                break;
            case "40":
                buildingNames = companyRepository.getManufacturerCompanyUseProjectNames();
                break;
            case "50":
                buildingNames = companyRepository.getOwnerCompanyUseProjectNames();
                break;
            case "60":
                buildingNames = companyRepository.getRegulatorCompanyUseProjectNames();
                break;
            case "70":
                buildingNames = companyRepository.getPersonalCompanyUseProjectNames();
                break;
            case "80":
                buildingNames = companyRepository.getOthersCompanyUseProjectNames();
        }
        if(CollectionUtils.isNotEmpty(buildingNames))
        {
            return APIResponse.success(buildingNames);
        }
        else{
            return APIResponse.error("没有匹配的项目名称");
        }

    }


    @ApiOperation("查询可用的项目名称")
    @RequestMapping(value = "/searchProjectName/{type}", method = RequestMethod.POST)
    public APIResponse searchProjectName(@PathVariable("type") String companyType,@ApiParam("projectName") String projectName){
        return companyService.searchProjectName(companyType,projectName);
    }


    @ApiOperation("搜索电梯（带分页）")
    @RequestMapping(value = "/new/searchElevator/page", method = RequestMethod.POST)
    public APIResponse searchNewPage(ServletRequest request,
                                     @ApiParam(value = "当前页，从1开始", defaultValue = "1")
                                     @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                     @ApiParam(value = "每页条数", defaultValue = "15")
                                     @RequestParam(value = "pageSize", defaultValue = "15") int pageSize

    ) {
        try {

            Map<String, Object> searchParams = ServletUtil.getParametersStartingWith(request, "search_");
            User user = userService.getCurrentUser();
            String companyType="";

           if(searchParams.size()!=0){
               if(searchParams.containsKey("projectName(TEXT)_LIKE"))
               {
                   searchParams.put("projectName_eq", searchParams.get("projectName(TEXT)_LIKE") != null ? searchParams.get("projectName(TEXT)_LIKE").toString() : "");//项目名称
                   searchParams.remove("projectName(TEXT)_LIKE");
               }
              if(searchParams.containsKey("companyType(TEXT)_LIKE"))
              {
                  companyType=searchParams.get("companyType(TEXT)_LIKE").toString();

                  searchParams.remove("companyType(TEXT)_LIKE");
              }
               if(searchParams.containsKey("number(TEXT)_LIKE"))
               {
                   searchParams.put("number(TEXT)_LIKE", searchParams.get("number(TEXT)_LIKE") != null ? searchParams.get("number(TEXT)_LIKE").toString().trim() : "");//电梯工号
               }

           }
            String searchType="";
            List<String> idList = new ArrayList<>();
            if (user.getRoles().get(0).getName().equals("超级管理员")||(user.getCompanyId() != null && !user.getCompanyId().equals(""))) {
                if(user.getCompanyType()!=null)
                {
                    searchType=user.getCompanyType();
                }
                else
                {
                    searchType=companyType;
                }
                switch (searchType) {
                    case "10":
                        idList = companyRepository.getElevatorIdsByTypeInstall();
                        break;
                    case "20":
                        idList = companyRepository.getElevatorIdsByTypeMaintainer();
                        break;
                    case "30":
                        idList = companyRepository.getElevatorIdsByTypeUser();
                        break;
                    case "40":
                        idList = companyRepository.getElevatorIdsByTypeManufacturer();
                        break;
                    case "50":
                        idList = companyRepository.getElevatorIdsByTypeOwner();
                        break;
                    case "60":
                        idList = companyRepository.getElevatorIdsByTypeRegulator();
                        break;
                    case "70":
                        idList = companyRepository.getElevatorIdsByTypePersonal();
                        break;
                    case "80":
                        idList = companyRepository.getElevatorIdsByTypeOthers();
                }
                if(idList!=null){
                    searchParams.put("id_in", getIds(idList));
                }
            }
            else
            {
                searchParams.put("id_in","none");
            }


            Specification specification = DynamicSpecification.buildSpecification(searchParams);
            Page entities = elevatorRepository.findAll(specification, new PageRequest(pageNumber - 1, pageSize));
            PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize);
            List<Elevator> elevatorList = entities.getContent();
            List<ElevatorDto> elevatorDtos=new ArrayList<>();
            for(Elevator e :elevatorList){
                elevatorDtos.add(fromElevatorDto(e));
            }
            PageImpl<ElevatorDto> elevatorPage=new PageImpl<ElevatorDto>(elevatorDtos,pageRequest,entities.getTotalElements());
            return APIResponse.success(elevatorPage);

        } catch (Exception e) {
            return APIResponse.error(e.getMessage());
        }
    }
    private ElevatorDto fromElevatorDto(Elevator e){
        ElevatorDto dto = new ElevatorDto();
        dto.setId(e.getId());
        dto.setAddress(e.getAddress());
        dto.setProjectName(e.getProjectName());
        dto.setAlias(e.getAlias());
        dto.setElevatorType(e.getElevatorType());
        dto.setNumber(e.getNumber());
        return dto;
    }


    @ApiOperation("关联公司与电梯")
    @RequestMapping(value = "/addElevator/{id}", method = RequestMethod.POST)
    public APIResponse addElevator(@ApiParam("公司id") @PathVariable("id") String id, @ApiParam("电梯id") String elevatorStr, @ApiParam("公司type") String companyType) {
        if (elevatorStr != null) {
            String[] elevatorIds = elevatorStr.split(",");
            switch (companyType) {
                case "10":
                    for (String elevatorId : elevatorIds) {
                        companyRepository.updateElevatorByInstallTypeForAddCompany(id, elevatorId);
                    }
                    break;
                case "20":
                    for (String elevatorId : elevatorIds) {
                        companyRepository.updateElevatorByMaintainerTypeForAddCompany(id, elevatorId);
                    }
                    break;
                case "30":
                    for (String elevatorId : elevatorIds) {
                        companyRepository.updateElevatorByUserTypeForAddCompany(id, elevatorId);
                    }
                    break;
                case "40":
                    for (String elevatorId : elevatorIds) {
                        companyRepository.updateElevatorByManufacturerTypeForAddCompany(id, elevatorId);
                    }
                    break;
                case "50":
                    for (String elevatorId : elevatorIds) {
                        companyRepository.updateElevatorByRegulatorTypeForAddCompany(id, elevatorId);
                    }
                    break;
                case "60":
                    for (String elevatorId : elevatorIds) {
                        companyRepository.updateElevatorByPersonalTypeForAddCompany(id, elevatorId);
                    }
                    break;
                case "70":
                    for (String elevatorId : elevatorIds) {
                        companyRepository.updateElevatorByOthersypeForAddCompany(id, elevatorId);
                    }
                    break;
                case "80":
                    for (String elevatorId : elevatorIds) {
                        companyRepository.updateElevatorByOwnersypeForAddCompany(id, elevatorId);
                    }
                    break;
            }
        }
        return APIResponse.success();

    }

    @ApiOperation("获取公司的详细信息")
    @RequestMapping(value = "/getDetail/{id}", method = RequestMethod.GET)
    public APIResponse getDetail(@ApiParam("公司id") @PathVariable("id") String id) {
        Map<String, Object> paraMap = new HashMap<>();
        Company company = companyRepository.findById(id);
        if (company == null) {
            return APIResponse.error("不存在此对象：" + id);
        } else {
            //基本信息
            //CompanyDto dto = new CompanyDto().mapFrom(company);
            CompanyDto dto = new CompanyDto();
            dto.setId(company.getId());
            dto.setName(company.getName());
            if(company.getContact()!=null){
                dto.setContact(company.getContact());
            }
            if(company.getPhone()!=null)
            {
                dto.setPhone(company.getPhone());
            }
            if(company.getAddress()!=null)
            {
                dto.setAddress(company.getAddress());
            }
            if(company.getMobile()!=null)
            {
                dto.setMobile(company.getMobile());
            }
            CompanyType companyType=CompanyType.getCompanyType(company.getType());
            dto.setTypeName(companyType.getName());
            dto.setType(companyType.getCode());
            paraMap.put("baseInfo", dto);
            //关联电梯清单
            paraMap.put("elevatorInfo", getElevators(company));
            return APIResponse.success(paraMap);
        }

    }

    //关联的电梯
    public List<ElevatorDto> getElevators(Company company) {
        List<Elevator> elevatorList = new ArrayList<>();
        List<ElevatorDto> elevatorDtos = new ArrayList<>();
        int type = company.getType();
        switch (type) {
            case 10:
                elevatorList = companyRepository.getRelatedElevatorForInstallCompany(company.getId());
                break;
            case 20:
                elevatorList = companyRepository.getRelatedElevatorForMaintainerCompany(company.getId());
                break;
            case 30:
                elevatorList = companyRepository.getRelatedElevatorForUserCompany(company.getId());
                break;
            case 40:
                elevatorList = companyRepository.getRelatedElevatorForManufacturer(company.getId());
                break;
            case 50:
                elevatorList = companyRepository.getRelatedElevatorForOwnerCompany(company.getId());
                break;
            case 60:
                elevatorList = companyRepository.getRelatedElevatorForRegulator(company.getId());
                break;
            case 70:
                elevatorList = companyRepository.getRelatedElevatorForPersonal(company.getId());
                break;
            case 80:
                elevatorList = companyRepository.getRelatedElevatorForOthers(company.getId());
        }

        if (elevatorList != null) {
            for (Elevator el : elevatorList) {
                ElevatorDto elevatorDto = new ElevatorDto();
                elevatorDto.setId(el.getId());
                elevatorDto.setNumber(el.getNumber());
                elevatorDto.setElevatorType(el.getElevatorType());
                elevatorDto.setProjectName(el.getProjectName());
                elevatorDto.setAddress(el.getAddress());
               elevatorDto.setAlias(el.getAlias());
                if(el.getMaintenanceId()!=null){
                    MaintenancePersonnel maintenancePersonnel=maintenancePersonnelRepository.findById(el.getMaintenanceId());
                    elevatorDto.setMaintenanceName(maintenancePersonnel.getName());
                    elevatorDto.setMaintenanceManager(maintenancePersonnel.getManager());
                }
                elevatorDtos.add(elevatorDto);
            }
        } else {
            elevatorDtos = null;
        }
        return elevatorDtos;
    }

    @ApiOperation("更新公司基础数据")
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public APIResponse update(@ApiParam("公司ID") @PathVariable("id") String id,
                              @ApiParam("需要更新的对象") CompanyDto CompanyDto) throws InvocationTargetException, IllegalAccessException {
        Company existingEntity = companyRepository.findOne(id);
        if (existingEntity == null) {
            return APIResponse.error("不存在此对象: " + id);
        }
        BeanUtils.copyProperties(existingEntity, CompanyDto);
        companyRepository.save(existingEntity);
        return APIResponse.success();
    }

    @ApiOperation("删除公司")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public APIResponse delete(@ApiParam("对象ID") @PathVariable("id") String id) {
        Company company = companyRepository.findOne(id);

        if (company == null) {
            return APIResponse.error("不存在此对象：" + id);
        }
        int companyType = company.getType();
        try {
            switch (companyType) {
                case 10:
                    companyRepository.updateElevatorByInstallTypeForDeleteCompany(company.getId());break;
                case 20:
                    companyRepository.updateElevatorByMaintainerTypeForDeleteCompany(company.getId());break;
                case 30:
                    companyRepository.updateElevatorByUserCompanyTypeForDeleteCompany(company.getId());break;
                case 40:
                    companyRepository.updateElevatorByManufacturerTypeForDeleteCompany(company.getId());break;
                case 50:
                    companyRepository.updateElevatorByOwnerCompanyTypeForDeleteCompany(company.getId());break;
                case 60:
                    companyRepository.updateElevatorByRegualtorTypeForDeleteCompany(company.getId());break;
                case 70:
                    companyRepository.updateElevatorByPersonalTypeForDeleteCompany(company.getId());break;
                case 80:
                    companyRepository.updateElevatorByOthersTypeForDeleteCompany(company.getId());break;
            }
            companyRepository.delete(company.getId());
            return APIResponse.success();
        } catch (Exception e) {
            return APIResponse.error(e.getMessage());
        }
    }


    @ApiOperation("查询所有维保负责人")
    @RequestMapping(value = "/searchAllManager/{id}", method = RequestMethod.POST)
    public APIResponse searchAllManager(@ApiParam("公司id") @PathVariable("id") String id) {
        List<String> manager = maintenancePersonnelRepository.findByMaintainerId(id);
        return APIResponse.success(manager);
    }

    @ApiOperation("查询维保负责人下的人员")
    @RequestMapping(value = "/searchMen/{manager}", method = RequestMethod.POST)
    public APIResponse searchAllMen(@ApiParam("负责人名称") @PathVariable("manager") String manager) {
        //List<MaintenancePersonnel> name = maintenancePersonnelRepository.findByManager(manager);
        List<String> name=maintenancePersonnelRepository.fingByManager(manager);
        return APIResponse.success(name);
    }

    @ApiOperation("更新电梯维保人员")
    @RequestMapping(value = "/updateMan/{manName}", method = RequestMethod.POST)
    public APIResponse updateMan(@ApiParam("负责人名称") @PathVariable("manName") String manName, @ApiParam("电梯id") String eleId, @ApiParam("公司Id") String companyId) {
        String manId=maintenancePersonnelRepository.findIdByName(manName);
        companyRepository.updateElevatorMaintenanceMan(manId, eleId, companyId);
        return APIResponse.success();
    }


    @ApiOperation("移除电梯")
    @RequestMapping(value = "/removeElevator/{id}", method = RequestMethod.POST)
    public APIResponse removeElevator(@ApiParam("公司id") @PathVariable("id") String id, @ApiParam("电梯id") String elevatorId, @ApiParam("type") String companyType) {
        switch (companyType) {
            case "10":
                companyRepository.updateSingleElevatorByInStallType(id, elevatorId);break;
            case "20":
                companyRepository.updateSingleElevatorByMaintainerType(id, elevatorId);break;
            case "30":
                companyRepository.updateSingleElevatorByUserCompanyType(id, elevatorId);break;
            case "40":
                companyRepository.updateSingleElevatorByManufacturerType(id, elevatorId);break;
            case "50":
                companyRepository.updateSingleElevatorByOwnerCompanyType(id, elevatorId);break;
            case "60":
                companyRepository.updateSingleElevatorByRegulatorType(id, elevatorId);break;
            case "70":
                companyRepository.updateSingleElevatorByPersonalType(id, elevatorId);break;
            case "80":
                companyRepository.updateSingleElevatorByOthersType(id, elevatorId);break;
        }

        return APIResponse.success();

    }

    @RequiresPermissions("export")
    @RequestMapping("/export")
    public void export(HttpServletResponse response,@ApiParam("公司类型")String type,@ApiParam("公司名称")String companyName,@ApiParam("公司地址")String companyAddress) throws Exception {
       companyService.export(response,type,companyName,companyAddress);
    }


}

