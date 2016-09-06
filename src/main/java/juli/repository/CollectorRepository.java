package juli.repository;

import juli.domain.Collector;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectorRepository extends PagingAndSortingRepository<Collector, String>, JpaSpecificationExecutor {
    Collector findByIntelHardwareNumber(String intelHardwareNumber);

    @Query(value = "select distinct intel_hardware_number from elevator  where install_company_id =?1",nativeQuery = true)
    List<String> findByCompanyIdInstall(String companyId);
    @Query(value = "select distinct intel_hardware_number from elevator  where maintainer_id =?1",nativeQuery = true)
    List<String> findByCompanyIdMaintainer(String companyId);
    @Query(value = "select distinct intel_hardware_number from elevator  where user_company_id =?1",nativeQuery = true)
    List<String> findByCompanyIdUser(String companyId);
    @Query(value = "select distinct intel_hardware_number from elevator  where manufacturer_id =?1",nativeQuery = true)
    List<String> findByCompanyIdManufacturer(String companyId);
    @Query(value = "select distinct intel_hardware_number from elevator  where owner_company_id =?1",nativeQuery = true)
    List<String> findByCompanyIdOwner(String companyId);
    @Query(value = "select distinct intel_hardware_number from elevator  where regulator_id =?1",nativeQuery = true)
    List<String> findByCompanyIdRegualtor(String companyId);
    @Query(value = "select distinct intel_hardware_number from elevator  where personal_id =?1",nativeQuery = true)
    List<String> findByCompanyIdPersonal(String companyId);
    @Query(value = "select distinct intel_hardware_number from elevator  where others_id =?1",nativeQuery = true)
    List<String> findByCompanyIdOthers(String companyId);

}
