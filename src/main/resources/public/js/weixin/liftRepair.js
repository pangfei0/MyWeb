define(['jquery','vue','ajax'],function($,Vue,ajax){
	$(".monthlypackageplan").click(function(){
		$(this).next().toggle();
	});
	$("#showDialog1").click(function(){
		$('#dialog1').show().on('click', '.weui_btn_dialog', function () {
            $('#dialog1').off('click').hide();
        });
	});
	$("#showDialog2").click(function(){
		$('#dialog1').show().on('click', '.weui_btn_dialog', function () {
            $('#dialog1').off('click').hide();
        });
	});
});