package juli.service;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import juli.api.core.APIResponse;
import juli.api.dto.CompanyDto;
import juli.api.dto.MaintenancePersonnelDto;
import juli.domain.*;
import juli.domain.enums.Appraise;
import juli.domain.enums.BillReportCategory;
import juli.domain.enums.ReportStatus;
import juli.infrastructure.DateUtil;
import juli.infrastructure.MD5Util;
import juli.infrastructure.ServletUtil;
import juli.infrastructure.excel.ExcelUtil;
import juli.infrastructure.exception.JuliException;
import juli.infrastructure.persist.DynamicSpecification;
import juli.repository.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.h2.bnf.context.DbTableOrView;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.*;

/**
 * Created by pf on 2016/2/1.
 */
@Service
public class MaintenanceService {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    MaintenanceRepository maintenanceRepository;
    @Autowired
    LoginRecordRepository loginRecordRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    WorkBillRepository workBillRepository;
    @Autowired
    UserService userService;
    @Autowired
    MaintenancePersonnelRepository maintenancePersonnelRepository;
    @Autowired
    ElevatorRepository elevatorRepository;

    public List<MaintenancePersonnel> findMaintainerByCompanyId(String companyId) {
        return maintenanceRepository.findMaintainerByCompanyId(companyId);
    }

    public JSONObject systemLogin(HttpSession session, String systemId) {
        JSONObject json = new JSONObject();
        logger.info("维修员统一认证" + systemId + "登录");

        MaintenancePersonnel maintenancePersonnel = maintenanceRepository.findByUid(systemId);
        if (maintenancePersonnel != null) {
            if (maintenancePersonnel.getActive() == "0") {
                json.put("success", false);
                json.put("description", "账号未激活");
            } else {
                if (maintenancePersonnel.getCreatedDate() == null)
                    maintenancePersonnel.setCreatedDate(new DateTime());
                String sid = MD5Util.parseStrToMd5L32(System.currentTimeMillis() + "");
                session.setAttribute("sid", sid);

                maintenanceRepository.save(maintenancePersonnel);
                MaintenancePersonnelDto dto = new MaintenancePersonnelDto().mapFrom(maintenancePersonnel);
                if (maintenancePersonnel.getMaintainer() != null) {
                    dto.setMaintainerId(maintenancePersonnel.getMaintainer().getId());
                }
                json.put("description", "");
                json.put("success", true);
                json.put("sid", sid);
                json.put("uid", maintenancePersonnel.getId());
                json.put("sessionId", session.getId());
                json.put("userInfo", JSONObject.toJSON(dto));
                System.out.println(json);
            }

            return json;
        } else {
            json.put("success", false);
            json.put("description", "维保系统不存在此用户");
            json.put("sid", "");
            json.put("uid", "");
            json.put("sessionId", "");
            json.put("userInfo", "");
            System.out.println(json);
            return json;
        }
    }

    public APIResponse getMaintenanceByNumberAndPassword(HttpSession session, String number, String password) {
        logger.info("维修员" + number + "登录");

        password = MD5Util.parseStrToMd5L32(password);
        Map<String, Object> paramMap = new HashMap<>();
        String sid = MD5Util.parseStrToMd5L32(System.currentTimeMillis() + "");
        paramMap.put("sid", sid);
        MaintenancePersonnel maintenancePersonnel = maintenanceRepository.findByNumberAndPassword(number, password);
        if (maintenancePersonnel != null) {
            if (maintenancePersonnel.getCreatedDate() == null)
                maintenancePersonnel.setCreatedDate(new DateTime());
            session.setAttribute("sid", sid);
            maintenanceRepository.save(maintenancePersonnel);
            MaintenancePersonnelDto dto = new MaintenancePersonnelDto().mapFrom(maintenancePersonnel);
            if (maintenancePersonnel.getMaintainer() != null) {
                dto.setMaintainerId(maintenancePersonnel.getMaintainer().getId());
            }
            paramMap.put("info", dto);
            return APIResponse.success(paramMap);
        } else {
            return APIResponse.error("账户或者密码错误");
        }

        //测试使用
//        MaintenancePersonnelDto dto = new MaintenancePersonnelDto();
//        session.setAttribute("sid", sid);
//        dto.setId("402848cc560092140156009d31ef0004");//lijia
//        paramMap.put("info", dto);
//        return APIResponse.success(paramMap);
    }

    public APIResponse changeStatus(String id, Integer status, String uuid, int deviceType) {
        MaintenancePersonnel maintenancePersonnel = maintenanceRepository.findOne(id);
        if (maintenancePersonnel != null) {
            maintenancePersonnel.setDeviceType(deviceType);
            logger.info("uuid:" + uuid);
            if (status == 10)//上岗
            {
                if (maintenancePersonnel.getCurrentState() == 10) {
                    maintenanceRepository.save(maintenancePersonnel);
                    return APIResponse.success("成功");
                } else if (maintenancePersonnel.getCurrentState() == 20) {
                    //结束待命记录
                    MaintenanceLoginRecord maintenanceLoginRecord = maintenancePersonnel.getMaintenanceLoginRecord();
                    maintenanceLoginRecord.setLeaveTime(new Date());
                    loginRecordRepository.save(maintenanceLoginRecord);
                    //添加上岗记录
                    MaintenanceLoginRecord maintenanceLoginRecord1 = new MaintenanceLoginRecord();
                    maintenanceLoginRecord1.setLoginTime(new Date());
                    maintenanceLoginRecord1.setStatus(10);
                    maintenanceLoginRecord1.setMaintenancePersonnel(maintenancePersonnel);
                    loginRecordRepository.save(maintenanceLoginRecord1);
                    maintenancePersonnel.setMaintenanceLoginRecord(maintenanceLoginRecord1);
                    maintenancePersonnel.setCurrentState(10);
                    maintenancePersonnel.setUuid(uuid);
                    maintenanceRepository.save(maintenancePersonnel);
                    return APIResponse.success("成功");
                } else {
                    MaintenanceLoginRecord maintenanceLoginRecord1 = new MaintenanceLoginRecord();
                    maintenanceLoginRecord1.setLoginTime(new Date());
                    maintenanceLoginRecord1.setStatus(10);
                    maintenanceLoginRecord1.setMaintenancePersonnel(maintenancePersonnel);
                    loginRecordRepository.save(maintenanceLoginRecord1);
                    maintenancePersonnel.setMaintenanceLoginRecord(maintenanceLoginRecord1);
                    maintenancePersonnel.setCurrentState(10);
                    maintenancePersonnel.setUuid(uuid);
                    maintenanceRepository.save(maintenancePersonnel);
                    return APIResponse.success("成功");
                }
            } else if (status == 20)//待命
            {
                if (maintenancePersonnel.getCurrentState() == 20) {
                    maintenanceRepository.save(maintenancePersonnel);
                    return APIResponse.success("成功");
                } else if (maintenancePersonnel.getCurrentState() == 10) {
                    //结束上岗记录
                    MaintenanceLoginRecord maintenanceLoginRecord = maintenancePersonnel.getMaintenanceLoginRecord();
                    maintenanceLoginRecord.setLeaveTime(new Date());
                    loginRecordRepository.save(maintenanceLoginRecord);//更新记录
                    //添加待命记录
                    MaintenanceLoginRecord maintenanceLoginRecord1 = new MaintenanceLoginRecord();
                    maintenanceLoginRecord1.setLoginTime(new Date());
                    maintenanceLoginRecord1.setStatus(20);
                    maintenanceLoginRecord1.setMaintenancePersonnel(maintenancePersonnel);
                    loginRecordRepository.save(maintenanceLoginRecord1);
                    maintenancePersonnel.setMaintenanceLoginRecord(maintenanceLoginRecord1);
                    maintenancePersonnel.setCurrentState(20);
                    maintenancePersonnel.setUuid(uuid);
                    maintenanceRepository.save(maintenancePersonnel);
                    return APIResponse.success("成功");
                } else {
                    //添加待命记录
                    MaintenanceLoginRecord maintenanceLoginRecord1 = new MaintenanceLoginRecord();
                    maintenanceLoginRecord1.setLoginTime(new Date());
                    maintenanceLoginRecord1.setStatus(20);
                    maintenanceLoginRecord1.setMaintenancePersonnel(maintenancePersonnel);
                    loginRecordRepository.save(maintenanceLoginRecord1);
                    maintenancePersonnel.setMaintenanceLoginRecord(maintenanceLoginRecord1);
                    maintenancePersonnel.setCurrentState(20);
                    maintenanceRepository.save(maintenancePersonnel);
                    maintenancePersonnel.setUuid(uuid);
                    return APIResponse.success("成功");
                }
            } else if (status == 30) {
                if (maintenancePersonnel.getCurrentState() == 30) {
                    maintenanceRepository.save(maintenancePersonnel);
                    return APIResponse.success("成功");
                } else if (maintenancePersonnel.getCurrentState() == 10) {
                    //结束待命记录
                    MaintenanceLoginRecord maintenanceLoginRecord = maintenancePersonnel.getMaintenanceLoginRecord();
                    if (maintenanceLoginRecord != null) {
                        maintenanceLoginRecord.setLeaveTime(new Date());
                        loginRecordRepository.save(maintenanceLoginRecord);//更新记录
                    }
                    maintenancePersonnel.setCurrentState(30);
                    maintenanceRepository.save(maintenancePersonnel);
                    return APIResponse.success("成功");
                } else {
                    MaintenanceLoginRecord maintenanceLoginRecord = maintenancePersonnel.getMaintenanceLoginRecord();
                    if (maintenanceLoginRecord != null) {
                        maintenanceLoginRecord.setLeaveTime(new Date());
                        loginRecordRepository.save(maintenanceLoginRecord);//更新记录
                    }
                    maintenancePersonnel.setCurrentState(30);
                    maintenanceRepository.save(maintenancePersonnel);
                    return APIResponse.success("成功");
                }
            } else
                return APIResponse.error("非法状态参数");
        } else
            return APIResponse.error("此员工不存在");
    }


    public List<MaintenancePersonnel> findByMaintainer(String maintainerId) {
        return maintenanceRepository.findByMaintainer(maintainerId);
    }

    //web端接口
    public APIResponse findMaintenancePersonal(ServletRequest request, Integer pageNumber, Integer pageSize) {
        Map<String, Object> searchParams = ServletUtil.getParametersStartingWith(request, "search_");
        Map<String, Object> searchParams_new = new HashMap<>();
        Sort sort = null;
        try {
            User user = userService.getCurrentUser();
            //判断用户是否是维保单位人员，不是则不能查看数据

            if (user.getCompanyType() != null && user.getCompanyType().equals("20")) {
                List<String> idList = maintenancePersonnelRepository.findPersonIdsByCompanyId(user.getCompanyId());
                searchParams.put("id_in", getIds(idList));
            } else {
                Role role=user.getRoles().get(0);
                if (!role.getName().equals("超级管理员")) {
                    searchParams.put("id_in", "none");
                }
            }
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
            searchParams_new.put(("number(TEXT)_LIKE"), searchParams.get("number(TEXT)_LIKE") != null ? searchParams.get("number(TEXT)_LIKE").toString().trim() : "");
            searchParams_new.put(("name(TEXT)_LIKE"), searchParams.get("name(TEXT)_LIKE") != null ? searchParams.get("name(TEXT)_LIKE").toString().trim() : "");
            searchParams_new.put("id_in", searchParams.get("id_in") != null ? searchParams.get("id_in").toString() : "");
            searchParams_new.put("active(TEXT)_LIKE","1");
            if (!"-1".equals(searchParams.get("currentState(TEXT)_LIKE"))&&searchParams.get("currentState(TEXT)_LIKE")!=null) {
                searchParams_new.put("currentState(TEXT)_EQ", searchParams.get("currentState(TEXT)_LIKE"));
            }
            Specification specification = DynamicSpecification.buildSpecification(searchParams_new);
            PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize,sort);
            Page<MaintenancePersonnel> maintenancePersonelsList = maintenancePersonnelRepository.findAll(specification, pageRequest);
            //时间处理
            String startTime = "";
            String endTime = "";
            java.text.SimpleDateFormat sf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(searchParams.get("startTime(TEXT)_LIKE")!=null){
                startTime = searchParams.get("startTime(TEXT)_LIKE").toString();
            }
            if(searchParams.get("endTime(TEXT)_LIKE")!=null){
                endTime= searchParams.get("endTime(TEXT)_LIKE").toString();
            }
            Date time1 = null;
            Date time2 = null;
            if(StringUtils.isNotEmpty(startTime)){
                Date b = null;//不是yyyy-MM-dd格式的date型
                try {
                    b = sf.parse(startTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                java.util.Date be = new java.sql.Date(b.getTime());//转换成了2009-11-04型了
                time1 =be;
            }
            if(StringUtils.isNotEmpty(endTime)){
                Date e = null;
                try {
                    e = sf.parse(endTime);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                java.util.Date en = new java.sql.Date(e.getTime());
                time2 =en;
            }

            List<MaintenancePersonnelDto> list = null;
            try {
                list = toDto(maintenancePersonelsList.getContent(),time1,time2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            PageImpl<MaintenancePersonnelDto> billsPage = new PageImpl<MaintenancePersonnelDto>(list, pageRequest, maintenancePersonelsList.getTotalElements());
            return APIResponse.success(billsPage);

        } catch (JuliException e) {
            return APIResponse.error(e.getMessage());
        }
    }
    public APIResponse searchPersonName(String name,String elevatorId){
        try {
            User user = userService.getCurrentUser();
            List<MaintenancePersonnel> maintenancePersonnels;
            List<MaintenancePersonnelDto> list = new ArrayList<>();
            String name1="%"+name+"%";
            Elevator elevator=elevatorRepository.findElevatorById(elevatorId);
            if(user.getCompanyId()!=null){
                maintenancePersonnels=maintenanceRepository.findByNameLike(name1,user.getCompanyId());
            }
            else
            {
                maintenancePersonnels=maintenanceRepository.findByNameByElevatorId(name1, elevatorId);
            }
            for(MaintenancePersonnel c:maintenancePersonnels)
            {
                Iterator<RepairLevel> repairLevelIterator = c.getLevelList().iterator();
                while (repairLevelIterator.hasNext()) {
                    RepairLevel level = repairLevelIterator.next();
                    if (level.getElevatorBrand().getId().equals(elevator.getBrandId())) {
                        if (level.getName().equals("高") || level.getName().equals("中")) {
                            MaintenancePersonnelDto maintenancePersonnelDto = new MaintenancePersonnelDto();
                            maintenancePersonnelDto.setId(c.getId());
                            maintenancePersonnelDto.setName(c.getName());
                            list.add(maintenancePersonnelDto);
                        }
                    }
                }
            }
            return APIResponse.success(list);
        }catch (JuliException e){
            return APIResponse.error(e.getMessage());
        }

    }

    public APIResponse getPerson(String name,String elevatorId) {
        try{
            MaintenancePersonnel maintenancePersonnel=maintenanceRepository.findByName(name);
            MaintenancePersonnelDto maintenancePersonnelDto=null;
            Elevator elevator=elevatorRepository.findElevatorById(elevatorId);
            Iterator<RepairLevel> repairLevelIterator = maintenancePersonnel.getLevelList().iterator();
            while (repairLevelIterator.hasNext()) {
                RepairLevel level = repairLevelIterator.next();
                if (level.getElevatorBrand().getId().equals(elevator.getBrandId())) {
                    if (level.getName().equals("高") || level.getName().equals("中")) {
                        maintenancePersonnelDto=new MaintenancePersonnelDto();
                        maintenancePersonnelDto.setId(maintenancePersonnel.getId());
                        maintenancePersonnelDto.setName(maintenancePersonnel.getName());
                        maintenancePersonnelDto.setNumber(maintenancePersonnel.getNumber());
                        maintenancePersonnelDto.setTelephone(maintenancePersonnel.getTelephone());

                    }
                }
            }
            if(maintenancePersonnelDto!=null){
                return APIResponse.success(maintenancePersonnelDto);
            }else
            {
                return APIResponse.error("");
            }
        }catch (Exception e){
            return APIResponse.error(e.getMessage());
        }
    }
    public APIResponse searchCompanyName(String name,int type){
        try {
            User user = userService.getCurrentUser();
            List<Company> companies=null;
            String name1="%"+name+"%";
            if(user.getCompanyId()!=null){
                Company company=companyRepository.findById(user.getCompanyId());
                companies.add(company);
            }else
            {
                companies=companyRepository.findByNameAndType(type,name1);
            }
            List<CompanyDto> list = new ArrayList<>();
            for (Company c : companies) {
                CompanyDto companyDto = new CompanyDto();
                companyDto.setId(c.getId());
                companyDto.setName(c.getName());
                list.add(companyDto);
            }
            return APIResponse.success(list);
        }catch (JuliException e){
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

    //获取所有维保人员
    public APIResponse findAllMaintencePersonalDto() {
        try{
            User user=userService.getCurrentUser();
            List<MaintenancePersonnelDto> dtoList = new ArrayList<>();
            if(user.getCompanyId()!=null)
            {
                Iterator<MaintenancePersonnel>   maintenancePersonnelsList = maintenanceRepository.findByMaintainerId(user.getCompanyId()).iterator();
                while (maintenancePersonnelsList.hasNext()) {
                    MaintenancePersonnelDto dto = new MaintenancePersonnelDto();
                    MaintenancePersonnel item=maintenancePersonnelsList.next();
                    dto.setId(item.getId());
                    dto.setNumber(item.getNumber());
                    dto.setName(item.getName());
                    dtoList.add(dto);
                }

            }
            else
            {
                Iterator<MaintenancePersonnel>   maintenancePersonnelsList = maintenanceRepository.findAll().iterator();
                while (maintenancePersonnelsList.hasNext()) {
                    MaintenancePersonnelDto dto = new MaintenancePersonnelDto();
                    MaintenancePersonnel item=maintenancePersonnelsList.next();
                    dto.setId(item.getId());
                    dto.setNumber(item.getNumber());
                    dto.setName(item.getName());
                    dtoList.add(dto);
                }
            }
            return APIResponse.success(dtoList);
        }catch (JuliException e)
        {
            return  APIResponse.error(e.getMessage());
        }
    }
    private void setForeignFieldFromDto(MaintenancePersonnel personnel, MaintenancePersonnelDto dto) {
        if (StringUtils.isNotEmpty(dto.getMaintainerId())) {
            personnel.setMaintainer(companyRepository.findOne(dto.getMaintainerId()));
        } else {
            personnel.setMaintainer(null);
        }
        if (StringUtils.isNotEmpty(dto.getCurrentBillId())) {
            personnel.setCurrentBill(workBillRepository.findOne(dto.getCurrentBillId()));
        } else {
            personnel.setCurrentBill(null);
        }
        if (StringUtils.isNotEmpty(dto.getMaintenanceLoginRecordId())) {
            personnel.setMaintenanceLoginRecord(loginRecordRepository.findOne(dto.getMaintenanceLoginRecordId()));
        } else {
            personnel.setMaintenanceLoginRecord(null);
        }
    }

    //将domian 转换为dto
    private List<MaintenancePersonnelDto> toDto(List<MaintenancePersonnel> maintenancePersonnelList,Date startTime,Date completeTime) throws Exception {
        List<MaintenancePersonnelDto> list = new ArrayList<>();
        for (MaintenancePersonnel personnel : maintenancePersonnelList) {
            MaintenancePersonnelDto dto = new MaintenancePersonnelDto();
            //dto = new MaintenancePersonnelDto().mapFrom(personnel); 影响加载速度，禁止使用该方法
            dto.setId(personnel.getId());
            dto.setNumber(personnel.getNumber());
            dto.setName(personnel.getName());
            dto.setCurrentState(personnel.getCurrentState());
            dto.setManager(personnel.getManager());
            dto.setTelephone(personnel.getTelephone());
            dto.setRegion(personnel.getRegion());
            dto.setStation(personnel.getStation());
            if(null!=personnel&&null!=personnel.getMaintainer()){
                dto.setMaintainerName(personnel.getMaintainer().getName());
            }
            if (personnel.getCurrentBill() != null) {
                dto.setCurrentBillId(personnel.getCurrentBill().getId());
                dto.setBillNumber(personnel.getCurrentBill().getBillNumber());
            }
            //统计相关
            long all =0L;//所有工单数量
            long by =0L;//半月保数量
            long jd =0L;//季度保数量
            long bn =0L;//半年保数量
            long qn =0L;//全年保数量
            long wx =0L;//维修数量
            long jx =0L;//急修数量
            long jj = 0L;
            long excellent =0L;//优秀评价数量
            long good =0L;//良好评价数量
            long common =0L;//一般评价数量
            long poor =0L;//差评价数量
            long noCom =0L;//未评价数量
            java.text.SimpleDateFormat sf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            all = workBillRepository.findAllBillsCount(personnel.getId());//所有工单数量
            if(startTime==null&&completeTime==null){
                by = workBillRepository.findBYBillsCount(personnel.getId());//半月保数量
                jd = workBillRepository.findJDBillsCount(personnel.getId());//季度保数量
                bn = workBillRepository.findBNBillsCount(personnel.getId());//半年保数量
                qn = workBillRepository.findQNBillsCount(personnel.getId());//全年保数量
                wx = workBillRepository.findWXBillsCount(personnel.getId());//维修数量
                jx = workBillRepository.findJXBillsCount(personnel.getId());//急修数量
                jj = workBillRepository.findjjBillsCount(personnel.getId());//拒绝工单数量
                excellent = workBillRepository.findEXCELENTBillsCount(personnel.getId());//优秀评价数量
                good = workBillRepository.findGOODBillsCount(personnel.getId());//良好评价数量
                common = workBillRepository.findCOMMONBillsCount(personnel.getId());//一般评价数量
                poor = workBillRepository.findPOORBillsCount(personnel.getId());//差评价数量
                noCom = workBillRepository.findNOCOMMENTBillsCount(personnel.getId());//未评价数量
            }else {
                if(startTime!=null&&completeTime==null){
                    completeTime = new Date();
                    java.util.Date b = sf.parse(DateUtil.dateToFullString2(completeTime));//不是yyyy-MM-dd格式的date型
                    java.util.Date be = new java.sql.Date(b.getTime());//转换成了2009-11-04型了
                    completeTime = be;
                }else if(startTime==null&&completeTime!=null){
                    startTime = new Date();
                    java.util.Date b = sf.parse(DateUtil.dateToFullString2(startTime));//不是yyyy-MM-dd格式的date型
                    java.util.Date be = new java.sql.Date(b.getTime());//转换成了2009-11-04型了
                    startTime = be;
                }
                by = workBillRepository.findBYBillsCountByTime(personnel.getId(), startTime, completeTime);//半月保数量
                jd = workBillRepository.findJDBillsCountByTime(personnel.getId(), startTime, completeTime);//季度保数量
                bn = workBillRepository.findBNBillsCountByTime(personnel.getId(), startTime, completeTime);//半年保数量
                qn = workBillRepository.findQNBillsCountByTime(personnel.getId(), startTime, completeTime);//全年保数量
                wx = workBillRepository.findWXBillsCountByTime(personnel.getId(), startTime, completeTime);//维修数量
                jx = workBillRepository.findJXBillsCountByTime(personnel.getId(), startTime, completeTime);//急修数量
                jj = workBillRepository.findJjBillsCountByTime(personnel.getId(),startTime,completeTime);
                excellent = workBillRepository.findEXCELENTBillsCountByTime(personnel.getId(), startTime, completeTime);//优秀评价数量
                good = workBillRepository.findGOODBillsCountByTime(personnel.getId(), startTime, completeTime);//良好评价数量
                common = workBillRepository.findCOMMONBillsCountByTime(personnel.getId(), startTime, completeTime);//一般评价数量
                poor = workBillRepository.findPOORBillsCountByTime(personnel.getId(), startTime, completeTime);//差评价数量
                noCom = workBillRepository.findNOCOMMENTBillsCountByTime(personnel.getId(),startTime,completeTime);//未评价数量
            }

            dto.setBillAllCount(all);
            dto.setByCount(by);
            dto.setJdCount(jd);
            dto.setBnCount(bn);
            dto.setQnCount(qn);
            dto.setWxCount(wx);
            dto.setJxCount(jx);
            dto.setExcellent(excellent);
            dto.setGood(good);
            dto.setCommon(common);
            dto.setPoor(poor);
            dto.setNoComment(noCom);
            dto.setJjCount(jj);
            list.add(dto);
        }
        return list;
    }

    public Map<String, Object> getPersonelInfo(String id, Date starttime, Date endtime) {
        Map<String, Object> paramMap = new HashMap<>();
        MaintenancePersonnel maintenancePersonnel = maintenanceRepository.findOne(id);
        paramMap.put("name", maintenancePersonnel.getName() + "/" + maintenancePersonnel.getNumber());
        paramMap.put("id", id);
        List<MaintenanceLoginRecord> list = loginRecordRepository.findByMaintenancePersonnelAndLoginTimeGreaterThanAndLeaveTimeLessThan(maintenancePersonnel, starttime, endtime);
        //在岗时长
        double onduty = 0;
        double standby = 0;
        for (MaintenanceLoginRecord mr : list) {
            if (mr.getLeaveTime() != null && mr.getLoginTime() != null) {
                if (mr.getStatus() == 10)//在岗
                    onduty = onduty + (mr.getLeaveTime().getTime() - mr.getLoginTime().getTime()) / (1000 * 60);
                if (mr.getStatus() == 20) //待命
                    standby = standby + (mr.getLeaveTime().getTime() - mr.getLoginTime().getTime()) / (1000 * 60);
            }

        }
        long days = (int) (endtime.getTime() - starttime.getTime()) / 86400000;
        if (days == 0)
            days = 1;
        paramMap.put("onduty", onduty);
        paramMap.put("standby", standby);
        paramMap.put("avgOnduty", onduty / days);
        paramMap.put("avgStandBy", standby / days);
        long jxbills = workBillRepository.countByAndBillCategoryAndBillstatusAndMaintenancePersonnelAndStartTimeGreaterThanAndCompleteTimeLessThan(30, 50, maintenancePersonnel, starttime, endtime);
        long wbbills = workBillRepository.countByAndBillCategoryAndBillstatusAndMaintenancePersonnelAndStartTimeGreaterThanAndCompleteTimeLessThan(20, 50, maintenancePersonnel, starttime, endtime);
        long wxbills = workBillRepository.countByAndBillCategoryAndBillstatusAndMaintenancePersonnelAndStartTimeGreaterThanAndCompleteTimeLessThan(10, 50, maintenancePersonnel, starttime, endtime);
        paramMap.put("jxbills", jxbills);
        paramMap.put("wbbills", wbbills);
        paramMap.put("wxbills", wxbills);

        paramMap.put("avgbills", (jxbills + wbbills + wxbills) / days);
        return paramMap;
    }

    public Map<String, Object> getmaintenanceInfo(String id, String starttime, String endtime) throws Exception {
        Map<String, Object> paramMap = getPersonelInfo(id, DateUtil.stringToFullDateformat(starttime), DateUtil.stringToFullDateformat(endtime));
        return paramMap;
    }

    public LinkedHashMap<String,List<?>> getBaseInfo(Date time1,Date time2)throws Exception{
        LinkedHashMap<String, List<?>> map = new LinkedHashMap<>();
        List<MaintenancePersonnelDto> list = null;
        List<MaintenancePersonnel> maintenancePersonnelList = null;
        User user = userService.getCurrentUser();
        //判断用户是否是维保单位人员，不是则不能查看数据
        Role role=user.getRoles().get(0);
        if (role.getName().equals("超级管理员")) {
            Iterator<MaintenancePersonnel>   maintenancePersonnelsList = maintenanceRepository.findAll().iterator();
            maintenancePersonnelList = IteratorUtils.toList(maintenancePersonnelsList);
        }else{
            maintenancePersonnelList = maintenancePersonnelRepository.findByComapanyId(user.getCompanyId());
        }
        if(CollectionUtils.isNotEmpty(maintenancePersonnelList)){
            list = toDto(maintenancePersonnelList,time1,time2);
        }
        map.put("员工考核数据", list);
        return map;
    }

    public LinkedHashMap<String,List<?>> getPersonForExport(HttpServletRequest request,String number,String name)throws Exception{
        try {
            Map<String, Object> searchParams = ServletUtil.getParametersStartingWith(request, "search_");
            User user = userService.getCurrentUser();
            //判断用户是否是维保单位人员，不是则不能查看数据
            if (user.getCompanyType() != null && user.getCompanyType().equals("20")) {
                List<String> idList = maintenancePersonnelRepository.findPersonIdsByCompanyId(user.getCompanyId());
                searchParams.put("id_in", getIds(idList));
            } else {
                Role role=user.getRoles().get(0);
                if (!role.getName().equals("超级管理员")) {
                    searchParams.put("id_in", "none");
                }
            }
            searchParams.put("number(TEXT)_LIKE", number != null ? number.trim() : "");
            searchParams.put("name(TEXT)_LIKE", name!=null ? name.trim() : "");
            searchParams.put("id_in", searchParams.get("id_in") != null ? searchParams.get("id_in").toString() : "");
            Specification specification = DynamicSpecification.buildSpecification(searchParams);
            List<MaintenancePersonnel> maintenancePersonnels = maintenancePersonnelRepository.findAll(specification);
            maintenancePersonnels= IteratorUtils.toList(maintenancePersonnels.iterator());
            List<MaintenancePersonnelDto> maintenancePersonnelDtos=new ArrayList<>();
            maintenancePersonnelDtos=toDtoForExportPerson(maintenancePersonnels);
            LinkedHashMap<String, List<?>> map = new LinkedHashMap<>();
            map.put("人员数据", maintenancePersonnelDtos);
            return map;
        }catch (JuliException e)
        {
            return null;
        }
    }

    private List<MaintenancePersonnelDto> toDtoForExportPerson(List<MaintenancePersonnel> maintenancePersonnelList) throws Exception {
        List<MaintenancePersonnelDto> list = new ArrayList<>();
        for (MaintenancePersonnel personnel : maintenancePersonnelList) {
            MaintenancePersonnelDto dto = new MaintenancePersonnelDto();
            dto.setId(personnel.getId());
            dto.setNumber(personnel.getNumber());
            dto.setName(personnel.getName());
            dto.setManager(personnel.getManager());
            dto.setTelephone(personnel.getTelephone());
            dto.setRegion(personnel.getRegion());
            dto.setStation(personnel.getStation());
            if(personnel.getActive().equals("1")){
                dto.setActive("在用");
            }else if(personnel.getActive().equals("0"))
            {
                dto.setActive("已禁用");
            }else {
                dto.setActive("状态未知");
            }
            if(null!=personnel&&null!=personnel.getMaintainer()){
                dto.setMaintainerName(personnel.getMaintainer().getName());
            }
            list.add(dto);
        }
        return list;
    }

    public void exportForPerson(HttpServletResponse response,HttpServletRequest request,String number,String name)throws Exception
    {
        LinkedHashMap<String,List<?>> map=getPersonForExport(request,number, name);
        ExcelUtil.ExcelExportData setInfo = new ExcelUtil.ExcelExportData();
        List<String[]> fieldNames = new ArrayList<>();
        fieldNames.add(new String[]{"number", "name", "manager", "telephone","maintainerName","region", "station","active"});
        List<String[]> columNames = new ArrayList<>();
        columNames.add(new String[]{"编号", "姓名", "维保负责人", "联系方式","维保单位","维保区域", "维保站","人员状态"});
        setInfo.setDataMap(map);
        setInfo.setFieldNames(fieldNames);
        setInfo.setTitles(new String[]{"维保人员数据"});
        setInfo.setColumnNames(columNames);
        ExcelUtil.export2Browser(response, setInfo, "人员数据");
    }
    public void export(HttpServletResponse response,String startTime,String endTime) throws Exception{
        //时间处理
        java.text.SimpleDateFormat sf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time1 = null;
        Date time2 = null;
        if(StringUtils.isNotEmpty(startTime)){
            Date b = null;//不是yyyy-MM-dd格式的date型
            try {
                b = sf.parse(startTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            java.util.Date be = new java.sql.Date(b.getTime());//转换成了2009-11-04型了
            time1 =be;
        }
        if(StringUtils.isNotEmpty(endTime)){
            Date e = null;
            try {
                e = sf.parse(endTime);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            java.util.Date en = new java.sql.Date(e.getTime());
            time2 =en;
        }

        LinkedHashMap<String,List<?>> map=getBaseInfo(time1,time2);
        List<String[]> fieldNames = new ArrayList<>();
        fieldNames.add(new String[]{"number", "name", "billAllCount","byCount","jdCount","bnCount","qnCount","wxCount","jxCount","jjCount","excellent","good","common","poor","noComment"});
        List<String[]> columNames = new ArrayList<>();
        columNames.add(new String[]{"编号", "姓名", "工单总数","半月保总数","季度保总数","半年保总数","全年保总数","维修总数","急修总数","工单拒绝总数","优秀评价总数","良好评价总数","一般评价总数","差评总数","未评价总数"});
        ExcelUtil.ExcelExportData setInfo = new ExcelUtil.ExcelExportData();
        setInfo.setDataMap(map);
        setInfo.setFieldNames(fieldNames);
        setInfo.setTitles(new String[]{"员工考核数据"});
        setInfo.setColumnNames(columNames);
        ExcelUtil.export2Browser(response, setInfo, "员工考核数据");
    }

    public String exportExcel(HttpServletRequest request, HttpServletResponse response, String id, String starttime, String endtime) throws Exception {


        Map<String, Object> paramMap = getPersonelInfo(id, DateUtil.stringToFullDateformat(starttime), DateUtil.stringToFullDateformat(endtime));

        String title = "维保人员综合信息";
        String headers[] = {"姓名", "在岗时长", "待命时长 ", " 完成保养工单数量", " 完成维修工单数量", " 完成急修工单数量 ", " 日均在岗时间", "日均待命时间 ", "日均完成工单"};
        String filename = "miantenanceInfo.xls";
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + filename);       //指定下载的文件名
        OutputStream output = response.getOutputStream();
        HSSFWorkbook work = new HSSFWorkbook();
        exportMaintenanceExcel(work, title, headers, paramMap);
        work.write(output);
        output.flush();
        output.close();
        return null;
    }

    public void exportMaintenanceExcel(HSSFWorkbook workbook, String title, String[] headers, Map<String, Object> map) throws IOException {
        // 声明一个工作薄
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth(15);


        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        row = sheet.createRow(1);
        //姓名
        HSSFCell cellNumber = row.createCell(0);
        HSSFRichTextString number = new HSSFRichTextString((String) map.get("name"));
        cellNumber.setCellValue(number);
        //在岗时长
        HSSFCell cell1 = row.createCell(1);
        HSSFRichTextString number1 = new HSSFRichTextString(map.get("onduty").toString());
        cell1.setCellValue(number1);
        //待命时长
        HSSFCell cell2 = row.createCell(2);
        HSSFRichTextString number2 = new HSSFRichTextString(map.get("standby").toString());
        cell2.setCellValue(number2);
        //完成保养工单数量
        HSSFCell cell3 = row.createCell(3);
        HSSFRichTextString number3 = new HSSFRichTextString(map.get("wbbills").toString());
        cell3.setCellValue(number3);
        //完成维修工单数量
        HSSFCell cell4 = row.createCell(4);
        HSSFRichTextString number4 = new HSSFRichTextString(map.get("wxbills").toString());
        cell4.setCellValue(number4);
        //完成急修工单数量
        HSSFCell cell5 = row.createCell(5);
        HSSFRichTextString number5 = new HSSFRichTextString(map.get("jxbills").toString());
        cell5.setCellValue(number5);
        //日均在岗时间
        HSSFCell cell6 = row.createCell(6);
        HSSFRichTextString number6 = new HSSFRichTextString(map.get("avgOnduty").toString());
        cell6.setCellValue(number6);
        //日均待命时间
        HSSFCell cell7 = row.createCell(7);
        HSSFRichTextString number7 = new HSSFRichTextString(map.get("avgStandBy").toString());
        cell7.setCellValue(number7);
        //日均完成工单
        HSSFCell cell8 = row.createCell(8);
        HSSFRichTextString number8 = new HSSFRichTextString(map.get("avgbills").toString());
        cell8.setCellValue(number8);


    }
}
