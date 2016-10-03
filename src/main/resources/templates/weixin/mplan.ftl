
<#import "weixinLayout.ftl" as layout />
<#include '../func.ftl'>

<#assign headerContent>
<link rel="stylesheet" href="/css/weixin/mplan.css"">
</#assign>
<@layout.weixinMasterTemplate initScript='js/weixin/mplan.js' header=headerContent title='维保计划'>
	<div class="weui_cells_title">电梯编号：G1092039</div>
	<div class="weui_cells weui_cells_access">
        <a class="weui_cell monthlypackageplan">
            <div class="weui_cell_bd weui_cell_primary">
                <p>电梯详细信息</p>
            </div>
            <div class="weui_cell_ft">
            </div>
        </a>
        <div class="weui_cells_access planpop" style="display:none;">
			<a class="weui_cell" id="showDialog1">
				<div class="weui_cell_bd weui_cell_primary">
					<p>计划开始时间--计划结束时间</p>
				</div>
				<div class="weui_cell_ft">状态</div>
			</a>
			<a class="weui_cell" id="showDialog2">
				<div class="weui_cell_bd weui_cell_primary">
					<p>计划开始时间--计划结束时间</p>
				</div>
				<div class="weui_cell_ft">状态</div>
			</a>
		</div>
		<a class="weui_cell monthlypackageplan">
            <div class="weui_cell_bd weui_cell_primary">
                <p>半月包计划</p>
            </div>
            <div class="weui_cell_ft">
            </div>
        </a>
        <div class="weui_cells_access planpop" style="display:none;">
			<a class="weui_cell" href="javascript:;">
				<div class="weui_cell_bd weui_cell_primary">
					<p>计划开始时间--计划结束时间</p>
				</div>
				<div class="weui_cell_ft">状态</div>
			</a>
			<a class="weui_cell" href="javascript:;">
				<div class="weui_cell_bd weui_cell_primary">
					<p>计划开始时间--计划结束时间</p>
				</div>
				<div class="weui_cell_ft">状态</div>
			</a>
		</div>
        <a class="weui_cell monthlypackageplan">
            <div class="weui_cell_bd weui_cell_primary">
                <p>月度包计划</p>
            </div>
            <div class="weui_cell_ft">
            </div>
        </a>
        <div class="weui_cells_access planpop" style="display:none;">
			<a class="weui_cell" href="javascript:;">
				<div class="weui_cell_bd weui_cell_primary">
					<p>计划开始时间--计划结束时间</p>
				</div>
				<div class="weui_cell_ft">状态</div>
			</a>
			<a class="weui_cell" href="javascript:;">
				<div class="weui_cell_bd weui_cell_primary">
					<p>计划开始时间--计划结束时间</p>
				</div>
				<div class="weui_cell_ft">状态</div>
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
			<a class="weui_cell" href="javascript:;">
				<div class="weui_cell_bd weui_cell_primary">
					<p>计划开始时间--计划结束时间</p>
				</div>
				<div class="weui_cell_ft">状态</div>
			</a>
			<a class="weui_cell" href="javascript:;">
				<div class="weui_cell_bd weui_cell_primary">
					<p>计划开始时间--计划结束时间</p>
				</div>
				<div class="weui_cell_ft">状态</div>
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
			<a class="weui_cell" href="javascript:;">
				<div class="weui_cell_bd weui_cell_primary">
					<p>计划开始时间--计划结束时间</p>
				</div>
				<div class="weui_cell_ft">状态</div>
			</a>
			<a class="weui_cell" href="javascript:;">
				<div class="weui_cell_bd weui_cell_primary">
					<p>计划开始时间--计划结束时间</p>
				</div>
				<div class="weui_cell_ft">状态</div>
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
			<a class="weui_cell" href="javascript:;">
				<div class="weui_cell_bd weui_cell_primary">
					<p>计划开始时间--计划结束时间</p>
				</div>
				<div class="weui_cell_ft">状态</div>
			</a>
			<a class="weui_cell" href="javascript:;">
				<div class="weui_cell_bd weui_cell_primary">
					<p>计划开始时间--计划结束时间</p>
				</div>
				<div class="weui_cell_ft">状态</div>
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
							<p>计划开始时间：</p>
						</div>
						<div class="weui_cell_ft">2017年6月19日</div>
					</div>
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary">
							<p>计划开始时间：</p>
						</div>
						<div class="weui_cell_ft">2017年6月19日</div>
					</div>
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary">
							<p>计划开始时间：</p>
						</div>
						<div class="weui_cell_ft">2017年6月19日</div>
					</div>
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary">
							<p>计划开始时间：</p>
						</div>
						<div class="weui_cell_ft">2017年6月19日</div>
					</div>
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary">
							<p>计划开始时间：</p>
						</div>
						<div class="weui_cell_ft">2017年6月19日</div>
					</div>
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary">
							<p>计划开始时间：</p>
						</div>
						<div class="weui_cell_ft">2017年6月19日</div>
					</div>
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary">
							<p>计划开始时间：</p>
						</div>
						<div class="weui_cell_ft">2017年6月19日</div>
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