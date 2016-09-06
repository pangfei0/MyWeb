define(["jquery", "vue", "ajax", "js/datatable", "js/util", "swal", "translator", "datetimepicker","typeahead","toastr"],
    function ($, Vue, ajax, datatable, util, swal, translator,  datetimepicker,typeahead,toastr) {
        var vm;
        var id;
        var elevatorId;

        function init() {
            $(".menu-archive-management").addClass("active");
            $(".companyMaintain").addClass("active");
            loadCompanyMaintain(util.getUrlParameter("id"));
            initInternal();
        }

        function loadCompanyMaintain(id) {
            ajax.get("/api/companyMaintain/getDetail/" + id, function (res) {
                vm.company = res.data;
            })
        }

        function changeEditMenuState(type) {
            if (type == "baseInfo") {
                $("#baseInfoShow").hide();
                $("#baseInfoUpdate").show();
            }
            else if (type == "elevatorInfo") {
                $("#elevatorInfoShow").hide();
                $("#elevatorInfoUpdate").show();
                $("#companyType").val(vm.company.baseInfo.type);
                initDataTables();
                getProjectName();
            }
        }

        function changeShowMenuState(type) {
            loadCompanyMaintain(util.getUrlParameter("id"));
            if (type == "baseInfo") {
                $("#baseInfoShow").show();
                $("#baseInfoUpdate").hide();
            } else if (type == "elevatorInfo") {
                $("#elevatorInfoShow").show();
                $("#elevatorInfoUpdate").hide();
                $("#visibleForMaintainer").hide();
            }
        }

        function getProjectName() {
            $("#projectName").typeahead({
                source: function(query, process) {
                    var parameter = {projectName: query};
                    ajax.post("/api/companyMaintain/searchProjectName/"+vm.company.baseInfo.type, parameter, function (res) {
                        if(res.success){
                            var array = [];
                            $.each(res.data,function(index, ele){
                                array.push(ele);
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
                    $("#projectName").val(item);
                }
            });

        }

        function initDataTables() {
            datatable.createTable("elevatorDataTable", "/api/companyMaintain/new/searchElevator/page", {
                columns: [{
                    title: '',
                    formatter: function (value, row, index) {
                        return (index + 1);
                    }
                }, {
                    field: 'number',
                    title: translator.translate("numberOfElevator"),  //即电梯合同号
                    formatter: function (value, row, index) {
                        return ("<a href='/elevator/cou?id=" + row.id + "'>" + row.number + "</a>");
                    }
                }, {
                    field: 'alias',
                    title: translator.translate("addressOfElevator")
                }, {
                    field: 'projectName',
                    title: translator.translate("projectName")
                },
                //    {
                //    field: 'elevatorBrand.name',
                //    title: translator.translate("elevatorBrand"),
                //    formatter: function (value, row, index) {
                //        if (row.elevatorBrand == null) return "-";
                //        return row.elevatorBrand.name;
                //    }
                //},
                    {
                    field: 'elevatorType',
                    title: translator.translate("elevatorType")
                }, {
                    field: '',
                    title: translator.translate("operation"),
                    formatter: function (value, row, index) {
                        var str = " <input type='checkbox'  value='" + row.id + "'>";
                        return str;
                    }
                }]
            });

        }

        function editBaseInfoOfCompanyMaintain() {
            ajax.patch("/api/companyMaintain/" + vm.company.baseInfo.id, vm.company.baseInfo, function (res) {
                swal({
                    title: translator.translate("updatedSuccessfully")
                }, function () {
                    changeShowMenuState("baseInfo");
                });
            })
        }

        function deleteCompanyMaintain() {
            swal({
                title: "需要删除这个公司？",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: translator.translate("delete"),
                cancelButtonText: translator.translate("cancel"),
                closeOnConfirm: false,
                html: false
            }, function () {
                ajax.delete("/api/companyMaintain/" + vm.company.baseInfo.id, function () {
                    swal(translator.translate("deletedInfo"), translator.translate("deletedInfo"), "success");
                    window.location.href = "/companyMaintain";
                })
            });
        }

        function removeElevator(id) {
            swal({
                title: "确认删除电梯?",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: translator.translate("SureDelete"),
                cancelButtonText: translator.translate("cancel"),
                closeOnConfirm: false,
                html: false
            }, function () {
                ajax.post("/api/companyMaintain/removeElevator/" + vm.company.baseInfo.id, {
                    elevatorId: id,
                    companyType: vm.company.baseInfo.type
                }, function () {
                    swal(translator.translate("RemoveInfo"), translator.translate("RemoveInfo"), "success");
                    loadCompanyMaintain(util.getUrlParameter("id"));
                })
            });
        }

        function updateElevatorMaintenance(id){
            if(vm.company.baseInfo.type!="20"){
                toastr.error("非维保公司不可以更新维保人员");
                $("#elevatorInfoShow").show();
                $("#elevatorInfoUpdate").hide();
                $("#visibleForMaintainer").hide();
            }else{
                elevatorId=id;
                $("#visibleForMaintainer").show();
                getMaintenanceName();
                window.getMen=getMen;
                window.updateMan=updateMan;
            }

        }

        function getMaintenanceName(){
            ajax.post("/api/companyMaintain/searchAllManager/" + vm.company.baseInfo.id,{}, function (res) {
                if (res.success) {
                    $('#search_manager').html('<option value="-1" selected="selected">--</option>');
                    $.each(res.data, function (index, data) {
                        var opStr = '<option value="' + data + '">' + data + '</option>';
                        $('#search_manager').append(opStr);
                    });
                }
            })
        }

        function getMen(){
            var manager=$("#search_manager").val();
            ajax.post("/api/companyMaintain/searchMen/" +manager,{}, function (res) {
                    if (res.success) {
                        $('#search_men').html('<option value="-1" selected="selected">--</option>');
                        $.each(res.data, function (index, data) {
                            var opStr = '<option value="' + data + '">' + data + '</option>';
                            $('#search_men').append(opStr);
                        });
                    }
                })
        }

        function updateMan(){
            var manName=$('#search_men').val();
            if(manName!='-1'){
                ajax.post("/api/companyMaintain/updateMan/" + manName,{eleId:elevatorId,companyId:vm.company.baseInfo.id}, function (res) {
                    swal(translator.translate("addedSuccessfully"), translator.translate("addedSuccessfully"), "success");
                    changeShowMenuState("elevatorInfo");
                })
            }else
            {
                swal({title:"请选择人员！"});
            }

        }

        function addElevator() {
            var str = ""
            $.each($(":checkbox"), function (i, n) {
                if (n.checked)
                    str = str + n.value + ","
            })
            swal({
                title:  "确定将以下电梯添加到该公司吗?",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: translator.translate("SureAdd"),
                cancelButtonText: translator.translate("cancel"),
                closeOnConfirm: false,
                html: false
            }, function () {
                ajax.post("/api/companyMaintain/addElevator/" + vm.company.baseInfo.id, {elevatorStr: str,companyType: vm.company.baseInfo.type}, function () {
                    swal(translator.translate("addedSuccessfully"), translator.translate("addedSuccessfully"), "success");
                    changeShowMenuState("elevatorInfo");
                })
            });
        }

        function initInternal() {
            vm = new Vue({
                el: "#companyMaintainCoU",
                data: {
                    isBaseInfoEdit: false,
                    company: {
                        baseInfo: {
                            id: "",
                            name: "",
                            contact: "",
                            phone: "",
                            address: "",
                            mobile: "",
                            type: "",
                            typeName:""
                        },
                        elevatorInfo: [{
                            id: "",
                            number: "",
                            elevatorType: "",
                            projectName: "",
                            address: "",
                            alias:"",
                            maintenanceName:"",
                            maintenanceManager:""
                        }]
                    }
                },
                methods: {
                    baseInfoEdit: function () {
                        this.isBaseInfoEdit = true;
                        changeEditMenuState("baseInfo");
                    },
                    baseInfoConfirm: function () {
                        editBaseInfoOfCompanyMaintain();
                    },
                    baseInfoCancel: function () {
                        changeShowMenuState("baseInfo");
                    },
                    deleteIt: function () {
                        deleteCompanyMaintain();
                    },
                    removeElevator: function (id) {
                        removeElevator(id);
                    },
                    updateElevatorMaintenance: function (id) {
                        updateElevatorMaintenance(id);
                    },
                    elevatorCancel: function () {
                        changeShowMenuState("elevatorInfo");
                    },
                    elevatorInfoEdit: function () {
                        $("#projectName").val("");
                        $("#number").val("");
                        changeEditMenuState("elevatorInfo");
                    },
                    addElevator: function () {
                        addElevator();
                    },
                    search: function () {
                        $('#companyType').val(vm.company.baseInfo.type);
                        $("#elevatorDataTable").bootstrapTable("refresh");
                    }
                }
            });
        }

        init();
    });



