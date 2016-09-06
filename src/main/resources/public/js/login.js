define(["jquery", "vue", "app/ajax", "toastr"], function ($, Vue, ajax, toastr) {

    Vue.directive('disabled', function (value) {
        this.el.disabled = !!value
    });

    new Vue({
        el: '#login-form',
        data: {
            submitting: false,
            userName: "",
            password: ""
        },
        methods: {
            login: function (e) {
                var self = this;
                e.preventDefault();

                var isSuccess = false;
                self.submitting = true;
                $.post("/api/user/login", {
                    userName: self.userName,
                    password: self.password
                }, function (res) {
                    if (res.success) {
                        isSuccess = true;
                        window.location.href = res.data.redirectUrl;
                    }
                    else {
                        self.submitting = false;
                        toastr.error(res.description);
                    }
                });
            },
            register: function (e) {
                e.preventDefault();
                window.location.href = "/registerVisitor";
            },
            welcome: function (e) {
                e.preventDefault();
                window.location.href = "/welcome";
            }
        }
    });
});