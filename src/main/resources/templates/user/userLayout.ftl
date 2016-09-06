<#import "../layout.ftl" as layout />
<#include '../func.ftl'>

<#macro userTemplate initScript="" header="">

    <#assign headerContent>
    <link rel="stylesheet" href="/css/user/userLayout.css">
    ${header}
    </#assign>

    <@layout.masterTemplate header=headerContent initScript=initScript>
    <div class="user-container">
        <div class="sidebar">
            <ul>
                <li class="user-info"><a href="/user/userInfo">${t("userInfo")}</a></li>
                <li class="change-password"><a href="/user/changePassword">${t("changePassword")}</a></li>
                <#if hasPermission("user:view")>
                    <li class="user-authorization"><a href="/user/authorization">${t("userManagement")}</a></li>
                </#if>
                <#if hasPermission("organization:view")>
                    <li class="organization"><a href="/organization/view">${t("organizationalStructure")}</a></li>
                </#if>
                <#if hasPermission("role:view")>
                    <li class="role"><a href="/rolePermission/view">${t("rolePermission")}</a></li>
                </#if>
                <li class="role"><a href="/user/seniorUserApply">申请高级用户</a></li>
                <#if hasPermission("auditPermission:view")>
                    <li class="role"><a href="/user/auditPermission">审核用户</a></li>
                </#if>
            <#--<#if hasPermission("unitRole:view")>-->
            <#--<li class="unitRole"><a href="/user/unitRole">${t("unitRole")}</a></li>-->
            <#--</#if>-->
            </ul>
        </div>
        <div class="content">
            <#nested />
        </div>
    </div>
    </@layout.masterTemplate>
</#macro>
