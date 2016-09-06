<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="IE=edge">
    <title>巨立电梯物联网系统 | 登录</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="/lib/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/lib/toastr/toastr.min.css">
    <link rel="stylesheet" href="/lib/font-awesome/css/font-awesome.min.css">

    <link rel="stylesheet" href="/css/login.css">
</head>
<body>
<div class="outer">
    <div class="middle">
        <div class="inner">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <span class="fa fa-lg fa-lock"></span> <span>巨立电梯物联网系统</span>
                </div>
                <div class="panel-body">
                    <form class="form-horizontal" role="form" id="login-form">
                        <div class="form-group">
                            <label for="username" class="col-sm-3 control-label">用户名</label>

                            <div class="col-sm-9">
                                <input type="text" v-model="userName" class="form-control" id="username" autofocus
                                       required/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="password" class="col-sm-3 control-label">密码</label>

                            <div class="col-sm-9">
                                <input type="password" v-model="password" class="form-control" id="password" required/>
                            </div>
                        </div>
                        <div class="form-group last">
                            <div class="col-sm-offset-3 col-sm-9">
                                <button type="submit" id="btn-login" v-disabled="submitting" class="btn btn-success"
                                        v-on:click="login">
                                    <i v-show="submitting" class="fa fa-refresh fa-spin"></i> 登录
                                </button>
                                <button type="button" id="btn-register" class="btn btn-success"
                                        v-on:click="register">注册
                                </button>
                                <button type="button" id="btn-welcome" class="btn btn-success"
                                        v-on:click="welcome">APP下载
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>

        </div>
    </div>
</div>
<script type="text/javascript">
    var require = {
        baseUrl: '/',
        paths: {
            app: "js",
            lib: "lib",
            jquery: "lib/jquery/jQuery-2.1.4.min",
            vue: "lib/vuejs/vue",
            toastr: "lib/toastr/toastr.min"
        }
    };
</script>
<script data-main="/js/login.js" src="/lib/requirejs/requirejs-2.1.20.js"></script>
</body>
</html>