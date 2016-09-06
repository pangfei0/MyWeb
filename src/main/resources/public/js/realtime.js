define(["jquery", "ajax", "toastr", "kkpager", "translator", "DataParsing", "typeahead"], function ($, ajax, toastr, kkpager, trans, DataParsing, typeahead) {

    var pageSize = 20;
    var elevatorArray = [];
    var elevatorSeriArray = [];
    var deviceInfo = {};//存储deviceInfo
    var rdtsWS = {}; //装载rdtsWS的对象，{"host : port" : new websokect()}
    var rdtsUrl = {};
    var ws = null;
    var isFav = null;
    var showFav = false;//默认不显示关注
    var sid = null;
    var dataParsing = new DataParsing();
    var selectedItem=null;
    var numberToId = {};
    var obj = new Object();


    function getElevators(type) {
        var item = new Object();
        item.elevStatus = type;
        item.pageIndex = 1;
        ajax.post("/api/dataserver/getStatusElevators", item, function (res){
            parseElevatorData(res, false, 1, true);
        })
    }

    function init() {
        $(".menu-realtime-monitoring").addClass("active");
        isFav = $('#isFav').val();
        selectedItem=$("#selectedItem").val();
        sid = $('#sid').val();
        if (isFav != '' && isFav == 1) {
            showFav = true;
        }
        if(selectedItem==0){
            $('.sb-0').removeClass("active");
            $('.sb-1').removeClass("active");
            $('.sb-0').addClass("active");
        }else{
            $('.sb-0').removeClass("active");
            $('.sb-1').removeClass("active");
            $('.sb-1').addClass("active");
        }
        dealWithItemClicked();
        loadData(1, false, true);
        loadProvince();
        buildWebSocket();
        elevatorTypeAhead();
    }

    function dealWithItemClicked(){
        //sbIcon视图或列表
        $('.sbIcon').on("click",function(e){
            $('.sb-0').removeClass("active");
            $('.sb-1').removeClass("active");
            $(this).addClass("active");
            if($(this).attr("data-val")==0){
                $('.bootstrap-table').hide();
                $('.elevator-content').show();
                $("#selectedItem").val(0);
            }else{
                $('.bootstrap-table').show();//列表形式显示
                $('.elevator-content').hide();//
                $("#selectedItem").val(1);
            }
        });

    }

    //加载数据
    function loadData(pageNumber, refresh, firstLoad) {
        clearData();
        //var obj = new Object();
        obj.pageNumber = pageNumber;
        obj.pageSize = pageSize;
        if (showFav) {
            obj.favorite = 1;
        }
        $.post("/api/elevator/new/search/page", obj, function (res) {
        //默认显示用户关注的电梯,逻辑上有点问题，首页应该显示所有电梯，通过小图标显示关注的电梯
       // $.post("/api/elevator/favorites", obj, function (res) {
            parseElevatorData(res, refresh, pageNumber, firstLoad);
        });
    }

    //解析返回数据
    function parseElevatorData(res, refresh, pageNumber, firstLoad) {
        if (res.success) {
            elevatorArray = [];
            var totalPages = res.data.totalPages;
            var totalRecords = res.data.totalElements;
            var eContent = '';
            var tContent='';
            $.each(res.data.content, function (index, data) {
                var floor = data.floor;
                var number = data.number;
                var iCode = (data.intelHardwareNumber == null ? "" : data.intelHardwareNumber.trim());
                var doorOpen = data.doorOpen;
                var maintenanceStatus = data.maintenanceStatus;
                var faultStatus=data.faultStatus;
                //var maintenanceStatus = data.maintenanceStatus == 20 ? "recondition.png" : "";
                //var faultStatus = data.faultStatus == 20 ? "malfunction.png" : "";
                var isHandled = data.isHandled;
                var address = data.alias;
                var id = data.id;
                var signal = data.signalIntensity;
                var favorite = data.favorite;
                var elevatorStatus = maintenanceStatus == 20 ? "recondition.png" : (faultStatus == 20 ? (isHandled == 20 ? "faultHandling2.png" : "malfunction.png") : "");
                var last = false;
                deviceInfo[iCode] = {
                    'deviceSerial': iCode,
                    'tdSerial': data.tdSerial,
                    'ctrlType': data.controllerType
                };
                elevatorArray[index] = [number, iCode];
                elevatorSeriArray[index] = iCode;
                if (index % 5 == 0) {
                    eContent += '<div class="clearfix">';
                } else if (index % 5 == 4) {
                    last = true;
                }
                var content = '<div class="co-sl-2 ' + (index < 5 ? (last ? 'elevator-trbox' : 'elevator-tbox') : (last ? 'elevator-rbox' : 'elevator-box')) +
                    '"><div class="clearfix w-160"><div class="elevator-img" data="' + id + '"><img class="elevator-sImg" src="/image/' +
                    (doorOpen ? "elevator_open.png" : "elevator_closed.png") + '" regCode="' + iCode + '" dataStatus="00">' +
                    (elevatorStatus == '' ? '' : '<img class="elevator-rImg" src="/image/' + elevatorStatus + '"/>' ) +
                    '</div><div class="elevator-status"><div class="up-area">' +
                    '<img class="signal-img" src="/image/icon_mobile_' + (signal == null ? 0 : signal) + '.png" regCode="' + iCode + '"/>' +
                    '</div><div class="down-area"><div class="floor-ind" regCode="' + iCode + '">' + (floor == null ? '1' : floor) + '</div>' +
                    '<img class="direction-arrow" src="/image/trans-bg.png" regCode="' + iCode + '"/>' +
                    '</div></div></div><div class="elevator-des"><p class="p-num">' + number + '</p><p class="p-addr">' + address + '</p>' +
                   // '<div class="elevator-fav"><a class="fav-common ' + (favorite == 1 ? "fav-img-st" : "fav-img f-hidden") + '" data-fav="' + favorite + '" data-id="' + id + '">' +
                    '<div class="elevator-fav"><a class="fav-common ' + (favorite == 1 ? "fav-img" : "fav-img-st") + '" data-fav="' + favorite + '" data-id="' + id + '">' +
                    '</a></div></div></div>';
                var tval='<tr regCode="' + iCode + '">'+
                    '<td>'+(index+1)+'</td>'+
                    '<td style="cursor:pointer" data="'+id+'">'+formatData(data.number)+'</td>'+
                    '<td style="cursor:pointer" data="'+id+'">'+formatData(data.alias)+'</td>'+
                    '<td>'+formatData()+'</td>'+
                    '<td>'+formatData(data.status == 10 ? "在线":"离线")+'</td>'+
                    '<td>'+formatData(data.faultStatus == 20 ? "故障":"正常")+'</td>'+
                    '<td>'+formatData(data.maintenanceStatus == 20 ? "检修":"正常")+'</td>'+
                    '<td floor-regCode="' + iCode + '">'+formatData(floor)+'</td>'+
                    '<td direction-regCode="' + iCode + '">'+ '<img class="direction-img" src="/image/trans-bg.png" direction-regCode="' + iCode + '"/>' +'</td></tr>';
                eContent += content;
                tContent += tval;
                if (index % 5 == 4) {
                    eContent += '</div>';
                }
            });
            $('.elevator-content').html(eContent);
            $('#v-table').html(tContent);
            if (!refresh) {
                showPageComp(pageNumber, totalPages, totalRecords);
            }
            registerDynamicEvent();
            if (firstLoad) {
                registerStaticEvent();
                buildWebSocket();
            } else {
                clearWebSocket();
                buildWebSocket();
            }
        } else {

        }
    }

    //加载省份
    function loadProvince() {
       /* $('#province').html('<option selected value="-1">'+${t("province")}+'</option>');
        $('#city').html('<option selected value="-1">'+${t("city")}+'</option>');
        $('#region').html('<option selected value="-1">'+${t("region")}+'</option>');
        $('#province').html('<option selected value="-1">'+$(t("province"))+'</option>');
        $('#city').html('<option selected value="-1">'+$(t("city"))+'</option>');
        $('#region').html('<option selected value="-1">'+$(t("region)"))+'</option>');*/
        $.post("/api/area/province", {}, function (res) {
            if (res.success) {
                $.each(res.data, function (index, data) {
                    var opStr = '<option value="' + data.id + '">' + data.name + '</option>';
                    $('#province').append(opStr);
                });
            }
        });
    }

    //加载市/区
    function loadCityOrRegion(type, parentId) {
        $.post("/api/area/children/" + parentId, {}, function (res) {
            if (res.success) {
                if (type == 1) {
                    $('#city').html('<option selected value="-1">--</option>');
                }
                $('#region').html('<option selected value="-1">--</option>');
                $.each(res.data, function (index, data) {
                    var opStr = '<option value="' + data.id + '">' + data.name + '</option>';
                    if (type == 1) {
                        $('#city').append(opStr);
                    } else {
                        $('#region').append(opStr);
                    }
                });
            }
        });
    }

    function registerStaticEvent() {
        $('.cLabel').on('click', function () {
            if ($(this).hasClass('checked')) {
                $(this).removeClass('checked');
                loadData(1, false, false);
            } else {
                $('.cLabel').removeClass('checked');
                $(this).addClass('checked');
                //开始搜索
                //var obj = new Object();
                //obj.deviceStatus = $(this).attr('data-status-type');
                //obj.elevStatus = $(this).attr('data-type');
                //obj.sid = $('#sid').val();
                //obj.page = 1;
                //obj.rows = pageSize;
                //$.ajax({
                //    type: "POST",
                //    url: "http://greatdt.dataserver.cn/baseinfo/elevator/elevator!elevMonitorList.do",
                //    data: obj,
                //    success: function (res) {
                //        console.log(res.data);
                //    }
                //});


                //var obj = new Object();
                obj.search_status_eq = $(this).attr('data-status-type');
                obj.search_faultStatus_eq = $(this).attr('data-fault-type');
                obj.search_maintenanceStatus_eq = $(this).attr('data-maintenance-type');
                obj.search_normal_eq = $(this).attr('data-normal-type');
                if (showFav) {
                    obj.favorite = 1;
                }
                obj.pageSize = pageSize;
                obj.pageNumber = 1;
                $.post("/api/elevator/new/search/page", obj, function (res) {
                    clearSearchArea();
                    parseElevatorData(res, false, 1, false);
                });
            }
        });

        $('#province').on('change', function (e) {
            e.preventDefault();
            loadCityOrRegion(1, $('#province').val());
        });

        $('#city').on('change', function (e) {
            e.preventDefault();
            loadCityOrRegion(2, $('#city').val());
        });

        $('#search-btn').on('click', function (e) {
            e.preventDefault();
            $('.cLabel').removeClass('checked');
            var contractNo = $('#contractNo').val().trim();//电梯工号
            var brand = $('#brand').val().trim();//品牌
            var eType = $('#eType').val();//品牌
            var registrationCode = $('#registrationCode').val().trim();//注册代码
            var iRegistrationCode = $('#iRegistrationCode').val().trim();//智能硬件注册码
            var alias = $('#alias').val().trim();//项目名称
            var provinceId = $('#province').val().trim();//省
            var cityId = $('#city').val().trim();//市
            var regionId = $('#region').val().trim();//县
            if (contractNo == '' &&  brand == '' && eType ==''
                && registrationCode == '' && iRegistrationCode == ''
                && alias == '' && provinceId == -1) {
                loadData(1, true, false);
                return;
            }
            //var obj = new Object();
            if (contractNo != '') {
                obj.search_number_like = contractNo;
            }
            //if (premises != '') {
            //    obj.search_premises_like = premises;
            //}
            if (brand != '-1') {
                obj.search_brandId_eq = brand;
            }
            if (eType != '-1') {
                obj.search_eType_eq = eType;
            }
            if (registrationCode != '') {
                obj.search_regCode_like = registrationCode;
            }
            if (iRegistrationCode != '') {
                obj.search_intelHardwareNumber_like = iRegistrationCode;
            }
            if (alias != '') {
                obj.search_alias_like = alias;
            }
            if (provinceId != -1) {
                obj.search_provinceId_eq = provinceId;
            }
            if (cityId != -1) {
                obj.search_cityId_eq = cityId;
            }
            if (regionId != -1) {
                obj.search_regionId_eq = regionId;
            }
            if (showFav) {
                obj.favorite = 1;
            }
            obj.pageSize = pageSize;
            obj.pageNumber = 1;
            $.post("/api/elevator/new/search/page", obj, function (res) {
                //clearSearchArea();
                parseElevatorData(res, false, 1, false);
            });
        });
    }

    //typeahead处理
    function elevatorTypeAhead() {
        $("#contractNo").typeahead({
            source: function(query, process) {
                var parameter = {number: query};
                ajax.post("/api/elevator/elevatorDto/typeahead", parameter, function (res) {
                    if(res.success){
                        var array = [];
                        $.each(res.data,function(index, ele){
                            numberToId[ele.number] = ele.id;
                            array.push(ele.number);
                        });
                    }
                    process(array);
                });
            },
            delay: 500,
            items: 6,
            updater: function (item) {
                return item;
            },
            afterSelect: function (item) {
                $("#contractNo").val(item);
                $("#contractNo").html(numberToId[item]);
            }
        });
    }


    //注册事件
    function registerDynamicEvent() {
        //$('.elevator-fav').hover(function () {
        //    $(this).find('a').removeClass("f-hidden");
        //}, function () {
        //    if ($(this).find('a').attr('data-fav') != 1) {
        //        $(this).find('a').addClass("f-hidden");
        //    }
        //});

        $('.fav-common').on('click', function (e) {
            updateFavorite($(this).attr('data-id'), $(this).attr('data-fav'), this);
        });

        $('.elevator-img').on('click', function (e) {
            e.preventDefault();
            window.location.href = "/realtime/detail/" + $(this).attr("data");
        });

        /*取消通过具体地址链接到详情，因为布局原因导致关注功能实现有点小问题
        $('.elevator-des').on('click', function (e) {
            e.preventDefault();
            window.location.href = "/realtime/detail/" + $(this).attr("data");
        });*/

        //列表显示时通过设备号或楼盘链接到详情页面
        $('#v-table td').on('click',function(e){
                e.preventDefault();
                window.location.href = "/realtime/detail/" + $(this).attr("data");
        });


    }

    //操作电梯关注
    function updateFavorite(id, favorite, handler) {
        favorite = favorite == 0 ? 1 : 0;
        $.post("/api/elevator/favorite/" + id, {}, function (res) {
            if (res.success) {
                //成功
                if (showFav && favorite == 0) {
                    loadData(1, false, true);
                } else if (favorite == 1) {
                    toastr.success('关注成功');
                    $(handler).removeClass("fav-img-st");
                    $(handler).addClass("fav-img");
                    $(handler).attr("data-fav", favorite);
                } else {
                    toastr.success("取消关注成功");
                    $(handler).removeClass("fav-img");
                    $(handler).addClass("fav-img-st");
                    $(handler).attr("data-fav", favorite);
                }
            } else {
                toastr.error("关注失败");
            }
        });
    }

    function clearSearchArea() {
        $('#contractNo').val("");
        //$('#premises').val("");
        $('#brand').val("-1");
        $('#eType').val("-1");
        $('#registrationCode').val("");
        $('#alias').val("");
        loadProvince();
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


    //获取信号类型
    //function getSignalTypeName(value) {
    //    var types = {
    //        '1': '2G',
    //        '2': '3G',
    //        '3': 'wifi',
    //        '4': '以太网'
    //    };
    //    return types[value];
    //}

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
        $('.signal-img').each(function () {
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
        $(".direction-arrow").each(function () {
            var cRegCode = $(this).attr("regCode");
            if (cRegCode == regCode && cRegCode != null) {
                var currentImg = $(this).attr("src");
                var img = "/image/";
                if (value == '00') {//停止
                    img += 'trans-bg.png';
                } else if (value == '01') {//下行
                    img += 'down-arrow.png';
                } else if (value == '10') {//上行
                    img += 'up-arrow.png';
                } else {
                    img += 'trans-bg.png';
                }
                if (currentImg != img) {
                    $(this).attr("src", img);
                }
            }
        });
        $("tr").each(function () {
            var cRegCode = $(this).attr("regCode");
            if (cRegCode == regCode && cRegCode != null) {
                var currentImg = $("img[direction-regCode=" + cRegCode + "]").attr("src");
                var img = "/image/";
                if (value == '00') {//停止
                    img += 'trans-bg.png';
                } else if (value == '01') {//下行
                    img += 'down-arrow.png';
                } else if (value == '10') {//上行
                    img += 'up-arrow.png';
                } else {
                    img += 'trans-bg.png';
                }
                if (currentImg != img) {
                    $("img[direction-regCode=" + cRegCode + "]").attr("src", img);
                }
            }
        });
    }

    //楼层
    function setFloor(value, regCode) {
        $(".floor-ind").each(function () {
            var cRegCode = $(this).attr("regCode");
            if (cRegCode == regCode && cRegCode != null) {
                if (value == null) {
                    return;
                }
                $(this).html(value);
            }
        });
        $("tr").each(function () {
            var cRegCode = $(this).attr("regCode");
            if (cRegCode == regCode && cRegCode != null) {
                if (value == null) {
                    return;
                }
                $("td[floor-regCode=" + cRegCode + "]").html(value);
            }
        });
    }

    //电梯门状态
    function setDoorStatus(value, regCode) {
        $(".elevator-sImg").each(function () {
            var cRegCode = $(this).attr("regCode");
            if (cRegCode == regCode && cRegCode != null) {
                var timestamp = Date.parse(new Date());
                timestamp = timestamp / 1000;
                if (value == '00' || value == '10') {
                    if (value == '00') {
                        return;
                    }
                    $(this).attr("dataStatus", '00');
                    if($(this).attr("src").indexOf("closing")<0) {
                        $(this).attr("src", "");
                        $(this).attr("src", "/image/elevator_closing.gif?timestamp=" + timestamp);
                    }
                    return;
                }
                if (value == '11' || value == "01") {
                    if (value == '00') {
                        return;
                    }
                    $(this).attr("dataStatus", '11');
                    if($(this).attr("src").indexOf("opening")<0){
                        $(this).attr("src", "");
                        $(this).attr("src", "/image/elevator_opening.gif?timestamp=" + timestamp);
                    }
                    return;
                }
            }
        });
    }

    //实时数据
    function parseRealTimeData() {
        var fun = arguments.callee;
        setTimeout(function () {
            var allData = dataParsing.getAllData();
            var realTimeData = '';
            var tagValue = 1;
            $.each(deviceInfo, function (index, data) {
                var regCode = data.deviceSerial;
                var id = getTdId(data, 0);
                realTimeData = allData[id] ? (allData[id][tagValue] ? allData[id][tagValue].pop() : null) : null;
                if (!realTimeData) {
                    return;
                }
                $.each(realTimeData, function (index, value) {
                    if (value.name == '运行方向') {
                        setDirection(value.escapeValue, regCode);
                    } else if (value.name == '楼层') {
                        setFloor(value.escapeValue, regCode);
                    } else if (value.name == '开关门状态') {
                        setDoorStatus(value.escapeValue, regCode);
                    }
                });
            });
            setTimeout(function () {
                fun();
            }, 1000);
        }, 1000);
    }

    /****
     readyState的值表示：
     0    CONNECTING        连接尚未建立
     1    OPEN            WebSocket的链接已经建立
     2    CLOSING            连接正在关闭
     3    CLOSED            连接已经关闭或不可用
     ****/
    function buildWebSocket() {
        ws = new WebSocket('ws://greatdt.dataserver.cn/monitor/websocket');
        ws.onopen = function (e) {
            sendMessageToAPPServer();
        };
        ws.onmessage = function (e) {
            var data = e.data;
            data = (typeof data == 'string') ? JSON.parse(data) : data;

            // 实时数据请求响应
            if (data.msgType == 'GET_TD_REALTIME_DATA_RSP' && data.errorCode == 0) {
                buildRdtsWebSocket(data);
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


    function buildRdtsWebSocket(data) {
        var host = data.rdtsHost,
            deviceSerial = data.deviceSerial,
            rdtsWsPort = data.rdtsWsPort,
            wsURL = 'ws://' + host + ':' + rdtsWsPort;
        var tRDWS = rdtsWS[wsURL];
        if (tRDWS) {
            //发送数据
            sendMessageToRDTS(data, tRDWS);
            return;
        }
        rdtsUrl[deviceSerial] = wsURL;

        tRDWS = rdtsWS[wsURL] = new WebSocket(wsURL);

        tRDWS.onopen = function (e) {
            //发送数据
            sendMessageToRDTS(data, tRDWS);
            sendHeartBeatPackage(tRDWS, 2, data);
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
                var regCode = data.deviceSerial;
                var id = getTdId(data, 0);
                if ($.inArray(regCode, elevatorSeriArray) >= 0) {
                    dataParsing.addData({
                        "id": id,
                        "dataSource": data,
                        "ctrlType": deviceInfo[regCode].ctrlType
                    });
                    console.log('透传数据成功！');
                }
            }
        };

        tRDWS.onerror = function (e) {
            tRDWS.close();
        };

        tRDWS.onclose = function (e) {
            console.log('WebSocket closed!');
        };
    }

    function rebuildWebsocket(data) {
        var deviceSerial = data.deviceSerial;
        var wsURL = rdtsUrl[deviceSerial];
        var tRDWS = rdtsWS[wsURL] = new WebSocket(wsURL);
        tRDWS.onopen = function (e) {
            //发送数据
            sendMessageToRDTS(data, tRDWS);
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
                if (data.tdDataTags * 1 !== 1) {
                    return;
                }
                var regCode = data.deviceSerial;
                var id = getTdId(data, 0);
                if ($.inArray(regCode, elevatorSeriArray) >= 0) {
                    dataParsing.addData({
                        "id": id,
                        "dataSource": data,
                        'ctrlType': deviceInfo[regCode].ctrlType
                    });
                }
            }
        };

        tRDWS.onerror = function (e) {
            tRDWS.close();
        };

        tRDWS.onclose = function (e) {
            console.log('WebSocket closed!');
        };
    }

    function sendHeartBeatPackage(webSoc, type, data) {
        var fun = arguments.callee;
        var params = {
            "from": "evdhs_003@ece",
            "proxy": "",
            "to": "gdhs_002@inovance",
            "version": "1.0",
            "msgType": 'SERVER_HEARTBEAT_REQ',
            "sid": sid
        };
        if (webSoc && webSoc.readyState == 1) {
            webSoc.send(JSON.stringify(params));
        }
        if (type == 2 && (webSoc.readyState == 2 || webSoc == 3)) {
            webSoc.close();
            rebuildWebsocket(data);
        }
        return setTimeout(function () {
            fun(webSoc, type, data);
        }, 10000);
    }

    function sendMessageToAPPServer() {
        $.each(elevatorArray, function (index, data) {
            var postData = {
                "from": "evdhs_003@ece",
                "proxy": "",
                "to": "gdhs_002@inovance",
                "version": "1.0",
                "msgType": "GET_TD_REALTIME_DATA_REQ",
                "deviceSerial": data[1],
                "tdSerial": "1",
                "sid": sid,
                "tdDataTags": [{"value": 1, "samplingPeriod": 3}]
            };
            ws.send(JSON.stringify(postData));
            querySignalByData(data);
        });
        parseRealTimeData();
        sendHeartBeatPackage(ws, 1, null);
    }

    //查询信号
    function querySignalByData(data) {
        var fun = arguments.callee;
        var postData = {
            "deviceSerial": data[1],
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
            fun(data);
        }, 20000);
    }

    //向应用服务器发送请求
    function sendMessageToRDTS(data, rdtsWS) {
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
        if (rdtsWS && rdtsWS.readyState == 1) {
            rdtsWS.send(JSON.stringify(params));
        } else {
            //重试
            if (rdtsWS && rdtsWS.readyState != 4) {
                setTimeout(function () {
                    fun(data, rdtsWS);
                }, 600);
            }
        }
    }

    /****Common Tools****/
    function clearData() {
        rdtsWS = {};
        deviceInfo = {};
        elevatorArray = [];
        elevatorSeriArray = [];
    }

    function clearWebSocket() {
        ws.close();
        ws = null;
        $.each(rdtsWS, function (index, data) {
            data.close();
            data = null;
        });
        rdtsWS = {};
    }

    function getTdId(data, type) {
        return data.deviceSerial + data.tdSerial + (data.type ? data.type : type);
    }

    function formatData(data){
        if(data==null){
            return "";
        }else{
            return data;
        }
    }

    init();

    window.getElevators = getElevators;
});