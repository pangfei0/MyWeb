package juli.api.core;

import io.swagger.annotations.ApiModelProperty;

/**
 * API返回格式
 * 所有的API都是通过这个实体返回给前台
 */
public class APIResponse {
    boolean success;
    String description;
    Object data;

    public APIResponse() {
    }

    public APIResponse(boolean success, String desc, Object data) {
        this.success = success;
        this.description = desc;
        this.data = data;
    }

    public static APIResponse success(Object data) {
        return new APIResponse(true, "", data);
    }

    public static APIResponse success(String description) {
        return new APIResponse(true, description, null);
    }

    public static APIResponse success() {
        return new APIResponse(true, "成功", null);
    }

    public static APIResponse error(String description) {
        return new APIResponse(false, description, null);
    }

    @ApiModelProperty(value = "返回实际的请求内容")
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @ApiModelProperty(value = "调用是否成功。成功返回true，否则返回false")
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @ApiModelProperty(value = "调用说明。如果出错，一般都会在这里看到相关错误信息")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
