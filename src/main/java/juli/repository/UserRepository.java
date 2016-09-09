package juli.repository;

import juli.domain.Company;
import juli.domain.Organization;
import juli.domain.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, String>, JpaSpecificationExecutor {
    User findByUserName(String userName);
    User findById(String id);
    User findByOpenid(String openid);
    List<User> findByOrganization(Organization organization);

    @Query(value="select * from company where type=?1",nativeQuery = true)
    List<Company> findCompanyByType(String type);

    @Query(value="select id from user where company_id=?1",nativeQuery = true)
    List<String> findUserIdByCompanyId(String companyId);



}