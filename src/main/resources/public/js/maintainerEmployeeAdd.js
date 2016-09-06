define(["jquery","vue","ajax","js/datatable","js/util","swal","translator","toastr","typeahead"],
    function($, Vue, ajax, datatable, util, swal, translator,toastr,typeahead){
        var vm;
        var nameToId={};
        function init(){
            $(".maintainer-employee").addClass("active");
            initInternal();
            getMaintainerName();
            window.getManager = getManager;

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

        function createMaintainerEmployee () {
            var str=""
            $.each($("select:gt(0)"),function(i,n){
                str=str+ n.value+",";
            })
            var number=$("#number").val();
            var name=$("#name").val();
            var maintainerName=$("#maintainerId").val();
            if(number==null||number==""){return toastr.error("请填写人员编号！");}
            else{
                $.post("/api/maintenancePersonnel/numberSearch",{num:number}, function (r) {
                    if (r.success) {
                        return toastr.error("人员编号已存在");
                    } else {
                        if(name==null||name==""){return toastr.error("请填写人员姓名！");}
                        vm.maintainerEmployee.levels=str;
                        vm.maintainerEmployee.manager=$("#manager").val();
                        vm.maintainerEmployee.maintainerId=nameToId[maintainerName];
                        ajax.post("/api/maintenancePersonnel", vm.maintainerEmployee, function (res) {
                                swal({
                                    title: translator.translate("addedSuccessfully")
                                }, function(){
                                    window.location.href="/maintainerEmployee";
                                });
                            }
                        );
                    }
                });
            }
        }

        function getManager() {
            var companyName = $("#maintainerId").val();
            var companyId=nameToId[companyName];
            $('#manager').html('<option value="-1" selected="selected">--</option>');
            ajax.post("/api/maintenancePersonnel/searchAllManager/" + companyId, {}, function (res) {
                if (res.success) {
                    $.each(res.data, function (index, data) {
                        var opStr = '<option value="' + data + '">' + data + '</option>';
                        $('#manager').append(opStr);
                    });
                }
            })
        }

        function initInternal(){
            vm = new Vue({
                el: "#maintainerEmployeeAdd",
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
                        levelListId:""
                    }
                },
                methods: {
                    upConfirm: function () {
                       createMaintainerEmployee();
                    },
                    cancel: function ()
                    {
                        window.location.href = "/maintainerEmployee";
                    }

                }
            });
        }


        init();

    });