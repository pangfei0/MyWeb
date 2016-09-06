define(["jquery", "vue", "ajax", "js/datatable", "js/util", "swal", "translator", "datetimepicker","toastr","typeahead"],
    function ($, Vue, ajax, datatable, util, swal, translator, datetimepicker,toastr,typeahead)
    {  var vm;
        var nameToId = {};

        function init() {
            $(".report-board").addClass("active");
            $(".upkeep-contract").addClass("active");
            initVue();
            getPartyA();
            getPartyB();
            $("#endDate").datetimepicker({format: 'YYYY-MM-DD',locale:translator.translate("language")});
            $("#beginDate").datetimepicker({format: 'YYYY-MM-DD',locale:translator.translate("language")});
        }
        //获取甲方数据,自动匹配
        function getPartyA()
        {
            $("#partyAId").typeahead({
                source: function(query, process) {
                    var parameter = {partyAName: query};
                    ajax.post("/api/upkeepContract/searchPartyA", parameter, function (res) {
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
                    $("#partyAId").val(item);
                }
            });
        }
        //获取乙方数据
        function getPartyB()
        {
            $("#partyBId").typeahead({
                source: function(query, process) {
                    var parameter = {partyBName: query};
                    ajax.post("/api/upkeepContract/searchPartyB", parameter, function (res) {
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
                    $("#partyBId").val(item);
                }
            });
        }
        var getYears=function(startDate,endDate){
            var years=(endDate.getTime()-startDate.getTime());
            var years2=years/3600000/24/365;
            return Math.round(years2*Math.pow(10,1))/Math.pow(10,1);
        }
        var req =  /^\d+(?=\.{0,1}\d+$|$)/;
        //新建合同
        function  add()
        {
            var num=$("#number").val();
            var aid=$("#partyAId").val();
            var bid=$("#partyBId").val();
            var partyAId=nameToId[aid];
            var partyBId=nameToId[bid];
            var beginDate=$("#beginDate").val();
            var endDate=$("#endDate").val();
            var amount=$("#value").val();
            if(num==null||num==""){return toastr.error("保养合同编号需要填写！");}
            else
            {
                $.post("/api/upkeepContract/noSearch",{num:$("#number").val()}, function (r) {
                    if(r.success){//判断合同号是否已存在
                        return  toastr.error("合同号已存在");
                    }else{
                        if(partyAId==null||partyAId==""){return toastr.error("甲方需要填写！");}
                        if(partyBId==null||partyBId==""){return toastr.error("乙方需要填写！");}
                        if(beginDate==null||beginDate==""){return toastr.error("合同生效日需要填写！");}
                        if(endDate==null||endDate==""){return toastr.error("合同到期日需要填写！");}
                        if(amount!=null&&!req.test(amount)){return toastr.error("合同金额不正确！");}
                        var beginDateNew=new Date(Date.parse(beginDate.replace(/-/g,"/")));
                        var endDateNew=new Date(Date.parse(endDate.replace(/-/g,"/")));
                        var years=getYears(beginDateNew,endDateNew);
                        if(years<=0) {return toastr.error("合同期限(年) 太短！");}
                        else {
                            vm.upkeepContract.number=num;
                            vm.upkeepContract.partyAId = partyAId;
                            vm.upkeepContract.partyBId = partyBId;
                            vm.upkeepContract.property = $("#search_property").val();
                            vm.upkeepContract.status = $("#search_status").val();
                            vm.upkeepContract.source = $("#search_source").val();
                            vm.upkeepContract.duration = years;
                            $.post("/api/upkeepContract/add", vm.upkeepContract, function (r) {
                                if (r.success) {
                                    swal({
                                        title: translator.translate("addedSuccessfully")
                                    }, function () {
                                        window.location.href = "/upkeepContract";
                                    });
                                }

                            });
                        }
                    }
                });
            }

        }
        function initVue() {
            vm = new Vue({
                el: "#upkeepContractAdd",
                data:{
                    upkeepContract:{
                        number:"",
                        property:"",
                        status:"",
                        source:"",
                        ownerFullname:"",
                        ownerShortname:"",
                        partyAId:"",
                        partyBId:"",
                        beginDate:"",
                        endDate:"",
                        value:"",
                        paymentTerm:"",
                        duration:""
                    }
                },
                methods: {

                    back:function()//(取消操作)
                    {
                        window.location.href = "/upkeepContract";
                    },
                    add:function()//新建
                    {
                        add();
                    }

                }
            });
        }

        init();
    }
);