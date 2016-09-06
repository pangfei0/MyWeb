package juli.repository;

import juli.domain.Translation;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranslationRepository extends PagingAndSortingRepository<Translation, String>, JpaSpecificationExecutor {
}