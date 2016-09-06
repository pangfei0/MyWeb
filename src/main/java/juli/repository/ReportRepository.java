package juli.repository;

import juli.domain.Reports;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

/**
 * Created by pf on 2016/2/4.
 */
public interface ReportRepository extends PagingAndSortingRepository<Reports, String>, JpaSpecificationExecutor {
    @Query(value="select report from  Reports report where report.maintenancePersonnel.id = ?1 and report.reportCategory = ?2 and reportStatus =?3")
    Page<Reports> findPersonalReportByCategroyAndStatus(String id,Integer reportCategory,Integer reportstatus, Pageable pageable);

    Page<Reports> findByReportStatus(Integer reportStatus,Pageable pageable);

    @Query(value="select report from  Reports report where report.currentBill.id = ?1")
    List<Reports> findReportsByWorkBillId(String id);
    @Query(value="select report from  Reports report where report.currentBill.id = ?1")
    Reports findReportByWorkBillId(String id);
    @Query("select report.id from Reports report join report.company company where company.id = ?1")
    List<String> findReportsIds(String companyId);
    @Query("select maintenancePersonnel.maintainer.id from MaintenancePersonnel maintenancePersonnel  where maintenancePersonnel.name like ?1")
    String findCompanyId(String personName);
    @Query("select report.id from Reports report join report.maintenancePersonnel maintenancePersonnel where maintenancePersonnel.name like ?1")
    List<String> findReportsIdsByPersonName(String name);

    @Query(value="select report from  Reports report where report.maintenancePersonnel.id = ?1 and report.reportStatus = 20 and report.startTime>=?2 and report.startTime<=?3 and report.generalEvaluate=?4")
    Page<Reports> findWorkBillsByGeneralEvaluate(String id,Date startTime,Date completeTime,Integer generalEvaluate,Pageable pageable);

    @Query(value="select report from  Reports report where report.maintenancePersonnel.id = ?1 and report.reportStatus = 20 and report.startTime>=?2 and report.startTime<=?3 and (report.generalEvaluate=20 or report.generalEvaluate=30)")
    Page<Reports> findGoodWorkBills(String id,Date startTime,Date completeTime,Pageable pageable);
}
