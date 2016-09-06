define(["jquery", "vue", "ajax", "js/datatable", "js/util", "swal", "translator"],
    function($, Vue, ajax, datatable, util, swal, translator){
        init();

        function init() {
            $(".menu-archive-management").addClass("active");
            $(".maintainer-employee").addClass("active");

            initDataTables();
            var vm = new Vue({
                el: ".search-form",
                methods: {
                    search: function () {
                        $("#dataTable").bootstrapTable("refresh");
                    },
                    export: function () {
                        var number=$("#number").val();
                        var name=$("#name").val();
                        window.location.href = "/api/maintenancePersonnel/exportForPerson?number="+number+"&name="+name;
                    }
                }
            });
        }

        function initDataTables() {
            datatable.createTable("dataTable","/api/maintenancePersonnel/new/search/page", {
                columns: [{
                    field: 'number',
                    title: translator.translate("numberOfMaintainerEmployee"),
                    formatter: function(value, row, index){
                        return ("<a href='/maintainerEmployee/cou?id=" + row.id + "'>" + row.number + "</a>");
                    },
                    sortable: true
                },{
                    field: 'name',
                    title:  translator.translate("nameOfMaintainerEmployee")
                },{
                    field: 'manager',
                    title:  translator.translate("managerOfMaintainerEmployee")
                },{
                    field: 'telephone',
                    title: translator.translate("telephoneOfMaintainerEmployee")
                },{
                    field: 'maintainerName',
                    title: translator.translate("maintenanceUnit")
                },{
                    field: 'region',
                    title: '维保区域'
                },{
                    field: 'station',
                    title: '维保站'
                }]
            });
        }
    }
);