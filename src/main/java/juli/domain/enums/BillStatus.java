package juli.domain.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum BillStatus {

    SEND(5,"待派发"),
    WAITING(10, "待接收"),
    INHAND(20, "处理中"),
    SUSPEND(30, "已暂停"),
    REFUSE(40, "已拒绝"),
    DONEREFUSE(45,"拒绝已处理"),
    ACCOMPLISH(50, "已完成"),
    TOFIX(55, "转维修"),
    EVALUATION(60, "待评价");

    private static final Map<Integer, BillStatus> lookup = new HashMap<Integer, BillStatus>();

    static {
        for (BillStatus s : EnumSet.allOf(BillStatus.class))
            lookup.put(s.getCode(), s);
    }

    private int id = 0;
    private String name = null;

    BillStatus(int id, String name) {
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

    public static BillStatus get(int code) {
        return lookup.get(code);
    }

}
