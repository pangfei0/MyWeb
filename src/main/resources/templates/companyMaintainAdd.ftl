<#import "archiveLayout.ftl" as layout />
<#include "func.ftl">

<#assign headerContent>
<link rel="stylesheet" href="/lib/sweetalert/sweetalert.css">
</#assign>

<@layout.archiveTemplate initScript="js/companyMaintainAdd" header=headerContent title='${t("recordsCompaniesInfo")}'>
<div class="panel panel-default" id="companyMaintainAdd">
    <div class="panel-heading" title="${t("newCompany")}"></div>
    <div class="panel-body">
        <form class="form-horizontal">
            <div class="form-group">
                <label class="col-sm-4 control-label">${t("nameOfCompany")}</label>

                <div class="col-sm-4">
                    <input type="text" id="companyName" style="width:95%;display:inline;" class="form-control" v-model="company.name" name="name">
                    <label style="color:red;">*</label>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-4 control-label">${t("typeOfCompany")}</label>
                <div class="col-sm-4">
                    <select id="companyType" style="width:95%;display:inline;" class="form-control" v-model="company.type">
                        <option value="-1" selected="selected">请选择公司类型</option>
                        <#list companyTypes as companyType>
                            <option value="${companyType.id}">${companyType.name}</option>
                        </#list>
                    </select>
                    <label style="color:red;">*</label>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-4 control-label">公司固话</label>

                <div class="col-sm-4">
                    <input type="text" class="form-control" v-model="company.phone" id="companyPhone">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-4 control-label">${t("addressOfCompany")}</label>

                <div class="col-sm-4">
                    <input type="text" class="form-control" v-model="company.address" name="address">
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
                    <input type="text" class="form-control" v-model="company.mobile" id="mobile">
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-3 col-sm-4">
                    <button type="button" class="btn btn-primary" v-on:click="confirm">${t("confirm")}</button>
                    <button type="button" class="btn btn-default col-sm-offset-2"
                            onclick="window.location.href='/companyMaintain'">${t("cancel")}</button>
                </div>
            </div>
        </form>
    </div>
</div>
</@layout.archiveTemplate>