package juli.domain;

import juli.domain.enums.BillReportCategory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import java.util.Date;

/**
 * Created by pangfei on 2016/1/21.
 */
@Entity
public class Reports extends BaseEntity{
    /**
     *报告编号（工单编号）
     */
    @Column
    private String reportNumber;
    /**
     *设备号（电梯编号）
     */
    @OneToOne
    private Elevator elevator;
    /**
     *报告状态 （已提交/未提交）
     * @see juli.domain.enums.ReportStatus
     */
    @Column
    private Integer reportStatus;
    /**
     *报告类型
     * @see BillReportCategory
     */
    @Column
    private Integer reportCategory;
    /**
     *开始时间（维修/维修）/响应时间（急修）
     */
    @Column
    private Date  startTime;
    /**
     *完成时间
     */
    @Column
    private  Date completeTime;
    /**
     *情况描述
     */
    @Column
    private  String description;
    /**
     *故障性质(维修。急修)/未决事宜（维保）
     */
    @Column
    private  String faultQuality;


    /**
     * 综合评价
     */
    @Column
    private  Integer generalEvaluate;
    /**
     *处理结果
     */
    @Column
    private String result;
    /**
     * 服务态度
     * @see juli.domain.enums.Appraise
     */
    @Column
    private Integer serviceAttitude;
    /**
     *服务水平
     * @see juli.domain.enums.Appraise
     */
    @Column
    private  Integer serviceLevel;
    /**
     *维护客户环境和完全
     * @see juli.domain.enums.Appraise
     */
    @Column
    private Integer envAndSafe;
    /**
     *问题解决和故障排除
     * @see juli.domain.enums.Appraise
     */
    @Column
    private  Integer resolveQuestion;
    /**
     *建议
     */
    @Column
    private  String suggestions;
    /**
     *顾客签名
     */
    @Column
    private String sign;
    /**
     *日期
     */
    @Column
    private Date reportTime;
    /**
     *进场时间（急修）
     */
    @Column
    private  Date enterTime;
    /**
     *维修人员（维修，维保）/维保人员（维保）
     */
    @OneToOne(fetch = FetchType.LAZY)
    private MaintenancePersonnel maintenancePersonnel;
    /**
     *维修单位/保养单位
     */
    @OneToOne(fetch = FetchType.LAZY)
    private  Company company;
    /**
     *后续维修工单（维保）
     */
    @OneToOne(fetch = FetchType.LAZY)
    private  WorkBills afterBill;
    /**
     *当前工单（维保）
     */
    @OneToOne(fetch = FetchType.LAZY)
    private  WorkBills currentBill;

    public Integer getGeneralEvaluate() {
        return generalEvaluate;
    }

    public void setGeneralEvaluate(Integer generalEvaluate) {
        this.generalEvaluate = generalEvaluate;
    }

    public String getReportNumber() {
        return reportNumber;
    }

    public void setReportNumber(String reportNumber) {
        this.reportNumber = reportNumber;
    }

    public Elevator getElevator() {
        return elevator;
    }

    public void setElevator(Elevator elevator) {
        this.elevator = elevator;
    }

    public Integer getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(Integer reportStatus) {
        this.reportStatus = reportStatus;
    }

    public Integer getReportCategory() {
        return reportCategory;
    }

    public void setReportCategory(Integer reportCategory) {
        this.reportCategory = reportCategory;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getServiceAttitude() {
        return serviceAttitude;
    }

    public void setServiceAttitude(Integer serviceAttitude) {
        this.serviceAttitude = serviceAttitude;
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

    public Integer getServiceLevel() {
        return serviceLevel;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void setServiceLevel(Integer serviceLevel) {
        this.serviceLevel = serviceLevel;
    }

    public Integer getEnvAndSafe() {
        return envAndSafe;
    }

    public void setEnvAndSafe(Integer envAndSafe) {
        this.envAndSafe = envAndSafe;
    }

    public Integer getResolveQuestion() {
        return resolveQuestion;
    }

    public void setResolveQuestion(Integer resolveQuestion) {
        this.resolveQuestion = resolveQuestion;
    }

    public String getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(String suggestions) {
        this.suggestions = suggestions;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public Date getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(Date enterTime) {
        this.enterTime = enterTime;
    }

    public MaintenancePersonnel getMaintenancePersonnel() {
        return maintenancePersonnel;
    }

    public void setMaintenancePersonnel(MaintenancePersonnel maintenancePersonnel) {
        this.maintenancePersonnel = maintenancePersonnel;
    }
    public WorkBills getAfterBill() {
        return afterBill;
    }

    public void setAfterBill(WorkBills afterBill) {
        this.afterBill = afterBill;
    }

    public WorkBills getCurrentBill() {
        return currentBill;
    }

    public void setCurrentBill(WorkBills currentBill) {
        this.currentBill = currentBill;
    }
}
