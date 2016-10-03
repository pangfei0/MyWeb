<#import "../weixinLayout.ftl" as layout />
<#include '../../func.ftl'>

<#assign headerContent>
<link rel="stylesheet" href="/css/weixin/liftRepair.css">
</#assign>
<@layout.weixinMasterTemplate initScript='js/weixin/liftManage.js' header=headerContent title='电梯管理'>
	<div class="container" id="container">
	<div class="page searchbar js_show">
    <div class="page__bd">
        <div class="weui-search-bar" id="search_bar">
            <form class="weui-search-bar__form">
                <div class="weui-search-bar__box">
                    <i class="weui-icon-search"></i>
                    <input type="search" class="weui-search-bar__input" id="search_input" placeholder="搜索" required="" v-model="number">
                    <a href="javascript:" class="weui-icon-clear" id="search_clear"></a>
                </div>
                <label for="search_input" class="weui-search-bar__label" id="search_text" v-on:click="elevatorSearch">
                    <i class="weui-icon-search"></i>
                    <span>搜索</span>
                </label>
            </form>
            <a href="javascript:" class="weui-search-bar__cancel-btn" id="search_cancel">取消</a>
        </div>
        <div class="weui-cells weui-cells_access search_show" id="search_show" style="display:none;">
            <div class="weui-cell">
                <div class="weui-cell__bd weui-cell_primary">
                    <p>实时搜索文本</p>
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__bd weui-cell_primary">
                    <p>实时搜索文本</p>
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__bd weui-cell_primary">
                    <p>实时搜索文本</p>
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__bd weui-cell_primary">
                    <p>实时搜索文本</p>
                </div>
            </div>
        </div>
    </div>
</div>
	<div class="weui_cells_title">关注电梯</div>
	<div class="weui-cells weui-cells_form">
    <#--<#list favlist as fav >-->
            <#--<div class="weui-cell weui-cell_switch">-->
                <#--<a href="/weixin/achieve/elevatorDetail/${fav.id}" >-->
            	<#--<div class="weui-cell__hd">-->
            		<#--<img src="/image/elevator_opening.gif?timestamp=1474260060 alt="" style="width:20px;margin-right:5px;display:block">-->
            	<#--</div>-->
                <#--<div class="weui-cell__bd">${fav.number} ${fav.address+" "+fav.alias}</div>-->
                <#--<div class="weui-cell__ft">-->
                    <#--<input class="weui-switch" type="checkbox">-->
                <#--</div>-->
                <#--</a>-->
            <#--</div>-->
    <#--</#list>-->
        <a href="/weixin/achieve/elevatorDetail" class="weui_media_box weui_media_appmsg">
            <div class="weui-cell weui-cell_switch">
                <div class="weui-cell__hd">
                    <img src="/image/elevator_opening.gif?timestamp=1474260060 alt="" style="width:20px;margin-right:5px;display:block">
                </div>
                <div class="weui-cell__bd">G1092039 &nbsp;&nbsp;&nbsp;&nbsp; 苏州大学理工楼 西楼</div>
                <div class="weui-cell__ft">
                    <input class="weui-switch" type="checkbox">
                </div>
            </div></a>
            <a href="/weixin/achieve/elevatorDetail" class="weui_media_box weui_media_appmsg">
                <div class="weui-cell weui-cell_switch">
                    <div class="weui-cell__hd">
                        <img src="/image/elevator_opening.gif?timestamp=1474260060 alt="" style="width:20px;margin-right:5px;display:block">
                    </div>
                    <div class="weui-cell__bd">G1092039 &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; werwerewwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; 苏州大学理工楼 西楼</div>
                    <div class="weui-cell__ft">
                        <input class="weui-switch" type="checkbox">
                    </div>
                </div><a href="/weixin/achieve/elevatorDetail" class="weui_media_box weui_media_appmsg">
                <div class="weui-cell weui-cell_switch">
                    <div class="weui-cell__hd">
                        <img src="/image/elevator_opening.gif?timestamp=1474260060 alt="" style="width:20px;margin-right:5px;display:block">
                    </div>
                    <div class="weui-cell__bd">G1092039 &nbsp;&nbsp;&nbsp;&nbsp; 苏州大学理工楼 西楼</div>
                    <div class="weui-cell__ft">
                        <input class="weui-switch" type="checkbox">
                    </div>
                </div>
                </a>
            <a href="javascript:void(0);" class="weui-cell weui-cell_link">
                <div class="weui-cell__bd">查看更多</div>
            </a>
    </div>
    <div class="weui_cells_title">拥有电梯</div>
	<div class="weui-cells weui-cells_form">
        <#--<#list elevators as elevator >-->
        <#--<a href="/weixin/achieve/elevatorDetail/${elevator.id}" >-->
            <#--<div class="weui-cell weui-cell_switch">-->
            	<#--<div class="weui-cell__hd">-->
            		<#--<img src="/image/elevator_opening.gif?timestamp=1474260060 alt="" style="width:20px;margin-right:5px;display:block">-->
            	<#--</div>-->
                <#--<div class="weui-cell__bd">>${elevator.number} ${elevator.address+" "+elevator.alias}</div>-->
                <#--<div class="weui-cell__ft">-->
                    <#--<input class="weui-switch" type="checkbox">-->
                <#--</div>-->
            <#--</div>-->
        <#--</a>-->
        <#--</#list>-->
            <a href="/weixin/achieve/elevatorDetail" class="weui_media_box weui_media_appmsg">
            <div class="weui-cell weui-cell_switch">
            	<div class="weui-cell__hd">
            		<img src="/image/elevator_opening.gif?timestamp=1474260060 alt="" style="width:20px;margin-right:5px;display:block">
            	</div>
                <div class="weui-cell__bd">G1092039 &nbsp;&nbsp;&nbsp;&nbsp; 苏州大学理工楼 西楼</div>
                <div class="weui-cell__ft">
                    <input class="weui-switch" type="checkbox">
                </div>
            </div>
            </a>
            <a href="/weixin/achieve/elevatorDetail" class="weui_media_box weui_media_appmsg">
                <div class="weui-cell weui-cell_switch">
                    <div class="weui-cell__hd">
                        <img src="/image/elevator_opening.gif?timestamp=1474260060 alt="" style="width:20px;margin-right:5px;display:block">
                    </div>
                    <div class="weui-cell__bd">G1092039 &nbsp;&nbsp;&nbsp;&nbsp; 苏州大学理工楼 西楼</div>
                    <div class="weui-cell__ft">
                        <input class="weui-switch" type="checkbox">
                    </div>
                </div>
                <a href="/weixin/achieve/elevatorDetail" class="weui_media_box weui_media_appmsg">
                    <div class="weui-cell weui-cell_switch">
                        <div class="weui-cell__hd">
                            <img src="/image/elevator_opening.gif?timestamp=1474260060 alt="" style="width:20px;margin-right:5px;display:block">
                        </div>
                        <div class="weui-cell__bd">G1092039 &nbsp;&nbsp;&nbsp;&nbsp; 苏州大学理工楼 西楼</div>
                        <div class="weui-cell__ft">
                            <input class="weui-switch" type="checkbox">
                        </div>
                    </div>
            <a href="javascript:void(0);" class="weui-cell weui-cell_link">
                <div class="weui-cell__bd">查看更多</div>
            </a>
    </div>
</div>
</@layout.weixinMasterTemplate>