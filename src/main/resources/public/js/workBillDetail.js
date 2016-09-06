define(["jquery", "vue", "ajax", "js/util", "swal", "translator"], function ($, Vue, ajax, util, swal, translator) {
        var vm;

        function init() {
            $(".work-bill-board").addClass("active");
            initVue();


        }
        function initVue() {
            vm = new Vue({
                el: "#workBillDetail",
                methods: {

                    back:function()//(取消操作)
                    {
                        window.location.href = "/workBillBoard";
                    }

                }
            });
        }

        init();
    }
);