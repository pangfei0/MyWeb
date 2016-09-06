package juli.api.dto;

public class MaintenancePlanBathDto {

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
     * 批次里的电梯
     */
    private String elevatorNumber;



    /**
     * 维保人id
     */
    private String maintenanceManId;

    /**
     * 维保人
     */
    private String maintenanceMan;


    /**
     *计划状态（10：生产计划还未产生工单  20：生成计划并产生工单 30：计划顺利完成【工单状态为已完成】）
     */
    private Integer status;//预留

    /**
     *开始时间
     */
    private String startTime;

    /**
     *结束时间
     */
    private String endTime;

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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
