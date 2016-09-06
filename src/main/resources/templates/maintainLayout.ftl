<#import "layout.ftl" as layout />
<#include 'func.ftl'>

<#macro maintainTemplate initScript="" header="" footer="" title="">

    <#assign headerContent>
    <link rel="stylesheet" href="/css/maintainLayout.css">
    ${header}
    </#assign>

    <@layout.masterTemplate header=headerContent footer=footerContent initScript=initScript title=title>
    <div class="maintain-container">
        <div class="sidebar">
            <ul>
                <#if hasPermission("personnelBoard:view")>
                    <li class="maintenance-personnel-board"><a href="/maintenancePersonnelBoard">${t("maintenancePersonnelBoard")}</a></li>
                </#if>
                <#if hasPermission("workbillBoard:view")>
                    <li class="work-bill-board"><a href="/workBillBoard">${t("wokBillBoard")}</a></li>
                </#if>
                <#if hasPermission("reportBoard:view")>
                    <li class="report-board"><a href="/reportBoard">${t("reportBoard")}</a></li>
                </#if>
                <#if hasPermission("upkeepContract:search")>
                    <li class="upkeep-contract"><a href="/upkeepContractBoard">${t("upkeepContract")}</a></li>
                </#if>
            </ul>
        </div>
        <div class="content">
            <#nested />
        </div>
    </div>
    </@layout.masterTemplate>
</#macro>
