<#import "archiveLayout.ftl" as layout />
<#include 'func.ftl'>

<#assign headerContent>

<link rel="stylesheet" href="/lib/sweetalert/sweetalert.css">
</#assign>

<@layout.archiveTemplate initScript='js/companyCoU' header=headerContent title='${t("recordsCompaniesInfo")}'>
<input id="type" type="hidden" value="${type}">
<div class="panel panel-default" id="companyCoU">
    <div class="panel-heading" v-html="isEdit ? '${t("editCompany")}':'${t("newCompany")}'"></div>
    <div class="panel-body">
        <form class="form-horizontal">
            <div class="form-group">
                <label class="col-sm-4 control-label">${t("nameOfCompany")}</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" v-model="company.name" name="name">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-4 control-label">${t("addressOfCompany")}</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" v-model="company.address" name="address">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-4 control-label">${t("phoneOfCompany")}</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" v-model="company.phone" name="phone">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-4 control-label">${t("contactOfCompany")}</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" v-model="company.contact" name="contact">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-4 control-label">${t("mobileOfCompany")}</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" v-model="company.mobile" name="mobile">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-3 col-sm-4">
                    <button type="submit" class="btn btn-primary" v-on:click="confirm">${t("confirm")}</button>
                    <button type="button" class="btn btn-default col-sm-offset-2" onclick="window.location.href='/company/'+ ${type}">${t("cancel")}</button>
                    <button type="button" class="btn btn-default col-sm-offset-2" v-if="isEdit" v-on:click="deleteIt">${t("delete")}</button>
                </div>
            </div>

        </form>


    </div>






</div>





</@layout.archiveTemplate>