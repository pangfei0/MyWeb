<#import "archiveLayout.ftl" as layout />
<#include "func.ftl">

<#assign headerContent>

<link rel="stylesheet" href="/lib/sweetalert/sweetalert.css">
</#assign>

<@layout.archiveTemplate initScript="js/maintainerEmployeeAdd" header=headerContent title="">
<div id="maintainerEmployeeAdd">
    <div class="panel panel-default">
        <div class="panel-heading">
        ${t("baseInfo")}
        </div>
        <div class="panel-body">
            <div class="form-horizontal">
                <div class="form-group">
                    <label class="col-sm-4 control-label">${t("numberOfMaintainerEmployee")}:</label>

                    <div class="col-sm-4">
                        <input type="text" id="number" style="width:95%;display:inline;" class="form-control" v-model="maintainerEmployee.number">
                        <label style="color:red;">*</label>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-4 control-label">人员姓名:</label>

                    <div class="col-sm-4">
                        <input type="text" id="name"  style="width:95%;display:inline;"class="form-control" v-model="maintainerEmployee.name">
                        <label style="color:red;">*</label>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-4 control-label">${t("telephoneOfMaintainerEmployee")}:</label>

                    <div class="col-sm-4">
                        <input type="text" class="form-control" v-model="maintainerEmployee.telephone">
                    </div>

                </div>
                <div class="form-group">
                    <label class="col-sm-4 control-label">${t("maintainerOfMaintainerEmployee")}:</label>

                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="maintainerId" v-model="maintainerEmployee.maintainerName"
                               autocomplete="off" data-provide="typeahead" onchange="getManager()">
                    </div>

                </div>
                <div class="form-group">
                    <label class="col-sm-4 control-label">${t("managerOfMaintainerEmployee")}:</label>

                    <div class="col-sm-4">
                        <select id="manager" class="form-control" v-model="maintainerEmployee.parentId">
                            <option value="-1" selected="selected">${t("pleaseSelectMaintenance")}</option>
                        </select>
                    </div>

                </div>
                <div class="form-group">
                    <label class="col-sm-4 control-label">维保区域:</label>

                    <div class="col-sm-4">
                        <input type="text" class="form-control" v-model="maintainerEmployee.region">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-4 control-label">维保站:</label>

                    <div class="col-sm-4">
                       <input type="text" class="form-control" v-model="maintainerEmployee.station">
                    </div>
                </div>
            </div>
        </div>

    </div>
    <div class="panel panel-default">
        <div class="panel-heading">
        ${t("maintainerEmployeeRepairLevelInfo")}
        </div>
        <div class="panel-body">
            <div class="form-horizontal">
                <div>
                    <#list brands as brand>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">${brand.elevatorName}</label>

                            <div class="col-sm-4">
                                <select class="form-control">
                                    <option selected="selected">请选择维保等级</option>
                                    <#list brand.repairLevel as repairLevel>
                                        <option id="${repairLevel.id}"
                                                value="${repairLevel.id}">${repairLevel.name}</option>
                                    </#list>
                                </select>
                            </div>
                        </div>
                    </#list>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-3 col-sm-4">
                        <button type="button" class="btn btn-default"
                                v-on:click="upConfirm">${t("confirm")}</button>
                        <button type="button" class="btn btn-default col-sm-offset-1"
                                v-on:click="cancel">${t("cancel")}</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</@layout.archiveTemplate>