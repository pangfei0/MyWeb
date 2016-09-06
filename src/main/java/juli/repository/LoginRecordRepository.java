package juli.repository;

import juli.domain.MaintenanceLoginRecord;
import juli.domain.MaintenancePersonnel;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by pf on 2016/3/1.
 */
public interface LoginRecordRepository extends PagingAndSortingRepository<MaintenanceLoginRecord, String>, JpaSpecificationExecutor {
    List<MaintenanceLoginRecord> findByMaintenancePersonnelAndLoginTimeGreaterThanAndLeaveTimeLessThan(MaintenancePersonnel maintenancePersonnel,Date loginTime,Date leaveTime);
}
