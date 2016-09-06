<#import "userLayout.ftl" as layout />
<#include "../func.ftl">

<#assign headerContent>
<link rel="stylesheet" href="/css/user/userAuthorization.css">
<link rel="stylesheet" href="/lib/bootstrap-table/bootstrap-table.css">
<link rel="stylesheet" href="/lib/sweetalert/sweetalert.css">
</#assign>

<@layout.userTemplate initScript="js/user/userAuthorization" header=headerContent>
<div class="panel panel-default">
    <div class="panel-heading">${t("userAuthorization")}</div>
    <div class="panel-body">
        <form class="search-container search-form tool-container ">
            <div class="search-field">
                <span>${t("userName")}:</span>
                <input type="text" style="width:120px;" id="userName" class="form-control"/>
            </div>
            <div class="search-field">
                <span>${t("staffName")}:</span>
                <input type="text" style="width:120px;" id="nick" class="form-control"/>
            </div>
            <div class="search-field">
                <span>${t("status")}:</span>
                <select id="search_userStatus" style="width:130px;" class="form-control" onchange="setUserStatus()">
                    <option value="-1" selected="selected">请选择用户状态</option>
                    <option value="0" >${t("forbid")}</option>
                    <option value="1" >${t("inUse")}</option>
                </select>
                <input type="hidden" style="width:120px;" id="userStatus" class="form-control"/>
            </div>
            <button type="button" class="btn btn-primary btn-search" v-on:click="search"><i class="fa fa-search"></i> ${t("search")}</button>
            <button type="reset" class="btn btn-default btn-reset" v-on:click="search | debounce 50">${t("reset")}</button>
            <div class="pull-right">
                <#if hasPermission("user:new")>
                    <a href="/user/authorization/cou" class="btn btn-success"><i class="fa fa-plus"></i> ${t("new")}</a>
                </#if>
            </div>
        </form>
        <table id="dataTable">
        </table>
    </div>
</div>
<div class="hidden" id="table-row-template">
    <#if hasPermission("user:edit")>
        <i class="fa fa-pencil-square-o edit-user" data-toggle="tooltip" data-placement="left" title="${t("edit")}" onclick="editUser('{id}');"></i>
    </#if>
    <#if hasPermission("user:forbid")>
        <i class="fa fa-trash-o delete-user" data-toggle="tooltip" data-placement="right" onclick="forbidUser('{id}');" title='${t("forbid")}'></i>
    </#if>
</div>
</@layout.userTemplate>