package juli.service.DataServer.contracts;

public class DataServerCompany {
    /**
     * 单位名称
     */
    String companyName;

    /**
     * 详细地址
     */
    String address;

    String companyTelOne;

    String companyTelTwo;

    /**
     * 负责人
     */
    String principal;
    /**
     * 负责人电话
     */
    String principalTel;

    /**
     * 维保联系人（汇同步川）
     */
    String mainTainName;
    /**
     * 维保联系人电话（汇同步川）
     */
    String mainTainPhone;

    /**
     * 物业联系人（汇同步川）
     */
    String managerPhone;
    /**
     * 物业联系人电话（汇同步川）
     */
    String managerName;

    /**
     * 公司所属省
     */
    String province;

    /**
     * 公司所属市
     */
    String city;

    /**
     * 公司所属区
     */
    String area;

    public String getMainTainName() {
        return mainTainName;
    }

    public void setMainTainName(String mainTainName) {
        this.mainTainName = mainTainName;
    }

    public void setMainTainPhone(String mainTainPhone) {
        this.mainTainPhone = mainTainPhone;
    }

    public String getMainTainPhone() {
        return mainTainPhone;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerPhone(String managerPhone) {
        this.managerPhone = managerPhone;
    }

    public String getManagerPhone() {
        return managerPhone;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompanyTelOne() {
        return companyTelOne;
    }

    public void setCompanyTelOne(String companyTelOne) {
        this.companyTelOne = companyTelOne;
    }

    public String getCompanyTelTwo() {
        return companyTelTwo;
    }

    public void setCompanyTelTwo(String companyTelTwo) {
        this.companyTelTwo = companyTelTwo;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getPrincipalTel() {
        return principalTel;
    }

    public void setPrincipalTel(String principalTel) {
        this.principalTel = principalTel;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
