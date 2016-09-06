define(["jquery", "vue", "ajax", "js/util", "swal", "translator","datetimepicker","js/formValidator"],
    function ($, Vue, ajax, util, swal, translator,datetimepicker,validator)
    {  var vm;

        function init() {
            //$(".work-bill-board").addClass("active");


            initInternal();
            $(".date").datetimepicker({
                format: 'YYYY-MM-DD',
                locale:translator.translate("language")
            });
            if(isEdit()){
                loadMaintenancePlan(util.getUrlParameter("id"));
            }
            validator.formValidator("maintenancePlanCou");

        }

        function initInternal(){
            vm = new Vue({
                el: '#maintenancePlanCou',
                data: {
                    isEdit: isEdit(),
                    maintenancePlan: {
                        id: "",
                        upkeepContractNumber: "",
                        number: "",
                        maintenanceManId: "",
                        maintenanceMan: "",
                        planTime: "",
                        planEndTime: "",
                        planType:""
                    }
                },
                methods: {
                    confirm: function(){
                        if(isEdit()){
                                //ajax.post("/api/maintenancePlan", vm.maintenancePlan, function() {
                                //    //if(vm.maintenancePlan.number==null||vm.maintenancePlan.number==""){
                                //    //    swal(translator.translate("addedFailed"),translator.translate("addedFailed"),"Failed");
                                //    //}else{
                                //    swal(translator.translate("addedSuccessfully"), translator.translate("addedSuccessfully"), "success");
                                //    //window.location.href = "/maintenancePlan" ;
                                //    //}
                                //});
                            editMaintenancePlan();
                            //ajax.patch("/api/maintenancePlan/upd/"+ vm.maintenancePlan.id,vm.maintenancePlan,function(){
                            //    swal({
                            //        title: translator.translate("updatedSuccessfully")
                            //    }, function(){
                            //        window.location.href = "/elevator";
                            //    });
                            //});
                        }else{
                            addMaintenancePlan();
                        }
                    },
                    deleteIt: function(){
                        deleteMaintenancePlan();
                    }
                }
            });
        }

        function isEdit(){
            return util.isUrlParameterExist("id");
        }
///
        function loadMaintenancePlan(id){
            ajax.get("/api/maintenancePlan/findById/" + id, function (res){
                vm.maintenancePlan = res.data;
            });
        }

        function addMaintenancePlan(){
            ajax.post("/api/maintenancePlan", vm.maintenancePlan, function() {
                //if(vm.maintenancePlan.number==null||vm.maintenancePlan.number==""){
                //    swal(translator.translate("addedFailed"),translator.translate("addedFailed"),"Failed");
                //}else{
                    swal(translator.translate("addedSuccessfully"), translator.translate("addedSuccessfully"), "success");
                    //window.location.href = "/maintenancePlan" ;
                //}
            });

        }

        //修改开始
        function editMaintenancePlan(actiontype) {
            var test = {
                number: vm.maintenancePlan.number,
                planType: vm.maintenancePlan.planType,
                maintenanceManId: vm.maintenancePlan.maintenanceManId,
                planTime: vm.maintenancePlan.planTime,
                planEndTime: vm.maintenancePlan.planEndTime
            };
            ajax.post("/api/maintenancePlan/upd/"+ vm.maintenancePlan.id,test,function(){
                //swal({
                //    title: translator.translate("updatedSuccessfully")
                //}, function(){
                    window.location.href = "/maintenancePlan";
                //});
            });

        }

        function deleteMaintenancePlan(){
            swal({
                title: "确定删除该计划吗？",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: translator.translate("delete"),
                cancelButtonText: translator.translate("cancel"),
                closeOnConfirm: false,
                html: false
            }, function(){
                ajax.delete("/api/maintenancePlan/" + vm.maintenancePlan.id, function (data) {
                    if (data.success){
                        swal("删除成功","该计划已删除","success");
                        window.location.href="/maintenancePlan";
                    }
                });
            });
        }


/////
//        function addElevator(){
//            var str=""
//            $.each($(":checkbox"),function(i,n){
//                if(n.checked)
//                    str=str+ n.value+","
//            })
//            swal({
//                title: "确定生成批次吗"+"?",
//                type: "warning",
//                showCancelButton: true,
//                confirmButtonColor: "#DD6B55",
//                confirmButtonText: translator.translate("SureAdd"),
//                cancelButtonText: translator.translate("cancel"),
//                closeOnConfirm: false,
//                html: false
//            }, function () {
//                var upkeepContractId = $("#upkeepContractId").val();
//                ajax.post("/api/upkeepContract/bathElevatorAdd/"+upkeepContractId,
//                    {
//                        elevatorStr:str,
//                        startTime: vm.bath.startTime,
//                        endTime:vm.bath.endTime,
//                        maintenanceManId:vm.bath.maintenanceMan
//                    },
//                    function () {
//                    swal(translator.translate("addedSuccessfully"), translator.translate("addedSuccessfully"), "success");
//                    window.location.href = "/upkeepContract";
//                })
//            });
//        }

        init();
    }
);