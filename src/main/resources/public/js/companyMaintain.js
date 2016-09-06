define(["jquery", "vue", "ajax", "js/datatable", "js/util", "swal", "translator"],
    function ($, Vue, ajax, datatable, util, swal, translator) {

    var vm;
    init();
    function init() {
        $(".menu-archive-management").addClass("active");
        $(".companyMaintain" ).addClass("active");
        initDataTables();
        vm = new Vue({
            el: ".search-form",
            methods: {
                search: function () {
                    $('#companyType').val($("#search_companyType").val());
                    $("#dataTable").bootstrapTable("refresh");
                },
                export: function () {
                   var type= $("#search_companyType").val();
                    var companyName=$('#name').val();
                    var companyAddress=$('#address').val();
                   window.location.href = "/api/companyMaintain/export?type="+type+"&companyName="+companyName+"&companyAddress="+companyAddress;
                }
            }
        });
    }
    function initDataTables() {
        datatable.createTable("dataTable", "/api/companyMaintain/new/search/page", {
            columns: [{
                title: '',
                formatter: function (value, row, index) {
                    return (index + 1);
                }
            }, {
                field: 'name',
                title: translator.translate("nameOfCompany"),
                formatter: function (value, row, index) {
                    return ("<a href= '/companyMaintain/cou?id=" + row.id + "'>" + row.name + "</a>");
                }
            },{
                field: 'type',
                title: translator.translate("typeOfCompany"),
                formatter:function(value){
                    if(value=="10") return "安装单位";
                    else if (value=="20") return "维保单位";
                    else if (value=="30") return "使用单位";
                    else if (value=="40") return "制造单位";
                    else if (value=="50") return "物业单位";
                    else if (value=="60") return "监管机构";
                    else if (value=="70") return "个人用户";
                    else if (value=="80") return "其他类型";
                }
            }, {
                field: 'address',
                title:translator.translate("addressOfCompany")
            }, {
                field: 'phone',
                title: translator.translate("phoneOfCompany")
            }, {
                field: 'contact',
                title:translator.translate("contactOfCompany")
            }, {
                field: 'mobile',
                title: translator.translate("mobileOfCompany")
            } ]
                //, {
                //field: '',
                //title: translator.translate("elevatorNumbers"),
                //formatter: function (value, row, index) {
                //    //TODO
                //}
           // }

        });
    }
});