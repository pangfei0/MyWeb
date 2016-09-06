package juli.api.dto;


import juli.domain.Collector;

public class CollectorDto extends BaseDto<Collector,CollectorDto> {

    private String id;

    private String intelHardwareNumber;

    private String number;

    /**
     * 电梯id
     */
    private String elevatorId;

    /**
     * 电梯所在楼盘楼盘
     */
    private String alias;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
