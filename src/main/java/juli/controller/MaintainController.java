package juli.controller;

import juli.api.dto.MaintenancePersonnel2Dto;
import juli.api.dto.MaintenancePersonnelDto;
import juli.domain.MaintenancePersonnel;
import juli.domain.User;
import juli.infrastructure.exception.JuliException;
import juli.repository.MaintenanceRepository;
import juli.service.MaintenanceService;
import juli.service.ReportService;
import juli.service.UserService;
import juli.service.WorkBillService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MaintainController {

    @Autowired
    private ReportService reportService;
    @Autowired
    private WorkBillService workBillService;
    @Autowired
    private MaintenanceService maintenanceService;
    @Autowired
    private MaintenanceRepository maintenanceRepository;
    @Autowired
    private UserService userService;
    @RequestMapping("/maintenancePersonnelBoard")
    public String maintenancePersonnel() {
        return "maintenancePersonnelBoard";
    }

    @RequestMapping("/maintenancePersonnelInfo")
    public String maintenancePersonnel(String id,Model model) {
        model.addAttribute("id",id);
        return "/maintenancePersonnelInfo";
    }

    @RequiresPermissions("workbillBoard:view")
    @RequestMapping("/workBillBoard")
    public String workBills(){
        return "workBillBoard";
    }

    @RequestMapping("/workBillStatic")
    public String statics(){
        return "workBillStatic";
    }

    @RequestMapping("/satisfactionStatic")
    public String satisfactionStatic(){
        return "satisfactionStatic";
    }

    @RequestMapping("/resultStatic")
    public String resultStatic(){
        return "resultStatic";
    }

    @RequestMapping("/faultStatic")
    public String faultStatic(){
        return "faultStatic";
    }

    @RequestMapping("/staticPicOne")
    public String staticPicOne(){
        return "staticPicOne";
    }

    @RequestMapping("/staticPicTwo")
    public String staticPicTwo(){
        return "staticPicTwo";
    }

    @RequestMapping("/staticPicThree")
    public String staticPicThree(){
        return "staticPicThree";
    }

    @RequestMapping("/staticPicFour")
    public String staticPicFour(){
        return "staticPicFour";
    }

    @RequestMapping("/workBill/cou")
    public String createOrUpdateWorkBill(String id,Model model)
    {
        model.addAttribute("elevatorNumbers","");
        return "workBillCoU";
    }

    @RequestMapping("/workBill/create")
    public String createWorkBill(String elevatorNumbers,Model model)
    {
        model.addAttribute("elevatorNumbers",elevatorNumbers);
        return "workBillCoU";
    }

    @RequestMapping("/workBill/export")
    public String exporteWorkBill()
    {
        return "BillExport";
    }


    @RequestMapping("/reportBoard")
    public String reportBoard(){
        return "report";
    }

    @RequestMapping("/reportBoard/detail")
    public String reportDetail(String id,Model model) throws Exception {

         model.addAttribute("report", reportService.getReportById(id));
        return "reportDetail";
    }

    @RequestMapping("/workBillBoard/detail")
    public String workBillDetail(String id,Model model) throws Exception {

        model.addAttribute("workBill", workBillService.getBillById(id));
        return "workBillDetail";
    }
    //合同
    @RequestMapping("/upkeepContractBoard")
    public String upkeepContract()
    {
        return "upkeepContractBoard";
    }

    @RequestMapping("/upkeepContract/add")
    public String upkeepContractAdd()
    {
       return "upkeepContract/upkeepContractAdd";
    }

    @RequestMapping("/upkeepContract/update")
    public String upkeepContractUpdate(String id)
    {
        return "upkeepContract/upkeepContractUpdate";
    }

    //跳转到添加批次界面
    @RequestMapping("/upkeepContract/planBathAdd")
    public String createBatch(Model model,String id) {

        try{
            User user = userService.getCurrentUser();
            List<MaintenancePersonnel2Dto> maintenancePersonnelDtos = new ArrayList<>();
            if(user.getCompanyId()!=null){
                String parentId=maintenanceRepository.findIdByName(user.getNick());//用户的名字需要和维保人员的名称一致
               if(parentId!=null){
                   List<MaintenancePersonnel> maintenancePersonnelList=maintenanceRepository.findMaintainerByParentId(parentId);
                   for (MaintenancePersonnel maintenancePersonnel : maintenancePersonnelList) {
                       MaintenancePersonnel2Dto maintenancePersonnel2Dto = new MaintenancePersonnel2Dto();
                       maintenancePersonnel2Dto.setId(maintenancePersonnel.getId());
                       maintenancePersonnel2Dto.setName(maintenancePersonnel.getName());
                       maintenancePersonnelDtos.add(maintenancePersonnel2Dto);
                   }
               }else
               {
                   List<MaintenancePersonnel> maintenancePersonnelList=maintenanceRepository.findByMaintainerId(user.getCompanyId());
                   for (MaintenancePersonnel maintenancePersonnel : maintenancePersonnelList) {
                       MaintenancePersonnel2Dto maintenancePersonnel2Dto = new MaintenancePersonnel2Dto();
                       maintenancePersonnel2Dto.setId(maintenancePersonnel.getId());
                       maintenancePersonnel2Dto.setName(maintenancePersonnel.getName());
                       maintenancePersonnelDtos.add(maintenancePersonnel2Dto);
                   }
               }
            }else
            {
                if(user.getRoles().get(0).getName().equals("超级管理员")){
                    for (MaintenancePersonnel maintenancePersonnel : maintenanceRepository.findAll()) {
                        MaintenancePersonnel2Dto maintenancePersonnel2Dto = new MaintenancePersonnel2Dto();
                        maintenancePersonnel2Dto.setId(maintenancePersonnel.getId());
                        maintenancePersonnel2Dto.setName(maintenancePersonnel.getName());
                        maintenancePersonnelDtos.add(maintenancePersonnel2Dto);
                    }
                }
            }
            model.addAttribute("maintenances", maintenancePersonnelDtos);
            model.addAttribute("upkeepContractId", id);
            return "upkeepContract/planBathAdd";
        }catch (JuliException e){
            return "wrong request!";
        }
    }

    //跳转到维保计划添加界面
    @RequestMapping("/maintenancePlan/add")
    public String maintenancePlanAddPage(Model model)
    {
        try {
            User user = userService.getCurrentUser();
            List<MaintenancePersonnel2Dto> maintenancePersonnelDtos = new ArrayList<>();
            if (user.getCompanyId() != null) {
                String parentId = maintenanceRepository.findIdByName(user.getNick());//用户的名称需要和维保人员的名称一致
                if (parentId != null) {
                    List<MaintenancePersonnel> maintenancePersonnelList = maintenanceRepository.findMaintainerByParentId(parentId);
                    for (MaintenancePersonnel maintenancePersonnel : maintenancePersonnelList) {
                        MaintenancePersonnel2Dto maintenancePersonnel2Dto = new MaintenancePersonnel2Dto();
                        maintenancePersonnel2Dto.setId(maintenancePersonnel.getId());
                        maintenancePersonnel2Dto.setName(maintenancePersonnel.getName());
                        maintenancePersonnelDtos.add(maintenancePersonnel2Dto);
                    }
                } else {
                    List<MaintenancePersonnel> maintenancePersonnelList = maintenanceRepository.findByMaintainerId(user.getCompanyId());
                    for (MaintenancePersonnel maintenancePersonnel : maintenancePersonnelList) {
                        MaintenancePersonnel2Dto maintenancePersonnel2Dto = new MaintenancePersonnel2Dto();
                        maintenancePersonnel2Dto.setId(maintenancePersonnel.getId());
                        maintenancePersonnel2Dto.setName(maintenancePersonnel.getName());
                        maintenancePersonnelDtos.add(maintenancePersonnel2Dto);
                    }
                }
            } else {
                if (user.getRoles().get(0).getName().equals("超级管理员")) {
                    for (MaintenancePersonnel maintenancePersonnel : maintenanceRepository.findAll()) {
                        MaintenancePersonnel2Dto maintenancePersonnel2Dto = new MaintenancePersonnel2Dto();
                        maintenancePersonnel2Dto.setId(maintenancePersonnel.getId());
                        maintenancePersonnel2Dto.setName(maintenancePersonnel.getName());
                        maintenancePersonnelDtos.add(maintenancePersonnel2Dto);
                    }
                }
            }
            model.addAttribute("maintenances", maintenancePersonnelDtos);
            return "maintenancePlanAdd";
        } catch(JuliException e){
            return  "wrong request!";
        }
    }


}
