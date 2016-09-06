<#import "../archiveLayout.ftl" as layout />
<#include "../func.ftl">

<#assign headerContent>
<link rel="stylesheet" href="/lib/datetimepicker/bootstrap-datetimepicker.css">
<link rel="stylesheet" href="/lib/sweetalert/sweetalert.css">
</#assign>

<@layout.archiveTemplate initScript="js/upkeepContract/upkeepContractAdd" header=headerContent title="添加合同">
<div id="upkeepContractAdd">
    <div class="panel panel-default">
        <div class="panel-heading">
            合同基本信息
        </div>
        <div class="panel-body">
            <div class="form-horizontal">
                <div class="row">
                    <label class="col-md-2 control-label">保养合同编号:</label>
                    <div class="col-md-4">
                        <input type="text" class="form-control" style="width:95%;display:inline;" id="number">
                        <label style="color:red;">*</label>
                    </div>
                    <label class="col-md-2 control-label">${t("contractNature")}:</label>
                    <div class="col-md-4">
                        <select class="form-control" id="search_property" style="width:95%;">
                            <option value="-1" selected="selected">请选择合同性质</option>
                            <option value="免保">免保</option>
                            <option value="有偿保养">有偿保养</option>
                            <option value="配件">配件</option>
                        </select>
                    </div>
                </div>
                <div class="row">
                    <label class="col-md-2 control-label">${t("ContractStatus")}:</label>
                    <div class="col-md-4">
                        <select class="form-control" id="search_status" style="width:95%;">
                            <option value="-1" selected="selected">请选择合同状态</option>
                            <option value="有效">有效</option>
                            <option value="有尾款">有尾款</option>
                            <option value="关闭">关闭</option>
                        </select>
                    </div>
                    <label class="col-md-2 control-label">${t("ContractSource")}:</label>
                    <div class="col-md-4">
                        <select class="form-control" id="search_source" style="width:95%;">
                            <option value="-1" selected="selected">请选择合同来源</option>
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
                        <input type="text" class="form-control" style="width:95%;" name="ownerFullname"
                               v-model="upkeepContract.ownerFullname">
                    </div>
                    <label class="col-md-2 control-label">业主简称:</label>
                    <div class="col-md-4">
                        <input type="text" class="form-control" style="width:95%;" name="ownerShortname"
                               v-model="upkeepContract.ownerShortname">
                    </div>
                </div>
                <div class="row">

                    <label class="col-md-2 control-label">甲方:</label>

                    <div class="col-md-4">
                        <input type="text" class="form-control" style="width:95%;display:inline;" id="partyAId" autocomplete="off" data-provide="typeahead"  placeholder="请选择甲方单位">
                        <label style="color:red;">*</label>
                    </div>
                    <label class="col-md-2 control-label">乙方:</label>

                    <div class="col-md-4">
                        <input type="text" class="form-control" style="width:95%;display:inline;" id="partyBId" autocomplete="off" data-provide="typeahead" placeholder="请选择乙方单位">
                        <label style="color:red;">*</label>
                    </div>
                </div>
                <div class="row">
                    <label class="col-md-2 control-label">合同生效日:</label>

                    <div class="col-md-4">
                        <input type="text" id="beginDate" name="beginDate" class="form-control" style="width:95%;display:inline;"
                               v-model="upkeepContract.beginDate">
                        <label style="color:red">*</label>
                    </div>
                    <label class="col-md-2 control-label">合同到期日:</label>

                    <div class="col-md-4">
                        <input type="text" id="endDate" name="endDate" class="form-control" style="width:95%;display:inline;"
                               v-model="upkeepContract.endDate">
                        <label style="color:red">*</label>
                    </div>
                </div>
                <div class="row">
                    <label class="col-md-2 control-label">合同金额:</label>

                    <div class="col-md-4">
                        <input type="text" class="form-control" style="width:95%;" id="value" v-model="upkeepContract.value">
                    </div>
                    <#--<label class="col-md-2 control-label">合同期限（年）:</label>-->

                    <#--<div class="col-md-4">-->
                        <#--<input type="text" class="form-control" style="width:95%;display:inline;" name="duration" v-model="upkeepContract.duration">-->
                        <#--<label style="color:red">*</label>-->
                    <#--</div>-->

                    <label class="col-md-2 control-label">付款条件:</label>

                    <div class="col-md-4">
                        <input type="text" class="form-control" style="width:95%;" name="paymentTerm" v-model="upkeepContract.paymentTerm">
                    </div>
                </div>
                <#--<div class="row">-->
                    <#---->
                    <#--<label class="col-md-2 control-label"></label>-->

                    <#--<div class="col-md-4">-->

                    <#--</div>-->
                <#--</div>-->
                <div class="row">
                    <div class="form-group">
                        <div class=" col-sm-offset-4 col-sm-2">
                            <button id="aftersend" type="button" class="btn btn-success" v-on:click="add"><i
                                    class="icon-download"></i>新建合同
                            </button>
                        </div>
                        <div class=" col-sm-offset-1 col-sm-2">
                            <button id="aftersend" type="button" class="btn btn-success" v-on:click="back"><i
                                    class="icon-download"></i> 取消操作
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
</@layout.archiveTemplate>