<#include "func.ftl">
<#import "layout.ftl" as layout />

<#assign headerContent>
<link rel="stylesheet" href="/css/realtime.css">
<link rel="stylesheet" href="/lib/bootstrap-table/bootstrap-table.css">
<link rel="stylesheet" type="text/css" href="/lib/kkpager/kkpager_black.css"/>
</#assign>

<@layout.masterTemplate initScript='js/realtime' header=headerContent title='${t("onlineMonitoring")}'>
<#--<style>
    .checkbox label: span{display:none;}
    .checkbox label:hover span{display:inline;}
</style>-->
<div class="realtime-container">
    <input type="hidden" id="isFav" value="${isFav}"/>
    <input type="hidden" id="uId" value="${uId}"/>
    <input type="hidden" id="sid" value="${sid}"/>
    <input type="hidden" id="selectedItem" value="0"/>

    <div class="left-menu">
       <#--电梯状态 -->
        <div class="content-area">
            <dl>
                <dt>${t("elevatorStatus")}</dt>
                <dd class="checkbox">
                    <label class="cLabel" data-status-type="10"><span class="label" id="countAll">${elevatorCountAll}</span>${t("all")}</label>
                    <label class="cLabel ml-10" data-status-type="20"><span class="label" id="countOffline"></span>${t("offline")}</label>
                </dd>
                <dd class="checkbox">
                    <label class="cLabel" data-fault-type="20"><span class="label" id="countFault"></span>${t("malfunction")}</label>
                    <#--<label class="cLabel" data-type="6"><a href="javascript:getElevators(6)"><span class="label" id="countFault"></span>${t("malfunction")}</a></label>-->
                    <label class="cLabel ml-10" data-maintenance-type="20"><span class="label" id="countMaintenance"></span>${t("recondition")}</label>
                    <#--<label class="cLabel ml-10" data-type="7"><span class="label" id="countMaintenance"></span>${t("recondition")}</label>-->
                </dd>
                <dd class="checkbox">
                    <label class="cLabel" data-normal-type="10"><span class="label" id="countNormal"></span>${t("normal")}</label>
                </dd>
            </dl>
        </div>
        <#-- 查询-->
        <div class="content-area mar-20">
            <dl id="search-dl">
                <dt>${t("search")}</dt>
                <dd><input type="text" class="form-control" id="contractNo" data-provide="typeahead" placeholder="${t("numberOfElevator")}"/></dd>
            <#--<dd><input type="text" class="form-control" id="premises" placeholder="${t("premises")}"/></dd>-->
                <dd>
                    <select class="form-control" id="brand">
                        <option selected value="-1">${t("chooseBrand")}</option>
                        <#list brands as brand>
                            <option value="${brand.id}">${brand.name}</option>
                        </#list>
                    </select>
                </dd>
                <dd>
                    <select class="form-control" id="eType">
                        <option selected value="-1">请选择电梯类型</option>
                        <#list eTypes as eType>
                            <option value="${eType.id}">${eType.name}</option>
                        </#list>
                    </select>
                </dd>
                <dd><input type="text" class="form-control" id="registrationCode" placeholder="${t("registrationCode")}"/></dd>
                <dd><input type="text" class="form-control" id="iRegistrationCode" placeholder="${t("iRegistrationCode")}"/></dd>
                <dd><input type="text" class="form-control" id="alias" placeholder="${t("alias")}"/></dd>
                <dd>
                    <div class="fl-2">
                        <select id="province" class="form-control">
                            <option selected value="-1" >${t("province")}</option>
                        </select>
                    </div>
                    <div class="fl-2">
                        <select id="city" class="form-control col-md-4">
                            <option selected value="-1">${t("city")}</option>
                        </select>
                    </div>
                </dd>
                <dd>
                    <div class="fl-2">
                        <select id="region" class="form-control col-md-4">
                            <option selected value="-1">${t("region")}</option>
                        </select>
                    </div>
                </dd>
                <dd>
                    <button class="search-btn glyphicon glyphicon-search" id="search-btn"/>
                </dd>
            </dl>
        </div>

    </div>

    <div class="right-content">
        <div class="elevator-content clearfix">

        </div>


        <div class="bootstrap-table">
            <div class="fixed-table-toolbar"></div>
            <div class="fixed-table-container" style="padding-bottom: 0px;">
                <div class="fixed-table-header" style="display: none;">
                    <table></table>
                </div>
                <div class="fixed-table-body">
                    <table id="dataTable" class="table table-hover">
                        <thead>
                        <tr>
                            <th style="" data-field="0">
                                <div class="th-inner "></div>
                                <div class="fht-cell"></div>
                            </th>
                            <th style="" data-field="number">
                                <div class="th-inner ">设备号</div>
                                <div class="fht-cell"></div>
                            </th>
                            <th style="" data-field="alias">
                                <div class="th-inner ">楼盘</div>
                                <div class="fht-cell"></div>
                            </th>
                            <th style="" data-field="maintainer">
                                <div class="th-inner ">责任人</div>
                                <div class="fht-cell"></div>
                            </th>
                            <th style="" data-field="elevatorStatus">
                                <div class="th-inner ">运行状态</div>
                                <div class="fht-cell"></div>
                            </th>
                            <th style="" data-field="faultStatus">
                                <div class="th-inner ">故障状态</div>
                                <div class="fht-cell"></div>
                            </th>
                            <th style="" data-field="maintenanceStatus">
                                <div class="th-inner ">检修状态</div>
                                <div class="fht-cell"></div>
                            </th>
                            <th style="" data-field="floor">
                                <div class="th-inner ">当前楼层</div>
                                <div class="fht-cell"></div>
                            </th>
                            <th style="" data-field="direction">
                                <div class="th-inner ">上/下行</div>
                                <div class="fht-cell"></div>
                            </th>
                        </tr>
                        </thead>
                        <tbody id="v-table">

                        </tbody>
                    </table>
                </div>

            </div>
        </div>

        <div class="pagination">
            <div id="kkpager"></div>
        </div>
    </div>

    <div class="switch-bar" style="position: fixed;top: 120px;right: 20px;z-index: 9999">
        <ul>
            <li class="sbItem" style="border-top: solid 1px #e5e5e5;">
                <a>
                    <span class="sb-0 sbIcon active glyphicon glyphicon-th-large" data-val="0"></span>
                </a>
            </li>
            <li class="sbItem">
                <a>
                    <span class="sb-1 sbIcon glyphicon glyphicon-th-list" data-val="1"></span>
                </a>
            </li>
        </ul>
    </div>
</div>
</@layout.masterTemplate>