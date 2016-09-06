define(["jquery", "vue", "ajax", "js/util", "swal", "translator"], function ($, Vue, ajax, util, swal, translator) {
        var vm;

        function init() {
            $(".report-board").addClass("active");
            initVue();


        }
        function initVue() {
            vm = new Vue({
                el: "#reportDetail",
                methods: {

                    back:function()//(取消操作)
                    {
                        window.location.href = "/reportBoard";
                    }

                }
            });
        }

        init();
    }
);/**
 * Created by pf on 2016/3/24.
 */
