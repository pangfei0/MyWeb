package juli.api.dto;

import juli.domain.Elevator;
import juli.domain.Premise;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ElevatorDto extends BaseDto<Elevator, ElevatorDto> {
    private String number;
    private String regCode;
    private String brandId;
    private String brandName;
    private String protocolType;
    private String address;
    private Premise premise;
    private String alias;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date productionTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deliverTime;
    private String controlMode;
    private String hoistingHeight;
    private String ratedWeight;
    private String ratedSpeed;
    private String station;
    private Integer status;
    private Integer maintenanceStatus;
    private Integer faultStatus;
    private Integer isHandled;
    private String intelHardwareNumber;
    private String controllerType;
    private String elevatorType;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date lastCheckDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date lastUpkeepTime;
    private String location;
    private double lng;
    private double lat;
    private Integer favorite;
    private String maintenanceId;
    private String maintenanceManager;
    private String maintenanceName;
    private String installCompanyId;
    private String installCompanyName;
    private String maintainerName;
    private String manufacturerName;
    private String userCompanyName;
    private String ownerCompanyName;
    private String maintainerId;
    private String manufacturerId;
    private String userCompanyId;//使用单位
    private String ownerCompanyId;//物业单位
    private String personalId;
    private String personalName;
    private String regulatorId;
    private String regulatorName;
    private String othersId;
    private String othersName;
    private String upKeepContractId;
    private String usageType;

    private String propertyCompanyName;

    private String dutyPhone;
    private String managerName1;
    private String managerName2;
    private String managerName3;
    private String maintainName1;
    private String maintainName2;
    private String maintainName3;
    private String projectName;
    private String equipmentNumber;
    private String elevatorModel;
    private String elevatorNumber;


    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public String getPersonalName() {
        return personalName;
    }

    public void setPersonalName(String personalName) {
        this.personalName = personalName;
    }

    public String getRegulatorId() {
        return regulatorId;
    }

    public void setRegulatorId(String regulatorId) {
        this.regulatorId = regulatorId;
    }

    public String getRegulatorName() {
        return regulatorName;
    }

    public void setRegulatorName(String regulatorName) {
        this.regulatorName = regulatorName;
    }

    public String getOthersId() {
        return othersId;
    }

    public void setOthersId(String othersId) {
        this.othersId = othersId;
    }

    public String getOthersName() {
        return othersName;
    }

    public void setOthersName(String othersName) {
        this.othersName = othersName;
    }

    public String getElevatorModel() {
        return elevatorModel;
    }

    public void setElevatorModel(String elevatorModel) {
        this.elevatorModel = elevatorModel;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(String protocolType) {
        this.protocolType = protocolType;
    }

    public String getOwnerCompanyName() {
        return ownerCompanyName;
    }

    public void setOwnerCompanyName(String ownerCompanyName) {
        this.ownerCompanyName = ownerCompanyName;
    }

    public String getUserCompanyName() {
        return userCompanyName;
    }

    public void setUserCompanyName(String userCompanyName) {
        this.userCompanyName = userCompanyName;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getMaintainerName() {
        return maintainerName;
    }

    public void setMaintainerName(String maintainerName) {
        this.maintainerName = maintainerName;
    }

    public String getInstallCompanyName() {
        return installCompanyName;
    }

    public void setInstallCompanyName(String installCompanyName) {
        this.installCompanyName = installCompanyName;
    }

    public String getElevatorNumber() {
        return elevatorNumber;
    }

    public void setElevatorNumber(String elevatorNumber) {
        this.elevatorNumber = elevatorNumber;
    }

    public String getMaintainName1() {
        return maintainName1;
    }

    public String getEquipmentNumber() {
        return equipmentNumber;
    }

    public void setEquipmentNumber(String equipmentNumber) {
        this.equipmentNumber = equipmentNumber;
    }

    public String getMaintenanceName() {
        return maintenanceName;
    }

    public void setMaintenanceName(String maintenanceName) {
        this.maintenanceName = maintenanceName;
    }

    public String getMaintenanceManager() {
        return maintenanceManager;
    }

    public void setMaintenanceManager(String maintenanceManager) {
        this.maintenanceManager = maintenanceManager;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

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

    public String getMaintenanceId() {
        return maintenanceId;
    }

    public void setMaintenanceId(String maintenanceId) {
        this.maintenanceId = maintenanceId;
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

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getMaintenanceStatus() {
        return maintenanceStatus;
    }

    public void setMaintenanceStatus(Integer maintenanceStatus) {
        this.maintenanceStatus = maintenanceStatus;
    }

    public Integer getFaultStatus() {
        return faultStatus;
    }

    public void setFaultStatus(Integer faultStatus) {
        this.faultStatus = faultStatus;
    }

    public Integer getIsHandled() {
        return isHandled;
    }

    public void setIsHandled(Integer isHandled) {
        this.isHandled = isHandled;
    }

    public String getIntelHardwareNumber() {
        return intelHardwareNumber;
    }

    public void setIntelHardwareNumber(String intelHardwareNumber) {
        this.intelHardwareNumber = intelHardwareNumber;
    }

    public String getControllerType() {
        return controllerType;
    }

    public void setControllerType(String controllerType) {
        this.controllerType = controllerType;
    }

    public String getElevatorType() {
        return elevatorType;
    }

    public void setElevatorType(String elevatorType) {
        this.elevatorType = elevatorType;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public Integer getFavorite() {
        return favorite;
    }

    public void setFavorite(Integer favorite) {
        this.favorite = favorite;
    }

    public String getInstallCompanyId() {
        return installCompanyId;
    }

    public void setInstallCompanyId(String installCompanyId) {
        this.installCompanyId = installCompanyId;
    }

    public String getMaintainerId() {
        return maintainerId;
    }

    public void setMaintainerId(String maintainerId) {
        this.maintainerId = maintainerId;
    }

    public String getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(String manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getUserCompanyId() {
        return userCompanyId;
    }

    public void setUserCompanyId(String userCompanyId) {
        this.userCompanyId = userCompanyId;
    }
    public String getOwnerCompanyId(){return ownerCompanyId;}
    public void setOwnerCompanyId(String ownerCompanyId){this.ownerCompanyId=ownerCompanyId;}
    public String getUpKeepContractId() {
        return upKeepContractId;
    }

    public void setUpKeepContractId(String upKeepContractId) {
        this.upKeepContractId = upKeepContractId;
    }

    public Date getLastCheckDate() {
        return lastCheckDate;
    }

    public void setLastCheckDate(Date lastCheckDate) {
        this.lastCheckDate = lastCheckDate;
    }



    public void setUsageType(String usageType) {
        this.usageType=usageType;
    }
    public String getUsageType() {
        return usageType;
    }


    public void setPropertyCompanyName(String propertyCompanyName){this.propertyCompanyName=propertyCompanyName;}
    public String getPropertyCompanyName(){return propertyCompanyName;}

    public void setDutyPhone(String dutyPhone){this.dutyPhone=dutyPhone;}
    public String getDutyPhone(){return  dutyPhone;}
    public void setManagerName1(String managerName1){this.managerName1=managerName1;}
    public String getManagerName1(){return managerName1;}
    public void setManagerName2(String managerName2){this.managerName2=managerName2;}
    public String getManagerName2(){return managerName2;}
    public void setManagerName3(String managerName3){this.managerName3=managerName3;}
    public String getManagerName3(){return managerName3;}
    public void setMaintainName1(String maintainName1){this.maintainName1=maintainName1;}
    public String getMaintainName11(){return maintainName1;}
    public void setMaintainName2(String maintainName2){this.maintainName2=maintainName2;}
    public String setMaintainName2(){return maintainName2;}
    public void setMaintainName3(String maintainName3){this.maintainName3=maintainName3;}
    public String setMaintainName3(){return maintainName3;}
    public void setLastUpkeepTime(Date lastUpkeepTime){this.lastUpkeepTime=lastUpkeepTime;}
    public Date getLastUpkeepTime(){return lastUpkeepTime;}


    public static List<ElevatorDto> mapFromElevatorList(List<Elevator> elevators) {
        List<ElevatorDto> elevatorDtos = new ArrayList<>();
        for (Elevator e : elevators) {
            ElevatorDto dto = new ElevatorDto().mapFrom(e);
            elevatorDtos.add(dto);
        }

        return elevatorDtos;
    }
}
