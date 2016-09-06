define(["jquery", "vue", "ajax", "js/util", "swal", "translator","datetimepicker"], function ($, Vue, ajax, util, swal, translator,datetimepicker) {
        var vm;
        function init() {
            $(".maintenance-personnel-board").addClass("active");
            initVue();
            $(".form_datetime").datetimepicker({format: 'YYYY-MM-DD',locale:translator.translate("language")});
            $("#starttime").datetimepicker({format: 'YYYY-MM-DD',locale:translator.translate("language")});
            $("#endtime").datetimepicker({format: 'YYYY-MM-DD',locale:translator.translate("language")});
        }
        function exportInfo()
        {
            window.location.href = "/api/maintenancePersonnel/maintenanceInfoExportExcel?id="+vm.scope.id+"&starttime="+vm.scope.starttime+"&endtime="+vm.scope.endtime;
        }
        //获取员工综合信息
        function getInfo()
        {
            //var d="{id:"+vm.data.scope.id+",starttime:"+vm.data.scope.starttime+",endtime:"+vm.data.scope.endtime+"}"
            $.post("/api/maintenancePersonnel/getMaintenceInfo",vm.scope, function(r){
               if(r.success)
               {
                   vm.info= r.data;
                   $("#info")[0].style.display="block";
               }
                else
               {
                   alert("查询失败！");
               }

        })
        }
        function initVue() {
            vm = new Vue({
                el: "#infoDetail",
                data:{
                    info:{
                        avgbills:"",
                        avgOnduty:"",
                        onduty:"",
                        avgStandBy:"",
                        standby:"",
                        wbbills:"",
                        name:"",
                        wxbills:"",
                        id:"",
                        jxbills:"",
                    },
                    scope:{
                        id:"",
                        starttime:"",
                        endtime:""
                    }
                },
                methods: {
                    export:function()//导出
                    {
                        exportInfo();
                    },
                    back:function()//(取消操作)
                    {
                        window.location.href = "/maintenancePersonnelBoard";
                    },
                    search:function()
                    {
                        getInfo();
                    }

                }
            });
        }

        init();
    }
);