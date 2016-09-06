<#import "userLayout.ftl" as layout />
<#include "../func.ftl">

<#assign headerContent>
<link rel="stylesheet" href="/css/user/unitRoleCoU.css">
<link rel="stylesheet" href="/lib/bootstrap-table/bootstrap-table.css">
<link rel="stylesheet" href="/lib/sweetalert/sweetalert.css">
<link rel="stylesheet" href="/lib/bootstrap-select/css/bootstrap-select.min.css">
</#assign>

<@layout.userTemplate initScript="js/user/unitRoleCoU" header=headerContent>
<div class="panel panel-default" id="unitRoleCoU">
    <div class="panel-heading" v-html="isEdit ? '${t("editUnitType")}':'${t("addUnitType")}'"></div>
    <div class="panel-body">
        <div class="form-horizontal">
            <div class="form-group">
                <label class="col-sm-2 control-label">${t("unitType")}</label>

                <div class="col-sm-10">
                    <input type="text" class="form-control" v-model="unitRole.unitType"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">${t("role")}</label>

                <div class="col-sm-10">
                    <input type="text" class="form-control" v-model="unitRole.roleName"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">${t("rolePermission")}</label>

                <div class="col-sm-10">
                    <div v-for="permissionGroup in availablePermissions">
                        <div class="panel panel-default">
                            <div class="panel-heading">{{permissionGroup[0].category}}</div>
                            <div class="panel-body">
                                <div v-for="permission in permissionGroup" class="permission-checkbox">
                                    <input type="checkbox" id="{{permission.id}}" value="{{permission.id}}" v-model="unitRole.permissions"/>
                                    <label for="{{permission.id}}">{{permission.name}}</label>
                                </div>
                            </div>
                        </div>
                    </div>
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