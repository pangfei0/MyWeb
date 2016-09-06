package juli.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class UnitRole extends BaseEntity {
    @Column(nullable = false)
    private String unitType;//单位类型

    @Column(nullable = false)
    private String roleId;//角色id

    @Column(nullable = false)
    private String roleName;//角色名称

    @Column
    private String parentId;

    @Column
    private int orderIndex = 1;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Permission> permissions = new ArrayList<>();

    @Column
    private String description;

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
