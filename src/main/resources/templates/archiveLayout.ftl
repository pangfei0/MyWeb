<#import "layout.ftl" as layout />
<#include 'func.ftl'>

<#macro archiveTemplate initScript="" header="" title="">

    <#assign headerContent>
    <link rel="stylesheet" href="/css/archiveLayout.css">
    ${header}
    </#assign>

    <@layout.masterTemplate header=headerContent initScript=initScript title=title>
    <div class="archive-container">
        <div class="sidebar">
            <ul>
                <#--<#if hasPermission("premise:view")>-->
                    <#--<li class="premise"><a href="/premise">${t("premise")}</a></li>-->
                <#--</#if>-->
                <#if hasPermission("companyMaintain:view")>
                    <li class="companyMaintain"><a href="/companyMaintain">${t("recordsCompanies")}</a></li>
                </#if>

            <#--<li class="use-company"><a href="/company/30">${t("useCompanies")}</a></li>-->
            <#--<li class="maintainer-company"><a href="/company/20">${t("maintainers")}</a></li>-->
                <#if hasPermission("maintainPerson:view")>
                    <li class="maintainer-employee"><a href="/maintainerEmployee">${t("maintainerEmployees")}</a></li>
                </#if>
                <#if hasPermission("elevator:view")>
                    <li class="elevator"><a href="/elevator">${t("elevators")}</a></li>
                </#if>
                <#if hasPermission("upkeepContract:view")>
                    <li class="upkeep-contract"><a href="/upkeepContract">${t("upkeepContracts")}</a></li>
                </#if>
                <#if hasPermission("upkeepContractPlanBath:view")>
                    <li class="maintenanceBath-plan"><a href="/maintenancePlanBath">维保批次</a></li>
                </#if>
                <#if hasPermission("upkeepContractPlan:view")>
                    <li class="maintenance-plan"><a href="/maintenancePlan">维保计划</a></li>
                </#if>
                <#if hasPermission("intelHardware:view")>
                    <li class="intel-hardware"><a href="/collector">${t("intelHardwares")}</a></li>
                </#if>

            <#--<li class="work-bill"><a href="">${t("workBills")}</a></li>-->

                <#if hasPermission("elevatorBrand:view")>
                    <li class="elevator-brand"><a href="/elevatorBrand">${t("elevatorBrands")}</a></li>
                </#if>
            </ul>
        </div>
        <div class="content">
            <#nested />
        </div>
    </div>
    </@layout.masterTemplate>
</#macro>
