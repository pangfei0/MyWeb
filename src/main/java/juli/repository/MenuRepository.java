package juli.repository;

import juli.domain.Menu;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends PagingAndSortingRepository<Menu, String>, JpaSpecificationExecutor {
    List<Menu> findByCategory(String category);

    @Query(value = "select distinct category from menu", nativeQuery = true)
    List<String> findAllCategory();
}
