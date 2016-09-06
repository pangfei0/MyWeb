define(["jquery", "vue", "ajax", "js/datatable", "js/util", "swal", "translator", "bootstrap","datetimepicker"],
    function ($, Vue, ajax, datatable, util, swal, translator, bootstrap) {
        var vm;

        init();
        window.deletePlan=deletePlan;
        function init() {
            $(".menu-archive-management").addClass("active");
            $(".work-bill-board").addClass("active");
            $(".date").datetimepicker({
                format: 'YYYY-MM-DD HH:mm:ss',
                locale:translator.translate("language")
            });
            initDataTables();
            initData();
        }

        function initDataTables() {
            datatable.createTable("dataTable", "/api/static/workBillStatic/search/page",
                {
                columns: [
                    {
                    field: 'number',
                    title: "员工编号"
                    },
                    {
                    field: 'name',
                    title: "员工姓名"
                    },
                    {
                        field: 'station',
                        title:"维保站"
                    },
                    {
                        field: 'region',
                        title:"区域"
                    },
                    {
                        field: 'billAllCount',
                        title:"总台量"
                    },
                    {
                        field: 'byCount',
                        title:"半月保"
                    },
                    {
                        field: 'jdCount',
                        title: "季度保"
                     },
                    {
                        field: 'bnCount',
                        title:"半年保"
                    },
                    {
                        field: 'qnCount',
                        title:"全年保"
                    },
                    {
                        field: 'wxCount',
                        title:"维修"
                    },
                    {
                        field: 'jxCount',
                        title:"急修"
                    },
                    {
                        field: 'jjCount',
                        title:"拒绝数"
                    }

                ]
            });
        }

        //function deletePlan(id){
        //    swal({
        //        title: "确定删除该维保计划吗?",
        //        type: "warning",
        //        showCancelButton: true,
        //        confirmButtonColor: "#DD6B55",
        //        confirmButtonText: translator.translate("delete"),
        //        cancelButtonText: translator.translate("cancel"),
        //        closeOnConfirm: false,
        //        html: false
        //    }, function () {
        //        ajax.delete("/api/maintenancePlan/" +id, function (data) {
        //            if (data.success){
        //                swal("删除成功","该计划已删除","success");
        //                window.location.href="/maintenancePlan";
        //            }
        //        });
        //    });
        //}
        function initData() {
            vm = new Vue({
                el: '.panel-default',
                data: {},
                methods: {
                    search: function () {
                        $("#dataTable").bootstrapTable("refresh");
                    },
                    exportWorkBillStatic: function () {
                        window.location.href = "/api/static/workBillExport?startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val();
                    }
                }
            });

        }

    });

//function setSearch_planStatusValue(){
//    var mapHeight = $("#search_status").val();
//    $("#status").val(mapHeight);
//}
//
//
//function setSearch_planTypeValue(){
//    var mapHeight = $("#search_type").val();
//    $("#planType").val(mapHeight);
//}
