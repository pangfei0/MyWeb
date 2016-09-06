define(["jquery", "ajax", "toastr", "translator"],function ($, ajax, toastr, trans){
    var ws;
    var tryTime = 0;
    var a = "100000000001";
    var b = "100000000003";
    var c = "100000000004";
    var d = "100000000006";
    var flag = true;

    init();
    function init() {
        buildWebSocket();
    }

    function buildWebSocket() {
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

    function parseStatus(data) {
        var lights = data;
        if(data.Type == a) {
            var temp = true;
            var preFloor = $("#aCurrentFloor").html();
            if(lights.leda == preFloor){
                temp = false;
            }
            if(lights.lamp13 && temp) {
                $("#1Direction").attr('src',"/image/direction-white-up.gif")
            }
            if(lights.lamp21 && temp) {
                $("#1Direction").attr('src',"/image/direction-white-down.gif")
            }
            $("#aCurrentFloor").html(lights.leda);
            if(lights.lamp11 && (!lights.lamp5) && (!lights.lamp4) ){
                $("#elevator1-pic").attr('src',"/image/elevator_opening.gif");
                //setTimeout("changeToOpenStatus()",2000);
            }
            if(lights.lamp6 && (!lights.lamp5) && (!lights.lamp4) ){
                $("#elevator1-pic").attr('src',"/image/elevator_closing.gif");
                flag = true;
            }
            if((flag == true) && (lights.lamp5) && (lights.lamp4) ){
                temp1 = true;
                $("#elevator1-pic").attr('src',"/image/elevator_closed.png");
            }
        } else if(data.Type == d){
            var temp = true;
            var preFloor = $("#eCurrentFloor").html();
            if(lights.leda == preFloor){
                temp = false;
            }
            if(lights.lamp13 && temp) {
                $("#5Direction").attr('src',"/image/direction-white-up.gif")
            }
            if(lights.lamp21 && temp) {
                $("#5Direction").attr('src',"/image/direction-white-down.gif")
            }
            $("#eCurrentFloor").html(lights.leda);
            if(lights.lamp11 && (!lights.lamp5) && (!lights.lamp4) ){
                $("#elevator5-pic").attr('src',"/image/elevator_opening.gif");
                //setTimeout("changeToOpenStatus()",2000);
            }
            if(lights.lamp6 && (!lights.lamp5) && (!lights.lamp4) ){
                $("#elevator5-pic").attr('src',"/image/elevator_closing.gif");
                flag = true;
            }
            if((flag == true) && (lights.lamp5) && (lights.lamp4) ){
                temp1 = true;
                $("#elevator5-pic").attr('src',"/image/elevator_closed.png");
            }

        }else if(data.Type == b) {
            switch (data.led6) {
                case 0:
                    $("#elevator2-pic").attr('src',"/image/elevator_closed.png");
                    break;
                case 1:
                    $("#elevator2-pic").attr('src',"/image/elevator_open.png");
                    break;
                case 2:
                    $("#elevator2-pic").attr('src',"/image/elevator_closing.gif");
                    break;
                case 3:
                    $("#elevator2-pic").attr('src',"/image/elevator_opening.gif");
                    break;
                case 4:
                    //$("#bCurrentFloor").html("电梯故障");
            }
            switch (data.led1) {
                case 0:
                    $("#bDirection").attr('src',"/image/direction-white-down.gif");
                    break;
                case 1:
                    $("#bDirection").attr('src',"/image/direction-white-up.gif");
                    break;
                case 2:
                    $("#bDirection").attr('src',"");
                    break;
            }
            var floor = data.led3*10 + data.led4;
            var preFloor = $("#bCurrentFloor").html();
            if(floor - preFloor <3 ||preFloor - floor <3){
                $("#bCurrentFloor").html(floor);
            }
        } else if (data.Type == c) {
                switch (data.light1) {
                    case 1:
                        $("#cDirection").attr('src',"/image/direction-white-down.gif");
                        break;
                    case 0:
                        $("#cDirection").attr('src',"/image/direction-white-up.gif");
                        break;
                    case 2:
                        $("#cDirection").attr('src',"");
                        break;
                }
                var floor = data.light2 * 10 + data.light3;
                $("#cCurrentFloor").html(floor);
            }

        }

});