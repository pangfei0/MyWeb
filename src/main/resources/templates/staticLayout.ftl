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
                <#--<#if hasPermission("companyMaintain:view")>-->
                    <li class="workBillStatic"><a href="/workBillStatic">台量</a></li>
                <#--</#if>-->
                <#--<#if hasPermission("maintainPerson:view")>-->
                    <li class="satisfactionStatic"><a href="/satisfactionStatic">满意度</a></li>
                <#--</#if>-->
                <#--<#if hasPermission("upkeepContractPlanBath:view")>-->
                    <li class="resultStatic"><a href="/resultStatic">业绩</a></li>
                <#--</#if>-->
                <#--<#if hasPermission("upkeepContractPlan:view")>-->
                    <li class="faultStatic"><a href="/faultStatic">故障</a></li>
                <#--</#if>-->

                    <li class="staticPic"><a href="/staticPicOne">统计图展示壹</a></li>
                    <li class="staticPic"><a href="/staticPicTwo">统计图展示贰</a></li>
                    <li class="staticPic"><a href="/staticPicThree">统计图展示叁</a></li>
                    <li class="staticPic"><a href="/staticPicFour">统计图展示肆</a></li>
            </ul>
        </div>
        <div class="content">
            <#nested />
        </div>
    </div>
    </@layout.masterTemplate>
</#macro>
