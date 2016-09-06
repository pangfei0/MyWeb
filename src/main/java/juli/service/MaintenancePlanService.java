package juli.service;

import juli.api.core.APIResponse;
import juli.api.dto.MaintenancePlanDto;
import juli.domain.*;
import juli.domain.enums.ElevatorFaultStatus;
import juli.domain.enums.FaultHandledStatus;
import juli.infrastructure.DateUtil;
import juli.infrastructure.ServletUtil;
import juli.infrastructure.persist.DynamicSpecification;
import juli.repository.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import javax.servlet.ServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MaintenancePlanService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    MaintenancePlanRepository maintenancePlanRepository;

    @Autowired
    ElevatorRepository elevatorRepository;

    @Autowired
    MaintenanceRepository maintenanceRepository;

    @Autowired
    WorkBillRepository workBillRepository;
    @Autowired
    ReportRepository reportRepository;

    public APIResponse newsearch(ServletRequest request,Integer pageNumber,Integer pageSize)
    {
        Map<String, Object> searchParams = ServletUtil.getParametersStartingWith(request, "search_");

        //过滤查询条件
        //电梯
        if(searchParams.get("number(TEXT)_LIKE")!=null){
            searchParams.put(("number(TEXT)_LIKE"),searchParams.get("number(TEXT)_LIKE").toString().trim());
        }else
        {
            searchParams.put("number(TEXT)_LIKE","");
        }

        //工单编号
        if(searchParams.get("workBillNumber(TEXT)_LIKE")!=null){
            searchParams.put(("workBillNumber(TEXT)_LIKE"),searchParams.get("workBillNumber(TEXT)_LIKE").toString().trim());
        }else
        {
            searchParams.put("workBillNumber(TEXT)_LIKE","");
        }

        //合同编号
        if(searchParams.get("upkeepContractNumber(TEXT)_LIKE")!=null){
            searchParams.put(("upkeepContractNumber(TEXT)_LIKE"),searchParams.get("upkeepContractNumber(TEXT)_LIKE").toString().trim());
        }else
        {
            searchParams.put("upkeepContractNumber(TEXT)_LIKE","");
        }

        //维保人
        if(searchParams.get("maintenanceMan(TEXT)_LIKE")!=null){
            searchParams.put(("maintenanceMan(TEXT)_LIKE"),searchParams.get("maintenanceMan(TEXT)_LIKE").toString().trim());
        }else
        {
            searchParams.put("maintenanceMan(TEXT)_LIKE","");
        }

        //计划时间
//        if(searchParams.get("actor(TEXT)_LIKE")!=null){
//            searchParams.put(("actor(TEXT)_LIKE"),searchParams.get("actor(TEXT)_LIKE").toString().trim());
//        }else
//        {
//            searchParams.put("actor(TEXT)_LIKE","");
//        }

        //状态
        if(searchParams.get("status(TEXT)_LIKE")!=null&&!"-1".equals(searchParams.get("status(TEXT)_LIKE"))){
            searchParams.put("status(TEXT)_EQ", searchParams.get("status(TEXT)_LIKE"));
        }
        searchParams.remove("status(TEXT)_LIKE");

        //状态
        if(searchParams.get("planType(TEXT)_LIKE")!=null&&!"-1".equals(searchParams.get("planType(TEXT)_LIKE"))){
            searchParams.put("planType(TEXT)_EQ", searchParams.get("planType(TEXT)_LIKE"));
        }
        searchParams.remove("planType(TEXT)_LIKE");

        //排序设置
        Sort sort = null;
        String sortName = request.getParameter("sortName");
        String sortOrder = request.getParameter("sortOrder");
        if(sortName==null){
            sort = new Sort(Sort.Direction.DESC, "createdDate");
        }else{
            if("asc".equals(sortOrder)){
                sort = new Sort(Sort.Direction.ASC, sortName);
            }else{
                sort = new Sort(Sort.Direction.DESC, sortName);
            }

        }
        Specification specification = DynamicSpecification.buildSpecification(searchParams);
        Pageable pageable = new PageRequest(pageNumber - 1, pageSize, sort);
        Page<MaintenancePlan> maintenancePlans=maintenancePlanRepository.findAll(specification, pageable);
        List<MaintenancePlanDto> list= null;
        try {
            list = toDto(maintenancePlans.getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
        PageImpl<MaintenancePlanDto> billsPage=new PageImpl<MaintenancePlanDto>(list,pageable,maintenancePlans.getTotalElements());
        return  APIResponse.success(billsPage);
    }


    private List<MaintenancePlanDto> toDto(List<MaintenancePlan> maintenancePlanList) throws Exception {
        List<MaintenancePlanDto> dtos=new ArrayList<>();
        for(MaintenancePlan maintenancePlan:maintenancePlanList) {
            MaintenancePlanDto dto =new MaintenancePlanDto();
            dto.setId(maintenancePlan.getId());
            if(null!=maintenancePlan.getActualTime()){
                dto.setActualTime(DateUtil.dateToFullString3(maintenancePlan.getActualTime()));
            }
            dto.setCreateBillTime(maintenancePlan.getCreateBillTime());
            dto.setStatus(maintenancePlan.getStatus());
            dto.setUpkeepContractId(maintenancePlan.getUpkeepContractId());
            dto.setUpkeepContractNumber(maintenancePlan.getUpkeepContractNumber());
            dto.setElevatorId(maintenancePlan.getElevatorId());
            dto.setNumber(maintenancePlan.getNumber());
            dto.setWorkBillId(maintenancePlan.getWorkBillId());
            dto.setPlanType(maintenancePlan.getPlanType());
            if(null!=maintenancePlan.getPlanTime()){
                dto.setPlanTime(maintenancePlan.getPlanTime());
            }
            if(null!=maintenancePlan.getPlanEndTime()){
                dto.setPlanEndTime(maintenancePlan.getPlanEndTime());
            }
            dto.setCreateBillTime(maintenancePlan.getCreateBillTime());
            dto.setMaintenanceMan(maintenancePlan.getMaintenanceMan());
            dto.setWorkBillNumber(maintenancePlan.getWorkBillNumber());
            dto.setCirculationTime("0");//todo 这个流转时间待续
            dtos.add(dto);
        }
        return dtos;
    }


    public APIResponse findById(String id){
        MaintenancePlanDto maintenancePlanDto = null;
        MaintenancePlan maintenancePlan = maintenancePlanRepository.findOne(id);
        if(null!=maintenancePlan){
            try {
                maintenancePlanDto =  toDTOS(maintenancePlan);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return APIResponse.success(maintenancePlanDto);
    }

    private MaintenancePlanDto toDTOS(MaintenancePlan maintenancePlan) throws Exception {
        MaintenancePlanDto dto =new MaintenancePlanDto();
        dto.setId(maintenancePlan.getId());
        if(null!=maintenancePlan.getActualTime()){
            dto.setActualTime(DateUtil.dateToFullString3(maintenancePlan.getActualTime()));
        }
        dto.setCreateBillTime(maintenancePlan.getCreateBillTime());
        dto.setStatus(maintenancePlan.getStatus());
        dto.setUpkeepContractId(maintenancePlan.getUpkeepContractId());
        dto.setUpkeepContractNumber(maintenancePlan.getUpkeepContractNumber());
        dto.setElevatorId(maintenancePlan.getElevatorId());
        dto.setNumber(maintenancePlan.getNumber());
        dto.setWorkBillId(maintenancePlan.getWorkBillId());
        if(null!=maintenancePlan.getPlanTime()){
            dto.setPlanTime(maintenancePlan.getPlanTime());
        }
        if(null!=maintenancePlan.getPlanEndTime()){
            dto.setPlanEndTime(maintenancePlan.getPlanEndTime());
        }
        dto.setCreateBillTime(maintenancePlan.getCreateBillTime());
        MaintenancePersonnel maintenancePersonnel = maintenanceRepository.findByName(maintenancePlan.getMaintenanceMan());
        if(null!=maintenancePersonnel){
            dto.setMaintenanceMan(maintenancePlan.getMaintenanceMan());
            dto.setMaintenanceManId(maintenancePersonnel.getId());
        }
        dto.setWorkBillNumber(maintenancePlan.getWorkBillNumber());
        dto.setPlanType(maintenancePlan.getPlanType());
        dto.setCirculationTime("0");//todo 这个流转时间待续
        dto.setContent(maintenancePlan.getContent());
        return dto;
    }


    public APIResponse addMaintenancePlan(MaintenancePlanDto maintenancePlanDto){
        if(StringUtils.isNotEmpty(maintenancePlanDto.getNumber())&&StringUtils.isNotEmpty(maintenancePlanDto.getMaintenanceManId())){
            MaintenancePlan maintenancePlan = new MaintenancePlan();
            Elevator elevator = elevatorRepository.findByNumber(maintenancePlanDto.getNumber());
            maintenancePlan.setNumber(maintenancePlanDto.getNumber());
            if(null!=elevator){
                maintenancePlan.setElevatorId(elevator.getId());
            }
            try {
                maintenancePlan.setPlanTime(maintenancePlanDto.getPlanTime());
                maintenancePlan.setPlanEndTime(maintenancePlanDto.getPlanEndTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
            maintenancePlan.setPlanType(maintenancePlanDto.getPlanType());
            maintenancePlan.setStatus(10);
            if(StringUtils.isNotEmpty(maintenancePlanDto.getMaintenanceManId())){
                MaintenancePersonnel maintenancePersonnel = maintenanceRepository.findOne(maintenancePlanDto.getMaintenanceManId());
                if(null!=maintenancePersonnel){
                    maintenancePlan.setMaintenanceMan(maintenancePersonnel.getName());
                }
            }
            maintenancePlan.setUpkeepContractNumber("手动生成");
            maintenancePlanRepository.save(maintenancePlan);
        }
        return APIResponse.success();
    }

    public APIResponse updateMaintenancePlan(String id,MaintenancePlanDto maintenancePlanDto){
        MaintenancePlan maintenancePlan = null;
        if(StringUtils.isNotEmpty(id)){
             maintenancePlan =  maintenancePlanRepository.findOne(id);
        }
        if(maintenancePlan!=null&&StringUtils.isNotEmpty(maintenancePlanDto.getNumber())&&StringUtils.isNotEmpty(maintenancePlanDto.getMaintenanceManId())){
            Elevator elevator = elevatorRepository.findByNumber(maintenancePlanDto.getNumber());
            maintenancePlan.setNumber(maintenancePlanDto.getNumber());
            if(null!=elevator){
                maintenancePlan.setElevatorId(elevator.getId());
            }
            try {
                maintenancePlan.setPlanTime(maintenancePlanDto.getPlanTime());
                maintenancePlan.setPlanEndTime(maintenancePlanDto.getPlanEndTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
            maintenancePlan.setPlanType(maintenancePlanDto.getPlanType());
//            maintenancePlan.setStatus(10);
            if(StringUtils.isNotEmpty(maintenancePlanDto.getMaintenanceManId())){
                MaintenancePersonnel maintenancePersonnel = maintenanceRepository.findOne(maintenancePlanDto.getMaintenanceManId());
                if(null!=maintenancePersonnel){
                    maintenancePlan.setMaintenanceMan(maintenancePersonnel.getName());
                    //将还未执行的计划对应的维保人修改
                    List<MaintenancePlan> maintenancePlanList = maintenancePlanRepository.findByNumberAndStatus(maintenancePlanDto.getNumber(),10);
                    if(CollectionUtils.isNotEmpty(maintenancePlanList)){
                        for(MaintenancePlan m:maintenancePlanList){
                            m.setMaintenanceMan(maintenancePersonnel.getName());
                            maintenancePlanRepository.save(m);
                        }
                    }
                }
            }
            maintenancePlanRepository.save(maintenancePlan);
        }
        return APIResponse.success();
    }

    public APIResponse deleteMaintenancePlan(String id){
        if(StringUtils.isNotEmpty(id)){
            MaintenancePlan mp=maintenancePlanRepository.findOne(id);
            String number=mp.getWorkBillId();//工单id
            if(null!=number){
                WorkBills workBills=workBillRepository.findOne(number);

                if(workBills.getBillstatus()< 50){//已完成的不能删除
                    if(workBills.getBillstatus()==20)
                    {
                        workBillRepository.updateCurrentBill(number);
                    }
                    workBillRepository.delete(workBills);
                }
//                if(workBills.getBillstatus()>=50)
//                {
//                    List<Reports> report= reportRepository.findReportsByWorkBillId(number);
//                    reportRepository.delete(report);
//                }
            }
            maintenancePlanRepository.delete(id);
        }
        return APIResponse.success();
    }

    /**
     * 自动生成工单
     */
    public void generateWorkBillByDate(){
        //获取时间
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        try
        {
            cal.setTime(date);
//            cal.add(cal.DATE, 1);
//            DateFormat.getDateInstance().format(new Date());
//            java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd");
            List<MaintenancePlan> maintenancePlanList = maintenancePlanRepository.findByPlanTimeAndStatus(cal.getTime(), 10);
            logger.info("时间："+cal.getTime());
            //生成工单
            if(CollectionUtils.isNotEmpty(maintenancePlanList)){
                for(MaintenancePlan maintenancePlan:maintenancePlanList){
                    logger.info("自动生成工单开始");
                    generaterWorkbill(maintenancePlan);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private void generaterWorkbill(MaintenancePlan maintenancePlan) throws Exception {
        if(null!=maintenancePlan){
            WorkBills bills= new WorkBills();
            bills.setId(null);
            bills.setBillstatus(10);
            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
            String billnumber=DateUtil.dateToString( DateUtil.toSomeDay( df.format(new Date()),0),"yyyy-MM-dd")+"-"+(new Random().nextInt(89999)+10000);
            if(StringUtils.isNotEmpty(maintenancePlan.getNumber())){
                Elevator elevator = elevatorRepository.findOne(maintenancePlan.getElevatorId().trim());
                if(elevator!=null){
                    bills.setAlias(elevator.getAlias());
                    bills.setEnumber(elevator.getNumber());
                    bills.setElevator(elevator);
                }
            }
            if (StringUtils.isNotEmpty(maintenancePlan.getMaintenanceMan())) {
                MaintenancePersonnel maintenancePersonnel = maintenanceRepository.findByName(maintenancePlan.getMaintenanceMan().trim());
                if(maintenancePersonnel!=null){
                    bills.setActor(maintenancePersonnel.getName());
                    bills.setMaintenancePersonnel(maintenancePersonnel);
                }
            }
            bills.setBillNumber("WB"+ billnumber);
            bills.setBillCategory(20);//维保
            if(maintenancePlan.getPlanType()!= null && maintenancePlan.getPlanType() == 10) {
                bills.setBillModel(0);
            }
            if(maintenancePlan.getPlanType()!= null && maintenancePlan.getPlanType() == 20) {
                bills.setBillModel(10);
            }
            if(maintenancePlan.getPlanType()!= null && maintenancePlan.getPlanType() == 30) {
                bills.setBillModel(20);
            }
            if(maintenancePlan.getPlanType()!= null && maintenancePlan.getPlanType() == 40) {
                bills.setBillModel(30);
            }
            WorkBills workBills = workBillRepository.save(bills);
            logger.info("生成的工单编号是："+workBills.getBillNumber()+",电梯工号是："+workBills.getEnumber());
            maintenancePlan.setWorkBillNumber("WB" + billnumber);
            maintenancePlan.setWorkBillId(workBills.getId());
            maintenancePlan.setCreateBillTime(new Date());
            maintenancePlan.setStatus(20);
            maintenancePlanRepository.save(maintenancePlan);
        }
    }


    /**
     * 根据维保人id查询该维保人的所有计划
     * @param id
     * @return
     */
    public APIResponse getPlanByMaintenancePersonnel(String id,Date beginTime,Date endTime){
        List<MaintenancePlanDto> maintenancePlanDtoList = new ArrayList<>();
        MaintenancePersonnel maintenancePersonnel = maintenanceRepository.findOne(id);
        if(null!=maintenancePersonnel&&StringUtils.isNotEmpty(maintenancePersonnel.getName())){
            List<MaintenancePlan> maintenancePlanList = maintenancePlanRepository.findByMaintenanceManAndPlanTimeGreaterThanAndPlanEndTimeLessThan(maintenancePersonnel.getName(), beginTime, endTime);
            if(CollectionUtils.isNotEmpty(maintenancePlanList)){
                for(MaintenancePlan m:maintenancePlanList){
                    try {
                        maintenancePlanDtoList.add(toDTOS(m));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        Map<String,Object> billMap;
        List<Map>  mapList=new ArrayList<>();
        if(CollectionUtils.isNotEmpty(maintenancePlanDtoList)){
            for(MaintenancePlanDto maintenancePlanDto:maintenancePlanDtoList)
            {
                billMap=new HashMap<String,Object>();
                billMap.put("id", maintenancePlanDto.getId());//id
                billMap.put("upkeepContractId", maintenancePlanDto.getUpkeepContractId());//合同id
                billMap.put("upkeepContractNumber",maintenancePlanDto.getUpkeepContractNumber());//合同编码
                billMap.put("elevatorId", maintenancePlanDto.getElevatorId());//电梯id
                billMap.put("number", maintenancePlanDto.getNumber());//电梯工号
                billMap.put("workBillId", maintenancePlanDto.getWorkBillId());//工单id
                billMap.put("workBillNumber", maintenancePlanDto.getWorkBillNumber());//工单编号
                billMap.put("maintenanceMan", maintenancePlanDto.getMaintenanceMan());//维保人姓名
                billMap.put("maintenanceManId", maintenancePlanDto.getMaintenanceManId());//维保人id
                billMap.put("status", maintenancePlanDto.getStatus());//状态   10：未开始   20：已开始  30：已完成
                billMap.put("planTime", maintenancePlanDto.getPlanTime());//开始时间
                billMap.put("planEndTime", maintenancePlanDto.getPlanEndTime());//结束时间
                billMap.put("actualTime", maintenancePlanDto.getActualTime());//实际完成时间
                billMap.put("createBillTime", maintenancePlanDto.getCreateBillTime());//创建工单时间
                billMap.put("planType", maintenancePlanDto.getPlanType());//类型  10：半月保  20：季度保 30：半年保 40：全年保
                billMap.put("content", maintenancePlanDto.getContent());//备注
                mapList.add(billMap);
            }
        }
        return APIResponse.success(mapList);
    }

    /**
     * 保存计划备注
     * @param id
     * @param content
     * @return
     */
    public APIResponse savePlanContent(String id,String content){
        MaintenancePlan maintenancePlan = maintenancePlanRepository.findOne(id);
        if(null!=maintenancePlan){
            maintenancePlan.setContent(content);
            maintenancePlanRepository.save(maintenancePlan);
        }
        return APIResponse.success();
    }

}
