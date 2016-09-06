package juli.repository;

import juli.domain.ElevatorBrand;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElevatorBrandRepository extends PagingAndSortingRepository<ElevatorBrand,String>,JpaSpecificationExecutor{
    ElevatorBrand findByName(String name);
    @Query(value="select eb.name from ElevatorBrand eb where id=?1")
    String findName(String id);
}
