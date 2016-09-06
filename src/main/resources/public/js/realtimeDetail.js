define(["jquery", "ajax", "toastr", "translator", "DataParsing"], function ($, ajax, toastr, trans, DataParsing) {

    var ws;
    var tRDWS;
    var sid;
    var deviceSerial = "";
    var dataParsing = new DataParsing();
    var deviceInfo = {};
    var redressStatus = {};

    function init() {
        $(".menu-realtime-monitoring").addClass("active");
        sid = $('#sid').val();
        $("#eleUp").hide();
        $("#eleDown").hide();
        $('#faultDiv').hide();//默认电梯故障代码不显示
        setData();
    }

    //设值
    function setData() {
        $.get("/api/elevator/full/" + $('#eleId').val(), function (res) {
            if (res.success) {
                var data = res.data;
                if (data.intelHardwareNumber) {
                    deviceSerial = parseData(data.intelHardwareNumber);
                }
                deviceInfo = {
                    'deviceSerial': deviceSerial,
                    'tdSerial': data.tdSerial,
                    'ctrlType': data.controllerType
                };
                setRedressStatus(getTdId(deviceInfo, 0));

                $('#elevatorNum').html(parseData(data.number));//电梯工号
                $('#elevatorAddr').html(parseData(data.alias));//项目名称
               // $('#elevatorAddr').css("font-size","11px");//设置项目名称字体
                $('#floor-num').html(parseData(data.floor));
                $('#controlType').html('&nbsp;' + parseData(data.controllerType));//控制器类型
                $('#registerCode').html(parseData(data.regCode));//注册代码
                $('#sRegisterCode').html(deviceSerial);//智能硬件注册码
                $('#elevatorCode').html(parseData(data.elevatorModel));//电梯型号
                $('#elevatorType').html(data.elevatorType == 1 ? "直梯" : (data.elevatorType == 2 ? "扶梯" : ""));//电梯类型
                $('#releaseDate').html(parseData(data.productionTime));//出厂日期
                if(data.usageType==null)
                {
                    $('#environment').html("");
                }
                else{
                   var usageType1=data.usageType;
                    switch (usageType1){
                        case 0: $('#environment').html("待定");break;
                        case 1:$('#environment').html("办公综合楼宇");break;
                        case 2:$('#environment').html("工厂企业楼宇");break;
                        case 3:$('#environment').html("机关单位楼宇");break;
                        case 4:$('#environment').html("政府部门楼宇");break;
                        case 5:$('#environment').html("住宅小区楼宇");break;
                        case 6:$('#environment').html("商住综合楼宇");break;
                        case 10:$('#environment').html("重点宾馆酒店");break;
                        case 11:$('#environment').html("重点公共场馆");break;
                        case 12:$('#environment').html("重点场所车站");break;
                        case 13:$('#environment').html("重点场所机场");break;
                        case 14:$('#environment').html("重点场所商场");break;
                        case 15:$('#environment').html("重点学院校园");break;
                        case 16:$('#environment').html("重点场所医院");break;
                        case 17:$('#environment').html("重点娱乐场所");break;
                        case 98:$('#environment').html("研发调试");break;
                        case 99:$('#environment').html("其他场所楼宇");break;
                    }
                }
                $('#environment').html(data.usageType==null?"":(data.usageType==0?"待定":"住宅小区楼宇"));//使用场合,目前传来3个值：空，0：待定，5：住宅小区楼宇
                $('#levelStD').html(parseData(data.station == null ? "" : data.station));//层站门
                $('#producer').html(parseData(data.manufacturer == null ? "" : data.manufacturer.name));//制造商名称
                $('#installation').html(parseData(data.installCompany == null ? "" : data.installCompany.name));//安装单位
                $('#detailAddr').html(parseData(data.address));//详细地址
                $('#useUnit').html(parseData(data.userCompany == null ? "" : data.userCompany.name));//使用单位
                $('#tenement').html(parseData(data.userCompany == null ? "" : data.userCompany.name));//物业单位即使用单位
                $('#maintenance').html(parseData(data.maintainer == null ? "" : data.maintainer.name));//维保单位
                $('#dPhone').html(parseData(data.dutyPhone));//24小时值班电话
                if(data.maintainer!=null){
                    $('#contactPerson').html(parseData(data.maintainer.mainTainName == null ? "" : (data.maintainer.mainTainPhone!=null?(data.maintainer.mainTainName+" / "+data.maintainer.mainTainPhone):data.maintainer.mainTainName)));//维保联系人
                }else{
                    $('#contactPerson').html("");
                }
                if(data.userCompany!=null){
                    $('#tenementCP').html(parseData(data.userCompany.managerName == null ? "" : (data.userCompany.managerPhone!=null?(data.userCompany.managerName+" / "+data.userCompany.managerPhone):data.userCompany.managerName)));//物业联系人
                }else{
                    $('#tenementCP').html("");
                }
                $('#lastCheckDate').html(parseData(data.lastcheckDate));//上次年检日期
                $('#lastMtDate').html(parseData(data.lastUpkeepDate));//上次维保日期
                $('#signalImg').attr("src", "/image/icon_mobile_" + (data.signalIntensity ? data.signalIntensity : 0) + ".png");
                $('#signalImg').attr("regCode", deviceSerial);


                $('#elevator-img').attr("regCode", deviceSerial);
                var maintenanceStatus = data.maintenanceStatus;
                var faultStatus = data.faultStatus;
                if (maintenanceStatus == 20) {
                    $('#recImg').attr("src", "/image/Recondition-On.png");
                }
                //点亮故障图标，显示故障代码
                if (faultStatus == 20) {
                    $('#malImg').attr("src", "/image/Malfuncion-On.png");
                    $('#faultDiv').show();
                    $('#floor-num').hide();
                    $('#eleUp').hide();
                    $('#eleDown').hide();
                    $('#faultCode').attr("regCode", deviceSerial);//故障代码

                }
                if(faultStatus!=20)
                {
                    $('#faultDiv').hide();
                    $('#floor-num').show();
                    $('#eleUp').show();
                    $('#eleDown').show();
                    $('#floor-num').attr("regCode", deviceSerial);//楼层
                    $('#eleUp').attr("regCode", deviceSerial);//上行
                    $('#eleDown').attr("regCode", deviceSerial);//下行
                    if(deviceSerial==""){
                        $('#faultDiv').show();
                        $('#eleUp').hide();
                        $('#eleDown').hide();
                        $('#floor-num').hide();
                        $('#faultDiv').html('正在连接中...');
                    }

                }
                buildWebSocket();
            } else {
                toastr.error("获取数据失败!");
            }
        });
    }


    function buildWebSocket() {
        ws = new WebSocket('ws://greatdt.dataserver.cn/monitor/websocket');
        ws.onopen = function (e) {
            sendMessageToAPPServer();
            sendHeartBeatPackage(1, null);
        };
        ws.onmessage = function (e) {
            var data = e.data;
            data = (typeof data == 'string') ? JSON.parse(data) : data;

            // 实时数据请求响应
            if (data.msgType == 'GET_TD_REALTIME_DATA_RSP' && data.errorCode == 0) {
                buildRdtsWebSocket(data, false);
            }

            //心跳响应
            if (data.msgType == 'SERVER_HEARTBEAT_RSP') {
                console.log('心跳响应1：' + JSON.stringify(data));
            }

            //实时数据信号强度
            if (data.msgType == 'GET_SINGLE_DATA') {
                dealWithRealSignal(data);
            }
        };
        ws.onerror = function (e) {
            ws.close();
        };
        ws.onclose = function (e) {
            console.log("WebSocket close");
        };
    }

    function buildRdtsWebSocket(data, rebuild) {
        var wsURL = 'ws://' + data.rdtsHost + ':' + data.rdtsWsPort;
        tRDWS = new WebSocket(wsURL);

        tRDWS.onopen = function (e) {
            //发送数据
            sendMessageToRDTS(data);
            if (!rebuild) {
                sendHeartBeatPackage(2, data);
            }
        };

        tRDWS.onmessage = function (e) {
            var data = e.data;
            data = (typeof data == 'string') ? JSON.parse(data) : data;

            //实时数据请求响应
            if (data.msgType === 'TD_REALTIME_DATA_ESTABLISH_RSP') {
            }

            //心跳响应
            if (data.msgType === 'SERVER_HEARTBEAT_RSP') {
                console.log('心跳响应2：' + JSON.stringify(data));
            }

            //实时数据透传数据
            if (data.msgType === 'DEVICE_DATA_TRANSFER') {
                var tagValue = data.tdDataTags * 1;
                var tdId = getTdId(data, 0);
                console.log("tagValue:" + tagValue + ",data:" + JSON.stringify(data));
                dataParsing.addData({
                    "id": tdId,
                    "dataSource": data,
                    'ctrlType': deviceInfo.ctrlType
                });
                fillRealTimeData(tagValue, tdId);
            }
        };

        tRDWS.onerror = function (e) {
            tRDWS.close();
        }

        tRDWS.onclose = function (e) {
            console.log('WebSocket closed!');
        };
    }

    function sendHeartBeatPackage(type, data) {
        var fun = arguments.callee;
        var webSoc = type == 1 ? ws : tRDWS;
        var params = {
            "from": "evdhs_003@ece",
            "proxy": "",
            "to": "gdhs_002@inovance",
            "version": "1.0",
            "msgType": 'SERVER_HEARTBEAT_REQ',
            "sid": sid
        };
        if (webSoc.readyState == 1) {
            webSoc.send(JSON.stringify(params));
        }
        if (type == 2 && (webSoc.readyState == 2 || webSoc == 3)) {
            webSoc.close();
            buildRdtsWebSocket(data, true);
        }
        return setTimeout(function () {
            fun(type, data);
        }, 10000);
    }


    function sendMessageToAPPServer() {
        var postData = {
            "from": "evdhs_003@ece",
            "proxy": "",
            "to": "gdhs_002@inovance",
            "version": "1.0",
            "msgType": "GET_TD_REALTIME_DATA_REQ",
            "deviceSerial": deviceSerial,
            "tdSerial": "1",
            "sid": sid,
            "tdDataTags": [{"value": 1, "samplingPeriod": 3}, {"value": 2, "samplingPeriod": 3}, {"value": 3, "samplingPeriod": 3}]
        };
        ws.send(JSON.stringify(postData));
        querySignalByData();
    }

    function sendMessageToRDTS(data) {
        var fun = arguments.callee;
        var params = {
            "from": data.from,
            "proxy": "",
            "to": "gdhs_002@inovance",
            "version": "1.0",
            "securityCode": data.securityCode,
            "msgType": "TD_REALTIME_DATA_ESTABLISH_REQ",
            "sid": sid
        };
        if (tRDWS.readyState == 1) {
            tRDWS.send(JSON.stringify(params));
        } else {
            //重试
            if (tRDWS.readyState > 1) {
                setTimeout(function () {
                    fun(data, tRDWS);
                }, 600);
            }
        }
    }

    //实时数据
    function fillRealTimeData(tagValue, tdId) {
        var regCode = deviceInfo.deviceSerial;
        var data = dataParsing.getFirstData(tdId, tagValue);
        if (!data) {
            return;
        }
        var flag = false;
        var upkeepStatus = 0;
        var communicationStatus = 0;
        var faultStatus = 0;
        if (tagValue === 1) {
            $.each(data, function (index, value) {
                if (value.name == '检修状态') {
                    if (value.escapeValue == 1) {
                        upkeepStatus = 2;
                    } else {
                        upkeepStatus = 1;
                    }

                    if (redressStatus[tdId]) {
                        if (redressStatus[tdId].upkeep != upkeepStatus) {
                            redressStatus[tdId].upkeep = upkeepStatus;
                            flag = true;
                        }

                    } else {
                        redressStatus[tdId].upkeep = upkeepStatus;
                        flag = true;
                    }
                }

                if (value.name == '串口通信异常') {
                    if (value.escapeValue == 1) {
                        communicationStatus = 2;
                    } else {
                        communicationStatus = 1;
                    }

                    if (redressStatus[tdId]) {
                        if (redressStatus[tdId].chuankou != communicationStatus) {
                            redressStatus[tdId].chuankou = communicationStatus;
                            flag = true;
                        }
                    } else {
                        redressStatus[tdId].chuankou = communicationStatus;
                        flag = true;
                    }
                }

                if (value.name == '电梯故障代码') {
                    if (value.escapeValue != 0) {
                        faultStatus = 2;
                        setFailurCode(value,tdId);//设置故障代码
                    } else {
                        faultStatus = 1;
                    }

                    if (redressStatus[tdId]) {
                        if (redressStatus[tdId].failureCode != faultStatus) {
                            redressStatus[tdId].failureCode = faultStatus;
                            flag = true;
                        }
                    } else {
                        redressStatus[tdId].failureCode = faultStatus;
                        flag = true;
                    }
                }


                if (value.name == '运行方向') {
                    setDirection(value.escapeValue, regCode);
                } else if (value.name == '楼层') {
                    setFloor(value.escapeValue, regCode);
                } else if (value.name == '开关门状态') {
                    setDoorStatus(value.escapeValue,regCode);
                }

            });

            if (flag) {
                ws.send(JSON.stringify({
                    'msgType': 'UPDATE_ELEV_STATUS',
                    'regCode': regCode,
                    'faultStatus': faultStatus, //（1.正常 2.故障）
                    'upkeepStatus': upkeepStatus, //（1.正常 2.检修）
                    'communicationStatus': communicationStatus //(1.正常 2.串口通信异常)
                }));
            }
            return;
        }

        if (tagValue == 2) {
            /* 内召数据 */
            var innerData = dataParsing.getInnerData(data);
            $('#innerCall').empty();
            if (innerData && innerData.length > 0) {
                var tempInnerData = [];
                $.each(
                    innerData,
                    function (i, v) {
                        tempInnerData.push(v);
                    }
                );
                $('#innerCall').html('<span class="num">' + tempInnerData.join('</span><span class="num">') + '</span>');
            }
            return;
        }


        if (tagValue == 3) {
            /* 外召数据 */
            var outerData = dataParsing.getOuterData(data);
            var callUp = outerData ? outerData.callUp : null;
            var callDown = outerData ? outerData.callDown : null;

            $('#upCall').empty();
            if (callUp && callUp.length > 0) {
                var tempCallUp = [];
                $.each(
                    callUp,
                    function (i, v) {
                        tempCallUp.push(v);
                    }
                );
                $('#upCall').html('<span class="num">' + tempCallUp.join('</span><span class="num">') + '</span>');
            }

            $('#downCall').empty();
            if (callDown && callDown.length > 0) {
                var tempCallDown = [];
                $.each(
                    callDown,
                    function (i, v) {
                        tempCallDown.push(v);
                    }
                );
                $('#downCall').html('<span class="num">' + tempCallDown.join('</span><span class="num">') + '</span>');
            }
        }

    }
    /* 设置电梯故障代码 */
    function setFailurCode(o,tdId){
        var value = o.escapeValue;
        if (value != 0) {
            $('#faultCode').html('故障代码:E' + value);
        }else
        {
            $('#faultDiv').hide();
        }

    }


    //查询信号
    function querySignalByData() {
        var fun = arguments.callee;
        var postData = {
            "deviceSerial": deviceSerial,
            "msgType": "DEVICE_DATA_TRANSFER",
            "tdSerial": '',
            "transferData": "",
            "sid": sid,
            "transferMsgType": 51
        };
        if (ws && ws.readyState == 1) {
            ws.send(JSON.stringify(postData));
        }
        return setTimeout(function () {
            fun();
        }, 20000);
    }


    //处理信号强度
    function dealWithSignalStrength(signalType, signalValue) {
        if (signalType == 4) {
            return 0;
        }
        var v = signalType == 3 ? Math.round((signalValue + 94) / 94 * 100) : Math.round(signalValue / 31 * 100);
        var gridNum = signalType == 3 ? 4 : 5,
            fullNum = 100;
        if (v <= 0) {
            return 0;
        } else if (v <= (fullNum / gridNum) * 1) {
            return 1;
        } else if ((v <= (fullNum / gridNum) * 2) && (v > (fullNum / gridNum) * 1)) {
            return 2;
        } else if ((v <= (fullNum / gridNum) * 3) && (v > (fullNum / gridNum) * 2)) {
            return 3;
        } else if ((v <= (fullNum / gridNum) * 4) && (v > (fullNum / gridNum) * 3)) {
            return 4;
        } else if ((v <= (fullNum / gridNum) * 5) && (v > (fullNum / gridNum) * 4) && signalType != 3) {
            return 5;
        }
        return 0;
    }

    //处理实时信号
    function dealWithRealSignal(data) {
        var regCode = data.regCode;
        var signalType = data.signalType;
        var signalValue = data.signalValue;
        var signalNum = dealWithSignalStrength(signalType, signalValue);
        $('#signalImg').each(function () {
            var cRegCode = $(this).attr("regCode");
            if (cRegCode == regCode && cRegCode != null) {
                if (signalType == 4) {
                    $(this).attr("src", "/image/icon_ethernet.png");
                } else if (signalType == 3) {
                    $(this).attr("src", "/image/icon_swifi_" + signalNum + ".png");
                } else {
                    $(this).attr("src", "/image/icon_mobile_" + signalNum + ".png");
                }
            }
        });
    }

    //电梯行驶方向
    function setDirection(value, regCode) {
        var cRegCode = $("#eleUp").attr("regCode");
        if (cRegCode == regCode && cRegCode != null) {
            if (value == '01') {//下行
                $("#eleUp").hide();
                $("#eleDown").show();
            } else if (value == "10") {//上行
                $("#eleUp").show();
                $("#eleDown").hide();
            } else {
                $("#eleUp").hide();
                $("#eleDown").hide();
            }
        }
    }

    //楼层
    function setFloor(value, regCode) {
        if (value == null) {
            return;
        }
        var cRegCode = $("#floor-num").attr("regCode");
        if (cRegCode == regCode && cRegCode != null) {
            $("#floor-num").html(value);
        }
    }

    //电梯门状态
    function setDoorStatus(value, regCode) {
        var cRegCode = $("#elevator-img").attr("regCode");
        if (cRegCode == regCode && cRegCode != null) {
            var timestamp = Date.parse(new Date());
            timestamp = timestamp / 1000;
            if (value == '00' || value == '10') {
                if (value == '00') {
                    return;
                }
                $("#elevator-img").attr("dataStatus", '00');
                if ($("#elevator-img").attr("src").indexOf("closing")<0) {
                    $("#elevator-img").attr("src", "");
                    $("#elevator-img").attr("src", "/image/elevator_big_closing.gif?timestamp=" + timestamp);
                }

                return;
            }
            if (value == '11' || value == "01") {
                if (value == '00') {
                    return;
                }
                $("#elevator-img").attr("dataStatus", '11');
                if ($("#elevator-img").attr("src").indexOf("opening")<0) {
                    $("#elevator-img").attr("src", "");
                    $("#elevator-img").attr("src", "/image/elevator_big_opening.gif?timestamp=" + timestamp);
                }
                return;
            }
        }
    }



    function getTdId(data, type) {
        return data.deviceSerial + data.tdSerial + (data.type ? data.type : type);
    }

    //设置电梯的状态
    function setRedressStatus(tdId) {
        if (redressStatus[tdId]) {
            return;
        }

        redressStatus[tdId] = {
            "upkeep": 0,
            "chuankou": 0,
            "failureCode": 0
        }
    }

    function parseData(originData) {
        if (originData) {
            return originData;
        } else {
            return "";
        }
    }

    init();
});