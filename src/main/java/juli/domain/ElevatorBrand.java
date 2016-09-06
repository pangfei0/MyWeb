package juli.domain;


import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class ElevatorBrand extends BaseEntity{

    /**
     * 电梯品牌编号
     */
    @Column
    private String number;

    /**
     * 品牌名称
     */
    @Column
    private String name;

    /**
     * 协议报文头
     */
    @Column
    private String protocolType;

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

    public String getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(String protocolType) {
        this.protocolType = protocolType;
    }
}
