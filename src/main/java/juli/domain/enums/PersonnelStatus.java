package juli.domain.enums;


import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum PersonnelStatus {
    ONDUTY(10,"上班"),
    STANDBY(20,"待命"),
    OFFLINE(30,"离线");

    private static final Map<Integer, PersonnelStatus> lookup = new HashMap<Integer, PersonnelStatus>();

    static {
        for (PersonnelStatus s : EnumSet.allOf(PersonnelStatus.class))
            lookup.put(s.getCode(), s);
    }

    private int id = 0;
    private String name = null;

    PersonnelStatus(int id, String name) {
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

    public static PersonnelStatus getStatusByName(String status) {
        if (status.toUpperCase().equals("ONDUTY")) {
            return PersonnelStatus.ONDUTY;
        }
        if (status.toUpperCase().equals("STANDBY")) {
            return PersonnelStatus.STANDBY;
        }
        if (status.toUpperCase().equals("OFFLINE")) {
            return PersonnelStatus.OFFLINE;
        }
        return null;
    }

    public static PersonnelStatus get(int code) {
        return lookup.get(code);
    }


}
