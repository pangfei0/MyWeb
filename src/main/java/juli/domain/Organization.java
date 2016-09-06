package juli.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Organization extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Organization parent;

    @OneToMany(mappedBy = "parent")
    @JsonIgnore
    private List<Organization> children = new ArrayList<>();

    @OneToMany(mappedBy = "organization")
    @JsonIgnore
    private List<User> users = new ArrayList<>();

    @Column
    private int orderIndex = 1;

    @Transient
    private int level;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Organization getParent() {
        return parent;
    }

    public void setParent(Organization parent) {
        this.parent = parent;
    }

    public List<Organization> getChildren() {
        return children;
    }

    public void setChildren(List<Organization> children) {
        this.children = children;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}