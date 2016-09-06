<#import "archiveLayout.ftl" as layout />
<#include 'func.ftl'>

<#assign headerContent>
<link rel="stylesheet" href="/css/archive.css">
<link rel="stylesheet" href="/lib/bootstrap-table/bootstrap-table.css">
<link rel="stylesheet" href="/lib/bootstrap/css/bootstrap.min.css">
</#assign>

<@layout.archiveTemplate initScript="js/elevator" header=headerContent title='${t("mainDataModule")}'>
<div class="panel panel-default">
    <div class="panel-heading">${t('elevators')}</div>
    <div class="panel-body">
        <form class="search-container search-form">
            <div class="search-field">
                <span>${t("numberOfElevator")}:</span>
                <input type="text" style="width:120px;"  data-provide="typeahead" autocomplete="off" id="number" class="form-control"/>
            </div>
            <div class="search-field">
                <span>${t("addressOfElevator")}:</span>
                <input type="text" style="width:120px;"  data-provide="typeahead" autocomplete="off" id="address" class="form-control"/>
            </div>
            <div class="search-field">
                <span>${t("projectName")}:</span>
                <input type="text" style="width:120px;"  data-provide="typeahead" autocomplete="off" id="projectName" class="form-control"/>
            </div>
            <button type="button" class="btn btn-primary btn-search" v-on:click="search"><i
                    class="fa fa-search"></i> ${t("search")}</button>
            <button type="reset" class="btn btn-default btn-reset"
                    v-on:click="search | debounce 50">${t("reset")}</button>
            <div class="box-tools pull-right">
                <#if hasPermission("elevator:new")>
                    <button type="button" class="btn btn-success btn-newOne"
                            onclick="window.location.href='/elevator/add'"><i class="fa fa-plus"></i> ${t("new")}
                    </button>
                </#if>
                <#if hasPermission("export")>
                    <button type="button" class="btn btn-export" v-on:click="export">${t("export")}</button>
                </#if>
            </div>
        </form>

    </div>
    <div class="panel-body">
        <table id="dataTable">

        </table>
    </div>
</div>
</@layout.archiveTemplate>