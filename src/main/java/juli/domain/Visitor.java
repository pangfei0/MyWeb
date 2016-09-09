package juli.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by pangfei on 2016/9/6.
 */
@Entity
public class Visitor extends BaseEntity{
    @Column
    private String openid;          //用户的标识，对当前公众号唯一
    @Column
    private String subscribe;       //用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
    @Column
    private String nickname;        //用户的昵称
    @Column
    private int sex;                //用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
    @Column
    private String city;            //用户所在城市
    @Column
    private String country;         //用户所在国家
    @Column
    private String province;        //用户所在省份
    @Column
    private String language;        //用户的语言，简体中文为zh_CN
    @Column
    private String headimgurl;      //用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空
    @Column
    private long subscribe_time;    //用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
    @Column
    private String unionid;         //只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段
    @Column
    private String remark;          //用户备注
    @Column
    private String groupid;         //用户分组

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(String subscribe) {
        this.subscribe = subscribe;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public long getSubscribe_time() {
        return subscribe_time;
    }

    public void setSubscribe_time(long subscribe_time) {
        this.subscribe_time = subscribe_time;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }
}
