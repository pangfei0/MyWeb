<#import "layout.ftl" as layout />
<#include "func.ftl">

<#assign headerContent>
<link rel="stylesheet" href="/css/rescue.css" xmlns="http://www.w3.org/1999/html">
<link rel="stylesheet" href="/lib/sweetalert/sweetalert.css">
<link rel="stylesheet" href="/lib/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/lib/kkpager/kkpager_orange.css"/>
</#assign>

<#assign footerContent>
<div id="choosePersonModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">${t('pleaseChooseMaintenance')}</h4>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <label for="recipient-name" class="control-label">维保人员:</label>
                        <select class="form-control" id="personSelect">
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="message-text" class="control-label">其他人员:</label>
                        <input type="text" class="form-control" id="otherPerson" autocomplete="off" data-provide="typeahead">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="sendBill()">发送急修</button>
            </div>
        </div>
    </div>
</div>
<div id="sendBillModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="secondModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="secondModalLabel">${t('sendRecueBill')}</h4>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <label for="recipient-name" class="control-label">故障等级:</label>
                        <span class="">应急救援</span>
                    </div>
                    <div class="form-group">
                        <label for="recipient-name" class="control-label">电梯:</label>
                        <span class="" id="number">电梯名测试</span>
                    </div>
                    <div class="form-group">
                        <label for="recipient-name" class="control-label">电梯地址:</label>
                        <span class="" id="elevatorAddress"></span>
                    </div>
                    <div class="form-group">
                        <label for="message-text" class="control-label">电梯类型:</label>
                        <span class="" id="elevatorType"></span>
                    </div>
                    <div class="form-group">
                        <label for="message-text" class="control-label">报障人员:</label>
                        <input type="text" class="form-group-sm" id="warnPerson"></input>
                    </div>
                    <div class="form-group">
                        <label for="message-text" class="control-label">联系方式:</label>
                        <input type="text" class="form-group-sm" id="warnPersonPhone"></input>
                    </div>
                    <div class="form-group">
                        <label for="message-text" class="control-label">故障描述:</label>
                        <input type="text" class="form-group-sm" id="faultDescription"></input>
                    </div>
                    <div class="form-group">
                        <label for="message-text" class="control-label">维修员:</label>
                        <span class="" id="selectedMaintenance"></span>
                    </div>
                    <div class="form-group">
                        <label for="message-text" class="control-label">联系方式:</label>
                        <span class="" id="maintenancePhone"></span>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="sendToMaintenance()">发送急修</button>
            </div>
        </div>
    </div>
</div>
</#assign>

<@layout.masterTemplate initScript="js/rescue" footer=footerContent header=headerContent title='${t("gridRescue")}'>
<div class="left-list">
    <ul>${t("rescueBill")}</ul>
    <ul class="order-list">
        <table>
            <tr width="100%" style="color:#4f7ab9">
            <#--<td width="50%" align="left" v-html="bill.billNumber"></td>-->
                <td width="40%" align="center" >电梯工号</td>
            <#--</tr>-->
            <#--<tr width="100%">-->
                <td width="30%" align="center">状态</td>
                <td width="30%" align="center">维修人员</td>
            </tr>
        </table>
        <li v-for="bill in rescueWorkBills" data-bill-id="{{bill.id}}">
            <a onclick="navToMap('{{bill.id}}')">
                <table>
                    <#--<tr width="100%">-->
                    <#--&lt;#&ndash;<td width="50%" align="left" v-html="bill.billNumber"></td>&ndash;&gt;-->
                        <#--<td width="40%" align="left" >电梯工号</td>-->
                    <#--&lt;#&ndash;</tr>&ndash;&gt;-->
                    <#--&lt;#&ndash;<tr width="100%">&ndash;&gt;-->
                        <#--<td width="30%" align="left">状态</td>-->
                        <#--<td width="30%" align="left">维修人员</td>-->
                    <#--</tr>-->
                    <tr width="100%">
                        <#--<td width="50%" align="left" v-html="bill.billNumber"></td>-->
                        <td width="40%" align="left" v-html="bill.elevatorNumber"></td>
                    <#--</tr>-->
                    <#--<tr width="100%">-->
                        <td width="30%" align="right" v-html="bill.billstatus"></td>
                        <td width="30%" align="right" v-html="bill.maintenanceName"></td>
                    </tr>
                </table>
            </a>
        </li>
    </ul>
    <div class="pagination">
        <div id="kkpager"></div>
    </div>
</div>
<div id="map">
</div>
</@layout.masterTemplate>