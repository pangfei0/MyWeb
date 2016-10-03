<#import "weixinLayout.ftl" as layout />
<#include '../func.ftl'>

    <#assign headerContent>
        <link rel="stylesheet" href="/css/weixin/liftRepair.css">
    </#assign>
<@layout.weixinMasterTemplate initScript='js/weixin/liftRepair.js' header=headerContent title='维保计划'>
<div class="weui_cells_title">电梯编号：G1092039</div>
<div class="weui-cells">
        <div class="weui-cell">
            <div class="weui-cell__bd">
                <p>别名：</p>
            </div>
            <div class="weui-cell__ft">城际风尚花园2号楼北</div>
        </div>
</div>
<img alt="电梯" src="image/lift.jpg" style="width: 280px;height: 250px;"/>
<div>
<a href="javascript:;" class="weui-btn weui-btn_primary"  style="width:50%;float: left;top: 15px;">普通故障报修</a>
<a href="javascript:;" class="weui-btn weui-btn_primary"  style="width:50%;float: right;">紧急故障报修</a>
</div>
</@layout.weixinMasterTemplate>