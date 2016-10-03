package juli.repository;

import juli.domain.MaintenancePersonnel;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by admin on 2016/6/27.
 */
@Repository
public interface MaintenancePersonnelRepository  extends PagingAndSortingRepository<MaintenancePersonnel,String>,JpaSpecificationExecutor{
    List<MaintenancePersonnel> findByManager(String manager);
    List<MaintenancePersonnel> findByParentId(String parentId);
    @Query(value = "select * from maintenance_personnel  where id in( select parent_id from maintenance_personnel where maintainer_id =?1 ) order by convert(manager using gbk) asc", nativeQuery = true)
    List<MaintenancePersonnel> getAllManager(String id);
    MaintenancePersonnel findById(String id);
    MaintenancePersonnel findByNumber(String number);
    @Query(value="select name from maintenance_personnel where maintainer_id=?1 order by convert(name using gbk) asc",nativeQuery = true)
    List<String> findByMaintainerId(String id);
    @Query(value = "select mp.id from maintenance_personnel mp where mp.maintainer_id = ?1",nativeQuery = true)
    List<MaintenancePersonnel> findByComapanyId(String companyId);
    @Query(value = "select mp.id from maintenance_personnel mp where mp.maintainer_id = ?1",nativeQuery = true)
    List<String> findPersonIdsByCompanyId(String companyId);
    @Query(value="select name from maintenance_personnel where manager=?1 order by convert(name using gbk) asc",nativeQuery = true)
    List<String> fingByManager(String manager);
    MaintenancePersonnel findByUid(String uid);
    @Query(value = "select mp.id from maintenance_personnel mp where mp.name= ?1",nativeQuery = true)
    String findIdByName(String name);
    @Query(value = "select mp.manager from maintenance_personnel mp where mp.id= ?1",nativeQuery = true)
    String findManager(String id);
    @Query(value = "select mp.name from maintenance_personnel mp where mp.id= ?1",nativeQuery = true)
    String findName(String id);
    @Query(value = "select mp from maintenance_personnel mp where mp.maintainer_id= ?1",nativeQuery = true)

    List<MaintenancePersonnel>  findByCompanyIdAndPlanType(String companyId,Integer planType);
}
