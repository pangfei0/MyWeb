package juli.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import juli.api.core.APIController;
import juli.api.core.APIResponse;
import juli.api.dto.*;
import juli.domain.*;
import juli.domain.contract.BillingRecord;
import juli.domain.contract.CollectingRecord;
import juli.domain.contract.UpkeepContract;
import juli.infrastructure.DateUtil;
import juli.infrastructure.ServletUtil;
import juli.infrastructure.excel.ExcelUtil;
import juli.infrastructure.exception.JuliException;
import juli.infrastructure.persist.DynamicSpecification;
import juli.repository.*;
import juli.service.UpkeepContractService;
import juli.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.IteratorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang3.StringUtils;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Api(value = "保养合同API", description = " ")
@RestController
@RequestMapping("/api/upkeepContract")
public class UpkeepContractAPIController extends APIController<UpkeepContract, UpkeepContractRepository> {

    @Autowired
    private  UpkeepContractRepository upkeepContractRepository;
    @Autowired
    private BillingRecordRepository billingRecordRepository;
    @Autowired
    private CollectingRecordRepository collectingRecordRepository;
    @Autowired
    private ElevatorRepository elevatorRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private MaintenanceRepository  maintenanceRepository;
    @Autowired
    private MaintenancePlanRepository  maintenancePlanRepository;
    @Autowired
    private MaintenancePlanBathRepository  maintenancePlanBathRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UpkeepContractService upkeepContractService;
    @ApiOperation("新建合同时搜索甲方,物业、使用单位")
    @RequestMapping(value = "/searchPartyA", method = RequestMethod.POST)
    public APIResponse SearchPartyA(@ApiParam("partyAName") String partyAName)
    {
        return upkeepContractService.getPartyA(partyAName);
    }
    @ApiOperation("新建合同时搜索乙方:维保单位")
    @RequestMapping(value = "/searchPartyB", method = RequestMethod.POST)
    public APIResponse SearchPartyB(@ApiParam("partyBName") String partyBName)
    {
        return upkeepContractService.getPartyB(partyBName);
    }

    @ApiOperation("查询可用的项目名称")
    @RequestMapping(value = "/searchAllBuildingName/forUpkeepContractUpdate/{partyAId}&{partyBId}", method = RequestMethod.POST)
    public APIResponse searchAllBuildingName(@ApiParam("甲方ID") @PathVariable("partyAId") String partyAId,@ApiParam("乙方ID") @PathVariable("partyBId") String partyBId) {
        List<String> buildingNames = new ArrayList<>();
        buildingNames = upkeepContractRepository.getMaintainerProjectNames(partyAId,partyBId);
        if(CollectionUtils.isNotEmpty(buildingNames))
        {
            return APIResponse.success(buildingNames);

        }
        else{
            return APIResponse.error("没有匹配的项目名称");
        }
    }

    @ApiOperation("查询可用的项目名称")
    @RequestMapping(value = "/searchBuildingName/{partyAId}&{partyBId}", method = RequestMethod.POST)
    public APIResponse searchBuildingName(@ApiParam("甲方ID") @PathVariable("partyAId") String partyAId,@ApiParam("乙方ID") @PathVariable("partyBId") String partyBId,@ApiParam("projectName")String projectName) {
        return upkeepContractService.searchProjectName(partyAId,partyBId,projectName);
    }

    @ApiOperation("搜索电梯（带分页）")
    @RequestMapping(value = "/new/searchElevator/page", method = RequestMethod.POST)
    public APIResponse searchNewPage(ServletRequest request,
                                     @ApiParam(value = "当前页，从1开始", defaultValue = "1")
                                     @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                     @ApiParam(value = "每页条数", defaultValue = "15")
                                     @RequestParam(value = "pageSize", defaultValue = "15") int pageSize
    ) {
            Map<String, Object> searchParams = ServletUtil.getParametersStartingWith(request, "search_");
            String projectName="";
            String partyAId="";
            String partyBId="";
            List<String> idList = new ArrayList<>();
            if(searchParams.containsKey("projectName(TEXT)_LIKE")){
                projectName = searchParams.get("projectName(TEXT)_LIKE").toString();
                searchParams.remove("projectName(TEXT)_LIKE");
                searchParams.remove("partyA(TEXT)_LIKE");
                searchParams.remove("partyB(TEXT)_LIKE");
                idList=upkeepContractRepository.getElevatorIdsByProjectNames(projectName);
            }
            else{
                partyAId=searchParams.get("partyA(TEXT)_LIKE").toString();
                partyBId=searchParams.get("partyB(TEXT)_LIKE").toString();
                searchParams.remove("partyA(TEXT)_LIKE");
                searchParams.remove("partyB(TEXT)_LIKE");
                //idList=upkeepContractRepository.getElevatorIdsByProjectNames(partyAId,partyBId);//暂时取消关联甲方乙方，因为服务中心录入问题
                idList=upkeepContractRepository.getElevatorIds();
            }
            searchParams.put("id_in", getIds(idList));
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
    @ApiOperation("获取合同的基本信息（分页）")
    @RequestMapping(value = "/new/search/page",method = RequestMethod.POST)
    public APIResponse searchPage(ServletRequest request,
                                  @ApiParam(value = "当前页，从1开始", defaultValue = "1")
                                  @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                  @ApiParam(value = "每页条数", defaultValue = "15")
                                  @RequestParam(value = "pageSize", defaultValue = "15") int pageSize
    ) {
        try {
            User user = userService.getCurrentUser();
            List<String> idList=new ArrayList<>();
            Role role=user.getRoles().get(0);
            Map<String, Object> searchParams = ServletUtil.getParametersStartingWith(request, "search_");
            Sort sort = null;
            if(user.getCompanyId() != null&&!user.getCompanyId().equals("")){
                switch (user.getCompanyType()){
                    case "30":
                    case "50":
                        idList=upkeepContractRepository.findupkeepContractIdsByPartyAId(user.getCompanyId());
                        searchParams.put("id_in", getIds(idList));
                        break;
                    case "20":
                        idList=upkeepContractRepository.findupkeepContractIdsByPartyBId(user.getCompanyId());
                        searchParams.put("id_in", getIds(idList));
                        break;
                    default:
                        searchParams.put("id_in", "none");
                }
            }
            else
            {
                if(!role.getName().equals("超级管理员")){
                    searchParams.put("id_in","none");
                }
            }
            if(searchParams.get("elevatorNumber(TEXT)_LIKE") != null){
                String upkeepId=upkeepContractRepository.getUpkeepcontractIdByElevatorNumber(searchParams.get("elevatorNumber(TEXT)_LIKE").toString().trim());
                if(StringUtils.isNotEmpty(upkeepId)){
                    if(idList.size()!=0){
                        if(!idList.contains(upkeepId)){
                            searchParams.put("id_in", "none");
                        }else
                        {
                            idList.clear();
                            idList.add(upkeepId);
                            searchParams.put("id_in", getIds(idList));
                        }
                    }else
                    {
                        if(!role.getName().equals("超级管理员")){
                            searchParams.put("id_in","none");
                        }
                       else {
                            idList.add(upkeepId);
                            searchParams.put("id_in", getIds(idList));
                        }
                    }
                }else{
                    searchParams.put("id_in", "none");
                }
                searchParams.remove("elevatorNumber(TEXT)_LIKE");
            }
            String sortName = request.getParameter("sortName");
            String sortOrder = request.getParameter("sortOrder");
            if (sortName == null) {
                sort = new Sort(Sort.Direction.DESC, "createdDate");
            } else {
                if ("ownershortname".equals(sortName)) {
                    sortName = "ownerShortname";
                }
                if ("property".equals(sortName)) {
                    sortName = "property";
                }
                if ("number".equals(sortName)) {
                    sortName = "number";
                }
                if ("renewstatus".equals(sortName)) {
                    sortName = "renewStatus";
                }
                if ("status".equals(sortName)) {
                    sortName = "status";
                }

                if ("asc".equals(sortOrder)) {
                    sort = new Sort(Sort.Direction.ASC, sortName);
                } else {
                    sort = new Sort(Sort.Direction.DESC, sortName);
                }
            }
            searchParams.put(("number(TEXT)_LIKE"), searchParams.get("number(TEXT)_LIKE") != null?searchParams.get("number(TEXT)_LIKE").toString().trim():"");
            searchParams.put(("ownerShortname(TEXT)_LIKE"), searchParams.get("ownerShortname(TEXT)_LIKE") != null?searchParams.get("ownerShortname(TEXT)_LIKE").toString().trim():"");
            if(searchParams.get("id_in") != null){
                searchParams.put(("id_in"), searchParams.get("id_in").toString());
            }else{
                searchParams.remove("id_in");
            }

            Specification specification = DynamicSpecification.buildSpecification(searchParams);
            PageRequest pageable = new PageRequest(pageNumber - 1, pageSize,sort);
            Page<UpkeepContract> entities = upkeepContractRepository.findAll(specification, pageable);
            List<UpkeepContractDto> list = new ArrayList<>();
            for (UpkeepContract upkeepContract : entities.getContent()) {
                UpkeepContractDto upkeepContract1=new UpkeepContractDto();
                upkeepContract1.setId(upkeepContract.getId());
                upkeepContract1.setNumber(upkeepContract.getNumber());
                upkeepContract1.setProperty(upkeepContract.getProperty());
                upkeepContract1.setStatus(upkeepContract.getStatus());
                upkeepContract1.setOwnerShortname(upkeepContract.getOwnerShortname());
                upkeepContract1.setRemainElevator(upkeepContract.getRemainElevator());
                upkeepContract1.setRemainDays(upkeepContract.getRemainDays());
                upkeepContract1.setRenewStatus(upkeepContract.getRenewStatus());
                list.add(upkeepContract1);
            }
            PageImpl<UpkeepContractDto> page = new PageImpl<UpkeepContractDto>(list,pageable,entities.getTotalElements());
            return APIResponse.success(page);
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

    @ApiOperation("获取合同的详细信息")
    @RequestMapping(value = "/getDetail/{id}", method = RequestMethod.GET)
    public APIResponse getDetail(@ApiParam("合同id") @PathVariable("id") String id) {
        Map<String, Object> paraMap = new HashMap<>();
        UpkeepContract upkeepContract = upkeepContractRepository.findOne(id);
        if (upkeepContract == null) {
            return APIResponse.error("不存在此对象：" + id);
        } else {
            //基本信息
            UpkeepContractDto dto = new UpkeepContractDto().mapFrom(upkeepContract);

            dto.setRemainElevator(upkeepContract.getRemainElevator());
            paraMap.put("baseInfo", dto);
            if (upkeepContract.getPartyA() != null) {
                dto.setPartyAId(upkeepContract.getPartyA().getId());
                dto.setPartyAContact(upkeepContract.getPartyA().getContact());
                dto.setPartyAAddress(upkeepContract.getPartyA().getAddress());
                dto.setPartyATelephone(upkeepContract.getPartyA().getMobile());
                dto.setPartyAName(upkeepContract.getPartyA().getName());
            } else {
                dto.setPartyAId(null);
                dto.setPartyAContact(null);
                dto.setPartyAAddress(null);
                dto.setPartyATelephone(null);
                dto.setPartyAName(null);
            }
            if (upkeepContract.getPartyB() != null) {
                dto.setPartyBId(upkeepContract.getPartyB().getId());
                dto.setPartyBContact(upkeepContract.getPartyB().getContact());
                dto.setPartyBAddress(upkeepContract.getPartyB().getAddress());
                dto.setPartyBTelephone(upkeepContract.getPartyB().getMobile());
                dto.setPartyBName(upkeepContract.getPartyB().getName());
            } else {
                dto.setPartyBId(null);
                dto.setPartyBContact(null);
                dto.setPartyBAddress(null);
                dto.setPartyBTelephone(null);
                dto.setPartyBName(null);
            }

            //开票记录
            paraMap.put("billInfo",getBillInfo(upkeepContract));
            //收款记录
            paraMap.put("collectInfo",getCollcetInfo(upkeepContract));
            //状态信息
            paraMap.put("stateInfo",getStateInfo(upkeepContract));
            //电梯清单
            paraMap.put("elevatorInfo",getElevators(upkeepContract));
            return APIResponse.success(paraMap);
        }

    }

    @ApiOperation("合同基本数据的更新")
    @RequestMapping(value = "/editBaseInfo/{id}",method = RequestMethod.PATCH)
    public APIResponse update(@ApiParam("合同Id")@PathVariable("id")String id,@ApiParam("需要更新的对象")UpkeepContractDto dto) throws Exception {
              UpkeepContract upkeepContract=upkeepContractRepository.findOne(id);
              if(upkeepContract==null)
                return  APIResponse.error("不存在此对象;"+id);
                  upkeepContract.setNumber(dto.getNumber());
                  upkeepContract.setProperty(dto.getProperty().equals("") ? null : dto.getProperty());
                  upkeepContract.setStatus(dto.getStatus().equals("") ? null : dto.getStatus());
                  upkeepContract.setSource(dto.getSource().equals("") ? null : dto.getSource());
                  upkeepContract.setOwnerFullname(dto.getOwnerFullname());
                  upkeepContract.setOwnerShortname(dto.getOwnerShortname());
                  upkeepContract.setBeginDate(dto.getBeginDate());
                  upkeepContract.setEndDate(dto.getEndDate());
                  upkeepContract.setValue(dto.getValue());//合同金额
                  upkeepContract.setPaymentTerm(dto.getPaymentTerm());
                  upkeepContract.setDuration(dto.getDuration());
                  upkeepContractRepository.save(upkeepContract);
                  return  APIResponse.success();

    }

    @ApiOperation("合同状态信息的更新")
    @RequestMapping(value = "/stateInfo/{id}",method = RequestMethod.PATCH)//p
    public APIResponse update(@ApiParam("合同Id")@PathVariable("id")String id,@ApiParam("续签状态")String renewStatus,@ApiParam("待开票")double needCollectValue ,@ApiParam("应收账款")double receivableValue) throws Exception {
        UpkeepContract upkeepContract=upkeepContractRepository.findOne(id);
        if(upkeepContract==null)
            return  APIResponse.error("不存在此对象;"+id);
            upkeepContract.setRenewStatus(renewStatus.equals("-1") ? null : renewStatus);
            upkeepContract.setNeedCollectValue(needCollectValue);
            upkeepContract.setReceivableValue(receivableValue);
            upkeepContractRepository.save(upkeepContract);
            return  APIResponse.success();
    }
    @ApiOperation("添加合同时判断合同号是否已存在")
    @RequestMapping(value="/noSearch",method = RequestMethod.POST)
    public APIResponse noSearch(@ApiParam("num")String num){
        UpkeepContract upkeepContract = upkeepContractRepository.findByNumber(num);
        if (upkeepContract != null) {
            return APIResponse.success();
        } else {
            return APIResponse.error("");
        }
    }
    @ApiOperation("添加合同")
    @RequestMapping(value="/add",method = RequestMethod.POST)
    public APIResponse create(UpkeepContractDto dto) throws Exception {
        UpkeepContract upkeepContract=dto.mapTo();
        setForeignFieldFromDto(upkeepContract, dto);
        if(dto.getSource().equals("-1")){
            upkeepContract.setSource(null);
        }else{upkeepContract.setSource(dto.getSource());}
        if(dto.getStatus().equals("-1")){upkeepContract.setStatus(null);}else{upkeepContract.setStatus(dto.getStatus());}
        if(dto.getProperty().equals("-1")){upkeepContract.setProperty(null);}else{upkeepContract.setProperty(dto.getProperty());}
        upkeepContractRepository.save(upkeepContract);
        return  APIResponse.success();
    }

    @ApiOperation("查询所有保养日")
    @RequestMapping(value = "/seekUpkeepDay", method = RequestMethod.GET)
    public APIResponse seekUpkeepDay(){
        JSONArray allEleDays = new JSONArray();
        Iterable<UpkeepContract> upkeepContractIterable = upkeepContractRepository.findAll();
        Iterator<UpkeepContract> upkeepContractIterator = upkeepContractIterable.iterator();
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
        while (upkeepContractIterator.hasNext()){
            JSONObject upkeepObject = new JSONObject();
            JSONArray upkeepArray = new JSONArray();
            UpkeepContract upkeepContract = upkeepContractIterator.next();
            Date beginDate = upkeepContract.getBeginDate();
            Date endDate = upkeepContract.getEndDate();

            DateTime beginDT = DateTime.parse(new DateTime(beginDate).toString("yyyy-MM-dd"), format);

            if (beginDate == null || endDate == null) {
                continue;
            }
            Set<Elevator> elevators = upkeepContract.getElevator();
            int elevatorCount = elevators.size();
            int phaseSize = (int) Math.ceil((double) elevators.size() / 5);

            int index = 1;

            for (Elevator elevator : elevators){
                    JSONObject eleObject = new JSONObject();
                    JSONArray eleArrayOfHalfMonth = new JSONArray();
                    JSONArray eleArrayOfSeason = new JSONArray();
                    JSONArray eleArrayOfHalfYear = new JSONArray();
                    JSONArray eleArrayOfYear = new JSONArray();
                    int pIndex = (int) Math.ceil((double) index % 5);
                    int pushDelayDay = 0;
                    if (elevatorCount <= 5){
                        pushDelayDay = index == 1 ? 0 : 3 + (index - 2) * 3;
                    } else {
                        if (pIndex == 1) {
                            pushDelayDay = 0;
                        } else if (pIndex == 2) {
                            pushDelayDay = 3;
                        } else if (pIndex == 3) {
                            pushDelayDay = 6;
                        } else if (pIndex == 4) {
                            pushDelayDay = 9;
                        } else if (pIndex == 5 || pIndex == 0) {
                            pushDelayDay = 12;
                        }
                    }
                    DateTime pushDay = beginDT.plusDays(pushDelayDay);
                    int count = 1;
                    while ( pushDay.getMillis()<endDate.getTime() ) {
                        eleArrayOfHalfMonth.add(pushDay.toString("yyyy-MM-dd"));

                        if (count % 6 == 0) {   //季保
                            eleArrayOfSeason.add(pushDay.toString("yyyy-MM-dd"));
                        }
                        if (count % 12 == 0) {     //半年保
                            eleArrayOfHalfYear.add(pushDay.toString("yyyy-MM-dd"));
                        }
                        if (count % 24 == 0) {     //年保
                            eleArrayOfYear.add(pushDay.toString("yyyy-MM-dd"));
                        }

                        pushDay = pushDay.plusDays(15);
                        count ++;
                    }

                eleObject.put(elevator.getNumber() + "半月保", eleArrayOfHalfMonth);
                eleObject.put(elevator.getNumber() + "季保", eleArrayOfSeason);
                eleObject.put(elevator.getNumber() + "半年保", eleArrayOfHalfYear);
                eleObject.put(elevator.getNumber() + "年保", eleArrayOfYear);


                upkeepArray.add(eleObject);
                index++;
            }

            upkeepObject.put(upkeepContract.getNumber(),upkeepArray);
            allEleDays.add(upkeepObject);
        }
        return APIResponse.success(allEleDays);
    }

    @ApiOperation("添加合同的开票记录")
    @RequestMapping(value = "/addBillRecord/{id1}",method = RequestMethod.PATCH)
    public  APIResponse addBillRecoding(@ApiParam("合同的id1")@PathVariable("id1")String id,@ApiParam("票号")String billNO ,@ApiParam("开票时间")String billingDate,@ApiParam("开票记录")double billValue)
    {
        UpkeepContract upkeepContract=upkeepContractRepository.findById(id);

        BillingRecord br = new BillingRecord();
        try {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        br.setBillingDate(sdf.parse(billingDate));
    }catch (ParseException e)
    {
        System.out.println(e.getMessage());
    }

        br.setBillNO(billNO);
        br.setBillValue(billValue);
        br.setUpkeepContract(upkeepContract);
        billingRecordRepository.save(br);
        return APIResponse.success();
    }

    @ApiOperation("收款记录添加")
    @RequestMapping(value = "/addCollectingRecord/{id}",method = RequestMethod.POST)
    public  APIResponse addCollectingRecord(@ApiParam("合同的id")@PathVariable("id")String id,@ApiParam("票号")String billNO ,@ApiParam("开票时间")String collectionDate,@ApiParam("开票记录")double collectingValue)
    {
        UpkeepContract upkeepContract=upkeepContractRepository.findById(id);
        CollectingRecord collectingRecord=new CollectingRecord();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            collectingRecord.setCollectionDate(sdf.parse(collectionDate));
        }catch (ParseException e)
        {
            System.out.println(e.getMessage());
        }
        collectingRecord.setBillNo(billNO);
        collectingRecord.setCollectingValue(collectingValue);
        collectingRecord.setUpkeepContract(upkeepContract);
        collectingRecordRepository.save(collectingRecord);
        return  APIResponse.success();

    }


    @ApiOperation("删除合同")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public  APIResponse removeUpkeepContrat(@ApiParam("合同的id")@PathVariable("id")String id ) {
        return upkeepContractService.removeUpkeepContrat(id);
    }

    @ApiOperation("移除电梯")
    @RequestMapping(value = "/removeElevator/{id}",method = RequestMethod.POST)
    public  APIResponse removeElevator(@ApiParam("合同的id")@PathVariable("id")String id,@ApiParam("电梯id")String elevatorId )
    {
        UpkeepContract upkeepContract= upkeepContractRepository.findOne(id);
        Elevator elevator=elevatorRepository.findElevatorById(elevatorId);
        upkeepContract.getElevator().remove(elevator);
        upkeepContractRepository.save(upkeepContract);
        return  APIResponse.success();

    }
    @ApiOperation("添加电梯到合同")
    @RequestMapping(value = "/addElevator/{id}",method = RequestMethod.POST)
    public  APIResponse addElevator(@ApiParam("合同的id")@PathVariable("id")String id,@ApiParam("电梯id")String elevatorStr )
    {
        UpkeepContract upkeepContract= upkeepContractRepository.findOne(id);
        String []elevatorIds=elevatorStr.split(",");
        for(String elevatorId:elevatorIds)
        {
            Elevator elevator=elevatorRepository.findElevatorById(elevatorId);
            upkeepContract.getElevator().add(elevator);
//            elevator.setUpkeepContract(upkeepContract);
//            elevatorRepository.save(elevator);
        }
        upkeepContractRepository.save(upkeepContract);
        return  APIResponse.success();

    }
    //详细中人员维护的保存接口。


   //状态信息
    public Map<String ,Object> getStateInfo(UpkeepContract upkeepContract)
    {
        Map<String,Object> paramMap=new HashMap<>();
        paramMap.put("remainDays", upkeepContract.getRemainDays());//倒计时
        paramMap.put("renewStatus", upkeepContract.getRenewStatus());//续签状态
        paramMap.put("allBillingValue",upkeepContract.getAllBillingValue());//开票总额
        paramMap.put("needCollectValue",upkeepContract.getNeedCollectValue());
        paramMap.put("allCollectValue", upkeepContract.getAllCollectValue());
        paramMap.put("receivableValue", upkeepContract.getReceivableValue());
        int count=upkeepContract.getBillingRecordList().size();
        if(count==0)
          paramMap.put("age", "0");
        else
        {
            BillingRecord billingRecord=upkeepContract.getBillingRecordList().get(count-1);
            Date now=new Date();
            long b=billingRecord.getBillingDate().getTime();
            long a=now.getTime();
            long  day=a -b;
            paramMap.put("age", day / 86400000);
        }
        return paramMap;

    }
    //开票记录
    public List<BillingRecordDto> getBillInfo(UpkeepContract upkeepContract) {
        Map<String, Object> paramMap = new HashMap<>();
        BillingRecordDto dto=null;
        List<BillingRecordDto> list=new ArrayList<>();
        double allBillingValue=0;
        for(BillingRecord br:billingRecordRepository.findByUpkeepContract(upkeepContract))
        {
            allBillingValue=allBillingValue+br.getBillValue();
            dto=new BillingRecordDto().mapFrom(br);
            dto.setName(br.getCreatedBy());
            dto.setUpkeepContractId(upkeepContract.getId());
            list.add(dto);
        }
        upkeepContract.setAllBillingValue(allBillingValue);
        return  list;
    }
    //收款记录
    public List<CollectingRecordDto> getCollcetInfo(UpkeepContract upkeepContract) {
        Map<String, Object> paramMap = new HashMap<>();
        CollectingRecordDto dto=null;
        List<CollectingRecordDto> list=new ArrayList<>();
        double allCollectValue=0;
        for(CollectingRecord cr:collectingRecordRepository.findByUpkeepContract(upkeepContract))
        {
            allCollectValue=allCollectValue+cr.getCollectingValue();
            dto=new CollectingRecordDto().mapFrom(cr);
            dto.setUpkeepContractId(upkeepContract.getId());
            dto.setCreateBy(cr.getCreatedBy());
            list.add(dto);
        }
        upkeepContract.setAllCollectValue(allCollectValue);
        return  list;
    }
    //电梯清单

    public List<Map<String,Object>> getElevators(UpkeepContract upkeepContract)
    {
          List<Map<String,Object>> mapList=new ArrayList<>();

          for(Elevator el:upkeepContract.getElevator())
          {

                Map<String,Object> paramMap=new HashMap<>();
                paramMap.put("id",el.getId());
                paramMap.put("elevatorType",el.getElevatorType());
//                paramMap.put("manufacturer",el.getManufacturer().getName());
                paramMap.put("number", el.getNumber());
                paramMap.put("lastcheckDate",el.getLastCheckDate());
                paramMap.put("avgValue",upkeepContract.getAvgValue());
                mapList.add(paramMap);
          }
        return mapList;
    }

    @RequiresPermissions("export")
    @RequestMapping("/export")
    public void export(HttpServletResponse response) throws Exception {

        List<UpkeepContract> upkeepContracts = IteratorUtils.toList(upkeepContractRepository.findAll().iterator());
        LinkedHashMap<String, List<?>> map = new LinkedHashMap<>();
        map.put("保养合同数据", upkeepContracts);

        List<String[]> fieldNames = new ArrayList<>();
        fieldNames.add(new String[]{"number", "property", "status", "source", "ownerFullname", "ownerShortname", "duration", "beginDate", "endDate", "value", "paymentTerm", "renewStatus", "receivableValue", "remainDays", "allBillingValue", "allCollectValue"});

        List<String[]> columNames = new ArrayList<>();
        columNames.add(new String[]{"合同编号", "合同性质", "合同状态", "合同来源", "业主全称", "业主简称", "合同期限", "合同生效日", "合同到期日", "合同金额", "付款条件", "续签状态", "应收账款", "合同到期时间", "开票总额", "到账总额"});

        ExcelUtil.ExcelExportData setInfo = new ExcelUtil.ExcelExportData();
        setInfo.setDataMap(map);
        setInfo.setFieldNames(fieldNames);
        setInfo.setTitles(new String[]{"保养合同数据"});
        setInfo.setColumnNames(columNames);

        ExcelUtil.export2Browser(response, setInfo, "保养合同数据");
    }


    private void setForeignFieldFromDto(UpkeepContract entity, UpkeepContractDto dto) throws Exception {
        if (StringUtils.isNotEmpty(dto.getPartyAId())) {
            entity.setPartyA(companyRepository.findOne(dto.getPartyAId()));
        } else {
            entity.setPartyA(null);
        }
        if (StringUtils.isNotEmpty(dto.getPartyBId())) {
            entity.setPartyB(companyRepository.findOne(dto.getPartyBId()));
        } else {
            entity.setPartyB(null);
        }
    }

    @ApiOperation("获取合同中的电梯")
    @RequestMapping(value = "/bathElevator",method = RequestMethod.GET)
    public APIResponse bathElevator(String id)
    {
        List<ElevatorBathDto> elevatorBathDtos = new ArrayList<>();
        UpkeepContract upkeepContract = upkeepContractRepository.findOne(id);
        if(null!=upkeepContract){
            Set<Elevator> elevatorSet = upkeepContract.getElevator();
            if(CollectionUtils.isNotEmpty(elevatorSet)){
                List<Elevator> elevatorList = new ArrayList<Elevator>(elevatorSet);
                //去掉已经产生批次的电梯
                List<Elevator> elevator_del = new ArrayList<Elevator>();
                String elevatorNumbers = upkeepContract.getElevatorAddBath();
                if(StringUtils.isEmpty(elevatorNumbers)){
                    elevatorNumbers = "";
                }
                for(Elevator e:elevatorList){
                    if(elevatorNumbers.indexOf(e.getNumber())!=-1){
                        elevator_del.add(e);
                    }
                }
                elevatorList.removeAll(elevator_del);
                //遍历
                for(Elevator e :elevatorList){
                    elevatorBathDtos.add(fromElevator2Dto(e));
                }
            }
        }
        return APIResponse.success(elevatorBathDtos);
    }

    private ElevatorBathDto fromElevator2Dto(Elevator e){
        ElevatorBathDto dto = new ElevatorBathDto();
        dto.setId(e.getId());
        dto.setAddress(e.getAddress());
        dto.setAlias(e.getAlias());
        dto.setElevatorType(e.getElevatorType());
        dto.setNumber(e.getNumber());
        return dto;
    }

    @ApiOperation("添加批次及计划")
    @RequestMapping(value = "/bathElevatorAdd/{id}",method = RequestMethod.POST)
    public APIResponse bathElevator(@ApiParam("合同的id")@PathVariable("id") String id, @ApiParam("批次参数")ElevatorBathpojo pojo) throws Exception {
        UpkeepContract upkeepContract = upkeepContractRepository.findOne(id);
        String elevatorStr2 = "";
        String elevatorStrSet = "";
        MaintenancePersonnel maintenancePersonnel = null;
        if(null!=pojo && StringUtils.isNotEmpty(pojo.getElevatorStr())){
             elevatorStr2 = pojo.getElevatorStr().substring(0, pojo.getElevatorStr().lastIndexOf(","));
        }
        if(null!=pojo && StringUtils.isNotEmpty(pojo.getMaintenanceManId())){
            maintenancePersonnel = maintenanceRepository.findOne(pojo.getMaintenanceManId());
        }
        //保存维保计划批次
        MaintenancePlanBath maintenancePlanBath = new MaintenancePlanBath();
        if(null!=upkeepContract){
            maintenancePlanBath.setUpkeepContractId(upkeepContract.getId());
            maintenancePlanBath.setUpkeepContractNumber(upkeepContract.getNumber());
        }
        if(StringUtils.isNotEmpty(elevatorStrSet)){
            elevatorStrSet = elevatorStrSet.substring(0,elevatorStrSet.lastIndexOf(","));
            maintenancePlanBath.setElevatorNumber(elevatorStrSet);
        }
        if(null!=maintenancePersonnel){
            maintenancePlanBath.setMaintenanceMan(maintenancePersonnel.getName());
            maintenancePlanBath.setMaintenanceManId(maintenancePersonnel.getId());
        }
        if(StringUtils.isNotEmpty(pojo.getStartTime())&&null!=pojo.getDays()){
            maintenancePlanBath.setStartTime(DateUtil.stringToFullDateformat(pojo.getStartTime()));
            maintenancePlanBath.setEndTime(DateUtil.toSomeDay(pojo.getStartTime(), pojo.getDays()-1));
        }
        maintenancePlanBathRepository.save(maintenancePlanBath);
        MaintenancePlanBath  maintenancePlanBath1=maintenancePlanBathRepository.findById(maintenancePlanBath.getId());
        String planBathId=maintenancePlanBath1.getId();
        if(StringUtils.isNotEmpty(elevatorStr2)){
            String [] a = elevatorStr2.split(",");
            for(int i=0;i<a.length;i++){
                Elevator elevator = elevatorRepository.findOne(a[i]);
                if(null!=elevator){
                    elevatorStrSet += elevator.getNumber()+",";
                    if(StringUtils.isEmpty(upkeepContract.getElevatorAddBath())){
                        upkeepContract.setElevatorAddBath("");
                    }
                    if(StringUtils.isNotEmpty(elevator.getNumber()) && upkeepContract.getElevatorAddBath().indexOf(elevator.getNumber())==-1 ){
                        upkeepContract.setElevatorAddBath(upkeepContract.getElevatorAddBath()+","+elevator.getNumber());
                        upkeepContractRepository.save(upkeepContract);
                    }
                    //保存维保计划
                    if(pojo.getDaysEd()>0){
                        for(int j =pojo.getDaysEd()-1,r=0;j<24;j++,r++){
                            Date start = DateUtil.toSomeDay(pojo.getStartTime(),0+r*15);
                            Date end = DateUtil.toSomeDay(pojo.getStartTime(),pojo.getDays()-1+r*15);
                            pojo.setStart(start);
                            pojo.setEnd(end);
                            pojo.setPlanType(10);//半月保
                            saveMaintenancePlan(elevator, pojo, upkeepContract, maintenancePersonnel,planBathId);
                            if(j==5){
                                pojo.setPlanType(20);//季度保
                                saveMaintenancePlan(elevator,pojo,upkeepContract,maintenancePersonnel,planBathId);
                            }
                            if(j==11){
                                pojo.setPlanType(20);//季度保
                                saveMaintenancePlan(elevator,pojo,upkeepContract,maintenancePersonnel,planBathId);
                                pojo.setPlanType(30);//半年保
                                saveMaintenancePlan(elevator,pojo,upkeepContract,maintenancePersonnel,planBathId);
                            }
                            if(j==18){
                                pojo.setPlanType(20);//季度保
                                saveMaintenancePlan(elevator,pojo,upkeepContract,maintenancePersonnel,planBathId);
                            }
                            if(j==23){
                                pojo.setPlanType(20);//季度保
                                saveMaintenancePlan(elevator, pojo, upkeepContract, maintenancePersonnel, planBathId);
                                pojo.setPlanType(30);//半年保
                                saveMaintenancePlan(elevator, pojo, upkeepContract, maintenancePersonnel, planBathId);
                                pojo.setPlanType(40);//全年保
                                saveMaintenancePlan(elevator, pojo, upkeepContract, maintenancePersonnel,planBathId);
                            }
                        }
                    }
                }
            }
        }
        if(StringUtils.isNotEmpty(elevatorStrSet)){
            elevatorStrSet = elevatorStrSet.substring(0,elevatorStrSet.lastIndexOf(","));
            maintenancePlanBath1.setElevatorNumber(elevatorStrSet);
            maintenancePlanBathRepository.save(maintenancePlanBath1);
        }
        return APIResponse.success();
    }


    private void saveMaintenancePlan(Elevator elevator,ElevatorBathpojo pojo,UpkeepContract upkeepContract,MaintenancePersonnel maintenancePersonnel,String bathId){
        MaintenancePlan maintenancePlan = new MaintenancePlan();
        maintenancePlan.setNumber(elevator.getNumber());
        maintenancePlan.setPlanBathId(bathId);
        maintenancePlan.setElevatorId(elevator.getId());
        maintenancePlan.setStatus(10);
        maintenancePlan.setPlanType(pojo.getPlanType());
//        if(StringUtils.isNotEmpty(pojo.getStartTime())){
            maintenancePlan.setPlanTime(pojo.getStart());
//        }
//        if(StringUtils.isNotEmpty(pojo.getEndTime())){
            maintenancePlan.setPlanEndTime(pojo.getEnd());
//        }
        if(null!=maintenancePersonnel){
            maintenancePlan.setMaintenanceMan(maintenancePersonnel.getName());
        }
        if(null!=upkeepContract){
            maintenancePlan.setUpkeepContractId(upkeepContract.getId());
            maintenancePlan.setUpkeepContractNumber(upkeepContract.getNumber());
        }
        maintenancePlanRepository.save(maintenancePlan);
    }

    //要产生的半月保
    public int getTheRemainMonthPlan(int day){
        return day-1;
    }

    //要产生的季度保
    public int getTheRemainQuarterPlan(int day){

        return day-1;
    }

    //要产生的半年保
    public int getTheRemainHalfYearPlan(int day){
        return day-1;
    }

    //要产生的年保
    public int getTheRemainYearPlan(int day){
        return day-1;
    }



}
