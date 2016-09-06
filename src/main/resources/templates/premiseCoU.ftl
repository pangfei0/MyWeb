<#import "archiveLayout.ftl" as layout />
<#include 'func.ftl'>

<#assign headerContent>
<link rel="stylesheet" href="/css/premiseCoU.css">
<link rel="stylesheet" href="/lib/bootstrap-table/bootstrap-table.css">
<link rel="stylesheet" href="/lib/sweetalert/sweetalert.css">
</#assign>

<@layout.archiveTemplate initScript='js/premiseCoU' header=headerContent title='${t("recordsEditPremise")}'>
<div class="panel panel-default" id="premiseCoU">
    <div class="panel-heading" v-html="isEdit ? '${t("editPremise")}':'${t("newPremise")}'"></div>
    <div class="panel-body">
        <div class="form-horizontal">
            <div class="form-group">
                <label class="col-sm-4 control-label">${t("nameOfPremise")}:</label>

                <div class="col-sm-4">
                    <input type="text" class="form-control" v-model="premise.name" name="name">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-4 control-label">${t("addressOfPremise")}:</label>

                <div class="col-sm-4">
                    <input type="text" class="form-control" v-model="premise.address" name="address">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-4 control-label">${t("maintainer")}:</label>

                <div class="col-sm-4">
                    <select class="form-control" v-model="premise.maintainer">
                        <option value="-1" selected="selected">${t("pleaseSelectMaintainer")}</option>
                        <#list maintainers as maintainer>
                            <option value="${maintainer.id}">${maintainer.name}</option>
                        </#list>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-3 col-sm-4">
                    <#if hasPermission("premise:edit")>
                        <button type="submit" class="btn btn-primary" v-on:click="confirm">${t("confirm")}</button>
                    </#if>
                    <#if hasPermission("premise:delete")>
                        <button type="button" class="btn btn-default col-sm-offset-2" v-if="isEdit"
                                v-on:click="deleteIt">${t("delete")}</button>
                    </#if>
                    <button type="button" class="btn btn-default col-sm-offset-2"
                            onclick="window.location.href='/premise'">${t("cancel")}</button>
                </div>
            </div>
        </div>
    </div>
</div>
</@layout.archiveTemplate>