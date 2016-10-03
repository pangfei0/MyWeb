<#import "../weixinLayout.ftl" as layout />
<#include '../../func.ftl'>

<#assign headerContent>
</#assign>

<@layout.weixinMasterTemplate initScript='js/weixin/visitorSearch.js' header=headerContent title='电梯搜索'>
<#--${loginUser.userName}-->
<div id="elevatorContainer">

    <div class="page searchbar js_show">
        <div class="page__bd">
            <div class="weui-search-bar" id="search_bar">
                <form class="weui-search-bar__form">
                    <div class="weui-search-bar__box">
                        <i class="weui-icon-search"></i>
                        <input type="search" class="weui-search-bar__input" id="search_input" placeholder="请输入电梯编号" required=""  v-model="number">
                        <a href="javascript:" class="weui-icon-clear" id="search_clear"></a>
                    </div>
                    <label for="search_input" class="weui-search-bar__label" id="search_text" v-on:click="elevatorSearch">
                        <i class="weui-icon-search"></i>
                        <span>请输入电梯编号</span>
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
<div class="weui_panel_bd">
    <div class="weui_media_box weui_media_text">
        <h4 class="weui_media_title">已关注电梯</h4>
    </div>
</div>
<div id="favElevator">
    <div class="weui-cells weui-cells_form">
    <#list favlist as fav >
      <a href="/weixin/achieve/elevatorDetail/${fav.id}" class="weui_media_box weui_media_appmsg">
            <div class="weui-cell weui-cell_switch">
            	<div class="weui-cell__hd">
            		<img src="/image/elevator_opening.gif?timestamp=1474260060 alt="" style="width:20px;margin-right:5px;display:block">
            	</div>
                <div class="weui-cell__bd">${fav.number}&nbsp;&nbsp; ${fav.address+" "+fav.alias}</div>
                <div class="weui-cell__ft">
                    <input class="weui-switch" type="checkbox">
                </div>
            </div>
      </a>
    </#list>
    </div>
</div>
<div class="weui_panel_bd">
    <div class="weui_media_box weui_media_text">
        <h4 class="weui_media_title">当前电梯</h4>
    </div>
</div>
<div class="weui_panel_bd" id="searchElevator" >
    <#list elevators as elevator >
    <a href="/weixin/achieve/elevatorDetail/${elevator.id}" class="weui_media_box weui_media_appmsg">
        <div class="weui-cell weui-cell_switch">
            <div class="weui-cell__hd">
                <img src="/image/elevator_opening.gif?timestamp=1474260060 alt="" style="width:20px;margin-right:5px;display:block">
            </div>
            <div class="weui-cell__bd">${elevator.number} &nbsp;&nbsp;${elevator.address+" "+elevator.alias}</div>
            <#--<div class="weui-cell__ft">-->
                <#--<input class="weui-switch" type="checkbox">-->
            <#--</div>-->
        </div>
    </a>
    </#list>

</div>
</div>

</@layout.weixinMasterTemplate>