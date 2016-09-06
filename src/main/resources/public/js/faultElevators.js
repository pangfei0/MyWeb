define(["jquery", "vue", "ajax", "ditu", "kkpager","ditu_markerCluster", "raphael", "lib/Raphael/chinaMap", "translator", "toastr"], function ($, Vue, ajax, BMap, kkpager, BMapLib, Raphael, chinaMap, translator, toastr) {
        var map;
        var mapHeight, mapWidth;
        var vm;

        function init() {
            $(".status .malfunction").addClass("active");
            loadData();
            loadFaultPage();
            loadHandledList(1);
            //loadUnHandledList(1);
        }

        function loadFaultPage() {
            mapHeight = $("#content").height() - 40;
            mapWidth = $("#content > div").width() - 350;
            $("#map").height(mapHeight).width(mapWidth).css({"left": 10});

            //创建地图
            map = new BMap.Map("map");
            map.centerAndZoom(new BMap.Point(105.006609, 35.667), 6);
            map.enableScrollWheelZoom();
            map.enableDragging();

            addFaultElevatorsToMap(20);
        }

        function loadHandledList(pageNumber) {
            var obj = new Object();
            obj.pageNumber = pageNumber;
            obj.pageSize = 5;
            ajax.post("/api/elevator/handled", obj, function(res){
                if(res.success) {
                    var totalPages = res.data.totalPages;
                    var totalRecords = res.data.totalElements;
                    vm.handledFaultEles = res.data.content;
                    showHandledPageEle(pageNumber, totalPages, totalRecords);
                }
            });
        }

        function loadUnHandledList(pageNumber) {
            var obj = new Object();
            obj.pageNumber = pageNumber;
            obj.pageSize = 5;
            ajax.post("/api/elevator/notHandled", obj, function(res){
                if(res.success){
                    var totalPages = res.data.totalPages;
                    var totalRecords = res.data.totalElements;
                    vm.unHandledfaultEles = res.data.content;
                    showUnHandledPageEle( pageNumber, totalPages, totalRecords);
                }
            });
        }

        //生成处理中分页控件
        function showHandledPageEle(pageNumber, totalPages, totalRecords) {
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
                click: function (n) {
                    loadHandledList(n);
                    //处理完后可以手动条用selectPage进行页码选中切换
                    this.selectPage(n);
                },
                //getHref是在click模式下链接算法，一般不需要配置，默认代码如下
                getHref: function (n) {
                    return '#';
                }
            }, true);
        }

        //生成未处理分页控件
        function showUnHandledPageEle(pageNumber, totalPages, totalRecords) {
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
                click: function (n) {
                    loadUnHandledList(n);
                    //处理完后可以手动条用selectPage进行页码选中切换
                    this.selectPage(n);
                },
                //getHref是在click模式下链接算法，一般不需要配置，默认代码如下
                getHref: function (n) {
                    return '#';
                }
            }, true);
        }

        //右侧地图相应加载
        function addFaultElevatorsToMap(isHandled) {
            map.clearOverlays();
            ajax.get("/api/elevator/allFault/" + isHandled, function (res) {
                var faultEles = [];
                var points = [];
                for (var i = 0; i < res.data.length; i++) {
                    faultEles.push(res.data[i]);
                    var point = new BMap.Point(res.data[i].lng, res.data[i].lat);
                    points.push(point);
                    var myIcon = new BMap.Icon("/image/dotslow.gif", new BMap.Size(23, 23));
                    var marker = new BMap.Marker(point, {icon: myIcon});
                    map.addOverlay(marker);
                    map.centerAndZoom(new BMap.Point(105.006609, 35.667), 6);

                    (function (elevator, marker, point) {
                        var opts = {
                            width: 200,
                            height: 100,
                            title: translator.translate("number") + "：<a href='/realtime/detail/" + elevator.id + "'>" + elevator.number + "</a>", // 信息窗口标题
                            enableMessage: false
                        };
                        var infoWindow = new BMap.InfoWindow(translator.translate("address") + "：" + elevator.alias, opts);  // 创建信息窗口对象
                        marker.addEventListener("click", function () {
                            map.openInfoWindow(infoWindow, point);
                        });
                    })(res.data[i], marker, point);


                }
                console.log(points.length);
            });

            if(isHandled == 20){
                loadHandledList(1);
            }else if(isHandled == 10){
                loadUnHandledList(1);
            }

        }

        //点击列表实现地图跳转
        function navToMap(id, lat, lng){
            $(".list-cell > div").removeClass("activeStyle");
            $(".list-cell [data-ele-id='" + id + "']").addClass("activeStyle");

            map.clearOverlays();
            var elePositon = new BMap.Point(lng, lat);
            var myIcon = new BMap.Icon("/image/dotslow.gif", new BMap.Size(23, 23));
            var eleMarker = new BMap.Marker(elePositon, {icon:myIcon});
            ajax.get("/api/elevator/" + id, function(res){
               var ele = res.data;
                (function (elevator, marker, point) {
                    var opts = {
                        width: 200,
                        height: 100,
                        title: translator.translate("number") + "：<a href='/realtime/detail/" + elevator.id + "'>" + elevator.number + "</a>", // 信息窗口标题
                        enableMessage: false
                    };
                    var infoWindow = new BMap.InfoWindow(translator.translate("address") + "：" + elevator.alias, opts);  // 创建信息窗口对象
                    marker.addEventListener("click", function () {
                        map.openInfoWindow(infoWindow, point);
                    });
                })(ele, eleMarker, elePositon);

            });
            map.addOverlay(eleMarker);
            map.centerAndZoom(elePositon,7);

        }

        function loadData() {
            vm = new Vue({
                el: '#statusTab',
                data: {
                    handledFaultEles: [{
                        id: "",
                        number: "",
                        alias: "",
                        lat: "",
                        lng: "",
                        billId: "",
                        billNumber: "",
                        dealTime: "",
                        billStatus: "",
                        DealerId: "",
                        billDealer: ""
                    }],
                    unHandledfaultEles: [{
                        id: "",
                        number: "",
                        alias: "",
                        lat: "",
                        lng: "",
                        faultTime: "",
                        faultCode: ""
                    }]
                },
                methods: {

                }
            });
        }

        window.addFaultElevatorsToMap = addFaultElevatorsToMap;
        window.navToMap = navToMap;
        init();
    }
);