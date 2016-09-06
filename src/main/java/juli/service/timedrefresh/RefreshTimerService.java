package juli.service.timedrefresh;

import juli.domain.MaintenancePersonnel;
import juli.repository.*;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class RefreshTimerService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    MaintenanceRepository maintenanceRepository;

    public void refreshMaintenancePersonnelTimer() {
        List<MaintenancePersonnel> maintenancePersonnels = maintenanceRepository.findAll();
        if(CollectionUtils.isNotEmpty(maintenancePersonnels)){
            for(MaintenancePersonnel m:maintenancePersonnels){
                if(null!=m && null!=m.getCurrentState()){
                    String lastDate = m.getLastModifiedDate().toString("yyyy-MM-dd HH:mm:ss");
                    SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date begin = null;
                    try {
                        begin = dfs.parse(lastDate);
                        java.util.Date end = dfs.parse(dfs.format(new Date()));
                        long between = (end.getTime() - begin.getTime()) / 1000;//除以1000是为了转换成秒
                        if(between>600){
                            m.setLat(null);
                            m.setLng(null);
                            maintenanceRepository.save(m);
                            logger.info("维保员"+m.getName()+"长时间未使用app,gprs数据清空");
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

}
