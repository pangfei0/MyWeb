package juli.domain.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pangfei on 2016/1/21.
 * 报告状态枚举类型
 */
public enum ReportStatus {
    WAITINGPOST(10, "待评价"),
    HAVEPOST(20, "已评价");
    private static final Map<Integer, ReportStatus> lookup = new HashMap<Integer, ReportStatus>();

    static {
        for (ReportStatus s : EnumSet.allOf(ReportStatus.class))
            lookup.put(s.getCode(), s);
    }

    private int id = 0;
    private String name = null;

    ReportStatus(int id, String name) {
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

    public static ReportStatus get(int code) {
        return lookup.get(code);
    }

}
