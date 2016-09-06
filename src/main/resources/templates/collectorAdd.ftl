<#--<#import "maintainLayout.ftl" as layout >-->
<#--<#include "func.ftl" />-->

<#--<#assign headerContent>-->
<#--<link rel="stylesheet" href="/lib/sweetalert/sweetalert.css">-->
<#--</#assign>-->

<#--<@layout.maintainTemplate initScript='js/workBillCoU' header=headerContent title=''>-->
    <#--<div class="panel panel-default" id="workBillCoU">-->
        <#--&lt;#&ndash;<div class="panel-heading" v-html="isEdit ? '${t("editWorkBill")}':'${t("newWorkBill")}'"></div>&ndash;&gt;-->
        <#--<div class="panel-body">-->
            <#--<div class="form-horizontal">-->
                <#--&lt;#&ndash;<div class="form-group">&ndash;&gt;-->
                    <#--&lt;#&ndash;<input type="hidden" value="{{workBill.faultDescription}" />&ndash;&gt;-->
                <#--&lt;#&ndash;</div>&ndash;&gt;-->
                <#--&lt;#&ndash;<div class="form-group">&ndash;&gt;-->
                    <#--&lt;#&ndash;<input type="hidden" value="{{workBill.faultPerson}" />&ndash;&gt;-->
                <#--&lt;#&ndash;</div>&ndash;&gt;-->
                <#--&lt;#&ndash;<div class="form-group">&ndash;&gt;-->
                    <#--&lt;#&ndash;<input type="hidden" value="{{workBill.faultPersonTelephone}" />&ndash;&gt;-->
                <#--&lt;#&ndash;</div>&ndash;&gt;-->
                <#--<div class="form-group">-->
                    <#--<label class="col-sm-2 control-label">智能硬件注册码:</label>-->
                    <#--<div class="col-sm-10">-->
                    <#--<input type="text" class="form-control"  id="billNumber" v-html="workBill.billNumber">-->
                    <#--</div>-->
                 <#--</div>-->
                <#--<div class="form-group">-->
                    <#--<label class="col-sm-2 control-label">电梯</label>-->
                    <#--<div class="col-sm-10">-->
                        <#--<input type="text" class="form-control" autocomplete="off" id="elevatorInput" data-provide="typeahead" v-model="workBill.elevatorNumber" placeholder="请输入电梯工号">-->
                    <#--</div>-->
                <#--</div>-->
                <#--<div  class="form-group">-->
                    <#--<div  class="col-sm-offset-1 col-sm-2">-->
                        <#--<button id="nowsend" type="button" class="btn btn-success" v-on:click="nowSend"> <i class="icon-book"></i> 确定</button>-->
                    <#--</div>-->
                    <#--<div  class="col-sm-offset-1 col-sm-2">-->
                        <#--<button type="button" class="btn btn-success" v-on:click="cancel">取消</button>-->
                    <#--</div>-->
                <#--</div>-->
            <#--</div>-->
        <#--</div>-->
    <#--</div>-->
<#--</@layout.maintainTemplate>-->