package juli.infrastructure.DateTime;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.beans.PropertyEditorSupport;

/**
 * 用于WebDataBinder将post的datetime的string格式自动转为joda date
 */
public class CustomJodaDateEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.isNotEmpty(text)) {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
            setValue(formatter.parseDateTime(text));
        }
    }

    @Override
    public String getAsText() throws IllegalArgumentException {
        return ((DateTime) getValue()).toString("yyyy-MM-dd HH:mm:ss");
    }
}