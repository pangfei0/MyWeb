<#import "archiveLayout.ftl" as layout />
<#include "func.ftl">

<#assign headerContent>

<link rel="stylesheet" href="/lib/sweetalert/sweetalert.css">
</#assign>

<@layout.archiveTemplate initScript="js/elevatorBrandCoU" header=headerContent title="">
<div class="panel panel-default" id="elevatorBrandCoU">
    <div class="panel-heading" v-html="isEdit ? '${t("editElevatorBrand")}':'${t("newElevatorBrand")}'"></div>
    <div class="panel-body">
        <div class="form-horizontal">
            <div class="form-group">
                <label class="col-sm-4 control-label">${t("numberOfElevatorBrand")}:</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" v-model="elevatorBrand.number" name="number">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-4 control-label">${t("nameOfElevatorBrand")}:</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" v-model="elevatorBrand.name" name="name">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-4 control-label">${t("proTypeOfElevatorBrand")}:</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" v-model="elevatorBrand.protocolType" name="protocolType">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-3 col-sm-4">
                    <#if hasPermission("elevatorBrand:edit")>
                    <button type="submit" class="btn btn-primary" v-on:click="confirm">${t("confirm")}</button>
                    <button type="button" class="btn btn-default col-sm-offset-2" onclick="window.location.href='/elevatorBrand'">${t("cancel")}</button>
                    </#if>
                    <#if hasPermission("elevatorBrand:delete")>
                        <button type="button" class="btn btn-default col-sm-offset-2" v-if="isEdit" v-on:click="deleteIt">${t("delete")}</button>
                    </#if>
                </div>
            </div>
        </div>

    </div>


</div>



</@layout.archiveTemplate>