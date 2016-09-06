define(["jquery", "vue", "ajax", "js/datatable", "js/util", "swal", "translator"],
    function ($, Vue, ajax, datatable, util, swal, translator) {
        init();
        function init(){
            $(".menu-service-management").addClass("active");
            $(".report-board").addClass("active");

            initDataTables();
            var vm= new Vue({
                el: ".search-form",
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



        function initDataTables() {
            datatable.createTable("dataTable","/api/report/getReports",{
                columns:[{
                    field: 'reportNumber',
                    title:translator.translate("billNumber"),
                    formatter: function (value, row, index) {
                        if (row.reportStatus ==20) {
                            return ("<a style='color:green;'href='/reportBoard/detail?id=" + row.id + "'>" + row.reportNumber + "</a>");
                        }
                        else
                            return row.reportNumber;
                    }
                }, {
                    field: 'reportCategory',
                    title:translator.translate("billType"),
                    formatter: function (value) {
                        if (value == 10)
                            return translator.translate("maintenanceReport");
                        if (value == 20)
                            return translator.translate("upkeepReport");
                        if (value == 30)
                            return translator.translate("rescueReport");
                    },
                    sortable: true
                }, {
                    field: 'elevatorNumber',
                    title: translator.translate("numberOfElevator")
                }, {
                    field: 'reportTimeStr',
                    title: "评价时间",
                    sortable: true
                }, {
                    field: 'maintenanceName',
                    title: translator.translate("actor")
                    }]
            })
        }
});