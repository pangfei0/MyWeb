<#import "userLayout.ftl" as layout />
<#include "../func.ftl">

<#assign headerContent>
<link rel="stylesheet" href="/css/user/userInfo.css">
</#assign>

<@layout.userTemplate initScript="js/user/userInfo" header=headerContent>
<div class="panel panel-default" id="userInfo">
    <div class="panel-heading">${t("userInfo")}</div>
    <div class="panel-body">
        <div class="form-horizontal">
            <div class="form-group">
                <label class="col-sm-2 control-label">${t("userName")}：</label>

                <label class="control-label pull-left" v-html="user.userName"></label>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">${t("nick")}：</label>

                <p class="control-label pull-left" v-html="user.nick"></p>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">${t("mobile")}：</label>
                <label class="control-label pull-left" v-html="user.telephone"></label>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">${t("email")}:</label>
                <label class="control-label pull-left" v-html="user.email"></label>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">${t("role")}:</label>
                <label class="control-label pull-left" v-html="user.roleName"></label>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label"></label>
                <a class="btn btn-success" href="/user/userInfo/edit"><i class="fa fa-pencil"></i> ${t("edit")}</a>
            </div>
        </div>
    </div>
</div>
</@layout.userTemplate>
