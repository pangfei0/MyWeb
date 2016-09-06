<#import "layout.ftl" as layout />
<#include 'func.ftl'>

<#assign headerContent>
<link rel="stylesheet" href="/css/dataReport.css">
</#assign>

<@layout.masterTemplate initScript="js/dataReport" header=headerContent title='${t("dataReport")}'>
<form method="post" action="/dataReport/export">
    <div class="data-report">
        <div class="top">
            <div class="section step1">
                <div class="title">${t("elevatorRangeSearch")}</div>
                <div class="body">
                    <dl>
                        <dd><input type="text" id="premise" name="premise" v-model="search.premise" placeholder="${t("premises")}"></dd>
                        <dd><input type="text" id="area" name="area" v-model="search.are" placeholder="${t("area")}"></dd>
                        <dd><input type="text" id="brand" name="brand" v-model="search.brand" placeholder="${t("brand")}"></dd>
                        <dd><input type="text" id="number" name="number" v-model="search.contractNumber" placeholder="${t("contractNumber")}"></dd>
                        <dd><input type="text" id="serviceLife" name="serviceLife" v-model="search.serviceLife" placeholder="${t("serviceLife")}"></dd>
                        <dd class="dropdown">
                            <select id="ele-status" name="eleStatus" class="form-control">
                                <option selected="selected" value="0">${t("currentStatus")}</option>
                                <option value="30">${t("malfunction")}</option>
                                <option value="20">${t("recondition")}</option>
                                <option value="10">${t("normal")}</option>
                                <option value="40">${t("offline")}</option>
                            </select>
                        </dd>
                    </dl>
                </div>
            </div>

            <div class="section step2">
                <div class="title">${t("statusReportGenerate")}</div>
                <div class="body">
                    <table>
                        <tr v-bind:class="{'active': generate.controlSystem}">
                            <td class="txt">
                                <div>${t("controlSystemStatistics")}</div>
                            </td>
                            <td class="checkbox">
                                <div v-on:click="generate.controlSystem = !generate.controlSystem"></div>
                                <input type="hidden" name="generateControlSystem" v-model="generate.controlSystem"/>
                            </td>
                        </tr>
                        <tr v-bind:class="{'active': generate.elevatorStatus}">
                            <td class="txt">
                                <div>${t("elevatorStatusStatistics")}</div>
                            </td>
                            <td class="checkbox">
                                <div v-on:click="generate.elevatorStatus = !generate.elevatorStatus"></div>
                                <input type="hidden" name="generateElevatorStatus" v-model="generate.elevatorStatus"/>
                            </td>
                        </tr>
                        <tr v-bind:class="{'active': generate.malfunctionHistory}">
                            <td class="txt">
                                <div>${t("malfunctionHistoryStatistics")}</div>
                            </td>
                            <td class="checkbox">
                                <div v-on:click="generate.malfunctionHistory = !generate.malfunctionHistory"></div>
                            </td>
                        </tr>
                        <tr v-bind:class="{'active': generate.maintenance}">
                            <td class="txt">
                                <div>${t("maintenanceStatusStatistics")}</div>
                            </td>
                            <td class="checkbox">
                                <div v-on:click="generate.maintenance = !generate.maintenance"></div>
                            </td>
                        </tr>
                        <tr v-bind:class="{'active': generate.malfunctionAnalyse}">
                            <td class="txt">
                                <div>${t("malfunctionAnalyseResult")}</div>
                            </td>
                            <td class="checkbox">
                                <div v-on:click="generate.malfunctionAnalyse = !generate.malfunctionAnalyse"></div>
                            </td>
                        </tr>
                        <tr v-bind:class="{'active': generate.currentHealthExamination}">
                            <td class="txt">
                                <div>${t("currentHealthExaminationResult")}</div>
                            </td>
                            <td class="checkbox">
                                <div v-on:click="generate.currentHealthExamination = !generate.currentHealthExamination"></div>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        <div class="bottom">
            <input type="submit" class="btn export" value="${t("export")}"/>
        </div>
    </div>
</form>
</@layout.masterTemplate>