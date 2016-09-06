define(["jquery", "vue", "app/ajax", "toastr", "translator"], function ($, Vue, ajax, toastr, translator) {

    var vm;
    var pass;
    //var userNameObj = $("#userName").val();
    //var passwordObj = $("#password").val();
    //var passwordAgainObj = $("#passwordAgain").val();
    //var nickObj = $("nick").val();
   // var emailObj = $("email").val();
    //var telephoneObj = $("telephone").val();
    var msg ="&nbsp;&nbsp;&nbsp;<img src='../../img/ok.png' style='width:12px'>";
    //userNameObj.onblur = checkUserName;
    //passwordObj.onblur = checkPassword;
    //passwordAgainObj.onblur = checkPasswordAgain;
    //emailObj.onblur=checkEmail;
    //telephoneObj.onblur=checkTelephone;

    function init() {
        $(".registerVisitor").addClass("active");
        window.checkUserName = checkUserName;
        window.checkPassword=checkPassword;
        window.checkPasswordAgain=checkPasswordAgain;
        window.checkEmail=checkEmail;
        window.checkTelephone=checkTelephone;
        initInternal();
    }
    function checkUserName(userName) {
        //var userNameValue = (userNameObj.value).trim();
        ///^[a-zA-Z0-9_]\w{0,9}$/;
        var usernameRegex = /^[a-zA-Z0-9_]\w{0,9}$/;
        if (userName == null || userName == "")
        {
            msg = "<label>用户名必须填写!</label>";
            pass=false;
        }
        else if (!usernameRegex.test(userName))
        {
            msg = "<label>用户名格式不正确</label>";
            pass=false;
        }else{
            pass=true;
        }
        var span = document.getElementById("userNameSpan");
        span.innerHTML = msg;
        return msg == "&nbsp;&nbsp;&nbsp;<img src='../../img/ok.png' style='width:12px'>";
    }

    function checkPassword(password) {
       // var passwordValue = passwordObj.value;
        var passwordRegex = /^\w{6,}$/;
        //msg ="&nbsp;&nbsp;&nbsp;<img src='../../img/ok.png' style='width:12px'>"; //不懂?
        if (!password) {
            msg = "<label>密码必须填写!</label>";
            pass=false;
        }
        else if (!passwordRegex.test(password)) {
            msg = "<label>密码至少为6个字符</label>";
            pass=false;
        }else{
            pass=true;
        }
        var span = document.getElementById("passwordSpan");
        span.innerHTML = msg;
        return msg == "&nbsp;&nbsp;&nbsp;<img src='../../img/ok.png' style='width:12px'>";
    }

    function checkPasswordAgain(passwordAgain) {
       // var confirmValue = passwordAgainObj.value;
        var passwordValue =$("#password").val();
        if (!passwordAgain)
        {
            msg = "<label>确认密码必须填写!</label>";
            pass=false;
        }
        else if (passwordAgain != passwordValue)
        {
            msg = "<label>密码必须一致!</label>";
            pass=false;
        }else{
            pass=true;
        }
        var span = document.getElementById("passwordAgainSpan");
        span.innerHTML = msg;
        return msg == "&nbsp;&nbsp;&nbsp;<img src='../../img/ok.png' style='width:12px'>";
    }

    function checkEmail(email){
        //var emailObjValue = emailObj.value;
        // /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]{2,}(\.[a-z0-9]{2,5}){1,2}$/,
        var emailRegex = /^[\w-]+@([\w-]+\.)+[a-zA-Z]{2,3}$/;
        if(!email)
        {
            msg = "<label>Email必须填写!</label>";
            pass=false;
        }
        else if(!emailRegex.test(email))
        {
            msg = "<label>Email格式不正确!</label>";
            pass=false;
        }else{
            pass=true;
        }
        var span = document.getElementById("emailSpan");
        span.innerHTML = msg;
        return msg == "&nbsp;&nbsp;&nbsp;<img src='../../img/ok.png' style='width:12px'>";
    }

    function checkTelephone(telephone){
        //var telephoneObjValue = telephoneObj.value;
        //"^(\\d{3,4}-?\\d{7,8}|(13|15|18)\\d{9})$"
        ///^1[358]\d{9}$/
        var telephoneRegex = /^1[358]\d{9}$/;
        if(!telephone)
        {
            msg = "<label>手机号码必须填写!</label>";
            pass=false;
        }
        else if(!telephoneRegex.test(telephone))
        {
            msg = "<label>手机号码格式不正确!</label>";
            pass=false;
        }
        else{
            pass=true;
        }
        var span = document.getElementById("telephoneSpan");
        span.innerHTML = msg;
        return msg == "&nbsp;&nbsp;&nbsp;<img src='../../img/ok.png' style='width:12px'>";
    }

    function addUser() {
        ajax.post("/api/user", vm.user, function () {
            swal({
                title: "注册成功，请重新登录"
            }, function () {
                window.location.href = "/login";
            });
        });
    }

    function initInternal() {
        vm = new Vue({
            el: '#register-form',
            user: {
                roleName: "",
                userName: "",
                password: "",
                passwordAgain: "",
                nick: "",
                email: "",
                telephone: ""
            },
            methods: {
                confirm: function () {
                    if(pass)
                    {
                        addUser();
                    }
                }
            }
        });
    }

    init();
});