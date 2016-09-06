<#include 'func.ftl'>

<#macro masterTemplate title="" header="" footer="" initScript="">
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>${title}</title>
    <#--<link rel="shortcut icon" href="/image/logo.ico">-->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">

    <link rel="stylesheet" href="/css/layout.css">
    <link rel="stylesheet" href="/lib/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/lib/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="/lib/toastr/toastr.min.css">
    <link rel="stylesheet" href="/lib/bootstrap-validator/bootstrapValidator.min.css">

    <script type="text/javascript">
        var require = {
            baseUrl: '/',
            waitSeconds: 200,
            paths: {
                lib: "lib",
                js: "js",
                jquery: "lib/jquery/jQuery-2.1.4.min",
                bootstrap: "lib/bootstrap/js/bootstrap.min",
                typeahead: "lib/bootstrap/js/bootstrap3-typeahead",
                vue: "lib/vuejs/vue",
                toastr: "lib/toastr/toastr.min",
                ajax: "js/ajax",
                translator: "js/translator",
                translationData: "/api/translation/data",
                swal: "lib/sweetalert/sweetalert",
                table: "lib/bootstrap-table/bootstrap-table",
                bootstrapValidator: "lib/bootstrap-validator/bootstrapValidator.min",
                ditu: "http://api.map.baidu.com/getscript?v=2.0&ak=qf3mmSNA7LGiXjRVCr9ISCqa&services=&t=20151113040005",
                ditu_markerCluster: "lib/baidumap/MarkerClusterer_min",
                ditu_TextIconOverlay: "lib/baidumap/TextIconOverlay_min",
                raphael: "lib/Raphael/raphael",
                kkpager: "lib/kkpager/kkpager",
                DataParsing: "lib/websocket/dataparsing",
                Protocol: "lib/websocket/protocol",
                Base64: "lib/websocket/base64",
                Static: "lib/websocket/sellon_static",
                datetimepicker: "lib/datetimepicker/bootstrap-datetimepicker",
                moment: "lib/moment/moment-with-locales.min",
                ztree: "lib/ztree/jquery.ztree.all.min",
                select: "lib/bootstrap-select/js/bootstrap-select",
                fullcalendar: "lib/fullcalendar/fullcalendar"
            },
            deps: [
                "bootstrap"
            ],
            shim: {
                bootstrap: {
                    deps: ["jquery", "js/layout"]
                },
                table: {
                    deps: ["jquery", "bootstrap"]
                },
                ditu_markerCluster: {
                    exports: 'BMapLib',
                    deps: ["ditu_TextIconOverlay"]
                },
                ditu_TextIconOverlay: {
                    exports: 'BMapLib',
                    deps: ["ditu"]
                },
                ditu: {
                    exports: 'BMap'
                },
                raphael: {
                    exports: 'Raphael'
                },
                kkpager: {
                    exports: 'kkpager',
                    deps: ["jquery"]
                },
                translator: {
                    deps: ["translationData"]
                },
                DataParsing: {
                    exports: 'DataParsing',
                    deps: ["Protocol", "Base64", "Static"]
                },
                datetimepicker: {
                    deps: ["jquery", "bootstrap", "moment"]
                },
                ztree: {
                    deps: ["jquery"]
                },
                select: {
                    deps: ["jquery"]
                },
                bootstrapValidator: {
                    deps: ["jquery","bootstrap"]
                },
                fullcalendar: {
                    deps: ["jquery", "moment"]
                }
            }
        };
    </script>
    <script data-main="${initScript}" src="/lib/requirejs/requirejs-2.1.20.js"></script>
${header}
</head>
<body>
<input type="hidden" id="sid" value="${sid}"/>
<div id="header">
    <div id="banner">
        <div>
            <div class="menu">
                <#if hasPermission("onlineMonitoring:view")>
                    <div>
                        <a href="/realTime" class="menu-realtime-monitoring">
                        ${t("onlineMonitoring")}
                            <div class="line"></div>
                        </a>
                    </div>
                    <div class="split"></div>
                </#if>
                <#if hasPermission("gridRescue:view")>
                    <div>
                        <a href="/gridRescue" class="menu-grid-rescue">
                        ${t("gridRescue")}
                        <div class="line"></div>
                        </a>
                    </div>
                    <div class="split"></div>
                 </#if>
                <#if hasPermission("workbillBoard:view")>
                    <div>
                        <a href="/workBillBoard" class="menu-service-management">
                        ${t("serviceManagement")}
                            <div class="line"></div>
                        </a>
                    </div>
                    <div class="split"></div>
                </#if>
                <#if hasPermission("elevator:view")>
                    <div>
                        <a href="/elevator" class="menu-archive-management">
                        ${t("mainDataModule")}
                            <div class="line"></div>
                        </a>
                    </div>
                    <div class="split"></div>
                </#if>
                <#--<#if hasPermission("elevator:particular:view")>-->
                    <#--<div>-->
                        <#--<a href="/multiElevator" class="multi-brand-elevator">-->
                        <#--${t("multiElevator")}-->
                            <#--<div class="line"></div>-->
                        <#--</a>-->
                    <#--</div>-->
                    <#--<div class="split"></div>-->
                <#--</#if>-->
                <#if hasPermission("user:view")>
                    <div>
                         <a href="/user/authorization" class="user-authorization">
                         ${t("userAuthorization")}
                             <div class="line"></div>
                         </a>
                    </div>
                </#if>
                <div>
                    <a href="/statics" class="user-authorization">
                    统计
                        <div class="line"></div>
                    </a>
                </div>
            </div>
            <div class="action-bar pull-right">
                <ul class="nav navbar-nav">
                    <input type="hidden" id="currentLanguage" value="${currentLanguage}"/>
                    <li <#if currentLanguage == "english">class="active"</#if>><a href="javascript:changeLanguage('english')">EN</a></li>
                    <li <#if currentLanguage == "chinese">class="active"</#if>><a href="javascript:changeLanguage('chinese')">中</a></li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                           aria-expanded="false">${loginUser.userName} <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="/user/userInfo">${t("userInfo")}</a></li>
                            <li><a href="/user/changePassword">${t("changePassword")}</a></li>
                            <li><a href="/favorite">${t("myFavorites")}</a></li>
                            <#if hasPermission("data:sync")>
                                <li><a href="javascript:syncData()">${t("dataSync")}</a></li>
                            </#if>
                            <li><a href="/logout">${t("logout")}</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <div id="sub-banner">
        <div>
            <div class="logo">
                <a href="/"><img src="/image/joylive.png" alt="logo"></a>
            </div>
            <div class="pull-right">
                <div class="status">
                    <div class="all" onclick="window.location.href='/all'">
                        <span class="label" id="navCountAll">${elevatorCountAll}</span><span class="desc">${t("all")}</span>
                    </div>
                    <div class="malfunction" onclick="window.location.href='/faultElevators'">
                        <span class="label" id="navCountFault"></span><span class="desc">${t("malfunction")}</span>
                    </div>
                    <#--<div class="recondition" onclick="window.location.href='/?status=recondition&statusType=maintenanceStatus'">-->
                        <#--<span class="label">${elevatorCountRecondition}</span><span class="desc">${t("recondition")}</span>-->
                    <#--</div>-->
                    <#--<div class="online" onclick="window.location.href='/?status=online&statusType=onlineStatus'">-->
                        <#--<span class="label">${elevatorCountOnline}</span><span class="desc">${t("normal")}</span>-->
                    <#--</div>-->
                    <#--<div class="offline" onclick="window.location.href='/?status=offline&statusType=onlineStatus'">-->
                        <#--<span class="label">${elevatorCountOffline}</span><span class="desc">${t("offline")}</span>-->
                    <#--</div>-->
                </div>
            </div>
        </div>
    </div>
</div>
<div id="content">
    <div>
        <#nested />
    </div>
</div>
<div id="footer">
    @2016 ${t("juliElevator")}
</div>

${footer}
</body>
</html>
</#macro>