<#import "../archiveLayout.ftl" as layout />
<#include "../func.ftl">

<#assign headerContent>
<link rel="stylesheet" href="/lib/datetimepicker/bootstrap-datetimepicker.css">
<link rel="stylesheet" href="/lib/sweetalert/sweetalert.css">
</#assign>

<@layout.archiveTemplate initScript="js/upkeepContract/planBathAdd" header=headerContent title="">
<div id="planPath">
    <div class="panel panel-default">
        <div class="panel-heading">规则设定</div>
        <div class="panel-body">
            <div class="form-horizontal">
                <div class="form-group">
                    <label class="col-sm-2 control-label">开始时间:</label>
                    <input type="hidden"  id="upkeepContractId" class="form-control" value="${upkeepContractId}">
                    <div class="col-sm-3">
                        <input type="text" id="startTime"  class="form-control"  v-model="bath.startTime">
                    </div>
                    <div class="col-sm-1">
                    </div>

                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label">维保人:</label>
                    <div class="col-sm-3">
                        <select class="form-control" id="maintenanceMan" v-model="bath.maintenanceMan">
                            <option value="-1" selected="selected">请选择维保人员</option>
                            <#list maintenances as maintenance>
                                <option value="${maintenance.id}">${maintenance.name}</option>
                            </#list>
                        </select>
                    </div>

                    <button type="button" class="btn btn-primary btn-search" v-on:click="addElevatorPage"><i class="fa fa-search"></i> ${t("addElevator")}</button>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label">完成所需天数:</label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" name="days" v-model="bath.days" style="width: 50%">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label">即将第几次半月保:</label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" name="daysEd" v-model="bath.daysEd" style="width: 50%">
                    </div>
                </div>

            </div>
        </div>
    </div>
    <div id="elevatorInfoUpdate" class="panel-body"  style="display: none;">

                <div class="panel-body">
                    <table id="elevatorTable">
                    </table>
                </div>

        <div class="panel-body">
            <form class="search-container search-form">
                <button type="button" class="btn btn-primary btn-search" v-on:click="elevatorCancel"><i class="fa fa-search"></i> ${t("cancel")}</button>
                <button type="button" class="btn btn-primary btn-search" v-on:click="addElevator"><i class="fa fa-search"></i> 生成批次</button>
            </form>
        </div>
    </div>
</div>
</@layout.archiveTemplate>