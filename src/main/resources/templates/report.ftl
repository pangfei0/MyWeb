<#import "maintainLayout.ftl" as layout />
<#include "func.ftl"/>

<#assign headerContent>
<link rel="stylesheet" href="/css/archive.css">
<link rel="stylesheet" href="/lib/bootstrap-table/bootstrap-table.css">
<link rel="stylesheet" href="/lib/bootstrap/css/bootstrap.min.css">
</#assign>

<#assign footerContent>

</#assign>

<@layout.maintainTemplate initScript="js/report" header=headerContent footer=footerContent title='${t("serviceManagement")}'>
<div class="panel panel-default">
    <div class="panel-heading">${t("report")}</div>
    <div class="panel-body">
        <form class="search-container search-form">
            <div class="search-field">
                <span>${t("reportNo")}:</span>
                <input type="text" style="width: 120px" id="reportNumber" class="form-control">
            </div>
            <div class="search-field">
                <span>处理人员:</span>
                <input type="text" style="width: 120px" id="maintenanceName" class="form-control">
            </div>
            <button type="button" class="btn btn-primary btn-search" v-on:click="search"><i
                    class="fa fa-search"></i> ${t("search")}</button>
            <button type="reset" class="btn btn-default btn-reset"
                    v-on:click="search | debounce 50">${t("reset")}</button>
            <div class="tool-container pull-right">
                <#if hasPermission("report")>
                    <button type="button" class="btn btn-export" v-on:click="exportWorkbill"><span></span>${t("report")}
                    </button>
                </#if>
            </div>
        </form>
    </div>
    <div class="panel-body">
        <table id="dataTable">

        </table>
    </div>

</div>
</@layout.maintainTemplate>