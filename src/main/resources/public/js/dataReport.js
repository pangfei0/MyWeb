define(["jquery", "vue", "ajax", "js/dynamicSearch"], function ($, Vue, ajax, dynamicSearch) {
        var vm;

        function init() {
            $(".menu-data-report").addClass("active");
            initVue();
        }

        function initVue() {
            vm = new Vue({
                el: "#content",
                data: {
                    search: {
                        premise: "",
                        area: "",
                        brand: "",
                        contractNumber: "",
                        serviceLife: "",
                        status: ""
                    },
                    generate: {
                        controlSystem: true,
                        elevatorStatus: true,
                        malfunctionHistory: false,
                        maintenance: false,
                        malfunctionAnalyse: false,
                        currentHealthExamination: false
                    }
                },
                methods: {
                    export: function () {

                    }
                }
            });
        }

        init();
    }
);