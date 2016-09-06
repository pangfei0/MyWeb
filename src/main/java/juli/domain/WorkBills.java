package juli.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import juli.domain.enums.BillReportCategory;

import javax.persistence.*;
import java.util.Date;

@Entity
public class WorkBills extends BaseEntity {
    /**
     * 工单编号
     */
    @Column
    private  String billNumber;
    /**
     *电梯信息
     */
    @OneToOne
    @JsonIgnore
    private Elevator elevator;
    /**
     *工单模板
     */
    @Column
    private Integer billModel;
    /**
     *工单状态
     * @see juli.domain.enums.BillStatus
     */
    @Column
    private  Integer billstatus;
    /**
     *工单类别
     * @see BillReportCategory
     */
    @Column
    private  Integer billCategory;
    /**
     *故障描述（维修，急修）
     */
    @Column
    private  String faultDescription;
    /**
     *报障人员（维修，急修）
     */
    @Column
    private String faultPerson;
    /**
     *报障人员电话（维修，急修）
     */
    @Column
    private String faultPersonTelephone;
    /**
     *服务处理于（维修，急修
     */
    @Column
    private String serviceIn;
    /**
     *联系方式（维修，急修）
     */
    @Column
    private String telephone;
    /**
     *开始时间（维修，维保）/响应时间（急修）
     */
    @Column
    private Date startTime;
    /**
     *现场情况描述（维修，维保）
     */
    @Column
    private String localDescription;
    /**
     *采取措施（维修，维保）
     */
    @Column
    private String takeSteps;
    /**
     *VARCHAR(255)	故障性质（维修，维保）
     */
    @Column
    private String faultQuality;
    /**
     *处理结果（维修，维保）/未决事宜（维保）
     */
    @Column
    private String result;
    /**
     *维修人员编号（维修，急修）
     */
    @OneToOne(fetch = FetchType.LAZY)
    private MaintenancePersonnel maintenancePersonnel;
    /**
     *进场时间（急修）
     */
    @Column
    private Date enterTime;
    /**
     *拒绝原因（维修，维保）急修直接电话
     */
    @Column
    private String refuseReason;
    /**
     *完成时间
     */
    @Column
    private Date completeTime;
    /**
     *此次保养名称
     */
    @Column
    private  String maintainName;
    /**
     *性质（维保）（质保期内/有偿维保）
     * @see juli.domain.enums.MaintainProperty
     */
    @Column
    private  Integer maintainProperty;
    /**
     *维保项目（一字符串格式 如 a,b,c,a,a,d）
     表示该保养工单对应的工单模板的第一项选择的是a,第二项是b,第三项是c ,等如果没有需要拍照，则为null。
     */
    @Column
    private  String maintainPrograms;
    /**
     *图片1（如果工单模板中有拍照选项的，图片1代表该工单对应的工单模板中第一个需要拍照的选项的图片位置）
     */
    @Column
    private String picture1;
    /**
     *图片2 （如果工单模板中有拍照选项的，图片2代表该工单对应的工单模板中第二个需要拍照的选项的图片位置，如果没有需要拍照，则为null）
     */
    @Column
    private String picture2;
    /**
     *图片3 如图片2
     */
    @Column
    private String picture3;
    /**
     *图片4 如图片2
     */
    @Column
    private String picture4;
    /**
     *图片5 如图片2
     */
    @Column
    private String picture5;

    /**
     * 后期工单
     */
    @OneToOne(fetch = FetchType.LAZY)
    private WorkBills afterbill;

    /**
     * 楼盘
     */
    @Column
    private String alias;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * 电梯工号
     */
    @Column
    private String enumber;

    /**
     * 处理人
     */
    @Column
    private String actor;

    /**
     * 协助人
     */
    @Column
    private String assists;

    public String getAssists() {
        return assists;
    }

    public void setAssists(String assists) {
        this.assists = assists;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getEnumber() {
        return enumber;
    }

    public void setEnumber(String enumber) {
        this.enumber = enumber;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public Elevator getElevator() {
        return elevator;
    }

    public void setElevator(Elevator elevator) {
        this.elevator = elevator;
    }

    public Integer getBillstatus() {
        return billstatus;
    }

    public void setBillstatus(Integer billstatus) {
        this.billstatus = billstatus;
    }

    public Integer getBillCategory() {
        return billCategory;
    }

    public void setBillCategory(Integer billCategory) {
        this.billCategory = billCategory;
    }

    public String getFaultDescription() {
        return faultDescription;
    }

    public void setFaultDescription(String faultDescription) {
        this.faultDescription = faultDescription;
    }

    public String getFaultPerson() {
        return faultPerson;
    }

    public void setFaultPerson(String faultPerson) {
        this.faultPerson = faultPerson;
    }

    public String getFaultPersonTelephone() {
        return faultPersonTelephone;
    }

    public void setFaultPersonTelephone(String faultPersonTelephone) {
        this.faultPersonTelephone = faultPersonTelephone;
    }

    public String getServiceIn() {
        return serviceIn;
    }

    public void setServiceIn(String serviceIn) {
        this.serviceIn = serviceIn;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getLocalDescription() {
        return localDescription;
    }

    public void setLocalDescription(String localDescription) {
        this.localDescription = localDescription;
    }

    public String getTakeSteps() {
        return takeSteps;
    }

    public void setTakeSteps(String takeSteps) {
        this.takeSteps = takeSteps;
    }

    public String getFaultQuality() {
        return faultQuality;
    }

    public void setFaultQuality(String faultQuality) {
        this.faultQuality = faultQuality;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public MaintenancePersonnel getMaintenancePersonnel() {
        return maintenancePersonnel;
    }

    public void setMaintenancePersonnel(MaintenancePersonnel maintenancePersonnel) {
        this.maintenancePersonnel = maintenancePersonnel;
    }

    public Date getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(Date enterTime) {
        this.enterTime = enterTime;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public String getMaintainName() {
        return maintainName;
    }

    public void setMaintainName(String maintainName) {
        this.maintainName = maintainName;
    }

    public Integer getMaintainProperty() {
        return maintainProperty;
    }

    public void setMaintainProperty(Integer maintainProperty) {
        this.maintainProperty = maintainProperty;
    }

    public String getMaintainPrograms() {
        return maintainPrograms;
    }

    public void setMaintainPrograms(String maintainPrograms) {
        this.maintainPrograms = maintainPrograms;
    }

    public String getPicture1() {
        return picture1;
    }

    public void setPicture1(String picture1) {
        this.picture1 = picture1;
    }

    public String getPicture2() {
        return picture2;
    }

    public void setPicture2(String picture2) {
        this.picture2 = picture2;
    }

    public String getPicture3() {
        return picture3;
    }

    public void setPicture3(String picture3) {
        this.picture3 = picture3;
    }

    public String getPicture4() {
        return picture4;
    }

    public void setPicture4(String picture4) {
        this.picture4 = picture4;
    }
    public String getPicture5() {
        return picture5;
    }

    public void setPicture5(String picture5) {
        this.picture5 = picture5;
    }

    public WorkBills getAfterbill() {
        return afterbill;
    }

    public void setAfterbill(WorkBills afterbill) {
        this.afterbill = afterbill;
    }

    public Integer getBillModel() {
        return billModel;
    }

    public void setBillModel(Integer billModel) {
        this.billModel = billModel;
    }


}
