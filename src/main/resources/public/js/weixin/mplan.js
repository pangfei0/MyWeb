define(['jquery','vue','ajax'],function($,Vue,ajax){
	var vm;
	$(".monthlypackageplan").click(function(){
		$(this).next().toggle();
	});
	function showDialog(index,detail)
	{
		//初始化数据
		$("#upkeepContractNumber").html("");
		$("#number").html("");
		$("#workBillNumber").html("");
		$("#maintenanceMan").html("");
		$("#status").html("");
		$("#planTime").html("");
		$("#planEndTime").html("");
		$("#actualTime").html("");
		$("#createBillTime").html("");

		$("#upkeepContractNumber").html(detail.upkeepContractNumber);
		$("#number").html(detail.number);
		$("#workBillNumber").html(detail.workBillNumber);
		$("#maintenanceMan").html(detail.maintenanceMan);
		if(detail.status==10)
			$("#status").html("未生成工单");
		else
		{
			$("#status").html("已生成工单");
		}
		$("#planTime").html(detail.planTime);
		$("#planEndTime").html(detail.planEndTime);
		$("#actualTime").html(detail.actualTime);
		$("#createBillTime").html(detail.createBillTime);
		if(detail.planType==10)
		{
			$("#planType").html("半月保");
		}
		else if(detail.planType==20)
		{
			$("#planType").html("季度保");
		}
		else if(detail.planType==30)
		{
			$("#planType").html("半年保");
		}
		else if(detail.planType==40)
		{
			$("#planType").html("全年保");
		}
		$('#dialog1').show().on('click', '.weui_btn_dialog', function () {
			$('#dialog1').off('click').hide();
		});
	}

	function banyueClick(index) {
		var detail=vm.banyue[index];
		showDialog(index,detail);
	}

	function jiduClick(index)
	{
		//初始化数据
		var detail=vm.jidu[index];
		showDialog(index,detail);

	}
	function bannianClick(index)
	{
		//初始化数据
		var detail=vm.bannian[index];
		showDialog(index,detail);
	}
	function qunnianClick(index)
	{
		//初始化数据
		var detail=vm.quannian[index];
		showDialog(index,detail);

	}

	//初始化信息
	function initData()
	{
		ajax.post("/weixin/mplan/getAllPlan/"+$("#elevatorid").val(),{},function(d)
		{
			vm.banyue= d.data.banyue;
			vm.jidu= d.data.jidu;
			vm.bannian= d.data.bannian;
			vm.quannian= d.data.quannian;
		})
	}
	function init()
	{
		vm=new Vue({
			el:"#container",
			data:{
				elevatorid:$("#elevatorid").val(),
                banyue:{},
				jidu:{},
				bannian:{},
				quannian:{}
			},
			methods:{
				banyueClick:function(index)
				{
					banyueClick(index);
				},
				jiduClick: function (index) {
					jiduClick(index);
				},
				bannianClick:function(index){
					bannianClick(index);
				},
				quannianClick:function(index)
				{
					quannianClick(index);
				}

			}
		});
		initData();

	}
	init();

});