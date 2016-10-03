<#import "weixinLayout.ftl" as layout />
<#include '../func.ftl'>

<#assign headerContent>
</#assign>

<@layout.weixinMasterTemplate initScript='js/weixin/elevatorDetail.js' header=headerContent title='电梯详细信息'>
<#--${loginUser.userName}-->


<div class="weui_cells weui_cells_access" id="elevatorDetail">
    <input type="hidden" id="elevatorid" v-model="id" value="${detail.id}">
    <input type="hidden" id="userid" v-model="userid" value="${userid}">
    <a class="weui_cell" href="javascript:;">
        <div class="weui_cell_bd weui_cell_primary">
            <p>电梯编号:</p>
        </div>
        <div class="weui_cell_ft">${detail.number!"Anonymous"}</div>
    </a>
    <a class="weui_cell" href="javascript:;">
        <div class="weui_cell_bd weui_cell_primary">
            <p>电梯品牌:</p>
        </div>
        <div class="weui_cell_ft">${detail.brandName!"Anonymous"}</div>
    </a>
    <a class="weui_cell" href="javascript:;">
        <div class="weui_cell_bd weui_cell_primary">
            <p>地  址:</p>
        </div>
        <div class="weui_cell_ft">${detail.address!"Anonymous"}</div>
    </a>
    <a class="weui_cell" href="javascript:;">
        <div class="weui_cell_bd weui_cell_primary">
            <p>使用单位:</p>
        </div>
        <div class="weui_cell_ft">${detail.userCompanyName!"Anonymous"}</div>
    </a>
    <a class="weui_cell" href="javascript:;">
        <div class="weui_cell_bd weui_cell_primary">
            <p>物业单位:</p>
        </div>
        <div class="weui_cell_ft">${detail.ownerCompanyId!"Anonymous"}</div>
    </a>
    <a class="weui_cell" href="javascript:;">
        <div class="weui_cell_bd weui_cell_primary">
            <p>维保单位:</p>
        </div>
        <div class="weui_cell_ft">${detail.maintainerName!"Anonymous"}</div>
    </a>
    <a class="weui_cell" href="javascript:;">
        <div class="weui_cell_bd weui_cell_primary">
            <p>安装单位:</p>
        </div>
        <div class="weui_cell_ft">${detail.installCompanyName!"Anonymous"}</div>
    </a>
    <a class="weui_cell" href="javascript:;">
        <div class="weui_cell_bd weui_cell_primary">
            <p>24小时值班电话:</p>
        </div>
        <div class="weui_cell_ft">${detail.dutyPhone!"Anonymous"}</div>
    </a>
    <a class="weui_cell" href="javascript:;">
        <div class="weui_cell_bd weui_cell_primary">
            <p>年检日期:</p>
        </div>
        <div class="weui_cell_ft">${detail.lastCheckDate!"Anonymous"}</div>
    </a>
    <a class="weui_cell" href="javascript:;">
        <div class="weui_cell_bd weui_cell_primary">
            <p>上次维保日期:</p>
        </div>
        <div class="weui_cell_ft">${detail.lastUpkeepTime!"Anonymous"}</div>
    </a>
    <a class="weui_cell" href="javascript:;">
        <div class="weui_cell_bd weui_cell_primary">
            <p>下次维保日期:</p>
        </div>
        <div class="weui_cell_ft">${detail.lastUpkeepTime!"Anonymous"}</div>
    </a>
    <a class="weui_cell" href="javascript:;">
          <p style="color: green;"> 点击查看维保计划</p>
    </a>

    <a class="weui_cell" href="javascript:;">
        <div class="weui_cell_bd weui_cell_primary">
            <p>维保人员:</p>
        </div>
        <div class="weui_cell_ft">${detail.maintenanceName!"Anonymous"}</div>
    </a>
    <#--toastBeigng-->
    <div id="toast" style="display: none;">
        <div class="weui_mask_transparent"></div>
        <div class="weui_toast">
            <i class="weui_icon_toast"></i>
            <p id="message" class="weui_toast_content">已完成</p>
        </div>
    </div>
    <#--toastEnd-->
    <#--rpeortdialogbeigng-->
    <div class="weui_dialog_confirm" id="reportDialog" style="display:none;">
        <div class="weui_mask"></div>
        <div class="weui_dialog">
            <div class="weui_dialog_hd"><strong class="weui_dialog_title">填写报障信息</strong></div>
            <div class="weui_dialog_bd"><div class="weui_cells weui_cells_form">
                    <div class="weui_cell">
                        <div class="weui_cell_hd"><label class="weui_label">电梯编号</label></div>
                        <div class="weui_cell_bd weui_cell_primary">
                            <input class="weui_input" type="number" v-model="report.number"  pattern="[0-9]*" placeholder="请输入电梯编号">
                        </div>
                    </div>
                    <div class="weui_cell weui_vcode">
                        <div class="weui_cell_hd"><label class="weui_label">被困人员数量</label></div>
                        <div class="weui_cell_bd weui_cell_primary">
                            <input class="weui_input" type="number" v-model="report.peoples"  placeholder="请输入被困人员数量">
                        </div>
                    </div>
                    <div class="weui_cell">
                        <div class="weui_cell_hd"><label class="weui_label">电梯目前状况</label></div>
                        <div class="weui_cell_bd weui_cell_primary">
                            <input class="weui_input" type="number" pattern="[0-9]*" v-model="report.currentstatus"  placeholder="电梯目前状况">
                        </div>
                    </div>
                    <div class="weui_cell">
                        <div class="weui_cell_hd"><label class="weui_label">联系方式</label></div>
                        <div class="weui_cell_bd weui_cell_primary">
                            <input class="weui_input" type="number" pattern="[0-9]*" v-model="report.telephone" placeholder="请输入联系方式">
                        </div>
                    </div>
                </div>
            </div>
            <div class="weui_dialog_ft">
                <a href="javascript:;" class="weui_btn_dialog default" v-on:click="reportCancel">取消</a>
                <a href="javascript:;" class="weui_btn_dialog primary" v-on:click="reportComfirm">确定</a>
            </div>
        </div>
    </div>
<#--reportdialogbeigng-->
<#--rpeortdialogbeigng-->
    <div class="weui_dialog_confirm" id="complaintDialog" style="display:none;">
        <div class="weui_mask"></div>
        <div class="weui_dialog">
            <div class="weui_dialog_hd"><strong class="weui_dialog_title">填写投诉信息</strong></div>
            <div class="weui_dialog_bd"><div class="weui_cells weui_cells_form">
                <div class="weui_cell">
                    <div class="weui_cell_hd"><label class="weui_label">电梯编号</label></div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <input class="weui_input" type="number" v-model="complaint.number" pattern="[0-9]*" placeholder="请输入电梯编号">
                    </div>
                </div>
                <div class="weui_cell weui_vcode">
                    <div class="weui_cell_hd"><label class="weui_label">投诉原因</label></div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <input class="weui_input" type="number" v-model="complaint.reason" placeholder="请输入投诉原因">
                    </div>
                </div>
                <div class="weui_cell">
                    <div class="weui_cell_hd"><label class="weui_label">电梯目前状况</label></div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <input class="weui_input" type="number" pattern="[0-9]*" v-model="complaint.currentstatus" placeholder="电梯目前状况">
                    </div>
                </div>
                <div class="weui_cell">
                    <div class="weui_cell_hd"><label class="weui_label">联系方式</label></div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <input class="weui_input" type="number" pattern="[0-9]*" v-model="complaint.telephone" placeholder="请输入联系方式">
                    </div>
                </div>
            </div>
            </div>
            <div class="weui_dialog_ft">
                <a href="javascript:;" class="weui_btn_dialog default" v-on:click="complaintCancel">取消</a>
                <a href="javascript:;" class="weui_btn_dialog primary" v-on:click="complaintComfirm">确定</a>
            </div>
        </div>
    </div>
<#--reportdialogbeigng-->
            <a href="javascript:;" class="weui_btn weui_btn_mini weui_btn_primary" id="showToast"v-on:click="attention" style="width: 32%">关注</a>
            <a href="javascript:;" class="weui_btn weui_btn_mini weui_btn_primary" id="showReportDialog" v-on:click="reportBarrier" style="width: 32%">报障</a>
            <a href="javascript:;" class="weui_btn weui_btn_mini weui_btn_primary" id="showComplaintDialog" v-on:click="complaint" style="width: 32%">投诉</a>


</div>
</div>
<script type="text/html" id="tpl_dialog">
    <div class="hd">
        <h1 class="page_title">Dialog</h1>
    </div>
    <div class="bd spacing">
        <a href="javascript:;" class="weui_btn weui_btn_primary" id="showDialog1">点击弹出Dialog样式一</a>
        <a href="javascript:;" class="weui_btn weui_btn_primary" id="showDialog2">点击弹出Dialog样式二</a>
    </div>
    <!--BEGIN dialog1-->
    <div class="weui_dialog_confirm" id="dialog1" style="display: none;">
        <div class="weui_mask"></div>
        <div class="weui_dialog">
            <div class="weui_dialog_hd"><strong class="weui_dialog_title">弹窗标题</strong></div>
            <div class="weui_dialog_bd">自定义弹窗内容，居左对齐显示，告知需要确认的信息等</div>
            <div class="weui_dialog_ft">
                <a href="javascript:;" class="weui_btn_dialog default">取消</a>
                <a href="javascript:;" class="weui_btn_dialog primary">确定</a>
            </div>
        </div>
    </div>
    <!--END dialog1-->
    <!--BEGIN dialog2-->
    <div class="weui_dialog_alert" id="dialog2" style="display: none;">
        <div class="weui_mask"></div>
        <div class="weui_dialog">
            <div class="weui_dialog_hd"><strong class="weui_dialog_title">弹窗标题</strong></div>
            <div class="weui_dialog_bd">弹窗内容，告知当前页面信息等</div>
            <div class="weui_dialog_ft">
                <a href="javascript:;" class="weui_btn_dialog primary">确定</a>
            </div>
        </div>
    </div>
    <!--END dialog2-->
</script>
</@layout.weixinMasterTemplate>