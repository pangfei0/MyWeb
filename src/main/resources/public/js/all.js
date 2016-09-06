define(["jquery", "vue", "ajax", "ditu", "kkpager","ditu_markerCluster", "raphael", "lib/Raphael/chinaMap", "translator", "toastr"], function ($, Vue, ajax, BMap, kkpager, BMapLib, Raphael, chinaMap, translator, toastr) {
        var mapHeight, mapWidth;
        var elevators={};
        var vm;
        init();

        function init() {
            $(".status .all").addClass("active");
            loadMap();
        }

        function loadMap() {
            mapHeight = $("#content").height() - 40;
            mapWidth = $("#content > div").width();
            $("#map").height(mapHeight).width(mapWidth).css({"left": 10});

            //创建地图
            map = new BMap.Map("map");
            map.centerAndZoom(new BMap.Point(105.006609, 35.667), 6);
            map.enableScrollWheelZoom();
            map.enableDragging();

            loadEle();
        }

        function loadEle() {
            ajax.get("/api/elevator/elevator/all", function(res){
                elevators = res.data;
                addFaultElevatorsToMap();
            });
        }

        function addFaultElevatorsToMap() {
            map.clearOverlays();
            var length = elevators.length;
            console.log(length);
            for (var i = 0; i < length; i++) {

                var point = new BMap.Point(elevators[i][3], elevators[i][4]);
                var myIcon = new BMap.Icon("/image/green-dot.gif", new BMap.Size(23, 23));
                var marker = new BMap.Marker(point, {icon: myIcon});
                map.addOverlay(marker);

                (function (elevator, marker, point) {
                    var opts = {
                        width: 200,
                        height: 100,
                        title: translator.translate("number") + "：<a href='/realtime/detail/" + elevator[0] + "'>" + elevator[1] + "</a>", // 信息窗口标题
                        enableMessage: false
                    };
                    var infoWindow = new BMap.InfoWindow(translator.translate("address") + "：" + elevator[2], opts);  // 创建信息窗口对象
                    marker.addEventListener("click", function () {
                        map.openInfoWindow(infoWindow, point);
                    });
                })(elevators[i], marker, point);


            }

        }

    }
);