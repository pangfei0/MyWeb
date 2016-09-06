package juli.domain.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ElevatorStatus {
    ONLINE(10, "在线"),
    OFFLINE(20, "离线");

    private static final Map<Integer, ElevatorStatus> lookup = new HashMap<Integer, ElevatorStatus>();

    static {
        for (ElevatorStatus s : EnumSet.allOf(ElevatorStatus.class))
            lookup.put(s.getCode(), s);
    }

    private int id = 0;
    private String name = null;

    ElevatorStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getCode() {
        return id;
    }

    public String getName() {
        if (name != null)
            return name;
        return Integer.toString(getCode());
    }

    public static ElevatorStatus getStatusByName(String status) {
        if (status.toUpperCase().equals("ONLINE")) {
            return ElevatorStatus.ONLINE;
        }
        if (status.toUpperCase().equals("OFFLINE")) {
            return ElevatorStatus.OFFLINE;
        }

        return null;
    }

    public static ElevatorStatus get(int code) {
        return lookup.get(code);
    }
}