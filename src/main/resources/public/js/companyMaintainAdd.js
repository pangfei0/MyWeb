define(["jquery", "vue", "ajax", "js/util", "swal", "translator","toastr", "js/formValidator"], function ($, Vue, ajax, util, swal, translator,toastr, validator) {
    var vm;

    function init() {
        $(".companyMaintain").addClass("active");
        initInternal();
    }
    var req = /^1[3|4|5|7|8]\d{9}$/;//判断手机号是否正确
    function createCompany() {
        var name=$("#companyName").val();
        var type=$("#companyType").val();
        var phone=$("#companyPhone").val();
        var mobile=$("#mobile").val();

        if (name == null || name == "") {
            toastr.error("公司名称不能为空!");
            return false;
        }
        if (type == "-1") {
          toastr.error("类型不能为空!");
            return false;
        }
        if(mobile!=null&&mobile!=""&&!req.test(mobile))
        {
            toastr.error("负责人电话不正确!");
            return false;
        }
            $.post("/api/companyMaintain/add", vm.company, function (res) {
                if(res.success) {
                    swal({
                        title: translator.translate("addedSuccessfully")
                    }, function () {
                        window.location.href = "/companyMaintain";
                    });
                }
            });

    }

    function initInternal() {
        vm = new Vue({
            el: '#companyMaintainAdd',
            data: {
                company: {
                    id: "",
                    name: "",
                    address: "",
                    phone: "",
                    contact: "",
                    mobile: "",
                    type: ""
                }
            },
            methods: {
                confirm: function () {
                    createCompany();
                }
            }
        });
    }

    init();
});
