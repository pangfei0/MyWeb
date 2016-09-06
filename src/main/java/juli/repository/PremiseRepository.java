package juli.repository;

import juli.domain.Premise;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PremiseRepository extends PagingAndSortingRepository<Premise, String>, JpaSpecificationExecutor {

    @Query(value = "select id from premise where name in( select distinct project_name from elevator where install_company_id=?1) ",nativeQuery = true)
    List<String> findPremiseIdsByCompanyIdInstall(String companyId);
    @Query(value = "select id from premise where name in( select distinct project_name from elevator where maintainer_id=?1) ",nativeQuery = true)
    List<String> findPremiseIdsByCompanyIdMaintainer(String companyId);
    @Query(value = "select id from premise where name in( select distinct project_name from elevator where user_company_id=?1) ",nativeQuery = true)
    List<String> findPremiseIdsByCompanyIdUser(String companyId);
    @Query(value = "select id from premise where name in( select distinct project_name from elevator where manufacturer_id=?1) ",nativeQuery = true)
    List<String> findPremiseIdsByCompanyIdmanufacturer(String companyId);
    @Query(value = "select id from premise where name in( select distinct project_name from elevator where owner_company_id=?1) ",nativeQuery = true)
    List<String> findPremiseIdsByCompanyIdOwner(String companyId);
    @Query(value = "select id from premise where name in( select distinct project_name from elevator where regulator_id=?1) ",nativeQuery = true)
    List<String> findPremiseIdsByCompanyIdRegulator(String companyId);
    @Query(value = "select id from premise where name in( select distinct project_name from elevator where personal_id=?1) ",nativeQuery = true)
    List<String> findPremiseIdsByCompanyIdPersonal(String companyId);
    @Query(value = "select id from premise where name in( select distinct project_name from elevator where others_id=?1) ",nativeQuery = true)
    List<String> findPremiseIdsByCompanyIdOthers(String companyId);
}
