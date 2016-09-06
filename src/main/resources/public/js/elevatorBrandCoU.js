define(["jquery", "vue", "ajax", "js/util", "swal", "translator", "js/formValidator"], function ($, Vue, ajax, util, swal, translator, validator) {
    var vm;

    function init(){
        $(".elevator-brand").addClass("active");
        initInternal();
        if (isEdit()){
            //load existing premise
            loadElevatorBrand(util.getUrlParameter("id"));
        }
        validator.formValidator("elevatorBrandCoU");
    }

    function isEdit(){
        return util.isUrlParameterExist("id");
    }

    function loadElevatorBrand(id){
        ajax.get("/api/elevatorBrand/" + id, function (res){
            vm.elevatorBrand = res.data;
        })
    }

    function editElevatorBrand(){
        ajax.patch("/api/elevatorBrand/" + vm.elevatorBrand.id, vm.elevatorBrand, function(res){
            swal({
                title: translator.translate("updatedSuccessfully")
            }, function(){
                window.location.href = "/elevatorBrand";
            });
        });
    }

    function createElevatorBrand(){
        ajax.post("/api/elevatorBrand", vm.elevatorBrand, function (res) {
            swal({
                title: translator.translate("addedSuccessfully")
            }, function(){
                //var premiseId = res.data.id;
                window.location.href="/elevatorBrand";
            });
        });
    }

    function deleteElevatorBrand(){
        swal({
            title: translator.translate("doYouWantToDeleteThisElevatorBrand"),
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: translator.translate("delete"),
            cancelButtonText: translator.translate("cancel"),
            closeOnConfirm: false,
            html: false
        }, function(){
            ajax.delete("/api/elevatorBrand/" + vm.elevatorBrand.id, function () {
                swal("删除成功","此品牌已经从系统中删除","success");
                window.location.href="/elevatorBrand";
            })
        });
    }


    function initInternal(){
        vm = new Vue({
            el: "#elevatorBrandCoU",
            data: {
                isEdit: isEdit(),
                elevatorBrand: {
                    id: "",
                    number: "",
                    name: "",
                    protocolType: ""
                }
            },
            methods: {
                confirm: function(){
                    if(isEdit()){
                        editElevatorBrand();
                    }
                    else{
                        createElevatorBrand();
                    }
                },
                deleteIt: function(){
                    deleteElevatorBrand();
                }
            }
        });
    }

    init();
});