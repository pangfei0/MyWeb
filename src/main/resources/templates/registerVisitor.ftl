<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="IE=edge">
    <title>巨立电梯物联网系统 | 注册</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="/lib/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/lib/toastr/toastr.min.css">
    <link rel="stylesheet" href="/lib/font-awesome/css/font-awesome.min.css">

    <link rel="stylesheet" href="/css/login.css">
    <script type="text/javascript">
        var require = {
            baseUrl: '/',
            paths: {
                app: "js",
                lib: "lib",
                jquery: "lib/jquery/jQuery-2.1.4.min",
                vue: "lib/vuejs/vue",
                ajax: "js/ajax",
                swal: "lib/sweetalert/sweetalert"
            }
        };
    </script>
    <script data-main="/js/registerVisitor.js" src="/lib/requirejs/requirejs-2.1.20.js"></script>
</head>
<body>
<div id="registerVisitor">
    <div class="middle">
        <div class="inner">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <span class="fa fa-lg fa-lock"></span><span>巨立电梯物联网系统</span>注册
                </div>
                <div class="panel-body">
                    <form class="form-horizontal" role="form" id="register-form" >

                        <div class="form-group">
                            <label for="userName" class="col-sm-3 control-label">用户名</label>
                            <div class="col-sm-9">
                                <input type="text" v-model="userName" class="form-control" id="userName" onblur="checkUserName(this.value)"/>（可包含 a-z、0-9 和下划线）<span id="userNameSpan" style="color:red">*</span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="password" class="col-sm-3 control-label">密码</label>
                            <div class="col-sm-9">
                                <input type="password" v-model="password" class="form-control" id="password" onblur="checkPassword(this.value)"/>（至少包含 6 个字符）<span id="passwordSpan" style="color:red">*</span>
                            </div>
                            <div style="color:red">*</div>
                        </div>
                        <div class="form-group">
                            <label for="passwordAgain" class="col-sm-3 control-label">再次输入密码</label>
                            <div class="col-sm-9">
                                <input type="password" v-model="passwordAgain" class="form-control" id="passwordAgain" onblur="checkPasswordAgain(this.value)"/><span id="passwordAgainSpan" style="color:red">*</span>
                            </div>
                            <div style="color:red">*</div>
                        </div>
                        <div class="form-group">
                            <label for="nick" class="col-sm-3 control-label">昵称</label>
                            <div class="col-sm-9">
                                <input type="text" v-model="nick" class="form-control" id="nick"/><span id="nickSpan" style="color:red">*</span>
                            </div>
                            <div style="color:red">*</div>
                        </div>
                        <div class="form-group">
                            <label for="email" class="col-sm-3 control-label">邮箱</label>
                            <div class="col-sm-9">
                                <input type="text" v-model="email" class="form-control" id="email" onblur="checkEmail(this.value)"/>（必须包含 @  和.字符）<span id="emailSpan" style="color:red">*</span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="telephone" class="col-sm-3 control-label">手机号码</label>
                            <div class="col-sm-9">
                                <input type="text" v-model="telephone" class="form-control" id="telephone" onblur="checkTelephone(this.value)"/><span id="telephoneSpan" style="color:red">*</span>
                            </div>
                        </div>
                        <div class="form-group" hidden="true">
                            <label class="col-sm-3 control-label">角色</label>
                            <div class="col-sm-9">
                                <label id="roleId" class="col-sm-3 control-label">游客</label>
                            </div>
                        </div>
                        <div class="form-group last">
                            <div class="col-sm-offset-3 col-sm-9">
                                <button type="button" id="btn-register" class="btn btn-success" v-on:click="confirm">
                                    确定
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>