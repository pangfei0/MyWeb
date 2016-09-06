<#import "maintainLayout.ftl" as layout />
<#include "func.ftl"/>

<#assign headerContent>
<link rel="stylesheet" href="/css/archive.css">
<link rel="stylesheet" href="/lib/bootstrap-table/bootstrap-table.css">
<link rel="stylesheet" href="/lib/bootstrap/css/bootstrap.min.css">
</#assign>

<#assign footerContent>

</#assign>

<@layout.maintainTemplate initScript="js/maintenancePersonnelInfo" header=headerContent footer=footerContent title='${t("serviceManagement")}'>
<div id="infoDetail">
<div class="panel panel-default" >
    <div class="panel-heading">选择时间段</div>
    <div class="panel-body">
        <div class="form-horizontal">
            <input type="hidden" v-model="scope.id" value="${id!''}">
            <div class="form-group">
                <label class="col-sm-2 control-label">开始时间:</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control form_datetime"  id="starttime" v-model="scope.starttime">
                </div>
                <label class="col-sm-2 control-label">结束时间:</label>
                <div class="col-sm-3">
                    <input type="text" id="endtime"  class="form-control" v-model="scope.endtime" >
                </div>
            </div>
            <div  class="form-group">
                <div  class=" col-sm-offset-4 col-sm-2">
                    <button  type="button" class="btn btn-success" v-on:click="search"> <i class="icon-download"></i>查询综合信息</button>
                </div>
                <div  class=" col-sm-offset-1 col-sm-2">
                    <button  type="button" class="btn btn-success" v-on:click="back"> <i class="icon-download"></i> 返回</button>
                </div>
            </div>
        </div>

</div>
<div class="panel panel-default" id="info" style="display: none;">
    <div class="panel-heading">{{info.name}}综合信息</div>
    <div class="panel-body">
            <table class="table">
                <tr>
                    <td>
                        <label>姓名：</label>
                        <label>{{info.name}}</label>
                    </td>

                </tr>
                <tr>
                    <td>
                        <label>在岗时长：</label>
                        <label>{{info.onduty}}   (分钟)</label>
                    </td>


                </tr>
                <tr>
                    <td>
                        <label>待命时长：</label>
                        <label>{{info.standby}}  (分钟)</label>
                    </td>

                </tr>

                <tr>
                    <td>
                        <label>完成保养工单：</label>
                        <label>{{info.wbbills}}  (单)</label>
                    </td>


                </tr>
                <tr>
                    <td>
                        <label>完成维修工单：</label>
                        <label>{{info.wxbills}}  (单)</label>
                    </td>


                </tr>
                <tr>
                    <td>
                        <label>完成急修工单：</label>
                        <label>{{info.jxbills}} (单)</label>
                    </td>


                </tr>
                <tr>
                <td>
                    <label>日均在岗时长：</label>
                    <label>{{info.avgOnduty}}  (分钟)</label>
                </td>


            </tr>
                <tr>
                    <td>
                        <label>日均待命时长：</label>
                        <label>{{info.avgStandBy}}  (分钟)</label>
                    </td>


                </tr>
                <tr>
                    <td>
                        <label>日均完成工单：</label>
                        <label>{{info.avgbills}}  (单)</label>
                    </td>


                </tr>
            </table>

        <div  class="form-group">
            <div  class=" col-sm-offset-4 col-sm-2">
                <button  type="button" class="btn btn-success" v-on:click="export"> <i class="icon-download"></i>导出</button>
            </div>
            <div  class=" col-sm-offset-1 col-sm-2">
                <button  type="button" class="btn btn-success" v-on:click="export"> <i class="icon-download"></i>导出</button>

            </div>
        </div>
    </div>
</div>
    </div>
</@layout.maintainTemplate>