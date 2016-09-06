package juli.domain;


import javax.persistence.*;

/**
 * 采集器实体类
 */
@Entity
public class Collector extends BaseEntity {

    /**
     * 采集器  智能硬件注册码
     */
    @Column
    private String intelHardwareNumber;

    /**
     * 电梯id
     */
    @Column
    private String elevatorId;

    /**
     * 电梯所在楼盘楼盘
     */
    @Column
    private String alias;

    /**
     * 电梯工号
     */
    @Column
    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public String getElevatorId() {
        return elevatorId;
    }

    public void setElevatorId(String elevatorId) {
        this.elevatorId = elevatorId;
    }
}
