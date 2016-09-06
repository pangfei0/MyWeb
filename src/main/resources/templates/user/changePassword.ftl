<#import "userLayout.ftl" as layout />
<#include "../func.ftl">

<@layout.userTemplate initScript="js/user/changePassword">
<div class="panel panel-default" id="change-password">
    <div class="panel-heading">${t("changePassword")}</div>
    <div class="panel-body">
        <div class="form-horizontal">
            <div class="form-group">
                <label class="col-sm-2 control-label">${t("oldPassword")}</label>

                <div class="col-sm-10">
                    <input type="password" class="form-control" v-model="d.oldPassword">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">${t("newPassword")}</label>

                <div class="col-sm-10">
                    <input type="password" class="form-control" v-model="d.newPassword">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="button" class="btn btn-success" v-on:click="confirm">${t("confirm")}</button>
                </div>
            </div>
        </div>
    </div>
</div>
</@layout.userTemplate>