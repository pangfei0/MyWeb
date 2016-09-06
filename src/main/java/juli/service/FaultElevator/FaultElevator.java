package juli.service.FaultElevator;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by MIS on 2016/5/25.
 */
public class FaultElevator {

    private String id;

    private String number;

    private String alias;

    private Double lat;

    private Double lng;

    private Date faultTime;

    private String faultCode;

    private String billId;

    private String billNumber;

    private DateTime DealTime;

    private String billStatus;

    private String DealerId;

    private String billDealer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Date getFaultTime() {
        return faultTime;
    }

    public void setFaultTime(Date faultTime) {
        this.faultTime = faultTime;
    }

    public String getFaultCode() {
        return faultCode;
    }

    public void setFaultCode(String faultCode) {
        this.faultCode = faultCode;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public DateTime getDealTime() {
        return DealTime;
    }

    public void setDealTime(DateTime dealTime) {
        DealTime = dealTime;
    }

    public String getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus;
    }

    public String getDealerId() {
        return DealerId;
    }

    public void setDealerId(String dealerId) {
        DealerId = dealerId;
    }

    public String getBillDealer() {
        return billDealer;
    }

    public void setBillDealer(String billDealer) {
        this.billDealer = billDealer;
    }
}
