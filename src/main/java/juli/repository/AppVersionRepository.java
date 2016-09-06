package juli.repository;

import juli.domain.AppVersion;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by pf on 2016/3/3.
 */
public interface AppVersionRepository extends PagingAndSortingRepository<AppVersion, String>, JpaSpecificationExecutor {

    AppVersion findByIsNew(Integer isNew);
}
