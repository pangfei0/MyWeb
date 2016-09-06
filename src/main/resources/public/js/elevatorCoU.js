define  (["jquery", "vue", "ajax", "ditu", "ditu_markerCluster", "js/util", "swal", "translator", "datetimepicker", "js/formValidator","toastr","typeahead"],
    function ($, Vue, ajax, BMap, BMapLib, util, swal, translator, datetimepicker, validator,toastr,typeahead) {
        var vm;
        var map;
        var nameToId={};
        var id;
        var firstLoad=true;
        var number;
        function init() {
            $(".elevator").addClass("active");
            id=util.getUrlParameter("id");
            loadElevator(id);
            initInternal();
            $(".date").datetimepicker({
                format: 'YYYY-MM-DD',
                locale:translator.translate("language")
            });
            window.getMen = getMen;
            window.getManager = getManager;

        }
        function changeEditMenuState(type) {
            loadElevator(id);
            if(type=="baseInfo")
            {
                $("#baseInfoShow").hide();
                $("#baseInfoUpdate").show();
            }
            else if(type=="stateInfo")
            {
                $("#stateInfoShow").hide();
                $("#stateInfoUpdate").show();
                getInstallCompanyName();
                getMaintainerName();
                getManufacturerCompanyName();
                getUseCompanyName();
                getOthersName();
                getOwnerName();
                getPersonalName();
                getRegulatorName();
            }
        }

        function changeShowMenuState(type)
        {
            loadElevator(id);
            if(type=="baseInfo")
            {
                $("#baseInfoShow").show();
                $("#baseInfoUpdate").hide();
            }
            else if(type=="stateInfo")
            {
                $("#stateInfoShow").show();
                $("#stateInfoUpdate").hide();
                $('#divShowManager').show();
                $('#divShowMan').show();
                $('#divChangeManager').hide();
                $('#divChangeMan').hide();
            }
        }
        function getManager() {

            var companyName = $("#maintainerId").val();
            var companyId=nameToId[companyName];
            ajax.post("/api/companyMaintain/searchAllManager/" + companyId, {}, function (res) {
                if (res.success) {
                    $('#divChangeManager').show();
                    $('#divShowManager').hide();
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
                    $('#divChangeMan').show();
                    $('#divShowMan').hide();
                    $('#maintenanceId').html('<option value="-1" selected="selected">--</option>');
                    $.each(res.data, function (index, data) {
                        var opStr = '<option value="' + data + '">' + data + '</option>';
                        $('#maintenanceId').append(opStr);
                    });
                }
            })
        }

        function loadElevator(id) {
            ajax.get("/api/elevator/full/" + id, function (res) {
                vm.elevator = res.data;
                number=vm.elevator.number;
            });
        }

        function editBaseInfoOfElevator() {
            var number2=$("#number").val();
            if(number!=number2&&((vm.elevator.userCompanyId!=null&&vm.elevator.userCompanyId!="")||(vm.elevator.installCompanyId!=null&&vm.elevator.installCompanyId!="")||(vm.elevator.maintainerId!=null&&vm.elevator.maintainerId!="")||(vm.elevator.maintenanceId!=null&&vm.elevator.maintenanceId!="")||(vm.elevator.manufacturerId!=null&&vm.elevator.manufacturerId!=""))){
                return toastr.error("电梯已关联单位，不可修改工号!");
            }else if(number2==null)
            {
                return toastr.error("电梯工号不能为空!");
            }
            else if(number!=number2){
                $.post("/api/elevator/numberSearch",{num:$("#number").val()}, function (r) {
                    if (r.success) {//判断合同号是否已存在
                        return toastr.error("电梯工号已存在");
                    } else {
                        ajax.patch("/api/elevator/editBaseInfoElevator/" + vm.elevator.id, vm.elevator, function (res) {
                            if(res.success){
                                swal({
                                    title: translator.translate("updatedSuccessfully")
                                }, function () {
                                    changeShowMenuState("baseInfo");
                                });
                            }
                        });
                    }
                });
            }else if(number==number2){
                ajax.patch("/api/elevator/editBaseInfoElevator/" + vm.elevator.id, vm.elevator, function (res) {
                    if(res.success){
                        swal({
                            title: translator.translate("updatedSuccessfully")
                        }, function () {
                            changeShowMenuState("baseInfo");
                        });
                    }
                });
            }
        }

        function editCompanyInfoOfElevator() {
            var userCompanyName=$("#userCompanyId").val();
            var maintainerName=$("#maintainerId").val();
            var installCompanyName=$("#installCompanyId").val();
            var manufacturerName=$("#manufacturerId").val();
            var ownerCompanyName=$("#ownerCompanyId").val();
            var regulatorName=$("#regulatorId").val();
            var othersName=$("#othersId").val();
            var personalName=$("#personalId").val();
            if(userCompanyName!=null&&userCompanyName!=""){
                vm.elevator.userCompanyId=nameToId[userCompanyName];
                if(nameToId[userCompanyName]==null){
                    vm.elevator.userCompanyName=userCompanyName;
                }
            }else{
                vm.elevator.userCompanyId=null;
            }
            if(maintainerName!=null&&maintainerName!="")
            {
                vm.elevator.maintainerId=nameToId[maintainerName];
                if(nameToId[maintainerName]==null){
                    vm.elevator.maintainerName=maintainerName;
                }
            }else{
                vm.elevator.maintainerId=null;
            }
            if(installCompanyName!=null&&installCompanyName!="")
            {
                vm.elevator.installCompanyId=nameToId[installCompanyName];
                if(nameToId[installCompanyName]==null){
                    vm.elevator.installCompanyName=installCompanyName;
                }
            }else{
                vm.elevator.installCompanyId=null;
            }
            if(manufacturerName!=null&&manufacturerName!="")
            {
                vm.elevator.manufacturerId=nameToId[manufacturerName];
                if(nameToId[manufacturerName]==null){
                    vm.elevator.manufacturerName=manufacturerName;
                }
            }else{
                vm.elevator.manufacturerId=null;
            }
            if(ownerCompanyName!=null&&ownerCompanyName!="")
            {
                vm.elevator.ownerCompanyId=nameToId[ownerCompanyName];
                if(nameToId[ownerCompanyName]==null){
                    vm.elevator.ownerCompanyName=ownerCompanyName;
                }
            }else{
                vm.elevator.ownerCompanyId=null;
            }
            if(regulatorName!=null&&regulatorName!="")
            {
                vm.elevator.regulatorId=nameToId[regulatorName];
                if(nameToId[regulatorName]==null){
                    vm.elevator.regulatorName=regulatorName;
                }
            }else{
                vm.elevator.regulatorId=null;
            }
            if(othersName!=null&&othersName!="")
            {
                vm.elevator.othersId=nameToId[othersName];
                if(nameToId[othersName]==null){
                    vm.elevator.othersName=othersName;
                }
            }else{
                vm.elevator.othersId=null;
            }
            if(personalName!=null&&personalName!="")
            {
                vm.elevator.personalId=nameToId[personalName];
                if(nameToId[personalName]==null){
                    vm.elevator.personalName=personalName;
                }
            }else{
                vm.elevator.personalId=null;
            }
            //var elevatorInfo={
            //    userCompanyId:vm.elevator.userCompanyId,
            //    maintainerId: vm.elevator.maintainerId,
            //    ownerCompanyId:vm.elevator.ownerCompanyId,
            //    regulatorId:vm.elevator.regulatorId,
            //    personalId:vm.elevator.personalId,
            //    othersId:vm.elevator.othersId,
            //    maintenanceName: $("#maintenanceId").val(),
            //    installCompanyId: vm.elevator.installCompanyId,
            //    manufacturerId: vm.elevator.manufacturerId
            //}
            ajax.patch("/api/elevator/editCompanyInfoElevator/" + vm.elevator.id, vm.elevator, function (res) {
                swal({
                    title: translator.translate("updatedSuccessfully")
                }, function () {
                    changeShowMenuState("stateInfo");
                });
            });


        }

        function deleteElevator() {
            if(vm.elevator.userCompanyId!=null||vm.elevator.maintainerId!=null||vm.elevator.maintenanceId!=null||vm.elevator.installCompanyId!=null||vm.elevator.manufacturerId!=null)
            {
                return toastr.error("该电梯已关联单位，不能删除！");
            }
            swal({
                title: translator.translate("doYouWantToDeleteThisElevator"),
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: translator.translate("delete"),
                cancelButtonText: translator.translate("cancel"),
                closeOnConfirm: false,
                html: false
            }, function () {
                ajax.delete("/api/elevator/" + vm.elevator.id, function () {
                    swal("删除成功", "该电梯已经从系统中删除", "success");
                    window.location.href = "/elevator";
                });
            });

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
            $("#ownerCompanyId").typeahead({
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
                    $("#ownerCompanyId").val(item);
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
            var mapWidth = ($("#elevatorCoU").width()) * 0.79;
            $("#map").height(mapHeight).width(mapWidth).css({left: 50});
            var longitude =120.622297;
            var latitude=31.329586;
            map = new BMap.Map("map");
            map.centerAndZoom(new BMap.Point(longitude, latitude), 11);
            map.enableScrollWheelZoom();
            map.enableDragging();


            var geoc = new BMap.Geocoder();


            if(firstLoad){
                geoc.getPoint(vm.elevator.address, function (point) {
                    if (point) {
                        longitude=vm.elevator.lng;
                        latitude=vm.elevator.lat;
                        map.clearOverlays();
                        var new_point=new BMap.Point(longitude,latitude);
                        var marker=new BMap.Marker(new_point);
                        map.addOverlay(marker);
                        map.panTo(new_point);
                    } else {
                        var myCity=new BMap.LocalCity();
                        var cityName=myCity.name;
                        map.setCenter(cityName);

                    }
                });
                firstLoad=false;
            }else{
                var address=$("#address").val();
                var local = new BMap.LocalSearch(map, {
                    renderOptions:{map: map}
                });
                local.search(address);
            }
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
                    vm.elevator.address = location;
                    vm.elevator.lng=pt2.lng;
                    vm.elevator.lat=pt2.lat;
                });
            });

        }


        function initInternal() {
            vm = new Vue({
                el: "#elevatorCoU",
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
                        brandName:"",
                        ratedWeight: "",
                        ratedSpeed: "",
                        productionTime: "",
                        deliverTime: "",
                        controlMode: "",
                        hoistingHeight: ""
                        //maintenanceStatus: "",
                        //status:"",
                        //faultStatus:"",
                        //isHandled:"",
                       // usageType: "",
                       // dutyPhone: "",
                       // elevatorModel: "",
                        //signalIntensity: ""
                        //installCompany: "",
                        //maintainer: "",
                        //mainTainName: "",
                        //mainTainPhone: "",
                        //managerName: "",
                        //managerPhone: "",
                        ////lastUpkeepDate: "",
                        //maintenanceStatus: "",
                        ////signalIntensity: "",
                    }
                },
                methods: {
                    baseInfoEdit: function () {
                        changeEditMenuState("baseInfo");
                    },
                    stateInfoEdit: function () {
                        changeEditMenuState("stateInfo");
                    },
                    baseInfoConfirm: function () {
                        editBaseInfoOfElevator();
                    },baseInfoCancel: function () {
                        changeShowMenuState("baseInfo");
                    },
                    stateInfoConfirm: function () {
                        editCompanyInfoOfElevator();
                    },
                    stateInfoCancel: function () {
                        changeShowMenuState("stateInfo");
                    },
                    deleteIt: function () {
                        deleteElevator();
                    },
                    locateElevator: function () {
                        initMap();
                    }
                }
            });
        }

        init();

    });
