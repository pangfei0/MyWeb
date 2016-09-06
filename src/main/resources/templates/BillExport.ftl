<#import "maintainLayout.ftl" as layout />
<#include "func.ftl"/>

<#assign headerContent>
<link rel="stylesheet" href="/lib/datetimepicker/bootstrap-datetimepicker.css">
<link rel="stylesheet" href="/lib/sweetalert/sweetalert.css">
<link rel="stylesheet" href="/css/archive.css">
<link rel="stylesheet" href="/lib/bootstrap-table/bootstrap-table.css">
<link rel="stylesheet" href="/lib/bootstrap/css/bootstrap.min.css">

</#assign>

<#assign footerContent>

</#assign>

<@layout.maintainTemplate initScript="js/workBillExport" header=headerContent footer=footerContent title='${t("serviceManagement")}'>
<div class="panel panel-default" id="workBillExport">
    <div class="panel-heading">工单详细</div>
    <div class="panel-body">
        <div class="form-horizontal">
            <div class="form-group">
                <label class="col-sm-2 control-label">时间段:</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control form_datetime"  id="starttime1" v-model="workBill.startTime1">
                </div>
                <div class="col-sm-1">

                </div>
                <div class="col-sm-3">
                    <input type="text"  id="starttime2"  class="form-control"  v-model="workBill.startTime2">
                </div>

            </div>

            <div class="form-group">
                <label class="col-sm-2 control-label">电梯工号:</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control"  v-model="workBill.elevatorNumber">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">工单类型:</label>
                <div class="col-sm-4">
                    <select class="form-control" id="billCategory" v-model="workBill.billCategory">
                        <option value="20">维保工单</option>
                        <option value="10">维修工单</option>
                        <option value="30">急修工单</option>
                    </select>
                </div>
            </div>
            <div  class="form-group">
                <div  class=" col-sm-offset-4 col-sm-2">
                    <button id="aftersend" type="button" class="btn btn-success" v-on:click="export"> <i class="icon-download"></i>导出工单</button>
                </div>
                <div  class=" col-sm-offset-1 col-sm-2">
                    <button id="aftersend" type="button" class="btn btn-success" v-on:click="back"> <i class="icon-download"></i> 取消操作</button>
                </div>
            </div>
        </div>
        </div>
        </div>

    </div>

    </div>
</div>
</@layout.maintainTemplate>