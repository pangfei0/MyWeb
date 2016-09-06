<#import "archiveLayout.ftl" as layout />
<#include "func.ftl">

<#assign headerContent>
<link rel="stylesheet" href="/css/archive.css">
<link rel="stylesheet" href="/lib/bootstrap-table/bootstrap-table.css">
<link rel="stylesheet" href="/lib/bootstrap/css/bootstrap.min.css">
</#assign>

<@layout.archiveTemplate initScript="js/elevatorBrand" header=headerContent title="">
<div class="panel panel-default">
    <div class="panel-heading">${t('elevatorBrands')}</div>
    <div class="panel-body" style="height:54px;">
        <form class="search-container search-form">
            <div class="tool-container pull-right">
                <#if hasPermission("elevatorBrand:new")>
                    <button type="button" class="btn btn-success btn-newOne"
                            onclick="window.location.href='/elevatorBrand/cou'"><i class="fa fa-plus"></i> ${t("new")}
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