<#import "maintainLayout.ftl" as layout />
<#include "func.ftl"/>

<#assign headerContent>
<link rel="stylesheet" href="/css/archive.css">
<link rel="stylesheet" href="/lib/bootstrap-table/bootstrap-table.css">
<link rel="stylesheet" href="/lib/bootstrap/css/bootstrap.min.css">
</#assign>

<#assign footerContent>

</#assign>

<@layout.maintainTemplate initScript="js/workBillDetail" header=headerContent footer=footerContent title='${t("serviceManagement")}'>
<div class="panel panel-default" id="workBillDetail">
    <div class="panel-heading">工单详细</div>
    <div class="panel-body">
        <#if workBill.data.billCategory!=20>
         <table class="table">
          <tr>
              <td>
                  <label>工单编号：</label>
                  <label>${workBill.data.billnumber!"无"}</label>
              </td>
              <td>
                  <label>工单类别：</label>
                  <#if workBill.data.billCategory==10>
                     <label>维修工单</label>
                  </#if>
                  <#if workBill.data.billCategory==30>
                      <label>急修工单</label>
                  </#if>
              </td>

              <td>
                  <label>发起时间：</label>
                  <label>${workBill.data.createtime!"无"}</label>
              </td>

          </tr>
             <tr>
                 <td>
                     <label>电梯类型：</label>
                     <label>${workBill.data.elevatortype!"无"}</label>
                 </td>
                 <td>
                     <label>用户名称：</label>
                     <label>${workBill.data.username!"无"}</label>
                 </td>

             </tr>
             <tr>
                 <td>
                     <label>电梯设备号：</label>
                     <label>${workBill.data.elevatornumber!"无"}</label>
                 </td>
                 <td>
                     <label>保养合同号:</label>
                     <label>${workBill.data.upkeepcontractnumber!"无"}</label>
                 </td>

             </tr>

             <tr>
                 <td>
                     <label>维保单位：</label>
                     <label>${workBill.data.maintainer!"无"}</label>
                 </td>

                     <td>
                 <#if workBill.data.billCategory==10>
                         <label>开始时间：</label>
                 </#if>
                 <#if workBill.data.billCategory==30>
                     <label>响应时间：</label>
                 </#if>
                         <label>${workBill.data.starttime!"无"}</label>
                     </td>
             </tr>
             <tr>

                 <td colspan="2">
                     <label>故障描述：</label>
                     <label>${workBill.data.faultDescription!"无"}</label>
                 </td>

             </tr>
             <tr>

                 <td colspan="2">
                     <label>现场描述：</label>
                     <label>${workBill.data.localdescription!"无"}</label>
                 </td>

             </tr>

             <tr>
                 <td>
                     <label>采取措施：</label>
                     <label>${workBill.data.makesteps!"无"}</label>
                 </td>
                 <td>
                     <label>该次故障性质：</label>
                     <label>${workBill.data.faulyquality!"无"}</label>
                 </td>

             </tr>
             <tr>
                 <td colspan="2">
                     <label>处理结果：</label>
                     <label>${workBill.data.result!"无"}</label>
                 </td>

             </tr>
             <tr>
                 <td>
                     <label>完成时间</label>
                     <label>${workBill.data.completetime!"无"}</label>
                 </td>
                 <td>
                     <label>维保人员：</label>
                     <label>${workBill.data.maintenancePersonal!"无"}</label>
                 </td>
             </tr>
         </table>
        </#if>
        <#if workBill.data.billCategory==20>
            <table class="table">
                <tr>
                    <td>
                        <label>工单编号：</label>
                        <label>${workBill.data.billnumber!"无"}</label>
                    </td>
                    <td>
                        <label>工单类别：</label>
                            <label>保养工单</label>
                    </td>
                    <td>
                        <label>保养类别：</label>
                        <label>${workBill.data.billModelName!"无"}</label>
                    </td>

                </tr>
                <tr>
                    <td>
                        <label>客户名称：</label>
                        <label>${workBill.data.usercompany!"无"}</label>
                    </td>
                    <td>
                        <label>单位地址：</label>
                        <label>${workBill.data.address!"无"}</label>
                    </td>

                </tr>
                <tr>
                    <td>
                        <label>联系方式：</label>
                        <label>${workBill.data.phone!"无"}</label>
                    </td>
                    <td>
                        <label>现场联系人:</label>
                        <label>${workBill.data.contact!"无"}</label>
                    </td>

                </tr>

                <tr>
                    <td>
                        <label>联系电话：</label>
                        <label>${workBill.data.mobile!"无"}</label>
                    </td>

                    <td>
                        <label>电梯型号：</label>
                        <label>${workBill.data.elevatortype!"无"}</label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>控制方式：</label>
                        <label>${workBill.data.elevatortype!"无"}</label>
                    </td>

                    <td>
                        <label>电梯设备号：</label>
                        <label>${workBill.data.elevatornumber!"无"}</label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>保养合同号：</label>
                        <label>${workBill.data.upkeepcontractnumber!"无"}</label>
                    </td>

                    <td>
                        <label>此次保养名称：</label>
                        <label>${workBill.data.maintainname!"无"}</label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>出场日期：</label>
                        <label>${workBill.data.productiontime!"无"}</label>
                    </td>

                    <td>
                        <label>额定负荷：</label>
                        <label>${workBill.data.ratedweight!"无"}</label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>额定速度：</label>
                        <label>${workBill.data.ratedspeed!"无"}</label>
                    </td>

                    <td>
                        <label>层站数：</label>
                        <label>${workBill.data.floor!"无"}</label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>提升高度：</label>
                        <label>${workBill.data.hoistingHeight!"无"}</label>
                    </td>

                    <td>
                        <label>使用地点：</label>
                        <label>${workBill.data.useaddress!"无"}</label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>性质：</label>
                        <label>${workBill.data.maintainProperty!"无"}</label>
                    </td>

                    <td>
                        <label>年检状态：</label>
                        <label>${workBill.data.checkstatus!"无"}</label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>开始时间：</label>
                        <label>${workBill.data.starttime!"无"}</label>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <label>未决事宜：</label>
                        <label>${workBill.data.result!"无"}</label>
                    </td>
                </tr>

                <tr>
                    <td>
                        <label>维保人员：</label>
                        <label>${workBill.data.maintenancePersonal!"无"}</label>
                    </td>
                    <td>
                        <label>维保项目：</label>
                        <a >点击查看</a>
                        <#--<label>${workBill.data.maintainprograms!"无"}</label>-->

                    </td>
                </tr>
            </table>
        </#if>

        <div  class="form-group">
            <div  class=" col-sm-offset-5 col-sm-2">
                <button id="aftersend" type="button" class="btn btn-success" v-on:click="back"> <i class="icon-download"></i>返回</button>
            </div>
        </div>
    </div>
</div>
</@layout.maintainTemplate>