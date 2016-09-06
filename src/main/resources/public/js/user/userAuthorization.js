define(["jquery", "vue", "ajax", "js/datatable", "js/util", "swal", "translator"],
    function ($, Vue, ajax, datatable, util, swal, translator) {
        var bootstrapTable;

        function init() {
            $(".user-authorization").addClass("active");
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
            bootstrapTable = datatable.createTable("dataTable", "/api/user/search/page", {
                columns: [{
                    field: 'userName',
                    title: translator.translate("userName"),
                    sortable: true
                }, {
                    field: 'nick',
                    title: translator.translate("staffName"),
                    sortable: true
                }, {
                    field: 'organization',
                    title: translator.translate("department"),
                    formatter: function (value, row, index) {
                        if (row.organization) {
                            return row.organization.name;
                        }
                        return "-";
                    }
                }, {
                    field: 'role',
                    title: translator.translate("role"),
                    formatter: function (value, row, index) {
                        if (row.roles.length > 0) {
                            return row.roles.map(function (r) {
                                return r.name;
                            }).join(",");
                        }
                        return "-";
                    },
                    sortable: true
                }, {
                    field: 'operation',
                    title: translator.translate("operation"),
                    formatter: function (value, row, index) {
                        return util.loadPageTemplate("table-row-template", row);
                    }
                }]
            });
        }

        function editUser(id) {
            window.location.href = "/user/authorization/cou?id=" + id;
        }

        function forbidUser(id) {
            swal({
                title: translator.translate("doYouWantToForbidUser") + "?",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: translator.translate("forbid"),
                cancelButtonText: translator.translate("cancel"),
                closeOnConfirm: false,
                html: false
            }, function () {
                ajax.delete("/api/user/forbid/" + id, function () {
                    swal(translator.translate("forbidInfo"), translator.translate("forbidInfo"), "success");
                    $("#dataTable").bootstrapTable('refresh');
                });
            });
        }

        window.editUser = editUser;
        window.forbidUser = forbidUser;
        init();
    });

function setUserStatus(){
    var mapHeight = $("#search_userStatus").val();
    $("#userStatus").val(mapHeight);
}