define(["jquery", "vue", "ajax", "js/datatable", "js/util", "swal", "translator"],
    function ($, Vue, ajax, datatable, util, swal, translator) {
        var bootstrapTable;

        function init() {
            $(".role").addClass("active");
            createBootstrapTable();

            vm = new Vue({
                el: ".search-form",
                methods: {
                    search: function () {
                        $("#dataTable").bootstrapTable("refresh");
                    }
                }
            });
        }

        function createBootstrapTable() {
            bootstrapTable = datatable.createTable("dataTable", "/api/permission/search/page/role", {
                columns: [{
                    field: 'name',
                    title: translator.translate("roleName"),
                    sortable: true
                }, {
                    field: 'operation',
                    title: translator.translate("operation"),
                    formatter: function (value, row, index) {
                        if(row.name!="超级管理员"){
                            return util.loadPageTemplate("table-row-template", row);
                        }

                    }
                }]
            });
        }

        function editUser(id) {
            window.location.href = "/user/role/cou?id=" + id;
        }

        function forbidRole(id) {
            swal({
                title: translator.translate("doYouWantToForbidRole") + "?",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: translator.translate("forbid"),
                cancelButtonText: translator.translate("cancel"),
                closeOnConfirm: false,
                html: false
            }, function () {
                ajax.delete("/api/permission/forbid/" + id, function () {
                    swal(translator.translate("forbidInfo"), translator.translate("forbidInfo"), "success");
                    $("#dataTable").bootstrapTable('refresh');
                });
            });
        }

        window.editUser = editUser;
        window.forbidRole = forbidRole;
        init();
    }
)
;
function setRoleStatus(){
    var mapHeight = $("#search_roleStatus").val();
    $("#roleStatus").val(mapHeight);
}