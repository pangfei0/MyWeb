package juli.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Created by pf on 2016/2/25.
 */
@Entity
public class RepairLevel extends BaseEntity {

    @Column
    private String name;
    @OneToOne
    private ElevatorBrand elevatorBrand;

//    @ManyToMany(fetch = FetchType.LAZY)
//    private List<MaintenancePersonnel> levelList=new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ElevatorBrand getElevatorBrand() {
        return elevatorBrand;
    }

    public void setElevatorBrand(ElevatorBrand elevatorBrand) {
        this.elevatorBrand = elevatorBrand;
    }

//    public List<MaintenancePersonnel> getLevelList() {
//        return levelList;
//    }
//
//    public void setLevelList(List<MaintenancePersonnel> levelList) {
//        this.levelList = levelList;
//    }
}
