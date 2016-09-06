package juli.controller;

import juli.api.MaintenancePersonnelAPIController;
import juli.api.dto.CompanyDto;
import juli.api.dto.MaintenancePersonnelDto;
import juli.domain.*;
import juli.domain.contract.UpkeepContract;
import juli.domain.enums.CompanyType;
import juli.infrastructure.DateUtil;
import juli.infrastructure.excel.CustomXWPFDocument;
import juli.infrastructure.excel.GenerateWord3;
import juli.infrastructure.exception.JuliException;
import juli.repository.*;
import juli.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Controller
public class ArchiveController {

    @Autowired
    PremiseRepository premiseRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ElevatorBrandRepository brandRepository;

    @Autowired
    MaintenanceRepository maintenanceRepository;

    @Autowired
    MaintenancePersonnelAPIController maintenancePersonnelAPIController;

    @Autowired
    UpkeepContractRepository upkeepContractRepository;

    @Autowired
    WorkBillRepository workBillRepository;

    @Autowired
    ReportRepository reportRepository;
    @Autowired
    UserService userService;

    @RequestMapping("/premise")
    public String premise() {
        return "premise";
    }

    @RequestMapping("/premise/cou")
    public String createOrUpdatePremise(Model model) {

        List<CompanyDto> maintainers = new ArrayList<>();
        for (Company maintainer : companyRepository.findOrderByName(20)) {
            maintainers.add(new CompanyDto().mapFrom(maintainer));
        }

        model.addAttribute("maintainers", maintainers);
        return "premiseCoU";
    }

    @RequestMapping("/company/{type}")
    public ModelAndView Company(@PathVariable("type") Integer type) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("type", type);
        mav.setViewName("company");
        return mav;
    }


    @RequestMapping("/company/{type}/cou")
    public ModelAndView createOrUpdateCompany(Model model, @PathVariable("type") Integer type) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("type", type);
        mav.setViewName("companyCoU");
        return mav;
    }

    @RequestMapping("/companyMaintain")
    public String companyMaintain(Model model) {
        try{
            User user = userService.getCurrentUser();
            List<Map<String,Object>> mapList=new ArrayList<>();
            if(user.getCompanyType()!=null){
                CompanyType companyType=CompanyType.getCompanyType(Integer.parseInt(user.getCompanyType()));
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("id", String.valueOf(companyType.getCode()));
                paramMap.put("name", companyType.getName());
                mapList.add(paramMap);
            }
            else
            {
                for (CompanyType companyType1 : CompanyType.values()) {
                    Map<String,Object> paramMap = new HashMap<>();
                    paramMap.put("id", String.valueOf(companyType1.getCode()));
                    paramMap.put("name",companyType1.getName());
                    mapList.add(paramMap);
                }
            }
            model.addAttribute("companyTypes", mapList);
            return "companyMaintain";
        }catch (JuliException e) {
            return "wrong request!";
        }
    }
    @RequestMapping("/companyMaintain/cou")
    public String companyMaintainCoU() {
            return "companyMaintainUpdate";
    }

    @RequestMapping("/companyMaintain/add")
    public String companyMaintainAdd(Model model){
        try{
            User user = userService.getCurrentUser();
            List<Map<String,Object>> mapList=new ArrayList<>();
            if(user.getCompanyType()!=null){
                CompanyType companyType=CompanyType.getCompanyType(Integer.parseInt(user.getCompanyType()));
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("id", String.valueOf(companyType.getCode()));
                paramMap.put("name", companyType.getName());
                mapList.add(paramMap);
            }
            else
            {
                for (CompanyType companyType1 : CompanyType.values()) {
                    Map<String,Object> paramMap = new HashMap<>();
                    paramMap.put("id", String.valueOf(companyType1.getCode()));
                    paramMap.put("name",companyType1.getName());
                    mapList.add(paramMap);
                }
            }
            model.addAttribute("companyTypes", mapList);
            return "companyMaintainAdd";
        }catch (JuliException e) {
            return "wrong request!";
        }
    }
        @RequestMapping("/elevator")
    public String elevator() {
        return "elevator";
    }
    @RequestMapping("/elevator/add")
    public String elevatorAdd(Model model)
    {
        List<ElevatorBrand> brands = new ArrayList<>();
        for (ElevatorBrand brand : brandRepository.findAll()) {
            brands.add(brand);
        }
        model.addAttribute("brands", brands);
        return "elevatorAdd";
    }
    @RequestMapping("/elevator/cou")
    public String createOrUpdateElevator(Model model) {
        List<ElevatorBrand> brands = new ArrayList<>();
        for (ElevatorBrand brand : brandRepository.findAll()) {
            brands.add(brand);
        }
        model.addAttribute("brands", brands);
        return "elevatorCoU";
    }

    @RequestMapping("/maintainerEmployee")
    public String maintainerEmployee() {
        return "maintainerEmployee";
    }
    @RequestMapping("/maintainerEmployee/add")
    public String maintainerEmployeeAdd(Model model) {
        List<ElevatorBrand> brands = new ArrayList<>();
        for (ElevatorBrand brand : brandRepository.findAll()) {
            brands.add(brand);
        }
        model.addAttribute("brands", maintenancePersonnelAPIController.getSomeBaseInfo().getData());
        return "maintainerEmployeeAdd";
    }
    @RequestMapping("/maintainerEmployee/cou")
    public String createOrUpdateMaintainerEmployee(Model model){
        List<ElevatorBrand> brands = new ArrayList<>();
        for (ElevatorBrand brand : brandRepository.findAll()) {
            brands.add(brand);
        }
        model.addAttribute("brands", maintenancePersonnelAPIController.getSomeBaseInfo().getData());
        return "maintainerEmployeeCoU";
    }


    @RequestMapping("/elevatorBrand")
    public String elevatorBrand(){
        return "elevatorBrand";
    }

    @RequestMapping("/statics")
    public String statics(){
        return "workBillStatic";
    }

    @RequestMapping("/elevatorBrand/cou")
    public String createElevatorBrand(){
        return "elevatorBrandCoU";
    }


    @RequestMapping("/upkeepContract")
    public String upkeepContract(){
        return "upkeepContract";
    }

    @RequestMapping("/upkeepContract/cou")
    public String upkeepContractUpdate(){
        /*return "upkeepContract";*/
        return "upkeepContract/upkeepContractUpdate";
    }

    @RequestMapping("/collector")
    public String collector(){
        return "intelHardware";
    }

    @RequestMapping("/collector/cou")
    public String createOrUpdateCollector(){
        return "intelHardwareCoU";
    }

    @RequestMapping("/maintenancePlan")
    public String maintenancePlan(){
        return "maintenancePlanList";
    }

    @RequestMapping("/maintenancePlanBath")
    public String maintenancePlanBath(){
        return "maintenancePlanBath";
    }


    /**
     * 生成工单合同
     * @param id
     * @param response
     * @throws Exception
     */
    @RequestMapping("/upkeepContract/generateWorkBill")
    public void generateWorkBill(String id,HttpServletResponse response) throws Exception {
        System.out.println("合同id= "+id);
        //替换word值封装map
        Map<String,Object> map = new HashMap<>();
        String elevatorStr="";
        String maintenanceUnit = "";
        String elevatorType="";
        int modelType = 0;
        Reports reports = null;
        WorkBills workBills = workBillRepository.findOne(id);
        if(workBills!=null){
             reports = reportRepository.findReportByWorkBillId(workBills.getId());
            //物业单位,此处改为甲方
            //TODO
            if(workBills.getElevator()!=null){
                Company company = companyRepository.findById(workBills.getElevator().getOwnerCompanyId());
                if(company!=null){
                    map.put("ā",company.getName());//物业单位
                }else{
                    Company userCompany = companyRepository.findById(workBills.getElevator().getUserCompanyId());
                    if(userCompany!=null){
                        map.put("ā",userCompany.getName());//使用单位
                    }else{
                        map.put("ā","未填写");//使用单位
                    }
                }
                Company maintainer = companyRepository.findById(workBills.getElevator().getMaintainerId());
                if(null!=maintainer){
                    maintenanceUnit = maintainer.getName();
                }else{
                    maintenanceUnit="未填写";
                }
                elevatorStr = workBills.getEnumber();
                elevatorType = workBills.getElevator().getElevatorType();
            }
            if(workBills.getElevator()!=null&&StringUtils.isNotEmpty(workBills.getElevator().getId())){
                String upkeepContractId = upkeepContractRepository.getUpkeepcontractIdByElevatorId(workBills.getElevator().getId());
                if(StringUtils.isNotEmpty(upkeepContractId)){
                    UpkeepContract upkeepContract = upkeepContractRepository.findOne(upkeepContractId);
                    if(null!=upkeepContract){
                        map.put("ω",upkeepContract.getProperty());//合同性质
                    }else{
                        map.put("ω","无");//合同性质
                    }
                }else{
                    map.put("ω","无");//合同性质
                }
            }
            if(null!=reports && null!=reports.getGeneralEvaluate()){
                String desc = "";
                if(reports.getGeneralEvaluate()==10){
                    desc = "好";
                }else if(reports.getGeneralEvaluate()==20){
                    desc = "良好";
                }else if(reports.getGeneralEvaluate()==30){
                    desc = "一般";
                }else if(reports.getGeneralEvaluate()==40){
                    desc = "差";
                }else{
                    desc = "无评价";
                }
                map.put("ψ",desc);//综合评价
            }else{
                map.put("ψ","无评价");//综合评价
            }

                if(StringUtils.isNotEmpty(elevatorStr)){
                    map.put("Θ", elevatorStr);//电梯工号
                    //电子签名
                    if(reports!=null&&StringUtils.isNotEmpty(reports.getSign())&&(reports.getSign().indexOf("qm")!=-1||reports.getSign().indexOf("wb")!=-1)){
                        Map<String,Object> header = new HashMap<String, Object>();
                        header.put("width", 100);
                        header.put("height", 150);
                        header.put("type", "jpg");
                        header.put("content", GenerateWord3.inputStream2ByteArray(new FileInputStream("D:\\juliImage\\Resource\\"+reports.getSign()), true));
                        map.put("￠",header);
                    }else{
                        map.put("￠","无");
                    }
                    //图片1
                    if(workBills!=null&&StringUtils.isNotEmpty(workBills.getPicture1())&&(workBills.getPicture1().indexOf("qm")!=-1||workBills.getPicture1().indexOf("wb")!=-1)){
                        Map<String,Object> header = new HashMap<String, Object>();
                        header.put("width", 100);
                        header.put("height", 150);
                        header.put("type", "jpg");
                        header.put("content", GenerateWord3.inputStream2ByteArray(new FileInputStream("D:\\juliImage\\Resource\\"+workBills.getPicture1()), true));
                        map.put("ǒ",header);
                    }else{
                        map.put("ǒ","无");
                    }
                    //图片2
                    if(workBills!=null&&StringUtils.isNotEmpty(workBills.getPicture2())&&(workBills.getPicture2().indexOf("qm")!=-1||workBills.getPicture2().indexOf("wb")!=-1)){
                        Map<String,Object> header = new HashMap<String, Object>();
                        header.put("width", 100);
                        header.put("height", 150);
                        header.put("type", "jpg");
                        header.put("content", GenerateWord3.inputStream2ByteArray(new FileInputStream("D:\\juliImage\\Resource\\"+workBills.getPicture2()), true));
                        map.put("ê",header);
                    }else{
                        map.put("ê","无");
                    }
                    //图片3
                    if(workBills!=null&&StringUtils.isNotEmpty(workBills.getPicture3())&&(workBills.getPicture3().indexOf("qm")!=-1||workBills.getPicture3().indexOf("wb")!=-1)){
                        Map<String,Object> header = new HashMap<String, Object>();
                        header.put("width", 100);
                        header.put("height", 150);
                        header.put("type", "jpg");
                        header.put("content", GenerateWord3.inputStream2ByteArray(new FileInputStream("D:\\juliImage\\Resource\\"+workBills.getPicture3()), true));
                        map.put("ē",header);
                    }else{
                        map.put("ē","无");
                    }
                    //图片4
                    if(workBills!=null&&StringUtils.isNotEmpty(workBills.getPicture4())&&(workBills.getPicture4().indexOf("qm")!=-1||workBills.getPicture4().indexOf("wb")!=-1)){
                        Map<String,Object> header = new HashMap<String, Object>();
                        header.put("width", 100);
                        header.put("height", 150);
                        header.put("type", "pg");
                        header.put("content", GenerateWord3.inputStream2ByteArray(new FileInputStream("D:\\juliImage\\Resource\\"+workBills.getPicture4()), true));
                        map.put("ě",header);
                    }else{
                        map.put("ě","无");
                    }
                    //图片5
                    if(workBills!=null&&StringUtils.isNotEmpty(workBills.getPicture5())&&(workBills.getPicture5().indexOf("qm")!=-1||workBills.getPicture5().indexOf("wb")!=-1)){
                        Map<String,Object> header = new HashMap<String, Object>();
                        header.put("width", 100);
                        header.put("height", 150);
                        header.put("type", "jpg");
                        header.put("content", GenerateWord3.inputStream2ByteArray(new FileInputStream("D:\\juliImage\\Resource\\"+workBills.getPicture5()), true));
                        map.put("ī",header);
                    }else{
                        map.put("ī","无");
                    }
                }
                if(StringUtils.isNotEmpty(maintenanceUnit)){
                    map.put("á",maintenanceUnit);//维保单位
                }
            //其他参数
            map.put("ō",workBills.getBillNumber());//合同号
            if(workBills.getStartTime()!=null){
                String beginDate = DateUtil.dateToString(workBills.getStartTime(),"yyyy-MM-dd HH:mm:ss");
                map.put("ǎ",beginDate);//合同开始时间
            }else{
                map.put("ǎ","未开始");
            }

            if(workBills.getCompleteTime()!=null){
                String endDate = DateUtil.dateToString(workBills.getCompleteTime(),"yyyy-MM-dd HH:mm:ss");
                map.put("à",endDate);//合同结束时间
            }else{
                map.put("à","未结束");
            }

            modelType = workBills.getBillModel();
        }


        String programs[] = null;
        //替换参数符号
        String programsStr = workBills.getMaintainPrograms();
        if(StringUtils.isNotEmpty(programsStr)){
            programs =  programsStr.split(",");
        }


        //替换word中的值    ./wordTemplates/半年维保.docx
        try{    //G:\project\git_web\src\main\resources\wordTemplates\halfYear.docx
            OPCPackage opc = null;
            String temp = "";
            if(0==modelType){
                if("扶梯".equals(elevatorType)||"自动人行道".equals(elevatorType)){//自动人行道
                    temp = "D:"+File.separator+"wordTemplates"+File.separator+"halfMonthF.docx";
                    halfMonthF(map,programs);
                }else{
                    temp = "D:"+File.separator+"wordTemplates"+File.separator+"halfMonth.docx";
                    halfMonth(map,programs);
                }
            }else if(10==modelType){
                if("扶梯".equals(elevatorType)||"自动人行道".equals(elevatorType)){
                    temp = "D:"+File.separator+"wordTemplates"+File.separator+"quarterF.docx";
                    quarterF(map,programs);
                }else{
                    temp = "D:"+File.separator+"wordTemplates"+File.separator+"quarter.docx";
                    quarter(map,programs);
                }
            }else if(20==modelType){
                if("扶梯".equals(elevatorType)||"自动人行道".equals(elevatorType)){
                    temp = "D:"+File.separator+"wordTemplates"+File.separator+"halfYearF.docx";
                    halfYearF(map,programs);
                }else{
                    temp = "D:"+File.separator+"wordTemplates"+File.separator+"halfYear.docx";
                    halfYear(map,programs);
                }
            }else if(30==modelType){
                if("扶梯".equals(elevatorType)||"自动人行道".equals(elevatorType)){
                    temp = "D:"+File.separator+"wordTemplates"+File.separator+"year_newF.docx";
                    yearNewF(map,programs);
                }else{
                    temp = "D:"+File.separator+"wordTemplates"+File.separator+"year_new.docx";
                    yearNew(map,programs);
                }
            }else if(40==modelType){
                temp = "D:"+File.separator+"wordTemplates"+File.separator+"emergencyRepair_new.docx";
                emergencyRepair(map,programs,workBills,reports);
            }else if(50==modelType){
                temp = "D:"+File.separator+"wordTemplates"+File.separator+"emergencyRepair_new.docx";
                emergencyRepair(map,programs,workBills,reports);
            }
//            temp = opc.toString();
            CustomXWPFDocument doc = GenerateWord3.generateWord(map, temp);

            //生成word并下载到指定位置
            String filename = "合同.doc";
            response.setHeader("Content-disposition", "attachment; filename="
                    + new String(filename.toString().getBytes("GB2312"), "ISO8859-1"));
            response.setContentType("application/msword;charset=gb2312");
            OutputStream os= new BufferedOutputStream(response.getOutputStream());
            doc.write(os);
//            xwpf.close();
            os.close();
//            FileOutputStream os = new FileOutputStream(new File("E:"+File.separator+"test1.docx"));
//            xwpf.write(os);
//            xwpf.close();
//            os.close();
        }catch (Exception e1){
            e1.printStackTrace();
        }
    }

    private String numberToImage(String number){
        if(StringUtils.isNotEmpty(number)){
            if("1".equals(number)){
                return "△";
            }else if("2".equals(number)){
                return "☆";
            }else if("3".equals(number)){
                return "√";
            }else if("4".equals(number)){
                return "×";
            }else {
                return "×";
            }
        }
        return "×";
    }

    private void halfMonth(Map<String, Object> param,String programs[]){
        param.put("ン",numberToImage(programs[0]));
        param.put("ǐ",numberToImage(programs[1]));
        param.put("ū",numberToImage(programs[2]));
        param.put("ǔ",numberToImage(programs[3]));
        param.put("ǖ",numberToImage(programs[4]));
        param.put("ǚ",numberToImage(programs[5]));
        param.put("ü",numberToImage(programs[6]));
        param.put("≮",numberToImage(programs[7]));
        param.put("≯",numberToImage(programs[8]));
        param.put("⌒",numberToImage(programs[9]));
        param.put("ぷ",numberToImage(programs[10]));
        param.put("¤",numberToImage(programs[11]));
        param.put("╬",numberToImage(programs[12]));
        param.put("♀",numberToImage(programs[13]));
        param.put("α",numberToImage(programs[14]));
        param.put("β",numberToImage(programs[15]));
        param.put("γ",numberToImage(programs[16]));
        param.put("δ",numberToImage(programs[17]));
        param.put("$",numberToImage(programs[18]));
        param.put("%",numberToImage(programs[19]));
        param.put("^",numberToImage(programs[20]));
        param.put("&",numberToImage(programs[21]));
        param.put("￥",numberToImage(programs[22]));
        param.put("~",numberToImage(programs[23]));
        param.put("!",numberToImage(programs[24]));
        param.put("ζ",numberToImage(programs[25]));
        param.put("@",numberToImage(programs[26]));
        param.put("#",numberToImage(programs[27]));
    }

    private void halfYear(Map<String, Object> param,String programs[]){
        param.put("ン",numberToImage(programs[0]));
        param.put("ǐ",numberToImage(programs[1]));
        param.put("ū",numberToImage(programs[2]));
        param.put("ǔ",numberToImage(programs[3]));
        param.put("ǖ",numberToImage(programs[4]));
        param.put("ǚ",numberToImage(programs[5]));
        param.put("ü",numberToImage(programs[6]));
        param.put("≮",numberToImage(programs[7]));
        param.put("≯",numberToImage(programs[8]));
        param.put("⌒",numberToImage(programs[9]));
        param.put("ぷ",numberToImage(programs[10]));
        param.put("¤",numberToImage(programs[11]));
        param.put("╬",numberToImage(programs[12]));
        param.put("♀",numberToImage(programs[13]));
    }

    private void quarter(Map<String, Object> param,String programs[]){
        param.put("ン",numberToImage(programs[0]));
        param.put("ǐ",numberToImage(programs[1]));
        param.put("ū",numberToImage(programs[2]));
        param.put("ǔ",numberToImage(programs[3]));
        param.put("ǖ",numberToImage(programs[4]));
        param.put("ǚ",numberToImage(programs[5]));
        param.put("ü",numberToImage(programs[6]));
        param.put("≮",numberToImage(programs[7]));
        param.put("≯",numberToImage(programs[8]));
        param.put("⌒",numberToImage(programs[9]));
        param.put("ぷ",numberToImage(programs[10]));
        param.put("¤",numberToImage(programs[11]));
        param.put("╬",numberToImage(programs[12]));
    }

    private void yearNew(Map<String, Object> param,String programs[]){
        param.put("ン",numberToImage(programs[0]));
        param.put("ǐ",numberToImage(programs[1]));
        param.put("ū",numberToImage(programs[2]));
        param.put("ǔ",numberToImage(programs[3]));
        param.put("ǖ",numberToImage(programs[4]));
        param.put("ǚ",numberToImage(programs[5]));
        param.put("ü",numberToImage(programs[6]));
        param.put("≮",numberToImage(programs[7]));
        param.put("≯",numberToImage(programs[8]));
        param.put("⌒",numberToImage(programs[9]));
        param.put("ぷ",numberToImage(programs[10]));
        param.put("¤",numberToImage(programs[11]));
        param.put("╬",numberToImage(programs[12]));
        param.put("♀",numberToImage(programs[13]));
        param.put("α",numberToImage(programs[14]));
        param.put("β",numberToImage(programs[15]));
    }

    //扶梯
    private void halfMonthF(Map<String, Object> param,String programs[]){
        param.put("ン",numberToImage(programs[0]));
        param.put("ǐ",numberToImage(programs[1]));
        param.put("ū",numberToImage(programs[2]));
        param.put("ǔ",numberToImage(programs[3]));
        param.put("ǖ",numberToImage(programs[4]));
        param.put("ǚ",numberToImage(programs[5]));
        param.put("ü",numberToImage(programs[6]));
        param.put("≮",numberToImage(programs[7]));
        param.put("≯",numberToImage(programs[8]));
        param.put("⌒",numberToImage(programs[9]));
        param.put("ぷ",numberToImage(programs[10]));
        param.put("¤",numberToImage(programs[11]));
        param.put("╬",numberToImage(programs[12]));
        param.put("♀",numberToImage(programs[13]));
        param.put("α",numberToImage(programs[14]));
        param.put("β",numberToImage(programs[15]));
        param.put("γ",numberToImage(programs[16]));
        param.put("δ",numberToImage(programs[17]));
        param.put("$",numberToImage(programs[18]));
        param.put("%",numberToImage(programs[19]));
        param.put("^",numberToImage(programs[20]));
        param.put("&",numberToImage(programs[21]));
        param.put("￥",numberToImage(programs[22]));
        param.put("~",numberToImage(programs[23]));
        param.put("!",numberToImage(programs[24]));
        param.put("ζ",numberToImage(programs[25]));
        param.put("@",numberToImage(programs[26]));
        param.put("#",numberToImage(programs[27]));
        param.put("♂",numberToImage(programs[28]));
        param.put("∞",numberToImage(programs[29]));
        param.put("ㄨ",numberToImage(programs[30]));
    }

    private void quarterF(Map<String, Object> param,String programs[]){
        param.put("ン",numberToImage(programs[0]));
        param.put("ǐ",numberToImage(programs[1]));
        param.put("ū",numberToImage(programs[2]));
        param.put("ǔ",numberToImage(programs[3]));
        param.put("ǖ",numberToImage(programs[4]));
    }

    private void halfYearF(Map<String, Object> param,String programs[]){
        param.put("ン",numberToImage(programs[0]));
        param.put("ǐ",numberToImage(programs[1]));
        param.put("ū",numberToImage(programs[2]));
        param.put("ǔ",numberToImage(programs[3]));
        param.put("ǖ",numberToImage(programs[4]));
        param.put("ǚ",numberToImage(programs[5]));
        param.put("ü",numberToImage(programs[6]));
        param.put("≮",numberToImage(programs[7]));
        param.put("≯",numberToImage(programs[8]));
        param.put("⌒",numberToImage(programs[9]));
        param.put("ぷ",numberToImage(programs[10]));
    }

    private void yearNewF(Map<String, Object> param,String programs[]){
        param.put("ン",numberToImage(programs[0]));
        param.put("ǐ",numberToImage(programs[1]));
        param.put("ū",numberToImage(programs[2]));
        param.put("ǔ",numberToImage(programs[3]));
        param.put("ǖ",numberToImage(programs[4]));
        param.put("ǚ",numberToImage(programs[5]));
        param.put("ü",numberToImage(programs[6]));
        param.put("≮",numberToImage(programs[7]));
        param.put("≯",numberToImage(programs[8]));
        param.put("⌒",numberToImage(programs[9]));
        param.put("ぷ",numberToImage(programs[10]));
        param.put("¤",numberToImage(programs[11]));
        param.put("╬",numberToImage(programs[12]));
    }

    private void emergencyRepair(Map<String, Object> param,String programs[],WorkBills workBills,Reports reports){

        if(null!=workBills&&StringUtils.isNotEmpty(workBills.getLocalDescription())){
            param.put("い",workBills.getLocalDescription());
        }else{
            param.put("い","未填写");
        }
        if(null!=workBills&&StringUtils.isNotEmpty(workBills.getFaultQuality())){
            param.put("う",workBills.getFaultQuality());
        }else{
            param.put("う","未填写");
        }
        if(null!=workBills&&StringUtils.isNotEmpty(workBills.getResult())){
            param.put("え",workBills.getResult());
        }else{
            param.put("え","未填写");
        }
        if(null!=workBills&&StringUtils.isNotEmpty(workBills.getTakeSteps())){
            param.put("お",workBills.getTakeSteps());
        }else{
            param.put("お","未填写");
        }
        //
        if(null!=workBills&&StringUtils.isNotEmpty(workBills.getFaultDescription())){
            param.put("μ",workBills.getFaultDescription());
        }else{
            param.put("μ","未填写");
        }

        if(null!=workBills&&StringUtils.isNotEmpty(workBills.getFaultPerson())){
            param.put("π",workBills.getFaultPerson());
        }else{
            param.put("π","未填写");
        }

        if(null!=workBills&&StringUtils.isNotEmpty(workBills.getFaultPersonTelephone())){
            param.put("ρ",workBills.getFaultPersonTelephone());
        }else{
            param.put("ρ","未填写");
        }

        if(null!=workBills&&null!=workBills.getElevator()&&StringUtils.isNotEmpty(workBills.getElevator().getElevatorType())){
                param.put("θ",workBills.getElevator().getElevatorType());
        }else{
            param.put("θ","未填写");
        }

        //服务态度
        if(null!=reports&&reports.getServiceAttitude()==10){
            param.put("ж","很满意");
        }else if(null!=reports&&reports.getServiceAttitude()==20){
            param.put("ж","满意");
        }else if(null!=reports&&reports.getServiceAttitude()==30){
            param.put("ж","一般");
        }else if(null!=reports&&reports.getServiceAttitude()== 40) {
            param.put("ж","不满意");
        }else{
            param.put("ж","未评价");
        }

        //技术水平评价
        if(null!=reports&&reports.getServiceLevel()==10){
            param.put("з","高");
        }else if(null!=reports&&reports.getServiceLevel()==20){
            param.put("з","一般");
        }else if(null!=reports&&reports.getServiceLevel()==30){
            param.put("з","较差");
        }else if(null!=reports&&reports.getServiceLevel()== 40) {
            param.put("з","差");
        }else{
            param.put("з","未评价");
        }

        //客户环境及安全评价
        if(null!=reports&&reports.getEnvAndSafe()==10){
            param.put("и","自觉");
        }else if(null!=reports&&reports.getEnvAndSafe()==20){
            param.put("и","一般");
        }else if(null!=reports&&reports.getEnvAndSafe()==30){
            param.put("и","较差");
        }else if(null!=reports&&reports.getEnvAndSafe()== 40) {
            param.put("и","差");
        }else{
            param.put("и","未评价");
        }

        //问题解决及故障排除
        if(null!=reports&&reports.getResolveQuestion()==10){
            param.put("й","已解决");
        }else if(null!=reports&&reports.getResolveQuestion()==20){
            param.put("й","部分解决");
        }else if(null!=reports&&reports.getResolveQuestion()==30){
            param.put("й","部分解决");
        }else if(null!=reports&&reports.getResolveQuestion()== 40) {
            param.put("й","未解决");
        }else{
            param.put("й","未评价");
        }

    }
}
