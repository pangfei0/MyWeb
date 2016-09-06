define(["jquery", "vue", "ajax", "js/datatable", "js/util", "swal", "translator", "bootstrap"],
    function ($, Vue, ajax, datatable, util, swal, translator, bootstrap) {
        var vm;

        init();
        window.deletePlan=deletePlan;
        function init() {
            $(".menu-archive-management").addClass("active");
            $(".work-bill-board").addClass("active");
            initDataTables();
            initData();
        }

        function initDataTables() {
            datatable.createTable("dataTable", "/api/maintenancePlan/new/search/page",
                {
                columns: [
                    {
                    field: 'upkeepContractNumber',
                    title: "合同编号",
                        sortable: true
                    },
                    {
                    field: 'number',
                    title: "电梯",
                        formatter: function (value, row, index) {
                            return ("<a href= '/maintenancePlan/add?id=" + row.id + "'>" + row.number + "</a>");
                        },
                    sortable: true
                    },
                    {
                    field: 'maintenanceMan',
                    title:"维保人",
                        sortable: true
                    },
                    {
                        field: 'planTime',
                        title:"计划开始时间",
                        sortable: true
                    },
                    {
                        field: 'planEndTime',
                        title:"计划结束时间",
                        sortable: true
                    },
                    {
                        field: 'planType',
                        title:"类型",
                        sortable: true,
                        formatter: function (value) {
                            if (value == 10)
                                return "半月保";
                            if (value == 20)
                                return "季度保";
                            if (value == 30)
                                return "半年保";
                            if (value == 40)
                                return "全年保";
                            //if (value == 50)
                            //    return "急修";
                            //if (value == 60)
                            //    return "维修";
                        }
                    },
                    {
                        field: 'actualTime',
                        title:"实际完成时间",
                        sortable: true
                    },
                    {
                    field: 'status',
                    title: "状态",
                        formatter: function (value) {
                            if (value == 10)
                                return "未开始";
                            if (value == 20)
                                return "已开始";
                            if (value == 30)
                                return "已完成";
                        },
                        sortable: true
                },
                    {
                        field: 'workBillNumber',
                        title:"工单编号",
                        sortable: true
                    },
                    {
                        field: '',
                        title:translator.translate("operation"),
                        formatter: function(value, row, index){
                            return util.loadPageTemplate("table-row-template", row);
                        }
                    }
                    //{
                    //    field: 'circulationTime',
                    //    title: "流转时间"
                    //},
                    //{
                    //    field: 'remainelevator',
                    //    title:translator.translate("operation"),
                    //    formatter: function(value, row, index){
                    //        if (row.billstatus == 50)
                    //        return ("<a href='/upkeepContract/generateWorkBill?id=" + row.id + "'>" + "生成工单" + "</a>");
                    //    }
                    //}
                ]
            });
        }

        function deletePlan(id){
            swal({
                title: "确定删除该维保计划吗?",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: translator.translate("delete"),
                cancelButtonText: translator.translate("cancel"),
                closeOnConfirm: false,
                html: false
            }, function () {
                ajax.delete("/api/maintenancePlan/" +id, function (data) {
                    if (data.success){
                        swal("删除成功","该计划已删除","success");
                        window.location.href="/maintenancePlan";
                    }
                });
            });
        }
        function initData() {
            vm = new Vue({
                el: '.panel-default',
                data: {},
                methods: {
                    search: function () {
                        $("#dataTable").bootstrapTable("refresh");
                    },
                    exportWorkbill: function () {
                        window.location.href="/workBill/export";
                    }
                }
            });

        }

    });

function setSearch_planStatusValue(){
    var mapHeight = $("#search_status").val();
    $("#status").val(mapHeight);
}


function setSearch_planTypeValue(){
    var mapHeight = $("#search_type").val();
    $("#planType").val(mapHeight);
}
