package juli.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 角色权限
 */
@Entity
public class Permission extends BaseEntity {

    @Column(nullable = false)
    String name;

    /**
     * 权限代码，一般为类似 user:add 类似的形式
     */
    @Column(nullable = false)
    String code;

    /**
     * 显示顺序，小的显示在前面
     */
    @Column
    Integer priority;

    @Column
    String description;

    /**
     * 分类
     */
    @Column
    String category;

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
