package juli.repository;

import juli.domain.Area;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaRepository extends PagingAndSortingRepository<Area, String>, JpaSpecificationExecutor {
    List<Area> findByParent(String parent);

    Area findByName(String name);

    Area findByNameAndParent(String name, String parentId);
}
