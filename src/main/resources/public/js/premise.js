define(["jquery", "vue", "ajax", "js/datatable", "js/util", "swal", "translator"],
    function ($, Vue, ajax, datatable, util, swal, translator) {
        var table;
        init();

        function init() {
            $(".menu-archive-management").addClass("active");
            $(".premise").addClass("active");
            initDataTables();
            vm = new Vue({
                el: ".search-form",
                methods: {
                    search: function () {
                        $("#dataTable").bootstrapTable("refresh");
                    },
                    export: function () {
                        window.location.href = "/api/premise/export";
                    }
                }
            });
        }

        function initDataTables() {
            table = datatable.createTable("dataTable", "/api/premise/search/page", {
                columns: [{
                    title: '',
                    formatter: function (value, row, index) {
                        return (index + 1);
                    }
                }, {
                    field: 'name',
                    title: translator.translate("nameOfPremise"),
                    formatter: function (value, row, index) {
                        return ("<a href='/premise/cou?id=" + row.id + "'>" + row.name + "</a>");
                    }
                }, {
                    field: 'address',
                    title: translator.translate("addressOfPremise")
                }, {
                    field: 'maintainer.name',
                    title: translator.translate("maintenanceUnit"),
                    formatter: function (value, row, index) {
                        if (row.maintainer == null) return "-";
                        return row.maintainer.name;
                    }
                }, {
                    field: 'maintainer.phone',
                    title: translator.translate("phoneOfCompany"),
                    formatter: function (value, row, index) {
                        if (row.maintainer == null) return "-";
                        return row.maintainer.phone;
                    }
                }
                //},
                //    {
                //    field: '',
                //    title: translator.translate("elevatorNumbers"),
                //    formatter: function (value, row, index) {
                //        //TODO
                //    }
                //},
                //    {
                //    field: 'lastModifyBy',
                //    title: translator.translate("lastModifier")
                //}, {
                //    field: 'lastModifiedDate',
                //    title: translator.translate("lastModifyDate"),
                //    formatter: function (value, row, index) {
                //        if (value != null) {
                //            return moment(value.millis).format("YYYY-MM-DD HH:mm:ss");
                //        }
                //        return "-";
                //    }
                //}
                ]
            });
        }
    });