package juli.domain.contract;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import juli.domain.BaseEntity;
import juli.infrastructure.DateTime.CustomDateSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class CollectingRecord extends BaseEntity {

    /**
     * 保养合同号
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private UpkeepContract upkeepContract;

    /**
     * 收款日期
     */
    @Column(columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date collectionDate;

    /**
     * 收款金额
     */
    @Column(columnDefinition="double default 0")
    private Double collectingValue;

    @Column
    private String billNo;

    public UpkeepContract getUpkeepContract() {
        return upkeepContract;
    }

    public void setUpkeepContract(UpkeepContract upkeepContract) {
        this.upkeepContract = upkeepContract;
    }

    public Date getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(Date collectionDate) {
        this.collectionDate = collectionDate;
    }

    public Double getCollectingValue() {
        return collectingValue;
    }

    public void setCollectingValue(Double collectingValue) {
        this.collectingValue = collectingValue;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }
}
