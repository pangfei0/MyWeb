package juli.service.DataServer.contracts;

import java.util.Date;

public class DataServerElevator {
    /**
     * 项目名称（地址）
     */
    String aliasOfAddress;

    /**
     * 项目名称
     */
    Building building;

    /**
     * 具体地址
     */
    String address;

    /**
     * 区域
     */
    String area;

    /**
     * 城市
     */
    String city;

    String province;

    /**
     * 注册代码
     */
    String registerCode;

    /**
     * 电梯工号
     */
    String factoryNO;

    /**
     * 使用单位名称（汇川同步）
     */
    String customerCompanyName;

    /**
     * 设备状态：在线1,离线2
     */
    Integer deviceStatus;

    /**
     * 维保状态：正常1,检修2
     */
    Integer upkeepStatus;

    /**
     * 故障状态：正常1,故障2
     */
    Integer faultStatus;
    /**
     * 电梯型号
     */
    String elevatorModel;


    /**
     * 控制器型号
     */
    String ctrlType;

    /**
     * 地址码
     */
    String tdSerial;

    /**
     * 采集设备注册码,智能硬件注册码（汇川同步）
     */
    String regCode;

    /**
     * 电梯类型：直梯1,扶梯2
     */
    String elevatorType;

    /**
     * 楼层/层、站、门
     */
    String floor;

    /**
     * 纬度
     */
    String gpsLat;

    /**
     * 经度
     */
    String gpsLng;

    /**
     * 安装单位
     */
    String installCompanyName;


    /**
     * 24小时值班电话（汇川同步）
     */

     String dutyPhone;
    /**
     * 物业联系人一（汇川同步）
     */
    String managerName1;
    /**
     * 物业联系人二（汇川同步）
     */
    String managerName2;
    /**
     * 物业联系人三（汇川同步）
     */
    String managerName3;
    /**
     * 维保联系人一（汇川同步）
     */
    String mainTainName1;
    /**
     * 维保联系人二（汇川同步）
     */
    String mainTainName2;
    /**
     * 维保联系人三（汇川同步）
     */
    String mainTainName3;
    /**
     * 上次维保时间（汇川同步）
     */
    Date lastUpkeepTime;

    /**
     * 物业单位(汇川同步)
     */
    String propertyCompanyName;
    /**
     * 生产单位(本系统)即制造厂商名称（汇川同步）
     */
    String manufacturerName;

    /**
     * 物业单位(本系统)
     */
    String ownerCompanyName;

    /**
     * 维保单位
     */
    String upkeepCompanyName;

    /**
     * 额定载重
     */
    String loadWeight;

    /**
     * 额定速度
     */
    String speed;
    /**
     * 使用场合
     */
    Integer usageType;

    /**
     * 出厂日期(汇川同步)
     */
    Date outFactoryDate;
    /**
     * 上次年检日期
     */
    Date lastcheckDate;
   /* *//**
     * 系统运行时间 分钟 *//*
    Integer J_Unit0;

    *//**
     * 系统运行次数 *//*
    Integer J_Unit1;

    public void setJ_Unit0(Integer j_Unit0) {
        J_Unit0 = j_Unit0;
    }

    public Integer getJ_Unit0() {
        return J_Unit0;
    }

    public void setJ_Unit1(Integer j_Unit1) {
        J_Unit1 = j_Unit1;
    }

    public Integer getJ_Unit1() {
        return J_Unit1;
    }*/

    public void setLastcheckDate(Date lastcheckDate) {
        this.lastcheckDate = lastcheckDate;
    }
    public Date getLastcheckDate() {
        return lastcheckDate;
    }



    public Integer getUpkeepStatus() {
        return upkeepStatus;
    }

    public void setUpkeepStatus(Integer upkeepStatus) {
        this.upkeepStatus = upkeepStatus;
    }

    public Integer getFaultStatus() {
        return faultStatus;
    }

    public void setFaultStatus(Integer faultStatus) {
        this.faultStatus = faultStatus;
    }

    public String getLoadWeight() {
        return loadWeight;
    }

    public void setLoadWeight(String loadWeight) {
        this.loadWeight = loadWeight;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public Date getOutFactoryDate() {
        return outFactoryDate;
    }

    public void setOutFactoryDate(Date outFactoryDate) {
        this.outFactoryDate = outFactoryDate;
    }

    public String getUpkeepCompanyName() {
        return upkeepCompanyName;
    }

    public void setUpkeepCompanyName(String upkeepCompanyName) {
        this.upkeepCompanyName = upkeepCompanyName;
    }

    public String getAliasOfAddress() {
        return aliasOfAddress;
    }

    public void setAliasOfAddress(String aliasOfAddress) {
        this.aliasOfAddress = aliasOfAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getRegisterCode() {
        return registerCode;
    }

    public void setRegisterCode(String registerCode) {
        this.registerCode = registerCode;
    }

    public String getFactoryNO() {
        return factoryNO;
    }

    public void setFactoryNO(String factoryNO) {
        this.factoryNO = factoryNO;
    }

    public String getCustomerCompanyName() {
        return customerCompanyName;
    }

    public void setCustomerCompanyName(String customerCompanyName) {
        this.customerCompanyName = customerCompanyName;
    }

    public Integer getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(Integer deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getElevatorModel() {
        return elevatorModel;
    }

    public void setElevatorModel(String elevatorModel) {
        this.elevatorModel = elevatorModel;
    }

    public String getRegCode() {
        return regCode;
    }

    public void setRegCode(String regCode) {
        this.regCode = regCode;
    }

    public String getElevatorType() {
        return elevatorType;
    }

    public void setElevatorType(String elevatorType) {
        this.elevatorType = elevatorType;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getGpsLat() {
        return gpsLat;
    }

    public void setGpsLat(String gpsLat) {
        this.gpsLat = gpsLat;
    }

    public String getGpsLng() {
        return gpsLng;
    }

    public void setGpsLng(String gpsLng) {
        this.gpsLng = gpsLng;
    }

    public String getInstallCompanyName() {
        return installCompanyName;
    }

    public void setInstallCompanyName(String installCompanyName) {
        this.installCompanyName = installCompanyName;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }
    public String getPropertyCompanyName(){return propertyCompanyName;}
    public void setPropertyCompanyName(String propertyCompanyName){this.propertyCompanyName=propertyCompanyName;}

    public String getOwnerCompanyName() {
        return ownerCompanyName;
    }

    public void setOwnerCompanyName(String ownerCompanyName) {
        this.ownerCompanyName = ownerCompanyName;
    }
    public Integer getUsageType(){return usageType;}
    public void setUsageType(Integer usageType){this.usageType=usageType;}
    public String getCtrlType() {
        return ctrlType;
    }

    public void setCtrlType(String ctrlType) {
        this.ctrlType = ctrlType;
    }

    public String getTdSerial() {
        return tdSerial;
    }

    public void setTdSerial(String tdSerial) {
        this.tdSerial = tdSerial;
    }

    public void setDutyPhone(String dutyPhone) {
        this.dutyPhone = dutyPhone;
    }

    public void setLastUpkeepTime(Date lastUpkeepTime) {
        this.lastUpkeepTime = lastUpkeepTime;
    }

    public Date getLastUpkeepTime() {
        return lastUpkeepTime;
    }

    public void setMaintainName1(String mainTainName1) {
        this.mainTainName1 = mainTainName1;
    }

    public String getMaintainName1() {
        return mainTainName1;
    }

    public void setMaintainName2(String mainTainName2) {
        this.mainTainName2 = mainTainName2;
    }

    public String getMaintainName2() {
        return mainTainName2;
    }

    public void setMaintainName3(String mainTainName3) {
        this.mainTainName3 = mainTainName3;
    }

    public String getMaintainName3() {
        return mainTainName3;
    }

    public void setManagerName1(String managerName1) {
        this.managerName1 = managerName1;
    }

    public String getManagerName1() {
        return managerName1;
    }

    public void setManagerName2(String managerName2) {
        this.managerName2 = managerName2;
    }

    public String getManagerName2() {
        return managerName2;
    }

    public void setManagerName3(String managerName3) {
        this.managerName3 = managerName3;
    }

    public String getManagerName3() {
        return managerName3;
    }

    public String getDutyPhone() {
        return dutyPhone;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }
}
