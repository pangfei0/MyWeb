package juli.domain.fault;


import juli.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 用于传给96333
 */
@Entity
public class ElevatorFault extends BaseEntity{

    @Column
    private String registrationCode;// 注册代码  //不能为空
    @Column
    private String number;// 电梯编号            //如“09GM1214A”，不能为空
    @Column
    private String faultCode;//故障代码
    @Column
    private String faultTime;//故障发生时间       //设备的当前时间 格式"2016-07-20 14:38:33"
    @Column
    private String serviceMode;//当前服务状态     //0：停止服务1：正常运行 2：检修 3：消防返回
                                                   // 4：消防员运行 5：应急电源运行 6：地震模式 7：未知
    @Column
    private String carStatus;//轿厢运行状态       //0：停止1：运行 (轿厢实际状态，停止即待机或断电)
    @Column
    private String carDirection;//轿厢运行方向    //0：无方向1：上行2：下行 (轿厢实际运行状态，非轿厢内指示方向)
    @Column
    private String doorZone;//门区                //1：电梯在门区 0：电梯在非门区（轿厢是否在门区，仅电梯停止时有效）
    @Column
    private String carPosition;//电梯当前层楼     //电梯物理楼层位
    @Column
    private String doorStatus;//关门到位          // 1：关门到位  0：无关门到位信号
    @Column
    private String passengerStatus;//轿内是否有人  //1;有人  0：无人
    @Column
    private String runNum;//电梯运行次数
    @Column
    private String operationStatus;//扶梯运行状态  //0：停止1：运行(实际状态，停止指待机或停止运行)
    @Column
    private String operationDirection;//扶梯运行方向 //0：无方向1：上行 2：下行
    @Column
    private String statusTime;  //状态时间
    @Column
    private String isTrapp;  //是否困人  0：未困人 1：困人

    public String getIsTrapp() {
        return isTrapp;
    }

    public void setIsTrapp(String isTrapp) {
        this.isTrapp = isTrapp;
    }

    public String getRegistrationCode() {
        return registrationCode;
    }

    public void setRegistrationCode(String registrationCode) {
        this.registrationCode = registrationCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    public String getFaultCode() {
        return faultCode;
    }

    public void setFaultCode(String faultCode) {
        this.faultCode = faultCode;
    }

    public String getFaultTime() {
        return faultTime;
    }

    public void setFaultTime(String faultTime) {
        this.faultTime = faultTime;
    }

    public String getServiceMode() {
        return serviceMode;
    }

    public void setServiceMode(String serviceMode) {
        this.serviceMode = serviceMode;
    }

    public String getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(String carStatus) {
        this.carStatus = carStatus;
    }

    public String getCarDirection() {
        return carDirection;
    }

    public void setCarDirection(String carDirection) {
        this.carDirection = carDirection;
    }

    public String getDoorZone() {
        return doorZone;
    }

    public void setDoorZone(String doorZone) {
        this.doorZone = doorZone;
    }

    public String getCarPosition() {
        return carPosition;
    }

    public void setCarPosition(String carPosition) {
        this.carPosition = carPosition;
    }

    public String getDoorStatus() {
        return doorStatus;
    }

    public void setDoorStatus(String doorStatus) {
        this.doorStatus = doorStatus;
    }

    public String getPassengerStatus() {
        return passengerStatus;
    }

    public void setPassengerStatus(String passengerStatus) {
        this.passengerStatus = passengerStatus;
    }

    public String getRunNum() {
        return runNum;
    }

    public void setRunNum(String runNum) {
        this.runNum = runNum;
    }

    public String getOperationStatus() {
        return operationStatus;
    }

    public void setOperationStatus(String operationStatus) {
        this.operationStatus = operationStatus;
    }

    public String getOperationDirection() {
        return operationDirection;
    }

    public void setOperationDirection(String operationDirection) {
        this.operationDirection = operationDirection;
    }

    public String getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(String statusTime) {
        this.statusTime = statusTime;
    }
}
