define(["jquery", "vue", "ajax", "js/datatable", "js/util", "swal", "translator", "bootstrap"],
    function ($, Vue, ajax, datatable, util, swal, translator,  bootstrap) {
        var vm;

        init();
        window.deleteWorkBill = deleteWorkBill;
        function init() {
            $(".menu-service-management").addClass("active");
            $(".work-bill-board").addClass("active");
            initDataTables();
            //setInterval(function () {
            //    $("#dataTable").bootstrapTable("refresh", {silent: true});
            //}, 2000);
            initData();
            $(document).ready(function(){
                    showpic();
                }
            );
        }

        function initDataTables() {
            datatable.createTable("dataTable", "/api/workBill/new/search/page",
                {
                columns: [{
                    field: 'billNumber',
                    title: translator.translate("numberOfWorkBill"),
                    formatter: function (value, row, index) {
                        if(row.billCategory == 20){
                            return ("<a style='color:green;'href='/workBill/cou/?id=" + row.id + "'>" + row.billNumber + "</a>");
                        }
                        if (row.billstatus == 40 || row.billstatus == 5|| row.billstatus == 50) {
                            if (row.billstatus == 40)
                                return ("<a style='color:red;' href='/workBill/cou/?id=" + row.id + "'>" + row.billNumber + "</a>");
                            if (row.billstatus == 5)
                                return ("<a style='color:green;'href='/workBill/cou/?id=" + row.id + "'>" + row.billNumber + "</a>");
                            if (row.billstatus == 50)
                                return ("<a style='color:green;'href='/workBillBoard/detail?id=" + row.id + "'>" + row.billNumber + "</a>");
                        }

                        else
                            return row.billNumber;
                    }
                }, {
                    field: 'billModel',
                    title: "工单类别",
                    formatter: function (value) {
                        if (value == 0)
                            return "半月保养";
                        else if (value == 10)
                            return "季度保养";
                        else if (value == 20)
                            return "半年保养";
                        else if (value == 30)
                            return "年度保养";
                        else if (value == 40)
                            return "急修工单";
                        else if (value == 50)
                            return "维修工单";
                    },
                    sortable: true
                },
                    {
                        field: 'alias',
                        title:translator.translate("premises"),
                        sortable: true
                    },
                    {
                    field: 'elevatorNumber',
                    title:translator.translate("numberOfElevator")
                }, {
                    field: 'billstatus',
                    title: translator.translate("currentState"),
                    formatter: function (value) {
                        if (value == 5)
                            return translator.translate("no-distribute");
                        if (value == 10)
                            return translator.translate("no-receive");
                        if (value == 20)
                            return translator.translate("ongoing");
                        if (value == 30)
                            return translator.translate("pause");
                        if (value == 40)
                            return translator.translate("refuse");
                        if (value == 45)
                            return translator.translate("refuseDone");
                        if (value == 50)
                            return translator.translate("done");
                        if (value == 55)
                            return "转维修";
                        if (value == 60)
                            return "待评价";
                    },
                        sortable: true
                }, {
                    field: 'maintenanceName',
                    title: translator.translate("actor")
                },
                    {
                        field: 'assists',
                        title: "辅助人员"
                    },
                    {
                        field: 'startTimeStr',
                        title:translator.translate("responseTime"),
                        sortable: true
                    },
                    {
                        field: '',
                        title:translator.translate("operation"),
                        formatter: function(value, row, index){
                            if (row.billstatus == 50)
                                return ("<a href='/upkeepContract/generateWorkBill?id=" + row.id + "'>" + "生成报告" + "</a>");
                        }
                    },{
                        field: '',
                        title:translator.translate("delete"),
                        formatter: function(value, row, index){
                            if (row.billstatus == 5||row.billstatus == 10||row.billstatus ==40||row.billstatus ==45)
                                return util.loadPageTemplate("table-row-template", row);
                       }
                    }
                ]
            });
        }

        function deleteWorkBill(id) {
            swal({
                title: "确定删除该工单吗?",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: translator.translate("delete"),
                cancelButtonText: translator.translate("cancel"),
                closeOnConfirm: false,
                html: false
            }, function () {
                ajax.delete("/api/workBill/delete/" + id, function (data) {
                    if(data.success){
                        swal("删除工单", "已删除该工单", "success");
                        window.location.href="/workBillBoard";
                    }

                });
            });
        }

        function showpic(){
            xOffset = 10;
            yOffset = 30;
            $('.upkeep').hover(function(e){
                    $("#dataTable").append("<p id='screenshot'><img src='/image/workbills/upkeep.png'/></p>");
                    $("#screenshot")
                        .css("top",(e.pageY - xOffset) + "px")
                        .css("left",(e.pageX + yOffset) + "px")
                        .css("z-index",2)
                        .fadeIn("fast");
                },
                function(){
                    $("#screenshot").remove();
                });
            $(".upkeep").mousemove(function(e){
                $("#screenshot")
                    .css("top",(e.pageY-xOffset)+"px")
                    .css("left",(e.pageX+yOffset)+"px");
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

function setSearch_billstatusValue(){
    var mapHeight = $("#search_billstatus").val();
    $("#billstatus").val(mapHeight);
}

function setSearch_billcategoryValue(){
    var mapHeight = $("#search_billcategory").val();
    $("#billCategory").val(mapHeight);
}