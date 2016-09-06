package juli.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

/**
 *  create by zj
 */

@Entity
public class MaintenancePlanBath extends BaseEntity{


    /**
     * 合同id
     */
    @Column
    private String upkeepContractId;

    /**
     * 合同编号
     */
    @Column
    private String upkeepContractNumber;

    /**
     * 批次里的电梯
     */
    @Column
    private String elevatorNumber;



    /**
     * 维保人id
     */
    @Column
    private String maintenanceManId;

    /**
     * 维保人
     */
    @Column
    private String maintenanceMan;


    /**
     *计划状态（10：生产计划还未产生工单  20：生成计划并产生工单 30：计划顺利完成【工单状态为已完成】）
     */
    @Column
    private Integer status;//预留

    /**
     *开始时间
     */
    @Column
    private Date startTime;

    /**
     *结束时间
     */
    @Column
    private Date endTime;

    public String getUpkeepContractId() {
        return upkeepContractId;
    }

    public void setUpkeepContractId(String upkeepContractId) {
        this.upkeepContractId = upkeepContractId;
    }

    public String getUpkeepContractNumber() {
        return upkeepContractNumber;
    }

    public void setUpkeepContractNumber(String upkeepContractNumber) {
        this.upkeepContractNumber = upkeepContractNumber;
    }

    public String getElevatorNumber() {
        return elevatorNumber;
    }

    public void setElevatorNumber(String elevatorNumber) {
        this.elevatorNumber = elevatorNumber;
    }

    public String getMaintenanceManId() {
        return maintenanceManId;
    }

    public void setMaintenanceManId(String maintenanceManId) {
        this.maintenanceManId = maintenanceManId;
    }

    public String getMaintenanceMan() {
        return maintenanceMan;
    }

    public void setMaintenanceMan(String maintenanceMan) {
        this.maintenanceMan = maintenanceMan;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
