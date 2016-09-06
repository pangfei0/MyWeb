package juli.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import juli.domain.contract.CollectingRecord;
import juli.infrastructure.DateTime.CustomDateSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by pf on 2016/2/28.
 */
public class CollectingRecordDto extends BaseDto<CollectingRecord, CollectingRecordDto> {

    private String upkeepContractId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date collectionDate;

    private Double collectingValue;

    private String billNo;
    private String CreateBy;

    public String getCreateBy() {
        return CreateBy;
    }

    public void setCreateBy(String createBy) {
        CreateBy = createBy;
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

    public String getUpkeepContractId() {
        return upkeepContractId;
    }

    public void setUpkeepContractId(String upkeepContractId) {
        this.upkeepContractId = upkeepContractId;
    }
}
