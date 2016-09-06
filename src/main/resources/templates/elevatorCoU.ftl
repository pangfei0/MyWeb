<#import "archiveLayout.ftl" as layout />
<#include 'func.ftl'>

<#assign headerContent>
<#--<link rel="stylesheet" href="/css/elevatorCoU.css">-->
<link rel="stylesheet" href="/lib/bootstrap-table/bootstrap-table.css">
<link rel="stylesheet" href="/lib/datetimepicker/bootstrap-datetimepicker.css">
<link rel="stylesheet" href="/lib/sweetalert/sweetalert.css">
</#assign>

<@layout.archiveTemplate initScript='js/elevatorCoU' header=headerContent title=''>
<div id="elevatorCoU">
    <div class="panel panel-default">
        <div class="panel-heading">
        ${t("baseInfo")}
            <#if hasPermission("elevator:edit")>
                <button type="button" class="btn btn-default pull-right" id="baseInfoEditButton"
                        v-on:click="baseInfoEdit">${t("edit")}</button>
            </#if>
        </div>
        <div class="panel-body">
            <div id="baseInfoShow">
                <div class="row">
                    <label class="col-md-2 control-label">${t("numberOfElevator")}:</label>

                    <div class="col-md-4" v-html="elevator.number"></div>
                    <label class="col-md-2 control-label">设备编号:</label>

                    <div class="col-md-4" v-html="elevator.equipmentNumber"></div>
                </div>
                <div class="row">
                    <label class="col-md-2 control-label">${t("alias")}:</label>

                    <div class="col-md-4" v-html="elevator.alias"></div>
                    <label class="col-md-2 control-label">项目名称:</label>

                    <div class="col-md-4" v-html="elevator.projectName"></div>
                </div>
                <div class="row">
                    <label class="col-md-2 control-label">${t("regCodeOfElevator")}:</label>

                    <div class="col-md-4" v-html="elevator.regCode"></div>
                    <label class="col-md-2 control-label">${t("elevatorType")}:</label>

                    <div class="col-md-4" v-html="elevator.elevatorType"></div>
                </div>
                <div class="row">
                    <label class="col-md-2 control-label">${t("controllerTypeOfElevator")}:</label>

                    <div class="col-md-4" v-html="elevator.controllerType"></div>
                    <label class="col-md-2 control-label">${t("intelHardwareNumberOfElevator")}:</label>

                    <div class="col-md-4" v-html="elevator.intelHardwareNumber"></div>
                </div>
                <div class="row">
                    <label class="col-md-2 control-label">${t("ratedWeight")}:</label>

                    <div class="col-md-4" v-html="elevator.ratedWeight"></div>
                    <label class="col-md-2 control-label">${t("ratedSpeed")}:</label>

                    <div class="col-md-4" v-html="elevator.ratedSpeed"></div>
                </div>
                <div class="row">
                    <label class="col-sm-2 control-label">${t("productionTime")}</label>

                    <div class="col-sm-4" v-html="elevator.productionTime"></div>
                    <label class="col-sm-2 control-label">${t("deliverTime")}</label>

                    <div class="col-sm-4" v-html="elevator.deliverTime"></div>
                </div>
                <div class="row">
                    <label class="col-md-2 control-label">${t("controlMode")}:</label>

                    <div class="col-md-4" v-html="elevator.controlMode"></div>
                    <label class="col-md-2 control-label">${t("hoistingHeight")}:</label>

                    <div class="col-md-4" v-html="elevator.hoistingHeight"></div>
                </div>
                <div class="row">
                    <label class="col-md-2 control-label">${t("station")}:</label>

                    <div class="col-md-4" v-html="elevator.station"></div>
                    <label class="col-md-2 control-label">${t("brand")}:</label>

                    <div class="col-sm-4"  v-html="elevator.brandName">

                    </div>
                </div>
                <div class="row">
                    <label class="col-md-2 control-label">经度:</label>

                    <div class="col-md-4" v-html="elevator.lng"></div>
                    <label class="col-md-2 control-label">纬度:</label>

                    <div class="col-md-4" v-html="elevator.lat"></div>
                </div>
                <div class="row">
                    <label class="col-md-2 control-label">${t("addressOfElevator")}:</label>

                    <div class="col-md-4" v-html="elevator.address"></div>
                </div>
            </div>
            <div id="baseInfoUpdate" style="display: none;">
                <div class="row">
                    <label class="col-md-2 control-label">${t("numberOfElevator")}:</label>

                    <div class="col-md-4"><input type="text" class="form-control" v-model="elevator.number"
                                                 id="number"></div>
                    <label class="col-md-2 control-label">设备编号:</label>

                    <div class="col-md-4"><input type="text" class="form-control" v-model="elevator.equipmentNumber">
                    </div>
                </div>
                <div class="row">
                    <label class="col-md-2 control-label">${t("alias")}:</label>

                    <div class="col-sm-4"><input type="text" class="form-control" v-model="elevator.alias"></div>
                    <label class="col-md-2 control-label">项目名称:</label>

                    <div class="col-md-4"><input type="text" class="form-control" v-model="elevator.projectName"></div>
                </div>
                <div class="row">
                    <label class="col-md-2 control-label">${t("regCodeOfElevator")}:</label>

                    <div class="col-sm-4"><input type="text" class="form-control" v-model="elevator.regCode"></div>
                    <label class="col-md-2 control-label">${t("elevatorType")}:</label>

                    <div class="col-sm-4">
                        <select class="form-control" id="elevatorType" v-model="elevator.elevatorType">
                            <option value="直梯">直梯</option>
                            <option value="扶梯">扶梯</option>
                            <option value="自动人行道">自动人行道</option>
                        </select>
                    </div>
                </div>
                <div class="row">
                    <label class="col-md-2 control-label">${t("controllerTypeOfElevator")}:</label>

                    <div class="col-md-4"><input type="text" class="form-control" v-model="elevator.controllerType">
                    </div>
                    <label class="col-md-2 control-label">${t("intelHardwareNumberOfElevator")}:</label>

                    <div class="col-sm-4"><input type="text" class="form-control"
                                                 v-model="elevator.intelHardwareNumber"></div>
                </div>
                <div class="row">
                    <label class="col-md-2 control-label">${t("station")}:</label>

                    <div class="col-md-4"><input type="text" class="form-control" v-model="elevator.station"></div>
                    <label class="col-md-2 control-label">${t("brand")}:</label>

                    <div class="col-sm-4"><select class="form-control" v-model="elevator.brandId">
                        <#list brands as brand>
                            <option value="${brand.id}">${brand.name}</option>
                        </#list>
                    </select></div>
                </div>
                <div class="row">
                    <label class="col-md-2 control-label">${t("ratedWeight")}:</label>

                    <div class="col-md-4"><input type="text" class="form-control" v-model="elevator.ratedWeight"></div>
                    <label class="col-md-2 control-label">${t("ratedSpeed")}:</label>

                    <div class="col-sm-4"><input type="text" class="form-control" v-model="elevator.ratedSpeed"></div>
                </div>
                <div class="row">
                    <label class="col-sm-2 control-label">${t("productionTime")}</label>

                    <div class="col-sm-4">
                        <div class="input-group date" id="datetimePicker">
                            <input type="text" class="form-control" name="datetimePicker" id="productTimePicker"
                                   v-model="elevator.productionTime"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                        </div>
                    </div>
                    <label class="col-sm-2 control-label">${t("deliverTime")}</label>

                    <div class="col-sm-4">
                        <div class="input-group date" id="datetimePicker">
                            <input type="text" class="form-control" name="datetimePicker" id="deliverTimePicker"
                                   v-model="elevator.deliverTime"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <label class="col-sm-2 control-label">${t("controlMode")}</label>

                    <div class="col-sm-4">
                        <input type="text" class="form-control" v-model="elevator.controlMode">
                    </div>
                    <label class="col-sm-2 control-label">${t("hoistingHeight")}</label>

                    <div class="col-sm-4">
                        <input type="text" class="form-control" v-model="elevator.hoistingHeight">
                    </div>
                </div>
                <div class="row">
                    <label class="col-md-2 control-label">经度:</label>

                    <div class="col-md-4"><input type="text" class="form-control" v-model="elevator.lng"></div>
                    <label class="col-md-2 control-label">纬度:</label>

                    <div class="col-sm-4"><input type="text" class="form-control" v-model="elevator.lat"></div>
                </div>
                <div class="row">
                    <label class="col-md-2 control-label">${t("addressOfElevator")}:</label>

                    <div class="col-md-4"><input type="text" class="form-control" v-model="elevator.address"
                                                 id="address"></div>
                    <ul class="nav nav-pills" role="tablist">
                        <button type="button" class="btn btn-default"
                                v-on:click="locateElevator">${t("gpsMap")}</button>
                    </ul>
                    <div class="form-group" style="height:530">
                        <div id="map"></div>
                    </div>
                </div>

                <div class="col-sm-offset-4 col-sm-4">
                    <button type="button" class="btn btn-primary" v-on:click="baseInfoConfirm">${t("confirm")}</button>
                    <button type="button" class="btn btn-success col-sm-offset-2"
                            v-on:click="baseInfoCancel">${t("cancel")}</button>
                <#if hasPermission("elevator:delete")>
                <button type="button" class="btn btn-default col-sm-offset-2" v-on:click="deleteIt">${t("delete")}</button>
                </#if>
                </div>
            </div>

        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading">
            关联单位
            <#if hasPermission("elevator:edit")>
                <button type="button" class="btn btn-default pull-right" id="stateInfoEditButton"
                        v-on:click="stateInfoEdit">${t("edit")}</button>
            </#if>
        </div>
        <div class="panel-body">
            <div id="stateInfoShow">
                <div class="row">
                    <label class="col-sm-2 control-label">${t("useCompany")}:</label>

                    <div class="col-sm-4" v-html="elevator.userCompanyName"></div>
                    <label class="col-sm-2 control-label">${t("installCompany")}:</label>

                    <div class="col-sm-4" v-html="elevator.installCompanyName"></div>
                </div>
                <div class="row">
                    <label class="col-sm-2 control-label">${t("manufacturer")}:</label>

                    <div class="col-sm-4" v-html="elevator.manufacturerName"></div>
                    <label class="col-sm-2 control-label">物业单位:</label>

                    <div class="col-sm-4" v-html="elevator.ownerCompanyName"></div>
                </div>
                <div class="row">
                    <label class="col-sm-2 control-label">监管机构:</label>

                    <div class="col-sm-4" v-html="elevator.regulatorName"></div>
                    <label class="col-sm-2 control-label">个人用户:</label>

                    <div class="col-sm-4" v-html="elevator.personalName"></div>
                </div>
                <div class="row">
                    <label class="col-sm-2 control-label">其他类型:</label>

                    <div class="col-sm-4" v-html="elevator.othersName"></div>
                    <label class="col-sm-2 control-label">${t("maintainer")}:</label>

                    <div class="col-sm-4" v-html="elevator.maintainerName"></div>
                </div>
                <div class="row">
                    <label class="col-sm-2 control-label">${t("maintenance")}:</label>

                    <div class="col-sm-4" v-html="elevator.maintenanceManager"></div>
                    <label class="col-sm-2 control-label">${t("maintenancePerson")}:</label>

                    <div class="col-sm-4" v-html="elevator.maintenanceName"></div>
                </div>
            </div>
            <div id="stateInfoUpdate" style="display: none;">
                <div class="row">
                    <label class="col-sm-2 control-label">${t("useCompany")}</label>

                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="userCompanyId" v-model="elevator.userCompanyName"
                               autocomplete="off" data-provide="typeahead">
                    </div>
                    <ul class="nav nav-pills" role="tablist">
                        <li role="presentation"><a href="/companyMaintain/add" target="_blank">找不到使用单位？</a></li>
                    </ul>
                </div>
                <div class="row">
                    <label class="col-sm-2 control-label">${t("installCompany")}</label>

                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="installCompanyId"
                               v-model="elevator.installCompanyName" autocomplete="off" data-provide="typeahead">

                    </div>
                    <ul class="nav nav-pills" role="tablist">
                        <li role="presentation"><a href="/companyMaintain/add" target="_blank">找不到安装单位？</a></li>
                    </ul>
                </div>
                <div class="row">
                    <label class="col-sm-2 control-label">${t("manufacturer")}</label>

                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="manufacturerId" v-model="elevator.manufacturerName"
                               autocomplete="off" data-provide="typeahead">

                    </div>
                    <ul class="nav nav-pills" role="tablist">
                        <li role="presentation"><a href="/companyMaintain/add" target="_blank">找不到制造单位？</a></li>
                    </ul>
                </div>
                <div class="row">
                    <label class="col-sm-2 control-label">物业单位</label>

                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="ownerCompanyId" v-model="elevator.ownerCompanyName"
                               autocomplete="off" data-provide="typeahead">

                    </div>
                    <ul class="nav nav-pills" role="tablist">
                        <li role="presentation"><a href="/companyMaintain/add" target="_blank">找不到物业单位？</a></li>
                    </ul>
                </div>
                <div class="row">
                    <label class="col-sm-2 control-label">监管机构</label>

                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="regulatorId" v-model="elevator.regulatorName"
                               autocomplete="off" data-provide="typeahead">

                    </div>
                    <ul class="nav nav-pills" role="tablist">
                        <li role="presentation"><a href="/companyMaintain/add" target="_blank">找不到监管机构？</a></li>
                    </ul>
                </div>
                <div class="row">
                    <label class="col-sm-2 control-label">个人用户</label>

                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="personalId" v-model="elevator.personalName"
                               autocomplete="off" data-provide="typeahead">

                    </div>
                    <ul class="nav nav-pills" role="tablist">
                        <li role="presentation"><a href="/companyMaintain/add" target="_blank">找不到个人用户？</a></li>
                    </ul>
                </div>
                <div class="row">
                    <label class="col-sm-2 control-label">其他类型</label>

                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="othersId" v-model="elevator.othersName"
                               autocomplete="off" data-provide="typeahead">

                    </div>
                    <ul class="nav nav-pills" role="tablist">
                        <li role="presentation"><a href="/companyMaintain/add" target="_blank">找不到其他类型？</a></li>
                    </ul>
                </div>
                <div class="row">
                    <label class="col-sm-2 control-label">${t("maintainer")}</label>

                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="maintainerId" v-model="elevator.maintainerName"
                               autocomplete="off" data-provide="typeahead" onchange="getManager()">

                    </div>
                    <ul class="nav nav-pills" role="tablist">
                        <li role="presentation"><a href="/companyMaintain/add" target="_blank">找不到维保单位？</a></li>
                    </ul>
                </div>
                <div class="row"><label class="col-sm-2 control-label">${t("maintenance")}</label>
                    <div class="col-sm-4" id="divShowManager" v-html="elevator.maintenanceManager">
                    </div>
                    <div class="col-sm-4" id="divChangeManager" style="display: none;">
                        <select id="maintenanceManager" class="form-control" v-model="elevator.maintenanceManager" onchange="getMen()">
                            <option value="-1" selected="selected">${t("pleaseSelectMaintenance")}</option>
                        </select>
                    </div>
                    <ul class="nav nav-pills" role="tablist">
                        <li role="presentation"><a href="/maintainerEmployee/cou" target="_blank">找不到维保负责人？</a></li>
                    </ul>
                </div>
                <div class="row">
                    <label class="col-sm-2 control-label">${t("maintenancePerson")}</label>
                    <div class="col-sm-4" id="divShowMan" v-html="elevator.maintenanceName">
                    </div>
                    <div class="col-sm-4" id="divChangeMan" style="display: none;">
                        <select id="maintenanceId" class="form-control" v-model="elevator.maintenanceName">
                            <option value="-1" selected="selected">${t("pleaseSelectMaintenanceMan")}</option>
                        </select>
                    </div>
                    <#--<div class="col-sm-4">-->
                        <#--<select class="form-control" style="width:95%;display:inline;" id="maintenanceId"-->
                                <#--v-model="elevator.maintenanceName">-->
                            <#--<option value="-1" selected="selected">${t("pleaseSelectMaintenanceMan")}</option>-->
                        <#--</select>-->
                    <#--</div>-->

                    <ul class="nav nav-pills" role="tablist">
                        <li role="presentation"><a href="/maintainerEmployee/cou" target="_blank">找不到维保人员？</a></li>
                    </ul>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-3 col-sm-4">
                        <button type="button" class="btn btn-primary"
                                v-on:click="stateInfoConfirm">${t("confirm")}</button>
                        <button type="button" class="btn btn-success col-sm-offset-2"
                                v-on:click="stateInfoCancel">${t("cancel")}</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</@layout.archiveTemplate>