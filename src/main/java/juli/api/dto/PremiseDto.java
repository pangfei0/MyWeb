package juli.api.dto;

import juli.domain.Company;
import juli.domain.Premise;

public class PremiseDto extends BaseDto<Premise, PremiseDto> {
    private String name;
    private String address;
    private Company maintainer;
    private double lat;
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
