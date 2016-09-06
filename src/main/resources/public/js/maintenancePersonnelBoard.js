define(["jquery", "vue", "ajax", "js/datatable", "js/util", "swal", "translator","datetimepicker"],
    function($, Vue, ajax, datatable, util, swal, translator){
        init();

        function init() {
            $(".menu-service-management").addClass("active");
            $(".maintenance-personnel-board").addClass("active");
            setInterval(initDataTables, 1000);
            $(".date").datetimepicker({
                format: 'YYYY-MM-DD HH:mm:ss',
                locale:translator.translate("language")
            });

        }


        var vm = new Vue({
           el: ".search-form",
           methods: {
               search: function(){
                   $("#dataTable").bootstrapTable("refresh");
               },
               export: function () {
                   window.location.href = "/api/maintenancePersonnel/export?startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val();
               }
           }
        });

        function initDataTables(){
            datatable.createTable("dataTable", "/api/maintenancePersonnel/new/search/page",{
                columns: [{
                    field: 'number',
                    title: translator.translate("numberOfMaintainerEmployee")
                },{
                    field: 'name',
                    title: translator.translate("nameOfMaintainerEmployee")
                },{
                    field: 'currentState',
                    title:translator.translate("currentState"),
                    formatter: function(value, row, index) {
                        if(row.currentState == 10) {
                            return translator.translate("in-service");
                        }else if (row.currentState == 20){
                            return translator.translate("onStandby");
                        }else if(row.currentState == 30){
                            return translator.translate("offline");
                        }else{
                            return translator.translate("unsynchronized");
                        }
                    }
                },
                    {
                        field: 'billAllCount',
                        title: "工单总数"
                    },
                    {
                        field: 'byCount',
                        title: "半月保"
                    },
                    {
                        field: 'jdCount',
                        title: "季度"
                    },
                    {
                        field: 'bnCount',
                        title: "半年"
                    },
                    {
                        field: 'qnCount',
                        title: "全年"
                    },
                    {
                        field: 'wxCount',
                        title: "维修"
                    },
                    {
                        field: 'jxCount',
                        title: "急修"
                    },
                    {
                        field: 'jjCount',
                        title: "拒绝总数"
                    },
                    {
                        field: 'excellent',
                        title: "优秀"
                    },
                    {
                        field: 'good',
                        title: "良好"
                    },
                    {
                        field: 'common',
                        title: "一般"
                    },
                    {
                        field: 'poor',
                        title: "差"
                    },
                    {
                        field: 'noComment',
                        title: "未评价"
                    }
                    //{
                    //    field:'',
                    //    title:translator.translate("operation"),
                    //    formatter: function(value, row, index){
                    //                return ("<a href='/maintenancePersonnelInfo?id=" + row.id + "'>查看状态并导出</a>");
                    //
                    //
                    //        }
                    //}
                ]

            })
            

        }


    }


);

function setCurrentStateValue(){
    var mapHeight = $("#search_currentState").val();
    $("#currentState").val(mapHeight);
}