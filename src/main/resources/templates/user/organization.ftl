<#import "userLayout.ftl" as layout />
<#include "../func.ftl">

<#assign headerContent>
<link rel="stylesheet" href="/css/user/organization.css">
<link rel="stylesheet" href="/lib/bootstrap-table/bootstrap-table.css">
<link rel="stylesheet" href="/lib/sweetalert/sweetalert.css">
<link rel="stylesheet" href="/lib/ztree/zTreeStyle/zTreeStyle.css">
</#assign>

<@layout.userTemplate initScript="js/user/organization" header=headerContent>
<div class="panel panel-default">
    <div class="panel-heading">${t("organizationalStructure")}</div>
    <div class="panel-body">
        <ul id="tree" class="ztree"></ul>
    </div>
</div>
</@layout.userTemplate>