<#import "archiveLayout.ftl" as layout />
<#include 'func.ftl'>

<#assign headerContent>
<link rel="stylesheet" href="/lib/bootstrap-table/bootstrap-table.css">
<link rel="stylesheet" href="/lib/datetimepicker/bootstrap-datetimepicker.css">
<link rel="stylesheet" href="/lib/sweetalert/sweetalert.css">
</#assign>

<@layout.archiveTemplate initScript='js/elevatorAdd' header=headerContent title='新建电梯'>
<div class="panel panel-default" id="elevatorAdd">
    <#--<div class="panel-heading"></div>-->
    <#--<div class="panel-body">-->
    <#--<form class="form-horizontal">-->
        <#--<div class="form-group">-->
            <#--<label class="col-sm-4 control-label">${t("numberOfElevator")}</label>-->

            <#--<div class="col-sm-4">-->
                <#--<input type="text" class="form-control" id="number" v-model="elevator.number">-->
            <#--</div>-->
        <#--</div>-->
        <#--<div class="form-group">-->
            <#--<label class="col-sm-4 control-label">设备编号</label>-->

            <#--<div class="col-sm-4">-->
                <#--<input type="text" class="form-control" id="equipmentNumber" v-model="elevator.equipmentNumber">-->
            <#--</div>-->
        <#--</div>-->
        <#--<div class="form-group">-->
            <#--<label class="col-sm-4 control-label">${t("alias")}</label>-->

            <#--<div class="col-sm-4">-->
                <#--<input type="text" class="form-control" v-model="elevator.alias" id="alias">-->
            <#--</div>-->
        <#--</div>-->
        <#--<div class="form-group">-->
            <#--<label class="col-sm-4 control-label">项目名称</label>-->

            <#--<div class="col-sm-4">-->
                <#--<input type="text" id="projectName" class="form-control" v-model="elevator.projectName">-->
            <#--</div>-->
        <#--</div>-->
        <#--<div class="form-group">-->
            <#--<label class="col-sm-4 control-label">${t("regCodeOfElevator")}</label>-->

            <#--<div class="col-sm-4">-->
                <#--<input type="text" class="form-control" id="regCode" v-model="elevator.regCode">-->
            <#--</div>-->
        <#--</div>-->
        <#--<div class="form-group">-->
            <#--<label class="col-sm-4 control-label">${t("elevatorType")}</label>-->

            <#--<div class="col-sm-4">-->
                <#--<select class="form-control" id="elevatorType" v-model="elevator.elevatorType">-->
                    <#--<option value="直梯">直梯</option>-->
                    <#--<option value="扶梯">扶梯</option>-->
                    <#--<option value="自动人行道">自动人行道</option>-->
                <#--</select>-->
            <#--</div>-->
        <#--</div>-->
        <#--<div class="form-group">-->
            <#--<label class="col-sm-4 control-label">${t("controllerTypeOfElevator")}</label>-->

            <#--<div class="col-sm-4">-->
                <#--<input type="text" class="form-control"  id="controllerType" v-model="elevator.controllerType">-->
            <#--</div>-->
        <#--</div>-->
        <#--<div class="form-group">-->
            <#--<label class="col-sm-4 control-label">${t("intelHardwareNumberOfElevator")}</label>-->

            <#--<div class="col-sm-4">-->
                <#--<input type="text" class="form-control"  v-model="elevator.intelHardwareNumber">-->
            <#--</div>-->
        <#--</div>-->
        <#--<div class="form-group">-->
            <#--<label class="col-sm-4 control-label">${t("brand")}</label>-->

            <#--<div class="col-sm-4">-->
                <#--<select class="form-control" v-model="elevator.brandId">-->
                    <#--<option value="-1" selected="selected">${t("pleaseSelectBrand")}</option>-->
                    <#--<#list brands as brand>-->
                        <#--<option value="${brand.id}">${brand.name}</option>-->
                    <#--</#list>-->
                <#--</select>-->
            <#--</div>-->
        <#--</div>-->
        <#--<div class="form-group">-->
            <#--<label class="col-sm-4 control-label">${t("ratedWeight")}</label>-->

            <#--<div class="col-sm-4">-->
                <#--<input type="text" class="form-control" v-model="elevator.ratedWeight">-->
            <#--</div>-->
        <#--</div>-->
        <#--<div class="form-group">-->
            <#--<label class="col-sm-4 control-label">${t("ratedSpeed")}</label>-->

            <#--<div class="col-sm-4">-->
                <#--<input type="text" class="form-control" v-model="elevator.ratedSpeed">-->
            <#--</div>-->
        <#--</div>-->
        <#--<div class="form-group">-->
            <#--<label class="col-sm-4 control-label">${t("productionTime")}</label>-->

            <#--<div class="col-sm-4">-->
                <#--<div class="input-group date" id="datetimePicker">-->
                    <#--<input type="text" class="form-control" name="datetimePicker" id="productTimePicker"-->
                           <#--v-model="elevator.productionTime"/>-->
                        <#--<span class="input-group-addon">-->
                            <#--<span class="glyphicon glyphicon-calendar"></span>-->
                        <#--</span>-->
                <#--</div>-->
            <#--</div>-->
        <#--</div>-->
        <#--<div class="form-group">-->
            <#--<label class="col-sm-4 control-label">${t("deliverTime")}</label>-->

            <#--<div class="col-sm-4">-->
                <#--<div class="input-group date" id="datetimePicker">-->
                    <#--<input type="text" class="form-control" name="datetimePicker" id="deliverTimePicker"-->
                           <#--v-model="elevator.deliverTime"/>-->
                        <#--<span class="input-group-addon">-->
                            <#--<span class="glyphicon glyphicon-calendar"></span>-->
                        <#--</span>-->
                <#--</div>-->
            <#--</div>-->
        <#--</div>-->
        <#--<div class="form-group">-->
            <#--<label class="col-sm-4 control-label">${t("controlMode")}</label>-->

            <#--<div class="col-sm-4">-->
                <#--<input type="text" class="form-control" v-model="elevator.controlMode">-->
            <#--</div>-->
        <#--</div>-->
        <#--<div class="form-group">-->
            <#--<label class="col-sm-4 control-label">${t("hoistingHeight")}</label>-->

            <#--<div class="col-sm-4">-->
                <#--<input type="text" class="form-control" v-model="elevator.hoistingHeight">-->
            <#--</div>-->
        <#--</div>-->
        <#--<div class="form-group">-->
            <#--<label class="col-sm-4 control-label">${t("station")}</label>-->

            <#--<div class="col-sm-4">-->
                <#--<input type="text" class="form-control" v-model="elevator.station">-->
            <#--</div>-->
        <#--</div>-->
        <#--<div class="form-group">-->
            <#--<label class="col-sm-4 control-label">${t("useCompany")}</label>-->

            <#--<div class="col-sm-4">-->
                <#--<input type="text" class="form-control" id="userCompanyId" v-model="elevator.userCompanyName" autocomplete="off" data-provide="typeahead">-->
            <#--</div>-->
            <#--<ul class="nav nav-pills" role="tablist">-->
                <#--<li role="presentation"><a href="/companyMaintain/add" target="_blank">找不到使用单位？</a></li>-->
            <#--</ul>-->
        <#--</div>-->
        <#--<div class="form-group">-->
            <#--<label class="col-sm-4 control-label">${t("maintainer")}</label>-->

            <#--<div class="col-sm-4">-->
                <#--<input type="text" class="form-control" id="maintainerId" v-model="elevator.maintainerName" autocomplete="off" data-provide="typeahead" onchange="getManager()">-->
            <#--</div>-->
            <#--<ul class="nav nav-pills" role="tablist">-->
                <#--<li role="presentation"><a href="/companyMaintain/add" target="_blank">找不到维保单位？</a></li>-->
            <#--</ul>-->
        <#--</div>-->
        <#--<div class="form-group">-->
            <#--<label class="col-sm-4 control-label">${t("maintenance")}</label>-->
            <#--<div class="col-sm-4">-->
                <#--<select class="form-control" style="width:95%;display:inline;" id="maintenanceManager" v-model="elevator.maintenanceManager" onchange="getMen()">-->
                    <#--<option value="-1" selected="selected">${t("pleaseSelectMaintenance")}</option>-->
                <#--</select>-->
                <#--<label style="color:red;">*</label>-->
            <#--</div>-->

            <#--<ul class="nav nav-pills" role="tablist">-->
                <#--<li role="presentation"><a href="/maintainerEmployee/cou" target="_blank">找不到维保负责人？</a></li>-->
            <#--</ul>-->

        <#--</div>-->
        <#--<div class="form-group">-->
            <#--<label class="col-sm-4 control-label">${t("maintenancePerson")}</label>-->
            <#--<div class="col-sm-4">-->
                <#--<select class="form-control" style="width:95%;display:inline;" id="maintenanceId" v-model="elevator.maintenanceName">-->
                    <#--<option value="-1" selected="selected">${t("pleaseSelectMaintenanceMan")}</option>-->
                <#--</select>-->
                <#--<label style="color:red;">*</label>-->
            <#--</div>-->
            <#--<ul class="nav nav-pills" role="tablist">-->
                <#--<li role="presentation"><a href="/maintainerEmployee/cou" target="_blank">找不到维保人员？</a></li>-->
            <#--</ul>-->

        <#--</div>-->
        <#--<div class="form-group">-->
            <#--<label class="col-sm-4 control-label">${t("installCompany")}</label>-->

            <#--<div class="col-sm-4">-->
                <#--<input type="text" class="form-control" id="installCompanyId" autocomplete="off" data-provide="typeahead" v-model="elevator.installCompanyName">-->
            <#--</div>-->
            <#--<ul class="nav nav-pills" role="tablist">-->
                <#--<li role="presentation"><a href="/companyMaintain/add" target="_blank">找不到安装单位？</a></li>-->
            <#--</ul>-->

        <#--</div>-->
        <#--<div class="form-group">-->
            <#--<label class="col-sm-4 control-label">${t("manufacturer")}</label>-->

            <#--<div class="col-sm-4">-->
                <#--<input type="text" class="form-control" id="manufacturerId" autocomplete="off" data-provide="typeahead" v-model="elevator.manufacturerName">-->
            <#--</div>-->
            <#--<ul class="nav nav-pills" role="tablist">-->
                <#--<li role="presentation"><a href="/companyMaintain/add" target="_blank">找不到制造单位？</a></li>-->
            <#--</ul>-->

        <#--</div>-->
        <#--<div class="form-group">-->
            <#--<label class="col-sm-4 control-label">${t("addressOfElevator")}</label>-->

            <#--<div class="col-sm-4">-->
                <#--<input type="text" class="form-control" id="address" v-model="elevator.address"-->
                       <#--name="address">-->
            <#--</div>-->
            <#--<ul class="nav nav-pills" role="tablist">-->
                <#--<button type="button" class="btn btn-default" v-on:click="locateElevator">${t("gpsMap")}</button>-->
            <#--</ul>-->
        <#--</div>-->
        <#--<div class="form-group">-->
            <#--<label class="col-sm-4 control-label">经度</label>-->

            <#--<div class="col-sm-4">-->
                <#--<input type="text" id="lng" class="form-control" v-model="elevator.lng">-->
            <#--</div>-->
        <#--</div>-->
        <#--<div class="form-group">-->
            <#--<label class="col-sm-4 control-label">纬度</label>-->

            <#--<div class="col-sm-4">-->
                <#--<input type="text" class="form-control" id="lat" v-model="elevator.lat">-->
            <#--</div>-->
        <#--</div>-->
        <#--<div class="form-group" style="height:530">-->
            <#--<div id="map"></div>-->
        <#--</div>-->

    <#--</form>-->
<#--</div>-->
    <div class="panel panel-default">
        <div class="panel-heading">
        ${t("baseInfo")}
        </div>
        <div class="panel-body">
            <div >
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

            </div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading">
            关联单位
        </div>
        <div class="panel-body">
            <div>
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
                        <input type="text" class="form-control" id="ownerId" v-model="elevator.ownerName"
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

                    <div class="col-sm-4">
                        <select class="form-control" id="maintenanceManager"
                                onchange="getMen()" v-model="elevator.maintenanceManager">
                            <option value="-1" selected="selected">${t("pleaseSelectMaintenance")}</option>
                        </select>
                    </div>

                    <ul class="nav nav-pills" role="tablist">
                        <li role="presentation"><a href="/maintainerEmployee/cou" target="_blank">找不到维保负责人？</a></li>
                    </ul>
                </div>
                <div class="row">
                    <label class="col-sm-2 control-label">${t("maintenancePerson")}</label>
                    <div class="col-sm-4">
                        <select class="form-control" id="maintenanceId"
                                v-model="elevator.maintenanceName">
                            <option value="-1" selected="selected">${t("pleaseSelectMaintenanceMan")}</option>
                        </select>
                    </div>

                    <ul class="nav nav-pills" role="tablist">
                        <li role="presentation"><a href="/maintainerEmployee/cou" target="_blank">找不到维保人员？</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="form-group">
            <div class="col-sm-offset-3 col-sm-4">
                <button type="button" class="btn btn-primary" v-on:click="confirm">${t("confirm")}</button>
                <button type="button" class="btn btn-default col-sm-offset-2"
                        onclick="window.location.href='/elevator'">${t("cancel")}</button>

            </div>
        </div>
    </div>
</div>
</@layout.archiveTemplate>