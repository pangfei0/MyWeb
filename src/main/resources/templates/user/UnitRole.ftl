<#import "userLayout.ftl" as layout />
<#include "../func.ftl">

<#assign headerContent>
<link rel="stylesheet" href="/css/user/unitRole.css">
<link rel="stylesheet" href="/lib/bootstrap-table/bootstrap-table.css">
<link rel="stylesheet" href="/lib/sweetalert/sweetalert.css">
</#assign>

<@layout.userTemplate initScript="js/user/unitRole" header=headerContent>
<div class="panel panel-default">
    <div class="panel-heading">${t("unitRole")}</div>
    <div class="panel-body">
        <form class="search-container search-form">
            <div class="search-field">
                <span>${t("unitType")}:<span>
                <select id="search_unitType"  class="form-control" onchange="setSearch_unitTypeValue()">
                     <option value="-1" selected="selected">${t("pleaseSelectUnitName")}</option>
                </select>
                <input type="hidden" style="width: 120px" id="unitType" class="form-control">
            </div>
            <button type="button" class="btn btn-primary btn-search" v-on:click="search"><i class="fa fa-search"></i> ${t("search")}</button>
            <button type="reset" class="btn btn-default btn-reset" v-on:click="search | debounce 50">${t("reset")}</button>
            <div class="tool-container pull-right">
               <#if hasPermission("unitRole:new")>
                 <a href="/user/unitRole/cou" class="btn btn-success"><i class="fa fa-plus"></i> ${t("new")}</a>
               </#if>
            </div>
        </form>
        <table id="dataTable">
        </table>
    </div>
</div>
<div class="hidden" id="table-row-template">
    <#if hasPermission("unitRole:edit")>
        <i class="fa fa-pencil-square-o edit-user" data-toggle="tooltip" data-placement="left" title="${t("edit")}" onclick="editUnitRole('{roleId}');"></i>
    </#if>
    <#if hasPermission("unitRole:delete")>
        <i class="fa fa-trash-o delete-user" data-toggle="tooltip" data-placement="right" onclick="deleteUnitRole('{roleId}');" title='${t("delete")}'></i>
    </#if>
</div>
</@layout.userTemplate>