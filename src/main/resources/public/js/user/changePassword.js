define(["jquery", "vue", "ajax", "translator", "toastr"], function ($, Vue, ajax, translator, toastr) {
    function init() {
        $(".change-password").addClass("active");
        var vm;

        vm = new Vue({
            el: "#change-password",
            data: {
                d: {
                    oldPassword: "",
                    newPassword: ""
                }
            },
            methods: {
                confirm: function () {
                    ajax.post("/api/user/changePassword", vm.d, function () {
                        toastr.success(translator.translate("updatedSuccessfully"));
                    });
                }
            }
        });
    }

    init();
});
