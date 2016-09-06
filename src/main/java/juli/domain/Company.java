package juli.domain;

import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinFormula;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Company extends BaseEntity {
    @Column(nullable = false)
    private String name;

    /**
     * 电梯所在的省份
     */
    @Column
    private String provinceId;

    /**
     * 电梯所在的城市
     */
    @Column
    private String cityId;

    /**
     * 电梯所在的区，例如工业园区
     */
    @Column
    private String regionId;

    /**
     * 单位地址
     */
    @Column
    private String address;

    /**
     * 固话
     */
    @Column
    private String phone;

    /**
     * 联系人
     */
    @Column
    private String contact;

    /**
     * 维保联系人（汇同步川）
     */
    @Column
    private String mainTainName;
    /**
     * 维保联系人电话（汇同步川）
     */
    @Column
    private String mainTainPhone;

    /**
     * 物业联系人（汇同步川）
     */
    @Column
    private String managerPhone;
    /**
     * 物业联系人电话（汇同步川）
     */
    @Column
    private String managerName;

    /**
     * 手机
     */
    @Column
    private String mobile;

//    /**
//     *公司关联的电梯
//     */
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "company_elevator",
//            inverseJoinColumns =@JoinColumn(name = "elevator_id"),
//            joinColumns =@JoinColumn(name = "company_id") )
//    @JoinColumnOrFormula(column = @JoinColumn(name="company_id",referencedColumnName = "elevator_id"),@JoinColumnOrFormula(formula = @JoinFormula(value = "",referencedColumnName = "type")))
//    private Set<Elevator> elevator=new HashSet<>();
//    /**
//     * 电梯数量
//     */
//    @Transient
//    private Integer remainElevator;


//    public Set<Elevator> getElevator() {
//        return elevator;
//    }
//
//    public void setElevator(Set<Elevator> elevator) {
//        this.elevator = elevator;
//    }
//    public Integer getRemainElevator() {
//        return this.getElevator().size();
//    }
//
//    public void setRemainElevator(Integer remainElevator) {
//        this.remainElevator = remainElevator;
//    }

    public void setMainTainPhone(String mainTainPhone) {
        this.mainTainPhone = mainTainPhone;
    }

    public String getMainTainPhone() {
        return mainTainPhone;
    }

    public void setMainTainName(String mainTainName) {
        this.mainTainName = mainTainName;
    }

    public String getMainTainName() {
        return mainTainName;
    }

    public void setManagerPhone(String managerPhone) {
        this.managerPhone = managerPhone;
    }

    public String getManagerPhone() {
        return managerPhone;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerName() {
        return managerName;
    }

    /**
     * 单位类型
     *
     * @see juli.domain.enums.CompanyType
     */

    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

}
