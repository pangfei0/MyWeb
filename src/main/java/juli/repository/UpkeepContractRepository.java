package juli.repository;

import juli.domain.contract.UpkeepContract;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface UpkeepContractRepository extends PagingAndSortingRepository<UpkeepContract, String>, JpaSpecificationExecutor {

    @Query(value = "select upkeepContract.id from UpkeepContract upkeepContract join upkeepContract.partyA company where company.id=?1")
    List<String> findupkeepContractIdsByPartyAId(String companyId);

    @Query(value = "select upkeepContract.id from UpkeepContract upkeepContract join upkeepContract.partyB company where company.id=?1")
    List<String> findupkeepContractIdsByPartyBId(String companyId);

    @Query(value = "select distinct e.id from elevator e left join upkeepcontract_elevator up on e.id =up.elevator_id where up.elevator_id is null and  e.project_name =?1 ",nativeQuery = true)
    List<String> getElevatorIdsByProjectNames(String projectName);

    @Query(value = "select e.id from elevator e left join upkeepcontract_elevator up on e.id =up.elevator_id where up.elevator_id is null ",nativeQuery = true)
    List<String> getElevatorIds();


//    @Query(value = "select elevator.id from Elevator elevator join elevator.maintainer maintainer join elevator.userCompany user join elevator.ownerCompany owner join elevator.upkeepContract upkeepContract where ( user.id=?1 or owner.id =?1 or maintainer.id = ?2 ) and upkeepContract.id is null")
//    List<String> getElevatorIdsByProjectNames(String partyAId,String partyBId);
    @Query(value = "select distinct project_name from elevator where user_company_id=?1 or owner_company_id =?1 or maintainer_id = ?2  order by convert(project_name using gbk) asc", nativeQuery = true)
    List<String> getMaintainerProjectNames(String partyAId,String partyBId);

    @Query(value = "select distinct project_name from elevator where ( user_company_id=?1 or owner_company_id =?1 or maintainer_id = ?2 )  and project_name like ?3 order by convert(project_name using gbk) asc", nativeQuery = true)
    List<String> getMaintainerProjectNames(String partyAId,String partyBId,String projectName);

    @Query(value = "select e.id from elevator e left join upkeepcontract_elevator up on e.id =up.elevator_id where up.elevator_id is null and (e.user_company_id =?1 or e.owner_company_id=?1 or e.maintainer_id=?2) ",nativeQuery = true)
    List<String> getElevatorIdsByProjectNames(String partyAId,String partyBId);

    UpkeepContract  findById(String id);

    UpkeepContract findByNumber(String number);

    @Query(value = "select ue.upkeepcontract_id from upkeepcontract_elevator ue left join elevator e on e.id=ue.elevator_id where e.id=?1 ",nativeQuery = true)
    String getUpkeepcontractIdByElevatorId(String elevatorId);

    @Query(value = "select upkeepcontract_id from upkeepcontract_elevator  where elevator_id in(select id from elevator where number = ?1) ",nativeQuery = true)
    String getUpkeepcontractIdByElevatorNumber(String elevatorNumber);
}
