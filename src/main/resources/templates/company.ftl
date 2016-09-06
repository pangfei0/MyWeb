<#import "archiveLayout.ftl" as layout />
<#include 'func.ftl'>

<#assign headerContent>
<link rel="stylesheet" href="/css/archive.css">
<link rel="stylesheet" href="/lib/bootstrap-table/bootstrap-table.css">
<link rel="stylesheet" href="/lib/bootstrap/css/bootstrap.min.css">
</#assign>

<@layout.archiveTemplate header=headerContent  initScript='js/company' title='${t("recordsCompanies")}'>
<div class="panel panel-default">
    <div class="panel-heading" v-html="isUseCompany ? '${t("useCompanies")}':'${t("maintainers")}'"></div>
    <div class="panel-body">
        <form class="search-container search-form">
            <input type="hidden" id="type" value="${type}" data-search-operation="eq"/>

            <div class="search-field">
                <span>${t("nameOfCompany")}:</span>
                <input type="text" style="width:120px;" id="name" class="form-control"/>
            </div>
            <div class="search-field">
                <span>${t("addressOfCompany")}:</span>
                <input type="text" style="width:120px;" id="address" class="form-control"/>
            </div>
            <button type="button" class="btn btn-primary btn-search" v-on:click="search"><i class="fa fa-search"></i> ${t("search")}</button>
            <button type="reset" class="btn btn-default btn-reset" v-on:click="search | debounce 50">${t("reset")}</button>
            <div class="box-tools pull-right">
                <button type="button" class="btn btn-success btn-newOne" onclick="window.location.href='/company/'+ ${type} +'/cou'"><i class="fa fa-plus"></i> ${t("new")}</button>
                <button type="button" class="btn btn-export" v-on:click="export">${t("export")}</button>
            </div>
        </form>

    </div>
    <div class="panel-body">
        <table id="dataTable">

        </table>
    </div>
</div>
</@layout.archiveTemplate>