<#import "layout.ftl" as layout />
<#include 'func.ftl'>

<#assign headerContent>
<link rel="stylesheet" href="/css/faultElevators.css">
<link rel="stylesheet" type="text/css" href="/lib/kkpager/kkpager_orange.css"/>
</#assign>

<@layout.masterTemplate initScript="js/faultElevators" header=headerContent title='${t("homePage")}'>
<div id="statusTab">
    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#handled" aria-controls="home" role="tab" data-toggle="tab" onclick="addFaultElevatorsToMap(20)">${t("ongoing2")}</a></li>
        <li role="presentation"><a href="#unHandled" aria-controls="unHandled" role="tab" data-toggle="tab" onclick="addFaultElevatorsToMap(10)">${t("untreated")}</a></li>
    </ul>
    <!-- Tab panes -->
    <div class="tab-content">
        <div role="tabpanel" class="tab-pane fade in active" id="handled">
            <div class="list-cell" v-for="ele in handledFaultEles">
                <div class="left-index" v-html="$index + 1"></div>
                <div data-ele-id="{{ele.id}}" data-lat="{{ele.lat}}" data-lng="{{ele.lng}}" onclick="navToMap('{{ele.id}}','{{ele.lat}}','{{ele.lng}}')">
                    <ul>
                        <li v-html="'工单：' + ele.billNumber"></li>
                        <li v-html="'工单状态：' + ele.billStatus"></li>
                        <li v-html="'处理人：' + ele.billDealer"></li>
                        <li v-html="'电梯：' + ele.number"></li>
                        <li v-html="'地址：' + ele.alias"></li>
                    </ul>
                </div>
            </div>
            <#--<div class="pagination">-->
                <#--<div id="handled-kkpager"></div>-->
            <#--</div>-->
        </div>
        <div role="tabpanel" class="tab-pane fade" id="unHandled">
            <div class="list-cell" v-for="ele in unHandledfaultEles">
                <div class="left-index" v-html="$index + 1"></div>
                <div data-ele-id="{{ele.id}}" data-lat="{{ele.lat}}" data-lng="{{ele.lng}}" onclick="navToMap('{{ele.id}}','{{ele.lat}}','{{ele.lng}}')">
                    <ul>
                        <li v-html="'电梯：' + ele.number"></li>
                        <li v-html="'地址：' + ele.alias"></li>
                        <li v-html="'故障时间：' + ele.faultTime"></li>
                        <li v-html="'故障代码：' + ele.faultCode"></li>
                    </ul>
                </div>
            </div>
            <#--<div class="pagination">-->
                <#--<div id="unHandled-kkpager"></div>-->
            <#--</div>-->
        </div>
    </div>
    <div class="pagination">
        <div id="kkpager"></div>
    </div>
</div>
<div id="map"></div>
</@layout.masterTemplate>