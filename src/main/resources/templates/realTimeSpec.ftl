<#include "func.ftl">
<#import "layout.ftl" as layout />

<#assign headerContent>
<link rel="stylesheet" href="/css/realtimeSpec.css" xmlns="http://www.w3.org/1999/html">
</#assign>

<@layout.masterTemplate initScript='js/realtimeDetail' header=headerContent title='${t("onlineMonitoring")}'>
<input type="hidden" id="eleId" value="${id}"/>
<input type="hidden" id="sid" value="${sid}"/>
<div class="realtime-detail clearfix">
    <div class="left-content">
        <div class="header-nav clearfix">
            <div class="ele-num">
                <span>${t("numberOfElevator")}:</span>
                <span id="elevatorNum"></span>
            </div>
            <div class="ele-add">
                <span>${t("alias")}:</span>
                <span id="elevatorAddr"></span>
            </div>
            <div class="signal">
                <img id="signalImg" src="/image/icon_mobile_0.png" regCode="">
            </div>
        </div>
        <div class="detail-content clearfix">
            <div class="left-status">

                <div class="up-down-area rows" id="normalDiv">
                    <div class="col-md-4">
                        <div id="eleUp" class="tImageUp" regCode=""></div>
                    </div>
                    <div class="col-md-4">
                        <span id="floor-num" regCode=""></span>
                    </div>
                    <div class="col-md-4">
                        <div id="eleDown"  class="tImageDown" regCode=""></div>
                    </div>
                    <div class="col-md-4" id="faultDiv" style="text-align:center;width:inherit;padding-top:22px;"><span class="typeFC" id="faultCode" regCode=""></span></div>
                </div>


                <ul class="ele-type">
                    <li><span class="t-title">${t('controllerTypeOfElevator')}:</span><span class="typeCl" id="controlType"></span></li>
                    <li><span class="t-title">${t("sysRuntime")}:</span><span class="typeCl" id="sysRuntime"></span></li>
                    <li><span class="t-title">${t("runCount")}:</span><span class="typeCl" id="runCount"></span></li>
                </ul>
                <ul class="ele-call">
                    <li class="clearfix"><div class="c-title">${t("upCall")}:</div><div class="c-content" id="upCall"></div></li>
                    <li class="clearfix"><div class="c-title">${t("downCall")}:</div><div class="c-content" id="downCall"></div></li>
                    <li class="clearfix"><div class="c-title">${t("innerCall")}:</div><div class="c-content" id="innerCall"></div></li>
                </ul>
                <div class="ele-img rows clearfix">
                    <div class="col-md-4">
                        <img id="recImg" src="/image/Recondition-Off.png">
                        <div class="em-rc">${t('recondition')}</div>
                    </div>
                    <div class="col-md-4">
                        <img id="malImg" src="/image/Malfuncion-Off.png">
                        <div class="em-mf">${t('malfunction')}</div>
                    </div>
                    <div class="col-md-4">
                        <img id="olImg" src="/image/Overload-Off.png">
                        <div class="em-ol">${t('overload')}</div>
                    </div>
                </div>

                <ul class="ele-nav">
                    <li>
                        <div class="rows clearfix">
                            <div class="col-md-6">
                                <div class="tbox">
                                    <span class="tSpan">${t("statusReport")}</span>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="tbox">
                                    <span class="tSpan">${t("elevatorEvent")}</span>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="rows clearfix">
                            <div class="col-md-6">
                                <div class="tbox">
                                    <span class="tSpan">${t("videoMonitor")}</span>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="tbox">
                                    <span class="tSpan">${t("elevatorInfo")}</span>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="rows clearfix">
                            <div class="col-md-6">
                                <div class="tbox" onclick="window.location.href = '/workBill/create?elevatorNumbers=${elevatorNumbers}'">
                                    <span class="tSpan">${t("launchMaintenance")}</span>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="tbox" onclick="window.location.href = '/workBill/create?elevatorNumbers=${elevatorNumbers}'">
                                    <span class="tSpan">${t("upkeepSchedule")}</span>
                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="right-ele">
                <img id="elevator-img" src="/image/elevator_pic.png" dataStatus="00" regCode="">
            </div>

        </div>
    </div>
    <div class="right-content">
        <ul class="ele-status-ul">
            <li>
                <label class="ele-status-lb-1">
                    <div class="lb-item">${t("regCodeOfElevator")}</div>
                    <div id="registerCode" class="lb-value"></div>
                </label>
            </li>
            <li>
                <label class="ele-status-lb-2">
                    <div class="lb-item">${t("intelHardwareNumberOfElevator")}</div>
                    <div id="sRegisterCode" class="lb-value"></div>
                </label>
            </li>
            <li>
                <label class="ele-status-lb-1">
                    <div class="lb-item">${t("elevatorModel")}</div>
                    <div id="elevatorCode"  class="lb-value"></div>
                </label>
            </li>
            <li>
                <label class="ele-status-lb-2">
                    <div class="lb-item">${t("elevatorType")}</div>
                    <div id="elevatorType"  class="lb-value"></div>
                </label>
            </li>
            <li>
                <label class="ele-status-lb-1">
                    <div class="lb-item">${t("exFactoryDate")}</div>
                    <div id="releaseDate"  class="lb-value"></div>
                </label>
            </li>
            <li>
                <label class="ele-status-lb-2">
                    <div class="lb-item">${t("useOccasion")}</div>
                    <div id="environment"  class="lb-value"></div>
                </label>
            </li>
            <li>
                <label class="ele-status-lb-1">
                    <div class="lb-item">${t("station")}</div>
                    <div id="levelStD"  class="lb-value"></div>
                </label>
            </li>
            <li>
                <label class="ele-status-lb-2">
                    <div class="lb-item">${t("manufacturerName")}</div>
                    <div id="producer"  class="lb-value"></div>
                </label>
            </li>
            <li>
                <label class="ele-status-lb-1">
                    <div class="lb-item">${t("installCompany")}</div>
                    <div id="installation"  class="lb-value"></div>
                </label>
            </li>
            <li>
                <label class="ele-status-lb-2">
                    <div class="lb-item">${t("addressOfElevator")}</div>
                    <div id="detailAddr"  class="lb-value"></div>
                </label>
            </li>
            <li>
                <label class="ele-status-lb-1">
                    <div class="lb-item">${t("useCompany")}</div>
                    <div id="useUnit"  class="lb-value"></div>
                </label>
            </li>
            <li>
                <label class="ele-status-lb-2">
                    <div class="lb-item">${t("tenement")}</div>
                    <div id="tenement"  class="lb-value"></div>
                </label>
            </li>
            <li>
                <label class="ele-status-lb-1">
                    <div class="lb-item">${t("maintenanceUnit")}</div>
                    <div id="maintenance"  class="lb-value"></div>
                </label>
            </li>
            <li>
                <label class="ele-status-lb-2">
                    <div class="lb-item">${t("dutyPhone")}</div>
                    <div id="dPhone"  class="lb-value"></div>
                </label>
            </li>
            <li>
                <label class="ele-status-lb-1">
                    <div class="lb-item">${t("maintenanceMan")}</div>
                    <div id="contactPerson"  class="lb-value"></div>
                </label>
            </li>
            <li>
                <label class="ele-status-lb-2">
                    <div class="lb-item">${t("managerMan")}</div>
                    <div id="tenementCP"  class="lb-value"></div>
                </label>
            </li>
            <li>
                <label class="ele-status-lb-2">
                    <div class="lb-item">${t("lastCheckDate")}</div>
                    <div id="lastCheckDate"  class="lb-value"></div>
                </label>
            </li>
            <li>
                <label class="ele-status-lb-1">
                    <div class="lb-item">${t("lastUpkeepTime")}</div>
                    <div id="lastMtDate"  class="lb-value"></div>
                </label>
            </li>
        </ul>
    </div>
</div>
</@layout.masterTemplate>