define(["jquery", "vue", "ajax", "js/datatable", "js/util", "swal", "translator", "bootstrap"],
    function ($, Vue, ajax, datatable, util, swal, translator, bootstrap) {
        var vm;

        init();
        window.deletePlanBath=deletePlanBath;
        function init() {
            $(".menu-archive-management").addClass("active");
            $(".work-bill-board").addClass("active");
            initDataTables();
            initData();

        }

        function initDataTables() {
            datatable.createTable("dataTable", "/api/maintenancePlanBath/new/search/page",
                {
                    bAutoWidth : true,
                    columns: [
                    {
                    field: 'upkeepContractNumber',
                    title: "合同编号",
                        sortable: true
                    },
                    {
                    field: 'elevatorNumber',
                    title: "电梯",
                        width:"80px",
                    sortable: true
                    },
                    {
                    field: 'maintenanceMan',
                    title:"维保人",
                        sortable: true
                    },
                    {
                        field: 'startTime',
                        title:"开始时间",
                        sortable: true
                    },
                    {
                        field: 'endTime',
                        title:"结束时间",
                        sortable: true
                    },
                    {
                        field: '',
                        title:translator.translate("operation"),
                        formatter: function(value, row, index){
                            return util.loadPageTemplate("table-row-template", row);
                        }
                    }
                ]
            });
        }

        function deletePlanBath(id){
            swal({
                title: "确定删除该维保批次吗?",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: translator.translate("delete"),
                cancelButtonText: translator.translate("cancel"),
                closeOnConfirm: false,
                html: false
            }, function () {
                ajax.delete("/api/maintenancePlanBath/delete/" + id, function (data) {
                    if (data.success) {
                    swal("删除成功", "已删除该批次", "success");
                    window.location.href = "/maintenancePlanBath";
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