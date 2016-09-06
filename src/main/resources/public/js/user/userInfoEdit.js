define(["jquery", "vue", "ajax", "translator", "toastr"], function ($, Vue, ajax, translator, toastr) {
    var vm;

    $(".user-info").addClass("active");

    function showData() {
        vm = new Vue({
            el: "#content",
            data: {
                user: {
                    email: " ",
                    nick: " ",
                    telephone: ""
                }
            },
            methods: {
                save: function () {
                    ajax.patch("/api/user/" + vm.user.id + "/selfupdate", vm.user, function () {
                        toastr.success(translator.translate("updatedSuccessfully"));
                    });
                }
            }
        });
        loadData();
    }

    function loadData() {
        ajax.get("/api/user/currentUser", function (res) {
                vm.user = res.data;
            }
        );
    }

    showData();
});