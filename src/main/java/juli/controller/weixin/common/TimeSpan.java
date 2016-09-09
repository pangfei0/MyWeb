package juli.controller.weixin.common;

import java.util.Date;

/**
 * Created by 53801 on 2015/9/4-23:09.
 */
public class TimeSpan
{
    public final static TimeSpan ZERO = new TimeSpan(0);

    private long _totalMilliSeconds = 0;

    public TimeSpan(long totalMilliSeconds)
    {
        _totalMilliSeconds = totalMilliSeconds;
    }

    public TimeSpan(Date afterDate, Date beforeDate)
    {
        this(afterDate.getTime() - beforeDate.getTime());
    }

    public long getMilliSeconds()
    {
        return _totalMilliSeconds;
    }
    public long  getSeconds()
    {
        return  Math.round(_totalMilliSeconds/1000);
    }
    public long getMinutes()
    {
        return  Math.round(_totalMilliSeconds/(1000*60));
    }
    public long getHours()
    {
        return  Math.round(_totalMilliSeconds/(1000*60*60));
    }
}