package juli.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import juli.api.core.APIController;
import juli.api.core.APIResponse;
import juli.api.dto.ElevatorDto;
import juli.domain.*;
import juli.domain.contract.UpkeepContract;
import juli.domain.enums.*;
import juli.infrastructure.ServletUtil;
import juli.infrastructure.excel.ExcelUtil;
import juli.infrastructure.exception.JuliException;
import juli.infrastructure.persist.DynamicSpecification;
import juli.repository.*;
import juli.service.AreaService;
import juli.service.ElevatorService;
import juli.service.FaultElevator.FaultElevator;
import juli.service.UserService;
import juli.service.WorkBillService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.el.lang.ELArithmetic;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Api(value = "电梯API", description = " ")
@RestController
@RequestMapping("/api/elevator")
public class ElevatorAPIController extends APIController<Elevator, ElevatorRepository> {

    @Autowired
    ElevatorRepository elevatorRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    PremiseRepository premiseRepository;

    @Autowired
    UpkeepContractRepository upkeepContractRepository;

    @Autowired
    ElevatorService elevatorService;

    @Autowired
    AreaService areaService;

    @Autowired
    UserService userService;

//    @Autowired
//    CollectorRepository collectorRepository;

    @Autowired
    MaintenanceRepository maintenanceRepository;

    @Autowired
    WorkBillService workBillService;

    @Autowired
    ElevatorBrandRepository brandRepository;

    @RequiresPermissions("elevator:view")
    @ApiOperation("获得电梯对象")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public APIResponse get(@ApiParam("对象ID") @PathVariable("id") String id) {
        Elevator elevator = elevatorRepository.findOne(id);
        if (elevator == null) {
            return APIResponse.error("不存在此对象：" + id);
        }
        ElevatorDto elevatorDto = new ElevatorDto();
        elevatorDto.setInstallCompanyId(elevator.getInstallCompanyId());
        elevatorDto.setUserCompanyId(elevator.getUserCompanyId());
        elevatorDto.setManufacturerId(elevator.getManufacturerId());
        elevatorDto.setMaintainerId(elevator.getMaintainerId());
        elevatorDto.setRegulatorId(elevator.getRegulatorId());
        elevatorDto.setPersonalId(elevator.getPersonalId());
        elevatorDto.setOthersId(elevator.getOthersId());
        elevatorDto.setOwnerCompanyId(elevator.getOwnerCompanyId());
        elevatorDto.setMaintenanceId(elevator.getMaintenanceId());
        return APIResponse.success(elevatorDto);
    }

    @RequiresPermissions("elevator:view")
    @ApiOperation("获得电梯对象")
    @RequestMapping(value = "/full/{id}", method = RequestMethod.GET)
    public APIResponse getDetailElevator(@ApiParam("对象ID") @PathVariable("id") String id) {
      return elevatorService.getDetailElevator(id);
    }

    @RequiresPermissions("elevator:new")
    @ApiOperation("新增电梯")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public APIResponse create(ElevatorDto elevatorDto) {
        try{
            Elevator elevator = elevatorDto.mapTo();
            setForeignFieldFromDto(elevator, elevatorDto);
            elevatorRepository.save(elevator);
            return APIResponse.success();
        }catch (Exception e){
            return APIResponse.error(e.getMessage());
        }
    }

    @ApiOperation("获取故障未处理电梯信息(分页)")
    @RequestMapping(value = "/notHandled", method = RequestMethod.POST)
    public APIResponse getUnHandledFault(ServletRequest request,
                                         @ApiParam(value = "当前页，从1开始", defaultValue = "1")
                                         @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                         @ApiParam(value = "每页条数", defaultValue = "5")
                                         @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
        Map<String, Object> searchParams = ServletUtil.getParametersStartingWith(request, "search_");
        Specification specification = DynamicSpecification.buildSpecification(searchParams);
        Sort sort = new Sort(Sort.Direction.ASC, "faultTime");
        Pageable pageable = new PageRequest(pageNumber - 1, pageSize, sort);
        Page<Elevator> entities = elevatorRepository.findNotHandledFaultElevatorByPage(pageable);

        return APIResponse.success(entities);
    }

    @ApiOperation("获取故障处理中电梯信息（分页）")
    @RequestMapping(value = "/handled", method = RequestMethod.POST)
    public APIResponse getHandledFault(ServletRequest request,
                                       @ApiParam(value = "当前页，从1开始", defaultValue = "1")
                                       @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                       @ApiParam(value = "每页条数", defaultValue = "5")
                                       @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "createdDate");
        Pageable pageable = new PageRequest(pageNumber - 1, pageSize, sort);
        Page<WorkBills> entities = workBillService.getByCategoryAndStatus(pageable);
        List<WorkBills> workBillsList = entities.getContent();

        List<FaultElevator> faultElevators = new ArrayList<>();

        for (WorkBills wb : workBillsList) {
            if (null!=wb.getElevator()&&(!(Integer.valueOf(ElevatorFaultStatus.NORMAL.getCode()).equals(wb.getElevator().getFaultStatus())) && Integer.valueOf(FaultHandledStatus.HANDLED.getCode()).equals(wb.getElevator().getIsHandled()))) {

                FaultElevator faultElevator = new FaultElevator();
                faultElevator.setId(wb.getElevator().getId());
                faultElevator.setNumber(wb.getElevator().getNumber());
                faultElevator.setAlias(wb.getElevator().getAlias());
                faultElevator.setLat(wb.getElevator().getLat());
                faultElevator.setLng(wb.getElevator().getLng());
                faultElevator.setFaultTime(wb.getElevator().getFaultTime());
                faultElevator.setFaultCode(wb.getElevator().getFaultCode());

                faultElevator.setBillId(wb.getId());
                faultElevator.setBillNumber(wb.getBillNumber());
                faultElevator.setDealTime(wb.getCreatedDate());
                faultElevator.setBillStatus(BillStatus.get(wb.getBillstatus()).getName());
                if(null!=wb.getMaintenancePersonnel()){
                    faultElevator.setDealerId(wb.getMaintenancePersonnel().getId());
                    faultElevator.setBillDealer(wb.getMaintenancePersonnel().getName());
                }
                faultElevators.add(faultElevator);
            }


        }

        PageImpl<FaultElevator> faultElevatorPage = new PageImpl<FaultElevator>(faultElevators, pageable, entities.getTotalElements());

        return APIResponse.success(faultElevatorPage);

    }


    @ApiOperation("获取故障电梯信息(地图需要，不分页)")
    @RequestMapping(value = "/allFault/{isHandled}", method = RequestMethod.GET)
    public APIResponse GetAllFault(@ApiParam("是否处理") @PathVariable("isHandled") Integer isHandled) {
        try {
            User user = userService.getCurrentUser();
            List<Elevator> elevators=new ArrayList<>();
            if(user.getCompanyId() != null&&!user.getCompanyId().equals("")){
                switch (user.getCompanyType()){
                    case "10":
                        elevators = elevatorRepository.findFaultStatusInstall(isHandled, user.getCompanyId());
                        break;
                    case "20":
                        elevators = elevatorRepository.findFaultStatusMaintainer(isHandled, user.getCompanyId());
                        break;
                    case "30":
                        elevators = elevatorRepository.findFaultStatusUser(isHandled, user.getCompanyId());
                        break;
                    case "40":
                        elevators = elevatorRepository.findFaultStatusManufacturer(isHandled, user.getCompanyId());
                        break;
                    case "50":
                        elevators = elevatorRepository.findFaultStatusOwner(isHandled, user.getCompanyId());
                        break;
                    case "60":
                        elevators = elevatorRepository.findFaultStatusRegulator(isHandled, user.getCompanyId());
                        break;
                    case "70":
                        elevators = elevatorRepository.findFaultStatusPersonal(isHandled, user.getCompanyId());
                        break;
                    case "80":
                        elevators = elevatorRepository.findFaultStatusOthers(isHandled, user.getCompanyId());
                }

            }else {
                elevators = elevatorRepository.findFaultStatus(isHandled);
            }

            JSONArray jsonArray = new JSONArray();
            for (Elevator e : elevators) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", e.getId());
                jsonObject.put("lng", e.getLng());
                jsonObject.put("lat", e.getLat());
                jsonObject.put("number", e.getNumber());
                jsonObject.put("alias", e.getAlias());
                jsonArray.add(jsonObject);
            }
            return APIResponse.success(jsonArray);
        } catch (JuliException e) {
            return APIResponse.error(e.getMessage());
        }

    }

    @ApiOperation("添加电梯时判断电梯号是否已存在")
    @RequestMapping(value="/numberSearch",method = RequestMethod.POST)
    public APIResponse numberSearch(@ApiParam("num")String num){
        Elevator elevator=elevatorRepository.findByNumber(num);
        if (elevator != null) {
            return APIResponse.success();
        } else {
            return APIResponse.error("");
        }
    }
    @RequiresPermissions("elevator:edit")
    @ApiOperation("更新")
    @RequestMapping(value = "/editBaseInfoElevator/{id}", method = RequestMethod.PATCH)
    public APIResponse editBaseInfoElevator(@ApiParam("电梯ID") @PathVariable("id") String id,
                              @ApiParam("需要更新的对象") ElevatorDto elevatorDto) throws InvocationTargetException, IllegalAccessException {

       return elevatorService.editBaseInfoElevator(id, elevatorDto);
    }
    @RequiresPermissions("elevator:edit")
    @ApiOperation("更新")
    @RequestMapping(value = "/editCompanyInfoElevator/{id}", method = RequestMethod.PATCH)
    public APIResponse editCompanyInfoElevator(@ApiParam("电梯ID") @PathVariable("id") String id,
                              @ApiParam("需要更新的对象") ElevatorDto elevatorDto) throws InvocationTargetException, IllegalAccessException {

        return elevatorService.editCompanyInfoElevator(id, elevatorDto);
    }

    @ApiOperation("获取当前用户关注的电梯")
    @RequestMapping(value = "/favorites")
    public APIResponse getFavoriteElevators(@ApiParam(value = "当前页，从1开始", defaultValue = "1")
                                            @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                            @ApiParam(value = "每页条数", defaultValue = "15")
                                            @RequestParam(value = "pageSize", defaultValue = "15") int pageSize) {
        try {
            User user = userService.getCurrentUser();
            Page<Elevator> elevatorList = elevatorRepository.findByUserId(user.getId(), new PageRequest(pageNumber - 1, pageSize));

            for (Elevator elevator : elevatorList.getContent()) {
                elevator.setUpkeepContracts(null);//upkeepContract 当前电梯下的合同
                if (elevator.getUsers().contains(user)) {
                    elevator.setFavorite(1);
                } else {
                    elevator.setFavorite(0);
                }
            }
            return APIResponse.success(elevatorList);
        } catch (JuliException e) {
            return APIResponse.error(e.getMessage());
        }
    }

    @ApiOperation("更新")
    @RequestMapping(value = "/favorite/{id}", method = RequestMethod.POST)
    public APIResponse updateFavoriteElevators(@ApiParam("电梯ID") @PathVariable("id") String id) {
        try {
            Elevator existingEntity = elevatorRepository.findElevatorById(id);
            if (existingEntity == null) {
                return APIResponse.error("不存在此对象：" + id);
            }
            User user = userService.getCurrentUser();
            if (existingEntity.getUsers().contains(user)) {
                existingEntity.getUsers().remove(user);
            } else {
                existingEntity.getUsers().add(user);
            }
            elevatorRepository.save(existingEntity);
            return APIResponse.success();
        } catch (JuliException e) {
            return APIResponse.error(e.getMessage());
        }
    }

    @RequiresPermissions(value = "onlineMonitoring:view")
    @ApiOperation("在线监控搜索（带分页）")
    @RequestMapping(value = "/new/search/page", method = RequestMethod.POST)
    public APIResponse searchNewPage(ServletRequest request,
                                     @ApiParam(value = "当前页，从1开始", defaultValue = "1")
                                     @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                     @ApiParam(value = "每页条数", defaultValue = "15")
                                     @RequestParam(value = "pageSize", defaultValue = "15") int pageSize,
                                     @ApiParam(value = "是否为我的关注", defaultValue = "0")
                                     @RequestParam(value = "favorite", defaultValue = "0") int favorite

    ) {
        try {
            User user = userService.getCurrentUser();
            Map<String, Object> searchParams = ServletUtil.getParametersStartingWith(request, "search_");
            List<String> idList = new ArrayList<>();
            if (user.getCompanyId() != null && !user.getCompanyId().equals("")) {
                if (favorite == 1) {
                    switch (user.getCompanyType()) {
                        case "10"://安装单位
                            idList = elevatorRepository.findElevatorIdsByFavCompanyIdInstall(user.getId(), user.getCompanyId());
                            break;
                        case "20"://维保单位
                            idList = elevatorRepository.findElevatorIdsByFavCompanyIdMaintainer(user.getId(), user.getCompanyId());
                            break;
                        case "30"://使用单位
                            idList = elevatorRepository.findElevatorIdsByFavCompanyIdUser(user.getId(), user.getCompanyId());
                            break;
                        case "40"://制造单位
                            idList = elevatorRepository.findElevatorIdsByFavCompanyIdManufacturer(user.getId(), user.getCompanyId());
                            break;
                        case "50"://物业单位
                            idList = elevatorRepository.findElevatorIdsByFavCompanyIdOwer(user.getId(), user.getCompanyId());
                            break;
                        case "60"://监管机构
                            idList = elevatorRepository.findElevatorIdsByFavCompanyIdRegulator(user.getId(), user.getCompanyId());
                            break;
                        case "70"://个人用户
                            idList = elevatorRepository.findElevatorIdsByFavCompanyIdPersonal(user.getId(), user.getCompanyId());
                            break;
                        case "80"://其他类型
                            idList = elevatorRepository.findElevatorIdsByFavCompanyIdOthers(user.getId(), user.getCompanyId());
                            break;
                    }
                    searchParams.put("id_in", getIds(idList));
                } else {
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
                }
            } else {
                if (favorite == 1) {
                    idList = elevatorRepository.findElevatorIds(user.getId());
                    searchParams.put("id_in", getIds(idList));
                }
            }

            Object iObj = searchParams.get("intelHardwareNumber_like");

            Object pObj = searchParams.get("premises_like");
            if (pObj != null) {
                Map<String, Object> nParams = new HashMap<>();
                nParams.put("name_like", pObj);
                Specification nSpecification = DynamicSpecification.buildSpecification(nParams);
                List<Premise> pList = premiseRepository.findAll(nSpecification);
                if (pList != null && pList.size() > 0) {
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < pList.size(); i++) {
                        if (i != pList.size() - 1) {
                            sb.append(pList.get(i).getId() + ",");
                        } else {
                            sb.append(pList.get(i).getId());
                        }
                    }
                    searchParams.put("premise.id_in", sb.toString());
                }
                searchParams.remove("premises_like");
            }
            Map<String, Object> searchParams_new = new HashMap<>();

            searchParams_new.put("number(TEXT)_LIKE", searchParams.get("number(TEXT)_LIKE") != null ? searchParams.get("number(TEXT)_LIKE").toString().trim() : "");
            searchParams_new.put("address(TEXT)_LIKE", searchParams.get("address(TEXT)_LIKE") != null ? searchParams.get("address(TEXT)_LIKE").toString().trim() : "");
            //获取在线管理首页查询功能相关字段值
            searchParams_new.put("number_like", searchParams.get("number_like") != null ? searchParams.get("number_like").toString().trim() : "");//电梯工号
            searchParams_new.put("brandId_eq", searchParams.get("brandId_eq") != null ? searchParams.get("brandId_eq").toString().trim() : "");//品牌
            searchParams_new.put("type_eq", searchParams.get("eType_eq") != null ? searchParams.get("eType_eq").toString().trim() : "");//电梯类型 0.未連接只能設備1.已連接智能設備2.多品牌電梯
            searchParams_new.put("regCode_like", searchParams.get("regCode_like") != null ? searchParams.get("regCode_like").toString().trim() : "");//注册代码
            searchParams_new.put("intelHardwareNumber_like", searchParams.get("intelHardwareNumber_like") != null ? searchParams.get("intelHardwareNumber_like").toString().trim() : "");//智能硬件注册码
            searchParams_new.put("alias_like", searchParams.get("alias_like") != null ? searchParams.get("alias_like").toString().trim() : "");//项目名称
            searchParams_new.put("provinceId_eq", searchParams.get("provinceId_eq") != null ? searchParams.get("provinceId_eq").toString().trim() : "");//省
            searchParams_new.put("cityId_eq", searchParams.get("cityId_eq") != null ? searchParams.get("cityId_eq").toString().trim() : "");//市
            searchParams_new.put("regionId_eq", searchParams.get("regionId_eq") != null ? searchParams.get("regionId_eq").toString().trim() : "");//区镇

            //获取左侧电梯状态
            searchParams_new.put("status_eq", searchParams.get("status_eq") != null ? searchParams.get("status_eq").toString().trim() : "");//在线，离线，正常
            searchParams_new.put("maintenanceStatus_eq", searchParams.get("maintenanceStatus_eq") != null ? searchParams.get("maintenanceStatus_eq").toString().trim() : "");//检修
            if (searchParams.get("faultStatus_eq") != null) {
                if (searchParams.get("faultStatus_eq").toString().trim().equals("" + ElevatorFaultStatus.MALFUNCTION.getCode())){
                    searchParams_new.put("faultStatus_eq", "" + ElevatorFaultStatus.MALFUNCTION.getCode());
                    searchParams_new.put("maintenanceStatus_eq", "" + ElevatorMaintennanceStatus.NORMAL.getCode());
                } else {
                    searchParams_new.put("faultStatus_eq", searchParams.get("faultStatus_eq").toString().trim());
                }
            }
            if (searchParams.get("normal_eq") != null && searchParams.get("normal_eq").toString().trim().equals("10")) {
                searchParams_new.put("faultStatus_eq", "" + ElevatorFaultStatus.NORMAL.getCode());
                searchParams_new.put("maintenanceStatus_eq", "" + ElevatorMaintennanceStatus.NORMAL.getCode());
                searchParams_new.put("status_eq" , "" + ElevatorStatus.ONLINE.getCode());
            }
//            searchParams_new.put("faultStatus_eq", searchParams.get("faultStatus_eq") != null ? searchParams.get("faultStatus_eq").toString().trim() : "");//故障
            searchParams_new.put("id_in", searchParams.get("id_in") != null ? searchParams.get("id_in").toString() : "");//favorite
            searchParams_new.put("premises_like", searchParams.get("premises_like") != null ? searchParams.get("premises_like").toString() : "");
            searchParams_new.put("premise.id_in", searchParams.get("premise.id_in") != null ? searchParams.get("premise.id_in").toString() : "");
            Specification specification = DynamicSpecification.buildSpecification(searchParams_new);
            Page entities = elevatorRepository.findAll(specification, new PageRequest(pageNumber - 1, pageSize));
            List<Elevator> elevators = entities.getContent();
            for (Elevator elevator : elevators) {
                elevator.setUpkeepContracts(null);//upkeepContract 当前电梯下的合同
                if (elevator.getUsers().contains(user)) {
                    elevator.setFavorite(1);
                } else {
                    elevator.setFavorite(0);
                }
            }
            return APIResponse.success(entities);
        } catch (JuliException e) {
            return APIResponse.error(e.getMessage());
        }
    }


    @RequiresPermissions(value = "elevator:view")
    @ApiOperation("电梯管理搜索（带分页）")
    @RequestMapping(value = "/new/elevatorSearch/page", method = RequestMethod.POST)
    public APIResponse searchNewPageElevator(ServletRequest request,
                                     @ApiParam(value = "当前页，从1开始", defaultValue = "1")
                                     @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                     @ApiParam(value = "每页条数", defaultValue = "15")
                                     @RequestParam(value = "pageSize", defaultValue = "15") int pageSize
    ) {
       return elevatorService.getElevatorsForSearch(request, pageNumber, pageSize);
    }

    @RequiresPermissions("elevator:view")
    @ApiOperation("获取全部Dto电梯")
    @RequestMapping(value = "/elevatorDto/all", method = RequestMethod.GET)
    public APIResponse getAllElevatorDto() {
        Iterator<Elevator> list = elevatorRepository.findAll().iterator();
        List<ElevatorDto> listDto = new ArrayList<>();
        ElevatorDto elevatorDto = new ElevatorDto();
        while (list.hasNext()) {
            elevatorDto.setId(list.next().getId());
            elevatorDto.setElevatorNumber(list.next().getNumber());
           // listDto.add(elevatorDto.mapFrom(list.next()));
            listDto.add(elevatorDto);
        }

        return APIResponse.success(listDto);
    }

    @RequestMapping(value="/searchCompanyName",method=RequestMethod.POST)
    public APIResponse searchCompanyName(@ApiParam("companyName")String companyName,@ApiParam("type")int type){
        return elevatorService.searchCompanyName(companyName, type);
    }

    //显示地图的功能
    @RequestMapping(value = "/elevator/all", method = RequestMethod.GET)
    public APIResponse getAll() {
        List<String[]> elevators = elevatorRepository.findPosition();
        return APIResponse.success(elevators);
    }


    @RequiresPermissions("elevator:view")
    @ApiOperation("根据电梯Number查询获取电梯")
    @RequestMapping(value = "/elevatorDto/typeahead", method = RequestMethod.POST)
    public APIResponse getElevatorTypeAhead(@ApiParam("电梯Number") String number) {
        String signNumber = "%" + number + "%";
        List<Elevator> list = elevatorRepository.selectElevatorByNumber(signNumber);
        List<ElevatorDto> listDto = new ArrayList<>();
        for (Elevator el : list) {
            ElevatorDto elevatorDto = new ElevatorDto();
            elevatorDto.setId(el.getId());
            elevatorDto.setNumber(el.getNumber());
            elevatorDto.setBrandName(el.getBrandId() == null ? "暂无":brandRepository.findName(el.getBrandId()));
            listDto.add(elevatorDto);
        }
        return APIResponse.success(listDto);
    }

    @ApiOperation("自动补全电梯Number")
    @RequestMapping(value = "/elevatorDto/getNumbers", method = RequestMethod.POST)
    public APIResponse getElevatorNumber(@ApiParam("电梯Number") String number) {
       return elevatorService.getElevatorNumber(number);
    }

    @ApiOperation("自动补全电梯Address")
    @RequestMapping(value = "/elevatorDto/getAddress", method = RequestMethod.POST)
    public APIResponse getElevatorAddress(@ApiParam("电梯Address") String address) {
        return elevatorService.getElevatorAddress(address);
    }

    @ApiOperation("自动补全电梯ProjectName")
    @RequestMapping(value = "/elevatorDto/getProjectName", method = RequestMethod.POST)
    public APIResponse getElevatorProjectName(@ApiParam("电梯ProjectName") String projectName) {
        return elevatorService.getElevatorProjectName(projectName);
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

    private void setForeignFieldFromDto(Elevator entity, ElevatorDto dto) {
        if(!dto.getElevatorType().equals("")){
            entity.setElevatorType(dto.getElevatorType());
        }else {
            entity.setElevatorType(null);
        }
        if (!dto.getBrandId().equals("-1")) {
            entity.setBrandId(dto.getBrandId());
        } else {
            entity.setBrandId(null);
        }
        if(dto.getInstallCompanyId()!=null&&!dto.getInstallCompanyId().equals("")){
            entity.setInstallCompanyId(dto.getInstallCompanyId());
        }
        else {
            entity.setInstallCompanyId(null);
        }
       if(dto.getUserCompanyId()!=null&&!dto.getUserCompanyId().equals("")){
           entity.setUserCompanyId(dto.getUserCompanyId());
       }
       else {
           entity.setUserCompanyId(null);
       }
        if(dto.getManufacturerId()!=null&&!dto.getManufacturerId().equals("")){
            entity.setManufacturerId(dto.getManufacturerId());
        }else {
            entity.setManufacturerId(null);
        }
        if(dto.getMaintainerId()!=null&&!dto.getMaintainerId().equals("")){
            entity.setMaintainerId(dto.getMaintainerId());}
        else {
            entity.setMaintainerId(null);
        }

        if(dto.getMaintenanceName()!=null&&!dto.getMaintenanceName().equals("-1")){
            String maintenanceId=maintenanceRepository.findIdByName(dto.getMaintenanceName());
            entity.setMaintenanceId(maintenanceId);
        }else {
            entity.setMaintenanceId(null);
        }
        if(dto.getOwnerCompanyId()!=null&&!dto.getOwnerCompanyId().equals("")){
            entity.setOwnerCompanyId(dto.getOwnerCompanyId());
        }else {
            entity.setOwnerCompanyId(null);
        }
        if(dto.getRegulatorId()!=null&&!dto.getRegulatorId().equals(""))
        {
            entity.setRegulatorId(dto.getRegulatorId());
        }
        else {
            entity.setRegulatorId(null);
        }
        if(dto.getPersonalId()!=null&&!dto.getPersonalId().equals(""))
        {
            entity.setPersonalId(dto.getPersonalId());
        }
       else {
            entity.setPersonalId(null);
        }
        if(dto.getOthersId()!=null&&!dto.getOthersId().equals("")) {
            entity.setOthersId(dto.getOthersId());
        }else {
            entity.setOthersId(null);
        }
    }

    private void mapDtoFromEntity(Elevator entity, ElevatorDto dto) {

    }


    @RequiresPermissions("export")
    @RequestMapping("/export")
    public void export(HttpServletResponse response,HttpServletRequest request) throws Exception {
        elevatorService.export(response,request);
    }


}