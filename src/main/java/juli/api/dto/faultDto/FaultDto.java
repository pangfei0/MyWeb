package juli.api.dto.faultDto;

/**
 * Created by jack on 2016/7/20.
 */
public class FaultDto {
    private String registration_code;// 注册代码  //不能为空
    private String number;// 电梯编号            //如“09GM1214A”，不能为空
    private String fault_id;//故障系统代码
    private String fault_code;//故障代码
    private String fault_time;//故障发生时间       //设备的当前时间 格式"2016-07-20 14:38:33"
    private String service_mode;//当前服务状态     //0：停止服务1：正常运行 2：检修 3：消防返回
                                                   // 4：消防员运行 5：应急电源运行 6：地震模式 7：未知
    private String car_status;//轿厢运行状态       //0：停止1：运行 (轿厢实际状态，停止即待机或断电)
    private String car_direction;//轿厢运行方向    //0：无方向1：上行2：下行 (轿厢实际运行状态，非轿厢内指示方向)
    private String door_zone;//门区                //1：电梯在门区 0：电梯在非门区（轿厢是否在门区，仅电梯停止时有效）
    private String car_position;//电梯当前层楼     //电梯物理楼层位
    private String door_status;//关门到位          // 1：关门到位  0：无关门到位信号
    private String passenger_status;//轿内是否有人  //1;有人  0：无人
    private String run_num;//电梯运行次数
    private String operation_status;//扶梯运行状态  //0：停止1：运行(实际状态，停止指待机或停止运行)
    private String operation_direction;//扶梯运行方向 //0：无方向1：上行 2：下行
    private String status_time;  //状态时间

    public String getRegistration_code() {
        return registration_code;
    }

    public void setRegistration_code(String registration_code) {
        this.registration_code = registration_code;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFault_id() {
        return fault_id;
    }

    public void setFault_id(String fault_id) {
        this.fault_id = fault_id;
    }

    public String getFault_code() {
        return fault_code;
    }

    public void setFault_code(String fault_code) {
        this.fault_code = fault_code;
    }

    public String getFault_time() {
        return fault_time;
    }

    public void setFault_time(String fault_time) {
        this.fault_time = fault_time;
    }

    public String getService_mode() {
        return service_mode;
    }

    public void setService_mode(String service_mode) {
        this.service_mode = service_mode;
    }

    public String getCar_status() {
        return car_status;
    }

    public void setCar_status(String car_status) {
        this.car_status = car_status;
    }

    public String getCar_direction() {
        return car_direction;
    }

    public void setCar_direction(String car_direction) {
        this.car_direction = car_direction;
    }

    public String getDoor_zone() {
        return door_zone;
    }

    public void setDoor_zone(String door_zone) {
        this.door_zone = door_zone;
    }

    public String getCar_position() {
        return car_position;
    }

    public void setCar_position(String car_position) {
        this.car_position = car_position;
    }

    public String getDoor_status() {
        return door_status;
    }

    public void setDoor_status(String door_status) {
        this.door_status = door_status;
    }

    public String getPassenger_status() {
        return passenger_status;
    }

    public void setPassenger_status(String passenger_status) {
        this.passenger_status = passenger_status;
    }

    public String getRun_num() {
        return run_num;
    }

    public void setRun_num(String run_num) {
        this.run_num = run_num;
    }

    public String getOperation_status() {
        return operation_status;
    }

    public void setOperation_status(String operation_status) {
        this.operation_status = operation_status;
    }

    public String getOperation_direction() {
        return operation_direction;
    }

    public void setOperation_direction(String operation_direction) {
        this.operation_direction = operation_direction;
    }

    public String getStatus_time() {
        return status_time;
    }

    public void setStatus_time(String status_time) {
        this.status_time = status_time;
    }
}
