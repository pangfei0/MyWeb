package juli.api.dto;

import juli.domain.*;

import java.util.Date;

/**
 * Created by pf on 2016/3/24.
 */
public class ReportDto extends BaseDto<Reports,ReportDto> {

    private String reportNumber;

    private String elevatorId;
    private String elevatorNumber;

    private Integer reportStatus;

    private Integer reportCategory;

    private Date startTime;

    private  Date completeTime;

    private  String description;

    private  String faultQuality;

    private String result;

    private Integer serviceAttitude;

    private  Integer serviceLevel;

    private Integer envAndSafe;

    private  Integer resolveQuestion;

    private  String suggestions;

    private String sign;

    private Date reportTime;

    private  Date enterTime;

    private String maintenancePersonnelId;
    private String maintenanceName;

    private String companyId;
    private String companyName;

    private String afterBillId;
    private String billNumber;

    private String reportTimeStr;

    public String getReportTimeStr() {
        return reportTimeStr;
    }

    public void setReportTimeStr(String reportTimeStr) {
        this.reportTimeStr = reportTimeStr;
    }

    public String getReportNumber() {
        return reportNumber;
    }

    public void setReportNumber(String reportNumber) {
        this.reportNumber = reportNumber;
    }

    public String getElevatorId() {
        return elevatorId;
    }

    public void setElevatorId(String elevatorId) {
        this.elevatorId = elevatorId;
    }

    public Integer getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(Integer reportStatus) {
        this.reportStatus = reportStatus;
    }

    public Integer getReportCategory() {
        return reportCategory;
    }

    public void setReportCategory(Integer reportCategory) {
        this.reportCategory = reportCategory;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFaultQuality() {
        return faultQuality;
    }

    public void setFaultQuality(String faultQuality) {
        this.faultQuality = faultQuality;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getServiceAttitude() {
        return serviceAttitude;
    }

    public void setServiceAttitude(Integer serviceAttitude) {
        this.serviceAttitude = serviceAttitude;
    }

    public Integer getServiceLevel() {
        return serviceLevel;
    }

    public void setServiceLevel(Integer serviceLevel) {
        this.serviceLevel = serviceLevel;
    }

    public Integer getEnvAndSafe() {
        return envAndSafe;
    }

    public void setEnvAndSafe(Integer envAndSafe) {
        this.envAndSafe = envAndSafe;
    }

    public Integer getResolveQuestion() {
        return resolveQuestion;
    }

    public void setResolveQuestion(Integer resolveQuestion) {
        this.resolveQuestion = resolveQuestion;
    }

    public String getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(String suggestions) {
        this.suggestions = suggestions;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public Date getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(Date enterTime) {
        this.enterTime = enterTime;
    }

    public String getMaintenancePersonnelId() {
        return maintenancePersonnelId;
    }

    public void setMaintenancePersonnelId(String maintenancePersonnelId) {
        this.maintenancePersonnelId = maintenancePersonnelId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getAfterBillId() {
        return afterBillId;
    }

    public void setAfterBillId(String afterBillId) {
        this.afterBillId = afterBillId;
    }

    public String getMaintenanceName() {
        return maintenanceName;
    }

    public void setMaintenanceName(String maintenanceName) {
        this.maintenanceName = maintenanceName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getElevatorNumber() {
        return elevatorNumber;
    }

    public void setElevatorNumber(String elevatorNumber) {
        this.elevatorNumber = elevatorNumber;
    }
}
