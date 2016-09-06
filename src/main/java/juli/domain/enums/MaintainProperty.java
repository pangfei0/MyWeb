package juli.domain.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pangfei on 2016/1/21.
 * 维保性质 枚举类型
 */
public enum MaintainProperty {
    PAID(10, "有偿维护"),
    NOPAID(20, "质保期内");

    private static final Map<Integer, MaintainProperty> lookup = new HashMap<Integer, MaintainProperty>();

    static {
        for (MaintainProperty s : EnumSet.allOf(MaintainProperty.class))
            lookup.put(s.getCode(), s);
    }

    private int id = 0;
    private String name = null;

    MaintainProperty(int id, String name) {
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

    public static MaintainProperty get(int code) {
        return lookup.get(code);
    }

}
