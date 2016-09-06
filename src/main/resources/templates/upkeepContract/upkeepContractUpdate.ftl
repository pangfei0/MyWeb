<#import "../archiveLayout.ftl" as layout />
<#include "../func.ftl">

<#assign headerContent>
<link rel="stylesheet" href="/lib/datetimepicker/bootstrap-datetimepicker.css">
<link rel="stylesheet" href="/lib/sweetalert/sweetalert.css">
</#assign>

<@layout.archiveTemplate initScript="js/upkeepContract/upkeepContractUpdate" header=headerContent title="">
<div id="upkeepContractCoU">
        <div class="panel panel-default">
            <div class="panel-heading">
            ${t("baseInfo")}
                <#if hasPermission("upkeepContract:edit")>
                    <button type="button" class="btn btn-default pull-right" id="baseInfoEditButton"
                            v-on:click="baseInfoEdit">${t("edit")}</button>
                </#if>
            </div>
            <div class="panel-body">
                <div id="baseInfoShow">
                    <div class="row">
                        <label class="col-md-2 control-label">${t("contractNo")}:</label>
                        <div class="col-md-4"  v-html="upkeepContract.baseInfo.number"></div>
                        <label class="col-md-2 control-label">${t("contractNature")}:</label>
                        <div class="col-md-4"  v-html="upkeepContract.baseInfo.property"></div>
                    </div>
                    <div class="row">
                        <label class="col-md-2 control-label">${t("ContractStatus")}:</label>
                        <div class="col-md-4"  v-html="upkeepContract.baseInfo.status"></div>
                        <label class="col-md-2 control-label">${t("ContractSource")}:</label>
                        <div class="col-md-4"  v-html="upkeepContract.baseInfo.source"></div>
                    </div>
                    <div class="row">
                        <label class="col-md-2 control-label">${t("ownerFullName")}:</label>
                        <div class="col-md-4"  v-html="upkeepContract.baseInfo.ownerFullname"></div>
                        <label class="col-md-2 control-label">${t("ownerName")}:</label>
                        <div class="col-md-4"  v-html="upkeepContract.baseInfo.ownerShortname"></div>
                    </div>
                    <div class="row">
                        <label class="col-md-2 control-label">${t("partyA")}:</label>
                        <div class="col-md-4" v-html="upkeepContract.baseInfo.partyAName" ></div>
                        <label class="col-md-2 control-label">${t("partyB")}:</label>
                        <div class="col-md-4"  v-html="upkeepContract.baseInfo.partyBName"></div>
                    </div>
                    <div class="row">
                        <label class="col-md-2 control-label">${t("contactsPartyA")}:</label>
                        <div class="col-md-4"  v-html="upkeepContract.baseInfo.partyAContact"></div>
                        <label class="col-md-2 control-label">${t("contactsPartyB")}:</label>
                        <div class="col-md-4"  v-html="upkeepContract.baseInfo.partyBContact"></div>
                    </div>
                    <div class="row">
                        <label class="col-md-2 control-label">${t("ContactPhonePartyA")}:</label>
                        <div class="col-md-4"  v-html="upkeepContract.baseInfo.partyATelephone"></div>
                        <label class="col-md-2 control-label">${t("ContactPhonePartyB")}:</label>
                        <div class="col-md-4"  v-html="upkeepContract.baseInfo.partyBTelephone"></div>
                    </div>
                    <div class="row">
                        <label class="col-md-2 control-label">${t("addressPartyA")}:</label>
                        <div class="col-md-4"  v-html="upkeepContract.baseInfo.partyAAddress"></div>
                        <label class="col-md-2 control-label">${t("addressPartyB")}:</label>
                        <div class="col-md-4"  v-html="upkeepContract.baseInfo.partyBAddress"></div>
                    </div>
                    <div class="row">
                        <label class="col-md-2 control-label">${t("effectiveDate")}:</label>
                        <div class="col-md-4"  v-html="upkeepContract.baseInfo.beginDate"></div>
                        <label class="col-md-2 control-label">${t("dueDate")}:</label>
                        <div class="col-md-4"  v-html="upkeepContract.baseInfo.endDate"></div>
                    </div>
                    <div class="row">
                        <label class="col-md-2 control-label">${t("amount")}:</label>
                        <div class="col-md-4"  v-html="upkeepContract.baseInfo.value"></div>
                        <label class="col-md-2 control-label">${t("paymentTerms")}:</label>
                        <div class="col-md-4"  v-html="upkeepContract.baseInfo.paymentTerm"></div>
                    </div>
                    <div class="row">
                        <label class="col-md-2 control-label">${t("contractPeriod")}:</label>
                        <div class="col-md-4"  v-html="upkeepContract.baseInfo.duration"></div>
                        <label class="col-md-2 control-label">${t("elevatorNumbers")}:</label>
                        <div class="col-md-4"  v-html="upkeepContract.baseInfo.remainElevator"></div>

                    </div>
                </div>
                <div id="baseInfoUpdate" style="display: none;">
                    <div class="row">
                        <label class="col-md-2 control-label">${t("contractNo")}:</label>
                        <div class="col-md-4" v-html="upkeepContract.baseInfo.number">
                        </div>
                        <label class="col-md-2 control-label">${t("contractNature")}:</label>
                        <div class="col-md-4">
                            <select class="form-control" id="search_property" v-model="upkeepContract.baseInfo.property">
                                <option value="免保">免保</option>
                                <option value="有偿保养">有偿保养</option>
                                <option value="配件">配件</option>
                            </select>
                        </div>
                    </div>
                    <div class="row">
                        <label class ="col-md-2 control-label">${t("ContractStatus")}:</label>
                        <div class="col-md-4">
                            <select class="form-control" id="search_status" v-model="upkeepContract.baseInfo.status">
                                <option value="有效">有效</option>
                                <option value="有尾款">有尾款</option>
                                <option value="关闭">关闭</option>
                            </select>
                        </div>
                        <label class="col-md-2 control-label">${t("ContractSource")}:</label>
                        <div class="col-md-4">
                            <select class="form-control" id="search_source" v-model="upkeepContract.baseInfo.source">
                                <option value="免保转有偿">免保转有偿</option>
                                <option value="新增">新增</option>
                                <option value="修理">修理</option>
                                <option value="有偿续签">有偿续签</option>
                            </select>
                        </div>
                    </div>
                    <div class="row">
                        <label class="col-md-2 control-label">${t('ownerFullName')}:</label>
                        <div class="col-md-4">
                            <input type="text" id="ownerFullname" class="form-control" v-model="upkeepContract.baseInfo.ownerFullname">
                        </div>
                        <label class="col-md-2 control-label">${t("ownerName")}:</label>
                        <div class="col-md-4">
                            <input type="text" id="ownerShortname" class="form-control" v-model="upkeepContract.baseInfo.ownerShortname">
                        </div>
                    </div>
                    <#--<div class="row">-->

                        <#--<label class="col-md-2 control-label">${t("partyA")}</label>-->
                        <#--<div class="col-md-4">-->
                            <#--<select class="form-control" id="partyAId"  v-model="upkeepContract.baseInfo.partyAId">-->
                                <#--<option value="-1" selected="selected">请选择甲方公司</option>-->
                                <#--<option v-for="company in partyA" value="{{company.id}}">-->
                                    <#--{{ company.name }}-->
                                <#--</option>-->
                            <#--</select>-->
                        <#--</div>-->
                        <#--<label class="col-md-2 control-label">${t("partyB")}</label>-->
                        <#--<div class="col-md-4" >-->
                            <#--<select class="form-control" id="partyBId"   v-model="upkeepContract.baseInfo.partyBId">-->
                                <#--<option value="-1" selected="selected">请选择乙方公司</option>-->
                            <#--<option v-for="company in partyB" value="{{company.id}}">-->
                                <#--{{ company.name }}-->
                            <#--</option>-->
                            <#--</select>-->
                        <#--</div>-->
                    <#--</div>-->
                    <div class="row">
                        <label class="col-md-2 control-label">${t("effectiveDate")}:</label>
                        <div class="col-md-4">
                            <input type="text" id="beginDate" class="form-control" v-model="upkeepContract.baseInfo.beginDate">
                        </div>
                        <label class="col-md-2 control-label">${t("dueDate")}:</label>
                        <div class="col-md-4">
                            <input type="text" id="endDate" class="form-control" v-model="upkeepContract.baseInfo.endDate">
                        </div>
                    </div>
                    <div class="row">
                        <label class="col-md-2 control-label">${t("amount")}:</label>
                        <div class="col-md-4">
                            <input type="text" class="form-control" id="amount" v-model="upkeepContract.baseInfo.value">
                        </div>
                        <label class="col-md-2 control-label">${t("paymentTerms")}:</label>
                        <div class="col-md-4">
                            <input type="text" id="paymentTerm" class="form-control" v-model="upkeepContract.baseInfo.paymentTerm">
                        </div>
                    </div>
                    <div class="col-sm-offset-4 col-sm-4">
                        <button type="button" class="btn btn-primary"  v-on:click="baseInfoConfirm">${t("confirm")}</button>
                        <button type="button" class="btn btn-success col-sm-offset-2"  v-on:click="baseInfoCancel">${t("cancel")}</button>
                        <#if hasPermission("upkeepContract:delete")>
                            <button type="button" class="btn btn-danger col-sm-offset-2"  v-on:click="deleteIt">${t("delete")}</button>
                        </#if>

                    </div>
                </div>
            </div>
        </div>

        <div class="panel panel-default">
            <div class="panel-heading">
            ${t("stateInfo")}
                <#if hasPermission("upkeepContract:edit")>
                <button type="button" class="btn btn-default pull-right" id="stateInfoEditButton" v-on:click="stateInfoEdit">${t("edit")}</button>
                </#if>
            </div>
            <div class="panel-body">
               <div id="stateInfoShow">
                   <div class="row">
                       <label class="col-sm-2 control-label">${t("dueDateCountdown")}:</label>
                       <div class="col-sm-4" >{{upkeepContract.stateInfo.remainDays}}${t("day")}</div>
                       <label class="col-sm-2 control-label">${t("renewalStatus")}:</label>
                       <div class="col-sm-4" v-html="upkeepContract.stateInfo.renewStatus"></div>
                   </div>
                   <div class="row">
                       <label class="col-sm-2 control-label">${t("billAmount")}:</label>
                       <div class="col-sm-4" v-html="upkeepContract.stateInfo.allBillingValue"></div>
                       <label class="col-sm-2 control-label">${t("needInvoice")}:</label>
                       <div class="col-sm-4" v-html="upkeepContract.stateInfo.needCollectValue"></div>
                   </div>
                   <div class="row">
                       <label class="col-sm-2 control-label">${t("accountAmount")}:</label>
                       <div class="col-sm-4" >{{upkeepContract.stateInfo.allCollectValue}}</div>
                       <label class="col-sm-2 control-label">${t("accountReceivable")}:</label>
                       <div class="col-sm-4" v-html="upkeepContract.stateInfo.receivableValue"></div>
                   </div>
                   <div class="row">
                       <label class="col-sm-2 control-label">${t("accountAge")}:</label>
                       <div class="col-sm-4" >{{upkeepContract.stateInfo.age}}${t("day")}</div>
                   </div>
               </div>
                <div id="stateInfoUpdate" style="display: none;">
                    <div class="row">
                        <div class="col-sm-3">${t("renewalStatus")}:</div>
                        <div class="col-md-3" >${t("needInvoice")}: </div>
                        <div class="col-md-3" >${t("accountReceivable")}: </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-3">
                            <select class="form-control" id="search_renewStatus">
                            <option value="-1" selected="selected">续签状态</option>
                            <option value="续签">续签</option>
                            <option value="丢失">丢失</option>
                            </select>
                        </div>
                        <div class="col-sm-3"> <input type="text" class="form-control"  v-model="upkeepContract.stateInfo.needCollectValue"></div>
                        <div class="col-sm-3"> <input type="text" class="form-control"  v-model="upkeepContract.stateInfo.receivableValue"></div>
                    </div>
                <div class="form-group">
                        <div class="col-sm-offset-4 col-sm-4">
                            <button type="button" class="btn btn-primary"  v-on:click="stateInfoConfirm">${t("confirm")}</button>
                            <button type="button" class="btn btn-success col-sm-offset-2"  v-on:click="stateInfoCancel">${t("cancel")}</button>
                        </div>
                    </div>
                </div>

            </div>


        <div class="panel panel-default">
                <div class="panel-heading">
                    ${t("invoiceRecord")}
                        <#if hasPermission("upkeepContract:edit")>
                            <button type="button" class="btn btn-default pull-right" id="stateInfoEditButton"
                                    v-on:click="billInfoEdit">${t("new")}</button>
                        </#if>
                </div>
                <div class="panel-body">
                    <div id="billInfoShow">
                        <table class="table-condensed col-sm-offset-2" v-if="upkeepContract.billInfo.length  >= 1 ? true: false">
                            <tr>
                                <td class="col-sm-3">${t("invoiceNo")}</td>
                                <td class="col-sm-3">${t("invoiceMan")}</td>
                                <td class="col-sm-4">${t("invoiceDate")}</td>
                                <td class="col-sm-2">${t("invoiceAmount")}</td>
                            </tr>
                            <tr v-for="bill in upkeepContract.billInfo">
                                <td class="col-sm-3" v-html="bill.billNO"></td>
                                <td class="col-sm-3" v-html="bill.name"></td>
                                <td class="col-sm-4" v-html="bill.billingDate"></td>
                                <td class="col-sm-2" >{{bill.billValue}}${t("yuan")}</td>
                            </tr>
                        </table>
                    </div>
                    <div id="billInfoUpdate" style="display: none;">
                        <div class="row">
                            <div class="col-sm-3">${t("invoiceNo")}</div>
                            <div class="col-sm-3">${t("invoiceDate")}</div>
                            <div class="col-sm-3">${t("invoiceAmount")}</div>

                        </div>
                        <div class="row">
                            <div class="col-sm-3"><input type="text" class="form-control" v-model="newBill.billNO"></div>
                            <div class="col-sm-3"><input type="text" id="billingDate" class="form-control"  v-model="newBill.billingDate"></div>
                            <div class="col-sm-3"><input type="text" class="form-control"  v-model="newBill.billValue"></div>

                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-4 col-sm-4">
                                <button type="button" class="btn btn-primary"  v-on:click="billInfoConfirm">${t("confirm")}</button>
                                <button type="button" class="btn btn-success col-sm-offset-2"  v-on:click="billInfoCancel">${t("cancel")}</button>
                            </div>
                        </div>
                    </div>
                </div>
        </div>

        <div class="panel panel-default">
                <div class="panel-heading">
                ${t("collectionRecord")}
                    <#if hasPermission("upkeepContract:edit")>
                        <button type="button" class="btn btn-default pull-right" id="stateInfoEditButton"
                                v-on:click="collectInfoEdit">${t("new")}</button>
                    </#if>
                </div>
                <div class="panel-body">
                    <div id="collectInfoShow">
                        <table class="table-condensed col-sm-offset-2" v-if="upkeepContract.collectInfo.length  >= 1 ? true: false">
                            <tr>
                                <td class="col-sm-3">${t("invoiceNo")}</td>
                                <td class="col-sm-3">${t("collectionMan")}</td>
                                <td class="col-sm-4">${t("collectionDate")}</td>
                                <td class="col-sm-2">${t("collectionAmount")}</td>
                            </tr>
                            <tr v-for="collect in upkeepContract.collectInfo">
                                <td class="col-sm-3" v-html="collect.billNo"></td>
                                <td class="col-sm-3" v-html="collect.createBy"></td>
                                <td class="col-sm-4" v-html="collect.collectionDate"></td>
                                <td class="col-sm-2" >{{collect.collectingValue}}${t("yuan")}</td>
                            </tr>
                        </table>
                    </div>
                    <div id="collectInfoUpdate" style="display: none;">
                        <div class="row">
                            <div class="col-sm-3">${t("invoiceNo")}</div>
                            <div class="col-sm-3">${t("collectionDate")}</div>
                            <div class="col-sm-3">${t("collectionAmount")}</div>
                        </div>
                        <div class="row">
                            <div class="col-sm-3"><input type="text" class="form-control" v-model="newCollect.billNo"></div>
                            <div class="col-sm-3"><input type="text" id="collectInfoDate" class="form-control"  v-model="newCollect.collectionDate"></div>
                            <div class="col-sm-3"><input type="text" class="form-control"  v-model="newCollect.collectingValue"></div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-4 col-sm-4">
                                <button type="button" class="btn btn-primary"  v-on:click="collectInfoConfirm">${t("confirm")}</button>
                                <button type="button" class="btn btn-success col-sm-offset-2"  v-on:click="collectInfoCancel">${t("cancel")}</button>
                            </div>
                        </div>
                    </div>
                </div>
        </div>

        <div class="panel panel-default">
            <div class="panel-heading">
            ${t("elevatorList")}
                <#if hasPermission("upkeepContract:edit")>
                    <button type="button" class="btn btn-default pull-right" id="stateInfoEditButton"
                            v-on:click="elevatorInfoEdit">${t("addElevator")}</button>
                </#if>
            </div>
            <div id="elevatorInfoShow" class="panel-body">
                <table class="table table-condensed table-bordered">
                    <thead>
                    <tr class="info" v-if="upkeepContract.elevatorInfo.length >=1 ? true:false">
                        <td>${t("factoryNo")}</td>
                        <td>${t("elevatorType")}</td>
                        <td>${t("manufacturerName")}</td>
                        <td>${t("lastCheckDate")}</td>
                        <td>${t("singleElevatorUpkeepCost")}</td>
                        <#if hasPermission("upkeepContract:edit")>
                            <td>${t("operation")}</td>
                        </#if>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="elevator in upkeepContract.elevatorInfo | orderBy 'number'">
                        <td>{{elevator.number}}</td>
                        <td>{{elevator.elevatorType}}</td>
                        <td>{{elevator.manufacturer}}</td>
                        <td>{{elevator.lastcheckDate}}</td>
                        <td>{{elevator.avgValue}}</td>
                        <#if hasPermission("upkeepContract:edit")>
                            <td>
                                <button type="button" class="btn btn-success col-sm-offset-2"
                                        @click="removeElevator(elevator.id)">${t("remove")}</button>
                            </td>
                        </#if>
                    </tr>
                    </tbody>
                    <tfoot>
                    <tr>
                        <td colspan="6">
                            <div class="pull-left">
                                <button class="btn btn-default" v-on:click="refresh">刷新</button>
                            </div>
                            <div class="pull-right">
                                <boot-page :async="false" :data="lists" :lens="lenArr" :page-len="pageLen" :param="param"></boot-page>
                            </div>
                        </td>
                    </tr>
                    </tfoot>
                </table>
            </div>
            <div id="elevatorInfoUpdate" class="panel-body" style="display: none;">
                <div class="panel-body">
                    <form class="search-container search-form">
                        <div class="search-field">
                            <span>项目名称:</span>
                            <input type="text" style="width: 120px" id="projectName" data-provide="typeahead" autocomplete="off" class="form-control"/>
                            <input type="hidden" id="partyA"/>
                            <input type="hidden" id="partyB"/>
                        </div>
                        <div class="search-field">
                            <span>${t("numberOfElevator")}:</span>
                            <input type="text" style="width:120px;" id="number" class="form-control"/>
                        </div>
                        <button type="button" class="btn btn-primary btn-search" v-on:click="search"><i class="fa fa-search"></i> ${t("search")}</button>
                        <button type="button" class="btn btn-primary btn-search" v-on:click="elevatorCancel"><i class="fa fa-search"></i> ${t("cancel")}</button>
                        <button type="button" class="btn btn-primary btn-search" v-on:click="addElevator"><i class="fa fa-search"></i> ${t("addElevator")}</button>
                    </form>
                </div>
                <div class="panel-body">
                    <table id="elevatorDataTable">
                    </table>
                </div>
            </div>
            </div>
</div>
</div>
</@layout.archiveTemplate>