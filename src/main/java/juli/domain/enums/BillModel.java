package juli.domain.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum BillModel {
    BANYUE(0,"半月保养工单"),
    JIDU(10, "季度保养工单"),
    BANNIAN(20, "半年保养工单"),
    YINIAN(30, "年度保养工单"),
    JIXIU(40, "急修工单"),
    WEIXIU(50, "维修工单");

    private static final Map<Integer, BillModel> lookup = new HashMap<>();

    static {
        for (BillModel s : EnumSet.allOf(BillModel.class))
            lookup.put(s.getCode(), s);
    }

    private int id = 0;
    private String name = null;

    BillModel(int id, String name) {
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

    public static BillModel get(int code) {
        return lookup.get(code);
    }

}
