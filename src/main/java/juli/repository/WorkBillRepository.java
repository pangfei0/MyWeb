package juli.repository;

import juli.domain.Elevator;
import juli.domain.MaintenancePersonnel;
import juli.domain.Reports;
import juli.domain.WorkBills;
import org.springframework.data.domain.Page;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface WorkBillRepository extends PagingAndSortingRepository<WorkBills, String>, JpaSpecificationExecutor {

    @Query("select bills from WorkBills bills  where bills.elevator.id = ?1  and bills.billCategory = ?2 order by bills.id desc")
    List<WorkBills> findBillsByElevatorId(String elevatorId,Integer billCategory);

    @Query(value="select bills from  WorkBills bills where bills.maintenancePersonnel.id = ?1 and bills.billCategory = ?2 and billstatus> ?3 and billstatus < ?4")
    Page<WorkBills> findPersonalBillByCategroyAndStatus(String id,Integer billCategory,Integer billstatus1,Integer billstatus2, Pageable pageable);

    @Query(value="select bills from  WorkBills bills where bills.maintenancePersonnel.id = ?1 and bills.billCategory = ?2 and billstatus> ?3 and billstatus < ?4")
    List<WorkBills> findPersonalBillByCategroyAndStatus(String id,Integer billCategory,Integer billstatus1,Integer billstatus2);

    @Query(value="select bills from WorkBills bills where bills.billstatus > ?1 and billstatus < ?2")
    Page<WorkBills> findBillsByStatus(Integer status1,Integer status2,Pageable pageable);

    Page findBillsByElevatorAndBillCategoryAndBillstatus(Elevator elevator,Integer billCategory,Integer billstatus,Pageable pageable);

    @Query(value="select bills from WorkBills bills where bills.startTime > ?1 and bills.startTime < ?2 and bills.enumber LIKE ?3 and bills.billCategory=?4")
    List<WorkBills> findBillsByTimeAndElevator(Date time1,Date time2,String elevatorNumber,Integer billCategory);

    @Query(value="select bills from WorkBills bills where bills.enumber LIKE ?1 and bills.billCategory=?2")
    List<WorkBills> findBillsByElevator(String elevatorNumber,Integer billCategory);

    @Query(value="select bills from WorkBills bills where bills.startTime > ?1 and bills.startTime < ?2 and bills.billCategory=?3")
    List<WorkBills> findBillsByTime(Date time1,Date time2,Integer billCategory);

    long countByAndBillCategoryAndBillstatusAndMaintenancePersonnelAndStartTimeGreaterThanAndCompleteTimeLessThan(Integer billCategory,Integer billstatus,MaintenancePersonnel maintenancePersonnel,Date starttime,Date completeTime);

    long countByAndBillCategoryAndBillstatusAndMaintenancePersonnel(Integer billCategory,Integer billstatus,MaintenancePersonnel maintenancePersonnel);

    @Query(value="select bills from WorkBills bills join bills.elevator elevator where bills.billstatus < ?1 and bills.billCategory = ?2 and elevator.maintainerId = ?3")
    Page<WorkBills> findByBillstatusLessThanAndBillCategory(Integer billstatus,Integer billCategory,String companyId,Pageable pageable);

    Page<WorkBills> findByBillstatusLessThanAndBillCategory(Integer billstatus,Integer billCategory,Pageable pageable);
//    Page<WorkBills> findByBillsByStatusAndPageable(Integer billstatus,Pageable pageable);
//    Page<WorkBills> findByBillsByCategoryPageable(Integer billCategory,Pageable pageable);
    long countByAndBillCategoryAndBillstatusLessThanAndMaintenancePersonnel(Integer billCategory,Integer billstatus,MaintenancePersonnel maintenancePersonnel);

//    @Query("select bills from WorkBills bills where bills.billCategory <> ?1 and bills.billstatus < ?2")
    Page<WorkBills> findByBillCategoryNotAndBillstatusLessThan(Integer billCategory, Integer billstatus, Pageable pageable);

    @Query("select bills.id from WorkBills bills join bills.elevator elevator where elevator.maintainerId = ?1")
    List<String> findWorkBillsIds(String companyId);

    @Query("select bills from WorkBills bills where  bills.actor =?1 and bills.elevator.number=?2 and bills.billNumber like ?3 and bills.billstatus in(10,40,45)")
    WorkBills findByActorAndEnumberAndBillNumber(String actor,String enumber,String billNumber);


    @Transactional
    @Modifying
    @Query(value = "update maintenance_personnel w set w.current_bill_id = null  where w.current_bill_id=?1 ", nativeQuery = true)
    void updateCurrentBill(String current_bill_id);

    //一些数量
    @Query(value = "select count(w.id) from  work_bills w  where w.maintenance_personnel_id=?1 and w.billstatus = 50 ", nativeQuery = true)
    long findAllBillsCount(String id);

    @Query(value = "select count(w.id) from  work_bills w  where w.maintenance_personnel_id=?1 and w.bill_model = 0 and w.billstatus =50", nativeQuery = true)
    long findBYBillsCount(String id);

    @Query(value = "select count(w.id) from  work_bills w  where w.maintenance_personnel_id=?1 and w.bill_model = 10 and w.billstatus =50", nativeQuery = true)
    long findJDBillsCount(String id);

    @Query(value = "select count(w.id) from  work_bills w  where w.maintenance_personnel_id=?1 and w.bill_model = 20 and w.billstatus =50", nativeQuery = true)
    long findBNBillsCount(String id);

    @Query(value = "select count(w.id) from  work_bills w  where w.maintenance_personnel_id=?1 and w.bill_model = 30 and w.billstatus =50", nativeQuery = true)
    long findQNBillsCount(String id);

    @Query(value = "select count(w.id) from  work_bills w  where w.maintenance_personnel_id=?1 and w.bill_model = 50 and w.billstatus =50", nativeQuery = true)
    long findWXBillsCount(String id);

    @Query(value = "select count(w.id) from  work_bills w  where w.maintenance_personnel_id=?1 and w.bill_model = 40 and w.billstatus =50", nativeQuery = true)
    long findJXBillsCount(String id);

    @Query(value = "select count(w.id) from  work_bills w  where w.maintenance_personnel_id=?1 and (w.billstatus = 40 or w.billstatus = 45) ", nativeQuery = true)
    long findjjBillsCount(String id);

    @Query(value = "select count(r.id) from  reports r  where r.maintenance_personnel_id=?1 and r.general_evaluate = 10 and r.report_status =20", nativeQuery = true)
    long findEXCELENTBillsCount(String id);

    @Query(value = "select count(r.id) from  reports r  where r.maintenance_personnel_id=?1 and r.general_evaluate = 20 and r.report_status =20", nativeQuery = true)
    long findGOODBillsCount(String id);

    @Query(value = "select count(r.id) from  reports r  where r.maintenance_personnel_id=?1 and r.general_evaluate = 30 and r.report_status =20", nativeQuery = true)
    long findCOMMONBillsCount(String id);

    @Query(value = "select count(r.id) from  reports r  where r.maintenance_personnel_id=?1 and r.general_evaluate = 40 and r.report_status =20", nativeQuery = true)
    long findPOORBillsCount(String id);

    @Query(value = "select count(r.id) from  reports r  where r.maintenance_personnel_id=?1 and r.general_evaluate is null and r.report_status <>20", nativeQuery = true)
    long findNOCOMMENTBillsCount(String id);

    @Query(value = "SELECT AVG(TIMESTAMPDIFF(MINUTE,w.start_time,w.complete_time)) from work_bills w where w.bill_model=40 and w.maintenance_personnel_id=?1 and w.billstatus =50", nativeQuery = true)
    Double findJXBillsAVGTime(String id);

    @Query(value = "SELECT AVG(TIMESTAMPDIFF(MINUTE,w.start_time,w.complete_time)) from work_bills w where w.bill_model=50 and w.maintenance_personnel_id=?1 and w.billstatus =50", nativeQuery = true)
    Double findWXBillsAVGTime(String id);

    //根据时间

    @Query(value = "select count(w.id) from  work_bills w  where w.maintenance_personnel_id=?1 and w.start_time >=?2 and w.start_time <= ?3 and w.billstatus =50", nativeQuery = true)
    long findAllBillsCountByTime(String id,Date startTime,Date completeTime);

    @Query(value = "select count(w.id) from  work_bills w  where w.maintenance_personnel_id=?1 and w.bill_model = 0 and w.start_time >=?2 and w.start_time <= ?3 and w.billstatus =50", nativeQuery = true)
    long findBYBillsCountByTime(String id,Date startTime,Date completeTime);

    @Query(value = "select count(w.id) from  work_bills w  where w.maintenance_personnel_id=?1 and w.bill_model = 10 and w.start_time >=?2 and w.start_time <= ?3 and w.billstatus =50", nativeQuery = true)
    long findJDBillsCountByTime(String id,Date startTime,Date completeTime);

    @Query(value = "select count(w.id) from  work_bills w  where w.maintenance_personnel_id=?1 and w.bill_model = 20 and w.start_time >=?2 and w.start_time <= ?3 and w.billstatus =50", nativeQuery = true)
    long findBNBillsCountByTime(String id,Date startTime,Date completeTime);

    @Query(value = "select count(w.id) from  work_bills w  where w.maintenance_personnel_id=?1 and w.bill_model = 30 and w.start_time >=?2 and w.start_time <= ?3 and w.billstatus =50", nativeQuery = true)
    long findQNBillsCountByTime(String id,Date startTime,Date completeTime);

    @Query(value = "select count(w.id) from  work_bills w  where w.maintenance_personnel_id=?1 and w.bill_model = 50 and w.start_time >=?2 and w.start_time <= ?3 and w.billstatus =50", nativeQuery = true)
    long findWXBillsCountByTime(String id,Date startTime,Date completeTime);

    @Query(value = "select count(w.id) from  work_bills w  where w.maintenance_personnel_id=?1 and w.bill_model = 40 and w.start_time >=?2 and w.start_time <= ?3 and w.billstatus =50", nativeQuery = true)
    long findJXBillsCountByTime(String id,Date startTime,Date completeTime);

    @Query(value = "select count(w.id) from  work_bills w  where w.maintenance_personnel_id=?1 and w.start_time >=?2 and w.start_time <= ?3 and (w.billstatus = 40 or w.billstatus = 45)", nativeQuery = true)
    long findJjBillsCountByTime(String id,Date startTime,Date completeTime);

    @Query(value = "select count(r.id) from  reports r  where r.maintenance_personnel_id=?1 and r.general_evaluate = 10 and r.start_time >=?2 and r.start_time <= ?3  and r.report_status =20", nativeQuery = true)
    long findEXCELENTBillsCountByTime(String id,Date startTime,Date completeTime);

    @Query(value = "select count(r.id) from  reports r  where r.maintenance_personnel_id=?1 and r.general_evaluate = 20 and r.start_time >=?2 and r.start_time <= ?3  and r.report_status =20", nativeQuery = true)
    long findGOODBillsCountByTime(String id,Date startTime,Date completeTime);

    @Query(value = "select count(r.id) from  reports r  where r.maintenance_personnel_id=?1 and r.general_evaluate = 30 and r.start_time >=?2 and r.start_time <= ?3  and r.report_status =20", nativeQuery = true)
    long findCOMMONBillsCountByTime(String id,Date startTime,Date completeTime);

    @Query(value = "select count(r.id) from  reports r  where r.maintenance_personnel_id=?1 and r.general_evaluate = 40 and r.start_time >=?2 and r.start_time <= ?3  and r.report_status =20", nativeQuery = true)
    long findPOORBillsCountByTime(String id,Date startTime,Date completeTime);

    @Query(value = "select count(r.id) from  reports r  where r.maintenance_personnel_id=?1 and r.general_evaluate is null and r.start_time >=?2 and r.start_time <= ?3 and r.report_status <>20", nativeQuery = true)
    long findNOCOMMENTBillsCountByTime(String id,Date startTime,Date completeTime);

    @Query(value = "SELECT AVG(TIMESTAMPDIFF(MINUTE,w.start_time,w.complete_time)) from work_bills w where w.bill_model=40 and w.maintenance_personnel_id=?1 and w.billstatus =50 and w.start_time >=?2 and w.start_time <= ?3", nativeQuery = true)
    Double findJXBillsAVGTimeByTime(String id,Date startTime,Date completeTime);

    @Query(value = "SELECT AVG(TIMESTAMPDIFF(MINUTE,w.start_time,w.complete_time)) from work_bills w where w.bill_model=50 and w.maintenance_personnel_id=?1 and w.billstatus =50 and w.start_time >=?2 and w.start_time <= ?3", nativeQuery = true)
    Double findWXBillsAVGTimeByTime(String id,Date startTime,Date completeTime);

    @Query(value = "SELECT AVG(TIMESTAMPDIFF(MINUTE,w.start_time,w.complete_time)) from work_bills w where w.bill_model=0 and w.maintenance_personnel_id=?1 and w.billstatus =50 and w.start_time >=?2 and w.start_time <= ?3", nativeQuery = true)
    Double findBYBillsAVGTimeByTime(String id,Date startTime,Date completeTime);

    @Query(value = "SELECT AVG(TIMESTAMPDIFF(MINUTE,w.start_time,w.complete_time)) from work_bills w where w.bill_model=10 and w.maintenance_personnel_id=?1 and w.billstatus =50 and w.start_time >=?2 and w.start_time <= ?3", nativeQuery = true)
    Double findJDBillsAVGTimeByTime(String id,Date startTime,Date completeTime);

    @Query(value = "SELECT AVG(TIMESTAMPDIFF(MINUTE,w.start_time,w.complete_time)) from work_bills w where w.bill_model=20 and w.maintenance_personnel_id=?1 and w.billstatus =50 and w.start_time >=?2 and w.start_time <= ?3", nativeQuery = true)
    Double findBNBillsAVGTimeByTime(String id,Date startTime,Date completeTime);

    @Query(value = "SELECT AVG(TIMESTAMPDIFF(MINUTE,w.start_time,w.complete_time)) from work_bills w where w.bill_model=30 and w.maintenance_personnel_id=?1 and w.billstatus =50 and w.start_time >=?2 and w.start_time <= ?3", nativeQuery = true)
    Double findQNBillsAVGTimeByTime(String id,Date startTime,Date completeTime);

    @Query(value="select bills from WorkBills bills where bills.billstatus =50 and bills.billModel =40 and bills.maintenancePersonnel.id = ?1 and bills.startTime>=?2 and bills.startTime<=?3")
    Page<WorkBills> findJXWorkBillsByTime(String id,Date startTime,Date completeTime,Pageable pageable);

    @Query(value="select bills from WorkBills bills where bills.billstatus =50 and bills.billModel =50 and bills.maintenancePersonnel.id = ?1 and bills.startTime>=?2 and bills.startTime<=?3")
    Page<WorkBills> findWXWorkBillsByTime(String id,Date startTime,Date completeTime,Pageable pageable);

    @Query(value="select bills from WorkBills bills where bills.billstatus =50 and bills.billModel <>50 and bills.billModel <>40  and bills.maintenancePersonnel.id = ?1 and bills.startTime>=?2 and bills.startTime<=?3")
    Page<WorkBills> findWBWorkBillsByTime(String id,Date startTime,Date completeTime,Pageable pageable);

}


