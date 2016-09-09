package juli.repository;

import juli.domain.Visitor;
import juli.domain.WorkBills;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by pangfei on 2016/9/6.
 */
@Repository
public interface VisitorRepository extends PagingAndSortingRepository<Visitor, String>, JpaSpecificationExecutor {

    Visitor findVisitorByOpenid(String Openid);
}