package juli.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import juli.domain.contract.UpkeepContract;
import juli.infrastructure.DateTime.CustomDateSerializer;
import juli.infrastructure.DateTime.CustomJodaDateTimeSerializer;
import juli.infrastructure.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


public class UpkeepContractDto extends BaseDto<UpkeepContract, UpkeepContractDto> {
    private String number;

    private String property;

    private String status;

    private String source;

    private String ownerFullname;

    private String ownerShortname;

    //private String  userCompanyId;

    // private String userCompanyName;
    private String partyAId;

    private String partyAContact;

    private String partyBId;

    private String partyBContact;
    private String partyATelephone;
    private String partyBTelephone;
    private String partyAAddress;
    private String partyBAddress;
    private String partyAName;
    private String partyBName;
    private Long remainDays;

    public Long getRemainDays() {
        return remainDays;
    }

    public void setRemainDays(Long remainDays) {
        this.remainDays = remainDays;
    }

    public String getPartyAAddress() {
        return partyAAddress;
    }

    public void setPartyAAddress(String partyAAddress) {
        this.partyAAddress = partyAAddress;
    }

    private float duration;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private float value;

    private double avgValue;

    private String paymentTerm;

    private String renewStatus;

    private double singleValue;
    private double needCollectValue;
    private double receivableValue;

    private Integer remainElevator;

    private Double  allBillingValue;

    private Double  allCollectValue;

    public String getPartyBName() {
        return partyBName;
    }

    public void setPartyBName(String partyBName) {
        this.partyBName = partyBName;
    }

    public String getPartyAName() {
        return partyAName;
    }

    public void setPartyAName(String partyAName) {
        this.partyAName = partyAName;
    }

    public String getPartyBTelephone() {
        return partyBTelephone;
    }

    public void setPartyBTelephone(String partyBTelephone) {
        this.partyBTelephone = partyBTelephone;
    }

    public String getPartyATelephone() {
        return partyATelephone;
    }

    public void setPartyATelephone(String partyATelephone) {
        this.partyATelephone = partyATelephone;
    }

    public String getPartyBAddress() {
        return partyBAddress;
    }

    public void setPartyBAddress(String partyBAddress) {
        this.partyBAddress = partyBAddress;
    }

    public String getPartyBContact() {
        return partyBContact;
    }

    public void setPartyBContact(String partyBContact) {
        this.partyBContact = partyBContact;
    }

    public String getPartyAContact() {
        return partyAContact;
    }

    public void setPartyAContact(String partyAContact) {
        this.partyAContact = partyAContact;
    }

    public String getPartyAId() {
        return partyAId;
    }

    public void setPartyAId(String partyAId) {
        this.partyAId = partyAId;
    }

    public String getPartyBId() {
        return partyBId;
    }

    public void setPartyBId(String partyBId) {
        this.partyBId = partyBId;
    }

    public double getNeedCollectValue() {
        return needCollectValue;
    }

    public void setNeedCollectValue(double needCollectValue) {
        this.needCollectValue = needCollectValue;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getOwnerFullname() {
        return ownerFullname;
    }

    public void setOwnerFullname(String ownerFullname) {
        this.ownerFullname = ownerFullname;
    }

    public String getOwnerShortname() {
        return ownerShortname;
    }

    public void setOwnerShortname(String ownerShortname) {
        this.ownerShortname = ownerShortname;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(String paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

    public String getRenewStatus() {
        return renewStatus;
    }

    public void setRenewStatus(String renewStatus) {
        this.renewStatus = renewStatus;
    }

    public double getReceivableValue() {
        return receivableValue;
    }

    public void setReceivableValue(double receivableValue) {
        this.receivableValue = receivableValue;
    }

    public Integer getRemainElevator() {
        return remainElevator;
    }

    public void setRemainElevator(Integer remainElevator) {
        this.remainElevator = remainElevator;
    }

    public Double getAllBillingValue() {
        return allBillingValue;
    }

    public void setAllBillingValue(Double allBillingValue) {
        this.allBillingValue = allBillingValue;
    }

    public Double getAllCollectValue() {
        return allCollectValue;
    }

    public void setAllCollectValue(Double allCollectValue) {
        this.allCollectValue = allCollectValue;
    }

    public double getAvgValue() {
        return avgValue;
    }

    public void setAvgValue(double avgValue) {
        this.avgValue = avgValue;
    }

    public double getSingleValue() {
        return singleValue;
    }

    public void setSingleValue(double singleValue) {
        this.singleValue = singleValue;
    }
}
