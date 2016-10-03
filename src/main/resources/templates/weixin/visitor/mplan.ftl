
<#import "../weixinLayout.ftl" as layout />
<#include '../../func.ftl'>

<#assign headerContent>
<link rel="stylesheet" href="/css/weixin/mplan.css"">
</#assign>
<@layout.weixinMasterTemplate initScript='js/weixin/mplan.js' header=headerContent title='维保计划'>

	<div class="weui_cells_title">电梯编号：G1092039</div>
	<div  id="container" class="weui_cells weui_cells_access">
        <input type="hidden" v-model="elevatorid"  id="elevatorid"value="${elevatorid}">
		<a class="weui_cell monthlypackageplan">
            <div class="weui_cell_bd weui_cell_primary">
                <p>半月包计划</p>
            </div>
            <div class="weui_cell_ft">
            </div>
        </a>
        <div class="weui_cells_access planpop" style="display:none;">
			<a class="weui_cell showDetail" href="javascript:;" v-for="plan in banyue" v-on:click="banyueClick($index)">
				<div class="weui_cell_bd weui_cell_primary">
					<p>({{$index}}) {{plan.planTime}}--{{plan.planEndTime}}</p>
				</div>
				<div class="weui_cell_ft" v-if="plan.status==10">未生成工单</div>
                <div class="weui_cell_ft" v-else>已生成工单</div>
			</a>
		</div>
		<a class="weui_cell monthlypackageplan">
            <div class="weui_cell_bd weui_cell_primary">
                <p>季度包计划</p>
            </div>
            <div class="weui_cell_ft">
            </div>
        </a>
        <div class="weui_cells_access planpop" style="display:none;">
			<a class="weui_cell" href="javascript:;" v-for="plan in jidu" v-on:click="jiduClick($index)">
				<div class="weui_cell_bd weui_cell_primary">
					<p>({{$index}}) {{plan.planTime}}--{{plan.planEndTime}}</p>
				</div>
                <div class="weui_cell_ft" v-if="plan.status==10">未生成工单</div>
                <div class="weui_cell_ft" v-else>已生成工单</div>
			</a>
		</div>
		<a class="weui_cell monthlypackageplan">
            <div class="weui_cell_bd weui_cell_primary">
                <p>半年包计划</p>
            </div>
            <div class="weui_cell_ft">
            </div>
        </a>
        <div class="weui_cells_access planpop" style="display:none;">
			<a class="weui_cell" href="javascript:;" v-for="plan in bannian" v-on:click="bannianClick($index)">
                <div class="weui_cell_bd weui_cell_primary">
                    <p>({{$index}}) {{plan.planTime}}--{{plan.planEndTime}}</p>
                </div>
                <div class="weui_cell_ft" v-if="plan.status==10">未生成工单</div>
                <div class="weui_cell_ft" v-else>已生成工单</div>
            </a>
		</div>
		<a class="weui_cell monthlypackageplan">
            <div class="weui_cell_bd weui_cell_primary">
                <p>年度包计划</p>
            </div>
            <div class="weui_cell_ft">
            </div>
        </a>
        <div class="weui_cells_access planpop" style="display:none;">
			<a class="weui_cell" href="javascript:;"v-for="plan in quannian" v-on:click="quannianClick($index)">
                <div class="weui_cell_bd weui_cell_primary">
                    <p>({{$index}}) {{plan.planTime}}--{{plan.planEndTime}}</p>
                </div>
                <div class="weui_cell_ft" v-if="plan.status==10">未生成工单</div>
                <div class="weui_cell_ft" v-else>已生成工单</div>
            </a>
		</div>
    </div>
	<div class="weui_dialog_confirm" id="dialog1" style="display: none;">
		<div class="weui_mask"></div>
		<div class="weui_dialog">
			<div class="weui_dialog_hd">
				<strong class="weui_dialog_title">计划详情</strong>
			</div>
			<div class="weui_dialog_bd">
				<div class="weui_cells">
                    <div class="weui_cell">
                        <div class="weui_cell_bd weui_cell_primary">
                            <p>合同编号：</p>
                        </div>
                        <div class="weui_cell_ft" id="upkeepContractNumber">2017年6月19日</div>
                    </div>
                    <div class="weui_cell">
                        <div class="weui_cell_bd weui_cell_primary">
                            <p>电梯编号：</p>
                        </div>
                        <div class="weui_cell_ft" id="number">2017年6月19日</div>
                    </div>
                    <div class="weui_cell">
                        <div class="weui_cell_bd weui_cell_primary">
                            <p>维保类型：</p>
                        </div>
                        <div class="weui_cell_ft" id="planType">2017年6月19日</div>
                    </div>
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary">
							<p>计划开始时间：</p>
						</div>
						<div class="weui_cell_ft" id="planTime">2017年6月19日</div>
					</div>
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary">
							<p>计划结束时间：</p>
						</div>
						<div class="weui_cell_ft" id="planEndTime">2017年6月19日</div>
					</div>
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary">
							<p>实际完成时间：</p>
						</div>
						<div class="weui_cell_ft" id="actualTime">2017年6月19日</div>
					</div>
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary">
							<p>工单生成时间：</p>
						</div>
						<div class="weui_cell_ft" id="createBillTime">2017年6月19日</div>
					</div>
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary">
							<p>计划状态：</p>
						</div>
						<div class="weui_cell_ft" id="status">2017年6月19日</div>
					</div>
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary">
							<p>维保人：</p>
						</div>
						<div class="weui_cell_ft" id="maintenanceMan">2017年6月19日</div>
					</div>
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary">
							<p>工单编号：</p>
						</div>
						<div class="weui_cell_ft" id="workBillNumber">2017年6月19日</div>
					</div>
				</div>
			</div>
			<div class="weui_dialog_ft">
				<a href="javascript:;" class="weui_btn_dialog default">取消</a> <a
					href="javascript:;" class="weui_btn_dialog primary">确定</a>
			</div>
		</div>
	</div>
</@layout.weixinMasterTemplate>