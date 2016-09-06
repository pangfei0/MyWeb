define(["jquery", "vue", "ajax", "js/util", "swal", "translator", "select"], function ($, Vue, ajax, util, swal, translator, select) {
        var vm;

        function init() {
            $(".unitRole").addClass("active");
            initVue();
            loadAllAvailablePermissions();
            if (isEdit()) {
                loadRole(util.getUrlParameter("roleId"));
            }
        }

        function loadAllAvailablePermissions() {
            ajax.get("/api/unitRole/all/flat", function (res) {
                vm.availablePermissions = res.data;
            });
        }

        function isEdit() {
            return util.isUrlParameterExist("roleId");
        }

        function loadRole(roleId) {
            ajax.get("/api/unitRole/get/" + roleId, function (res) {
                vm.unitRole = res.data;
            });
        }

        function edit() {
            ajax.patch("/api/unitRole/update/" + vm.unitRole.roleId, vm.unitRole, function (res) {
                swal({
                    title: translator.translate("updatedSuccessfully")
                }, function () {
                    window.location.href = "/user/unitRole";
                });
            });
        }

        function create() {

             $.ajax({
                     type:"post",
                     dataType:"json",traditional:true,
                     data:{ unitType:vm.unitRole.unitType,
                         roleName:vm.unitRole.roleName,
                         roleId:vm.unitRole.roleId,
                         parentId:vm.unitRole.parentId,
                         permissions:JSON.stringify(vm.unitRole.permissions)
                     },
                     url:"/api/unitRole",
                     success:function (res) {
                         swal({
                             title: translator.translate("addedSuccessfully")
                         }, function () {
                             window.location.href = "/user/unitRole";
                         });
                     }
            });
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
                el: "#unitRoleCoU",
                data: {
                    isEdit: isEdit(),
                    availablePermissions: [],
                    unitRole: {
                        id:"",
                        unitType:"",
                        parentId:"",
                        roleName:"",
                        roleId:"",
                        permissions:[]
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