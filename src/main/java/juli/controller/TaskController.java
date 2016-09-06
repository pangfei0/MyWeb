//package juli.controller;
//
//import juli.domain.Company;
//import juli.domain.Elevator;
//import juli.domain.MaintenancePersonnel;
//import juli.domain.WorkBills;
//import juli.domain.contract.UpkeepContract;
//import juli.infrastructure.DateUtil;
//import juli.repository.UpkeepContractRepository;
//import juli.service.ElevatorService;
//import juli.service.MaintenanceService;
//import juli.service.WorkBillService;
//import org.joda.time.DateTime;
//import org.joda.time.format.DateTimeFormat;
//import org.joda.time.format.DateTimeFormatter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Configurable;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.*;
//
///**
// * Created by LYL on 16/4/18.
// */
//@Component
//@Configurable
//@EnableScheduling
//public class TaskController {
//    @Autowired
//    WorkBillService workBillService;
//
//    @Autowired
//    ElevatorService elevatorService;
//
//    @Autowired
//    UpkeepContractRepository upkeepContractRepository;
//
//    @Autowired
//    MaintenanceService maintenanceService;
//
//    //每两小时执行一次任务
//    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
//    public void scheduledTask() throws Exception {
//        //查询保养合同
//        int fixRateDay = 15;
//        Iterable<UpkeepContract> upkeepContractIterable = upkeepContractRepository.findAll();
//        Iterator<UpkeepContract> upkeepContractIterator = upkeepContractIterable.iterator();
//        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
//        while (upkeepContractIterator.hasNext()) {
//            UpkeepContract upkeepContract = upkeepContractIterator.next();
//            Date beginDate = upkeepContract.getBeginDate();
//            Date endDate = upkeepContract.getEndDate();
//            if (beginDate == null || endDate == null) {
//                continue;
//            }
//            Date curDate = new Date();
//            DateTime beginDT = DateTime.parse(new DateTime(beginDate).toString("yyyy-MM-dd"), format);
//            DateTime todayFT = DateTime.parse(DateTime.now().toString("yyyy-MM-dd"), format);
//            //日期合法性检查
//            if (curDate.before(endDate) && curDate.after(beginDate)) {
//                //遍历关联电梯
//                Set<Elevator> elevators = upkeepContract.getElevator();
//                int elevatorCount = elevators.size();
//                int phaseSize = (int) Math.ceil((double) elevators.size() / 5);
//                int index = 1;
//                for (Elevator elevator : elevators) {
//                    if (elevators != null) {
//                        Company company = elevator.getMaintainer();
//                        //判断是否有对应的维保公司,如果有才继续下面流程
//                        if (company != null) {
//                            //获取维保人员
//                            MaintenancePersonnel personnel = elevator.getMaintenance();
//                            //查询已经存在的保养工单
//                            List<WorkBills> workBillsList = workBillService.findBillsByElevatorId(elevator.getId(), 20);
//                            int billModel = 0;//半月保
//                            int billCategory = 20;//维保工单
//                            String billNumber = "WB" + DateUtil.dateToString(new Date(), "yyyy-MM-dd") + "-" + (new Random().nextInt(89999) + 10000);
//                            WorkBills workBills = new WorkBills();
//                            workBills.setBillCategory(billCategory);
//                            workBills.setBillstatus(10);
//                            workBills.setBillNumber(billNumber);
//                            workBills.setMaintenancePersonnel(personnel);
//                            workBills.setElevator(elevator);
//                            workBills.setCreatedDate(new DateTime());
//                            workBills.setBillModel(billModel);
//                            if (workBillsList != null && workBillsList.size() > 0) {
//                                //获取最新一次维保工单
//                                WorkBills lastWorkBills = workBillsList.get(0);
//
//                                DateTime createDT = lastWorkBills.getCreatedDate();
//                                if (createDT == null) {
//                                    continue;
//                                }
//                                DateTime createFT = DateTime.parse(createDT.toString("yyyy-MM-dd"), format);
//                                DateTime duringFT = DateTime.parse(createFT.plus(fixRateDay).toString("yyyy-MM-dd"), format);
//
//                                if (duringFT.isEqual(todayFT)) {
//                                    workBillService.saveWorkBills(workBills);
//                                    int count = workBillsList.size();
//                                    if (count % 6 == 0) {       //季保
//                                        billModel = 10;
//                                        WorkBills seasonBill = copyBill(workBills);
//                                        seasonBill.setBillModel(billModel);
//                                        workBillService.saveWorkBills(seasonBill);
//                                    }
//                                    if (count % 12 == 0) {      //半年保
//                                        billModel = 20;
//                                        WorkBills halfYearBill = copyBill(workBills);
//                                        halfYearBill.setBillModel(billModel);
//                                        workBillService.saveWorkBills(halfYearBill);
//                                    }
//                                    if (count % 24 == 0) {      //年保
//                                        billModel = 30;
//                                        WorkBills yearBill = copyBill(workBills);
//                                        yearBill.setBillModel(billModel);
//                                        workBillService.saveWorkBills(yearBill);
//                                    }
//                                }
//                            } else {
//                                int pushDelayDay = 0;
//                                int pIndex = (int) Math.ceil((double) index % 5);
//                                if (elevatorCount <= 5) {
//                                    pushDelayDay = index == 1 ? 0 : 3 + (index - 2) * 3;
//                                } else {
//                                    if (pIndex == 1) {
//                                        pushDelayDay = 0;
//                                    } else if (pIndex == 2) {
//                                        pushDelayDay = 3;
//                                    } else if (pIndex == 3) {
//                                        pushDelayDay = 6;
//                                    } else if (pIndex == 4) {
//                                        pushDelayDay = 9;
//                                    } else if (pIndex == 5 || pIndex == 0) {
//                                        pushDelayDay = 12;
//                                    }
//                                }
//                                if (beginDT.plusDays(pushDelayDay).isEqual(todayFT)||(pIndex==1&&todayFT.getDayOfYear()-beginDT.getDayOfYear()<=4)) {
//                                    workBillService.saveWorkBills(workBills);
//                                }
//                            }
//                        }
//                        index++;
//                    }
//                }
//            }
//        }
//    }
//
//    private WorkBills copyBill(WorkBills preBill) throws Exception {
//        String billNumber = "WB" + DateUtil.dateToString(new Date(), "yyyy-MM-dd") + "-" + (new Random().nextInt(89999) + 10000);
//        WorkBills postBill = new WorkBills();
//        postBill.setBillCategory(preBill.getBillCategory());
//        postBill.setBillstatus(preBill.getBillstatus());
//        postBill.setBillNumber(billNumber);
//        postBill.setMaintenancePersonnel(preBill.getMaintenancePersonnel());
//        postBill.setElevator(preBill.getElevator());
//        postBill.setCreatedDate(new DateTime());
//        return postBill;
//    }
//
//}
