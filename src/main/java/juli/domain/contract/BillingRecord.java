package juli.domain.contract;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import juli.domain.BaseEntity;
import juli.infrastructure.DateTime.CustomDateSerializer;

import javax.persistence.*;
import java.util.Date;

@Entity
public class BillingRecord extends BaseEntity {

    /**
     * 保养合同号
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private UpkeepContract upkeepContract;

    /**
     * 发票号
     */
    @Column
    private String billNO;

    /**
     * 开票人员
     */
    @Column
    private String name;

    /**
     * 开票日期
     */
    @Column(columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date billingDate;

    /**
     * 开票金额
     */
    @Column(columnDefinition="double default 0")
    private Double billValue;

    public UpkeepContract getUpkeepContract() {
        return upkeepContract;
    }

    public void setUpkeepContract(UpkeepContract upkeepContract) {
        this.upkeepContract = upkeepContract;
    }

    public String getBillNO() {
        return billNO;
    }

    public void setBillNO(String billNO) {
        this.billNO = billNO;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(Date billingDate) {
        this.billingDate = billingDate;
    }

    public Double getBillValue() {
        return billValue;
    }

    public void setBillValue(Double billValue) {
        this.billValue = billValue;
    }
}
