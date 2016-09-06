package juli.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * 楼盘信息
 */
@Entity
public class Premise extends BaseEntity {
    @Column
    private String name;

    @Column
    private String address;

    /**
     * 维保单位
     */
    @OneToOne
    private Company maintainer;

    @Column
    private double lat;

    @Column
    private double lng;

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

    public Company getMaintainer() {
        return maintainer;
    }

    public void setMaintainer(Company maintainer) {
        this.maintainer = maintainer;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
