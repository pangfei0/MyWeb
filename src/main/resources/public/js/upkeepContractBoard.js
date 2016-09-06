define(["jquery", "vue", "ajax", "js/datatable", "js/util", "swal", "translator"],
    function($, Vue, ajax, datatable, util, swal, translator){
        var vm;
        init();

        function init(){
            $(".menu-service-management").addClass("active");
            $(".upkeep-contract-board").addClass("active");

            initDataTables();
            vm = new Vue({
                el: ".search-form",
                methods: {
                    search: function () {
                        $("#dataTable").bootstrapTable("refresh");
                    }
                }

            });
        }

        function initDataTables(){
            datatable.createTable("dataTable","/api/upkeepContract/new/search/page", {
                columns: [{
                    field: 'number',
                    title:translator.translate("numberOfUpkeepContract"),
                    formatter: function(value, row, index){
                        return ("<a href='/upkeepContract/cou?id=" + row.id + "'>" + row.number + "</a>");
                    },
                    sortable: true
                },{
                    field: 'ownerShortname',
                    title:translator.translate("ownerName"),
                    sortable: true
                },{
                    field: 'property',
                    title: translator.translate("contractNature"),
                    sortable: true
                },{
                    field: 'remainDays',
                    title: translator.translate("remainingDaysOfContract")
                },{
                    field: 'renewStatus',
                    title:translator.translate("renewalStatus"),
                    sortable: true
                },{
                    field: 'status',
                    title: translator.translate("ContractStatus"),
                    sortable: true
                },{
                    field: 'remainElevator',
                    title: translator.translate("elevatorNumbers")
                },
                    {
                        field: 'remainElevator',
                        title:translator.translate("operation"),
                        formatter: function(value, row, index){
                            return ("<a href='/upkeepContract/planBathAdd?id=" + row.id + "'>" + "生成批次" + "</a>");
                        }
                    }
                ]
            });
        }
    });