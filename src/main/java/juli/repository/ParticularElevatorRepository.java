package juli.repository;

import juli.domain.ParticularElevator;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticularElevatorRepository extends PagingAndSortingRepository<ParticularElevator, String>, JpaSpecificationExecutor {

    @Query(value = "select * from particular_elevator e where e.type_id = ?1 order by time desc LIMIT 1",nativeQuery = true)
    ParticularElevator getLastOneByType(String typeId);

}
