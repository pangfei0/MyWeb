define(["jquery", "vue", "ajax", "js/datatable", "js/util", "swal", "translator", "datetimepicker","toastr","typeahead"],
    function ($, Vue, ajax, datatable, util, swal, translator, datetimepicker,toastr,typeahead) {
        var vm;
        var id;


        function init() {
            $(".menu-archive-management").addClass("active");
            $(".upkeep-contract").addClass("active");
            id=util.getUrlParameter("id")
            loadUpkeepContract(id);
            initInternal();
            $("#endDate").datetimepicker({format: 'YYYY-MM-DD',locale:translator.translate("language")});
            $("#beginDate").datetimepicker({format: 'YYYY-MM-DD',locale:translator.translate("language")});
            $("#billingDate").datetimepicker({format: 'YYYY-MM-DD',locale:translator.translate("language")});
            $("#collectInfoDate").datetimepicker({format: 'YYYY-MM-DD',locale:translator.translate("language")});

        }
        function loadUpkeepContract(id) {
            ajax.get("/api/upkeepContract/getDetail/" + id, function (res) {
                vm.upkeepContract = res.data;
            })
        }

        function changeEditMenuState(type) {
          if(type=="baseInfo")
          {
              $("#baseInfoShow").hide();
              $("#baseInfoUpdate").show();
          }
          else if(type=="stateInfo")
          {
              $("#stateInfoShow").hide();
              $("#stateInfoUpdate").show();
          }
          else if(type=="billInfo")
          {
              vm.newBill.billNO="";
              vm.newBill.billingDate="";
              vm.newBill.billValue="";
              $("#billInfoShow").hide();
              $("#billInfoUpdate").show();
          }
          else if(type=="collectInfo")
          {
              vm.newCollect.billNo="";
              vm.newCollect.collectingValue="";
              vm.newCollect.createBy="";
              vm.newCollect.collectionDate="";
              $("#collectInfoShow").hide();
              $("#collectInfoUpdate").show();
          }
          else if(type=="elevatorInfo")
          {
              $("#elevatorInfoShow").hide();
              $("#elevatorInfoUpdate").show();
              $('#partyA').val(vm.upkeepContract.baseInfo.partyAId);
              $('#partyB').val(vm.upkeepContract.baseInfo.partyBId);
              initDataTables();
              getProjectName();
          }

        }
        function changeShowMenuState(type)
        {
            loadUpkeepContract(id);
            if(type=="baseInfo")
            {
                $("#baseInfoShow").show();
                $("#baseInfoUpdate").hide();
            }
            else if(type=="stateInfo")
            {
                $("#stateInfoShow").show();
                $("#stateInfoUpdate").hide();
            }
            else if(type=="billInfo")
            {
                $("#billInfoShow").show();
                $("#billInfoUpdate").hide();
            }
            else if(type=="collectInfo")
            {
                $("#collectInfoShow").show();
                $("#collectInfoUpdate").hide();
            }
            else if(type=="elevatorInfo")
            {
                $("#elevatorInfoShow").show();
                $("#elevatorInfoUpdate").hide();

            }
        }
        var getYears=function(startDate,endDate){
            var years=(endDate.getTime()-startDate.getTime());
            var years2=years/3600000/24/365;
            return Math.round(years2*Math.pow(10,1))/Math.pow(10,1);
        }
        function editBaseInfoOfUpkeepContract() {
            var beginDate=$("#beginDate").val();
            var endDate=$("#endDate").val();
            var amount=$("#amount").val();
            if(beginDate==null||beginDate==""){return toastr.error("合同生效日需要填写！");}
            if(endDate==null||endDate==""){return toastr.error("合同到期日需要填写！");}
            if(amount!=null&& !req.test(amount)){return toastr.error("请填写正确的合同金额！");}
            var beginDateNew=new Date(Date.parse(beginDate.replace(/-/g,"/")));
            var endDateNew=new Date(Date.parse(endDate.replace(/-/g,"/")));
            var years=getYears(beginDateNew,endDateNew);
            if(years<=0) {return toastr.error("合同期限(年) 太短！");}
            else{
                var baseInfo={
                    number:vm.upkeepContract.baseInfo.number,
                    property:$("#search_property").val(),
                    status:$("#search_status").val(),
                    source:$("#search_source").val(),
                    duration:years,
                    beginDate:$("#beginDate").val(),
                    endDate:$("#endDate").val(),
                    value:amount,
                    ownerFullname:$("#ownerFullname").val(),
                    ownerShortname:$("#ownerShortname").val(),
                    paymentTerm:$("#paymentTerm").val()
                }
                ajax.patch("/api/upkeepContract/editBaseInfo/" + vm.upkeepContract.baseInfo.id,baseInfo, function (res) {
                swal({
                    title: translator.translate("updatedSuccessfully")
                }, function () {
                    changeShowMenuState("baseInfo");
                });
            })}

        }
        var req =  /^\d+(?=\.{0,1}\d+$|$)/;
        function editStateInfoOfUpkeepContract() {
            var needCollectValue=vm.upkeepContract.stateInfo.needCollectValue;
            var receivableValue=vm.upkeepContract.stateInfo.receivableValue;
            if(needCollectValue !=null && !req.test(needCollectValue.toString()))
            { return toastr.error("待开票金额不正确！"); }
           if(receivableValue!=null&& !req.test(receivableValue.toString())){ return toastr.error("应收账款金额不正确！")}
            vm.upkeepContract.stateInfo.renewStatus=$("#search_renewStatus").val();
            ajax.patch("/api/upkeepContract/stateInfo/" + vm.upkeepContract.baseInfo.id, vm.upkeepContract.stateInfo, function () {
                swal({
                    title: translator.translate("updatedSuccessfully")
                }, function () {
                    changeShowMenuState("stateInfo");
                });
            })
        }
        function editBillInfoOfUpkeepContract() {
            var billValue=vm.newBill.billValue;
            var date=$("#billingDate").val();
            if(billValue!=null&& !req.test(billValue)){ return toastr.error("开票金额不正确！")}
            if(date==null){return toastr.error("开票日期不正确！");}
            var BillInfo={
                billNO:vm.newBill.billNO,
                billingDate:date,
                billValue:vm.newBill.billValue
            }
            ajax.patch("/api/upkeepContract/addBillRecord/" + vm.upkeepContract.baseInfo.id, BillInfo, function (res) {
                swal({
                    title: translator.translate("addBillingRecordSuccessfully")
                },function(){
                    changeShowMenuState("billInfo");
                });
            })
        }
        function editCollectInfoOfUpkeepContract() {
            var collectingValue=vm.newCollect.collectingValue;
            var date=$("#collectInfoDate").val();
            if(collectingValue!=null&& !req.test(collectingValue)){ return toastr.error("收款金额不正确！");}
            if(date==null){return toastr.error("收款日期不正确！");}
            var BillInfo={
                billNO:vm.newCollect.billNo,
                collectionDate:date,
                collectingValue:collectingValue
            }
            ajax.post("/api/upkeepContract/addCollectingRecord/" + vm.upkeepContract.baseInfo.id, BillInfo, function () {
                swal({
                    title: translator.translate("addedCollectionRecord")
                },function(){
                    changeShowMenuState("collectInfo");
                });
             });
        }

        function deleteUpkeepContract() {
            if(vm.upkeepContract.elevatorInfo!=null&&vm.upkeepContract.elevatorInfo.length>0)
            {
                return toastr.error("该合同已关联电梯，不能删除！");
            }
            swal({
                title: translator.translate("doYouWantToDeleteThisUpkeepContract"),
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: translator.translate("delete"),
                cancelButtonText: translator.translate("cancel"),
                closeOnConfirm: false,
                html: false
            }, function () {
                ajax.delete("/api/upkeepContract/delete/" + vm.upkeepContract.baseInfo.id, function () {
                    swal(translator.translate("deletedInfo"), translator.translate("deletedInfo"), "success");
                    window.location.href = "/upkeepContract";
                })
            });
        }
        function removeElevator(id){
            swal({
                title: translator.translate("doYouWantToRemoveUpkeepContract")+"?",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: translator.translate("SureDelete"),
                cancelButtonText: translator.translate("cancel"),
                closeOnConfirm: false,
                html: false
            }, function () {
                ajax.post("/api/upkeepContract/removeElevator/" + vm.upkeepContract.baseInfo.id,{elevatorId:id}, function () {
                    swal(translator.translate("RemoveInfo"), translator.translate("RemoveInfo"), "success");
                    changeShowMenuState("elevatorInfo");
                })
            });
        }
        function getProjectName() {
            $("#projectName").typeahead({
                source: function(query, process) {
                    var parameter = {projectName: query};
                    ajax.post("/api/upkeepContract/searchBuildingName/"+vm.upkeepContract.baseInfo.partyAId+"&"+vm.upkeepContract.baseInfo.partyBId, parameter, function (res) {
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

        function addElevator(){
            var str=""
            $.each($(":checkbox"),function(i,n){
                if(n.checked)
                 str=str+ n.value+","
            })
            swal({
                title: translator.translate("doYouWantToAddElevators")+"?",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: translator.translate("SureAdd"),
                cancelButtonText: translator.translate("cancel"),
                closeOnConfirm: false,
                html: false
            }, function () {
                ajax.post("/api/upkeepContract/addElevator/" + vm.upkeepContract.baseInfo.id,{elevatorStr:str}, function () {
                    swal(translator.translate("addedSuccessfully"), translator.translate("addedSuccessfully"), "success");
                    changeShowMenuState("elevatorInfo");
                })
            });
        }
        function initDataTablesElevators(){
            datatable.createTable("elevatorDataTable","/api/upkeepContract/containElevators/page", {
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
                    title: translator.translate("addressOfElevator")
                },{
                    field: 'projectName',
                    title: translator.translate("projectName")
                },
                    //    {
                    //    field: 'elevatorBrand.name',
                    //    title: translator.translate("elevatorBrand"),
                    //    formatter: function (value, row, index){
                    //        if(row.elevatorBrand == null) return "-";
                    //        return row.elevatorBrand.name;
                    //    }
                    //},
                    {
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

        function initDataTables(){
            datatable.createTable("elevatorDataTable","/api/upkeepContract/new/searchElevator/page", {
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
                    title: translator.translate("addressOfElevator")
                },{
                    field: 'projectName',
                    title: translator.translate("projectName")
                },
                //    {
                //    field: 'elevatorBrand.name',
                //    title: translator.translate("elevatorBrand"),
                //    formatter: function (value, row, index){
                //        if(row.elevatorBrand == null) return "-";
                //        return row.elevatorBrand.name;
                //    }
                //},
                    {
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
        function initInternal() {
            vm = new Vue({
                el: "#upkeepContractCoU",
                data: {
                    isBaseInfoEdit: false,
                    isStateInfoEdit: false,
                    isNewBill: false,
                    isNewCollect: false,
                    partyA:[],
                    partyB:[],
                    newBill: {
                        billNO: "",
                        name: "",
                        billValue: "",
                        billingDate: ""
                    },
                    newCollect: {
                        billNo: "",
                        collectingValue: "",
                        createBy:"",
                        collectionDate: ""
                    },
                    upkeepContract: {
                        baseInfo: {
                            id: "",
                            number: "",
                            property: "",
                            status: "",
                            source: "",
                            ownerFullname: "",
                            ownerShortname: "",
                            partyAId:"",
                            partyBId:"",
                            partyAName:"",
                            partyBName:"",
                            partyAContact:"",
                            partyAAddress:"",
                            partyATelephone:"",
                            partyBContact:"",
                            partyBAddress:"",
                            partyBTelephone:"",
                            beginDate:"",
                            endDate:"",
                            value: 0.0,
                            paymentTerm:"",
                            duration: 0.0,
                            remainElevator:0
                        },
                        stateInfo: {
                            remainDays: 0,
                            renewStatus: "",
                            allCollectValue: 0,
                            needCollectValue: 0,
                            receivableValue: 0,
                            allBillingValue: 0,
                            age: 0
                        },
                        billInfo: [{
                            billNO: "",
                            name: "",
                            billValue: "",
                            billingDate: ""
                        }],
                        collectInfo: [{
                            billNo: "",
                            collectingValue: "",
                            createBy:"",
                            collectionDate: ""
                        }],
                        elevatorInfo: [{
                            id:"",
                            number: "",
                            lastcheckDate: null,
                            avgValue: "",
                            elevatorType: "",
                            manufacturer: ""
                        }]

                    }
                },
                methods: {
                    baseInfoEdit: function () {
                        changeEditMenuState("baseInfo");
                    },
                    stateInfoEdit: function () {
                        changeEditMenuState("stateInfo");
                    },
                    billInfoEdit: function () {
                        changeEditMenuState("billInfo");
                    },
                    collectInfoEdit: function () {
                        changeEditMenuState("collectInfo");
                    },
                    elevatorInfoEdit:function(){

                        changeEditMenuState("elevatorInfo");
                    },
                    baseInfoConfirm: function () {
                        editBaseInfoOfUpkeepContract();
                    },
                    stateInfoConfirm: function () {
                        editStateInfoOfUpkeepContract();
                    },
                    billInfoConfirm: function () {
                        editBillInfoOfUpkeepContract();
                    },
                    collectInfoConfirm: function () {
                        editCollectInfoOfUpkeepContract();
                    },
                    baseInfoCancel: function () {
                        changeShowMenuState("baseInfo");
                    },
                    stateInfoCancel: function () {
                        changeShowMenuState("stateInfo");
                    },
                    billInfoCancel: function () {
                        changeShowMenuState("billInfo");
                    },
                    collectInfoCancel: function () {
                        changeShowMenuState("collectInfo");
                    },
                    elevatorCancel:function(){
                        changeShowMenuState("elevatorInfo");
                    },
                    deleteIt: function () {
                        deleteUpkeepContract();
                    },
                    removeElevator: function (id) {
                        removeElevator(id);
                    },
                    addElevator: function () {
                        if(vm.upkeepContract.baseInfo.partyAId==null||vm.upkeepContract.baseInfo.partyBId==null){return toastr.error("该合同甲方和乙方不能为空！");}
                        addElevator();
                    },
                    search: function () {
                        $("#elevatorDataTable").bootstrapTable("refresh");
                    }
                }
                //},
                //ready: function () {
                //    $("#billDatePicker").datetimepicker();
                //}
            });
        }

        init();

    });