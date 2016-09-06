package juli.domain.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * 电梯检修状态
 */
public enum ElevatorMaintennanceStatus {
    NORMAL(10, "正常"),
    RECONDITION(20, "检修");

    private static final Map<Integer, ElevatorMaintennanceStatus> lookup = new HashMap<Integer, ElevatorMaintennanceStatus>();

    static {
        for (ElevatorMaintennanceStatus s : EnumSet.allOf(ElevatorMaintennanceStatus.class))
            lookup.put(s.getCode(), s);
    }

    private int id = 0;
    private String name = null;

    ElevatorMaintennanceStatus(int id, String name) {
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

    public static ElevatorMaintennanceStatus getStatusByName(String status) {
        if (status.toUpperCase().equals("RECONDITION")) {
            return ElevatorMaintennanceStatus.RECONDITION;
        }
        if (status.toUpperCase().equals("NORMAL")) {
            return ElevatorMaintennanceStatus.NORMAL;
        }

        return null;
    }

    public static ElevatorMaintennanceStatus get(int code) {
        return lookup.get(code);
    }
}