<#include '../func.ftl'>

<#macro weixinMasterTemplate title="" header="" footer="" initScript="">
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>${title}</title>
<#--<link rel="shortcut icon" href="/image/logo.ico">-->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="/css/weixin/weui.css">
    <link rel="stylesheet" href="/css/weixin/weui.min.css">
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
<input type="hidden" id="userid" v-model="userid" value="${userid}">
<div id="content">
    <div>
        <#nested />
    </div>
</div>
<div id="footer">
    @2016 苏州大学
</div>

${footer}
</body>
</html>
</#macro>