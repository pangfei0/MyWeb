package juli.api.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class MaintenancePlanDto{

    private String id;

    /**
     * 合同id
     */
    private String upkeepContractId;

    /**
     * 合同编号
     */
    private String upkeepContractNumber;

    /**
     * 电梯id
     */
    private String elevatorId;

    /**
     * 电梯编号
     */
    private String number;

    /**
     * 工单id
     */
    private String workBillId;

    /**
     * 工单编号
     */
    private String workBillNumber;

    /**
     * 批次id
     */

    private String planBathId;
    /**
     * 维保人
     */
    private String maintenanceMan;

    /**
     * 维保人id
     */
    private String maintenanceManId;

//    /**
//     *流转时间
//     */
//    @Column
//    private Date circulationTime;

    /**
     *计划状态（10：生产计划还未产生工单  20：生成计划并产生工单 30：计划顺利完成【工单状态为已完成】）
     */
    private Integer status;

    /**
     *计划开始时间（计划时间）
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date planTime;

    /**
     *计划结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date planEndTime;


    /**
     *实际完成时间
     */
    private String actualTime;

    /**
     *生成工单时间(定时器不出错，应该是计划的前一天生成)
     */
    private Date createBillTime;

    /**
     *流转时间
     */
    private String circulationTime;

    private Integer planType;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getCreateBillTime() {
        return createBillTime;
    }

    public void setCreateBillTime(Date createBillTime) {
        this.createBillTime = createBillTime;
    }

    public String getCirculationTime() {
        return circulationTime;
    }

    public void setCirculationTime(String circulationTime) {
        this.circulationTime = circulationTime;
    }

    public String getWorkBillNumber() {
        return workBillNumber;
    }

    public void setWorkBillNumber(String workBillNumber) {
        this.workBillNumber = workBillNumber;
    }

    public Date getPlanTime() {
        return planTime;
    }

    public void setPlanTime(Date planTime) {
        this.planTime = planTime;
    }

    public Date getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(Date planEndTime) {
        this.planEndTime = planEndTime;
    }

    public String getActualTime() {
        return actualTime;
    }

    public void setActualTime(String actualTime) {
        this.actualTime = actualTime;
    }

    public String getMaintenanceManId() {
        return maintenanceManId;
    }

    public void setMaintenanceManId(String maintenanceManId) {
        this.maintenanceManId = maintenanceManId;
    }
}
