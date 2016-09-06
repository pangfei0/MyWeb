package juli.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import juli.infrastructure.DateTime.CustomDateSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 *  create by zj
 */

@Entity
public class MaintenancePlan extends BaseEntity{


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
     * 电梯id
     */
    @Column
    private String elevatorId;

    /**
     * 电梯编号
     */
    @Column
    private String number;

    /**
     * 工单id
     */
    @Column
    private String workBillId;
    /**
     * 批次id
     */
    @Column
    private String planBathId;

    /**
     * 工单编号
     */
    private String workBillNumber;


    /**
     * 维保人
     */
    @Column
    private String maintenanceMan;

//    /**
//     *流转时间
//     */
//    @Column
//    private Date circulationTime;

    /**
     *计划状态（10：未生成工单  20：已生成工单）
     */
    @Column
    private Integer status;

    /**
     *计划开始时间（生成计划时间）
     */
    @JsonSerialize(using = CustomDateSerializer.class)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date planTime;

    /**
     *计划结束时间
     */
    @JsonSerialize(using = CustomDateSerializer.class)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date planEndTime;

    /**
     *实际完成时间
     */
    @JsonSerialize(using = CustomDateSerializer.class)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date actualTime;

    /**
     *生成工单时间(定时器不出错，应该是计划的前一天生成)
     */
    @Column
    private Date createBillTime;

    /**
     *类型
     */
    @Column
    private Integer planType;

    /**
     * 备注
     */
    @Column
    private String content;

    public String getPlanBathId() {
        return planBathId;
    }

    public void setPlanBathId(String planBathId) {
        this.planBathId = planBathId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getPlanType() {
        return planType;
    }

    public void setPlanType(Integer planType) {
        this.planType = planType;
    }

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

    public String getElevatorId() {
        return elevatorId;
    }

    public void setElevatorId(String elevatorId) {
        this.elevatorId = elevatorId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getWorkBillId() {
        return workBillId;
    }

    public void setWorkBillId(String workBillId) {
        this.workBillId = workBillId;
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

    public Date getPlanTime() {
        return planTime;
    }

    public void setPlanTime(Date planTime) {
        this.planTime = planTime;
    }

    public Date getActualTime() {
        return actualTime;
    }

    public void setActualTime(Date actualTime) {
        this.actualTime = actualTime;
    }

    public Date getCreateBillTime() {
        return createBillTime;
    }

    public void setCreateBillTime(Date createBillTime) {
        this.createBillTime = createBillTime;
    }

    public String getWorkBillNumber() {
        return workBillNumber;
    }

    public void setWorkBillNumber(String workBillNumber) {
        this.workBillNumber = workBillNumber;
    }

    public Date getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(Date planEndTime) {
        this.planEndTime = planEndTime;
    }
}
