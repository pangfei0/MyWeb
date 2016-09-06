package juli.api.dto;

import java.util.Date;

public class ElevatorBathpojo {

    private String elevatorStr;
    private String startTime;
    private String endTime;
    private String maintenanceManId;
    private Date start;
    private Date end;
    private Integer planType;
    private Integer days;
    private Integer daysEd;

    public Integer getDaysEd() {
        return daysEd;
    }

    public void setDaysEd(Integer daysEd) {
        this.daysEd = daysEd;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getPlanType() {
        return planType;
    }

    public void setPlanType(Integer planType) {
        this.planType = planType;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public String getElevatorStr() {
        return elevatorStr;
    }

    public void setElevatorStr(String elevatorStr) {
        this.elevatorStr = elevatorStr;
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

    public String getMaintenanceManId() {
        return maintenanceManId;
    }

    public void setMaintenanceManId(String maintenanceManId) {
        this.maintenanceManId = maintenanceManId;
    }
}
