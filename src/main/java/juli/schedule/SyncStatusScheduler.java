package juli.schedule;

import juli.service.DataServer.DataServerSyncService;
import juli.service.DataServer.StatusSyncService;
import juli.service.MaintenancePlanService;
import juli.service.timedrefresh.RefreshTimerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 同步DataServer的电梯状态数据
 */
@Component
public class SyncStatusScheduler {
    private static final int SCHEDULE_INTERVAL = 1000 * 60 * 10;

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    StatusSyncService syncService;

    @Autowired
    DataServerSyncService dataServerSyncService;

    @Autowired
    MaintenancePlanService maintenancePlanService;

    @Autowired
    RefreshTimerService refreshTimerService;

    @Scheduled(fixedDelay = SCHEDULE_INTERVAL)
    public void sync() {
        try {
//            logger.info("定时器：同步电梯状态");
//            dataServerSyncService.syncElevatorStatus();
//            syncService.sync();
        } catch (Exception e) {
            logger.error("sync error", e);
        }
    }

//
//    @Scheduled(cron = "0 30 23 ? * *")
//    public void generateWorkbill() {
//        try {
//
//        } catch (Exception e) {
//            logger.error("generate Workbill error", e);
//        }
//    }

    @Scheduled(cron = "0 0/10 * * * ?")
     public void refreshMaintenancePersonnelTimer() {
        try {
            logger.info("定时刷新维保人员GPRS数据");
            refreshTimerService.refreshMaintenancePersonnelTimer();
            //自动生成工单开始
            maintenancePlanService.generateWorkBillByDate();
        } catch (Exception e) {
            logger.error("定时刷新维保人员GPRS数据 error", e);
        }
    }
}
