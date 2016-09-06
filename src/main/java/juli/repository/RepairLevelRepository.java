package juli.repository;

import juli.domain.Company;
import juli.domain.ElevatorBrand;
import juli.domain.RepairLevel;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by pf on 2016/2/25.
 */
public interface RepairLevelRepository  extends PagingAndSortingRepository<RepairLevel, String>, JpaSpecificationExecutor {
    List<RepairLevel> findByElevatorBrand(ElevatorBrand elevatorBrand);
}
