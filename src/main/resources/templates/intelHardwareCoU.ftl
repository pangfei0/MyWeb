<#import "archiveLayout.ftl" as layout />
<#include "func.ftl">

<#assign headerContent>
<link rel="stylesheet" href="/lib/sweetalert/sweetalert.css">
</#assign>

<@layout.archiveTemplate initScript="js/intelHardwareCoU" header=headerContent title="">
<div class="panel panel-default" id="intelHardwareCoU">
    <div class="panel-heading" v-html="isEdit ? '${t("editIntelHardware")}':'${t("newIntelHardware")}'"></div>
    <div class="panel-body">
        <div class="form-horizontal">
            <div class="form-group">
                <label class="col-sm-4 control-label">${t("intelHardwareNumberOfElevator")}:</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" v-model="collector.intelHardwareNumber" name="number">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-4 control-label">${t("numberOfElevator")}:</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="contractNo" data-provide="typeahead" placeholder="${t("numberOfElevator")}">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-3 col-sm-4">
                <#--<#if hasPermission("intelHardware:edit")>-->
                    <button type="submit" class="btn btn-primary" v-on:click="confirm">${t("confirm")}</button>
                    <button type="button" class="btn btn-default col-sm-offset-2" onclick="window.location.href='/collector'">${t("cancel")}</button>
                <#--</#if>-->
                <#--<#if hasPermission("intelHardware:delete")>-->
                    <button type="button" class="btn btn-default col-sm-offset-2" v-if="isEdit" v-on:click="deleteIt">${t("delete")}</button>
                <#--</#if>-->
                </div>
            </div>
        </div>
    </div>
</div>
</@layout.archiveTemplate>
