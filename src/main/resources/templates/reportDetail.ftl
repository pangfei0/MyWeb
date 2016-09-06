<#import "maintainLayout.ftl" as layout />
<#include "func.ftl"/>

<#assign headerContent>
<link rel="stylesheet" href="/css/archive.css">
<link rel="stylesheet" href="/lib/bootstrap-table/bootstrap-table.css">
<link rel="stylesheet" href="/lib/bootstrap/css/bootstrap.min.css">
</#assign>

<#assign footerContent>

</#assign>

<@layout.maintainTemplate initScript="js/reportDetail" header=headerContent footer=footerContent title='${t("serviceManagement")}'>
<div class="panel panel-default" id="reportDetail">
    <div class="panel-heading">报告详细</div>
    <div class="panel-body">
     <table class="table">
       <tr>
           <td>
          <label>设备号：</label>
               <label>${report.data.elevatornumber!"无"}</label>
           </td>
           <td>
               <label>合同编号：</label>
               <label>${report.data.upkeepcontractnumber!"无"}</label>

           </td>

       </tr>

         <tr>
             <td>
                 <label>完成时间：</label>
                 <label>${report.data.completetime!"无"}</label>
             </td>
             <td>
                 <label>开始时间：</label>
                 <label>${report.data.starttime!"无"}</label>

             </td>

         </tr>
         <tr>
             <td>
                 <label>维保维修人员：</label>
                 <label>${report.data.maintenancename!"无"}</label>
             </td>
             <td>
                 <label>现场描述：</label>
                 <label>${report.data.localdescription!"无"}</label>

             </td>

         </tr>
         <tr>
             <td><label>使用单位：</label>
             <label>${report.data.company!"无"}</label>
             </td>

         </tr>
         <tr>
             <td>
                 <label>故障性质：</label>
                 <label>${report.data.faultQuality!"无"}</label>
             </td>
             <td>
                 <label>处理结果：</label>
                 <label>${report.data.resultv!"无"}</label>

             </td>

         </tr>
         <tr>
             <#if report.data.reportCategory==30>
                 <td>
                     <label>进入时间：</label>
                     <label>${report.entertime!"无"}</label>
                 </td>
             </#if>
             <#if report.data.reportCategory==20>
                 <td>
                     <label>后续工单：</label>
                     <label>${report.afterbill!"无"}</label>
                 </td>
             </#if>


             <td>
                 <label>服务态度：</label>
                 <label>${report.data.serviceattidute!"无"}</label>

             </td>

         </tr>
         <tr>
             <td>
                 <label>维护安全和环境：</label>
                 <label>${report.data.envandsafe!"无"}</label>
             </td>
             <td>
                 <label>服务水平：</label>
                 <label>${report.data.servicelevel!"无"}</label>

             </td>

         </tr>
         <tr>
             <td>
                 <label>是否解决问题：</label>
                 <label>${report.data.resolvequestion!"无"}</label>
             </td>
             <td>
                 <label>建议：</label>
                 <label>${report.data.suggestions!"无"}</label>

             </td>

         </tr>
         <tr>
             <td>
                 <label>签名:</label>
                 <label>${report.data.sign!"无"}</label>
             </td>
             <td>
                 <label>报告时间：</label>
                 <label>${report.data.reportime!"无"}</label>

             </td>

         </tr>
     </table>
        <div  class="form-group">
            <div  class=" col-sm-offset-5 col-sm-2">
                <button id="aftersend" type="button" class="btn btn-success" v-on:click="back"> <i class="icon-download"></i>返回</button>
            </div>
        </div>
    </div>
</div>
</@layout.maintainTemplate>