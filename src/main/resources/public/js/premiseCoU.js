define(["jquery", "vue", "ajax", "js/util", "swal", "translator", "js/formValidator"], function ($, Vue, ajax, util, swal, translator, validator) {
    var vm;

    function init(){
        $(".premise").addClass("active");
        initInternal();
        if (isEdit()){
            //load existing premise
            loadPremise(util.getUrlParameter("id"));
        }
        validator.formValidator("premiseCoU");
    }

    function isEdit(){
        return util.isUrlParameterExist("id");
    }

    function loadPremise(id){
        ajax.get("/api/premise/" + id, function (res){
            vm.premise = res.data;
        })
    }

    function editPremise(){
        ajax.patch("/api/premise/" + vm.premise.id, vm.premise, function(res){
            swal({
                title: translator.translate("updatedSuccessfully")
            }, function(){
                window.location.href = "/premise";
            });
        });
    }

    function createPremise(){
        ajax.post("/api/premise", vm.premise, function (res) {
            swal({
                title: translator.translate("addedSuccessfully")
            }, function(){
                //var premiseId = res.data.id;
                window.location.href="/premise";
            });
        });
    }

    function deletePremise(){
        swal({
            title: translator.translate("doYouWantToDeleteThisPremise"),
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: translator.translate("delete"),
            cancelButtonText: translator.translate("cancel"),
            closeOnConfirm: false,
            html: false
        }, function(){
            ajax.delete("/api/premise/" + vm.premise.id, function () {
                swal("删除成功","此楼盘已经从系统中删除","success");
                window.location.href="/premise";
            })
        });
    }


    function initInternal(){
        vm = new Vue({
            el: "#premiseCoU",
            data: {
                isEdit: isEdit(),
                premise: {
                    id: "",
                    name: "",
                    address: "",
                    maintainer: ""
                }
            },
            methods: {
                confirm: function(){
                    if(isEdit()){
                        editPremise();
                    }
                    else{
                        createPremise();
                    }
                },
                deleteIt: function(){
                    deletePremise();
                }
            }
        });
    }

    init();
});