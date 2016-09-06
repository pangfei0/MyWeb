define(["jquery", "ajax", "translator", "swal"], function ($, ajax, t, swal) {
    var ws = null;
    var sid = null;

    function init() {
        sid = $('#sid').val();
        console.log(sid);
        buildWebSocket();
    }

    function changeLanguage(language) {
        t.translate("all");
        ajax.get("/api/translation/change/" + language, function () {
            //刷新页面
            window.location.reload();
        });
    }

    function syncData() {
        ajax.get("/api/dataserver/sync", function () {
        });
    }

    function buildWebSocket() {
        ws = new WebSocket('ws://greatdt.dataserver.cn/monitor/websocket');
        ws.onopen = function (e) {
            sendMessageToAPPServer();
        };
        ws.onmessage = function (e) {
            var data = e.data;
            data = (typeof data == 'string') ? JSON.parse(data) : data;

            if (data.msgType === 'FLOATING_WINDOW') {
                console.log(data);
                showCount(data);
            }

        };
        ws.onerror = function (e) {
            console.log('webSocket error');
            ws.close();
        };
        ws.onclose = function (e) {
            console.log('webSocket closed');
        };
    }

    function showCount(data) {
        $('#navCountFault').html(data.elevFaultCounts);
        //$('#navCountAll').html(data.elevAllCounts);
        //$('#countAll').html(data.elevAllCounts);
        $('#countOffline').html(data.elevOfflineCounts);
        $('#countFault').html(data.elevFaultCounts);
        $('#countMaintenance').html(data.elevMaintainCounts);
        $('#countNormal').html(data.elevNormalCounts);
    }

    function sendMessageToAPPServer() {
        var postData = {
            "sid": sid,
            "msgType": "FLOATING_WINDOW"
        };
        ws.send(JSON.stringify(postData));
    }

    init();

    window.changeLanguage = changeLanguage;
    window.syncData = syncData;
});