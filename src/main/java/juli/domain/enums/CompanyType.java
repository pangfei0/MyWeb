package juli.domain.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public enum CompanyType {
    INSTALL(10, "安装单位"),
    MAINTAINER(20, "维保单位"),
    USE(30, "使用单位"),
    MANUFACTURER(40, "制造单位"),
    OWNER(50, "物业单位"),
    REGULATOR(60, "监管机构"),
    PERSONAL(70, "个人用户"),
    OTHERS(80,"其他类型");



    private static final Map<Integer, CompanyType> lookup = new HashMap<Integer, CompanyType>();

    static {
        for (CompanyType s : EnumSet.allOf(CompanyType.class))
            lookup.put(s.getCode(), s);
    }

    private int id = 0;
    private String name = null;

    CompanyType(int id, String name) {
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

    public static CompanyType get(int code) {
        return lookup.get(code);
    }

    public static CompanyType getCompanyType(int value){
        for(CompanyType companyType:CompanyType.values()){
            if(value==companyType.getCode())
            {
                return companyType;
            }
        }
        return null;
    }

}