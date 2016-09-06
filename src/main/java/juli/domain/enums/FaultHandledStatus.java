package juli.domain.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum FaultHandledStatus {
    NOTHANDLED(10, "未处理"),
    HANDLED(20, "已处理");

    private static final Map<Integer, FaultHandledStatus> lookup = new HashMap<Integer, FaultHandledStatus>();

    static {
        for (FaultHandledStatus s : EnumSet.allOf(FaultHandledStatus.class))
            lookup.put(s.getCode(), s);
    }

    private int id = 0;
    private String name = null;

    FaultHandledStatus(int id, String name) {
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

    public static FaultHandledStatus getStatusByName(String status) {
        if (status.toUpperCase().equals("NOTHANDLED")) {
            return FaultHandledStatus.NOTHANDLED;
        }
        if (status.toUpperCase().equals("HANDLED")) {
            return FaultHandledStatus.HANDLED;
        }

        return null;
    }

    public static FaultHandledStatus get(int code) {
        return lookup.get(code);
    }









}
