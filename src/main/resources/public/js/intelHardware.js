define(["jquery", "vue", "ajax", "js/datatable", "js/util", "swal", "translator"],
    function ($, Vue, ajax, datatable, util, swal, translator) {
        init();
        function init(){
            $(".menu-archive-management").addClass("active");
            $(".intel-hardware").addClass("active");

            initDataTables();
            var vm= new Vue({
                el: ".search-form",
                methods: {
                    search: function () {
                        $("#dataTable").bootstrapTable("refresh");
                    },
                    export: function () {

                    }
                }
            });
        }



        function initDataTables() {
            datatable.createTable("dataTable","/api/collector/search/page", {
                columns:[{
                    title: '',
                    formatter: function(value, row, index){
                        return (index + 1);
                    }
                },{
                    field: 'intelHardwareNumber',
                    title:translator.translate("intelHardwareNumberOfElevator")
                }, {
                    field: 'number',
                    title:translator.translate("numberOfElevator")
                }, {
                    field: 'address',
                    title:translator.translate("addressOfElevator")
                }]
            })
        }

});