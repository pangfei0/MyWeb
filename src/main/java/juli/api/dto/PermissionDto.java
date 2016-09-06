package juli.api.dto;

import juli.domain.Permission;

public class PermissionDto extends BaseDto<Permission, PermissionDto> {
    String name;
    String code;
    Integer priority;
    String description;
    String category;

    public PermissionDto(String id, String name, String code, Integer priority, String description, String category) {
        setId(id);
        this.name = name;
        this.code = code;
        this.priority = priority;
        this.description = description;
        this.category = category;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
