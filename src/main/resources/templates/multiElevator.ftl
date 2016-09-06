<#import "layout.ftl" as layout />
<#include 'func.ftl'>

<#assign headerContent>
<link rel="stylesheet" href="/css/realtime.css">
<link rel="stylesheet" type="text/css" href="/lib/kkpager/kkpager_black.css"/>
</#assign>

<@layout.masterTemplate header=headerContent initScript="js/multiElevator" title="${t('multiElevator')}">
<div class="clearfix realtime-container" id="elevator">


    <div class="right-content">
        <div class="elevator-content clearfix">

        </div>
        <div class="pagination">
            <div id="kkpager"></div>
        </div>
    </div>
</div>
</@layout.masterTemplate>