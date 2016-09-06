package juli.repository;

import juli.domain.Company;
import juli.domain.Elevator;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CompanyRepository extends PagingAndSortingRepository<Company, String>, JpaSpecificationExecutor {
    Company findByNameAndType(String name,Integer type);
    List<Company> findByName(String name);
    @Query(value = "select * from company  where name=?1 and type= 20 ", nativeQuery = true)
    Company findByNameForCompany(String name);
    Company findByAddress(String address);
    Company findById(String id);
    @Query(value = "select name from company  where id=?1", nativeQuery = true)
    String findName(String id);
    @Query(value = "select * from company  where type=?1 order by convert(name using gbk) asc", nativeQuery = true)
    List<Company> findOrderByName(int type);

    List<Company> findByType(Integer type);

    @Query(value = "select * from company  where type=?1 order by convert(name using gbk) asc", nativeQuery = true)
    List<Company> findByType(int type);

    @Query(value = "select * from company where type =?1  and name like ?2 order by convert(name using gbk) asc ", nativeQuery = true)
    List<Company> findByNameAndType(int type1,String name);

    @Query(value = "select company.id from Company company where type =?1")
    List<String> findByType2(int type1);

    @Query(value = "select * from company where type in(?1,?2) order by convert(name using gbk) asc ", nativeQuery = true)
    List<Company> findByType(int type1, int type2);

    @Query(value = "select * from company where type in(?1,?2)  and name like ?3 order by convert(name using gbk) asc ", nativeQuery = true)
    List<Company> findByType(int type1, int type2,String name);
    @Query(value = "select * from company  where type=?1 and name like ?2 order by convert(name using gbk) asc", nativeQuery = true)
    List<Company> findByType(int type,String name);
    @Transactional
    @Modifying
    @Query(value = "update elevator e set e.maintenance_id = ?1,e.maintainer_id=?3  where e.id=?2", nativeQuery = true)
    void updateElevatorMaintenanceMan(String maintenanceId, String elevatorId, String companyId);

    @Query(value = "select id from company where type=?1 and name like ?2", nativeQuery = true)
    String findCompanyIdByNameAndType(int type, String name);

    @Query(value = "select id from company where type=?1 and name = ?2", nativeQuery = true)
    String findIdByNameAndType(int type, String name);

    @Query(value = "select distinct project_name from elevator where install_company_id is null order by convert(project_name using gbk) asc", nativeQuery = true)
    List<String> getInstallCompanyUseProjectNames();

    @Query(value = "select distinct project_name from elevator where maintainer_id is null order by convert(project_name using gbk) asc", nativeQuery = true)
    List<String> getMaintainerCompanyUseProjectNames();

    @Query(value = "select distinct project_name from elevator where user_company_id is null order by convert(project_name using gbk) asc", nativeQuery = true)
    List<String> getUserCompanyUseProjectNames();

    @Query(value = "select distinct project_name from elevator where manufacturer_id is null order by convert(project_name using gbk) asc", nativeQuery = true)
    List<String> getManufacturerCompanyUseProjectNames();

    @Query(value = "select distinct project_name from elevator where owner_company_id is null order by convert(project_name using gbk) asc", nativeQuery = true)
    List<String> getOwnerCompanyUseProjectNames();

    @Query(value = "select distinct project_name from elevator where regulator_id is null order by convert(project_name using gbk) asc", nativeQuery = true)
    List<String> getRegulatorCompanyUseProjectNames();

    @Query(value = "select distinct project_name from elevator where personal_id is null order by convert(project_name using gbk) asc", nativeQuery = true)
    List<String> getPersonalCompanyUseProjectNames();

    @Query(value = "select distinct project_name from elevator where others_id is null order by convert(project_name using gbk) asc", nativeQuery = true)
    List<String> getOthersCompanyUseProjectNames();


    @Query(value = "select distinct project_name from elevator where install_company_id is null and project_name like ?1 order by convert(project_name using gbk) asc", nativeQuery = true)
    List<String> getInstallCompanyUseProjectNames(String projectName );

    @Query(value = "select distinct project_name from elevator where maintainer_id is null and project_name like ?1 order by convert(project_name using gbk) asc", nativeQuery = true)
    List<String> getMaintainerCompanyUseProjectNames(String projectName );

    @Query(value = "select distinct project_name from elevator where user_company_id is null and project_name like ?1 order by convert(project_name using gbk) asc", nativeQuery = true)
    List<String> getUserCompanyUseProjectNames(String projectName );

    @Query(value = "select distinct project_name from elevator where manufacturer_id is null and project_name like ?1 order by convert(project_name using gbk) asc", nativeQuery = true)
    List<String> getManufacturerCompanyUseProjectNames(String projectName );

    @Query(value = "select distinct project_name from elevator where owner_company_id is null and project_name like ?1 order by convert(project_name using gbk) asc", nativeQuery = true)
    List<String> getOwnerCompanyUseProjectNames(String projectName );

    @Query(value = "select distinct project_name from elevator where regulator_id is null and project_name like ?1 order by convert(project_name using gbk) asc", nativeQuery = true)
    List<String> getRegulatorCompanyUseProjectNames(String projectName );

    @Query(value = "select distinct project_name from elevator where personal_id is null and project_name like ?1 order by convert(project_name using gbk) asc", nativeQuery = true)
    List<String> getPersonalCompanyUseProjectNames(String projectName );

    @Query(value = "select distinct project_name from elevator where others_id is null and project_name like ?1 order by convert(project_name using gbk) asc", nativeQuery = true)
    List<String> getOthersCompanyUseProjectNames(String projectName );



    @Query(value = "select distinct id  from elevator where project_name =?1 and install_company_id is null ", nativeQuery = true)
    List<String> getElevatorIdsByProjectNamesAndTypeInstall(String projectName);

    @Query(value = "select distinct id  from elevator where project_name =?1 and maintainer_id is null ", nativeQuery = true)
    List<String> getElevatorIdsByProjectNamesAndTypeMaintainer(String projectName);

    @Query(value = "select distinct id  from elevator where project_name =?1 and user_company_id is null ", nativeQuery = true)
    List<String> getElevatorIdsByProjectNamesAndTypeUser(String projectName);

    @Query(value = "select distinct id  from elevator where project_name =?1 and manufacturer_id is null ", nativeQuery = true)
    List<String> getElevatorIdsByProjectNamesAndTypeManufacturer(String projectName);

    @Query(value = "select distinct id  from elevator where project_name =?1 and owner_company_id is null ", nativeQuery = true)
    List<String> getElevatorIdsByProjectNamesAndTypeOwner(String projectName);

    @Query(value = "select distinct id  from elevator where project_name =?1 and regulator_id is null ", nativeQuery = true)
    List<String> getElevatorIdsByProjectNamesAndTypeRegulator(String projectName);

    @Query(value = "select distinct id  from elevator where project_name =?1 and personal_id is null ", nativeQuery = true)
    List<String> getElevatorIdsByProjectNamesAndTypePersonal(String projectName);

    @Query(value = "select distinct id  from elevator where project_name =?1 and others_id is null ", nativeQuery = true)
    List<String> getElevatorIdsByProjectNamesAndTypeOthers(String projectName);

    @Query(value = "select distinct id  from elevator where install_company_id is null ", nativeQuery = true)
    List<String> getElevatorIdsByTypeInstall();

    @Query(value = "select distinct id  from elevator where maintainer_id is null ", nativeQuery = true)
    List<String> getElevatorIdsByTypeMaintainer();

    @Query(value = "select distinct id  from elevator where user_company_id is null ", nativeQuery = true)
    List<String> getElevatorIdsByTypeUser();

    @Query(value = "select distinct id  from elevator where manufacturer_id is null ", nativeQuery = true)
    List<String> getElevatorIdsByTypeManufacturer();

    @Query(value = "select distinct id  from elevator where owner_company_id is null ", nativeQuery = true)
    List<String> getElevatorIdsByTypeOwner();

    @Query(value = "select distinct id  from elevator where regulator_id is null ", nativeQuery = true)
    List<String> getElevatorIdsByTypeRegulator();

    @Query(value = "select distinct id  from elevator where personal_id is null ", nativeQuery = true)
    List<String> getElevatorIdsByTypePersonal();

    @Query(value = "select distinct id  from elevator where others_id is null ", nativeQuery = true)
    List<String> getElevatorIdsByTypeOthers();



    @Query(value = "select elevator from Elevator elevator where elevator.installCompanyId = ?1 ")
    List<Elevator> getRelatedElevatorForInstallCompany(String companyId);

    @Query(value = "select elevator from Elevator elevator where elevator.maintainerId = ?1 ")
    List<Elevator> getRelatedElevatorForMaintainerCompany(String companyId);

    @Query(value = "select elevator from Elevator elevator where elevator.userCompanyId = ?1 ")
    List<Elevator> getRelatedElevatorForUserCompany(String companyId);

    @Query(value = "select elevator from Elevator elevator where elevator.manufacturerId = ?1 ")
    List<Elevator> getRelatedElevatorForManufacturer(String companyId);

    @Query(value = "select elevator from Elevator elevator where elevator.regulatorId = ?1 ")
    List<Elevator> getRelatedElevatorForRegulator(String companyId);

    @Query(value = "select elevator from Elevator elevator where elevator.personalId = ?1 ")
    List<Elevator> getRelatedElevatorForPersonal(String companyId);

    @Query(value = "select elevator from Elevator elevator where elevator.othersId = ?1 ")
    List<Elevator> getRelatedElevatorForOthers(String companyId);

    @Query(value = "select elevator from Elevator elevator where elevator.ownerCompanyId = ?1 ")
    List<Elevator> getRelatedElevatorForOwnerCompany(String companyId);

    @Transactional
    @Modifying
    @Query(value = "update elevator e set e.install_company_id = ?1  where e.id=?2", nativeQuery = true)
    void updateElevatorByInstallTypeForAddCompany(String companyId, String elevatorId);

    @Transactional
    @Modifying
    @Query(value = "update elevator e set e.maintainer_id = ?1  where e.id=?2", nativeQuery = true)
    void updateElevatorByMaintainerTypeForAddCompany(String companyId, String elevatorId);

    @Transactional
    @Modifying
    @Query(value = "update elevator e set e.user_company_id = ?1  where e.id=?2", nativeQuery = true)
    void updateElevatorByUserTypeForAddCompany(String companyId, String elevatorId);

    @Transactional
    @Modifying
    @Query(value = "update elevator e set e.manufacturer_id = ?1  where e.id=?2", nativeQuery = true)
    void updateElevatorByManufacturerTypeForAddCompany(String companyId, String elevatorId);

    @Transactional
    @Modifying
    @Query(value = "update elevator e set e.regulator_id = ?1  where e.id=?2", nativeQuery = true)
    void updateElevatorByRegulatorTypeForAddCompany(String companyId, String elevatorId);

    @Transactional
    @Modifying
    @Query(value = "update elevator e set e.personal_id = ?1  where e.id=?2", nativeQuery = true)
    void updateElevatorByPersonalTypeForAddCompany(String companyId, String elevatorId);

    @Transactional
    @Modifying
    @Query(value = "update elevator e set e.others_id = ?1  where e.id=?2", nativeQuery = true)
    void updateElevatorByOthersypeForAddCompany(String companyId, String elevatorId);

    @Transactional
    @Modifying
    @Query(value = "update elevator e set e.owner_company_id = ?1  where e.id=?2", nativeQuery = true)
    void updateElevatorByOwnersypeForAddCompany(String companyId, String elevatorId);


    @Transactional
    @Modifying
    @Query(value = "update elevator e set e.install_company_id = null  where e.install_company_id=?1", nativeQuery = true)
    void updateElevatorByInstallTypeForDeleteCompany(String companyId);

    @Transactional
    @Modifying
    @Query(value = "update elevator e set e.maintainer_id = null  where e.maintainer_id=?1", nativeQuery = true)
    void updateElevatorByMaintainerTypeForDeleteCompany(String companyId);

    @Transactional
    @Modifying
    @Query(value = "update elevator e set e.user_company_id = null  where e.user_company_id=?1", nativeQuery = true)
    void updateElevatorByUserCompanyTypeForDeleteCompany(String companyId);

    @Transactional
    @Modifying
    @Query(value = "update elevator e set e.manufacturer_id = null  where e.manufacturer_id=?1", nativeQuery = true)
    void updateElevatorByManufacturerTypeForDeleteCompany(String companyId);

    @Transactional
    @Modifying
    @Query(value = "update elevator e set e.regulator_id = null  where e.regulator_id=?1", nativeQuery = true)
    void updateElevatorByRegualtorTypeForDeleteCompany(String companyId);

    @Transactional
    @Modifying
    @Query(value = "update elevator e set e.personal_id = null  where e.personal_id=?1", nativeQuery = true)
    void updateElevatorByPersonalTypeForDeleteCompany(String companyId);

    @Transactional
    @Modifying
    @Query(value = "update elevator e set e.others_id = null  where e.others_id=?1", nativeQuery = true)
    void updateElevatorByOthersTypeForDeleteCompany(String companyId);

    @Transactional
    @Modifying
    @Query(value = "update elevator e set e.owner_company_id = null  where e.owner_company_id=?1", nativeQuery = true)
    void updateElevatorByOwnerCompanyTypeForDeleteCompany(String companyId);


    @Transactional
    @Modifying
    @Query(value = "update elevator e set e.install_company_id = null  where e.install_company_id=?1 and e.id=?2", nativeQuery = true)
    void updateSingleElevatorByInStallType(String company_id, String elevator_id);

    @Transactional
    @Modifying
    @Query(value = "update elevator e set e.maintainer_id = null  where e.maintainer_id=?1 and e.id=?2", nativeQuery = true)
    void updateSingleElevatorByMaintainerType(String company_id, String elevator_id);

    @Transactional
    @Modifying
    @Query(value = "update elevator e set e.user_company_id = null  where e.user_company_id=?1 and e.id=?2", nativeQuery = true)
    void updateSingleElevatorByUserCompanyType(String company_id, String elevator_id);

    @Transactional
    @Modifying
    @Query(value = "update elevator e set e.manufacturer_id = null  where e.manufacturer_id=?1 and e.id=?2", nativeQuery = true)
    void updateSingleElevatorByManufacturerType(String company_id, String elevator_id);

    @Transactional
    @Modifying
    @Query(value = "update elevator e set e.regulator_id = null  where e.regulator_id=?1 and e.id=?2", nativeQuery = true)
    void updateSingleElevatorByRegulatorType(String company_id, String elevator_id);

    @Transactional
    @Modifying
    @Query(value = "update elevator e set e.personal_id = null  where e.personal_id=?1 and e.id=?2", nativeQuery = true)
    void updateSingleElevatorByPersonalType(String company_id, String elevator_id);

    @Transactional
    @Modifying
    @Query(value = "update elevator e set e.others_id = null  where e.others_id=?1 and e.id=?2", nativeQuery = true)
    void updateSingleElevatorByOthersType(String company_id, String elevator_id);

    @Transactional
    @Modifying
    @Query(value = "update elevator e set e.owner_company_id = null  where e.owner_company_id=?1 and e.id=?2", nativeQuery = true)
    void updateSingleElevatorByOwnerCompanyType(String company_id, String elevator_id);

}
