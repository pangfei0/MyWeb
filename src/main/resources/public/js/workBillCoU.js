define(["jquery", "vue", "ajax", "js/util", "swal", "translator", "typeahead"], function ($, Vue, ajax, util, swal, translator, typeahead) {
        var vm;
        var numberToId = {};

        function init() {
            $(".work-bill-board").addClass("active");
            initVue();
            if (isEdit()) {
                //load existing workBill
                loadWorkBill(util.getUrlParameter("id"));
                $("#billNumber").attr("disabled",true);
                $("#billCategory").attr("disabled",true);
                $("#billModel").attr("disabled",true);
                $("#elevator").attr("disabled",true);
            } else {
                //$("#refusesend")[0].style.display="none";
                $("#billNumber").attr("disabled",true);
                $("#refusesend").attr("disabled",true);
            }
            elevatorTypeAhead();

            $("#billCategory").change(function () {
                if ($(this).children('option:selected').val() == 10) {
                    $("#billModel").val("50");
                    $("#billModel option[value='0']").wrap('<span>').hide();
                    $("#billModel option[value='10']").wrap('<span>').hide();
                    $("#billModel option[value='20']").wrap('<span>').hide();
                    $("#billModel option[value='30']").wrap('<span>').hide();
                    $("#billModel option[value='40']").wrap('<span>').hide();
                }
                if ($(this).children('option:selected').val() == 20) {
                    $("#billModel option[value='40']").wrap('<span>').hide();
                    $("#billModel option[value='50']").wrap('<span>').hide();
                }
                if ($(this).children('option:selected').val() == 30) {
                    $("#billModel").val("40");
                    $("#billModel option[value='0']").wrap('<span>').hide();
                    $("#billModel option[value='10']").wrap('<span>').hide();
                    $("#billModel option[value='20']").wrap('<span>').hide();
                    $("#billModel option[value='30']").wrap('<span>').hide();
                    $("#billModel option[value='50']").wrap('<span>').hide();
                }
            });

            //ajax.get("/api/elevator/elevatorDto/all/", function (res) {
            //    vm.elevators = res.data;
            //});

            ajax.get("/api/maintenancePersonnel/maintenance/dto/", function (res) {
                vm.maintenances = res.data;
            });

        }

        function isEdit() {
            return util.isUrlParameterExist("id");
        }

        function loadWorkBill(id) {
            $.get("/api/workBill/dto/" + id, function (res) {
                vm.workBill = res.data;
                var status=vm.workBill.billstatus;
                switch (status)
                {
                    case 5://工单待派发，只显示立即派发按钮
                        $("#refusesend").hide();//工单改派
                        $("#aftersend").hide();//稍后派发（更新）
                        break;
                    case 10://待接收的工单,可改派
                        $("#nowsend").hide();//立即派发
                        $("#aftersend").hide();
                        break;
                    case 20://处理中的工单，可改派
                        $("#nowsend").hide();
                        $("#aftersend").hide();
                        break;
                    case 30://已暂停的工单，可改派
                        $("#nowsend").hide();
                        $("#aftersend").hide();
                        break;
                    case 40://被拒绝的工单，可改派
                        $("#nowsend").hide();
                        $("#aftersend").hide();
                        break;
                    case 45://拒绝已处理的工单
                        $("#nowsend").hide();
                        $("#aftersend").hide();
                        $("#refusesend").hide();
                        $("#cancel").hide();
                        break;
                    case 50://已完成
                        $("#nowsend").hide();
                        $("#aftersend").hide();
                        $("#refusesend").hide();
                        $("#cancel").hide();
                        break;
                    case 55://转维修
                        $("#nowsend").hide();
                        $("#aftersend").hide();
                        $("#refusesend").hide();
                        $("#cancel").hide();
                        break;
                    case 60://待评价工单
                        $("#nowsend").hide();
                        $("#aftersend").hide();
                        $("#refusesend").hide();
                        $("#cancel").hide();
                        break;
                }
            });
        }

        function editWorkBill(actiontype) {
            var test = {
                billNumber: vm.workBill.billNumber,
                billModel: vm.workBill.billModel,
                billCategory: vm.workBill.billCategory,
                elevatorId: vm.workBill.elevatorId,
                maintenanceId: vm.workBill.maintenanceId,
                faultDescription: vm.workBill.maintenanceId,
                faultPerson: vm.workBill.faultPerson,
                faultPersonTelephone: vm.workBill.faultPersonTelephone,
                elevatorNumber:vm.workBill.elevatorNumber,
                actiontype: actiontype
            };
            if(test.elevatorNumber==null||test.elevatorNumber==''){
                swal({
                    title: "请输入电梯工号"
                });
                return false;
            }
            $.post("/api/workBill/existElevator",{num:test.elevatorNumber}, function (res) {
                if (res.success) {
                    swal({
                        title: "电梯工号不存在"
                    });
                    return false;
                } else {
                    ajax.post("/api/workBill/" + vm.workBill.id, test, function (res) {
                        swal({
                            title: translator.translate("updatedSuccessfully")
                        }, function () {
                            window.location.href = "/workBillBoard";
                        });
                    });
                }
            });
        }

        function createWorkBill(actiontype) {
            var test = {
                billNumber: vm.workBill.billNumber,
                billModel: vm.workBill.billModel,
                billCategory: vm.workBill.billCategory,
                elevatorId: vm.workBill.elevatorId,
                maintenanceId: vm.workBill.maintenanceId,
                faultDescription: vm.workBill.faultDescription,
                faultPerson: vm.workBill.faultPerson,
                faultPersonTelephone: vm.workBill.faultPersonTelephone,
                elevatorNumber:vm.workBill.elevatorNumber,
                actiontype: actiontype
            };
            if(test.elevatorNumber==null||test.elevatorNumber==''){
                swal({
                    title: "请输入电梯工号"
                });
                return false;
            }

            //空判断
            if(test.billModel==null||test.billModel==''){
                swal({
                    title: "请选择工单模板"
                });
                return false;
            }
            if(test.maintenanceId==null||test.maintenanceId==''){
                swal({
                    title: "请指派员工"
                });
                return false;
            }
            $.post("/api/workBill/existElevator",{num:test.elevatorNumber}, function (res) {
                if(res.success)
                {
                    swal({
                    title: "电梯工号不存在"
                });
                    return false;
                }else
                {
                    ajax.post("/api/workBill/create", test, function (res) {
                        swal({
                            title: translator.translate("pushedSuccessfully")
                        }, function () {
                            //var id = res.data.id;
                            window.location.href = "/workBillBoard";
                        });
                    });
                }
            })
        }

        //拒绝工单改派
        function resendother(actiontype) {
            var test = {
                billNumber: vm.workBill.billNumber,
                billModel: vm.workBill.billModel,
                billCategory: vm.workBill.billCategory,
                elevatorId: vm.workBill.elevatorId,
                maintenanceId: vm.workBill.maintenanceId,
                faultDescription: vm.workBill.faultDescription,
                faultPerson: vm.workBill.faultPerson,
                faultPersonTelephone: vm.workBill.faultPersonTelephone,
                actiontype: actiontype
            };
            ajax.patch("/api/workBill/sendJXBill/" + vm.workBill.id, test , function (res) {
                swal({
                    title: "确定改派给该维保人员吗",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "确定",
                    cancelButtonText: translator.translate("cancel"),
                    closeOnConfirm: false,
                    html: false
                }, function () {
                    //var id = res.data.id;
                    window.location.href = "/workBillBoard";
                });
            });
        }



        //typeahead处理
        function elevatorTypeAhead() {
            $("#elevatorInput").typeahead({
                source: function(query, process) {
                    var parameter = {number: query};
                    ajax.post("/api/elevator/elevatorDto/typeahead", parameter, function (res) {
                        if(res.success){
                            var array = [];
                            $.each(res.data,function(index, ele){
                                numberToId[ele.number] = ele.id;
                                array.push(ele.number);
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
                    vm.workBill.elevatorId = numberToId[item] ;
                }
            });
        }

        function initVue() {
            vm = new Vue({
                el: "#workBillCoU",
                data: {
                    isEdit: isEdit(),
                    elevators:[{
                        id:"",
                        elevatorNumber:""
                    }],
                    maintenances:[{
                        maintainerId:"",
                        name:"",
                        number:""
                    }],
                    workBill: {
                        id: "",
                        billNumber: "",
                        billModel:"",
                        billCategory:"",
                        elevatorId:"",
                        elevatorNumber: "",
                        maintenanceId:"",
                        billstatus:"",
                        faultDescription:"",
                        faultPerson:"",
                        faultPersonTelephone:""
                    }
                },
                methods: {
                    afterSend: function () {//稍后派发（更新）
                        if (isEdit()) {

                            editWorkBill(2);
                        }
                        else {
                            createWorkBill(2);
                        }
                    },
                    nowSend: function()//(立即派发)
                    {
                         if(isEdit())
                         {
                             editWorkBill(1);
                         }
                        else
                         {
                             createWorkBill(1);
                         }
                    },
                    resendother: function()
                    {
                        resendother(1);
                    },
                    cancel: function()//(取消操作)
                    {
                        window.location.href = "/workBillBoard";
                    }

                }
            });
        }

        init();
    }
);

//onchange事件
function changeDiv(obj){
    if(obj == 50|| obj ==40){
        document.getElementById("faultDate").style.display="";//显示
    }else{
        document.getElementById("faultDate").style.display="none";//隐藏
    }
}