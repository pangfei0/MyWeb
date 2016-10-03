package juli.repository;

import juli.domain.MaintenancePersonnel;
import juli.domain.MaintenancePlan;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 *  create by zj
 */

@Repository
public interface MaintenancePlanRepository extends PagingAndSortingRepository<MaintenancePlan,String>,JpaSpecificationExecutor{

    List<MaintenancePlan> findByPlanTimeAndStatus(Date d,Integer status);

    MaintenancePlan findByWorkBillId(String workBillId);

    List<MaintenancePlan> findByNumberAndStatus(String number,Integer status);

    @Query(value="select m from MaintenancePlan m where m.planTime >= ?2 and m.planTime <= ?3 and  m.maintenanceMan = ?1")
    List<MaintenancePlan> findByMaintenanceManAndPlanTimeGreaterThanAndPlanEndTimeLessThan(String name,Date planTime,Date planEndTime);

    @Query(value="select m from MaintenancePlan m where m.planBathId= ?1")
    List<MaintenancePlan> findByBathId(String bathId);


    List<MaintenancePlan>  findByElevatorIdAndPlanType(String elevatorId,Integer planType);
}

