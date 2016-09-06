define(["jquery", "vue", "ajax", "js/util", "swal", "translator", "select"], function ($, Vue, ajax, util, swal, translator, select) {
        var vm;

        function init() {
            $(".organization").addClass("active");
            initVue();
            initBootstrapSelect();
            loadOrganizations();
            if (isEdit()) {
                //load existing
                load(util.getUrlParameter("id"));
            }
        }

    function initBootstrapSelect() {
        $('.selectpicker').selectpicker({
            iconBase: 'fa'
        });
    }

    function loadOrganizations() {
        ajax.get("/api/organization/tree/flat", function (res) {
            vm.organizations = res.data;
            //在Vue下次更新dom的时候，更新下拉框
            Vue.nextTick(function () {
                $(".selectpicker").selectpicker("val", vm.organization.parent);
                $(".selectpicker").selectpicker("refresh");
            });
        });
    }

        function isEdit() {
            return util.isUrlParameterExist("id");
        }

        function load(id) {
            ajax.get("/api/organization/" + id, function (res) {
                vm.organization = res.data;
            });
        }

        function edit() {
            ajax.patch("/api/organization/" + vm.organization.id, vm.organization, function (res) {
                swal({
                    title: translator.translate("updatedSuccessfully")
                }, function () {
                    window.location.href = "/organization/view";
                });
            });
        }

        function create() {
            ajax.post("/api/organization", vm.organization, function (res) {
                swal({
                    title: translator.translate("addedSuccessfully")
                }, function () {
                    window.location.href = "/organization/view";
                });
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

            var parent = util.getUrlParameter("parent") || "";
            vm = new Vue({
                el: "#organizationCoU",
                data: {
                    isEdit: isEdit(),
                    organizations: [],
                    organization: {
                        id: "",
                        name: "",
                        parent: parent,
                        orderIndex: 1
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