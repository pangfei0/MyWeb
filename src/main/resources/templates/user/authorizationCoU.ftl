<#import "userLayout.ftl" as layout />
<#include "../func.ftl">

<#assign headerContent>
<link rel="stylesheet" href="/lib/bootstrap-table/bootstrap-table.css">
<link rel="stylesheet" href="/lib/sweetalert/sweetalert.css">
<link rel="stylesheet" href="/lib/bootstrap-select/css/bootstrap-select.min.css">
</#assign>

<@layout.userTemplate initScript="js/user/userAuthorizationCoU" header=headerContent>
<div class="panel panel-default" id="userCoU">
    <div class="panel-heading" v-html="isEdit ? '${t("editUser")}':'${t("newUser")}'"></div>
    <div class="panel-body">
        <div class="form-horizontal">
            <div class="form-group">
                <label class="col-sm-2 control-label">${t("userName")}</label>

                <div class="col-sm-10">
                    <input type="text" class="form-control" v-model="user.userName">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">${t("password")}</label>

                <div class="col-sm-10">
                    <input type="password" class="form-control" v-model="user.password">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">${t("staffName")}</label>

                <div class="col-sm-10">
                    <input type="text" class="form-control" v-model="user.nick">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">${t("department")}</label>

                <div class="col-sm-10">
                    <select class="form-control selectpicker" data-live-search="true" v-model="user.organization">
                        <option title="{{org.name}}" value="{{org.id}}" v-for="org in organizations" v-html="org | treeItem">
                        </option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">${t("role")}</label>

                <div class="col-sm-10">
                    <select class="form-control" v-model="user.roleId">
                        <option value="-1" selected="selected">${t("pleaseSelectRole")}</option>
                        <#list roles as role>
                            <option value="${role.id}">${role.name}</option>
                        </#list>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">${t("typeOfCompany")}</label>
                <div class="col-sm-4">
                    <select id="search_companyType" v-model="user.companyType" class="form-control" onchange="searchCompany()">
                        <option value="-1" selected="selected">请选择公司类型</option>
                        <#list companyTypes as companyType>
                            <option value="${companyType.id}">${companyType.name}</option>
                        </#list>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">${t("nameOfCompany")}</label>

                <div class="col-sm-10">
                    <select class="form-control" id="companyId" v-model="user.companyId">
                        <option value="-1" selected="selected">${t("pleaseSelectCompany")}</option>
                        <#--<#list companies as company>-->
                            <#--<option value="${company.id}">${company.name}</option>-->
                        <#--</#list>-->
                    </select>
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