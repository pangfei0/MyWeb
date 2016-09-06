define(["jquery", "vue", "ajax", "js/datatable", "js/util", "swal", "translator","typeahead"],
    function($, Vue, ajax, datatable, util, swal, translator,typeahead){
        init();

        function init(){
            $(".menu-archive-management").addClass("active");
            $(".elevator").addClass("active");

            initDataTables();
            getNumbers();
          //  getAddress();
            getProjectName();
            var vm= new Vue({
                el: ".search-form",
                methods: {
                    search: function () {
                        $("#dataTable").bootstrapTable("refresh");
                    },
                    export: function () {
                        window.location.href = "/api/elevator/export";
                    }
                }

            });
        }

        function getNumbers(){
            $("#number").typeahead({
                source: function(query, process) {
                    var parameter = {number: query};
                    ajax.post("/api/elevator/elevatorDto/getNumbers", parameter, function (res) {
                        if(res.success){
                            var array = [];
                            $.each(res.data,function(index, ele){
                                array.push(ele);
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
                    $("#number").val(item);
                }
            });
        }

        //function getAddress(){
        //    $("#address").typeahead({
        //        source: function(query, process) {
        //            var parameter = {address: query};
        //            ajax.post("/api/elevator/elevatorDto/getAddress", parameter, function (res) {
        //                if(res.success){
        //                    var array = [];
        //                    $.each(res.data,function(index, ele){
        //                        array.push(ele);
        //                    });
        //                }
        //                process(array);
        //            });
        //        },
        //        delay: 500,
        //        items: 6,
        //        updater: function (item) {
        //            return item;
        //        },
        //        afterSelect: function (item) {
        //            $("#address").val(item);
        //        }
        //    });
        //}

        function getProjectName(){
            $("#projectName").typeahead({
                source: function(query, process) {
                    var parameter = {projectName: query};
                    ajax.post("/api/elevator/elevatorDto/getProjectName", parameter, function (res) {
                        if(res.success){
                            var array = [];
                            $.each(res.data,function(index, ele){
                                array.push(ele);
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
                    $("#projectName").val(item);
                }
            });
        }

        function initDataTables(){
        datatable.createTable("dataTable","/api/elevator/new/elevatorSearch/page", {
            columns: [
                {
                title: '',
                formatter: function(value, row, index){
                    return (index + 1);
                }
                },
                {
                field: 'number',
                title: translator.translate("numberOfElevator"),  //即电梯合同号 电梯工号
                formatter: function(value, row, index){
                    return ("<a href='/elevator/cou?id=" + row.id + "'>" + row.number + "</a>");
                }
                },
                //{
                //field: '',
                //title: translator.translate("numberOfUpkeepContract"),//保养合同编号
                //formatter: function(value, row, index){
                //
                //}
                //},
                {
                field: 'address',
                title: translator.translate("addressOfElevator")
                },
                {
                field: 'projectName',
                title:  translator.translate("projectName")
                },
                {
                    field: 'equipmentNumber',
                    title:"设备编号"
                },
                //{
                //field: '',
                //title: translator.translate("maintenanceMan")
                // },
                //{
                //field: 'elevatorBrand.name',
                //title: translator.translate("elevatorBrand"),
                //formatter: function (value, row, index){
                //    if(row.elevatorBrand == null) return "-";
                //    return row.elevatorBrand.name;
                //}
                //},
                {
                field: 'elevatorType',
                title: translator.translate("elevatorType")
                },
                //{
                //field: 'controlMode',
                //title: translator.translate("controlMode")
                //},
                {
                field: 'controllerType',
                title:translator.translate("controllerTypeOfElevator")
                },
                {
                field: 'station',
                title:translator.translate("station")
                },
                //{
                //field: 'intelHardwareNumber',
                //title: translator.translate("intelHardwareNumber"),
                //formatter: function (value, row, index) {
                //    if(row.collector == null) return "-";
                //    return row.collector.intelHardwareNumber;
                //}
                //},
                {
                field: 'productionTime',
                title:translator.translate("productionTime")
                },
                //{
                //field: 'deliverTime',
                //title: translator.translate("deliverTime")
                //},
                //{
                //field: 'lastcheckDate',
                //title: translator.translate("lastCheckDate")
                //},
                {
                field: 'ratedWeight',
                title: translator.translate("ratedWeight")
                },
                {
                field: 'ratedSpeed',
                title: translator.translate("ratedSpeed")
                }
                //,
                //{
                //field: '',
                //title:translator.translate("useCompany")
                //},
                //{
                //field: '',
                //title:translator.translate("maintenanceUnit")
                //},
                //{
                //field: '',
                //title:translator.translate("premises")
                //}
            ]
        });
    }

});