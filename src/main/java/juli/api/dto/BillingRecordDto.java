package juli.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import juli.domain.contract.BillingRecord;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by pf on 2016/2/28.
 */
public class BillingRecordDto  extends BaseDto<BillingRecord, BillingRecordDto>{

    private String upkeepContractId;

    private String billNO;

    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date billingDate;

    private Double billValue;

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

    public String getUpkeepContractId() {
        return upkeepContractId;
    }

    public void setUpkeepContractId(String upkeepContractId) {
        this.upkeepContractId = upkeepContractId;
    }
}
