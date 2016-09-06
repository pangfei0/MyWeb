<#import "userLayout.ftl" as layout />
<#include "../func.ftl">

<#assign headerContent>
<link rel="stylesheet" href="/css/user/userInfo.css">
</#assign>

<@layout.userTemplate initScript="js/user/userInfoEdit" header=headerContent>
<div class="panel panel-default" id="userInfo">
    <div class="panel-heading">${t("userInfo")}</div>
    <div class="panel-body">
        <div class="form-horizontal">
            <div class="form-group">
                <label class="col-sm-2 control-label">${t("nick")}：</label>

                <div class="col-sm-10">
                    <input type="text" class="form-control" v-model="user.nick">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">${t("mobile")}：</label>

                <div class="col-sm-10">
                    <input type="text" class="form-control" v-model="user.telephone">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">${t("email")}:</label>

                <div class="col-sm-10">
                    <input type="text" class="form-control" v-model="user.email">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label"></label>

                <div class="col-sm-10">
                    <button class="btn btn-success" type="button" v-on:click="save">${t("save")}</button>
                </div>
            </div>
        </div>
    </div>
</div>
</@layout.userTemplate>
