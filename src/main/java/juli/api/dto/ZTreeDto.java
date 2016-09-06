package juli.api.dto;

import java.util.ArrayList;
import java.util.List;

public class ZTreeDto {
    String id;
    String name;
    boolean open = true;
    List<ZTreeDto> children = new ArrayList<>();

    public List<ZTreeDto> getChildren() {
        return children;
    }

    public void setChildren(List<ZTreeDto> children) {
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
