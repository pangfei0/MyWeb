package juli.api.dto;

import juli.domain.Company;

public class CompanyDto extends BaseDto<Company, CompanyDto> {

    private String name;
    private String address;
    private String phone;
    private String contact;
    private String mobile;
    private Integer type;
    private Integer remainElevator;
    private String  typeName;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getRemainElevator() {
        return remainElevator;
    }

    public void setRemainElevator(Integer remainElevator) {
        this.remainElevator = remainElevator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
