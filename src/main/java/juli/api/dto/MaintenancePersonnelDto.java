package juli.api.dto;


import juli.domain.MaintenancePersonnel;
import juli.domain.RepairLevel;

import java.util.List;
import java.util.Set;

public class MaintenancePersonnelDto extends BaseDto<MaintenancePersonnel,MaintenancePersonnelDto>{

    private String number;
    private String name;
    private String manager;
    private String telephone;
    private String picture;
    private String maintainerId;
    private String maintainerName;
    private Double lng;
    private Double lat;
    private Integer currentState;
    private String active;
    private String billNumber;
    private String currentBillId;
    private String maintenanceLoginRecordId;
    private long days;
    private String parentId;
    private Set<RepairLevel> levelList;
    private String levelListId;
    private Long billAllCount;
    private Long wbCount;
    private Long byCount;
    private Long jdCount;
    private Long bnCount;
    private Long qnCount;
    private Long wxCount;
    private Long jxCount;
    private Long jjCount;
    private Long excellent;
    private Long good;
    private Long common;
    private Long poor;
    private Long noComment;
    private String region;
    private String station;
    private String satisfaction;//满意度
    private String wjxsatisfaction;//维修急修满意度
    private String wxCostTime;//花费时间
    private String jxCostTime;//花费时间
    private String wxjxCount;//维修急修总次数即故障次数

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Long getWbCount() {
        return wbCount;
    }

    public void setWbCount(Long wbCount) {
        this.wbCount = wbCount;
    }

    public String getWxjxCount() {
        return wxjxCount;
    }

    public void setWxjxCount(String wxjxCount) {
        this.wxjxCount = wxjxCount;
    }

    public String getWjxsatisfaction() {
        return wjxsatisfaction;
    }

    public void setWjxsatisfaction(String wjxsatisfaction) {
        this.wjxsatisfaction = wjxsatisfaction;
    }

    public String getWxCostTime() {
        return wxCostTime;
    }

    public void setWxCostTime(String wxCostTime) {
        this.wxCostTime = wxCostTime;
    }

    public String getJxCostTime() {
        return jxCostTime;
    }

    public void setJxCostTime(String jxCostTime) {
        this.jxCostTime = jxCostTime;
    }

    public String getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(String satisfaction) {
        this.satisfaction = satisfaction;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Long getJjCount() {
        return jjCount;
    }

    public void setJjCount(Long jjCount) {
        this.jjCount = jjCount;
    }

    public Long getNoComment() {
        return noComment;
    }

    public void setNoComment(Long noComment) {
        this.noComment = noComment;
    }

    public Long getByCount() {
        return byCount;
    }

    public void setByCount(Long byCount) {
        this.byCount = byCount;
    }

    public Long getJdCount() {
        return jdCount;
    }

    public void setJdCount(Long jdCount) {
        this.jdCount = jdCount;
    }

    public Long getBnCount() {
        return bnCount;
    }

    public void setBnCount(Long bnCount) {
        this.bnCount = bnCount;
    }

    public Long getQnCount() {
        return qnCount;
    }

    public void setQnCount(Long qnCount) {
        this.qnCount = qnCount;
    }

    public Long getWxCount() {
        return wxCount;
    }

    public void setWxCount(Long wxCount) {
        this.wxCount = wxCount;
    }

    public Long getJxCount() {
        return jxCount;
    }

    public void setJxCount(Long jxCount) {
        this.jxCount = jxCount;
    }

    public Long getExcellent() {
        return excellent;
    }

    public void setExcellent(Long excellent) {
        this.excellent = excellent;
    }

    public Long getGood() {
        return good;
    }

    public void setGood(Long good) {
        this.good = good;
    }

    public Long getCommon() {
        return common;
    }

    public void setCommon(Long common) {
        this.common = common;
    }

    public Long getPoor() {
        return poor;
    }

    public void setPoor(Long poor) {
        this.poor = poor;
    }

    public Long getBillAllCount() {
        return billAllCount;
    }

    public void setBillAllCount(Long billAllCount) {
        this.billAllCount = billAllCount;
    }

    public String getLevelListId() {
        return levelListId;
    }

    public void setLevelListId(String levelListId) {
        this.levelListId = levelListId;
    }

    public Set<RepairLevel> getLevelList() {
        return levelList;
    }

    public void setLevelList(Set<RepairLevel> levelList) {
        this.levelList = levelList;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getMaintainerName() {
        return maintainerName;
    }

    public void setMaintainerName(String maintainerName) {
        this.maintainerName = maintainerName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getMaintainerId() {
        return maintainerId;
    }

    public void setMaintainerId(String maintainerId) {
        this.maintainerId = maintainerId;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Integer getCurrentState() {
        return currentState;
    }

    public void setCurrentState(Integer currentState) {
        this.currentState = currentState;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getCurrentBillId() {
        return currentBillId;
    }

    public void setCurrentBillId(String currentBillId) {
        this.currentBillId = currentBillId;
    }

    public String getMaintenanceLoginRecordId() {
        return maintenanceLoginRecordId;
    }

    public void setMaintenanceLoginRecordId(String maintenanceLoginRecordId) {
        this.maintenanceLoginRecordId = maintenanceLoginRecordId;
    }

    public long getDays() {
        return days;
    }

    public void setDays(long days) {
        this.days = days;
    }
}
