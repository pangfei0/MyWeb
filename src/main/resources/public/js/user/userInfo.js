define(["jquery", "vue", "ajax"], function ($, Vue, ajax) {
    var vm;

    $(".user-info").addClass("active");

    function showData() {
        vm = new Vue({
            el: "#content",
            data: {
                user: {
                    userName: " ",
                    nick: " ",
                    telephone: "",
                    roleName: " "
                }
            },
            methods: {}
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