<#import "archiveLayout.ftl" as layout />
<#include 'func.ftl'>

<#assign headerContent>
<link rel="stylesheet" href="/lib/bootstrap-table/bootstrap-table.css">
<link rel="stylesheet" href="/lib/datetimepicker/bootstrap-datetimepicker.css">
<link rel="stylesheet" href="/lib/sweetalert/sweetalert.css">
</#assign>

<@layout.archiveTemplate initScript='js/maintenancePlanCou' header=headerContent title=''>
<#--<input id="type" type="hidden" value="${type}">-->
<div class="panel panel-default" id="maintenancePlanCou">
    <div class="panel-heading" v-html="isEdit ? '编辑维保计划':'添加维保计划'"></div>
    <div class="panel-body">
        <form class="form-horizontal">
            <div class="form-group">
                <label class="col-sm-4 control-label">合同编号</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" v-model="maintenancePlan.upkeepContractNumber" id="upkeepContractNumber" readonly>
                    <input type="hidden"  id="id" v-model="maintenancePlan.id" />
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-4 control-label">电梯</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" v-model="maintenancePlan.number" id="number">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-4 control-label">维保人:</label>
                <div class="col-sm-3">
                    <select class="form-control" id="maintenanceManId" v-model="maintenancePlan.maintenanceManId">
                        <option value="-1" selected="selected">${t("pleaseSelectMaintenance")}</option>
                        <#list maintenances as maintenance>
                            <option value="${maintenance.id}">${maintenance.name}</option>
                        </#list>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-4 control-label">类型</label>
                <div class="col-sm-3">
                    <select  class="form-control" id="planType" v-model="maintenancePlan.planType">
                        <option value="10">半月保</option>
                        <option value="20">季度保</option>
                        <option value="30">半年保</option>
                        <option value="40">年度保</option>
                        <#--<option value="50">急修</option>-->
                        <#--<option value="60">维修</option>-->
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-4 control-label">开始时间</label>
                <div class="col-sm-4">
                    <div class="input-group date" id="datetimePicker">
                        <input type="text" class="form-control" name="datetimePicker" id="productTimePicker" v-model="maintenancePlan.planTime" />
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-4 control-label">结束时间</label>
                <div class="col-sm-4">
                    <div class="input-group date" id="datetimePicker">
                        <input type="text" class="form-control" name="datetimePicker" id="productTimePicker"  v-model="maintenancePlan.planEndTime"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-3 col-sm-4">
                    <button type="submit" class="btn btn-primary" v-on:click="confirm">${t("confirm")}</button>
                    <button type="button" class="btn btn-default col-sm-offset-2" onclick="window.location.href='/maintenancePlan'">${t("cancel")}</button>
                    <#if hasPermission("upkeepContractPlan:delete")>
                    <button type="button" class="btn btn-default col-sm-offset-2" v-if="isEdit" v-on:click="deleteIt">${t("delete")}</button>
                    </#if>
                </div>
            </div>

        </form>


    </div>






</div>





</@layout.archiveTemplate>