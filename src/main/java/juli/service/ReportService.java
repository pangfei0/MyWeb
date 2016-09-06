package juli.service;

import freemarker.template.utility.StringUtil;
import juli.api.core.APIResponse;
import juli.api.dto.ReportDto;
import juli.domain.*;
import juli.domain.enums.Appraise;
import juli.domain.enums.BillReportCategory;
import juli.domain.enums.MaintainProperty;
import juli.domain.enums.ReportStatus;
import juli.infrastructure.DateUtil;
import juli.infrastructure.ServletUtil;
import juli.infrastructure.exception.JuliException;
import juli.infrastructure.persist.DynamicSpecification;
import juli.repository.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by pf on 2016/2/4.
 */
@Service
public class ReportService {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    ReportRepository reportRepository;
    @Autowired
    MaintenanceRepository maintenanceRepository;
    @Autowired
    ElevatorRepository elevatorRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    WorkBillRepository workBillRepository;
    @Autowired
    UserService userService;

    /*
    分页获取报告
     */
    public PageImpl findPersonalReportByCategroyAndStatus(String id, Integer reportCategory, Integer reportStatus, Integer pageNumber, Integer pageSize) throws Exception {
        PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "reportStatus");

        Page<Reports> list = reportRepository.findPersonalReportByCategroyAndStatus(id, reportCategory, reportStatus, pageRequest);
        Map reportMap;
        List<Map> mapList = new ArrayList<>();
        for (Reports report : list.getContent()) {
            reportMap = new HashMap<String, Object>();
            reportMap.put("id", report.getId());
            reportMap.put("statusName", ReportStatus.get(report.getReportStatus()).getName());
            reportMap.put("status", report.getReportStatus());
            reportMap.put("category", report.getReportCategory());
            String completetime = report.getCompleteTime() == null ? "" : DateUtil.dateToFullString2(report.getCompleteTime());
            reportMap.put("completetime", completetime);
            if (report.getElevator() != null) {
                Elevator elevator = report.getElevator();
                reportMap.put("elevator", elevator.getNumber() + "/" + elevator.getAlias());
                reportMap.put("equipmentNumber",elevator.getEquipmentNumber());
            }

            mapList.add(reportMap);
        }

        PageImpl<Map> reportsPage = new PageImpl<>(mapList, pageRequest, list.getTotalElements());
        return reportsPage;
    }

    /*
    根据报告id获取报告详细
     */
    public APIResponse getReportById(String id) throws Exception {
        Reports reports = reportRepository.findOne(id);
        if (reports != null) {
            String starttime = reports.getStartTime() == null ? " " : DateUtil.dateToFullString2(reports.getStartTime());
            String entertime = reports.getEnterTime() == null ? " " : DateUtil.dateToFullString2(reports.getEnterTime());
            String completetime = reports.getCompleteTime() == null ? " " : DateUtil.dateToFullString2(reports.getCompleteTime());
            String reporttime = reports.getReportTime() == null ? " " : DateUtil.dateToFullString2(reports.getReportTime());
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("reportCategory", reports.getReportCategory());
            if (reports.getReportCategory() == 10 || reports.getReportCategory() == 30)//维修报告或者急修报告
            {
                paramMap.put("elevatornumber", reports.getElevator().getNumber());
//                paramMap.put("upkeepcontractnumber", reports.getElevator().getUpkeepContract().getNumber());
                paramMap.put("completetime", completetime);
                paramMap.put("starttime", starttime);
                paramMap.put("maintenancename", reports.getMaintenancePersonnel().getNumber() + "/" + reports.getMaintenancePersonnel().getName());
                if(StringUtils.isNotEmpty(reports.getElevator().getMaintainerId())){
                    Company company=companyRepository.findById(reports.getElevator().getMaintainerId());
                    if(null!=company){
                        paramMap.put("company",company.getName());
                    }
                }else{
                    paramMap.put("company","无");
                }
                paramMap.put("localdescription", reports.getDescription());
                paramMap.put("faultQuality", reports.getFaultQuality());
                paramMap.put("result", reports.getResult());

                if (reports.getReportCategory() == 30)
                    paramMap.put("entertime", entertime);
                paramMap.put("serviceattidute", Appraise.get(reports.getServiceAttitude() == null ? 10 : reports.getServiceAttitude()).getName());
                paramMap.put("envandsafe", Appraise.get(reports.getEnvAndSafe() == null ? 10 : reports.getEnvAndSafe()).getName());
                paramMap.put("servicelevel", Appraise.get(reports.getServiceLevel() == null ? 10 : reports.getServiceLevel()).getName());
                paramMap.put("resolvequestion", Appraise.get(reports.getResolveQuestion() == null ? 10 : reports.getResolveQuestion()).getName());
                paramMap.put("suggestions", reports.getSuggestions());
                paramMap.put("sign", reports.getSign());
                paramMap.put("reportime", reporttime);
            } else if (reports.getReportCategory() == 20) {

                paramMap.put("elevatornumber", reports.getElevator().getNumber());
//                paramMap.put("upkeepcontractnumber", reports.getElevator().getUpkeepContract().getNumber());
                paramMap.put("completetime", completetime);
                paramMap.put("starttime", starttime);
                paramMap.put("maintenancename", reports.getMaintenancePersonnel().getNumber() + "/" + reports.getMaintenancePersonnel().getName());
                if(reports.getElevator().getMaintainerId() !=null){
                    Company company=companyRepository.findById(reports.getElevator().getMaintainerId());
                    paramMap.put("company",company.getName());
                }else{
                    paramMap.put("company","无");
                }
                paramMap.put("result", reports.getResult());
                paramMap.put("afterbill", reports.getAfterBill() == null ? "" : reports.getAfterBill().getBillNumber());
                paramMap.put("serviceattidute", Appraise.get(reports.getServiceAttitude() == null ? 10 : reports.getServiceAttitude()).getName());
                paramMap.put("envandsafe", Appraise.get(reports.getEnvAndSafe() == null ? 10 : reports.getEnvAndSafe()).getName());
                paramMap.put("servicelevel", Appraise.get(reports.getServiceLevel() == null ? 10 : reports.getServiceLevel()).getName());
                paramMap.put("resolvequestion", Appraise.get(reports.getResolveQuestion() == null ? 10 : reports.getResolveQuestion()).getName());
                paramMap.put("suggestions", reports.getSuggestions());
                paramMap.put("sign", reports.getSign());
                paramMap.put("reportime", reporttime);
            }
            return APIResponse.success(paramMap);

        } else {
            return APIResponse.error("不存在此报告");
        }

    }

    /*
    提交报告
     */
    public APIResponse commitReport(String id, Integer serviceAttitude, Integer servicLevel, Integer envAndSafe, Integer resolveQuestion, String suggestions,String picPath,Integer generalEvaluate) throws IOException {
        Reports reports = reportRepository.findOne(id);

        if (reports != null) {
//            MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
//
//            MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
//            ServletRequest request1 = ((ShiroHttpServletRequest) request).getRequest();
//            //对图片处理保存
//            uploadFiles(multipartRequest, reports);
            reports.setReportStatus(20);
            reports.setServiceAttitude(serviceAttitude);
            reports.setServiceLevel(servicLevel);
            reports.setEnvAndSafe(envAndSafe);
            reports.setGeneralEvaluate(generalEvaluate);
            reports.setSign(picPath);
            reports.setResolveQuestion(resolveQuestion);
            reports.setSuggestions(suggestions);
            reports.setReportTime(new Date());
            reports.setCompleteTime(new Date());
            reportRepository.save(reports);
            WorkBills bill=reports.getCurrentBill();
            //更改工单状态为完成
            bill.setBillstatus(50);
//            bill.setCompleteTime(new Date());
            workBillRepository.save(bill);
            return APIResponse.success();
        } else {
            return APIResponse.error("不存在此报告");
        }

    }

    public APIResponse commitReportPicture(String id,String path) throws IOException {
        Reports reports = reportRepository.findOne(id);

        if (reports != null) {
            reports.setSign(path);
//            uploadFiles(request1, reports);
            reportRepository.save(reports);
            return APIResponse.success();
        } else {
            return APIResponse.error("不存在此报告");
        }
    }

    /*
    根据工单生成对应的报告
     */
    public APIResponse createReport(WorkBills bills) {
        Reports reports = new Reports();
        reports.setReportStatus(10);
        reports.setCurrentBill(bills);
        reports.setReportCategory(bills.getBillCategory());
        if(bills.getMaintenancePersonnel().getMaintainer()!=null){
            reports.setCompany(bills.getMaintenancePersonnel().getMaintainer());
        }
        if (bills.getBillCategory() == 10)//维修报告
        {
            reports.setReportNumber(bills.getBillNumber());
            reports.setElevator(bills.getElevator());
            reports.setStartTime(bills.getStartTime());
//            reports.setCompleteTime(bills.getCompleteTime());
            reports.setMaintenancePersonnel(bills.getMaintenancePersonnel());
            reports.setDescription(bills.getLocalDescription());
            reports.setFaultQuality(bills.getFaultQuality());
            reports.setResult(bills.getResult());

        } else if (bills.getBillCategory() == 30)//急修报告
        {
            reports.setReportNumber(bills.getBillNumber());
            reports.setElevator(bills.getElevator());
            reports.setStartTime(bills.getStartTime());//响应时间
            reports.setEnterTime(bills.getEnterTime());
//            reports.setCompleteTime(bills.getCompleteTime());
            reports.setMaintenancePersonnel(bills.getMaintenancePersonnel());
            reports.setDescription(bills.getLocalDescription());
            reports.setFaultQuality(bills.getFaultQuality());
            reports.setResult(bills.getResult());
        } else if (bills.getBillCategory() == 20)//维保报告
        {
            reports.setReportNumber(bills.getBillNumber());
            reports.setElevator(bills.getElevator());
            reports.setStartTime(bills.getStartTime());
//            reports.setCompleteTime(bills.getCompleteTime());
            reports.setAfterBill(bills.getAfterbill());
            reports.setMaintenancePersonnel(bills.getMaintenancePersonnel());
            reports.setResult(bills.getResult());//未决事宜
        }
        reports.setCurrentBill(bills);
        reportRepository.save(reports);
        return APIResponse.success();

    }


    //web 接口
    public APIResponse findReports(ServletRequest request, Integer pageNumber, Integer pageSize) {
        try {
            User user = userService.getCurrentUser();
            List<String> idList;
            Page<Reports> reportList = null;
            Sort sort = null;
            Map<String, Object> searchParams = ServletUtil.getParametersStartingWith(request, "search_");
            if(user.getCompanyId() != null&&!user.getCompanyId().equals("")&&user.getCompanyType().equals("20")){
                if(searchParams.containsKey("maintenanceName(TEXT)_LIKE")){
                   String companyId=reportRepository.findCompanyId("%"+searchParams.get("maintenanceName(TEXT)_LIKE").toString()+"%");
                    if(companyId!=user.getCompanyId()){
                        searchParams.put("id_in", "none");
                    }else
                    {
                        idList=reportRepository.findReportsIdsByPersonName("%"+searchParams.get("maintenanceName(TEXT)_LIKE").toString()+"%");
                        searchParams.put("id_in", getIds(idList));
                    }
                    searchParams.remove("maintenanceName(TEXT)_LIKE");
                }else
                {
                    idList=reportRepository.findReportsIds(user.getCompanyId());
                    searchParams.put("id_in", getIds(idList));
                }

            }
            else
            {
                Role role=user.getRoles().get(0);
                if(!role.getName().equals("超级管理员")) {
                    searchParams.put("id_in","none");
                    if(searchParams.containsKey("maintenanceName(TEXT)_LIKE")){
                        searchParams.remove("maintenanceName(TEXT)_LIKE");
                    }
                }else
                {
                    if(searchParams.containsKey("maintenanceName(TEXT)_LIKE")){
                        idList=reportRepository.findReportsIdsByPersonName("%"+searchParams.get("maintenanceName(TEXT)_LIKE").toString()+"%");
                        searchParams.put("id_in", getIds(idList));
                        searchParams.remove("maintenanceName(TEXT)_LIKE");
                    }
                }
            }
            String sortName = request.getParameter("sortName");
            String sortOrder = request.getParameter("sortOrder");
            if (sortName == null) {
                sort = new Sort(Sort.Direction.DESC, "createdDate");
            } else {
                if ("reportCategory".equals(sortName)) {
                    sortName = "reportCategory";
                }
                if ("reportTimeStr".equals(sortName)) {
                    sortName = "reportTimeStr";
                }
                if ("asc".equals(sortOrder)) {
                    sort = new Sort(Sort.Direction.ASC, sortName);
                } else {
                    sort = new Sort(Sort.Direction.DESC, sortName);
                }
            }
            PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize,sort);
            searchParams.put("reportStatus_eq","20");//只查询已评价的报告
            Specification specification = DynamicSpecification.buildSpecification(searchParams);
            reportList = reportRepository.findAll(specification,pageRequest);
            List<ReportDto> list = this.toDto(reportList.getContent());
            PageImpl<ReportDto> reportsPage = new PageImpl<ReportDto>(list, pageRequest, reportList.getTotalElements());
            return APIResponse.success(reportsPage);
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

    public String exportExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> searchParams = ServletUtil.getParametersStartingWith(request, "search_");
        Specification specification = DynamicSpecification.buildSpecification(searchParams);
        List<Reports> reportList = reportRepository.findAll(specification);

        String title = "报告信息";
        String headers[] = {"报告编号", "电梯编号", "报告状态 ", " 报告类型", " 开始时间(维修/维保)/响应时间(急修)", " 情况描述 ", " 故障性质/未决事宜", "处理结果 ", "服务态度", "服务水平", " 维护客户环境和安全 ", "问题解决和故障排除 ", " 建议", "完成时间", " 进场时间 ", "报告日期", " 维修人员/维保人员", " 维修单位/保养单位", " 后续工单"};
        String filename = "reports.xls";
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + filename);       //指定下载的文件名
        OutputStream output = response.getOutputStream();
        HSSFWorkbook work = new HSSFWorkbook();
        exportReportExcel(work, title, headers, reportList);
        work.write(output);
        output.flush();
        output.close();
        return null;
    }

    public void exportReportExcel(HSSFWorkbook workbook, String title, String[] headers, List<Reports> dataset) throws IOException {
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
        int index = 0;
        for (Reports dto : dataset) {
            index++;
            row = sheet.createRow(index);
            //报告编号       维护客户环境和安全 问题解决和故障排除  建议 进场时间 报告日期 维修人员/维保人员 维修单位/保养单位 后续工单
            HSSFCell cellNumber = row.createCell(0);
            HSSFRichTextString number = new HSSFRichTextString(dto.getReportNumber());
            cellNumber.setCellValue(number);
            //电梯编号
            HSSFCell cellElevator = row.createCell(1);
            HSSFRichTextString elevatorNumber = new HSSFRichTextString(dto.getElevator().getNumber());
            cellElevator.setCellValue(elevatorNumber);
            //报告状态
            HSSFCell cell2 = row.createCell(2);
            HSSFRichTextString name2 = new HSSFRichTextString(ReportStatus.get(dto.getReportStatus() == null ? 10 : 20).getName());
            cell2.setCellValue(name2);
            //报告类型
            HSSFCell cell3 = row.createCell(3);
            HSSFRichTextString name3 = new HSSFRichTextString(BillReportCategory.get(dto.getReportCategory() == null ? 10 : dto.getReportCategory()).getName());
            cell3.setCellValue(name3);
            // 开始时间(维修/维保)/响应时间(急修)
            HSSFCell cell4 = row.createCell(4);
            HSSFRichTextString name4 = new HSSFRichTextString(dto.getStartTime() == null ? "" : dto.getStartTime().toString());
            cell4.setCellValue(name4);
            //情况描述
            HSSFCell cell5 = row.createCell(5);
            HSSFRichTextString name5 = new HSSFRichTextString(dto.getDescription());
            cell5.setCellValue(name5);
            // 故障性质/未决事宜
            HSSFCell cell6 = row.createCell(6);
            HSSFRichTextString name6 = new HSSFRichTextString(dto.getFaultQuality());
            cell6.setCellValue(name6);
            //处理结果
            HSSFCell cell7 = row.createCell(7);
            HSSFRichTextString name7 = new HSSFRichTextString(dto.getResult());
            cell7.setCellValue(name7);
            // 服务态度
            HSSFCell cell8 = row.createCell(8);
            HSSFRichTextString name8 = new HSSFRichTextString(Appraise.get(dto.getServiceAttitude() == null ? 30 : dto.getServiceAttitude()).getName());
            cell8.setCellValue(name8);
            // 服务水平
            HSSFCell cell9 = row.createCell(9);
            HSSFRichTextString name9 = new HSSFRichTextString(Appraise.get(dto.getServiceLevel() == null ? 30 : dto.getServiceLevel()).getName());
            cell9.setCellValue(name9);
            // 维护客户环境和安全
            HSSFCell cell10 = row.createCell(10);
            HSSFRichTextString name10 = new HSSFRichTextString(Appraise.get(dto.getEnvAndSafe() == null ? 30 : dto.getEnvAndSafe()).getName());
            cell10.setCellValue(name10);
            // 问题解决和故障排除
            HSSFCell cell11 = row.createCell(11);
            HSSFRichTextString name11 = new HSSFRichTextString(Appraise.get(dto.getResolveQuestion() == null ? 30 : dto.getResolveQuestion()).getName());
            cell11.setCellValue(name11);
            // 建议
            HSSFCell cell12 = row.createCell(12);
            HSSFRichTextString name12 = new HSSFRichTextString(dto.getSuggestions());
            cell12.setCellValue(name12);
            //完成时间
            HSSFCell cell13 = row.createCell(13);
            HSSFRichTextString name13 = new HSSFRichTextString(dto.getCompleteTime() != null ? dto.getCompleteTime().toString() : "");
            cell13.setCellValue(name13);
            //进场时间 报告日期 维修人员/维保人员 维修单位/保养单位 后续工单
            HSSFCell cell14 = row.createCell(14);
            HSSFRichTextString name14 = new HSSFRichTextString(dto.getEnterTime() != null ? dto.getEnterTime().toString() : "");
            cell14.setCellValue(name14);
            //报告日期
            HSSFCell cell15 = row.createCell(15);
            HSSFRichTextString name15 = new HSSFRichTextString(dto.getReportTime() != null ? dto.getReportTime().toString() : "");
            cell15.setCellValue(name15);
            //维修人员/维保人员
            HSSFCell cell16 = row.createCell(16);
            HSSFRichTextString name16 = new HSSFRichTextString(dto.getMaintenancePersonnel() != null ? dto.getMaintenancePersonnel().getName() + "/" + dto.getMaintenancePersonnel().getNumber() : "");
            cell16.setCellValue(name16);
            // 维修单位/保养单位
            HSSFCell cell17 = row.createCell(17);
            HSSFRichTextString name17 = new HSSFRichTextString(dto.getCompany() != null ? dto.getCompany().getAddress() + "/" + dto.getCompany().getName() : "");
            cell17.setCellValue(name17);
            // 后续工单
            HSSFCell cell18 = row.createCell(18);
            HSSFRichTextString name18 = new HSSFRichTextString(dto.getAfterBill() != null ? dto.getAfterBill().getBillNumber() : "");
            cell18.setCellValue(name18);

        }

    }

    //图片上传接口
//    public void uploadFiles(StandardMultipartHttpServletRequest request, Reports report) throws IOException {
//        //MultipartFile file1 = multipartRequest.getFile("iconImg");
//        //Map<String,MultipartFile> fileMap  =multipartRequest.getFileMap();
//        List<MultipartFile> fileList = request.getMultiFileMap().get("file");
//        for (MultipartFile file : fileList) {
//            int index = 1;
//            String pic_path = "C:/juliImage/image/sign/";
//            File target = new File(pic_path);
//            if (!target.exists()) {
//                target.mkdir();
//            }
//            String originalFilename = file.getOriginalFilename();
//            if (originalFilename != "") {
//                String newFile_name = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
//                File newFile = new File(pic_path + newFile_name);
//                try {
//                    file.transferTo(newFile);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                report.setSign(newFile_name);
//            }
//        }
//    }

    public void setForeignFieldFromDto(Reports entity, ReportDto dto) {
        if (StringUtils.isNotEmpty(dto.getElevatorId())) {
            entity.setElevator(elevatorRepository.findOne(dto.getElevatorId()));
        } else {
            entity.setElevator(null);
        }

        if (StringUtils.isNotEmpty(dto.getMaintenancePersonnelId())) {
            entity.setMaintenancePersonnel(maintenanceRepository.findOne(dto.getMaintenancePersonnelId()));
        } else {
            entity.setMaintenancePersonnel(null);
        }
        if (StringUtils.isNotEmpty(dto.getAfterBillId())) {
            entity.setAfterBill(workBillRepository.findOne(dto.getAfterBillId()));
        } else {
            entity.setAfterBill(null);
        }

    }

    public List<ReportDto> toDto(List<Reports> reportsList) {
        List<ReportDto> dtos = new ArrayList<>();
        for (Reports reports : reportsList) {
            ReportDto dto = new ReportDto();
            dto.setReportNumber(reports.getReportNumber());
            dto.setReportCategory(reports.getReportCategory());
//            dto = new ReportDto().mapFrom(reports);
            if (reports.getMaintenancePersonnel() != null) {
                dto.setMaintenancePersonnelId(reports.getMaintenancePersonnel().getId());
                dto.setMaintenanceName(reports.getMaintenancePersonnel().getName() + "/" + reports.getMaintenancePersonnel().getNumber());
            }
            if(null!=reports.getReportTime()){
                try {
                    dto.setReportTimeStr(DateUtil.dateToFullString2(reports.getReportTime()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (reports.getAfterBill() != null) {
                dto.setAfterBillId(reports.getAfterBill().getId());
                dto.setBillNumber(reports.getAfterBill().getBillNumber());
            }
            if (reports.getElevator() != null) {
                dto.setElevatorId(reports.getElevator().getId());
                dto.setElevatorNumber(reports.getElevator().getNumber());
            }
            dtos.add(dto);
        }
        return dtos;
    }
}