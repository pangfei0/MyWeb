package juli.api.dto;


import juli.domain.MaintenancePersonnel;

public class MaintenancePersonnel2Dto extends BaseDto<MaintenancePersonnel,MaintenancePersonnel2Dto>{

    private String name;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
