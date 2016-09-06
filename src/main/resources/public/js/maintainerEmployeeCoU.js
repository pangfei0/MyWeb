define(["jquery","vue","ajax","js/datatable","js/util","swal","translator","toastr","typeahead"],
    function($, Vue, ajax, datatable, util, swal, translator,toastr,typeahead){
        var vm;
        var nameToId={};
        var id=util.getUrlParameter("id");
        var number;
        function init(){
            $(".maintainer-employee").addClass("active");
            initInternal();
            loadMaintainerEmployee(id);
            window.getManager = getManager;

        }
        function loadMaintainerEmployee(id){
            ajax.get("/api/maintenancePersonnel/getInfo/" + id, function (res){
                vm.maintainerEmployee = res.data;
                number=vm.maintainerEmployee.number;
            });
        }
        function getMaintainerName()
        {//维保单位
            $("#maintainerId").typeahead({
                source: function(query, process) {
                    var parameter = {companyName: query,type:20};
                    ajax.post("/api/elevator/searchCompanyName", parameter, function (res) {
                        if(res.success){
                            var array = [];
                            $.each(res.data,function(index, ele){
                                nameToId[ele.name] = ele.id;
                                array.push(ele.name);
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
                    $("#maintainerId").val(item);
                }
            });
        }


        function changeEditInfoMenuState(type){
            if(type=="baseInfoEdit"){
                $("#baseInfoEdit").show();
                $("#brandInfoEdit").show();
                $("#baseInfoShow").hide();
                $("#brandInfoShow").hide();
                getMaintainerName();
            }
        }

        function changeShowInfoMenuState(type){
            loadMaintainerEmployee(id);
            if(type=="baseInfoCancel"){
                $("#baseInfoShow").show();
                $("#brandInfoShow").show();
                $('#divShowManager').show();
                $('#divChangeManager').hide();
                $("#baseInfoEdit").hide();
                $("#brandInfoEdit").hide();

            }
        }

        function getManager() {
            var companyName = $("#maintainerId").val();
            var companyId=nameToId[companyName];
            ajax.post("/api/maintenancePersonnel/searchAllManager/" + companyId, {}, function (res) {
                if (res.success) {
                    $('#divChangeManager').show();
                    $('#divShowManager').hide();
                    $('#manager').html('<option value="-1" selected="selected">--</option>');
                    $.each(res.data, function (index, data) {
                        var opStr = '<option value="' + data + '">' + data + '</option>';
                        $('#manager').append(opStr);
                    });
                }
            })
        }

        function editMaintainerEmployee(){
            var str=""
            $.each($("select:gt(0)"),function(i,n){
                str=str+ n.value+",";
            });
            var number2=$("#number").val();
            var name=$("#name").val();
            var maintainerName=$("#maintainerId").val();
            if(number2!=number&&vm.maintainerEmployee.maintainerId!=null){
                return toastr.error("人员已关联公司，不可修改编号！");
            }else if(number2==null)
            {return toastr.error("请填写人员编号！");}
            else if(number2!=number){
                $.post("/api/maintenancePersonnel/numberSearch", {num: number2}, function (r) {
                    if (r.success) {//判断人员工号是否已存在
                        return toastr.error("人员编号已存在");
                    } else {
                        if(maintainerName!=null&&maintainerName!="")
                        {
                            vm.maintainerEmployee.maintainerId=nameToId[maintainerName];
                            if(nameToId[maintainerName]==null){
                                vm.maintainerEmployee.maintainerName=maintainerName;
                            }
                        }else{
                            vm.maintainerEmployee.maintainerId=null;
                        }
                        vm.maintainerEmployee.levelListId=str;
                        vm.maintainerEmployee.levelList=null;
                        vm.maintainerEmployee.number=number2;
                        if($("#manager").val()!=null){
                            vm.maintainerEmployee.manager=$("#manager").val();
                        }
                        ajax.patch("/api/maintenancePersonnel/edit/" + vm.maintainerEmployee.id, vm.maintainerEmployee, function(res){
                            swal({
                                title: translator.translate("updatedSuccessfully")
                            }, function(){
                                changeShowInfoMenuState("baseInfoCancel");
                            });
                        })
                    }
                });
            }else if(number2==number){
                if(maintainerName!=null&&maintainerName!="")
                {
                    vm.maintainerEmployee.maintainerId=nameToId[maintainerName];
                    if(nameToId[maintainerName]==null){
                        vm.maintainerEmployee.maintainerName=maintainerName;
                    }
                }else{
                    vm.maintainerEmployee.maintainerId=null;
                }
                vm.maintainerEmployee.levelListId=str;
                vm.maintainerEmployee.levelList=null;
                vm.maintainerEmployee.number=number2;
                if($("#manager").val()!=null){
                    vm.maintainerEmployee.manager=$("#manager").val();
                }
                ajax.patch("/api/maintenancePersonnel/edit/" + vm.maintainerEmployee.id, vm.maintainerEmployee, function(res){
                    swal({
                        title: translator.translate("updatedSuccessfully")
                    }, function(){
                        changeShowInfoMenuState("baseInfoCancel");
                    });
                })
            }

        }

        function deleteMaintainerEmployee(){
            swal({
                title: translator.translate("doYouWantToDeleteThisMaintainerEmployee"),
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: translator.translate("delete"),
                cancelButtonText: translator.translate("cancel"),
                closeOnConfirm: false,
                html: false
            }, function(){
                ajax.post("/api/maintenancePersonnel/delete/" + vm.maintainerEmployee.id, {},function (r) {
                    if(r.success)
                    {
                        swal({
                            title: translator.translate("forbidInfo")
                        }, function(){
                            window.location.href = "/maintainerEmployee";
                        });
                    }
                    else
                    {
                        swal(translator.translate("deletedUnsuccessful"), r.description,"success");
                        vm.isBaseInfoEdit = false;
                        $("#baseInfoEditButton").show();
                    }

                })
            });
        }

        function initInternal(){
            vm = new Vue({
                el: "#maintainerEmployeeCoU",
                data: {
                    maintainerEmployee: {
                        id: "",
                        number: "",
                        name: "",
                        manager:"",
                        parentId:"",
                        telephone: "",
                        maintainerId: "",
                        maintainerName:"",
                        levelList:"",
                        levelListId:"",
                        region:"",
                        station:""
                    }
                },
                methods: {
                    baseInfoEdit: function () {
                        changeEditInfoMenuState("baseInfoEdit");
                    },
                    upConfirm: function () {
                       editMaintainerEmployee();
                    },
                    deleteIt: function () {
                        deleteMaintainerEmployee();
                    },
                    baseInfoCancel: function ()
                    {
                        changeShowInfoMenuState("baseInfoCancel");
                    }

                }
            });
        }


        init();

    });