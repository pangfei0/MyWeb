package juli.domain.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pangfei on 2016/1/21.
 * 报告 客户评价枚举类型
 */
public enum Appraise {
    DEGREE1(10, "好"),
    DEGREE2(20, "良好"),
    DEGREE3(30, "一般"),
    DEGREE4(40, "差");

    private static final Map<Integer, Appraise> lookup = new HashMap<Integer, Appraise>();

    static {
        for (Appraise s : EnumSet.allOf(Appraise.class))
            lookup.put(s.getCode(), s);
    }

    private int id = 0;
    private String name = null;

    Appraise(int id, String name) {
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

    public static Appraise get(int code) {
        return lookup.get(code);
    }

}
