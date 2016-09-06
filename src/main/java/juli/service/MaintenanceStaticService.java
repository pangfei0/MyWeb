package juli.service;

import juli.api.core.APIResponse;
import juli.api.dto.BillStaticsDto;
import juli.api.dto.MaintenancePersonnelDto;
import juli.domain.*;
import juli.infrastructure.DateUtil;
import juli.infrastructure.ServletUtil;
import juli.infrastructure.excel.ExcelUtil;
import juli.infrastructure.exception.JuliException;
import juli.infrastructure.persist.DynamicSpecification;
import juli.repository.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang3.StringUtils;
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
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;

/**
 * Created by pf on 2016/2/1.
 */
@Service
public class MaintenanceStaticService {
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
    ReportRepository reportRepository;



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
                Date be = new java.sql.Date(b.getTime());//转换成了2009-11-04型了
                time1 =be;
            }
            if(StringUtils.isNotEmpty(endTime)){
                Date e = null;
                try {
                    e = sf.parse(endTime);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                Date en = new java.sql.Date(e.getTime());
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
            Double jxCostTime= 0.0;
            Double wxCostTime=0.0;
            java.text.SimpleDateFormat sf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(startTime==null&&completeTime==null){
                all = workBillRepository.findAllBillsCount(personnel.getId());//所有工单数量
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
                jxCostTime = workBillRepository.findJXBillsAVGTime(personnel.getId());
                wxCostTime = workBillRepository.findWXBillsAVGTime(personnel.getId());
            }else {
                if(startTime!=null&&completeTime==null){
                    completeTime = new Date();
                    Date b = sf.parse(DateUtil.dateToFullString2(completeTime));//不是yyyy-MM-dd格式的date型
                    Date be = new java.sql.Date(b.getTime());//转换成了2009-11-04型了
                    completeTime = be;
                }else if(startTime==null&&completeTime!=null){
                    startTime = new Date();
                    Date b = sf.parse(DateUtil.dateToFullString2(startTime));//不是yyyy-MM-dd格式的date型
                    Date be = new java.sql.Date(b.getTime());//转换成了2009-11-04型了
                    startTime = be;
                }
                all = workBillRepository.findAllBillsCountByTime(personnel.getId(), startTime, completeTime);//所有工单数量
                by = workBillRepository.findBYBillsCountByTime(personnel.getId(), startTime, completeTime);//半月保数量
                jd = workBillRepository.findJDBillsCountByTime(personnel.getId(), startTime, completeTime);//季度保数量
                bn = workBillRepository.findBNBillsCountByTime(personnel.getId(), startTime, completeTime);//半年保数量
                qn = workBillRepository.findQNBillsCountByTime(personnel.getId(), startTime, completeTime);//全年保数量
                wx = workBillRepository.findWXBillsCountByTime(personnel.getId(), startTime, completeTime);//维修数量
                jx = workBillRepository.findJXBillsCountByTime(personnel.getId(), startTime, completeTime);//急修数量
                jj = workBillRepository.findJjBillsCountByTime(personnel.getId(), startTime, completeTime);
                excellent = workBillRepository.findEXCELENTBillsCountByTime(personnel.getId(), startTime, completeTime);//优秀评价数量
                good = workBillRepository.findGOODBillsCountByTime(personnel.getId(), startTime, completeTime);//良好评价数量
                common = workBillRepository.findCOMMONBillsCountByTime(personnel.getId(), startTime, completeTime);//一般评价数量
                poor = workBillRepository.findPOORBillsCountByTime(personnel.getId(), startTime, completeTime);//差评价数量
                noCom = workBillRepository.findNOCOMMENTBillsCountByTime(personnel.getId(), startTime, completeTime);//未评价数量N
                jxCostTime = workBillRepository.findJXBillsAVGTimeByTime(personnel.getId(), startTime, completeTime);
                wxCostTime = workBillRepository.findWXBillsAVGTimeByTime(personnel.getId(),startTime,completeTime);
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
            dto.setWbCount(all-wx-jx);
            //计算总满意度
            DecimalFormat df = new DecimalFormat("0.00");//格式化小数
            if(0!=excellent+good+common+poor){
                Double a = (double)(excellent*5+good*4+common*3)/(double) (excellent+good+common+poor);
                dto.setSatisfaction(df.format(a));
            }else{
                dto.setSatisfaction("0");
            }
            //维修急修满意度
            if(0!=excellent+good+common+poor){
                Double a = (double)(excellent*5+good*4+common*3)/(double) (excellent+good+common+poor);
                dto.setWjxsatisfaction(df.format(a));
            }else{
                dto.setWjxsatisfaction("0");
            }
            //故障次数
            dto.setWxjxCount(String.valueOf(wx + jx));

            if(null!=jxCostTime){
                dto.setJxCostTime(df.format(jxCostTime));
            }else{
                dto.setJxCostTime("无故障统计");
            }
            if(null!=wxCostTime){
                dto.setWxCostTime(df.format(wxCostTime));
            }else{
                dto.setWxCostTime("无故障统计");
            }

            list.add(dto);
        }
        return list;
    }


    //导出
    public void workBillExport(HttpServletResponse response,String startTime,String endTime) throws Exception{
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
        fieldNames.add(new String[]{"number", "name", "station","region","billAllCount","byCount","jdCount","bnCount","qnCount","wxCount","jxCount","jjCount"});
        List<String[]> columNames = new ArrayList<>();
        columNames.add(new String[]{"员工编号", "员工姓名", "维保站","区域","总台量","半月保总数","季度保总数","半年保总数","全年保总数","维修总数","急修总数","工单拒绝总数"});
        ExcelUtil.ExcelExportData setInfo = new ExcelUtil.ExcelExportData();
        setInfo.setDataMap(map);
        setInfo.setFieldNames(fieldNames);
        setInfo.setTitles(new String[]{"台量统计数据"});
        setInfo.setColumnNames(columNames);
        ExcelUtil.export2Browser(response, setInfo, "台量统计数据");
    }

    public void satisfactionExport(HttpServletResponse response,String startTime,String endTime) throws Exception{
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
        fieldNames.add(new String[]{"number", "name", "station","region","excellent","good","common","poor","noComment"});
        List<String[]> columNames = new ArrayList<>();
        columNames.add(new String[]{"员工编号", "员工姓名", "维保站","区域","优秀","良好","一般","差","未评价"});
        ExcelUtil.ExcelExportData setInfo = new ExcelUtil.ExcelExportData();
        setInfo.setDataMap(map);
        setInfo.setFieldNames(fieldNames);
        setInfo.setTitles(new String[]{"满意度统计数据"});
        setInfo.setColumnNames(columNames);
        ExcelUtil.export2Browser(response, setInfo, "满意度统计数据");
    }

    public void resultExport(HttpServletResponse response,String startTime,String endTime) throws Exception{
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
        fieldNames.add(new String[]{"number", "name", "station","region","wbCount","wxCount","jxCount","satisfaction"});
        List<String[]> columNames = new ArrayList<>();
        columNames.add(new String[]{"员工编号", "员工姓名", "维保站","区域","维保总台量","维修总台量","急修总台量","满意度(满分5分)"});
        ExcelUtil.ExcelExportData setInfo = new ExcelUtil.ExcelExportData();
        setInfo.setDataMap(map);
        setInfo.setFieldNames(fieldNames);
        setInfo.setTitles(new String[]{"业绩统计数据"});
        setInfo.setColumnNames(columNames);
        ExcelUtil.export2Browser(response, setInfo, "业绩统计数据");
    }

    public void faultExport(HttpServletResponse response,String startTime,String endTime) throws Exception{
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
        fieldNames.add(new String[]{"number", "name", "station","region","wxjxCount","wxCostTime","jxCostTime","wjxsatisfaction"});
        List<String[]> columNames = new ArrayList<>();
        columNames.add(new String[]{"员工编号", "员工姓名", "维保站","区域","故障次数","维修平均花费时间(分钟)","急修平均花费时间(分钟)","满意度(满分5分)"});
        ExcelUtil.ExcelExportData setInfo = new ExcelUtil.ExcelExportData();
        setInfo.setDataMap(map);
        setInfo.setFieldNames(fieldNames);
        setInfo.setTitles(new String[]{"故障统计数据"});
        setInfo.setColumnNames(columNames);
        ExcelUtil.export2Browser(response, setInfo, "故障统计数据");
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
        map.put("考核数据", list);
        return map;
    }

    public Map billStatistic(Date startTime,Date completeTime,String personnelId){
        Map map = new HashMap();
        long all = workBillRepository.findAllBillsCount(personnelId);//所有工单数量
        long by = workBillRepository.findBYBillsCountByTime(personnelId, startTime, completeTime);//半月保数量
        long jd = workBillRepository.findJDBillsCountByTime(personnelId, startTime, completeTime);//季度保数量
        long bn = workBillRepository.findBNBillsCountByTime(personnelId, startTime, completeTime);//半年保数量
        long qn = workBillRepository.findQNBillsCountByTime(personnelId, startTime, completeTime);//全年保数量
        long wx = workBillRepository.findWXBillsCountByTime(personnelId, startTime, completeTime);//维修数量
        long jx = workBillRepository.findJXBillsCountByTime(personnelId, startTime, completeTime);//急修数量
        long excellent = workBillRepository.findEXCELENTBillsCountByTime(personnelId, startTime, completeTime);//优秀评价数量
        long good = workBillRepository.findGOODBillsCountByTime(personnelId, startTime, completeTime);//良好评价数量
        long common = workBillRepository.findCOMMONBillsCountByTime(personnelId, startTime, completeTime);//一般评价数量
        long poor = workBillRepository.findPOORBillsCountByTime(personnelId, startTime, completeTime);//差评价数量
        long noCom = workBillRepository.findNOCOMMENTBillsCountByTime(personnelId, startTime, completeTime);//未评价数量
        Double jxCostTime = workBillRepository.findJXBillsAVGTimeByTime(personnelId, startTime, completeTime);
        Double wxCostTime = workBillRepository.findWXBillsAVGTimeByTime(personnelId, startTime, completeTime);
        Double byCostTime = workBillRepository.findBYBillsAVGTimeByTime(personnelId, startTime, completeTime);
        Double jdCostTime = workBillRepository.findJDBillsAVGTimeByTime(personnelId, startTime, completeTime);
        Double bnCostTime = workBillRepository.findBNBillsAVGTimeByTime(personnelId, startTime, completeTime);
        Double qnCostTime = workBillRepository.findQNBillsAVGTimeByTime(personnelId, startTime, completeTime);
        map.put("startTime",startTime);
        map.put("endTime",completeTime);
        map.put("allCount",all);
        map.put("wbAllCount",by+bn+jd+qn);
        map.put("byCount",by);
        map.put("jdCount",jd);
        map.put("bnCount",bn);
        map.put("qnCount",qn);
        map.put("wxCount",wx);
        map.put("jxCount",jx);
        map.put("excellent",excellent);
        map.put("good",good);
        map.put("common", common);
        map.put("poor", poor);
        map.put("noCom", noCom);
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数
        if(null!=jxCostTime){
            map.put("jxCostTime", df.format(jxCostTime));
        }else{
            map.put("jxCostTime", "无");//无急修可统计
        }
        if(null!=wxCostTime){
            map.put("wxCostTime", df.format(wxCostTime));
        }else{
            map.put("wxCostTime", "无");//无维修可统计
        }
        if(null!=byCostTime){
            map.put("byCostTime", df.format(byCostTime));
        }else{
            map.put("byCostTime", "无");//无半月保可统计
        }
        if(null!=jdCostTime){
            map.put("jdCostTime", df.format(jdCostTime));
        }else{
            map.put("jdCostTime", "无");//无季度保可统计
        }
        if(null!=bnCostTime){
            map.put("bnCostTime", df.format(bnCostTime));
        }else{
            map.put("bnCostTime", "无");//无半年保可统计
        }
        if(null!=qnCostTime){
            map.put("qnCostTime", df.format(qnCostTime));
        }else{
            map.put("qnCostTime", "无");//无年保可统计
        }
        return map;
    }

    public APIResponse getBillsByType(Date startTime,Date completeTime,String personnelId,String type,Integer pageNumber,Integer pageSize){
        List<Map>  mapList=new ArrayList<>();
        Map map = new HashMap();
        if("1".equals(type)){
            Page<WorkBills> workBillsPage = workBillRepository.findWBWorkBillsByTime(personnelId, startTime, completeTime,new PageRequest(pageNumber - 1, pageSize));
            mapList = toMapList(workBillsPage);
        }else if("2".equals(type)){
            Page<WorkBills> workBillsPage = workBillRepository.findJXWorkBillsByTime(personnelId,startTime,completeTime,new PageRequest(pageNumber - 1, pageSize));
            mapList = toMapList(workBillsPage);
        }else if("3".equals(type)){
            Page<WorkBills> workBillsPage = workBillRepository.findWXWorkBillsByTime(personnelId, startTime, completeTime,new PageRequest(pageNumber - 1, pageSize));
            mapList = toMapList(workBillsPage);
        }else{
            map.put("result","type传值不正确，1:维保 2：急修 3：维修");
            mapList.add(map);
        }
        return APIResponse.success(mapList);
    }

    private List<Map> toMapList(Page<WorkBills> workBillsPage){
        List<Map> mapList = new ArrayList<>();
        int i = 1;
        if(CollectionUtils.isNotEmpty(workBillsPage.getContent())){
            for(WorkBills workBills:workBillsPage.getContent()){
                Map map = new HashMap();
                map.put("No",i);//计数
                map.put("workBillId",workBills.getId());//工单id
                map.put("number",workBills.getEnumber());//电梯工号
                map.put("startTime",workBills.getStartTime());//处理开始时间
                map.put("completeTime",workBills.getCompleteTime());//完成时间
                mapList.add(map);
                i++;
            }
        }
        return mapList;
    }

    public APIResponse getBillsByComment(Date startTime,Date completeTime,String personnelId,String type,Integer pageNumber,Integer pageSize){
        List<Map>  mapList=new ArrayList<>();
        Map map = new HashMap();
        if("1".equals(type)){
            Page<Reports> reportsPage = reportRepository.findWorkBillsByGeneralEvaluate(personnelId,startTime,completeTime,10,new PageRequest(pageNumber - 1, pageSize));
            mapList = toReportsMapList(reportsPage);
        }else if("2".equals(type)){
            Page<Reports> reportsPage = reportRepository.findGoodWorkBills(personnelId, startTime, completeTime, new PageRequest(pageNumber - 1, pageSize));
            mapList = toReportsMapList(reportsPage);
        }else if("3".equals(type)){
            Page<Reports> reportsPage = reportRepository.findWorkBillsByGeneralEvaluate(personnelId,startTime,completeTime,40,new PageRequest(pageNumber - 1, pageSize));
            mapList = toReportsMapList(reportsPage);
        }else{
            map.put("result","type传值不正确，1:优秀 2：良好 3：差");
            mapList.add(map);
        }
        return APIResponse.success(mapList);
    }

    private List<Map> toReportsMapList(Page<Reports> reportsPage){
        List<Map> mapList = new ArrayList<>();
        int i = 1;
        if(CollectionUtils.isNotEmpty(reportsPage.getContent())){
            for(Reports reports:reportsPage.getContent()){
                Map map = new HashMap();
                if(null!=reports&&null!=reports.getCurrentBill()){
                    map.put("No",i);//计数
                    map.put("workBillId",reports.getCurrentBill().getId());//工单id
                    map.put("number",reports.getCurrentBill().getEnumber());//电梯工号
                    map.put("startTime",reports.getCurrentBill().getStartTime());//处理开始时间
                    map.put("completeTime",reports.getCurrentBill().getCompleteTime());//完成时间
                    mapList.add(map);
                    i++;
                }
            }
        }
        return mapList;
    }

}
