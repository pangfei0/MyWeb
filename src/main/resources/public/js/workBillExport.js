define(["jquery", "vue", "ajax", "js/datatable", "js/util", "swal", "translator", "datetimepicker"],
    function ($, Vue, ajax, datatable, util, swal, translator,datetimepicker)
    {  var vm;

        function init() {
            $(".work-bill-board").addClass("active");


            initVue();
            $(".form_datetime").datetimepicker({format: 'YYYY-MM-DD',locale:translator.translate("language")});
            $("#starttime2").datetimepicker({format: 'YYYY-MM-DD',locale:translator.translate("language")});
            //$("#completetime1").datetimepicker({format: 'YYYY-MM-DD',locale:translator.translate("language")});
            //$("#completetime2").datetimepicker({format: 'YYYY-MM-DD',locale:translator.translate("language")});

        }
        function initVue() {
            vm = new Vue({
                el: "#workBillExport",
                data:{
                  workBill:{
                      startTime1:"",
                      startTime2:"",
                      //completeTime1:"",
                      //completeTime2:"",
                      elevatorNumber:"",
                      billCategory:""
                  }
                },
                methods: {

                    back:function()//(取消操作)
                    {
                        window.location.href = "/workBillBoard";
                    },
                    export:function()//导出
                    {
                        var url="/api/workBill/exportExcel?startTime1="+vm.workBill.startTime1+"&startTime2="+vm.workBill.startTime2+
                                "&billCategory="+
                            vm.workBill.billCategory+"&elevatorNumber="+vm.workBill.elevatorNumber;
                        window.location.href = url;
                        //ajax.post("/api/workBill/exportExcel",vm.workBill,function (res) {
                        //    swal({
                        //        title: translator.translate("addedSuccessfully")
                        //    }, function () {
                        //        //var id = res.data.id;
                        //        window.location.href = "/workBillBoard";
                        //    });
                        //});
                    }

                }
            });
        }

        init();
    }
);