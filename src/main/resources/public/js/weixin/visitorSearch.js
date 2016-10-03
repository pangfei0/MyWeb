define(['jquery','vue','ajax'],function($,Vue,ajax){

    //var vm;
    function search(number)
    {
            ajax.get("/weixin/elevator/elevatorSearch/"+vm.number,function(res)
            {
                if(res.data!=null)
                {
                    $("#searchElevator").innerHTML="<a href=\"javascript:void(0);\" class=\"weui_media_box weui_media_appmsg\">" +
                        "<div class=\"weui_media_hd\">"+
                        "<a href=\"javascript:void(0);\" class=\"weui_media_box weui_media_appmsg\"> </div>" +

                        "<div class=\"weui_media_bd\">"+
                        "<h4 class=\"weui_media_title\">"+res.data.number+"</h4>"+
                        "<p class=\"weui_media_desc\">"+res.data.address+res.data.alias+"</p>"+
                        "</div> </a>"
                }

            })
    }
    function unfav()
    {
      ajax.post("/weixin/elevator/delFavorite",{userid:$('#userid').val(),elevatorid:elevatorid},function(res)
      {
          var str="";
          $.forEach(res.data,function(i,n)
          {
              str=str+"<a href=\"javascript:void(0);\" class=\"weui_media_box weui_media_appmsg\">" +
                  "<div class=\"weui_media_hd\">"+
                  "<a href=\"javascript:void(0);\" class=\"weui_media_box weui_media_appmsg\"> </div>" +

                  "<div class=\"weui_media_bd\">"+
                  "<h4 class=\"weui_media_title\">"+n.number+"</h4>"+
                  "<p class=\"weui_media_desc\">"+n.address+n.alias+"</p>"+
                  "</div> </a>"
          })
          $("#favElevator").innerHTML=str;

      })
    }
    function init()
    {
        vm=new Vue({
            el:"#elevatorContainer",
            data:{
              number:""
            },
            methods:{
                elevatorSearch:search(number)
              }
        })
    }

  init();


})