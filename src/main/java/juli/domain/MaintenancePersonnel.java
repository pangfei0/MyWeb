package juli.domain;


import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class MaintenancePersonnel extends BaseEntity {

    /**
     * 工号
     */
    @Column(nullable = false, unique = true)
    private String number;
    /**
     * 密码
     */
    @Column
    private String password;
    @Column
    private String name;

    @Column
    private String manager;//维保负责人

    @Column
    private String parentId;//维保负责人id

    @Column
    private String active;//是否激活
    @Column
    private String uid;//认证服务器账号id
    @Column
    private String telephone;
    @Column
    private String region;//维保区域
    @Column
    private String station;//维保站

    /**
     * 维保人员上传头像
     */
    @Column
    private String picture;

    /**
     * 所属维保单位
     */
    @OneToOne
    private Company maintainer;

    @Column
    private Double lng;

    @Column
    private Double lat;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<RepairLevel> levelList=new HashSet<>();
    /**
     * 当前状态(10:上班，20：待命，30：离线)
     * @see juli.domain.enums.PersonnelStatus
     */
    @Column
    private Integer currentState;

    /**
     * 当前工单
     */
    @OneToOne(fetch = FetchType.LAZY)
    private WorkBills currentBill;

    /**
     * 当前登录信息
     */
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private MaintenanceLoginRecord maintenanceLoginRecord;

    /**
     * 唯一手机客户端标识
     */
    @Column
    private String uuid;
    /**
     * 设备类型 IOS 1 Android 2
     */
    @Column
    private Integer deviceType;
    /**
     * 百度推送角标
     */
    @Column(columnDefinition="int default 0",nullable=false)
    private Integer badgeNumber;
    /**
     * 员工注册天数
     */
    @Transient
    private long days;
    @Transient
    private String levels;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
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

    public String getLevels() {
        String str="";
        for(RepairLevel repairLevel:this.getLevelList())
        str=str+repairLevel.getId()+",";
        return str;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public void setLevels(String levels) {
        this.levels = levels;
    }

    public WorkBills getCurrentBill() {
        return currentBill;
    }

    public void setCurrentBill(WorkBills currentBill) {
        this.currentBill = currentBill;
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

    public Company getMaintainer() {
        return maintainer;
    }

    public void setMaintainer(Company maintainer) {
        this.maintainer = maintainer;
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
    @Column(columnDefinition="int default 1",nullable=false)
    public Integer getBadgeNumber() {
        return badgeNumber;
    }

    public void setBadgeNumber(Integer badgeNumber) {
        this.badgeNumber = badgeNumber;
    }



    public Set<RepairLevel> getLevelList() {
        return levelList;
    }

    public void setLevelList(Set<RepairLevel> levelList) {
        this.levelList = levelList;
    }

    public Integer getCurrentState() {
        return currentState;
    }

    public void setCurrentState(Integer currentState) {
        this.currentState = currentState;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MaintenanceLoginRecord getMaintenanceLoginRecord() {
        return maintenanceLoginRecord;
    }

    public void setMaintenanceLoginRecord(MaintenanceLoginRecord maintenanceLoginRecord) {
        this.maintenanceLoginRecord = maintenanceLoginRecord;
    }

    public long getDays() {
        DateTime dt=new DateTime();
        DateTime cd=this.getCreatedDate();
        if(cd==null)
            return 1;
        long day= (dt.getMillis()-cd.getMillis())/(1000*3600*24);
        return day;
    }

    public void setDays(long days) {
        this.days = days;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }
}
