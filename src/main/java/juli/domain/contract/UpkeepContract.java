package juli.domain.contract;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import juli.domain.BaseEntity;
import juli.domain.Company;
import juli.domain.Elevator;
import juli.infrastructure.DateTime.CustomDateSerializer;

import javax.persistence.*;
import java.util.*;

@Entity
public class UpkeepContract extends BaseEntity {
    /**
     * 保养合同编号
     */
    @Column(unique = true, nullable = false)
    private String number;

    /**
     * 合同性质
     */
    @Column
    private String property;

    /**
     * 合同状态
     */
    @Column
    private String status;

    /**
     * 合同来源
     */
    @Column
    private String source;

    /**
     * 业主全称
     */
    @Column
    private String ownerFullname;

    /**
     * 业主简称
     */
    @Column
    private String ownerShortname;

    /**
     * 甲方：包含使用单位、物业单位
     */
    @OneToOne
    private Company partyA;

    /**
     * 乙方：维保单位
     */
    @OneToOne
    private Company partyB;

    /**
     * 合同期限
     */
    @Column(columnDefinition="float default 0")
    private float duration;

    /**
     * 合同生效日
     */
    @Column(columnDefinition = "DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date beginDate;

    /**
     * 合同到期日
     */
    @Column(columnDefinition = "DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date endDate;

    /**
     * 合同金额
     */
    @Column(columnDefinition="double default 0")
    private double value;

    /**
     * 付款条件
     */
    @Column
    private String paymentTerm;

    /**
     * 续签状态
     */
    @Column
    private String renewStatus;

    @Column(columnDefinition="double default 0")
    private double needCollectValue;
    /**
     * 开票记录
     */
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "upkeepContract")
    private List<BillingRecord> billingRecordList;
    /**
     * 收款记录
     */
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "upkeepContract")
    private List<CollectingRecord> collectingRecordList;
    /**
     * 应收账款
     */
    @Column(columnDefinition="double default 0")
    private double receivableValue;

    /**
     * 当前合同下的电梯的平均保养单价
     */
    @Column(columnDefinition="double default 0")
    private double avgValue;

    /**
     * 保养合同内的电梯
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "upkeepcontract_elevator",
            inverseJoinColumns =@JoinColumn(name = "elevator_id"),
            joinColumns =@JoinColumn(name = "upkeepcontract_id"))
    private Set<Elevator> elevator=new HashSet<>();
    /**
     * 单台电梯的保养价格
     */
    @Column(columnDefinition="double default 0.0")
    private double singleValue;
    /**
     * 合同到期倒计时
     */
    @Transient
    private Long  remainDays;
    /**
     * 电梯数量
     */
    @Transient
    private Integer remainElevator;
    /**
     * 开票总额
     */
    @Transient
    private Double  allBillingValue;
    /**
     * 到账总额
     */
    @Transient
    private Double  allCollectValue;

    /**
     * 合同中已生成批次的电梯
     */
    @Column
    private String elevatorAddBath;

    public Company getPartyA() {
        return partyA;
    }

    public void setPartyA(Company partyA) {
        this.partyA = partyA;
    }

    public Company getPartyB() {
        return partyB;
    }

    public void setPartyB(Company partyB) {
        this.partyB = partyB;
    }

    public String getElevatorAddBath() {
        return elevatorAddBath;
    }

    public void setElevatorAddBath(String elevatorAddBath) {
        this.elevatorAddBath = elevatorAddBath;
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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
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
    public Long getRemainDays() {
        Date date=new Date();
        if(endDate==null)
        {
            return 0l;
        }
        long days=endDate.getTime()-date.getTime();
        return  days/86400000;
    }

    public void setRemainDays(Long remainDays) {
        this.remainDays = remainDays;
    }

    public List<BillingRecord> getBillingRecordList() {
        return billingRecordList;
    }

    public void setBillingRecordList(List<BillingRecord> billingRecordList) {
        this.billingRecordList = billingRecordList;
    }

    public List<CollectingRecord> getCollectingRecordList() {
        return collectingRecordList;
    }

    public void setCollectingRecordList(List<CollectingRecord> collectingRecordList) {
        this.collectingRecordList = collectingRecordList;
    }

    public Set<Elevator> getElevator() {
        return elevator;
    }

    public void setElevator(Set<Elevator> elevator) {
        this.elevator = elevator;
    }

    public Integer getRemainElevator() {
        return this.getElevator().size();
    }

    public void setRemainElevator(Integer remainElevator) {
        this.remainElevator = remainElevator;
    }

    public Double getAllBillingValue() {
        double b=0;
        for(BillingRecord br:this.getBillingRecordList())
           b=b+br.getBillValue();
        return b;
    }

    public void setAllBillingValue(Double allBillingValue) {
        this.allBillingValue = allBillingValue;
    }

    public Double getAllCollectValue() {
        double b=0;
        for(CollectingRecord cr:this.getCollectingRecordList())
            b=b+cr.getCollectingValue();
        return b;
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
