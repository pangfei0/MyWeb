package juli.repository;

import juli.domain.MaintenancePlanBath;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *  create by zj
 */

@Repository
public interface MaintenancePlanBathRepository extends PagingAndSortingRepository<MaintenancePlanBath,String>,JpaSpecificationExecutor{
    MaintenancePlanBath findById(String id);
}
