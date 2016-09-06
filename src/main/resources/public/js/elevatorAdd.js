define(["jquery", "vue", "ajax", "ditu", "ditu_markerCluster", "js/util", "swal", "translator", "datetimepicker", "js/formValidator","toastr","typeahead"],
    function ($, Vue, ajax, BMap, BMapLib, util, swal, translator, datetimepicker, validator,toastr,typeahead) {
        var vm;
        var map;
        var nameToId={};
        function init() {
            $(".elevator").addClass("active");
            getInstallCompanyName();
            getMaintainerName();
            getManufacturerCompanyName();
            getUseCompanyName();
            getOthersName();
            getOwnerName();
            getPersonalName();
            getRegulatorName();
            initInternal();

            $(".date").datetimepicker({
                format: 'YYYY-MM-DD',
                locale:translator.translate("language")
            });
            window.getMen = getMen;
            window.getManager = getManager;

        }

        function getManager() {
            var companyName = $("#maintainerId").val();
            var companyId=nameToId[companyName];
            ajax.post("/api/companyMaintain/searchAllManager/" + companyId, {}, function (res) {
                if (res.success) {
                    $('#maintenanceManager').html('<option value="-1" selected="selected">--</option>');
                    $.each(res.data, function (index, data) {
                        var opStr = '<option value="' + data + '">' + data + '</option>';
                        $('#maintenanceManager').append(opStr);
                    });
                }
            })
        }

        function getMen() {
            var manager = $("#maintenanceManager").val();
            ajax.post("/api/companyMaintain/searchMen/" + manager, {}, function (res) {
                if (res.success) {
                    $('#maintenanceId').html('<option value="-1" selected="selected">--</option>');
                    $.each(res.data, function (index, data) {
                        var opStr = '<option value="' + data + '">' + data + '</option>';
                        $('#maintenanceId').append(opStr);
                    });
                }
            })
        }
        function addElevator() {
            var number=$("#number").val();
            var userCompanyName = $("#userCompanyId").val();
            var maintainerName=$("#maintainerId").val();
            var installCompanyName=$("#installCompanyId").val();
            var manufacturerName=$("#manufacturerId").val();
            var ownerName=$("ownerId").val();
            var regulatorName=$("#regulatorId").val();
            var othersName=$("#othersId").val();
            var personalName=$("#personalId").val();
            var projectName=$("#projectName").val();
            vm.elevator.projectName=projectName;
            if(number==null||number==""){
               return toastr.error("电梯工号不能为空!");
            }else{
                $.post("/api/elevator/numberSearch",{num:$("#number").val()}, function (r) {
                    if (r.success) {//判断电梯工号是否已存在
                        return toastr.error("电梯工号已存在");
                    } else {
                        if(userCompanyName!=null&&userCompanyName!=""){
                            vm.elevator.userCompanyId=nameToId[userCompanyName];
                        }else{
                            vm.elevator.userCompanyId=null;
                        }
                        if(maintainerName!=null&&maintainerName!="")
                        {
                            vm.elevator.maintainerId=nameToId[maintainerName];
                        }else{
                            vm.elevator.maintainerId=null;
                        }
                        if(installCompanyName!=null&&installCompanyName!="")
                        {
                            vm.elevator.installCompanyId=nameToId[installCompanyName];
                        }else{
                            vm.elevator.installCompanyId=null;
                        }
                        if(manufacturerName!=null&&manufacturerName!="")
                        {
                            vm.elevator.manufacturerId=nameToId[manufacturerName];
                        }else{
                            vm.elevator.manufacturerId=null;
                        }
                        if(ownerName!=null&&ownerName!="")
                        {
                            vm.elevator.ownerId=nameToId[ownerName];
                        }else{
                            vm.elevator.ownerId=null;
                        }
                        if(regulatorName!=null&&regulatorName!=""){
                            vm.elevator.regulatorId=nameToId[regulatorName];
                        }else{
                            vm.elevator.regulatorId=null;
                        }
                        if(personalName!=null&&personalName!=""){
                            vm.elevator.personalId=nameToId[personalName];
                        }else{
                            vm.elevator.personalId=null;
                        }
                        if(othersName!=null&&othersName!=""){
                            vm.elevator.othersId=nameToId[othersName];
                        }else{
                            vm.elevator.othersId=null;
                        }
                        ajax.post("/api/elevator/add", vm.elevator, function (r) {
                            if (r.success) {
                                swal({
                                    title: translator.translate("addedSuccessfully")
                                }, function () {
                                    window.location.href = "/elevator";
                                });
                            }
                        });
                    }
                });
            }
        }

        function getUseCompanyName()
        {//使用单位
            $("#userCompanyId").typeahead({
                source: function(query, process) {
                    var parameter = {companyName: query,type:30};
                    ajax.post("/api/elevator/searchCompanyName", parameter, function (res) {
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
                    $("#userCompanyId").val(item);
                }
            });
        }
        function getMaintainerName()
        {//维保单位
            $("#maintainerId").typeahead({
                source: function(query, process) {
                    var parameter = {companyName: query,type:20};
                    ajax.post("/api/elevator/searchCompanyName", parameter, function (res) {
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
                    $("#maintainerId").val(item);
                }
            });
        }
        function getInstallCompanyName()
        {//安装单位
            $("#installCompanyId").typeahead({
                source: function(query, process) {
                    var parameter = {companyName: query,type:10};
                    ajax.post("/api/elevator/searchCompanyName", parameter, function (res) {
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
                    $("#installCompanyId").val(item);
                }
            });
        }
        function getManufacturerCompanyName()
        {//制造单位
            $("#manufacturerId").typeahead({
                source: function(query, process) {
                    var parameter = {companyName: query,type:40};
                    ajax.post("/api/elevator/searchCompanyName", parameter, function (res) {
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
                    $("#manufacturerId").val(item);
                }
            });
        }
        function getOwnerName()
        {//物业单位
            $("#ownerId").typeahead({
                source: function(query, process) {
                    var parameter = {companyName: query,type:50};
                    ajax.post("/api/elevator/searchCompanyName", parameter, function (res) {
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
                    $("#ownerId").val(item);
                }
            });
        }
        function getRegulatorName()
        {//监管机构
            $("#regulatorId").typeahead({
                source: function(query, process) {
                    var parameter = {companyName: query,type:60};
                    ajax.post("/api/elevator/searchCompanyName", parameter, function (res) {
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
                    $("#regulatorId").val(item);
                }
            });
        }
        function getOthersName()
        {//其他类型
            $("#othersId").typeahead({
                source: function(query, process) {
                    var parameter = {companyName: query,type:80};
                    ajax.post("/api/elevator/searchCompanyName", parameter, function (res) {
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
                    $("#othersId").val(item);
                }
            });
        }
        function getPersonalName()
        {//个人用户
            $("#personalId").typeahead({
                source: function(query, process) {
                    var parameter = {companyName: query,type:70};
                    ajax.post("/api/elevator/searchCompanyName", parameter, function (res) {
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
                    $("#personalId").val(item);
                }
            });
        }
        function initMap() {
            var mapHeight = 530;
            var mapWidth = ($("#elevatorAdd").width()) * 0.79;
            $("#map").height(mapHeight).width(mapWidth).css({left: 50});
            map = new BMap.Map("map");
            map.centerAndZoom(new BMap.Point(105.006609, 35.667), 11);
            function myFun(result){
                var cityName=result.name;
                map.setCenter(cityName);
            }
            var myCity=new BMap.LocalCity();
            myCity.get(myFun);
            map.enableScrollWheelZoom();
            map.enableDragging();

            var geoc = new BMap.Geocoder();
            map.addEventListener("click", function (e) {
                var pt = new BMap.Marker(e.point);
                map.clearOverlays();
                map.addOverlay(pt);
                map.centerAndZoom(e.point, 16);
                pt.setAnimation(BMAP_ANIMATION_BOUNCE);
                var pt2= e.point;
                geoc.getLocation(pt2, function (rs) {
                    var addComp = rs.addressComponents;
                    var location = addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber;
                    vm.elevator.lng=pt2.lng;
                    vm.elevator.lat=pt2.lat;
                    vm.elevator.address = location;
                });
            });
            var local = new BMap.LocalSearch(map, {
                renderOptions:{map: map}
            });
            local.search($("#address").val());

            //geoc.getPoint($("#address").val(), function (point) {
            //    if (point) {
            //        map.centerAndZoom(point, 16);
            //        map.addOverlay(new BMap.Marker(point));
            //    } else {
            //        var myCity=new BMap.LocalCity();
            //        var cityName=myCity.name;
            //        map.setCenter(cityName);
            //
            //    }
            //});
        }


        function initInternal() {
            vm = new Vue({
                el: "#elevatorAdd",
                data: {
                    elevator: {
                        id: "",
                        number: "",
                        equipmentNumber:"",
                        address: "",
                        alias: "",
                        projectName:"",
                        regCode: "",
                        elevatorType: "",
                        controllerType: "",
                        intelHardwareNumber: "",
                        lng: 0,
                        lat: 0,
                        userCompanyId: "",
                        userCompanyName:"",
                        maintainerId: "",
                        maintainerName:"",
                        maintenanceManager: "",
                        maintenanceId: "",
                        maintenanceName: "",
                        installCompanyId: "",
                        installCompanyName:"",
                        manufacturerId: "",
                        manufacturerName:"",
                        ownerId: "",
                        ownerName:"",
                        regulatorId: "",
                        regulatorName:"",
                        othersId: "",
                        othersName:"",
                        personalId:"",
                        personalName:"",
                        station: "",
                        brandId: "",
                        ratedWeight: "",
                        ratedSpeed: "",
                        productionTime: "",
                        deliverTime: "",
                        controlMode: "",
                        hoistingHeight: ""
                        //maintenanceStatus: "",
                    }
                },
                methods: {
                    confirm: function () {
                        addElevator();
                    },
                    locateElevator: function () {
                        initMap();
                    }
                }
            });
        }

        init();

    });