define(["jquery","vue","ajax","js/util","swal","translator","js/formValidator","toastr","typeahead"], function($, Vue, ajax, util, swal, translator, validator, toastr,typeahead){
    var vm;
    var numberToId = {};
    var numberToBrandName = {};

    function init(){
        $(".menu-archive-management").addClass("active");
        $(".intel-hardware").addClass("active");
        initInternal();
        if (isEdit()){
            //load existing premise
            loadIntelHardWare(util.getUrlParameter("id"));
        }
        elevatorTypeAhead();
    }

    function isEdit(){
        return util.isUrlParameterExist("id");
    }

    function loadIntelHardWare(id){
        ajax.get("/api/collector/" + id, function (res){
            vm.collector = res.data;
        });
    }

    //typeahead处理
    function elevatorTypeAhead() {
        $("#contractNo").typeahead({
            source: function(query, process) {
                var parameter = {number: query};
                ajax.post("/api/elevator/elevatorDto/typeahead", parameter, function (res) {
                    if(res.success){
                        var array = [];
                        $.each(res.data,function(index, ele){
                            numberToId[ele.number] = ele.id;
                            numberToBrandName[ele.number] = ele.brandName;
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
                $("#contractNo").val(item);
            }
        });
    }

    function editIntelHardware(){
        ajax.patch("/api/collector/"+ vm.collector.id, function(res){
            swal({
                title: translator.translate("updatedSuccessfully")
            }, function(){
                window.location.href = "/collector";
            });
        });
    }

    function createIntelHardware(){
        var elevatorNo = $("#contractNo").val();

        if(elevatorNo!=null && elevatorNo!="") {
            vm.collector.number = elevatorNo;
            vm.collector.elevatorId = numberToId[elevatorNo];
        }
        ajax.post("/api/collector", vm.collector, function(res){
            swal({
                title: translator.translate("addedSuccessfully")
            }, function(){
                window.location.href="/collector";
            });
        });
    }

    function deleteIntelHardware(){
        swal({
            title: translator.translate("doYouWantToDeleteThisIntelHardware"),
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: translator.translate("delete"),
            cancelButtonText: translator.translate("cancel"),
            closeOnConfirm: false,
            html: false
        }, function(){
            ajax.delete("/api/collector/" + vm.collector.id, function(res) {
                if(res.success){
                    swal("删除成功","此品牌已经从系统中删除","success");
                    window.location.href="/collector";
                }else{
                    swal("删除失败");
                    window.location.href="/collector";
                }
            });
        });
    }

    function initInternal(){
        vm = new Vue({
            el: "#intelHardwareCoU",
            data: {
                isEdit: isEdit(),
                collector: {
                    id: "",
                    intelHardwareNumber: "",
                    elevatorId: "",
                    number: ""
                }
            },
            methods: {
                confirm: function (){
                    if(isEdit()){
                        editIntelHardware();
                    }else{
                        createIntelHardware();
                    }
                },
                deleteIt: function(){
                    deleteIntelHardware();
                }
            }
        });
    }

    init();
});
