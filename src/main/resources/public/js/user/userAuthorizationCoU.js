define(["jquery", "vue", "ajax", "js/util", "swal", "translator", "select","toastr"], function ($, Vue, ajax, util, swal, translator, select,toastr) {
        var vm;

        function init() {
            $(".user-authorization").addClass("active");
            initVue();
            window.searchCompany=searchCompany;
            loadOrganizations();
            if (isEdit()) {
                //load existing user
                loadUser(util.getUrlParameter("id"));
            }
        }

    function loadOrganizations() {
        ajax.get("/api/organization/tree/flat", function (res) {
            vm.organizations = res.data;
            updateUserCurrentOrganization();
        });
    }
   function searchCompany(){
    var companyType=$("#search_companyType").val();
       if(companyType!="-1"){
           ajax.post("/api/user/searchCompany/"+companyType,{},function(res){
               if (res.success) {
                   $('#companyId').html('<option selected value="-1">--</option>');
                   $.each(res.data, function (index, data) {
                       var opStr = '<option value="' + data.id + '">' + data.name + '</option>';
                       $('#companyId').append(opStr);
                   });
               }
           })
       }

   }
    function updateUserCurrentOrganization() {
        //在Vue下次更新dom的时候，更新下拉框
        Vue.nextTick(function () {
            if (vm.user.organization) {
                $(".selectpicker").selectpicker("val", vm.user.organization);
            }
            $(".selectpicker").selectpicker("refresh");
        });
    }

        function isEdit() {
            return util.isUrlParameterExist("id");
        }

        function loadUser(id) {
            ajax.get("/api/user/" + id, function (res) {
                vm.user = res.data;
                updateUserCurrentOrganization();
            });
        }

        function editUser() {
            vm.user.companyType=$("#search_companyType").val();
            vm.user.companyId=$("#companyId").val();
            ajax.patch("/api/user/" + vm.user.id, vm.user, function (res) {
                swal({
                    title: translator.translate("updatedSuccessfully")
                }, function () {
                    window.location.href = "/user/authorization";
                });
            });
        }

        function createUser() {
            if(vm.user.roleId=="-1")
            {
                toastr.error("请选择角色！");
            }else{
                ajax.post("/api/user", vm.user, function (res) {
                    swal({
                        title: translator.translate("addedSuccessfully")
                    }, function () {
                        window.location.href = "/user/authorization";
                    });
                });
            }
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
                    organizations: [],
                    user: {
                        id: "",
                        userName: "",
                        password: "",
                        nick: "",
                        roleId: "",
                        companyId:"",
                        companyType:"",
                        organization: ""
                    }
                },
                methods: {
                    confirm: function () {
                        if (isEdit()) {
                            editUser();
                        }
                        else {
                            createUser();
                        }
                    }
                }
            });
        }

        init();
    }
);