package juli.service;

import com.alibaba.fastjson.JSONObject;
import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.PushMsgToSingleDeviceRequest;
import com.baidu.yun.push.model.PushMsgToSingleDeviceResponse;
import juli.api.NetworkRescueAPIController;
import juli.api.core.APIResponse;
import juli.api.dto.BillStaticsDto;
import juli.api.dto.WorkBillDto;
import juli.domain.*;
import juli.domain.enums.*;
import juli.infrastructure.DateUtil;
import juli.infrastructure.ServletUtil;
import juli.infrastructure.exception.JuliException;
import juli.infrastructure.persist.DynamicSpecification;
import juli.repository.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Service
public class WorkBillService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    WorkBillRepository workBillRepository;
    @Autowired
    MaintenanceRepository maintenanceRepository;
    @Autowired
    ElevatorRepository elevatorRepository;
    @Autowired
    ReportService reportService;
    @Autowired
    UserService userService;
    @Autowired
    MaintenancePlanRepository maintenancePlanRepository;
    @Autowired
    CompanyRepository companyRepository;

    //ios端 Service
    public PageImpl findPersonalBillByCategroyAndStatus(String id, Integer billCategory, Integer billstatus1, Integer billstatus2, Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "billstatus");
        Page<WorkBills> list = workBillRepository.findPersonalBillByCategroyAndStatus(id, billCategory, billstatus1, billstatus2, pageRequest);
        Map billMap;
        List<Map> mapList = new ArrayList<>();
        if (billstatus2 < 40) {
            for (WorkBills bill : list.getContent()) {
                billMap = new HashMap<String, Object>();
                billMap.put("id", bill.getId());
                billMap.put("statusName", BillStatus.get(bill.getBillstatus()).getName());
                billMap.put("status",bill.getBillstatus());
                billMap.put("category",bill.getBillCategory());
                billMap.put("startime",bill.getStartTime()==null?"00-00-00:0:0:0":bill.getStartTime().toString().substring(0,19));
                billMap.put("billmode",bill.getBillModel());
                MaintenancePlan maintenancePlan =maintenancePlanRepository.findByWorkBillId(bill.getId());
                if(null!=maintenancePlan){
                    billMap.put("planEndTime",maintenancePlan.getPlanEndTime()==null?maintenancePlan.getPlanEndTime():maintenancePlan.getPlanEndTime().toString());
                }
                if (bill.getElevator() != null) {
                    Elevator elevator = bill.getElevator();
                    billMap.put("elevatorNumber",elevator.getNumber());
                    billMap.put("equipmentNumber",elevator.getEquipmentNumber());
                    billMap.put("elevator", elevator.getNumber() + "/" + elevator.getAlias());
                    billMap.put("elevatorType", elevator.getElevatorType());
                }
                mapList.add(billMap);
            }
        }
       else if(billstatus1>30)
        {
            for(WorkBills bill:list.getContent())
            {
                billMap=new HashMap<String,Object>();
                billMap.put("id", bill.getId());
                billMap.put("status",bill.getBillstatus());
                billMap.put("category",bill.getBillCategory());
                billMap.put("completetime",bill.getCompleteTime()==null?"00-00-00:0:0:0":bill.getCompleteTime().toString().substring(0,19));
                if (bill.getElevator() != null) {
                    Elevator elevator = bill.getElevator();
                    billMap.put("elevator", elevator.getNumber() + "/" +elevator.getAlias());
                    billMap.put("elevatorType", elevator.getElevatorType());
                }
                mapList.add(billMap);
            }
        }
        PageImpl<Map> billsPage=new PageImpl<Map>(mapList,pageRequest,list.getTotalElements());
        return billsPage;
    }
    public List<Map> findPersonalBillByCategroyAndStatus(String id, Integer billCategory, Integer billstatus1, Integer billstatus2) {
        //PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "billstatus");
        List<WorkBills> list = workBillRepository.findPersonalBillByCategroyAndStatus(id, billCategory, billstatus1, billstatus2);
        Map billMap;
        List<Map> mapList = new ArrayList<>();
        if (billstatus2 < 40) {
            for (WorkBills bill : list) {
                billMap = new HashMap<String, Object>();
                billMap.put("id", bill.getId());
                billMap.put("statusName", BillStatus.get(bill.getBillstatus()).getName());
                billMap.put("equipmentNumber",bill.getElevator().getEquipmentNumber());
                billMap.put("status",bill.getBillstatus());
                billMap.put("category",bill.getBillCategory());
                billMap.put("startime",bill.getStartTime()==null?"00-00-00:0:0:0":bill.getStartTime().toString().substring(0,19));
                billMap.put("billmode",bill.getBillModel());
                billMap.put("elevatorNumber",bill.getElevator().getNumber());
                if (bill.getElevator() != null) {
                    Elevator elevator = bill.getElevator();
                    billMap.put("elevator", elevator.getNumber() + "/" + elevator.getAlias());
                }
                mapList.add(billMap);
            }
        }
        else if(billstatus1>30)
        {
            for(WorkBills bill:list)
            {
                billMap=new HashMap<String,Object>();
                billMap.put("id", bill.getId());
                billMap.put("status",bill.getBillstatus());
                billMap.put("category",bill.getBillCategory());
                billMap.put("completetime",bill.getCompleteTime()==null?"00-00-00:0:0:0":bill.getCompleteTime().toString().substring(0,19));
                if (bill.getElevator() != null) {
                    Elevator elevator = bill.getElevator();
                    billMap.put("elevator", elevator.getNumber() + "/" +elevator.getAlias());
                }
                mapList.add(billMap);
            }
        }
        return mapList;
    }
//    public Page<WorkBills> findByBillsByCategoryPageable(Integer billCategory,Pageable pageable){
//        return workBillRepository.findByBillsByCategoryPageable(billCategory,pageable);
//    }
//
//    public Page<WorkBills> getPagedWorkBills(Integer pageNumber,Integer pageSize,Integer status){
//        PageRequest pageRequest=new PageRequest(pageNumber - 1, pageSize);
//        Page<WorkBills> billList=workBillRepository.findByBillsByStatusAndPageable(status, pageRequest);
//        return billList;
//    }
//
    public APIResponse getJXBills(Integer pageNumber, Integer pageSize) {
        try {
            User user = userService.getCurrentUser();
            PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize);
            Page<WorkBills> billList;
            if (user.getCompanyId() != null && !user.getCompanyId().equals("")) {
                billList = workBillRepository.findByBillstatusLessThanAndBillCategory(40, 30, user.getCompanyId(), pageRequest);
            } else {
                billList = workBillRepository.findByBillstatusLessThanAndBillCategory(40, 30, pageRequest);
            }
            List<WorkBillDto> list = toDto(billList.getContent());
            PageImpl<WorkBillDto> billsPage = new PageImpl<WorkBillDto>(list, pageRequest, billList.getTotalElements());
            return APIResponse.success(billsPage);
        } catch (JuliException e) {
            return APIResponse.error(e.getMessage());
        }

    }
    public APIResponse deleteBill(String id){
        if(StringUtils.isNotEmpty(id)){
            workBillRepository.delete(id);
        }
        return APIResponse.success();
    }

    public List<WorkBills> findBillsByElevatorId(String elevatorId,Integer billCategory){
        return workBillRepository.findBillsByElevatorId(elevatorId, billCategory);
    }


    public WorkBills saveWorkBills(WorkBills workBills){
        return workBillRepository.save(workBills);
    }

    public APIResponse getBillById(String id) throws Exception {
        WorkBills workBills=workBillRepository.findOne(id);
        if(workBills!=null)
        {
            Map<String,Object> paramMap=new HashMap<>();
            paramMap.put("billnumber",workBills.getBillNumber());
            Elevator elevator=workBills.getElevator();
            String type = "";
            String lastChecktime="";
            String producttime = "";
            if(null!=elevator){
                type=elevator.getElevatorType();
                lastChecktime=elevator.getLastCheckDate()==null?"":DateUtil.dateToFullString2(elevator.getLastCheckDate());
                producttime=elevator.getProductionTime()==null?"":DateUtil.dateToFullString2(elevator.getProductionTime());
                paramMap.put("elevatortype",type);
                paramMap.put("controllerType",elevator.getControllerType());
                paramMap.put("floor",elevator.getFloor());
                paramMap.put("hoistingHeight",elevator.getHoistingHeight());
                paramMap.put("elevatornumber",elevator.getNumber());
                paramMap.put("elevatorid",elevator.getId());
                paramMap.put("productiontime",elevator.getProductionTime());
                if(CollectionUtils.isNotEmpty(elevator.getUpkeepContracts())&&elevator.getUpkeepContracts().get(0)!=null){
                    paramMap.put("maintainProperty",elevator.getUpkeepContracts().get(0).getProperty());
                }
                if(StringUtils.isNotEmpty(elevator.getMaintainerId())){
                    Company company = companyRepository.findOne(elevator.getMaintainerId());
                    paramMap.put("maintainer",company.getName());
                }
                if(CollectionUtils.isNotEmpty(elevator.getUpkeepContracts())&&elevator.getUpkeepContracts().get(0)!=null){
                    paramMap.put("upkeepcontractnumber",elevator.getUpkeepContracts().get(0).getNumber());
                }
                if(StringUtils.isNotEmpty(elevator.getUserCompanyId())){
                    Company company=companyRepository.findById(elevator.getUserCompanyId());
                    if(null!=company){
                        paramMap.put("userCompany",company.getName());
                        paramMap.put("address",company.getAddress());
                        paramMap.put("phone",company.getPhone());
                        paramMap.put("contact",company.getContact());
                        paramMap.put("mobile",company.getMobile());
                    }
                }
            }
            String starttime=workBills.getStartTime()==null?"":DateUtil.dateToFullString2(workBills.getStartTime());
            String entertime=workBills.getEnterTime()==null?"":DateUtil.dateToFullString2(workBills.getEnterTime());
            String completetime=workBills.getCompleteTime()==null?"":DateUtil.dateToFullString2(workBills.getCompleteTime());
            String createtime=workBills.getCreatedDate()==null?"":DateUtil.dateToFullString(workBills.getCreatedDate().toDate());
            paramMap.put("billCategory", workBills.getBillCategory());
            paramMap.put("billModel", workBills.getBillModel());
            paramMap.put("billModelName", BillModel.get(workBills.getBillModel()).getName());
            paramMap.put("maintenancePersonal", workBills.getMaintenancePersonnel().getName() + "/" + workBills.getMaintenancePersonnel().getNumber());
            if(workBills.getBillCategory()==10||workBills.getBillCategory()==30)//维修工单
            {
                paramMap.put("createTime",createtime);
                paramMap.put("id",workBills.getId());
                paramMap.put("starttime",starttime);
                paramMap.put("faultDescription",workBills.getFaultDescription());
                paramMap.put("faultPerson",workBills.getFaultPerson());
                paramMap.put("faultPersonTelephone",workBills.getFaultPersonTelephone());
                paramMap.put("entertime", entertime);
                paramMap.put("localdescription",workBills.getLocalDescription());
                paramMap.put("makesteps",workBills.getTakeSteps());
                paramMap.put("faulyquality",workBills.getFaultQuality());
                paramMap.put("result",workBills.getResult());
                paramMap.put("completetime",completetime);
            }
            if(workBills.getBillCategory()==20)
            {
                paramMap.put("id",workBills.getId());
                if(null!=elevator){
                    paramMap.put("elevatortype",type);
                    paramMap.put("elevatornumber",elevator.getNumber());
                    paramMap.put("productiontime",producttime);
                    paramMap.put("ratedweight",elevator.getRatedWeight());
                    paramMap.put("ratedspeed",elevator.getRatedSpeed());
                    paramMap.put("floor",elevator.getFloor());
                    paramMap.put("hoistingHeight",elevator.getHoistingHeight());
                    paramMap.put("useaddress",elevator.getAddress());
                    paramMap.put("checkstatus",lastChecktime);
                }

//                paramMap.put("upkeepcontractnumber",elevator.getUpkeepContract().getNumber());
                paramMap.put("maintainname",workBills.getMaintainName());
                paramMap.put("starttime",starttime);
                paramMap.put("maintainprograms",workBills.getMaintainPrograms());
                paramMap.put("result",workBills.getResult());
                paramMap.put("completetime",completetime);
            }
            return APIResponse.success(paramMap);
        }
        else
        {
            return APIResponse.error("不存在此工单");
        }
    }
    public APIResponse changeBillStatus(String id,Integer status) throws Exception {
        WorkBills workBills= workBillRepository.findOne(id);
        Map<String,Object> paramMap=new HashMap<>();
        if(workBills!=null)
        {
            MaintenancePersonnel maintenancePersonnel=workBills.getMaintenancePersonnel();
           if(status==20)//执行
           {
               maintenancePersonnel.setCurrentBill(workBills);
           }
            else
           {
               maintenancePersonnel.setCurrentBill(null);
           }
            maintenanceRepository.save(maintenancePersonnel);
            workBills.setStartTime(new Date());
            workBills.setBillstatus(status);
            workBillRepository.save(workBills);
            paramMap.put("starttime", DateUtil.dateToFullString2(workBills.getStartTime()
            ));
            return  APIResponse.success(paramMap);
        }
        else
            return APIResponse.error("不存在此对象");
    }
    public APIResponse refuseBill(String id, String refudeReason) {
        WorkBills workBills = workBillRepository.findOne(id);
        if (workBills != null) {
            workBills.setRefuseReason(refudeReason);
            workBills.setStartTime(new Date());
            workBills.setCompleteTime(new Date());
            workBills.setBillstatus(40);
            workBillRepository.save(workBills);
            return  APIResponse.success();
        }
        else
            return APIResponse.error("不存在此对象");
    }
    public APIResponse hangWXBill(String id,String localDescription,String makesteps,String faultQuality,String result,Integer status,String assists,String picPath)
    {
        WorkBills workBills= workBillRepository.findOne(id);
        if(workBills!=null)
        {
            MaintenancePersonnel maintenancePersonnel=workBills.getMaintenancePersonnel();
            maintenancePersonnel.setCurrentBill(null);
            maintenanceRepository.save(maintenancePersonnel);//修改维保人员当前工单
            workBills.setLocalDescription(localDescription);
            workBills.setTakeSteps(makesteps);
            workBills.setFaultQuality(faultQuality);
            workBills.setResult(result);
            workBills.setPicture1(picPath);
            if(status==30)//挂起操作
                workBills.setBillstatus(30);
            if(status==50)//完成操作
            {
                workBills.setAssists(assists);
                //工单状态改为待评价
                workBills.setBillstatus(60);
                workBills.setCompleteTime(new Date());
                //工单完成后，将电梯状态设置为正常
                Elevator e = workBills.getElevator();
                e.setFaultStatus(ElevatorFaultStatus.NORMAL.getCode());
                elevatorRepository.save(e);
                reportService.createReport(workBills);//生成急修报告

            }

            workBillRepository.save(workBills);
            return  APIResponse.success();
        }
        else
            return APIResponse.error("不存在此对象");
    }
    public APIResponse hangWBBill(String id,String maintainPrograms,String result,Integer status,String assists,String picPath) throws Exception {
        WorkBills workBills= workBillRepository.findOne(id);
        if(workBills!=null)
        {
            if(StringUtils.isNotEmpty(picPath)){
                String pics[] = picPath.split(",");
                if(pics.length>=1){
                    workBills.setPicture1(pics[0]);
                }
                if(pics.length>=2){
                    workBills.setPicture2(pics[1]);
                }
                if(pics.length>=3){
                    workBills.setPicture3(pics[2]);
                }
                if(pics.length>=4){
                    workBills.setPicture4(pics[3]);
                }
                if(pics.length>=5){
                    workBills.setPicture5(pics[4]);
                }
            }
            MaintenancePersonnel maintenancePersonnel=workBills.getMaintenancePersonnel();
            maintenancePersonnel.setCurrentBill(null);
            maintenanceRepository.save(maintenancePersonnel);
            workBills.setMaintainPrograms(maintainPrograms);
            workBills.setResult(result);
//   取消处理结果为空时自动生成一个维修工单的功能，本身实现条件有问题
//            if(!result.equals("无"))
//            {
//                WorkBillDto dto=new WorkBillDto();
//                dto=dto.mapFrom(workBills);
//                dto.setMaintenanceId(null);
//                dto.setId(null);
//                dto.setStartTime(null);
//                dto.setCompleteTime(null);
//                dto.setBillModel(50);
//                this.create(2,dto);
//            }
            if(status==30)//挂起操作
              workBills.setBillstatus(30);
            if(status==50)//完成操作
            {
                workBills.setAssists(assists);
                workBills.setCompleteTime(new Date());
                //工单状态改为待评价
                workBills.setBillstatus(60);
                reportService.createReport(workBills);//生成维保报告

            }
            workBillRepository.save(workBills);
            return  APIResponse.success();
        }
        else
            return APIResponse.error("不存在此对象");
    }
    public APIResponse hangJXBill(String id,String localDescription,String makesteps,String faultQuality,String result,Integer status,String assists,String picPath) throws Exception {
        WorkBills workBills= workBillRepository.findOne(id);
        if(workBills!=null)
        {
            workBills.setEnterTime(new Date());
            workBills.setLocalDescription(localDescription);
            workBills.setTakeSteps(makesteps);
            workBills.setFaultQuality(faultQuality);
            workBills.setResult(result);
            workBills.setPicture1(picPath);
            if(status==30)//转为普通并挂起操作
            {
                //生成相应的维修工单
                String billnumber=DateUtil.dateToString(new Date(),"yyyy-MM-dd")+"-"+(new Random().nextInt(89999)+10000);
                WorkBills bills= new WorkBills();
                bills.setBillNumber("WX" + billnumber);
                bills.setBillstatus(5);//待派发
                bills.setBillCategory(10);//维修
                bills.setBillModel(50);//维修
//                bills.setMaintenancePersonnel(workBills.getMaintenancePersonnel());
                bills.setElevator(workBills.getElevator());
                bills.setEnumber(workBills.getElevator().getNumber());
                bills.setId(null);
                bills.setAssists(assists);
                workBillRepository.save(bills);
                //将急修工单转台转换为”转维修“
                workBills.setBillstatus(55);
                workBills.setCompleteTime(new Date());
                reportService.createReport(workBills);//生成急修报告
                workBills.setAfterbill(bills);
                workBillRepository.save(workBills);

            }
            if(status==50)//完成操作
            {
                workBills.setAssists(assists);
                workBills.setCompleteTime(new Date());
                workBills.setBillstatus(60);//急修工单状态改为待评价
                reportService.createReport(workBills);//生成急修报告
                workBillRepository.save(workBills);

                //急修工单完成，修改电梯状态
                Elevator e = workBills.getElevator();
                e.setFaultStatus(ElevatorFaultStatus.NORMAL.getCode());
                elevatorRepository.save(e);

            }
            return  APIResponse.success();
        }
        else
            return APIResponse.error("不存在此对象");
    }
    public APIResponse findBillsByElevatorAndBillCategoryAndBillstatus(String id,Integer category,Integer pageNumber,Integer pageSize)
    {
        Map<String,Object> billMap;
        List<Map>  mapList=new ArrayList<>();
        Elevator elevator=elevatorRepository.findOne(id);
        Page<WorkBills> list=workBillRepository.findBillsByElevatorAndBillCategoryAndBillstatus(elevator,category,50,new PageRequest(pageNumber - 1, pageSize,Sort.Direction.ASC,"completeTime"));
        for(WorkBills bill:list.getContent())
        {
            billMap=new HashMap<String,Object>();
            billMap.put("id", bill.getId());
            billMap.put("statusName", BillStatus.get(bill.getBillstatus()).getName());
            billMap.put("equipmentNumber",elevator.getEquipmentNumber());
            billMap.put("category",bill.getBillCategory());
            billMap.put("elevator", elevator.getNumber() + " | " + elevator.getAddress());
            mapList.add(billMap);
        }
        return  APIResponse.success(mapList);
    }
    //web Service
    public APIResponse findBills(Integer status1,Integer status2,Integer pageNumber,Integer pageSize)
    {
        Page<WorkBills> billList=workBillRepository.findBillsByStatus(status1, status2, new PageRequest(pageNumber - 1, pageSize));
        Map billMap;
        List<Map>  mapList=new ArrayList<>();

            for(WorkBills bill:billList.getContent())
            {
                billMap=new HashMap<String,Object>();
                billMap.put("id", bill.getId());
                billMap.put("billnumber",bill.getBillNumber());
                billMap.put("statusname", BillStatus.get(bill.getBillstatus()).getName());
                billMap.put("elevatornumber",bill.getElevator().getNumber());
                billMap.put("maintenance",bill.getMaintenancePersonnel().getName());
                billMap.put("category", BillReportCategory.get(bill.getBillCategory()).getName());
                mapList.add(billMap);
            }
        PageRequest pageRequest=new PageRequest(pageNumber-1,pageSize,Sort.Direction.ASC,"billstatus");
        PageImpl<Map> billsPage=new PageImpl<Map>(mapList,pageRequest,billList.getTotalElements());
        return  APIResponse.success(billsPage);
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
    public APIResponse newsearch(ServletRequest request, Integer pageNumber, Integer pageSize) {
        try {
            User user = userService.getCurrentUser();
            Map<String, Object> searchParams = ServletUtil.getParametersStartingWith(request, "search_");
            List<String> idList;
            if(user.getCompanyId() != null&&!user.getCompanyId().equals("")&&user.getCompanyType().equals("20")){
               idList=workBillRepository. findWorkBillsIds(user.getCompanyId());
                searchParams.put("id_in", getIds(idList));
            }
            else
            {
                Role role=user.getRoles().get(0);
                if(!role.getName().equals("超级管理员")) {
                    searchParams.put("id_in","none");
                }
            }

            Sort sort = null;
            String sortName = request.getParameter("sortName");
            String sortOrder = request.getParameter("sortOrder");
            if (sortName == null) {
                sort = new Sort(Sort.Direction.DESC, "createdDate");
            } else {
                if ("startTimeStr".equals(sortName)) {
                    sortName = "startTime";
                }
                if ("asc".equals(sortOrder)) {
                    sort = new Sort(Sort.Direction.ASC, sortName);
                } else {
                    sort = new Sort(Sort.Direction.DESC, sortName);
                }
            }
            if (searchParams.get("billNumber(TEXT)_LIKE") != null) {
                searchParams.put(("billNumber(TEXT)_LIKE"), searchParams.get("billNumber(TEXT)_LIKE").toString().trim());
            } else {
                searchParams.put("billNumber(TEXT)_LIKE", "");
            }
            if (searchParams.get("alias(TEXT)_LIKE") != null) {
                searchParams.put(("alias(TEXT)_LIKE"), searchParams.get("alias(TEXT)_LIKE").toString().trim());
            } else {
                searchParams.put("alias(TEXT)_LIKE", "");
            }
            if (searchParams.get("enumber(TEXT)_LIKE") != null) {
                searchParams.put(("enumber(TEXT)_LIKE"), searchParams.get("enumber(TEXT)_LIKE").toString().trim());
            } else {
                searchParams.put("enumber(TEXT)_LIKE", "");
            }
            if (searchParams.get("actor(TEXT)_LIKE") != null) {
                searchParams.put(("actor(TEXT)_LIKE"), searchParams.get("actor(TEXT)_LIKE").toString().trim());
            } else {
                searchParams.put("actor(TEXT)_LIKE", "");
            }
            if (searchParams.get("billstatus(TEXT)_LIKE") != null && !"-1".equals(searchParams.get("billstatus(TEXT)_LIKE"))) {
                searchParams.put("billstatus(TEXT)_EQ", searchParams.get("billstatus(TEXT)_LIKE"));
            }
            searchParams.remove("billstatus(TEXT)_LIKE");

            if (searchParams.get("billCategory(TEXT)_LIKE") != null && !"-1".equals(searchParams.get("billCategory(TEXT)_LIKE"))) {
                searchParams.put("billCategory(TEXT)_EQ", searchParams.get("billCategory(TEXT)_LIKE"));
            }
            searchParams.remove("billCategory(TEXT)_LIKE");

            Specification specification = DynamicSpecification.buildSpecification(searchParams);
            Pageable pageable = new PageRequest(pageNumber - 1, pageSize, sort);
            Page<WorkBills> billList = workBillRepository.findAll(specification, pageable);
            List<WorkBillDto> list = toDto(billList.getContent());
            PageImpl<WorkBillDto> billsPage = new PageImpl<WorkBillDto>(list, pageable, billList.getTotalElements());
            return APIResponse.success(billsPage);
        } catch (JuliException e) {
            return APIResponse.error(e.getMessage());
        }

    }


    public APIResponse getDto(String id)
    {
        WorkBills workBills=workBillRepository.findOne(id);
        WorkBillDto dto=new WorkBillDto().mapFrom(workBills);
        return  APIResponse.success(dto);
    }
    public APIResponse existElevator(String elevatorNumber)
    {
        try {
            User user = userService.getCurrentUser();
            Elevator elevator=new Elevator();
            if(user.getCompanyId()!=null){
                elevator=elevatorRepository.findByMaintainerIdAndNumber(user.getCompanyId(),elevatorNumber);
            }else
            {
                elevator = elevatorRepository.findByNumber(elevatorNumber);
            }

            if (elevator == null) {
                return APIResponse.success();
            } else {
                return APIResponse.error("");
            }
        }catch (JuliException e)
        {
            return APIResponse.error(e.getMessage());
        }
    }

    public APIResponse create(Integer actiontype,WorkBillDto workBillDto) throws Exception {
        WorkBills bills=workBillDto.mapTo();
        bills.setId(null);
        if(actiontype==1)
        {
            bills.setBillstatus(10);

            MaintenancePersonnel personnel=maintenanceRepository.findById(workBillDto.getMaintenanceId());
            if (personnel.getUuid() != null && personnel.getDeviceType() == 1) {
                PushKeyPair pair = new PushKeyPair(NetworkRescueAPIController.IOS_APIKEY, NetworkRescueAPIController.IOS_SECRETKEY);
                // 2. build a BaidupushClient object to access released interfaces
                BaiduPushClient pushClient = new BaiduPushClient(pair,
                        BaiduPushConstants.CHANNEL_REST_URL);

                // 3. register a YunLogHandler to get detail interacting information
                // in this request.
                pushClient.setChannelLogHandler(new YunLogHandler() {
                    @Override
                    public void onHandle(YunLogEvent event) {
                        System.out.println(event.getMessage());
                    }
                });
                if(personnel.getBadgeNumber()==null)
                    personnel.setBadgeNumber(0);
                JSONObject jo = new JSONObject();
                JSONObject jo1 = new JSONObject();
                jo1.put("alert", "一个新的工单,请查收");
                jo1.put("sound", "default");
                jo1.put("badge", personnel.getBadgeNumber()+1);
                jo.put("aps", jo1);
                jo.put("key1", "");
                jo.put("key2", "");
                pushSingleIOS(personnel, jo, pushClient);
                personnel.setBadgeNumber(personnel.getBadgeNumber()+1);
                maintenanceRepository.save(personnel);
            }
            else if(personnel.getUuid() != null && personnel.getDeviceType() == 2)
            {
                PushKeyPair pair = new PushKeyPair(NetworkRescueAPIController.ANDROID_APIKEY, NetworkRescueAPIController.ANDROID_SECRETKEY);
                // 2. build a BaidupushClient object to access released interfaces
                BaiduPushClient pushClient = new BaiduPushClient(pair,
                        BaiduPushConstants.CHANNEL_REST_URL);

                // 3. register a YunLogHandler to get detail interacting information
                // in this request.
                pushClient.setChannelLogHandler(new YunLogHandler() {
                    @Override
                    public void onHandle(YunLogEvent event) {
                        System.out.println(event.getMessage());
                    }
                });
                JSONObject jo = new JSONObject();
                JSONObject jo1 = new JSONObject();
                jo.put("title", "JULI_TEST");
                jo.put("description",  "一个新的工单,请查收");
                jo.put("notification_builder_id", 0);
                jo.put("notification_basic_style", 7);
                jo.put("open_type", 3);
                jo.put("custom_content", jo1.put("key", "value"));
                pushSingleAndroid(personnel,jo,pushClient);
            }
        }

        if(actiontype==2)
            bills.setBillstatus(5);
        setForeignFieldFromDto(bills, workBillDto);
        String billnumber=DateUtil.dateToString(new Date(),"yyyy-MM-dd")+"-"+(new Random().nextInt(89999)+10000);
        if(StringUtils.isNoneEmpty(workBillDto.getElevatorNumber())){
            Elevator elevator = elevatorRepository.findByNumber(workBillDto.getElevatorNumber().trim());
            if(elevator!=null){
                bills.setAlias(elevator.getAlias());
                bills.setEnumber(elevator.getNumber());
                bills.setElevator(elevator);
            }
        }
        if (StringUtils.isNotEmpty(workBillDto.getMaintenanceId())) {
            MaintenancePersonnel maintenancePersonnel = maintenanceRepository.findOne(workBillDto.getMaintenanceId());
            if(maintenancePersonnel!=null){
                bills.setActor(maintenancePersonnel.getName());
            }
        }
        if(workBillDto.getBillModel()!= null && workBillDto.getBillModel() <= 30) {
            bills.setBillNumber("WB"+ billnumber);
            bills.setBillCategory(20);//维保
        }
        else if(workBillDto.getBillModel()!= null && workBillDto.getBillModel() == 40) {
            bills.setBillNumber("JX" + billnumber);
            bills.setBillCategory(30);//急修
            if(StringUtils.isNoneEmpty(workBillDto.getElevatorNumber())){
                Elevator elevator = elevatorRepository.findByNumber(workBillDto.getElevatorNumber().trim());
                if(elevator!=null){
                    elevator.setFaultStatus(ElevatorFaultStatus.MALFUNCTION.getCode());
                    elevator.setFaultTime(new Date());
                    elevator.setFaultCode("人为报障");
                    elevator.setIsHandled(FaultHandledStatus.HANDLED.getCode());
                    elevatorRepository.save(elevator);
                }
            }

        }
        else {
            bills.setBillNumber("WX" + billnumber);
            bills.setBillCategory(10);//维修
            if(StringUtils.isNoneEmpty(workBillDto.getElevatorNumber())){
                Elevator elevator = elevatorRepository.findByNumber(workBillDto.getElevatorNumber().trim());
                if(elevator!=null){
                    elevator.setIsHandled(FaultHandledStatus.HANDLED.getCode());
                    elevatorRepository.save(elevator);
                }
            }
        }
        bills.setBillModel(workBillDto.getBillModel());
        workBillRepository.save(bills);
        return APIResponse.success();
    }

    public APIResponse update(String id,Integer actiontype,WorkBillDto workBillDto) throws InvocationTargetException, IllegalAccessException
    {
        WorkBills existingEntity = workBillRepository.findOne(id);
        if (existingEntity == null) {
            return APIResponse.error("不存在此对象：" + id);
        }
        BeanUtils.copyProperties(existingEntity, workBillDto);
        setForeignFieldFromDto(existingEntity, workBillDto);
        if(actiontype==1)
        {
            existingEntity.setBillstatus(10);
        }
        if(actiontype==2)
        {
            existingEntity.setBillstatus(5);
        }
        if(StringUtils.isNoneEmpty(workBillDto.getElevatorNumber())){
            Elevator elevator = elevatorRepository.findByNumber(workBillDto.getElevatorNumber().trim());
            if(elevator!=null){
                existingEntity.setAlias(elevator.getAlias());
                existingEntity.setEnumber(elevator.getNumber());
            }
        }
        if (StringUtils.isNotEmpty(workBillDto.getMaintenanceId())) {
            MaintenancePersonnel maintenancePersonnel = maintenanceRepository.findOne(workBillDto.getMaintenanceId());
            if(maintenancePersonnel!=null){
                existingEntity.setActor(maintenancePersonnel.getName());
            }
        }
        workBillRepository.save(existingEntity);
        return APIResponse.success();
    }

    //正常工单派发
    public APIResponse sendBill(String id) throws PushClientException, PushServerException {
        WorkBills existingEntity = workBillRepository.findOne(id);
        if (existingEntity == null) {
            return APIResponse.error("不存在此对象：" + id);
        }
        existingEntity.setBillstatus(10);//设置工单为待接收（待确认）
        workBillRepository.save(existingEntity);

        MaintenancePersonnel personnel=existingEntity.getMaintenancePersonnel();
        if (personnel.getUuid() != null && personnel.getDeviceType() == 1) {

            //推送工单消息
            PushKeyPair pair = new PushKeyPair(NetworkRescueAPIController.IOS_APIKEY, NetworkRescueAPIController.IOS_SECRETKEY);
            // 2. build a BaidupushClient object to access released interfaces
            BaiduPushClient pushClient = new BaiduPushClient(pair,
                    BaiduPushConstants.CHANNEL_REST_URL);

            // 3. register a YunLogHandler to get detail interacting information
            // in this request.
            pushClient.setChannelLogHandler(new YunLogHandler() {
                @Override
                public void onHandle(YunLogEvent event) {
                    System.out.println(event.getMessage());
                }
            });
            if(personnel.getBadgeNumber()==null)
                personnel.setBadgeNumber(0);
            JSONObject jo = new JSONObject();
            JSONObject jo1 = new JSONObject();
            jo1.put("alert", "一个新的工单,请查收");
            jo1.put("sound", "default");
            jo1.put("badge", personnel.getBadgeNumber()+1);
            jo.put("aps", jo1);
            jo.put("key1", "");
            jo.put("key2", "");
            pushSingleIOS(personnel, jo, pushClient);
            personnel.setBadgeNumber(personnel.getBadgeNumber()+1);
            maintenanceRepository.save(personnel);
        }
        else if(personnel.getUuid() != null && personnel.getDeviceType() == 2)
        {
            //推送工单消息
            PushKeyPair pair = new PushKeyPair(NetworkRescueAPIController.ANDROID_APIKEY, NetworkRescueAPIController.ANDROID_SECRETKEY);
            // 2. build a BaidupushClient object to access released interfaces
            BaiduPushClient pushClient = new BaiduPushClient(pair,
                    BaiduPushConstants.CHANNEL_REST_URL);

            // 3. register a YunLogHandler to get detail interacting information
            // in this request.
            pushClient.setChannelLogHandler(new YunLogHandler() {
                @Override
                public void onHandle(YunLogEvent event) {
                    System.out.println(event.getMessage());
                }
            });
            JSONObject jo = new JSONObject();
            JSONObject jo1 = new JSONObject();
            jo.put("title", "JULI_TEST");
            jo.put("description",  "一个新的工单,请查收");
            jo.put("notification_builder_id", 0);
            jo.put("notification_basic_style", 7);
            jo.put("open_type", 3);
            jo.put("custom_content", jo1.put("key", "value"));
            pushSingleAndroid(personnel,jo,pushClient);
        }
        return  APIResponse.success();

    }

    //拒绝工单改派
    public APIResponse sendRefuseBill(String id,Integer actiontype,WorkBillDto workBillDto) throws Exception {
        WorkBills workBills=workBillRepository.findOne(id);
        workBills.setBillstatus(45);//设置拒绝工单已处理
        workBillRepository.save(workBills);
        this.create(actiontype, workBillDto);
        return  APIResponse.success();

    }
    public void setForeignFieldFromDto(WorkBills entity, WorkBillDto dto) {
        if (StringUtils.isNotEmpty(dto.getElevatorId())) {
            Elevator elevator = elevatorRepository.findOne(dto.getElevatorId());
            if(elevator!=null){
                entity.setElevator(elevator);
                entity.setAlias(elevator.getAlias());
                entity.setEnumber(elevator.getNumber());
            }
        } else if(StringUtils.isNotEmpty(dto.getElevatorNumber())){
            Elevator elevator = elevatorRepository.findByNumber(dto.getElevatorNumber().trim());
            if(elevator!=null){
                entity.setElevator(elevator);
                entity.setAlias(elevator.getAlias());
                entity.setEnumber(elevator.getNumber());
            }
        }else{
            entity.setElevator(null);
        }
        if (StringUtils.isNotEmpty(dto.getMaintenanceId())) {
            MaintenancePersonnel maintenancePersonnel = maintenanceRepository.findOne(dto.getMaintenanceId());
            if(maintenancePersonnel!=null){
                entity.setMaintenancePersonnel(maintenancePersonnel);
                entity.setActor(maintenancePersonnel.getName());
            }
        } else {
            entity.setMaintenancePersonnel(null);
        }
        if (StringUtils.isNotEmpty(dto.getAfterBillId())) {
            entity.setAfterbill(workBillRepository.findOne(dto.getAfterBillId()));
        } else {
            entity.setAfterbill(null);
        }

    }
    public String exportExcel(Date startTime1, Date startTime2, Integer billCateory, String elevatorNumber, HttpServletResponse response) throws IOException {
        List<WorkBills> billList = new ArrayList<>();
        if(startTime1!=null&&startTime2!=null&&StringUtils.isEmpty(elevatorNumber)){
            billList = workBillRepository.findBillsByTime(startTime1, startTime2, billCateory);
        }else if(startTime1!=null&&startTime2!=null&&StringUtils.isNotEmpty(elevatorNumber)){
            billList = workBillRepository.findBillsByTimeAndElevator(startTime1, startTime2, elevatorNumber, billCateory);
        }else if(startTime1==null&&startTime2==null&&StringUtils.isNotEmpty(elevatorNumber)){
            billList=workBillRepository.findBillsByElevator(elevatorNumber, billCateory);
        }
        List<WorkBillDto> list=toDto(billList);

        String title="工单信息";
        String headers[]={"工单编号","电梯","工单类型","故障描述","保障人员","处理于","开始时间","现场描述","采取措施","故障性质","处理结果","维修人员","进入时间","完成时间","保养名称","保养性质","后续工单"};
        String filename="bills.xls";
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + filename);       //指定下载的文件名
        OutputStream output = response.getOutputStream();
        HSSFWorkbook work=new HSSFWorkbook();
        exportWorkBillExcel(work, title, headers, list);
        work.write(output);
        output.flush();
        output.close();
        return  null;
    }
    //图片上传接口
    public  APIResponse commitReportPicture(HttpServletRequest request1,String id,Integer index) throws IOException {

        WorkBills workBills= workBillRepository.findOne(id);
        StandardMultipartHttpServletRequest request=(StandardMultipartHttpServletRequest)request1;
        List<MultipartFile> fileList = request.getMultiFileMap().get("file");
        for (MultipartFile file : fileList) {
            String pic_path = "C:/juliImage/image/wb/";
            File target = new File(pic_path);
            if (!target.exists()) {
                target.mkdir();
            }
            String originalFilename = file.getOriginalFilename();
            if (originalFilename != "") {
                String newFile_name = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
                File newFile = new File(pic_path + newFile_name);
                try {
                    file.transferTo(newFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (index == 1) {
                    workBills.setPicture1(newFile_name);
                }
                if (index == 2) {
                    workBills.setPicture2(newFile_name);
                }
                if (index == 3) {
                    workBills.setPicture3(newFile_name);
                }
                if (index == 4) {
                    workBills.setPicture4(newFile_name);
                }
                if (index == 5) {
                    workBills.setPicture5(newFile_name);
                }
            }
        }
        workBillRepository.save(workBills);
        return APIResponse.success();
    }
    //返回当前员工的未处理的工单数量
    public APIResponse getCountByStatus(Integer type,String id)
    {
        MaintenancePersonnel maintenancePersonnel=maintenanceRepository.findOne(id);
        long count=workBillRepository.countByAndBillCategoryAndBillstatusAndMaintenancePersonnel(type, 10, maintenancePersonnel);
        return APIResponse.success(count);
    }
    public List<WorkBillDto> toDto(List<WorkBills> workBillsList)
    {
        List<WorkBillDto> dtos=new ArrayList<>();
        for(WorkBills bills:workBillsList)
        {
            WorkBillDto dto=new WorkBillDto();
//            dto=new WorkBillDto().mapFrom(bills);尽量不要用这种方法，影响加载速度
            dto.setId(bills.getId());
            dto.setAlias(bills.getAlias());
            dto.setBillNumber(bills.getBillNumber());
            dto.setBillModel(bills.getBillModel());
            dto.setAssists(bills.getAssists());
            dto.setBillCategory(bills.getBillCategory());
            dto.setBillstatus(bills.getBillstatus());
            dto.setMaintenanceName(bills.getActor());
            if(bills.getMaintenancePersonnel()!=null)
            {
                dto.setMaintenanceId(bills.getMaintenancePersonnel().getId());
                dto.setMaintainName(bills.getMaintenancePersonnel().getName() + "/" + bills.getMaintenancePersonnel().getNumber());
            }
            dto.setAlias(bills.getAlias());
            dto.setElevatorNumber(bills.getEnumber());
            try {
                if(bills.getStartTime()!=null){
                    dto.setStartTimeStr(DateUtil.dateToFullString(bills.getStartTime()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(bills.getAfterbill()!=null) {
                dto.setAfterBillId(bills.getAfterbill().getId());
               // dto.setAfterBillName(bills.getAfterbill().getBillNumber());
//                dto.setAfterBillName(bills.getAfterbill().getBillNumber());
            }
            if(bills.getElevator()!=null)
            {
                dto.setElevatorId(bills.getElevator().getId());
            }
            dtos.add(dto);
        }
        return dtos;
    }
    public  void exportWorkBillExcel  ( HSSFWorkbook workbook,String title, String[] headers,List<WorkBillDto> dataset) throws IOException
    {
        // 声明一个工作薄
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth(15);


        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++)
        {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        int index = 0;
        for(WorkBillDto dto:dataset)
        {
            index++;
            row = sheet.createRow(index);
            //
            HSSFCell cellNumber= row.createCell(0);
            HSSFRichTextString number= new HSSFRichTextString(dto.getBillNumber());
            cellNumber.setCellValue(number);
            //电梯
            HSSFCell cellElevator= row.createCell(1);
            HSSFRichTextString elevatorNumber= new HSSFRichTextString(dto.getElevatorNumber());
            cellElevator.setCellValue(elevatorNumber);
            //工单类型
            HSSFCell cell2= row.createCell(2);
            HSSFRichTextString name2= new HSSFRichTextString(BillReportCategory.get(dto.getBillCategory()).getName());
            cell2.setCellValue(name2);
            //故障描述
            HSSFCell cell3= row.createCell(3);
            HSSFRichTextString name3= new HSSFRichTextString(dto.getFaultDescription());
            cell3.setCellValue(name3);
            // 保障人员
            HSSFCell cell4= row.createCell(4);
            HSSFRichTextString name4= new HSSFRichTextString(dto.getFaultPerson());
            cell4.setCellValue(name4);
            // 处理于
            HSSFCell cell5= row.createCell(5);
            HSSFRichTextString name5= new HSSFRichTextString(dto.getServiceIn());
            cell5.setCellValue(name5);
            // 开始时间
            HSSFCell cell6= row.createCell(6);
            HSSFRichTextString name6= new HSSFRichTextString(dto.getStartTime()!=null?dto.getStartTime().toString():"");
            cell6.setCellValue(name6);
            // 现场描述
            HSSFCell cell7= row.createCell(7);
            HSSFRichTextString name7= new HSSFRichTextString(dto.getLocalDescription());
            cell7.setCellValue(name7);
            // 采取措施
            HSSFCell cell8= row.createCell(8);
            HSSFRichTextString name8= new HSSFRichTextString(dto.getTakeSteps());
            cell8.setCellValue(name8);
            // 故障性质
            HSSFCell cell9= row.createCell(9);
            HSSFRichTextString name9= new HSSFRichTextString(dto.getFaultQuality());
            cell9.setCellValue(name9);
            // 处理结果
            HSSFCell cell10= row.createCell(10);
            HSSFRichTextString name10= new HSSFRichTextString(dto.getResult());
            cell10.setCellValue(name10);
            // 维修人员
            HSSFCell cell11= row.createCell(11);
            HSSFRichTextString name11= new HSSFRichTextString(dto.getMaintenanceName());
            cell11.setCellValue(name11);
            // 进入时间
            HSSFCell cell12 = row.createCell(12);
            HSSFRichTextString name12= new HSSFRichTextString(dto.getEnterTime()!=null?dto.getEnterTime().toString():"");
            cell12.setCellValue(name12);
            // 完成时间
            HSSFCell cell13= row.createCell(13);
            HSSFRichTextString name13= new HSSFRichTextString(dto.getCompleteTime()!=null?dto.getCompleteTime().toString():"");
            cell13.setCellValue(name13);
            // 保养名称
            HSSFCell cell14= row.createCell(14);
            HSSFRichTextString name14= new HSSFRichTextString(dto.getMaintainName());
            cell14.setCellValue(name14);
            // 保养性质
            HSSFCell cell15= row.createCell(15);
            HSSFRichTextString name15= new HSSFRichTextString(  MaintainProperty.get(dto.getMaintainProperty()!=null?10:20).getName());
            cell15.setCellValue(name15);

            //后续工单
            HSSFCell cell16= row.createCell(16);
            HSSFRichTextString name16= new HSSFRichTextString(dto.getAfBName());
            cell16.setCellValue(name16);

        }

    }


    public Page<WorkBills> getByCategoryAndStatus(Pageable pageable) {

        Page<WorkBills> workBillses = workBillRepository.findByBillCategoryNotAndBillstatusLessThan(BillReportCategory.MAINTAIN.getCode(), BillStatus.REFUSE.getCode(), pageable);
//        List<WorkBills> workBillsList = workBillses.getContent();
//
//        for(WorkBills wb : workBillsList){
//            if (wb.getElevator().getFaultStatus() == ElevatorFaultStatus.MALFUNCTION.getCode() || wb.getElevator().getIsHandled() == FaultHandledStatus.HANDLED.getCode())
//        }


        return  workBillses;

    }


    //单设备Android 推送
    public void pushSingleAndroid(MaintenancePersonnel personnel,JSONObject jsonObject,BaiduPushClient pushClient)throws PushClientException, PushServerException
    {
        try
        {
                PushMsgToSingleDeviceRequest request = new PushMsgToSingleDeviceRequest()
                        .addChannelId(personnel.getUuid())
                        .addMsgExpires(new Integer(3600))//相对于当前时间的消息过期时间，单位为秒
                        .addMessageType(1)//0：透传消息 1：通知 默认值为0
                        .addMessage(jsonObject.toJSONString()) //添加透传消息
                                //.addSendTime(System.currentTimeMillis() / 1000 + 120) // 设置定时推送时间，必需超过当前时间一分钟，单位秒.实例2分钟后推送
                        .addDeviceType(3);//Android

                // 5. http request
                PushMsgToSingleDeviceResponse response = pushClient.
                        pushMsgToSingleDevice(request);
                // Http请求结果解析打印
                System.out.println("msgId: " + response.getMsgId()
                        + ",sendTime: " + response.getSendTime());
        }
        catch(PushClientException e) {
            if (BaiduPushConstants.ERROROPTTYPE) {
                throw e;
            } else {
                e.printStackTrace();
            }
        } catch (PushServerException e) {
            if (BaiduPushConstants.ERROROPTTYPE) {
                throw e;
            } else {
                System.out.println(String.format(
                        "requestId: %d, errorCode: %d, errorMessage: %s",
                        e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
            }
        }
    }
    //单设备IOS推送
    public void pushSingleIOS(MaintenancePersonnel personnel,JSONObject jsonObject,BaiduPushClient pushClient)throws PushClientException, PushServerException
    {
        try
        {

                    PushMsgToSingleDeviceRequest request = new PushMsgToSingleDeviceRequest()
                            .addChannelId(personnel.getUuid())
                            .addMsgExpires(new Integer(3600))//相对于当前时间的消息过期时间，单位为秒
                            .addMessageType(1)//0：透传消息 1：通知 默认值为0
                            .addMessage(jsonObject.toJSONString()) //添加透传消息
                            .addDeployStatus(2)
                                    //.addSendTime(System.currentTimeMillis() / 1000 + 120) // 设置定时推送时间，必需超过当前时间一分钟，单位秒.实例2分钟后推送
                            .addDeviceType(4);//IOS

                    // 5. http request
                    PushMsgToSingleDeviceResponse response = pushClient.
                            pushMsgToSingleDevice(request);
                    // Http请求结果解析打印
// Http请求结果解析打印
                    System.out.println("msgId: " + response.getMsgId()
                            + ",sendTime: " + response.getSendTime());
        }catch (PushClientException e) {
            if (BaiduPushConstants.ERROROPTTYPE) {
                throw e;
            } else {
                e.printStackTrace();
            }
        } catch (PushServerException e) {
            if (BaiduPushConstants.ERROROPTTYPE) {
                throw e;
            } else {
                System.out.println(String.format(
                        "requestId: %d, errorCode: %d, errorMessage: %s",
                        e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
            }
        }

    }

}