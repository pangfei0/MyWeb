package juli.api.dto;


import juli.domain.WorkBills;

import java.util.Date;
public class WorkBillDto extends BaseDto<WorkBills,WorkBillDto> {
    private String billNumber;
    private String elevatorId;
    private String elevatorNumber;
    private Integer billModel;
    private Integer billstatus;
    private Integer billCategory;
    private String faultDescription;
    private String faultPerson;
    private String faultPersonTelephone;
    private String serviceIn;
    private String telephone;
    private Date startTime;
    private String startTimeStr;
    private String localDescription;
    private String takeSteps;
    private String faultQuality;
    private String result;
    private String maintenanceId;
    private String maintenanceName;
    private Date enterTime;
    private String refuseReason;
    private Date completeTime;
    private String maintainName;
    private Integer maintainProperty;
    private String maintainPrograms;
    private String picture1;
    private String picture2;
    private String picture3;
    private String picture4;
    private String picture5;
    private String afterBillId;
    private String alias;
    private String afBName;
    private String assists;

    public String getAssists() {
        return assists;
    }

    public void setAssists(String assists) {
        this.assists = assists;
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getElevatorId() {
        return elevatorId;
    }

    public void setElevatorId(String elevatorId) {
        this.elevatorId = elevatorId;
    }

    public Integer getBillstatus() {
        return billstatus;
    }

    public void setBillstatus(Integer billstatus) {
        this.billstatus = billstatus;
    }

    public Integer getBillCategory() {
        return billCategory;
    }

    public void setBillCategory(Integer billCategory) {
        this.billCategory = billCategory;
    }

    public String getFaultDescription() {
        return faultDescription;
    }

    public void setFaultDescription(String faultDescription) {
        this.faultDescription = faultDescription;
    }

    public String getFaultPerson() {
        return faultPerson;
    }

    public void setFaultPerson(String faultPerson) {
        this.faultPerson = faultPerson;
    }

    public String getFaultPersonTelephone() {
        return faultPersonTelephone;
    }

    public void setFaultPersonTelephone(String faultPersonTelephone) {
        this.faultPersonTelephone = faultPersonTelephone;
    }

    public String getServiceIn() {
        return serviceIn;
    }

    public void setServiceIn(String serviceIn) {
        this.serviceIn = serviceIn;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getLocalDescription() {
        return localDescription;
    }

    public void setLocalDescription(String localDescription) {
        this.localDescription = localDescription;
    }

    public String getTakeSteps() {
        return takeSteps;
    }

    public void setTakeSteps(String takeSteps) {
        this.takeSteps = takeSteps;
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

    public String getMaintenanceId() {
        return maintenanceId;
    }

    public void setMaintenanceId(String maintenanceId) {
        this.maintenanceId = maintenanceId;
    }

    public Date getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(Date enterTime) {
        this.enterTime = enterTime;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public String getMaintainName() {
        return maintainName;
    }

    public void setMaintainName(String maintainName) {
        this.maintainName = maintainName;
    }

    public Integer getMaintainProperty() {
        return maintainProperty;
    }

    public void setMaintainProperty(Integer maintainProperty) {
        this.maintainProperty = maintainProperty;
    }

    public String getMaintainPrograms() {
        return maintainPrograms;
    }

    public void setMaintainPrograms(String maintainPrograms) {
        this.maintainPrograms = maintainPrograms;
    }

    public String getPicture1() {
        return picture1;
    }

    public void setPicture1(String picture1) {
        this.picture1 = picture1;
    }

    public String getPicture2() {
        return picture2;
    }

    public void setPicture2(String picture2) {
        this.picture2 = picture2;
    }

    public String getPicture3() {
        return picture3;
    }

    public void setPicture3(String picture3) {
        this.picture3 = picture3;
    }

    public String getPicture4() {
        return picture4;
    }

    public void setPicture4(String picture4) {
        this.picture4 = picture4;
    }

    public String getPicture5() {
        return picture5;
    }

    public void setPicture5(String picture5) {
        this.picture5 = picture5;
    }

    public String getAfterBillId() {
        return afterBillId;
    }

    public void setAfterBillId(String afterBillId) {
        this.afterBillId = afterBillId;
    }

    public Integer getBillModel() {
        return billModel;
    }

    public void setBillModel(Integer billModel) {
        this.billModel = billModel;
    }

    public String getElevatorNumber() {
        return elevatorNumber;
    }

    public void setElevatorNumber(String elevatorNumber) {
        this.elevatorNumber = elevatorNumber;
    }

    public String getMaintenanceName() {
        return maintenanceName;
    }

    public void setMaintenanceName(String maintenanceName) {
        this.maintenanceName = maintenanceName;
    }

    public String getAfBName() {
        return afBName;
    }

    public void setAfBName(String afBName) {
        this.afBName = afBName;
    }
}
