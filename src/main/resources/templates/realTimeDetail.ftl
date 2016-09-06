<#include "func.ftl">
<#import "layout.ftl" as layout />

<#assign headerContent>
<link rel="stylesheet" href="/css/realtimeDetail.css">
</#assign>

<@layout.masterTemplate initScript='js/realtimeDetail' header=headerContent title='${t("onlineMonitoring")}'>
<input type="hidden" id="eleId" value="${id}"/>
<input type="hidden" id="sid" value="${sid}"/>
<div class="clearfix realtime-detail">
    <div class="left-container">
        <div class="elevator-big-box big-image-box">
            <div class="display-area">
                <span class="connecting" id="ele-status">正在连接...</span>
            </div>
            <img class="image"
                 src="/image/elevator-big-closed.gif">
        </div>
    </div>
    <div class="right-container">
        <ul class="clearfix">
            <li class="item">
                <label class="form-label">
                    <span class="ele-num-title">电梯工号:</span>
                    <span id="eleNum" class="ele-num"></span>
                </label>
            </li>
            <li class="item">
                <label class="form-label">
                    <span class="ele-detail-addr">项目名称:</span>
                    <span class="eleAddr"></span>
                </label>
            </li>
        </ul>
        <ul class="req-list">
            <li class="req-item clearfix">
                <div class="labelbox">
                    <span class="langbox">上召</span>
                    <span class="sbox sbox-right"><i class="s s-left"></i></span>
                </div>
                <div class="valuebox">
                    <div id="upCall" class="f-fix"></div>
                </div>
            </li>
            <li class="req-item clearfix">
                <div class="labelbox">
                    <span class="langbox">下召</span>
                    <span class="sbox sbox-right"><i class="s s-left"></i></span>
                </div>
                <div class="valuebox">
                    <div id="downCall" class="f-fix"></div>
                </div>
            </li>
            <li class="req-item clearfix">
                <div class="labelbox">
                    <span class="langbox">内召</span>
                    <span class="sbox sbox-right"><i class="s s-left"></i></span>
                </div>
                <div class="valuebox">
                    <div id="innerCall" class="f-fix"></div>
                </div>
            </li>
        </ul>

        <div class="block-elevator-status clearfix">
            <ul class="icons-list">
                <li class="icons-item icons-item-5217" data-menu-number="5217"><i class="icon-elev"></i><span
                        class="icon-text">检修</span></li>
                <li class="icons-item icons-item-5218" data-menu-number="5218"><i class="icon-elev"></i><span
                        class="icon-text">故障</span></li>
                <li class="icons-item icons-item-5219" data-menu-number="5219"><i class="icon-elev"></i><span
                        class="icon-text">超载</span></li>
                <li class="icons-item icons-item-5220" data-menu-number="5220"><i class="icon-elev"></i><span
                        class="icon-text">满载</span></li>
                <li class="icons-item icons-item-5221" data-menu-number="5221"><i class="icon-elev"></i><span
                        class="icon-text">消防</span></li>
                <li class="icons-item icons-item-5222" data-menu-number="5222"><i class="icon-elev"></i><span
                        class="icon-text">驻停</span></li>
                <li class="icons-item icons-item-5223" data-menu-number="5223"><i class="icon-elev"></i><span
                        class="icon-text">平层</span></li>
                <li class="icons-item icons-item-5225" data-menu-number="5225"><i class="icon-elev"></i><span
                        class="icon-text">串口异常</span></li>
                <li class="icons-item icons-item-5226" data-menu-number="5226"><i class="icon-elev"></i><span
                        class="icon-text">控制器密码</span></li>
            </ul>
        </div>

        <div class="rows pd-tb-20 clearfix">
            <div class="col-md-4">
                <span>控制器类型:</span>
                <span id="controlType"></span>
            </div>
            <div class="col-md-4">
                <span>系统运行时间:</span>
                <span id="sysRunTime"></span>
            </div>
            <div class="col-md-4">
                <span>运行次数:</span>
                <span id="sysRunCount"></span>
            </div>
        </div>

        <div class="elevator-container">
            <ul>
                <li class="form-item">
                    <label class="form-label">
                        <span class="langbox" data-lang="registerCode" title="注册代码">注册代码:</span>
                    </label>

                    <div class="form-element">
                        <div class="f-fix"><span id="registerCode" class="value"></span></div>
                    </div>
                </li>
                <li class="form-item">
                    <label class="form-label">
                        <span class="langbox" data-lang="sRegisterCode" title="智能硬件注册码">智能硬件注册码:</span>
                    </label>

                    <div class="form-element">
                        <div class="f-fix"><span id="sRegisterCode" class="value"></span></div>
                    </div>
                </li>
                <li class="form-item">
                    <label class="form-label">
                        <span class="langbox" data-lang="elevatorCode" title="电梯型号">电梯型号:</span>
                    </label>

                    <div class="form-element">
                        <div class="f-fix"><span id="elevatorCode" class="value"></span></div>
                    </div>
                </li>
                <li class="form-item">
                    <label class="form-label">
                        <span class="langbox" data-lang="elevatorType" title="电梯类型">电梯类型:</span>
                    </label>

                    <div class="form-element">
                        <div class="f-fix"><span id="elevatorType" class="value"></span></div>
                    </div>
                </li>
                <li class="form-item">
                    <label class="form-label">
                        <span class="langbox" data-lang="releaseDate" title="出厂日期">出厂日期:</span>
                    </label>

                    <div class="form-element">
                        <div class="f-fix"><span id="releaseDate" class="value"></span></div>
                    </div>
                </li>
                <li class="form-item">
                    <label class="form-label">
                        <span class="langbox" data-lang="environment" title="使用场合">使用场合:</span>
                    </label>

                    <div class="form-element">
                        <div class="f-fix"><span id="environment" class="value"></span></div>
                    </div>
                </li>
                <li class="form-item">
                    <label class="form-label">
                        <span class="langbox" data-lang="levelStD" title="层">层/站/门:</span>
                    </label>

                    <div class="form-element">
                        <div class="f-fix"><span id="levelStD" class="value"></span></div>
                    </div>
                </li>
                <li class="form-item">
                    <label class="form-label">
                        <span class="langbox" data-lang="producer" title="制造商名称">制造商名称:</span>
                    </label>

                    <div class="form-element">
                        <div class="f-fix"><span id="producer" class="value"></span></div>
                    </div>
                </li>
                <li class="form-item">
                    <label class="form-label">
                        <span class="langbox" data-lang="installation" title="安装单位">安装单位:</span>
                    </label>

                    <div class="form-element">
                        <div class="f-fix"><span id="installation" class="value"></span></div>
                    </div>
                </li>
                <li class="form-item">
                    <label class="form-label">
                        <span class="langbox" data-lang="detailAddr" title="详细地址">详细地址:</span>
                    </label>

                    <div class="form-element">
                        <div class="f-fix"><span id="detailAddr" class="value"></span></div>
                    </div>
                </li>
                <li class="form-item">
                    <label class="form-label">
                        <span class="langbox" data-lang="useUnit" title="使用单位">使用单位:</span>
                    </label>

                    <div class="form-element">
                        <div class="f-fix"><span id="useUnit" class="value"></span></div>
                    </div>
                </li>
                <li class="form-item">
                    <label class="form-label">
                        <span class="langbox" data-lang="tenement" title="物业单位">物业单位:</span>
                    </label>

                    <div class="form-element">
                        <div class="f-fix"><span id="tenement" class="value"></span></div>
                    </div>
                </li>
                <li class="form-item">
                    <label class="form-label">
                        <span class="langbox" data-lang="maintenance" title="维保单位">维保单位:</span>
                    </label>

                    <div class="form-element">
                        <div class="f-fix"><span id="maintenance" class="value"></span></div>
                    </div>
                </li>
                <li class="form-item">
                    <label class="form-label">
                        <span class="langbox" data-lang="dPhone" title="24小时值班电话">24小时值班电话:</span>
                    </label>

                    <div class="form-element">
                        <div class="f-fix"><span id="dPhone" class="value"></span></div>
                    </div>
                </li>
                <li class="form-item">
                    <label class="form-label">
                        <span class="langbox" data-lang="contactP1" title="维保联系人一">维保联系人一:</span>
                    </label>

                    <div class="form-element">
                        <div class="f-fix"><span id="contactP1" class="value"></span></div>
                    </div>
                </li>
                <li class="form-item">
                    <label class="form-label">
                        <span class="langbox" data-lang="tenementCP1" title="物业联系人一">物业联系人一:</span>
                    </label>

                    <div class="form-element">
                        <div class="f-fix"><span id="tenementCP1" class="value"></span></div>
                    </div>
                </li>
                <li class="form-item">
                    <label class="form-label">
                        <span class="langbox" data-lang="lastCheckDate" title="上次年检日期">上次年检日期:</span>
                    </label>

                    <div class="form-element">
                        <div class="f-fix"><span id="lastCheckDate" class="value"></span></div>
                    </div>
                </li>
                <li class="form-item">
                    <label class="form-label">
                        <span class="langbox" data-lang="lastMtDate" title="上次维保时间">上次维保时间:</span>
                    </label>

                    <div class="form-element">
                        <div class="f-fix"><span id="lastMtDate" class="value"></span></div>
                    </div>
                </li>
            </ul>
        </div>

    </div>
</div>
</@layout.masterTemplate>