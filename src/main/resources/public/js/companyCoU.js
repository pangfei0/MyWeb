define(["jquery", "vue", "ajax", "js/util", "swal", "translator","js/formValidator"], function ($, Vue, ajax, util, swal, translator, validator) {
    var vm;
    var type = $("#type").val();

    function init(){
        initInternal();
        if(type == 30){
            $(".use-company").addClass("active");
        }
        else if(type == 20){
            $(".maintainer-company").addClass("active");
        }
        if(isEdit()){
            loadCompany(util.getUrlParameter("id"));
        }
        validator.formValidator("companyCoU");
    }


    function isEdit(){
        return util.isUrlParameterExist("id");
    }

    function loadCompany(id){
        ajax.get("/api/company/" + id, function (res){
            vm.company = res.data;
        });
    }

    function editCompany(){
        ajax.patch("/api/company/" + vm.company.id, vm.company, function(res){
            swal({
                title: translator.translate("updatedSuccessfully")
            },function(){
                window.location.href = "/company/" + type;
            });
        });
    }

    function addCompany(){
        ajax.post("/api/company", vm.company, function() {
            if(vm.company.name==null||vm.company.name==""){
                swal(translator.translate("addedFailed"),translator.translate("addedFailed"),"Failed");
            }else{
            swal({
                title: translator.translate("addedSuccessfully")
            },function(){
                window.location.href = "/company/" + type;
            });}
        });

    }

    function deleteCompany(){
        swal({
            title: translator.translate("doYouWantToDeleteThisCompany"),
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: translator.translate("delete"),
            cancelButtonText: translator.translate("cancel"),
            closeOnConfirm: false,
            html: false
        }, function(){
            ajax.delete("/api/company/" + vm.company.id, function (data) {
                if (data.success){
                    swal("删除成功","该单位已经从系统中删除","success");
                    window.location.href="/company/" + type;
                } else {
                    swal("删除失败","该单位已经关联电梯","OK");
                    window.location.href="/company/" + type;
                }
            });
        });
    }

    function initInternal(){
        vm = new Vue({
            el: '#companyCoU',
            data: {
                isEdit: isEdit(),
                company: {
                    id: "",
                    name: "",
                    address: "",
                    phone: "",
                    contact: "",
                    mobile: "",
                    type: type
                }
            },
            methods: {
                confirm: function(){
                    if(isEdit()){
                        editCompany();
                    }else{
                        addCompany();
                    }
                },
                deleteIt: function(){
                    deleteCompany();
                }
            }
        });
    }
    init();
});
