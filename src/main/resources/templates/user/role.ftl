<#import "userLayout.ftl" as layout />
<#include "../func.ftl">

<#assign headerContent>
<link rel="stylesheet" href="/css/user/role.css">
<link rel="stylesheet" href="/lib/bootstrap-table/bootstrap-table.css">
<link rel="stylesheet" href="/lib/sweetalert/sweetalert.css">
</#assign>

<@layout.userTemplate initScript="js/user/role" header=headerContent>
<div class="panel panel-default">
    <div class="panel-heading">${t("rolePermission")}</div>
    <div class="panel-body">
        <form class="search-container search-form">
            <div class="search-field">
                <span>${t("roleName")}:</span>
                <input type="text" style="width:120px;" id="name" class="form-control"/>
            </div>
            <div class="search-field">
                <span>${t("status")}:</span>
                <select id="search_roleStatus" class="form-control" onchange="setRoleStatus()">
                    <option value="-1" selected="selected">请选择角色状态</option>
                    <option value="0" >${t("forbid")}</option>
                    <option value="1" >${t("inUse")}</option>
                </select>
                <input type="hidden" style="width:120px;" id="roleStatus" class="form-control"/>
            </div>
            <button type="button" class="btn btn-primary btn-search" v-on:click="search"><i class="fa fa-search"></i> ${t("search")}</button>
            <button type="reset" class="btn btn-default btn-reset" v-on:click="search | debounce 50">${t("reset")}</button>
            <div class="tool-container pull-right">
                <a href="/user/role/cou" class="btn btn-success"><i class="fa fa-plus"></i> ${t("new")}</a>
            </div>
        </form>
        <table id="dataTable">
        </table>
    </div>
</div>
<div class="hidden" id="table-row-template">
    <#if hasPermission("role:edit")>
        <i class="fa fa-pencil-square-o edit-user" data-toggle="tooltip" data-placement="left" title="${t("edit")}" onclick="editUser('{id}');"></i>
    </#if>
    <#if hasPermission("role:forbid")>
        <i class="fa fa-trash-o delete-user" data-toggle="tooltip" data-placement="right" onclick="forbidRole('{id}');" title='${t("forbid")}'></i>
    </#if>
</div>
</@layout.userTemplate>