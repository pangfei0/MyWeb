package juli.repository;

import juli.domain.MaintenancePersonnel;
import juli.domain.WorkBills;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by pf on 2016/2/1.
 */
@Repository
public interface MaintenanceRepository extends PagingAndSortingRepository<MaintenancePersonnel, String>, JpaSpecificationExecutor {
     MaintenancePersonnel findByNumberAndPassword(String number,String password);
     MaintenancePersonnel findById(String id);
     List<MaintenancePersonnel> findByCurrentStateLessThan(Integer currentState);
     List<MaintenancePersonnel> findByMaintainer(String maintainerId);
     List<MaintenancePersonnel> findByMaintainerId(String maintainerId);
     @Query("select mp from MaintenancePersonnel mp  where mp.maintainer.id = ?1")
     List<MaintenancePersonnel> findMaintainerByCompanyId(String companyId);
     @Query("select mp from MaintenancePersonnel mp  where mp.parentId = ?1")
     List<MaintenancePersonnel> findMaintainerByParentId(String parentId);
     @Query("select mp from MaintenancePersonnel mp  where mp.name like ?1 and mp.maintainer.id = ?2")
     List<MaintenancePersonnel> findByNameLike(String name,String companyId);
     @Query(value = "select * from maintenance_personnel where name like ?1 and maintainer_id in ( select maintainer_id from elevator where id=?2) order by convert(name using gbk) asc",nativeQuery = true)
     List<MaintenancePersonnel> findByNameByElevatorId(String name,String elevatorId);

     @Query(value = "select id from maintenance_personnel where name=?1",nativeQuery = true)
     String findIdByName(String name);
     MaintenancePersonnel findByUid(String uid);
     MaintenancePersonnel findByName(String name);
     MaintenancePersonnel findByNumber(String number);
     MaintenancePersonnel findByNameAndNumber(String name,String number);
     MaintenancePersonnel findByNameAndMaintainerId(String name,String maintainerId);
     MaintenancePersonnel findByNumberAndMaintainerId(String number,String maintainerId);
     MaintenancePersonnel findByNameAndNumberAndMaintainerId(String name,String number,String maintainerId);
     List<MaintenancePersonnel> findAll();
}
