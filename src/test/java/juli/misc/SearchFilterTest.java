package juli.misc;

import juli.SpringTestBase;
import juli.infrastructure.persist.SearchFilter;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchFilterTest extends SpringTestBase {

    @Test
    public void testDynamicSearchParameter() {
        Pattern pattern = Pattern.compile(SearchFilter.REG_PATTERN);
        Matcher matcher = pattern.matcher("userName(text)_like");
        boolean find = matcher.find();
        Assert.assertTrue(find);

        String field = matcher.group(1);
        String type = matcher.group(2);
        String operation = matcher.group(3);
        Assert.assertEquals("userName", field);
        Assert.assertEquals("(text)", type);
        Assert.assertEquals("like", operation);
    }

    @Test
    public void testDynamicSearchParameterWithouType() {
        Pattern pattern = Pattern.compile(SearchFilter.REG_PATTERN);
        Matcher matcher = pattern.matcher("userName_like");
        boolean find = matcher.find();
        Assert.assertTrue(find);

        String field = matcher.group(1);
        String type = matcher.group(2);
        String operation = matcher.group(3);
        Assert.assertEquals("userName", field);
        Assert.assertEquals(null, type);
        Assert.assertEquals("like", operation);
    }

    @Test
    public void testParse() {
        HashMap<String, Object> searches = new HashMap<>();
        searches.put("username_like", "qianlifeng");
        searches.put("time(datetime)_eq", "2011-11-11");
        List<SearchFilter> filters = SearchFilter.parse(searches);

        Assert.assertTrue(filters.size() == 2);
        Assert.assertTrue(filters.get(0).getOperator() == SearchFilter.Operator.LIKE);
        Assert.assertTrue(filters.get(0).getFieldName().equals("username"));
        Assert.assertTrue(filters.get(0).getDataType() == SearchFilter.DataType.TEXT);
        Assert.assertTrue(filters.get(1).getOperator() == SearchFilter.Operator.EQ);
        Assert.assertTrue(filters.get(1).getDataType() == SearchFilter.DataType.DATETIME);

    }
}
