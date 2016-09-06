package juli.domain.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pangfei on 2016/1/21.
 * 工单类别 枚举类型
 */
public enum BillReportCategory {
    REPAIR(10, "维修工单"),
    MAINTAIN(20, "维保工单"),
    URGENT(30, "急修工单");

    private static final Map<Integer, BillReportCategory> lookup = new HashMap<Integer, BillReportCategory>();

    static {
        for (BillReportCategory s : EnumSet.allOf(BillReportCategory.class))
            lookup.put(s.getCode(), s);
    }

    private int id = 0;
    private String name = null;

    BillReportCategory(int id, String name) {
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

    public static BillReportCategory get(int code) {
        return lookup.get(code);
    }

}
