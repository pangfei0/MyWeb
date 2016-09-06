<#import "layout.ftl" as layout />
<#include 'func.ftl'>

<#assign headerContent>
<link rel="stylesheet" href="/css/faultElevators.css">
</#assign>

<@layout.masterTemplate initScript="js/all" header=headerContent title='${t("homePage")}'>
<div id="map"></div>
</@layout.masterTemplate>