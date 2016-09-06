define(["jquery", "vue", "ajax", "js/datatable", "js/util", "swal", "translator"],
    function ($, Vue, ajax, datatable, util, swal, translator) {
        var bootstrapTable;
        function init() {
            $(".unitRole").addClass("active");
            createBootstrapTable();
            loadUnit_type();
            vm = new Vue({
                el: ".search-form",
                methods: {
                    search: function () {
                        $("#dataTable").bootstrapTable("refresh");
                    }
                }
            });
        }
        function loadUnit_type(){
            $.post("/api/unitRole/unitType", {}, function (res) {
                if (res.success) {
                    $.each(res.data, function (index, data) {
                        var opStr = '<option value="' + data + '">' + data + '</option>';
                        $('#search_unitType').append(opStr);
                    });
                }
            });
        }




        function createBootstrapTable() {
            bootstrapTable = datatable.createTable("dataTable", "/api/unitRole/search/page/unitRole", {
                columns: [{
                    field: 'unitType',
                    title: translator.translate("unitType")
                }, {
                    field: 'roleName',
                    title: translator.translate("role"),

                },{
                    field: 'roleId',
                    title: '',
                    visible:false
                },
                    {
                    field: 'operation',
                    title: translator.translate("operation"),
                    formatter: function (value, row, index) {
                        return util.loadPageTemplate("table-row-template", row);
                    }
                }]
            });
        }

        function editUnitRole(role_id) {
            window.location.href = "/user/unitRole/cou?role_id=" + role_id;
        }
        function deleteUnitRole(role_id) {
            swal({
                title: translator.translate("doYouWantToDeleteRole") + "?",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: translator.translate("delete"),
                cancelButtonText: translator.translate("cancel"),
                closeOnConfirm: false,
                html: false
            }, function () {
                ajax.delete("/api/unitRole/" + role_id, function () {
                    swal(translator.translate("deletedInfo"), translator.translate("deletedInfo"), "success");
                    $("#dataTable").bootstrapTable('refresh');
                });
            });
        }
        window.editUnitRole = editUnitRole;
        window.deleteUnitRole = deleteUnitRole;
        init();
    }

);
function setSearch_unitTypeValue(){
    var mapHeight = $("#search_unitType").val();
    $("#unitType").val(mapHeight);
}