define(["jquery", "vue", "ajax", "kkpager", "ditu", "ditu_markerCluster", "raphael", "lib/Raphael/chinaMap", "translator", "bootstrap","toastr","typeahead"],
    function ($, Vue, ajax, kkpager, BMap, BMapLib, Raphael, chinaMap, translator, bootstrap,toastr,typeahead) {

        var map;
        var vm;
        var number;
        var elevatorId;
        var elevatorProjectName;
        var elevatorAddress;
        var elevatorType;
        var elevatorPoint;
        var maintenanceChoosen = {};
        var nameOfMaintenanceChoosen;
        var squareArea;
        var nameToId={};
        function init() {
            $(".menu-grid-rescue").addClass("active");
            initRescueMap();
            initData();
            loadRescueBills(1);

            //locateElevatorAndMaintenances(number);
            window.navToMap = navToMap;
            window.locateElevatorAndMaintenances = locateElevatorAndMaintenances;
            window.sendBill = sendBill;
            window.sendToMaintenance = sendToMaintenance;
            window.messagePushToMaintenances = messagePushToMaintenances;
            window.cloudPushToMaintenances = cloudPushToMaintenances;
        }
        function getOtherPerson(elevatorId)
        {//获取其他人员
            $("#otherPerson").typeahead({
                source: function(query, process) {
                    var parameter = {name: query,elevatorId:elevatorId};
                    ajax.post("/api/netRescue/searchPersonName", parameter, function (res) {
                        if(res.success){
                            var array = [];
                            $.each(res.data,function(index, ele){
                                nameToId[ele.name] = ele.id;
                                array.push(ele.name);
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
                    $("#otherPerson").val(item);
                }
            });
        }
        function initRescueMap() {
            var mapHeight = $("#content").height() - 50;
            var mapWidth = $("#content > div").width() - 250;
            $("#map").height(mapHeight).width(mapWidth).css({left: 10});


            map = new BMap.Map("map");
            map.centerAndZoom(new BMap.Point(105.006609, 35.667), 5);
            map.enableScrollWheelZoom();
            map.enableDragging();

            //添加城市列表控件
            //var size = new BMap.Size(10, 20);
            //map.addControl(new BMap.CityListControl({
            //    anchor: BMAP_ANCHOR_TOP_LEFT,
            //    offset: size
            //}));


            //添加搜索框控件
            function SearchControl() {
                // 默认停靠位置和偏移量
                this.defaultAnchor = BMAP_ANCHOR_TOP_LEFT;
                this.defaultOffset = new BMap.Size(30, 20);
            }

            // 通过JavaScript的prototype属性继承于BMap.Control
            SearchControl.prototype = new BMap.Control();

            // 自定义控件必须实现自己的initialize方法,并且将控件的DOM元素返回
            // 在本方法中创建个div元素作为控件的容器,并将其添加到地图容器中
            SearchControl.prototype.initialize = function (map) {
                // 创建一个DOM元素
                var div = document.createElement("input");
                div.setAttribute("id", "elevatorNumber");
                div.setAttribute("type", "text");
                div.setAttribute("class", "form-control");
                div.setAttribute("placeholder", "Search");
                // 设置样式
                div.style.width = "100px";
                div.style.border = "1px solid gray";
                div.style.backgroundColor = "white";

                div.onkeyup = function () {
                    if (event.keyCode == 13) {
                        initData();
                        number = $("#elevatorNumber").val();
                        locateRescueElevator(number);
                        //var bs = map.getBounds();   //获取可视区域
                        //var bssw = bs.getSouthWest();   //可视区域左下角
                        //var bsne = bs.getNorthEast();   //可视区域右上角
                        //squareArea = {
                        //    lng1: bssw.lng,
                        //    lng2: bsne.lng,
                        //    lat1: bssw.lat,
                        //    lat2: bsne.lat
                        //};
                        //console.debug("当前地图可视范围是：" + bssw.lng + "," + bssw.lat + "到" + bsne.lng + "," + bsne.lat);
                        //locateElevatorAndMaintenances(number);
                        //loadRightList();
                    }
                };
                // 添加DOM元素到地图中
                map.getContainer().appendChild(div);
                // 将DOM元素返回
                return div;
            };
            // 创建控件
            var mySearchCtrl = new SearchControl();
            // 添加到地图当中
            map.addControl(mySearchCtrl);

            showFaultElevators();
        }

        function showFaultElevators() {
            ajax.get("/api/elevator/allFault/10", function (res) {
                var faultEles = [];
                var points = [];
                for(var i = 0; i < res.data.length; i++){
                    faultEles.push(res.data[i]);
                    var point = new BMap.Point(res.data[i].lng,res.data[i].lat);
                    points.push(point);
                    var myIcon = new BMap.Icon("/image/dotslow.gif", new BMap.Size(23,23));
                    var marker = new BMap.Marker(point, {icon:myIcon});
                    map.addOverlay(marker);

                    (function (elevator, marker, point) {
                        var opts = {
                            width: 200,
                            height: 100,
                            title: translator.translate("number") + "：<a href='/realtime/detail/" + elevator.id + "'>" + elevator.number + "</a>", // 信息窗口标题
                            enableMessage: false
                        };
                        var infoWindow = new BMap.InfoWindow(translator.translate("alias") + "：" + elevator.alias, opts);  // 创建信息窗口对象
                        marker.addEventListener("click", function () {
                            map.openInfoWindow(infoWindow, point);
                        });
                    })(res.data[i], marker, point);


                }
                console.log(points.length);
                //map.setViewport(map.getViewport(points));
            });
        }
        function locateRescueElevator(number) {
            ajax.post("/api/netRescue/find" ,{number:number}, function (res) {
                elevatorProjectName=res.data.projectName;
                elevatorAddress = res.data.address;
                elevatorType = res.data.type;
                elevatorId = res.data.id;
                getOtherPerson(elevatorId);
                if(res.data.lng==0&&res.data.lat==0){
                    return toastr.info("该电梯没有具体位置！");
                }
                elevatorPoint = new BMap.Point(res.data.lng, res.data.lat);
                var marker = new BMap.Marker(elevatorPoint);
                map.clearOverlays();
                map.addOverlay(marker);
                map.centerAndZoom(elevatorPoint, 14);
                marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
                marker.addEventListener("click", getAttr);
                var opts = {
                    width: 400,
                    height: 100,
                    title: translator.translate("number") + "：<a href='/realtime/detail/" + res.data.id + "'>" + res.data.number + "</a>" + " | " + res.data.address, // 信息窗口标题
                    enableMessage: false
                };
                var sContent = "<button class='btn btn-primary' type='button' id='rescueButton' onclick='locateElevatorAndMaintenances(\"" + elevatorId + "\")'>" + translator.translate("gridRescue") + "</button>"
                    //+ "&nbsp;" + "<button class='btn btn-primary' type='button' id='messagePushButton' onclick='messagePushToMaintenances(\"" + number + "\")'>" + translator.translate("messagePush") + "</button>"
                    + "&nbsp;" + "<button class='btn btn-primary' type='button' id='cloudPushButton' onclick='cloudPushToMaintenances(\"" + elevatorId + "\")'>" + translator.translate("cloudPush") + "</button>";
                var infoWindow = new BMap.InfoWindow(sContent, opts);  //创建信息窗口对象
                function getAttr() {
                    map.openInfoWindow(infoWindow, elevatorPoint);
                }
            })
        }

        function getBounds() {
            var bs = map.getBounds();   //获取可视区域
            var bssw = bs.getSouthWest();   //可视区域左下角
            var bsne = bs.getNorthEast();   //可视区域右上角
            squareArea = {
                lng1: bssw.lng,
                lng2: bsne.lng,
                lat1: bssw.lat,
                lat2: bsne.lat,
                message: ''
            };
            console.debug("当前地图可视范围是：" + bssw.lng + "," + bssw.lat + "到" + bsne.lng + "," + bsne.lat);
        }

        function locateElevatorAndMaintenances(elevatorId) {
            getBounds();
            ajax.post("/api/netRescue/locate/" + elevatorId, squareArea, function (res) {
                console.debug(res.data);
                var points = [];
                points[0] = elevatorPoint;
                for (var i = 0; i < res.data.length; i++) {
                    vm.maintenances[i] = res.data[i];
                    var maintenanceLocation = new BMap.Point(vm.maintenances[i].lng, vm.maintenances[i].lat);
                    points.push(maintenanceLocation);
                    var marker = new BMap.Marker(maintenanceLocation, {
                        // 指定icon属性为Symbol
                        icon: new BMap.Symbol(BMap_Symbol_SHAPE_POINT, {
                            scale: 1.2,//图标缩放大小
                            fillColor: "green",//填充颜色
                            fillOpacity: 0.8//填充透明度
                        })
                    });

                    //var marker = new BMap.Marker(maintenanceLocation);
                    map.addOverlay(marker);
                    var label = new BMap.Label(vm.maintenances[i].name + " | " + vm.maintenances[i].telephone, {offset: new BMap.Size(20, -10)});
                    marker.setLabel(label);
                }
                console.debug(vm.maintenances);
                if (vm.maintenances.length == 0 || vm.maintenances[0].id == "") {
                    swal({
                        title: translator.translate("noPersonFindSoExtendedTheScope")
                    }, function () {
                        map.centerAndZoom(elevatorPoint, map.getZoom() - 2);
                    })
                } else {
                    map.setViewport(map.getViewport(points));
                    showMaintenancesList();
                    $("#rescueButton").text("选择人员");
                    $("#rescueButton").bind("click", function () {
                        showChoosePersonMenu();
                    });
                }
                showMaintenancesList();
            });

        }

        function messagePushToMaintenances() {
            getBounds();
            ajax.post("/api/netRescue/sendSms/" + elevatorId, squareArea, function  (res) {
                if(res.success) {
                    swal({
                            title: translator.translate("sendMessage")
                        }, function(){

                        }
                    );
                }
            });
        }

        function cloudPushToMaintenances() {
            getBounds;
            squareArea.message =  elevatorProjectName +"发生急修事件，请关注派单系统！";
            ajax.post("/api/netRescue/push/" + elevatorId, squareArea, function (res) {
                if(res.success) {
                    swal({
                        title: translator.translate("sendApp")
                    }, function (){

                    });
                }
            });
        }

        function showMaintenancesList() {
            //添加右上角附近维修人员列表控件
            function ListShowControl() {
                // 默认停靠位置和偏移量
                this.defaultAnchor = BMAP_ANCHOR_TOP_RIGHT;
                this.defaultOffset = new BMap.Size(20, 13);
            }

            ListShowControl.prototype = new BMap.Control();
            ListShowControl.prototype.initialize = function (map) {
                // 创建一个DOM元素
                var div = document.createElement("div");
                div.setAttribute("class", "dropdown open");
                var buttonDiv=document.createElement("button");
                buttonDiv.setAttribute("class","btn btn-default dropdown-toggle");
                buttonDiv.setAttribute("type","button");
                buttonDiv.setAttribute("id","dropdown1");
                buttonDiv.setAttribute("data-toggle","dropdown");
                buttonDiv.setAttribute("aria-haspopup","true");
                buttonDiv.setAttribute("aria-expanded","true");
                buttonDiv.setAttribute("style","width:105px;height:30px");
                buttonDiv.setAttribute("value","维保人员");
                var span=document.createElement("span");
                span.setAttribute("class","caret");
                buttonDiv.appendChild(span);
                var ulDiv=document.createElement("ul");
                ulDiv.setAttribute("class","dropdown-menu");
                ulDiv.setAttribute("aria-labelledby","dropdownMenu1");
                console.debug(vm.maintenances.length);
                for (var i = 0; i < vm.maintenances.length; i++) {
                    var left = document.createElement("li");
                    left.appendChild(document.createTextNode((i + 1) + ".   " + vm.maintenances[i].name + "等级:" + vm.maintenances[i].level));
                    var leftDiv=document.createElement("li");
                    leftDiv.setAttribute("class","divider");
                    leftDiv.setAttribute("role","separator");
                    ulDiv.appendChild(left);
                    ulDiv.appendChild(leftDiv);
                }

                div.appendChild(buttonDiv);
                div.appendChild(ulDiv);
                // 添加DOM元素到地图中
                map.getContainer().appendChild(div);
                // 将DOM元素返回
                return div;
            };
            // 创建控件
            var myListCtrl = new ListShowControl();
            // 添加到地图当中
            map.addControl(myListCtrl);
        }

        function showChoosePersonMenu() {
            var j = vm.maintenances.length;

            var selectArea = document.getElementById("personSelect");
            selectArea.options.length=0;
            selectArea.options.add(new Option("--",""));
            for (var i = 0;  i < j; i++) {
                var op = new Option();
                op.text = vm.maintenances[i].name; //显示值
                op.value = vm.maintenances[i].number; //value
                selectArea.options.add(op);
            }

            $('#choosePersonModal').modal();
        }

        function sendBill() {
            $("#number").html(number);
            $("#elevatorAddress").html(elevatorAddress);
            $("#elevatorType").html(elevatorType);
            var choosePerson=$("#personSelect  option:selected").text();
            var otherMan=$("#otherPerson").val();
            if (choosePerson!="--"&&otherMan.trim()==""){
                nameOfMaintenanceChoosen=choosePerson;
            }else if(choosePerson=="--"&&otherMan.trim()!="")
            {
                nameOfMaintenanceChoosen=otherMan;
            }else if(choosePerson=="--"&&otherMan.trim()=="")
            {
                return toastr.error("请务必选择急修人员！");
            }else if(choosePerson!="--"&&otherMan.trim()!="--"){
                nameOfMaintenanceChoosen=choosePerson;
            }
            if(nameOfMaintenanceChoosen==choosePerson)
            {
                var phone1="",i = 0, j = vm.maintenances.length;
                while (i < j) {
                    if (vm.maintenances[i].name == nameOfMaintenanceChoosen) {
                        maintenanceChoosen = vm.maintenances[i];
                        phone1 = vm.maintenances[i].telephone;
                        break;
                    } else {
                        i++;
                    }
                }
                $("#selectedMaintenance").html(nameOfMaintenanceChoosen);
                $("#maintenancePhone").html(phone1);
                $('#sendBillModal').modal();
            }
           else{
                $.post("/api/netRescue/getPerson/"+otherMan,{elevatorId:elevatorId}, function (res) {
                if(res.success){
                    maintenanceChoosen=res.data;
                    var phone=maintenanceChoosen.telephone;
                    $("#selectedMaintenance").html(nameOfMaintenanceChoosen);
                    $("#maintenancePhone").html(phone);
                    $('#sendBillModal').modal();
                }else{return toastr.error("此人员无权限维修该电梯！");}
                });
            }

        }

        function sendToMaintenance() {
            var test = {
                elevatorId: elevatorId,
                elevatorNumber: number,
                maintenanceId: maintenanceChoosen.id,
                faultPerson: $("#warnPerson").val(),
                faultPersonTelephone: $("#warnPersonPhone").val(),
                faultDescription: $("#faultDescription").val()
            };
            ajax.post("/api/netRescue/sendJX", test, function (res) {
                swal({
                    title: translator.translate("workBillPush")
                }, function () {
                    window.location.href = "/gridRescue";
                });
            })
        }

        function navToMap(billId) {
            $(".order-list li").removeClass("active");
            $(".order-list [data-bill-id='" + billId + "']").addClass("active");

            map.clearOverlays();
            ajax.post("/api/netRescue/getElevatorAndMaintenance", {id: billId}, function (res) {
                var currentElevatorPosition = new BMap.Point(res.data.elevator_lng, res.data.elevator_lat);
                var markerElevator = new BMap.Marker(currentElevatorPosition);
                if(res.data.maintenance_lng!=null&&res.data.maintenance_lat!=null)
                {
                    var currentMaintenancePosition = new BMap.Point(res.data.maintenance_lng, res.data.maintenance_lat);
                    //设置维保人员图标为水滴
                    var markerMaintenance = new BMap.Marker(currentMaintenancePosition, {
                        // 指定icon属性为Symbol
                        icon: new BMap.Symbol(BMap_Symbol_SHAPE_POINT, {
                            scale: 1.2,//图标缩放大小
                            fillColor: "green",//填充颜色
                            fillOpacity: 0.8//填充透明度
                        })
                    });
                    var label1 = new BMap.Label(res.data.maintenance_number + "/" + res.data.maintenance_name + "/" + res.data.maintenance_phone, {offset: new BMap.Size(20, -10)});
                    markerMaintenance.setLabel(label1);
                }else{
                    var currentMaintenancePosition=null;
                    var markerMaintenance=null;
                    toastr.info("该人员未登录APP，无法定位！");
                }
                var label2 = new BMap.Label(res.data.elevatorNumber + "/" + res.data.alias, {offset: new BMap.Size(20, -10)});
                markerElevator.setLabel(label2);

                map.addOverlay(markerElevator);
                map.addOverlay(markerMaintenance);
                markerElevator.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
                map.setViewport(map.getViewport([currentElevatorPosition, currentMaintenancePosition]));
            });
        }

        function loadRescueBills(pageNumber) {
            var obj = new Object();
            obj.pageNumber = pageNumber;
            obj.pageSize = 5;
            ajax.post("/api/workBill/getJXBills", obj, function (res) {
                parseWorkBills(res, pageNumber);
            });
        }

        function parseWorkBills(res, pageNumber) {
            if (res.success) {
                var totalPages = res.data.totalPages;
                var totalRecords = res.data.totalElements;
                vm.rescueWorkBills = res.data.content;
                for(var item in vm.rescueWorkBills){
                    var status = vm.rescueWorkBills[item].billstatus;
                    switch (status) {
                        case 10:
                            vm.rescueWorkBills[item].billstatus = "待接收";
                            break;
                        case 5:
                            vm.rescueWorkBills[item].billstatus = "待派发";
                            break;
                        case 20:
                            vm.rescueWorkBills[item].billstatus = "处理中";
                            break;
                        case 30:
                            vm.rescueWorkBills[item].billstatus = "已暂停";
                            break;
                        case 40:
                            vm.rescueWorkBills[item].billstatus = "已拒绝";
                            break;
                        case 45:
                            vm.rescueWorkBills[item].billstatus = "拒绝已处理";
                            break;
                        case 50:
                            vm.rescueWorkBills[item].billstatus = "已完成";
                            break;
                    }
                }
                showPageComp(pageNumber, totalPages, totalRecords);
            } else {

            }
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
                    firstPageText: translator.translate('firstPageText'),
                    firstPageTipText: translator.translate('firstPageText'),
                    lastPageText: translator.translate('lastPageText'),
                    lastPageTipText: translator.translate('lastPageText'),
                    prePageText: translator.translate('prePageText'),
                    prePageTipText: translator.translate('prePageText'),
                    nextPageText: translator.translate('nextPageText'),
                    nextPageTipText: translator.translate('nextPageText'),
                    totalPageBeforeText: translator.translate('totalPageBeforeText'),
                    totalPageAfterText: translator.translate('totalPageAfterText'),
                    currPageBeforeText: translator.translate('currPageBeforeText'),
                    currPageAfterText: translator.translate('buttonTipAfterText'),
                    totalInfoSplitStr: '/',
                    totalRecordsBeforeText: translator.translate('totalPageBeforeText'),
                    totalRecordsAfterText: translator.translate('totalRecordsAfterText'),
                    gopageBeforeText: translator.translate('gopageBeforeText'),
                    gopageButtonOkText: translator.translate('confirm'),
                    gopageAfterText: translator.translate('buttonTipAfterText'),
                    buttonTipBeforeText: translator.translate('buttonTipBeforeText'),
                    buttonTipAfterText: translator.translate('buttonTipAfterText')
                },
                isGoPage: false,
                click: function (n) {
                    loadRescueBills(n);
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

        function initData() {
            vm = new Vue({
                el: '.left-list',
                data: {
                    rescueWorkBills: [{
                        id: "",
                        elevatorId: "",
                        billNumber: "",
                        elevatorNumber: "",
                        billstatus: "",
                        billCategory: "",
                        maintenanceName: ""
                    }],
                    maintenances: [{
                        id: "",
                        number: "",
                        name: "",
                        telephone: "",
                        lng: 0,
                        lat: 0
                    }]
                },
                methods: {}
            });
        }

        init();

    });