package juli.repository;

import juli.domain.Organization;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepository extends PagingAndSortingRepository<Organization, String>, JpaSpecificationExecutor {
    List<Organization> findByParentIsNull();
}
