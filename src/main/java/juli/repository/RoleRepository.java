package juli.repository;


import juli.domain.Role;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends PagingAndSortingRepository<Role, String>, JpaSpecificationExecutor {
    Role findById(String id);
    Role findByName(String name);
    @Query(value="select distinct id from role where parent_id =?1",nativeQuery = true)
    List<String> findroleIdsByUserId(String userId);

    @Query(value = "select * from role where name like ?1",nativeQuery = true)
    Role findByType(String type);

    @Query(value="select * from role where parent_id=?1",nativeQuery = true)
    List<Role> findRoleByParentId(String id);
}