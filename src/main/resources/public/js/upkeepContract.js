define(["jquery", "vue", "ajax", "js/datatable", "js/util", "swal", "translator", "fullcalendar"],
    function($, Vue, ajax, datatable, util, swal, translator, calendar){
        var vm;
        var eventsArray=[];
        init();

        function init(){
            $(".menu-archive-management").addClass("active");
            $(".upkeep-contract").addClass("active");

            initDataTables();
            loadUpkeepEvents();
            vm = new Vue({
                el: ".search-form",
                methods: {
                    search: function () {
                        $("#dataTable").bootstrapTable("refresh");
                    },
                    export: function () {
                        window.location.href = "/api/upkeepContract/export";
                    },
                    add: function() {
                        window.location.href="/upkeepContract/add";
                    },
                    viewScheduler: function() {
                        $('.bootstrap-table').remove();
                        $('#calendar').removeClass('hidden');
                        $('#calendar').fullCalendar('today');
                    }
                }

            });
        }

        function loadUpkeepEvents() {
            ajax.get("/api/upkeepContract/seekUpkeepDay",function (res){
                for(var index in res.data){
                    var upkeepObject = res.data[index];
                    for(var i in upkeepObject){
                        for(var j in upkeepObject[i]){
                            var eleObject = upkeepObject[i][j];
                            for(var k in eleObject){
                                for(var m in eleObject[k]){
                                    var event = new Object();
                                    event.title = k;
                                    event.start = eleObject[k][m];
                                    if(k.indexOf("季保")>=0){
                                        event.backgroundColor='red';
                                    } else if(k.indexOf("半年保")>=0){
                                        event.backgroundColor='green';
                                    } else if(k.indexOf("年保")>=0){
                                        event.backgroundColor='blue';
                                    } else {
                                        event.backgroundColor="tomato";
                                    }
                                    eventsArray.push(event);
                                }
                            }
                        }
                    }
                }
                initCalendar();
            });

        }



        function initCalendar (){
            $("#calendar").fullCalendar({
                lang:translator.translate("language"),
                defaultView: 'month',
                events: eventsArray,
                titleFormat: 'MMMM, YYYY',
                eventLimit: 5,
                header: {
                    left:   'title',
                    center: '',
                    right:  'today, prev, next'
                },
                buttonText: {
                    today: translator.translate("today")
                },
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
                    field: 'remainelevator',
                    title:translator.translate("operation"),
                    formatter: function(value, row, index){
                        return ("<a href='/upkeepContract/planBathAdd?id=" + row.id + "'>" + "生成批次" + "</a>");
                    }
                }
                ]
            });
        }
    });