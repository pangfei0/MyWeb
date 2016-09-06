<#import "staticLayout.ftl" as layout>
<#include "func.ftl"/>

<#assign headerContent>
<link rel="stylesheet" href="/css/archive.css">
<link rel="stylesheet" href="/css/workBillBoard.css">
<link rel="stylesheet" href="/lib/bootstrap-table/bootstrap-table.css">
<link rel="stylesheet" href="/lib/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="/lib/datetimepicker/bootstrap-datetimepicker.css">
<link rel="stylesheet" href="/lib/sweetalert/sweetalert.css">
</#assign>

<#assign footerContent>
<#--modal-->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Modal title</h4>
            </div>
            <div class="modal-body">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save changes</button>
            </div>
        </div>
    </div>
</div>
</#assign>

<@layout.archiveTemplate initScript="js/satisfactionStatic" header=headerContent  title=''>
<div class="panel panel-default">
    <div class="panel-heading">满意度统计</div>
    <div class="panel-body">
        <form class="search-container search-form">
            <div class="search-field">
                <span>员工姓名:</span>
                <input type="text" style="width: 120px" id="name" class="form-control">
            </div>
            <div class="search-field">
                <span>维保站:</span>
                <input type="text" style="width: 120px" id="maintenanceArea" class="form-control">
            </div>
            <div class="search-field">
                <span>区域:</span>
                <input type="text" style="width: 120px" id="areas" class="form-control">
            </div>

            <button type="button" class="btn btn-primary btn-search" v-on:click="search"><i class="fa fa-search"></i> ${t("search")}</button>
            <button type="reset" class="btn btn-default btn-reset" v-on:click="search | debounce 50">${t("reset")}</button>

            <div class="tool-container pull-right">
                <button type="button" class="btn btn-export" v-on:click="satisfactionExport"><span></span>导出</button>
            </div>

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
<div class="hidden" id="table-row-template">
    <#if hasPermission("upkeepContractPlan:delete")>
        <i class="fa fa-trash-o delete-bill" data-toggle="tooltip" data-placement="right" onclick="deletePlan('{id}');" title='${t("delete")}'></i>
    </#if>
</div>
</@layout.archiveTemplate>