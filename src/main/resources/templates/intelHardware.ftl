<#import "archiveLayout.ftl" as layout />
<#include "func.ftl">

<#assign headerContent>

</#assign>

<@layout.archiveTemplate initScript="js/intelHardware" header=headerContent title="">
<div class="panel panel-default">
    <div class="panel-heading">${t('intelHardwares')}</div>
    <div class="panel-body">
        <form class="search-container search-form">
            <div class="search-field">
                <span>${t("hardwareNumber")}:</span>
                <input type="text" style="width:120px;" id="intelHardwareNumber" class="form-control"/>
            </div>
            <div class="search-field">
                <span>${t("numberOfElevator")}:</span>
                <input type="text" style="width:120px;" id="number" class="form-control"/>
            </div>
            <div class="search-field">
                <span>${t("addressOfElevator")}:</span>
                <input type="text" style="width:120px;" id="alias" class="form-control"/>
            </div>
            <button type="button" class="btn btn-primary btn-search" v-on:click="search"><i
                    class="fa fa-search"></i> ${t("search")}</button>
            <button type="reset" class="btn btn-default btn-reset"
                    v-on:click="search | debounce 50">${t("reset")}</button>
            <button type="button" class="btn btn-success btn-newOne" onclick="window.location.href='/collector/cou'"><i class="fa fa-plus"></i> ${t("new")}</button>
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