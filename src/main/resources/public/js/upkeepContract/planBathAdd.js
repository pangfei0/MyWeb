define(["jquery", "vue", "ajax", "js/datatableG", "js/util", "swal", "translator", "datetimepicker","toastr"],
    function ($, Vue, ajax, datatable, util, swal, translator,datetimepicker,toastr)
    {  var vm;

        function init() {
            //$(".work-bill-board").addClass("active");


            initVue();
            $("#startTime").datetimepicker({format: 'YYYY-MM-DD',locale:translator.translate("language")});
            $("#endTime").datetimepicker({format: 'YYYY-MM-DD',locale:translator.translate("language")});

        }
        function initVue() {
            vm = new Vue({
                el: "#planPath",
                data:{
                  bath:{
                      startTime:"",
                      //endTime:"",
                      maintenanceMan:"",
                      days:"1",
                      daysEd:"1"
                      //billCategory:""
                  }
                },
                methods: {
                    addElevatorPage:function()
                    {
                        $("#elevatorInfoUpdate").show();
                        initDataTables();
                    },
                    elevatorCancel:function()//(取消操作)
                    {
                        window.location.href = "/upkeepContract";
                    },
                    addElevator:function()//导出
                    {
                        addElevator();
                    }

                }
            });
        }

        //初始化电梯
        function initDataTables(){
            var upkeepContractId = $("#upkeepContractId").val();
            datatable.createTableG("elevatorTable","/api/upkeepContract/bathElevator?id="+upkeepContractId,
                {
                columns: [{
                    title: '',
                    formatter: function(value, row, index){
                        return (index + 1);
                    }
                },{
                    field: 'number',
                    title: translator.translate("numberOfElevator"),  //即电梯合同号
                    formatter: function(value, row, index){
                        return ("<a href='/elevator/cou?id=" + row.id + "'>" + row.number + "</a>");
                    }
                },{
                    field: 'alias',
                    title: translator.translate("premises")
                }
                ,{
                        field: 'address',
                        title: translator.translate("addressOfElevator")
                    },{
                    field: 'elevatorType',
                    title:translator.translate("elevatorType")
                },{
                    field: '',
                    title:translator.translate("operation"),
                    formatter: function(value, row, index){
                        //var b="'"+row.id+"'";
                        //var a="addElevator("+b+")";
                        //var str="<button type='button' class='btn btn-success col-sm-offset-2' "+"@click="+a+">添加</button>";
                        //return str;

                        var str=" <input type='checkbox'  value='"+row.id+"'>";
                        return str;
                    }
                }]
            });
        }

        function addElevator(){
            var str=""
            $.each($(":checkbox"),function(i,n){
                if(n.checked)
                    str=str+ n.value+","
            })
            //swal({
            //    title: "确定生成批次吗"+"?",
            //    type: "warning",
            //    showCancelButton: true,
            //    confirmButtonColor: "#DD6B55",
            //    confirmButtonText: translator.translate("SureAdd"),
            //    cancelButtonText: translator.translate("cancel"),
            //    closeOnConfirm: false,
            //    html: false
            //}, function () {
                var upkeepContractId = $("#upkeepContractId").val();
                var maintenanceManId=$("#maintenanceMan").val();
                if(maintenanceManId==null||maintenanceManId=="-1")
                {
                    toastr.error("请务必选择维保人员！");

                }else{
                    ajax.post("/api/upkeepContract/bathElevatorAdd/"+upkeepContractId,
                        {
                            elevatorStr:str,
                            startTime: vm.bath.startTime,
                            endTime:vm.bath.endTime,
                            maintenanceManId:vm.bath.maintenanceMan,
                            days:vm.bath.days,
                            daysEd:vm.bath.daysEd
                        },
                        function () {
                            swal(translator.translate("addedSuccessfully"), translator.translate("addedSuccessfully"), "success");
                            window.location.href = "/upkeepContract";
                        }
                    )
                }

            //});
        }

        init();
    }
);