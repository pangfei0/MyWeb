<#import "maintainLayout.ftl" as layout />
<#include 'func.ftl'>

<#assign headerContent>
<link rel="stylesheet" href="/css/archive.css">
<link rel="stylesheet" href="/lib/bootstrap-table/bootstrap-table.css">
<link rel="stylesheet" href="/lib/datetimepicker/bootstrap-datetimepicker.css">
<link rel="stylesheet" href="/lib/bootstrap/css/bootstrap.min.css">
</#assign>

<@layout.maintainTemplate initScript="js/maintenancePersonnelBoard" header=headerContent title='${t("serviceManagement")}'>
<div class="panel panel-default">
    <div class="panel-heading">${t('staffBoard')}</div>
    <div class="panel-body">
        <form class="search-container search-form">
            <div class="search-field">
                <span>${t("numberOfMaintenancePersonnel")}:</span>
                <input type="text" style="width: 120px" id="number" class="form-control">
            </div>
            <div class="search-field">
                <span>${t("nameOfMaintenancePersonnel")}:</span>
                <input type="text" style="width: 120px" id="name" class="form-control">
            </div>
            <div class="search-field">
                <span>${t("status")}:</span>
                <select id="search_currentState" class="form-control" onchange="setCurrentStateValue()">
                    <option value="-1">${t("pleaseSelect")}</option>
                    <option value="10">${t("in-service")}</option>
                    <option value="20">${t("onStandby")}</option>
                    <option value="30">${t("offline")}</option>
                </select>
                <input type="hidden" style="width: 120px" id="currentState" class="form-control">
            </div>
            <button type="button" class="btn btn-primary btn-search" v-on:click="search"><i
                    class="fa fa-search"></i> ${t("search")}</button>
            <button type="reset" class="btn btn-default btn-reset"
                    v-on:click="search | debounce 50">${t("reset")}</button>
            <#if hasPermission("export")>
                <div class="box-tools pull-right">
                    <button type="button" class="btn btn-export" v-on:click="export"><span></span>${t("export")}</button>
                </div>
            </#if>
            <div style="padding:10px 0 0 0px;">
            <div class="input-group date" id="datetimePicker1" style="width: 200px;">
                <input type="text" class="form-control" name="datetimePicker" id="startTime"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
            </div>
                <div class="input-group date" id="datetimePicker2" style="width: 200px;">
                <input type="text" class="form-control" name="datetimePicker" id="endTime"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                </div>
            </div>
        </form>
    </div>
    <div class="panel-body">
        <table id="dataTable">

        </table>
    </div>
</div>
</@layout.maintainTemplate>