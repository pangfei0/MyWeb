package juli.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User extends BaseEntity {
    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column
    private String nick;

    @Column
    private String email;

    @Column
    private String telephone;
   //添加公司
   @Column
    private String companyId;

    //添加公司类型
    @Column
    private String companyType;

    //添加人员状态 0代表不在用，1代表在用
    @Column
    private String inUse;

    //添加申请状态 0代表已审核通过，1代表正在申请
    @Column
    private String isDemand;

    /**
     * 经度
     */
    @Column
    private Double lng;

    /**
     * 纬度
     */
    @Column
    private Double lat;


    //代表用户与微信端绑定的唯一标示openid
    @Column
    private String openid;

    @ManyToOne
    private Organization organization;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Role> roles = new ArrayList<>();

    public String getIsDemand() {
        return isDemand;
    }

    public void setIsDemand(String isDemand) {
        this.isDemand = isDemand;
    }

    public String getInUse() {
        return inUse;
    }

    public void setInUse(String inUse) {
        this.inUse = inUse;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public User() {
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    @Override
    public int hashCode() {
        return this.getId().length();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        return this.getId().equals(((User) obj).getId());
    }
}
