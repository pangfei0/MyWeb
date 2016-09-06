package juli.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import juli.infrastructure.DateTime.CustomDateSerializer;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by pf on 2016/3/1.
 */
@Entity
public class MaintenanceLoginRecord extends BaseEntity{
    /**
     * 员工
     */
    @ManyToOne(fetch = FetchType.LAZY)
    MaintenancePersonnel maintenancePersonnel;
    /**
     * 登录时间
     */
    @Column(columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date loginTime;

    /**
     * 登出时间
     */
    @Column(columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date leaveTime;
    /**
     * 状态类型 10上岗 20 待命 30 离线
     */
    @Column
    private Integer status;

    @Transient
    private  float hours;

    public MaintenancePersonnel getMaintenancePersonnel() {
        return maintenancePersonnel;
    }

    public void setMaintenancePersonnel(MaintenancePersonnel maintenancePersonnel) {
        this.maintenancePersonnel = maintenancePersonnel;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Date leaveTime) {
        this.leaveTime = leaveTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public float getHours() {
        if(leaveTime==null)
            return 0;
        else
        {
           return (leaveTime.getTime()-loginTime.getTime()) /(1000*3600*24);
        }

    }

    public void setHours(float hours) {
        this.hours = hours;
    }
}
