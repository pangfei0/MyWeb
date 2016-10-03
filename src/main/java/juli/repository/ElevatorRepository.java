package juli.repository;

import juli.domain.Elevator;
import juli.domain.Premise;
import juli.domain.contract.UpkeepContract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElevatorRepository extends PagingAndSortingRepository<Elevator, String>, JpaSpecificationExecutor {
    @Query(value = "select elevator from Elevator elevator where elevator.number=?1")
    Elevator findByNumber(String number);

    List<Elevator> findElevatorByNumber(String number);

    @Query(value = "select elevator from Elevator elevator where elevator.number=?1 and elevator.maintainerId=?2")
    Elevator findByNumber(String number,String companyId);
    Elevator findByDataServerReferenceId(String dataServerReferenceId);

    List<Elevator> findByDataServerReferenceIdNotNull();

    Page<Elevator> findByProtocolTypeNotNull(Pageable pageable);

    @Query(value = "select * from elevator where elevator.id=?1",nativeQuery = true)
    Elevator findElevatorById(String id);

    @Query(value = "select distinct a.name from elevator e left join area a on e.province_id = a.id", nativeQuery = true)
    List<String> findElevatorProvinces();

    @Query(value = "select distinct a.name from elevator e left join area a on e.province_id = a.id where e.status = ?1", nativeQuery = true)
    List<String> findElevatorProvincesByStatus(int status);

    @Query(value = "select distinct a.name from elevator e left join area a on e.province_id = a.id where e.maintenance_status = ?1", nativeQuery = true)
    List<String> findElevatorProvincesByMaintenanceStatus(int status);

    @Query(value = "select distinct a.name from elevator e left join area a on e.province_id = a.id where e.fault_status = ?1", nativeQuery = true)
    List<String> findElevatorProvincesByFaultStatus(int status);

    //故障未处理电梯的信息(分页)
    @Query(value = "select elevator from Elevator elevator where elevator.faultStatus = 20 and elevator.isHandled = 10")
    Page<Elevator> findNotHandledFaultElevatorByPage(Pageable pageable);

    //故障处理中电梯的信息和工单信息(分页)
//    @Query(value = "select wb from workBills wb join wb.elevator e where e.faultStatus = 20 and e.isHandled = 20")
//    Page<Object[]> findHandledFaultElevatorByPage(Pageable pageable);

//    @Query(value = "select e.id,e.number,wb.id,wb.number,wb.billStatus from elevator e, work_bills wb where wb.elevator_id = e.id and e.fault_status = 20 and e.is_handled = 20", nativeQuery = true)
//    List<Object[]> findHandledFaultElevatorByPage();

    @Query(value = "select e from Elevator e where e.faultStatus = 20 and e.isHandled = 20")
    Page<Elevator> findHandledFaultElevatorByPage(Pageable pageable);

    @Query(value = "select elevator from Elevator elevator where elevator.faultStatus = 20 and elevator.isHandled = ?1")
    List<Elevator> findFaultStatus(Integer isHandled);

    @Query(value = "select elevator from Elevator elevator where elevator.faultStatus = 20 and elevator.isHandled = ?1 and elevator.installCompanyId=?2")
    List<Elevator> findFaultStatusInstall(Integer isHandled,String companyId);
    @Query(value = "select elevator from Elevator elevator where elevator.faultStatus = 20 and elevator.isHandled = ?1 and elevator.maintainerId=?2")
    List<Elevator> findFaultStatusMaintainer(Integer isHandled,String companyId);
    @Query(value = "select elevator from Elevator elevator where elevator.faultStatus = 20 and elevator.isHandled = ?1 and elevator.userCompanyId=?2")
    List<Elevator> findFaultStatusUser(Integer isHandled,String companyId);
    @Query(value = "select elevator from Elevator elevator where elevator.faultStatus = 20 and elevator.isHandled = ?1 and elevator.manufacturerId=?2")
    List<Elevator> findFaultStatusManufacturer(Integer isHandled,String companyId);
    @Query(value = "select elevator from Elevator elevator where elevator.faultStatus = 20 and elevator.isHandled = ?1 and elevator.ownerCompanyId=?2")
    List<Elevator> findFaultStatusOwner(Integer isHandled,String companyId);
    @Query(value = "select elevator from Elevator elevator where elevator.faultStatus = 20 and elevator.isHandled = ?1 and elevator.regulatorId=?2")
    List<Elevator> findFaultStatusRegulator(Integer isHandled,String companyId);
    @Query(value = "select elevator from Elevator elevator where elevator.faultStatus = 20 and elevator.isHandled = ?1 and elevator.personalId=?2")
    List<Elevator> findFaultStatusPersonal(Integer isHandled,String companyId);
    @Query(value = "select elevator from Elevator elevator where elevator.faultStatus = 20 and elevator.isHandled = ?1 and elevator.othersId=?2")
    List<Elevator> findFaultStatusOthers(Integer isHandled,String companyId);




    List<Elevator> findByProvinceIdAndStatus(String provinceId, int status);

    List<Elevator> findByProvinceIdAndMaintenanceStatus(String provinceId, int status);

    List<Elevator> findByProvinceIdAndFaultStatus(String provinceId, int status);

    List<Elevator> findByProvinceId(String provinceId);

    Elevator findByIntelHardwareNumber(String intelHardwareNumber);
    Elevator findByMaintainerIdAndNumber(String maintainerId,String number );

    @Query("select elevator from Elevator elevator join elevator.users users where users.id = ?1")
    Page findByUserId(String id, Pageable pageable);

    @Query("select elevator.id from Elevator elevator join elevator.users users where users.id = ?1")
    List<String> findElevatorIds(String userId);


    @Query(value = "select id from elevator where install_company_id = ?1",nativeQuery = true)
    List<String> findElevatorIdsByCompanyIdInstall(String companyId);
    @Query(value = "select id from elevator   where maintainer_id = ?1",nativeQuery = true)
    List<String> findElevatorIdsByCompanyIdMaintainer(String companyId);
    @Query(value = "select id from elevator  where user_company_id = ?1",nativeQuery = true)
    List<String> findElevatorIdsByCompanyIdUser(String companyId);
    @Query(value = "select id from elevator   where manufacturer_id = ?1",nativeQuery = true)
    List<String> findElevatorIdsByCompanyIdManufacturer(String companyId);
    @Query(value = "select id from elevator   where owner_company_id = ?1",nativeQuery = true)
    List<String> findElevatorIdsByCompanyIdOwner(String companyId);
    @Query(value = "select id from elevator where regulator_id = ?1",nativeQuery = true)
    List<String> findElevatorIdsByCompanyIdRegulator(String companyId);
    @Query(value = "select id from elevator where personal_id = ?1",nativeQuery = true)
    List<String> findElevatorIdsByCompanyIdPersonal(String companyId);
    @Query(value = "select id from elevator where others_id = ?1",nativeQuery = true)
    List<String> findElevatorIdsByCompanyIdOthers(String companyId);


    @Query(value = "select elevator.id from Elevator elevator join elevator.users user  where user.id= ?1 and elevator.userCompanyId = ?2 ")
    List<String> findElevatorIdsByFavCompanyIdInstall(String userId,String companyId);

    @Query(value = "select elevator.id from Elevator elevator join elevator.users user where user.id= ?1 and elevator.maintainerId = ?2 ")
    List<String> findElevatorIdsByFavCompanyIdMaintainer(String userId,String companyId);

    @Query(value = "select elevator.id from Elevator elevator join elevator.users user where user.id= ?1 and elevator.userCompanyId = ?2 ")
    List<String> findElevatorIdsByFavCompanyIdUser(String userId,String companyId);

    @Query(value = "select elevator.id from Elevator elevator join elevator.users user where user.id= ?1 and elevator.manufacturerId = ?2 ")
    List<String> findElevatorIdsByFavCompanyIdManufacturer(String userId,String companyId);

    @Query(value = "select elevator.id from Elevator elevator join elevator.users user where user.id= ?1 and elevator.ownerCompanyId = ?2 ")
    List<String> findElevatorIdsByFavCompanyIdOwer(String userId,String companyId);

    @Query(value = "select elevator.id from Elevator elevator join elevator.users user where user.id= ?1 and elevator.regulatorId = ?2 ")
    List<String> findElevatorIdsByFavCompanyIdRegulator(String userId,String companyId);

    @Query(value = "select elevator.id from Elevator elevator join elevator.users user where user.id= ?1 and elevator.personalId = ?2 ")
    List<String> findElevatorIdsByFavCompanyIdPersonal(String userId,String companyId);

    @Query(value = "select elevator.id from Elevator elevator join elevator.users user where user.id= ?1 and elevator.othersId = ?2 ")
    List<String> findElevatorIdsByFavCompanyIdOthers(String userId,String companyId);



    @Query(value = "select count(elevator.id)  from Elevator elevator where elevator.status= ?1 and elevator.userCompanyId = ?2 ")
    long countByStatusUser(int status,String companyId);

    @Query(value = "select count(elevator.id)  from Elevator elevator where elevator.maintenanceStatus= ?1 and elevator.userCompanyId = ?2 ")
    long countByMaintenanceStatusUser(int status,String companyId);

    @Query(value = "select count(elevator.id)  from Elevator elevator where elevator.faultStatus= ?1 and elevator.userCompanyId = ?2 ")
    long countByFaultStatusUser(int status,String companyId);

    @Query(value = "select count(elevator.id)  from Elevator elevator where elevator.userCompanyId = ?1 ")
    long countByAllStatusUser(String companyId);


    long countByStatus(int status);

    long countByMaintenanceStatus(int status);

    long countByFaultStatus(int status);

    long countByPremise(Premise premise);

//    @Query("select elevator from Elevator elevator  where elevator.upkeepContract.id = ?1")
//    List<Elevator> selectUpKeepContractId(String upKeepContractId);

//    int countByUpkeepContract(UpkeepContract upkeepContract);

    /**
     * 按控制器类型分组
     */
    @Query(value = "select controller_type,count(controller_type) from elevator group by controller_type order by controller_type", nativeQuery = true)
    List<Object[]> findGroupControllerType();

    @Query("select elevator from Elevator elevator where elevator.number like ?1")
    List<Elevator> selectElevatorByNumber(String number);

    @Query("select e.id,e.number,e.alias,e.lng,e.lat from Elevator e")
    List<String[]> findPosition();

    @Query(value = "select  number from  elevator where elevator.number like ?1 order by number",nativeQuery = true)
    List<String> findNumbers(String number);
    @Query(value = "select distinct address from  elevator where elevator.address like ?1 order by address",nativeQuery = true)
    List<String> findAddress(String address);
    @Query(value = "select distinct project_name from  elevator where elevator.project_name like ?1 order by project_name",nativeQuery = true)
    List<String> findProjectNames(String project_name);

    /*
     * 微信端电梯数据接口
     */
    @Query(value = "select elevator from Elevator elevator where abs(elevator.lng-?2)<1 and abs(elevator.lat-?1) <1")
    Page<Elevator> findBylatlng(double lat,double lng,Pageable pageable);
}