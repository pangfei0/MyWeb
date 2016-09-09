package juli.controller.weixin.common;

public class ParamContext extends ThreadLocal<Object>{
    private Object InitObj=null;
    public ParamContext()
    {
        InitObj=null;
    }
    public ParamContext(Object obj)
    {
        InitObj=obj;
    }
    @Override
    protected Object initialValue()
    {
        return InitObj;
    }
}
