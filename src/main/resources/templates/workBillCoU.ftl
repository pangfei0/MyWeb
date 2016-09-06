<#import "maintainLayout.ftl" as layout >
<#include "func.ftl" />

<#assign headerContent>
<link rel="stylesheet" href="/lib/sweetalert/sweetalert.css">
</#assign>

<@layout.maintainTemplate initScript='js/workBillCoU' header=headerContent title=''>
<div class="panel panel-default" id="workBillCoU">
    <div class="panel-heading" v-html="isEdit ? '${t("editWorkBill")}':'${t("newWorkBill")}'"></div>
    <div class="panel-body">
        <div class="form-horizontal">
            <div class="form-group">
                <input type="hidden" value="{{workBill.faultDescription}"/>
            </div>
            <div class="form-group">
                <input type="hidden" value="{{workBill.faultPerson}"/>
            </div>
            <div class="form-group">
                <input type="hidden" value="{{workBill.faultPersonTelephone}"/>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">工单编号:</label>

                <div class="col-sm-10">
                    <input type="text" class="form-control" id="billNumber" v-model="workBill.billNumber">
                </div>
            </div>
        <#--<div class="form-group">-->
        <#--<label class="col-sm-2 control-label">工单类型:</label>-->
        <#--<div class="col-sm-10">-->
        <#--<select class="form-control" id="billCategory" v-model="workBill.billCategory">-->
        <#--<option value="10">维修工单</option>-->
        <#--<option value="20">保养工单</option>-->
        <#--<option value="30">急修工单</option>-->
        <#--</select>-->
        <#--</div>-->
        <#--</div>-->
            <div class="form-group">
                <label class="col-sm-2 control-label">工单模板</label>

                <div class="col-sm-10">
                    <select class="form-control" id="billModel" v-model="workBill.billModel" onchange="changeDiv(this.value);">
                        <option value="0">半月保养工单模板</option>
                        <option value="10">季度保养工单模板</option>
                        <option value="20">半年保养工单模板</option>
                        <option value="30">年度保养工单模板</option>
                        <option value="40">急修工单模板</option>
                        <option value="50">维修工单模板</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">电梯</label>

                <div class="col-sm-10">
                    <input type="text" class="form-control" autocomplete="off" id="elevatorInput" value="${elevatorNumbers}"
                           data-provide="typeahead" v-model="workBill.elevatorNumber" placeholder="请输入电梯工号">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">员工</label>

                <div class="col-sm-10">
                    <select class="form-control" v-model="workBill.maintenanceId">
                        <option v-for="maintenance in maintenances" value="{{maintenance.id}}">
                            {{maintenance.name}}/{{maintenance.number}}
                        </option>
                    </select>
                </div>
            </div>
            <div style="display: none;" id="faultDate">
            <div class="form-group">
                <label class="col-sm-2 control-label">报障人员</label>

                <div class="col-sm-10">
                    <input type="text" class="form-control" autocomplete="off" id="faultPerson"
                           data-provide="typeahead" v-model="workBill.faultPerson" placeholder="请输入报障人员">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">联系方式</label>

                <div class="col-sm-10">
                    <input type="text" class="form-control" autocomplete="off" id="faultPersonTelephone"
                           data-provide="typeahead" v-model="workBill.faultPersonTelephone" placeholder="请输入联系方式">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">故障描述</label>

                <div class="col-sm-10">
                    <input type="text" class="form-control" autocomplete="off" id="faultDescription"
                           data-provide="typeahead" v-model="workBill.faultDescription" placeholder="请输入故障描述">
                </div>
            </div>
            </div>
            <div class="form-group">
                <#if hasPermission("workbillBoard:send")>
                    <div class=" col-sm-offset-1 col-sm-2">
                        <button id="aftersend" type="button" class="btn btn-success" v-on:click="afterSend"><i
                                class="icon-download"></i> 稍后派发（更新）
                        </button>
                    </div>
                    <div class="col-sm-offset-1 col-sm-2">
                        <button id="nowsend" type="button" class="btn btn-success" v-on:click="nowSend"><i
                                class="icon-book"></i> 立即派发
                        </button>
                    </div>
                    <div class="col-sm-offset-1 col-sm-2">
                        <button id="refusesend" type="button" class="btn btn-success" v-on:click="resendother"> 工单改派
                        </button>
                    </div>
                    <div class="col-sm-offset-1 col-sm-2">
                        <button id="cancel" type="button" class="btn btn-success" v-on:click="cancel">取消</button>
                    </div>
                </#if>
            </div>
        </div>
    </div>
</div>
</@layout.maintainTemplate>