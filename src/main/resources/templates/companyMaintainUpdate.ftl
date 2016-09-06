<#import "archiveLayout.ftl" as layout />
<#include 'func.ftl'>

<#assign headerContent>
<link rel="stylesheet" href="/lib/datetimepicker/bootstrap-datetimepicker.css">
<link rel="stylesheet" href="/lib/sweetalert/sweetalert.css">
</#assign>

<@layout.archiveTemplate initScript='js/companyMaintainUpdate' header=headerContent title='${t("recordsCompaniesInfo")}'>
<div class="panel panel-default" id="companyMaintainCoU">
    <div class="panel-heading">
    ${t("baseInfo")}
        <#if hasPermission("companyMaintain:edit")>
            <button type="button" class="btn btn-default pull-right" id="baseInfoEditButton"
                    v-on:click="baseInfoEdit">${t("edit")}</button>
        </#if>
    </div>
    <div class="panel-body">
        <div id="baseInfoShow">
            <div class="row">
                <label class="col-md-2 control-label">${t("nameOfCompany")}:</label>

                <div class="col-md-4" v-html="company.baseInfo.name"></div>
                <label class="col-md-2 control-label">${t("typeOfCompany")}:</label>
                <div class="col-md-4" v-html="company.baseInfo.typeName"></div>

            </div>
            <div class="row">
                <label class="col-md-2 control-label">${t("addressOfCompany")}:</label>

                <div class="col-md-4" v-html="company.baseInfo.address"></div>
                <label class="col-md-2 control-label">${t("phoneOfCompany")}:</label>

                <div class="col-md-4" v-html="company.baseInfo.phone"></div>
            </div>
            <div class="row">
                <label class="col-md-2 control-label">${t("contactOfCompany")}:</label>

                <div class="col-md-4" v-html="company.baseInfo.contact"></div>
                <label class="col-md-2 control-label">${t("mobileOfCompany")}:</label>

                <div class="col-md-4" v-html="company.baseInfo.mobile"></div>
            </div>
        </div>
        <div id="baseInfoUpdate" style="display: none;">
            <div class="row">
                <label class="col-md-2 control-label">${t("nameOfCompany")}:</label>

                <div class="col-md-4" v-html="company.baseInfo.name"></div>

                <label class="col-md-2 control-label">${t("typeOfCompany")}:</label>
                <div class="col-md-4"  v-html="company.baseInfo.typeName"></div>
            </div>
            <div class="row">
                <label class="col-md-2 control-label">${t("addressOfCompany")}:</label>

                <div class="col-md-4">
                    <input type="text" class="form-control" v-model="company.baseInfo.address">
                </div>
                <label class="col-md-2 control-label">${t("phoneOfCompany")}:</label>

                <div class="col-md-4">
                    <input type="text" class="form-control" v-model="company.baseInfo.phone">
                </div>

            <div class="row">
                <label class="col-md-2 control-label">${t('contactOfCompany')}:</label>

                <div class="col-md-4">
                    <input type="text" class="form-control" v-model="company.baseInfo.contact">
                </div>
                <label class="col-md-2 control-label">${t("mobileOfCompany")}:</label>

                <div class="col-md-4">
                    <input type="text" class="form-control" v-model="company.baseInfo.mobile">
                </div>
            </div>
            <div class="col-sm-offset-4 col-sm-4">
                <button type="button" class="btn btn-primary" v-on:click="baseInfoConfirm">${t("confirm")}</button>
                <button type="button" class="btn btn-success col-sm-offset-2"
                        v-on:click="baseInfoCancel">${t("cancel")}
                <#if hasPermission("companyMaintain:delete")>
                    <button type="button" class="btn btn-danger col-sm-offset-2"
                            v-on:click="deleteIt">${t("delete")}</button>
                </#if>

            </div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading">
        ${t("relatedElevator")}
            <#if hasPermission("companyMaintain:edit")>
                <button type="button" class="btn btn-default pull-right" id="stateInfoEditButton"
                        v-on:click="elevatorInfoEdit">${t("addElevator")}</button>
            </#if>
        </div>
        <div id="elevatorInfoShow" class="panel-body">
            <div id="visibleForMaintainer" hidden="true">
                <label class="col-md-2 control-label">关联维保人员:</label>

                <form class="search-container search-form">
                    <div class="search-field">
                        <span>维保负责人:</span>
                        <select id="search_manager" class="form-control" onchange="getMen()">
                            <option value="-1" selected="selected">请选择维保负责人</option>
                        </select>
                    </div>
                    <div class="search-field">
                        <span>维保人员:</span>
                        <select id="search_men" class="form-control">
                            <option value="-1" selected="selected">请选择维保人员</option>
                        </select>
                    </div>
                    <div class="search-field">
                        <button type="button" class="btn btn-success col-sm-offset-2" onclick="updateMan()"
                        ">${t("updateMaintainer")}</button>
                    </div>
                </form>
            </div>
            <table class="table table-condensed table-bordered">
                <tr class="info" v-if="company.elevatorInfo.length >=1 ? true:false">
                    <td>${t("numberOfElevator")}</td>
                    <td>${t("elevatorType")}</td>
                    <td>${t("projectName")}</td>
                    <td>${t("addressOfElevator")}</td>
                    <td>${t("nameOfMaintainerEmployee")}</td>
                    <td>${t("managerOfMaintainerEmployee")}</td>
                    <#if hasPermission("companyMaintain:edit")>
                        <td>${t("operation")}</td>
                        <td>${t("operation")}</td>
                    </#if>
                    <td hidden="hidden"></td>
                </tr>
                <tr v-for="elevator in company.elevatorInfo">
                    <td>{{elevator.number}}</td>
                    <td>{{elevator.elevatorType}}</td>
                    <td>{{elevator.projectName}}</td>
                    <td>{{elevator.alias}}</td>
                    <td>{{elevator.maintenanceName}}</td>
                    <td>{{elevator.maintenanceManager}}</td>
                    <#if hasPermission("companyMaintain:edit")>
                        <td>
                            <button type="button" class="btn btn-success col-sm-offset-2"
                                    @click="updateElevatorMaintenance(elevator.id)">${t("updateMaintainer")}</button>
                        </td>
                        <td>
                            <button type="button" class="btn btn-success col-sm-offset-2"
                                    @click="removeElevator(elevator.id)">${t("remove")}</button>
                        </td>
                    </#if>
                    <td hidden="hidden">{{elevator.id}}</td>
                </tr>
            </table>
        </div>
        <div id="elevatorInfoUpdate" class="panel-body" style="display: none;">
            <div class="panel-body">
                <form class="search-container search-form">
                    <div class="search-field">
                        <span>${t("projectName")}:</span>
                        <input type="text" style="width: 120px" data-provide="typeahead" autocomplete="off"  id="projectName" class="form-control"/>
                        <input type="hidden" id="companyType"/>
                    </div>
                    <div class="search-field">
                        <span>${t("numberOfElevator")}:</span>
                        <input type="text" style="width: 120px" id="number" class="form-control"/>
                    </div>
                <#--<div class="search-field">-->
                <#--<span>${t("addressOfElevator")}:</span>-->
                <#--<input type="text" style="width:120px;" id="search_address" class="form-control"/>-->
                <#--</div>-->
                    <button type="button" class="btn btn-primary btn-search" v-on:click="search"><i
                            class="fa fa-search"></i> ${t("search")}</button>
                    <button type="button" class="btn btn-primary btn-search" v-on:click="elevatorCancel"><i
                            class="fa fa-search"></i> ${t("cancel")}</button>
                    <button type="button" class="btn btn-primary btn-search" v-on:click="addElevator"><i
                            class="fa fa-search"></i> ${t("addElevator")}</button>
                </form>
            </div>
            <div class="panel-body">
                <table id="elevatorDataTable">
                </table>
            </div>
        </div>
    </div>
</div>
</div>
</@layout.archiveTemplate>