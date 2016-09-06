package juli.repository;

import juli.domain.fault.ElevatorFault;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElevatorFaultRepository extends PagingAndSortingRepository<ElevatorFault,String>,JpaSpecificationExecutor{
}
