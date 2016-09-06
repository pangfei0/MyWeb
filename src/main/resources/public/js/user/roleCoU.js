define(["jquery", "vue", "ajax", "js/util", "swal", "translator", "select"], function ($, Vue, ajax, util, swal, translator, select) {
        var vm;

        function init() {
            $(".role").addClass("active");
            initVue();
            loadAllAvailablePermissions();
            if (isEdit()) {
                loadRole(util.getUrlParameter("id"));
            }
        }

        function loadAllAvailablePermissions() {
            ajax.get("/api/permission/all/flat", function (res) {
                vm.availablePermissions = res.data;
            });
        }

        function isEdit() {
            return util.isUrlParameterExist("id");
        }

        function loadRole(id) {
            ajax.get("/api/permission/role/" + id, function (res) {
                vm.role = res.data;
            });
        }

        function edit() {
            ajax.patch("/api/permission/role/" + vm.role.id, vm.role, function (res) {
                swal({
                    title: translator.translate("updatedSuccessfully")
                }, function () {
                    window.location.href = "/rolePermission/view";
                });
            });
        }

        function create() {
            $.ajax({
                type:"post",
                dataType:"json",
                traditional:true,
                data:{
                    name:vm.role.name,
                    companyType:vm.role.companyType,
                    permissions:JSON.stringify(vm.role.permissions)
                },
                url:"/api/permission/role",
                success:function (res) {
                    swal({
                        title: translator.translate("addedSuccessfully")
                    }, function () {
                        window.location.href = "/rolePermission/view";
                    });
                }
            });
            //var companyType=$("#companyType").val();
            //ajax.post("/api/permission/role", vm.role, {comType:companyType},function (res) {
            //    swal({
            //        title: translator.translate("addedSuccessfully")
            //    }, function () {
            //        window.location.href = "/rolePermission/view";
            //    });
            //});
        }

        function formatTreeItem(item) {
            var levelSpace = "";
            for (var i = 0; i < item.level; i++) {
                levelSpace = levelSpace + "&nbsp;&nbsp;&nbsp;"
            }
            return levelSpace + "<i class='fa fa-caret-right'></i>&nbsp;&nbsp;&nbsp;&nbsp;" + item.name;
        }

        function initVue() {
            Vue.filter('treeItem', function (value) {
                if (!value) return "";
                return formatTreeItem(value);
            });

            vm = new Vue({
                el: "#userCoU",
                data: {
                    isEdit: isEdit(),
                    availablePermissions: [],

                    role: {
                        id: "",
                        name: "",
                        companyType:"",
                        permissions: []
                    }
                },
                methods: {
                    confirm: function () {
                        if (isEdit()) {
                            edit();
                        }
                        else {
                            create();
                        }
                    }
                }
            });
        }

        init();
    }
);