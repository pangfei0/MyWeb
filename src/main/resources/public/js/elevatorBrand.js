define(["jquery", "vue", "ajax", "js/datatable", "js/util", "swal", "translator", "moment"],
    function($, Vue, ajax, datatable, util, swal, translator, moment){
        var table;
        init();

        function init() {
            $(".menu-archive-management").addClass("active");
            $(".elevator-brand").addClass("active");
            initDataTables();
            var vm = new Vue({
                el: ".search-form",
                methods: {
                    export: function () {
                        window.location.href = "/api/elevatorBrand/export";
                    }
                }
            });

        }

        function initDataTables(){
            table = datatable.createTable("dataTable","/api/elevatorBrand/search/page", {
                columns: [{
                    field: 'number',
                    title: translator.translate("numberOfElevatorBrand"),
                    formatter: function (value, row, index){
                        return ("<a href='/elevatorBrand/cou?id=" + row.id + "'>" + row.number + "</a>");
                    }
                }, {
                    field: 'name',
                    title:translator.translate("nameOfElevatorBrand")
                }, {
                    field: 'protocolType',
                    title:translator.translate("proTypeOfElevatorBrand")
                }, {
                    field: '',
                    title:translator.translate("elevatorNumbers"),
                    formatter: function (value, row, index){
                        //TODO
                    }
                }, {
                    field: 'lastModifyBy',
                    title: translator.translate("lastModifier")
                }, {
                    field: 'lastModifiedDate',
                    title: translator.translate("lastModifyDate"),
                    formatter: function (value, row, index) {
                        if (value != null) {
                            return moment(value.millis).format("YYYY-MM-DD HH:mm:ss");
                        }
                        return "-";
                    }
                }]
            });
        }
    });