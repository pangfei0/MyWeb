package juli.service;

import juli.api.core.APIResponse;
import juli.api.dto.MaintenancePlanBathDto;
import juli.domain.MaintenancePlan;
import juli.domain.MaintenancePlanBath;
import juli.domain.Reports;
import juli.domain.WorkBills;
import juli.domain.contract.UpkeepContract;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MaintenancePlanBathService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    MaintenancePlanBathRepository maintenancePlanBathRepository;
    @Autowired
    MaintenancePlanRepository maintenancePlanRepository;
    @Autowired
    WorkBillRepository workBillRepository;
    @Autowired
    UpkeepContractRepository  upkeepContractRepository;
    @Autowired
    ReportRepository reportRepository;


    public APIResponse newsearch(ServletRequest request,Integer pageNumber,Integer pageSize)
    {
        Map<String, Object> searchParams = ServletUtil.getParametersStartingWith(request, "search_");

        //过滤查询条件
        //电梯
        if(searchParams.get("elevatorNumber(TEXT)_LIKE")!=null){
            searchParams.put(("elevatorNumber(TEXT)_LIKE"),searchParams.get("elevatorNumber(TEXT)_LIKE").toString().trim());
        }else
        {
            searchParams.put("elevatorNumber(TEXT)_LIKE","");
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
//        if(searchParams.get("status(TEXT)_LIKE")!=null&&!"-1".equals(searchParams.get("status(TEXT)_LIKE"))){
//            searchParams.put("status(TEXT)_EQ", searchParams.get("status(TEXT)_LIKE"));
//        }
//        searchParams.remove("status(TEXT)_LIKE");

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
        Page<MaintenancePlanBath> maintenancePlanBaths=maintenancePlanBathRepository.findAll(specification, pageable);
        List<MaintenancePlanBathDto> list= null;
        try {
            list = toDto(maintenancePlanBaths.getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
        PageImpl<MaintenancePlanBathDto> billsPage=new PageImpl<MaintenancePlanBathDto>(list,pageable,maintenancePlanBaths.getTotalElements());
        return  APIResponse.success(billsPage);
    }


    public List<MaintenancePlanBathDto> toDto(List<MaintenancePlanBath> maintenancePlanBathList) throws Exception {
        List<MaintenancePlanBathDto> dtos=new ArrayList<>();
        for(MaintenancePlanBath maintenancePlanBath:maintenancePlanBathList) {
            MaintenancePlanBathDto dto =new MaintenancePlanBathDto();
            dto.setId(maintenancePlanBath.getId());
            dto.setStatus(maintenancePlanBath.getStatus());
            dto.setUpkeepContractId(maintenancePlanBath.getUpkeepContractId());
            dto.setUpkeepContractNumber(maintenancePlanBath.getUpkeepContractNumber());
            if(null!=maintenancePlanBath.getStartTime()){
                dto.setStartTime(DateUtil.dateToFullString3(maintenancePlanBath.getStartTime()));
            }
            if(null!=maintenancePlanBath.getEndTime()){
                dto.setEndTime(DateUtil.dateToFullString3(maintenancePlanBath.getEndTime()));
            }
            dto.setMaintenanceMan(maintenancePlanBath.getMaintenanceMan());
            dto.setMaintenanceManId(maintenancePlanBath.getMaintenanceManId());
            dto.setElevatorNumber(maintenancePlanBath.getElevatorNumber());
            dtos.add(dto);
        }
        return dtos;
    }

    public APIResponse deleteBath(String id)
    {
        if(StringUtils.isNotEmpty(id)){
            MaintenancePlanBath mpb=maintenancePlanBathRepository.findOne(id);
            if(null!=mpb){
                String upkeepContractId=mpb.getUpkeepContractId();
                String bathElevatorNumber=mpb.getElevatorNumber();
                List<MaintenancePlan> maintenancePlanList=maintenancePlanRepository.findByBathId(id);
                if(CollectionUtils.isNotEmpty(maintenancePlanList))
                {
                    for(MaintenancePlan mp:maintenancePlanList)
                    {
                        if(StringUtils.isNotEmpty(mp.getId())){
                            String number=mp.getWorkBillId();//工单id
                            if(null!=number){
                                WorkBills workBills=workBillRepository.findOne(number);
                                if(workBills.getBillstatus()<50)//已完成的不能删除
                                {
                                    if(workBills.getBillstatus()==20)
                                    {
                                        workBillRepository.updateCurrentBill(number);
                                    }
                                    workBillRepository.delete(workBills);
                                }
                            }
                        }
                        maintenancePlanRepository.delete(mp.getId());
                    }
                }
                UpkeepContract upkeepContract= upkeepContractRepository.findById(upkeepContractId);
                if(bathElevatorNumber!=null){
                    String[] elevatorNumberList=bathElevatorNumber.split(",");
                    if(null!=upkeepContract){
                        if(null!=upkeepContract.getElevatorAddBath())
                        {
                            String elevatorAddList=upkeepContract.getElevatorAddBath();
                            for(String number:elevatorNumberList){
                                if(elevatorAddList.contains(number))//包含该电梯,将该电梯剔除
                                {
                                    elevatorAddList=elevatorAddList.replace(number,"");
                                }
                            }
                            upkeepContract.setElevatorAddBath(elevatorAddList);
                            upkeepContractRepository.save(upkeepContract);
                        }
                    }
                }

            }
            maintenancePlanBathRepository.delete(id);
        }
        return APIResponse.success();
    }
}