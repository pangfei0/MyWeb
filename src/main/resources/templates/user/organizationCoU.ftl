<#import "userLayout.ftl" as layout />
<#include "../func.ftl">

<#assign headerContent>
<link rel="stylesheet" href="/lib/bootstrap-table/bootstrap-table.css">
<link rel="stylesheet" href="/lib/sweetalert/sweetalert.css">
<link rel="stylesheet" href="/lib/bootstrap-select/css/bootstrap-select.min.css">
</#assign>

<@layout.userTemplate initScript="js/user/organizationCoU" header=headerContent>
<div class="panel panel-default" id="organizationCoU">
    <div class="panel-heading" v-html="isEdit ? '${t("editOrganization")}':'${t("newOrganization")}'"></div>
    <div class="panel-body">
        <div class="form-horizontal">
            <div class="form-group">
                <label class="col-sm-2 control-label">${t("name")}</label>

                <div class="col-sm-10">
                    <input type="text" class="form-control" v-model="organization.name">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">${t("parentOrganization")}</label>

                <div class="col-sm-10">
                    <select class="form-control selectpicker" data-live-search="true" v-model="organization.parent">
                        <option title="{{org.name}}" value="{{org.id}}" v-for="org in organizations" v-html="org | treeItem">
                        </option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">${t("orderIndex")}</label>

                <div class="col-sm-10">
                    <input type="text" class="form-control" v-model="organization.orderIndex">
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