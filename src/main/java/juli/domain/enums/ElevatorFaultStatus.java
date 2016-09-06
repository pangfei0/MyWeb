package juli.domain.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ElevatorFaultStatus {
    NORMAL(10, "正常"),
    MALFUNCTION(20, "故障");

    private static final Map<Integer, ElevatorFaultStatus> lookup = new HashMap<Integer, ElevatorFaultStatus>();

    static {
        for (ElevatorFaultStatus s : EnumSet.allOf(ElevatorFaultStatus.class))
            lookup.put(s.getCode(), s);
    }

    private int id = 0;
    private String name = null;

    ElevatorFaultStatus(int id, String name) {
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

    public static ElevatorFaultStatus getStatusByName(String status) {
        if (status.toUpperCase().equals("MALFUNCTION")) {
            return ElevatorFaultStatus.MALFUNCTION;
        }
        if (status.toUpperCase().equals("NORMAL")) {
            return ElevatorFaultStatus.NORMAL;
        }

        return null;
    }

    public static ElevatorFaultStatus get(int code) {
        return lookup.get(code);
    }
}