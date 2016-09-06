<#import "maintainLayout.ftl" as layout>
<#include "func.ftl"/>

<#assign headerContent>
<link rel="stylesheet" href="/css/archive.css">
<link rel="stylesheet" href="/css/workBillBoard.css">
<link rel="stylesheet" href="/lib/bootstrap-table/bootstrap-table.css">
<link rel="stylesheet" href="/lib/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="/lib/sweetalert/sweetalert.css">
</#assign>

<#assign footerContent>
<#--modal-->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
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

<@layout.maintainTemplate initScript="js/workBillBoard" header=headerContent footer=footerContent title='${t("serviceManagement")}'>
<div class="panel panel-default">
    <div class="panel-heading">${t('wokBillBoard')}</div>
    <div class="panel-body">
        <form class="search-container search-form">
            <div class="search-field">
                <span>${t("numberOfWorkBill")}:</span>
                <input type="text" style="width: 120px" id="billNumber" class="form-control">
            </div>
            <div class="search-field">
                <span>${t("buildingOfWorkBill")}:</span>
                <input type="text" style="width: 120px" id="alias" class="form-control">
            </div>
            <div class="search-field">
                <span>${t("elevatorOfWorkBill")}:</span>
                <input type="text" style="width: 120px" id="enumber" class="form-control">
            </div>

            <button type="button" class="btn btn-primary btn-search" v-on:click="search"><i
                    class="fa fa-search"></i> ${t("search")}</button>
            <button type="reset" class="btn btn-default btn-reset"
                    v-on:click="search | debounce 50">${t("reset")}</button>
            <div class="tool-container pull-right">
                <#if hasPermission("workbillBoard:new")>
                    <button type="button" class="btn btn-success btn-newOne"
                            onclick="window.location.href='/workBill/cou'"><i class="fa fa-plus"></i>&nbsp;${t("new")}
                    </button>
                </#if>
                <#if hasPermission("export")>
                <button type="button" class="btn btn-export" v-on:click="exportWorkbill"><span></span>${t("report")}
                </button>
            </#if>
            </div>
            <div style="padding:10px 0 0 0px;">
                <span>${t("actor")}:</span>
                <input type="text" style="width: 120px" id="actor" class="form-control search">
                <span>${t("status")}:</span>
                <select id="search_billstatus" class="form-control" onchange="setSearch_billstatusValue()">
                    <option value="-1">${t("pleaseSelect")}</option>
                    <option value="5">${t("no-distribute")}</option>
                    <option value="10">${t("no-receive")}</option>
                    <option value="20">${t("ongoing")}</option>
                    <option value="30">${t("pause")}</option>
                    <option value="40">${t("refuse")}</option>
                    <option value="45">${t("refuseDone")}</option>
                    <option value="50">${t("done")}</option>
                    <option value="55">转维修</option>
                    <option value="60">待评价</option>
                </select>
                <input type="hidden" style="width: 120px" id="billstatus" class="form-control">

                <span>工单类型:</span>
                <select id="search_billcategory" class="form-control" onchange="setSearch_billcategoryValue()">
                    <option value="-1">${t("pleaseSelect")}</option>
                    <option value="10">维修</option>
                    <option value="20">维保</option>
                    <option value="30">急修</option>
                </select>
                <input type="hidden" style="width: 120px" id="billCategory" class="form-control">

            </div>
        </form>
    </div>
    <div class="panel-body">
        <table id="dataTable">

        </table>
    </div>

</div>
<div class="hidden" id="table-row-template">
    <#if hasPermission("workbillBoard:delete")>
        <i class="fa fa-trash-o delete-bill" data-toggle="tooltip" data-placement="right" onclick="deleteWorkBill('{id}');" title='${t("delete")}'></i>
    </#if>
</div>
</@layout.maintainTemplate>