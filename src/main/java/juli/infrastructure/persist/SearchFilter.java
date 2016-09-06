package juli.infrastructure.persist;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchFilter {

    public static String REG_PATTERN = "(.*?)(\\(.*\\))?_(.*)";  //useranme(date)_like or username_like
    private String fieldName;
    private Object value;
    private Operator operator;
    private DataType dataType;

    public SearchFilter(String fieldName, Operator operator, Object value) {
        this(fieldName, operator, value, DataType.TEXT);
    }

    public SearchFilter(String fieldName, Operator operator, Object value, DataType dataType) {
        this.fieldName = fieldName;
        this.value = value;
        this.operator = operator;
        this.dataType = dataType;
    }

    /**
     * searchParams中key的格式为fileName(dataType)_operator,其中dataType可以省略为TEXT类型,value为操作值
     * 例如：
     * userName_like=xxxx
     * userNameCreationDate(DATETIME)_like=xxxx  这里如果不指定为Datetime类型，则动态搜索的时候会报错（数据库搜索类型不匹配）
     */
    public static List<SearchFilter> parse(Map<String, Object> searchParams) {
        List<SearchFilter> filters = new ArrayList<>();

        for (Entry<String, Object> entry : searchParams.entrySet()) {
            // 过滤掉空值
            String key = entry.getKey();
            Object value = entry.getValue();
            if (StringUtils.isBlank((String) value)) {
                continue;
            }

            Pattern pattern = Pattern.compile(SearchFilter.REG_PATTERN);
            Matcher matcher = pattern.matcher(key);
            if (matcher.find()) {
                String field = matcher.group(1);
                String type = matcher.group(2);
                String operator = matcher.group(3);
                if (type == null) {
                    type = "(text)"; //默认为text类型
                }
                type = type.substring(1, type.length() - 1);

                SearchFilter filter = new SearchFilter(field, Operator.valueOf(operator.toUpperCase()), value, DataType.valueOf(type.toUpperCase()));
                filters.add(filter);
            }
        }

        return filters;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Object getValue() {
        if (getDataType() == DataType.DATETIME) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return format.parse(this.value.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (getOperator() == Operator.IN) {
            if (value instanceof String) {
                return Arrays.asList(StringUtils.split(value.toString(), ","));
            }
        }
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public enum Operator {
        EQ, LIKE, GT, LT, GTE, LTE, IN
    }

    public enum DataType {
        TEXT, DATETIME
    }
}
