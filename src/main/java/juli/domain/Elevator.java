package juli.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import juli.domain.contract.UpkeepContract;
import juli.infrastructure.DateTime.CustomDateSerializer;
import juli.infrastructure.DateTime.CustomDateTimeSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Elevator extends BaseEntity {
    /**
     * 电梯工号、设备号
     */
    @Column(nullable = false)
    private String number;

    /**
     * 电梯所在的省份
     */
    @Column
    private String provinceId;

    /**
     * 电梯所在的城市
     */
    @Column
    private String cityId;

    /**
     * 电梯所在的区，例如工业园区
     */
    @Column
    private String regionId;

    /**
     * 详细地址
     */
    @Column
    private String address;

    /**
     * 所在楼盘
     */
    @Column
    private Premise premise;

    /**
     * 项目名称（具体楼盘地址）
     */
    @Column
    private String alias;

    /**
     * 注册代码（质监局下发）
     */
    @Column
    private String regCode;

    /**
     * 此电梯在DataServer平台的ID
     * 同步电梯数据的时候需要用到
     */
    @Column
    private String dataServerReferenceId;

    /**
     * 电梯品牌（巨立、通力等）
     */
    @Column
    private String brandId;

    @Column
    private String protocolType;

    /**
     * 控制器类型（NICE 3000+等）
     */
    @Column
    private String controllerType;
    /**
     * 电梯类型：直梯，扶梯等
     */
    @Column
    private String elevatorType;

    /**
     * 地址码
     */
    @Column
    private String tdSerial;

    /**
     * 生产日期即出厂日期汇川同步
     */
//    @Column(columnDefinition = "DATETIME")
//    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = CustomDateSerializer.class)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date productionTime;

    /**
     * 交付时间
     */
//    @Column(columnDefinition = "DATETIME")
//    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = CustomDateSerializer.class)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date deliverTime;

    /**
     * 控制方式
     */
    @Column
    private String controlMode;

    /**
     * 提升高度
     */
    @Column
    private String hoistingHeight;

    /**
     * 额定载重
     */
    @Column
    private String ratedWeight;

    /**
     * 额定速度
     */
    @Column
    private String ratedSpeed;



    /**
     * 经度
     */
    @Column
    private Double lng;

    /**
     * 纬度
     */
    @Column
    private Double lat;

    /**
     * 安装单位(本系统)
     */
    @Column
    private String installCompanyId;
    /**
     * 使用单位(本系统)
     */
    @Column
    private String userCompanyId;
    /**
     * 物业单位(本系统)即使用单位
     */
    @Column
    private String ownerCompanyId;
    /**
     * 制造单位(本系统)
     */
    @Column
    private String manufacturerId;
    /**
     * 维保单位(本系统)
     */
    @Column
    private String maintainerId;
    /**
     * 监管机构(本系统)
     */
    @Column
    private String regulatorId;
    /**
     * 个人用户(本系统)
     */
    @Column
    private String personalId;
    /**
     * 其他单位类型(本系统)
     */
    @Column
    private String othersId;
    /**
     * 电梯在线状态
     *
     * @see juli.domain.enums.ElevatorStatus
     */
    @Column
    private Integer status;

    /**
     * 电梯检修状态
     *
     * @see juli.domain.enums.ElevatorMaintennanceStatus
     */
    @Column
    private Integer maintenanceStatus = 10;

    /**
     * 故障状态
     *
     * @see juli.domain.enums.ElevatorFaultStatus
     */
    @Column
    private Integer faultStatus = 10;

    /**
     * 故障发生时间
     */
    @Column(columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date faultTime;

    @Column
    private String faultCode;

    /**
     * 故障是否处理
     *
     * @see juli.domain.enums.FaultHandledStatus
     */
    @Column
    private Integer isHandled;

    @Column
    private Float temp;

    @Column
    private Float humidity;

    @Column
    private Boolean existsPeople;

    @Column
    private Float speed;

    /**
     * 当前停靠楼层
     */
    @Column
    private Integer floor;

    /**
     * 当前运行方向：上行(1)、下行(0)
     */
    @Column
    private Boolean upGoing;

    /**
     * 当前开关门状态；开门(1)、关门(0)
     */
    @Column
    private Boolean doorOpen;

    /**
     * 信号强度
     */
    @Column
    private Integer signalIntensity;
    /**
     * 关注
     */
    @Column
    private Integer favorite;

    /**
     * 电梯型号：3100、3200等
     */
    @Column
    private String elevatorModel;

    /**
     * 使用场合：待定等（汇川同步）
     * 将楼盘类型(使用场合)转换成文字
     0.待定
     1.办公综合楼宇
     2.工厂企业楼宇
     3.机关单位楼宇
     4.政府部门楼宇
     5.住宅小区楼宇
     6.商住综合楼宇
     10.重点宾馆酒店
     11.重点公共场馆
     12.重点场所车站
     13.重点场所机场
     14.重点场所商场
     15.重点学院校园
     16.重点场所医院
     17.重点娱乐场所
     99.其他场所楼宇
     */


    @Column
    private Integer usageType;



    /**
     * 24小时值班电话（汇川同步）
     */
    @Column
    private String dutyPhone;

    /**
     * 上次年检日期
     */
    @Column(columnDefinition = "DATE")
    @JsonSerialize(using = CustomDateSerializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date lastCheckDate;

    /**
     * 上次维保时间（汇川同步）
     */
    @Column(columnDefinition = "DATE")
    @JsonSerialize(using = CustomDateSerializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date lastUpkeepTime;
    /**
     * 层、站、门
     */
    @Column
    private String station;

    /**
     * 维保人员（本系统）
     */
    @Column
    private String maintenanceId;
    /**
     * 当前电梯的保养合同
     */
//    @OneToOne(fetch = FetchType.LAZY)
//    private UpkeepContract upkeepContract;

    /**
     * 当前电梯下的合同
     */
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "elevator")
    private List<UpkeepContract> upkeepContracts;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();

    /**
     * 智能硬件注册码
     */
    @Column
    private String intelHardwareNumber;

    /**
     * 项目名称（汇川同步）
     */
    @Column
    private String projectName;

    /**
     * 设备编号
     */
    @Column
    private String equipmentNumber;

    /**
     * 0.未連接只能設備1.已連接智能設備2.多品牌電梯
     */
    @Column
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEquipmentNumber() {
        return equipmentNumber;
    }

    public void setEquipmentNumber(String equipmentNumber) {
        this.equipmentNumber = equipmentNumber;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(String protocolType) {
        this.protocolType = protocolType;
    }

    /**
     * 采集器 对应智能硬件注册码
     */
//    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "intelHardwareNumber", referencedColumnName = "intelHardwareNumber")
//    private Collector collector;

    public void setElevatorModel(String elevatorModel) {
        this.elevatorModel = elevatorModel;
    }

    public String getElevatorModel() {
        return elevatorModel;
    }

    public String getDataServerReferenceId() {
        return dataServerReferenceId;
    }

    public void setDataServerReferenceId(String dataServerReferenceId) {
        this.dataServerReferenceId = dataServerReferenceId;
    }

    public String getElevatorType() {
        return elevatorType;
    }
    public void setElevatorType(String elevatorType) {
        this.elevatorType = elevatorType;
    }

    public Integer getUsageType() {
        return usageType;
    }
    public void setUsageType(Integer usageType) {
        this.usageType=usageType;
    }

    public String getDutyPhone() {
        return dutyPhone;
    }

    public void setDutyPhone(String dutyPhone) {
        this.dutyPhone=dutyPhone;
    }
    public Date getLastUpkeepTime() {
        return lastUpkeepTime;
    }

    public void setLastUpkeepTime(Date lastUpkeepTime) {
        this.lastUpkeepTime = lastUpkeepTime;
    }


    public Integer getFaultStatus() {
        return faultStatus;
    }

    public void setFaultStatus(Integer faultStatus) {
        this.faultStatus = faultStatus;
    }

    public String getRegionId() {
        return regionId;
    }

    public String getFaultCode() {
        return faultCode;
    }

    public void setFaultCode(String faultCode) {
        this.faultCode = faultCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Premise getPremise() {
        return premise;
    }

    public void setPremise(Premise premise) {
        this.premise = premise;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }


    public String getIntelHardwareNumber() {
        return intelHardwareNumber;
    }

    public void setIntelHardwareNumber(String intelHardwareNumber) {
        this.intelHardwareNumber = intelHardwareNumber;
    }

//    public Collector getCollector() {
//        return collector;
//    }
//
//    public void setCollector(Collector collector) {
//        this.collector = collector;
//    }

    public String getRegCode() {
        return regCode;
    }

    public void setRegCode(String regCode) {
        this.regCode = regCode;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getControllerType() {
        return controllerType;
    }

    public void setControllerType(String controllerType) {
        this.controllerType = controllerType;
    }

    public Date getProductionTime() {
        return productionTime;
    }

    public void setProductionTime(Date productionTime) {
        this.productionTime = productionTime;
    }

    public Date getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(Date deliverTime) {
        this.deliverTime = deliverTime;
    }

    public String getControlMode() {
        return controlMode;
    }

    public void setControlMode(String controlMode) {
        this.controlMode = controlMode;
    }

    public String getHoistingHeight() {
        return hoistingHeight;
    }

    public void setHoistingHeight(String hoistingHeight) {
        this.hoistingHeight = hoistingHeight;
    }

    public String getRatedWeight() {
        return ratedWeight;
    }

    public void setRatedWeight(String ratedWeight) {
        this.ratedWeight = ratedWeight;
    }

    public String getRatedSpeed() {
        return ratedSpeed;
    }

    public void setRatedSpeed(String ratedSpeed) {
        this.ratedSpeed = ratedSpeed;
    }

    public Date getLastCheckDate() {
        return lastCheckDate;
    }

    public void setLastCheckDate(Date lastCheckDate) {
        this.lastCheckDate = lastCheckDate;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getFaultTime() {
        return faultTime;
    }

    public void setFaultTime(Date faultTime) {
        this.faultTime = faultTime;
    }

    public Integer getIsHandled() {
        return isHandled;
    }

    public void setIsHandled(Integer isHandled) {
        this.isHandled = isHandled;
    }

    public Float getTemp() {
        return temp;
    }

    public void setTemp(Float temp) {
        this.temp = temp;
    }

    public Float getHumidity() {
        return humidity;
    }

    public void setHumidity(Float humidity) {
        this.humidity = humidity;
    }

    public Boolean getExistsPeople() {
        return existsPeople;
    }

    public void setExistsPeople(Boolean existsPeople) {
        this.existsPeople = existsPeople;
    }

    public Float getSpeed() {
        return speed;
    }

    public void setSpeed(Float speed) {
        this.speed = speed;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Boolean getUpGoing() {
        return upGoing;
    }

    public void setUpGoing(Boolean upGoing) {
        this.upGoing = upGoing;
    }

    public Boolean getDoorOpen() {
        return doorOpen;
    }

    public void setDoorOpen(Boolean doorOpen) {
        this.doorOpen = doorOpen;
    }

    public Integer getSignalIntensity() {
        return signalIntensity;
    }

    public void setSignalIntensity(Integer signalIntensity) {
        this.signalIntensity = signalIntensity;
    }

    public Integer getFavorite() {
        return favorite;
    }

    public void setFavorite(Integer favorite) {
        this.favorite = favorite;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public int hashCode() {
        return this.getId().length();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Elevator)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        return this.getId().equals(((Elevator) obj).getId());
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getTdSerial() {
        return tdSerial;
    }

    public void setTdSerial(String tdSerial) {
        this.tdSerial = tdSerial;
    }

    public List<UpkeepContract> getUpkeepContracts() {
        return upkeepContracts;
    }

    public void setUpkeepContracts(List<UpkeepContract> upkeepContracts) {
        this.upkeepContracts = upkeepContracts;
    }

    public Integer getMaintenanceStatus() {
        return maintenanceStatus;
    }

    public void setMaintenanceStatus(Integer maintenanceStatus) {
        this.maintenanceStatus = maintenanceStatus;
    }

    public String getInstallCompanyId() {
        return installCompanyId;
    }

    public void setInstallCompanyId(String installCompanyId) {
        this.installCompanyId = installCompanyId;
    }

    public String getUserCompanyId() {
        return userCompanyId;
    }

    public void setUserCompanyId(String userCompanyId) {
        this.userCompanyId = userCompanyId;
    }

    public String getOwnerCompanyId() {
        return ownerCompanyId;
    }

    public void setOwnerCompanyId(String ownerCompanyId) {
        this.ownerCompanyId = ownerCompanyId;
    }

    public String getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(String manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getMaintainerId() {
        return maintainerId;
    }

    public void setMaintainerId(String maintainerId) {
        this.maintainerId = maintainerId;
    }

    public String getRegulatorId() {
        return regulatorId;
    }

    public void setRegulatorId(String regulatorId) {
        this.regulatorId = regulatorId;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public String getOthersId() {
        return othersId;
    }

    public void setOthersId(String othersId) {
        this.othersId = othersId;
    }


    public String getMaintenanceId() {
        return maintenanceId;
    }

    public void setMaintenanceId(String maintenanceId) {
        this.maintenanceId = maintenanceId;
    }
}

