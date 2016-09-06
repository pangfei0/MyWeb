define(["jquery", "ajax", "toastr", "kkpager", "translator"], function($, ajax, toastr, kkpager, trans){
    var pageSize = 20;
    var obj = {};     //查询参数
    var ws;
    var tryTime = 0;

    function init() {
        $('.multi-brand-elevator').addClass("active");
        loadData(1,false,true);
        buildWebSocket();
    }

    function loadData(pageNumber, refresh, firstLoad) {
        obj.pageNumber = pageNumber;
        obj.pageSize = pageSize;
        $.post("/api/multiElevator/search/page", obj, function(res){
            parseElevator(res, refresh, pageNumber, firstLoad);

        });
    }

    function parseElevator(res, refresh, pageNumber, firstLoad) {
        if(res.success) {
            var totalPages = res.data.totalPages;
            var totalRecords = res.data.totalElements;
            var eContent = '';
            $.each(res.data.content, function (index, data){
                var number = data.number;
                var address = data.address;
                var eleId = data.id;
                var iCode = data.protocolType;
                var floor = data.floor;
                var faultStatus=data.faultStatus;
                var maintenanceStatus = data.maintenanceStatus;
                var isHandled = data.isHandled;
                var elevatorStatus = maintenanceStatus == 20 ? "recondition.png" : (faultStatus == 20 ? (isHandled == 20 ? "faultHandling2.png" : "malfunction.png") : "");
                var last = false;
                if (index % 5 == 0) {
                    eContent += '<div class="clearfix">';
                } else if (index % 5 == 4) {
                    last = true;
                }
                var content = '<div class="co-sl-2 ' + (index < 5 ? (last ? 'elevator-trbox' : 'elevator-tbox') : (last ? 'elevator-rbox' : 'elevator-box')) +
                    '"><div class="clearfix w-160"><div class="elevator-img" eleId="' + eleId + '" data="' + iCode + '"><img class="elevator-sImg" src="/image/elevator_closed.png" regCode="' + iCode + '"/>' +
                    (elevatorStatus == '' ? '' : '<img class="elevator-rImg" src="/image/' + elevatorStatus + '"/>' ) +
                    '</div><div class="elevator-status"><div class="up-area">' +
                    '<img class="signal-img" src="/image/icon_mobile_4.png" regCode="' + iCode + '"/>' +
                    '</div><div class="down-area"><div class="floor-ind" regCode="' + iCode + '">' + (floor == null ? '1' : floor) + '</div>' +
                    '<img class="direction-arrow" src="/image/trans-bg.png" regCode="' + iCode + '"/>' +
                    '</div></div></div><div class="elevator-des"><p class="p-num">' + number + '</p><p class="p-addr">' + address + '</p>' +
                    '</div></div>';
                eContent += content;
                if (index % 5 == 4) {
                    eContent += '</div>';
                }
                $('.elevator-content').html(eContent);
                if (!refresh) {
                    showPageComp(pageNumber, totalPages, totalRecords);
                }
                registerDynamicEvent();
            });
        }
    }

    function buildWebSocket(){
        ws = new WebSocket('ws://58.210.173.54:50020/echo');
        ws.onopen = function(e) {
            console.log("client connected");
        };
        ws.onmessage = function(e) {
            var data = e.data;
            data = (typeof data == 'string') ? JSON.parse(data) : data;
            parseStatus(data);
        };
        ws.onerror = function(e) {
            ws.close();
        };
        ws.onclose = function(e) {
            if (tryTime < 10) {
                setTimeout(function () {
                    ws = null;
                    tryTime ++;
                    buildWebSocket();
                }, 1000);
            } else {
                tryTime = 0;
            }
            console.log("webSocket closed");
        };
        window.onbeforeunload = function() {
            ws.close();
        }
    }

    //实时数据
    function parseStatus(data) {
        var fun = arguments.callee;
        setTimeout(function () {
            $(".elevator-img").each(function () {
                var cRegCode = $(this).attr("data");
                if(cRegCode == data.Type && cRegCode != null){
                    $(".floor-ind[regCode=" + data.Type + "]").html(data.floor);
                    var directionTemp = $(".direction-arrow[regCode=" + data.Type + "]");
                    var doorTemp = $(".elevator-sImg[regCode=" + data.Type + "]");
                    if(data.direction && directionTemp.attr("src").indexOf(data.direction)<0){
                        directionTemp.attr("src","");
                        directionTemp.attr("src","/image/"+ data.direction + "-arrow.png");
                    }
                    if(data.doorStatus && doorTemp.attr("src").indexOf(data.doorStatus)<0){
                        doorTemp.attr("src","");
                        doorTemp.attr("src","/image/elevator_" + data.doorStatus + (data.doorStatus.indexOf("ing") > -1 ? ".gif":".png"));
                    }
                }
            });
            setTimeout(function () {
                fun();
            }, 1000);
        }, 1000);
    }

    function registerDynamicEvent(){
        $('.elevator-img').on('click', function (e) {
            e.preventDefault();
            window.location.href = "/realtime/detail/" + $(this).attr("eleId");
        });
    }

    //生成分页控件
    function showPageComp(pageNumber, totalPages, totalRecords) {
        $("#kkpager").html('');
        kkpager.total = totalPages;
        kkpager.totalRecords = totalRecords;
        kkpager.generPageHtml({
            pno: pageNumber,
            mode: 'click', //可选，默认就是link
            //总页码
            total: totalPages,
            //总数据条数
            totalRecords: totalRecords,
            lang: {
                firstPageText: trans.translate('firstPageText'),
                firstPageTipText: trans.translate('firstPageText'),
                lastPageText: trans.translate('lastPageText'),
                lastPageTipText: trans.translate('lastPageText'),
                prePageText: trans.translate('prePageText'),
                prePageTipText: trans.translate('prePageText'),
                nextPageText: trans.translate('nextPageText'),
                nextPageTipText: trans.translate('nextPageText'),
                totalPageBeforeText: trans.translate('totalPageBeforeText'),
                totalPageAfterText: trans.translate('totalPageAfterText'),
                currPageBeforeText: trans.translate('currPageBeforeText'),
                currPageAfterText: trans.translate('buttonTipAfterText'),
                totalInfoSplitStr: '/',
                totalRecordsBeforeText: trans.translate('totalPageBeforeText'),
                totalRecordsAfterText: trans.translate('totalRecordsAfterText'),
                gopageBeforeText: trans.translate('gopageBeforeText'),
                gopageButtonOkText: trans.translate('confirm'),
                gopageAfterText: trans.translate('buttonTipAfterText'),
                buttonTipBeforeText: trans.translate('buttonTipBeforeText'),
                buttonTipAfterText: trans.translate('buttonTipAfterText')
            },
            click: function (n) {
                loadData(n, true, false);
                //处理完后可以手动条用selectPage进行页码选中切换
                this.selectPage(n);
                return false;
            },
            //getHref是在click模式下链接算法，一般不需要配置，默认代码如下
            getHref: function (n) {
                return '#';
            }

        }, true);
    }

    init();
});