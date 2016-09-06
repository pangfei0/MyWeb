package juli.api.dto;


import juli.domain.Permission;
import juli.domain.UnitRole;

import java.util.List;

public class UnitRoleDto extends BaseDto<UnitRole, UnitRoleDto> {

    String unitType;
    String roleName;
    String roleId;
    String parentIid;
    int orderIndex;
    List<String> permissions;

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
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

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getParentIid() {
        return parentIid;
    }

    public void setParentIid(String parentIid) {
        this.parentIid = parentIid;
    }
}
