<#import "archiveLayout.ftl" as layout>
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

<@layout.archiveTemplate initScript="js/maintenancePlanBathBoard" header=headerContent  title=''>
<div class="panel panel-default">
    <div class="panel-heading">维保批次管理</div>
    <div class="panel-body">
        <form class="search-container search-form">
            <div class="search-field">
                <span>电梯:</span>
                <input type="text" style="width: 120px" id="elevatorNumber" class="form-control">
            </div>
            <div class="search-field">
                <span>合同编号:</span>
                <input type="text" style="width: 120px" id="upkeepContractNumber" class="form-control">
            </div>
            <div class="search-field">
                <span>维保人:</span>
                <input type="text" style="width: 120px" id="maintenanceMan" class="form-control">
            </div>


            <button type="button" class="btn btn-primary btn-search" v-on:click="search"><i class="fa fa-search"></i> ${t("search")}</button>
            <button type="reset" class="btn btn-default btn-reset" v-on:click="search | debounce 50">${t("reset")}</button>
        <#--<div class="tool-container pull-right">-->
        <#--<button type="button" class="btn btn-success btn-newOne" onclick="window.location.href='/workBill/cou'"><i class="fa fa-plus"></i>&nbsp;${t("new")}</button>-->
        <#--<button type="button" class="btn btn-export" v-on:click="exportWorkbill"><span></span>${t("report")}</button>-->
        <#--</div>-->
            <#--<div  style="padding:10px 0 0 0px;">-->
            <#--&lt;#&ndash;<span>计划时间:</span>&ndash;&gt;-->
            <#--&lt;#&ndash;<input type="text" style="width: 120px" id="planTime" class="form-control search">&ndash;&gt;-->
            <#--&lt;#&ndash;<div class="col-sm-3">&ndash;&gt;-->
            <#--&lt;#&ndash;<input type="text" class="form-control form_datetime"  id="planTime">&ndash;&gt;-->
            <#--&lt;#&ndash;</div>&ndash;&gt;-->
                <#--<span>状态:</span>-->
                <#--<select id ="search_status" class="form-control" onchange="setSearch_planStatusValue()">-->
                    <#--<option value="-1">请选择</option>-->
                    <#--<option value="10">未开始</option>-->
                    <#--<option value="20">已开始</option>-->
                    <#--<option value="30">已完成</option>-->
                <#--</select>-->
                <#--<input type="hidden" style="width: 120px" id="status" class="form-control">-->
            <#--</div>-->
        </form>
    </div>
    <div class="panel-body">
        <table id="dataTable">

        </table>
    </div>

</div>
<div class="hidden" id="table-row-template">
    <#if hasPermission("upkeepContractPlanBath:delete")>
        <i class="fa fa-trash-o delete-bill" data-toggle="tooltip" data-placement="right" onclick="deletePlanBath('{id}');" title='${t("delete")}'></i>
    </#if>
</div>
</@layout.archiveTemplate>