package juli.repository;

import juli.domain.contract.CollectingRecord;
import juli.domain.contract.UpkeepContract;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by pf on 2016/2/27.
 */
public interface CollectingRecordRepository extends PagingAndSortingRepository<CollectingRecord ,String>, JpaSpecificationExecutor {
    List<CollectingRecord> findByUpkeepContract(UpkeepContract upkeepContract);
}
