define(['jquery','vue','ajax'],function($,Vue,ajax) {

    var vm;
    var userid=$("#userid").val();
    var id=$("#elevatorid").val();
    function attention() {
        ajax.post("/weixin/elevator/addFavorite/"+id,{userid:userid},function(res){
            $("#message").html(res.description);
            $('#toast').show();
            setTimeout(function () {
                $('#toast').hide();
            }, 2000);
        })
    }
    function  init()
    {
        vm=new Vue({
            el:"#elevatorDetail",
            data:{
                userid:"",
                id:"",
                report:{
                    number:"",
                    peoples:0,
                    currentstatus:"",
                    telephone:""
                },
                complaint:{
                    number:"",
                    reason:"",
                    currentstatus:"",
                    telephone:""
                }

            },
            methods:{
                attention:function(){//关注
                    attention();
                },
                reportBarrier:function()//报障
                {
                    $('#reportDialog').show();
                },
                reportCancel:function()
                {
                    $('#reportDialog').hide();
                },
                reportComfirm:function()
                {

                }
                ,
                complaint:function(){
                    $('#complaintDialog').show();
                },
                complaintCancel:function()
                {
                    $('#complaintDialog').hide();
                },
                complaintComfirm:function()
                {

                }
            }
        })
    }

    init();
})