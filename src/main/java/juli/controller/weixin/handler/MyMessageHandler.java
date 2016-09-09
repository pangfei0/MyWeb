package juli.controller.weixin.handler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import java.util.Map;


import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import suda.wxSDK.message.Base.AllTypeMessage;
import suda.wxSDK.message.Base.OutputMessage;
import suda.wxSDK.message.enums.EventType;
import suda.wxSDK.message.enums.MsgType;

import suda.wxSDK.message.handler.IEventMessageHandler;
import suda.wxSDK.message.handler.IMessageHandler;
import suda.wxSDK.message.handler.INormalMessageHandler;
import suda.wxSDK.util.XStream.XStreamUtil;
import suda.wxSDK.weixin.Configuration;
import suda.wxSDK.weixin.WeixinException;

@Component
public class MyMessageHandler implements IMessageHandler,Runnable{

    //获取普通消息处理工具类
	@Autowired
    INormalMessageHandler normalMsgHandler;
  //获取消息处理工具类
	@Autowired
    IEventMessageHandler eventMsgHandler;

	public String invoke(ServletInputStream inputStream) throws WeixinException {
		// TODO Auto-generated method stub
		AllTypeMessage allTypeMessage = null;
		try {
			allTypeMessage = (AllTypeMessage)XStreamUtil.xml2JavaObject(inputStream, AllTypeMessage.class);
			System.out.println(allTypeMessage);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return invoke(allTypeMessage);
	}
	public String invoke(ServletInputStream inputStream,ServletContext sc ) throws WeixinException {
		// TODO Auto-generated method stub
		AllTypeMessage allTypeMessage = null;
		try {
			allTypeMessage = (AllTypeMessage)XStreamUtil.xml2JavaObject(inputStream, AllTypeMessage.class);
			System.out.println(allTypeMessage);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

			return invoke(allTypeMessage);
		
	}

	@Override
	public String invoke(AllTypeMessage allTypeMessage, Map<String, Object> map) throws WeixinException {
		return null;
	}

	public  String invoke(AllTypeMessage allTypeMessage) throws WeixinException {
		// TODO Auto-generated method stub
		//输出消息对象
        OutputMessage outputMsg = null;
		 // 取得消息类型
        String msgType = allTypeMessage.getMsgType();
        if (Configuration.isDebug()) {
            System.out.println("POST的消息类型:[" + msgType + "]");
        }
        try
        {
 /*       //获取普通消息处理工具类
        INormalMessageHandler normalMsgHandler = HandlerFactory.getNormalMessageHandler();*/
        if (msgType.equals(MsgType.MSG_Text.toString())) {
            //处理文本消息
            outputMsg = normalMsgHandler.textTypeMsg(allTypeMessage.toTextInputMessage());
        } else if (msgType.equals(MsgType.MSG_Image.toString())) {
            //处理图片消息
            outputMsg = normalMsgHandler.imageTypeMsg(allTypeMessage.toImageInputMessage());
        } else if (msgType.equals(MsgType.MSG_Voice.toString())) {
            //处理语音消息
            outputMsg = normalMsgHandler.voiceTypeMsg(allTypeMessage.toVoiceInputMessage());
        } else if (msgType.equals(MsgType.MSG_Video.toString())) {
            //处理视频消息
            outputMsg = normalMsgHandler.videoTypeMsg(allTypeMessage.toVideoInputMessage());
        } else if (msgType.equals(MsgType.MSG_ShortVideo.toString())) {
            //处理小视频消息
            outputMsg = normalMsgHandler.shortvideoTypeMsg(allTypeMessage.toShortVideoInputMessage());
        } else if (msgType.equals(MsgType.MSG_Location.toString())) {
            //处理地理位置消息
            outputMsg = normalMsgHandler.locationTypeMsg(allTypeMessage.toLocationInputMessage());
        } else if (msgType.equals(MsgType.MSG_Link.toString())) {
            //处理链接消息
            outputMsg = normalMsgHandler.linkTypeMsg(allTypeMessage.toLinkInputMessage());
        } else if (msgType.equals(MsgType.MSG_Event.toString())) {
            //获取事件类型
            String event = allTypeMessage.getEvent();
            /*//获取消息处理工具类
            IEventMessageHandler eventMsgHandler = HandlerFactory.getEventMessageHandler();*/
            //自定义菜单事件
            if (event.equals(EventType.EVENT_Click.toString())) {
                //点击菜单拉取消息时的事件推送
                outputMsg = eventMsgHandler.click(allTypeMessage.toClickEventMessage());
            } else if (event.equals(EventType.EVENT_View.toString())) {
                //点击菜单跳转链接时的事件推送
                outputMsg = eventMsgHandler.view(allTypeMessage.toViewEventMessage());
            } else if (event.equals(EventType.EVENT_Subscribe.toString())) {
                //关注事件
                outputMsg = eventMsgHandler.subscribe(allTypeMessage.toSubscribeEventMessage());
            } else if (event.equals(EventType.EVENT_UnSubscribe.toString())) {
                //取消关注事件
                outputMsg = eventMsgHandler.unSubscribe(allTypeMessage.toUnSubscribeEventMessage());
            } else if (event.equals(EventType.EVENT_Scan.toString())) {
                //扫描带参数二维码事件
                //获取事件KEY值，判断是否关注
                String eventKey = allTypeMessage.getEventKey();
                if (eventKey.startsWith("qrscene_")) {
                    //用户未关注时，进行关注后的事件推送
                    outputMsg = eventMsgHandler.qrsceneSubscribe(allTypeMessage.toQrsceneSubscribeEventMessage());
                } else {
                    //用户已关注时的事件推送
                    outputMsg = eventMsgHandler.qrsceneScan(allTypeMessage.toQrsceneScanEventMessage());
                }
            } else if (event.equals(EventType.EVENT_Location.toString())) {
                //上报地理位置事件
                outputMsg = eventMsgHandler.location(allTypeMessage.toLocationEventMessage());
            } else if (event.equals(EventType.EVENT_Scancode_Push.toString())) {
                //扫码推事件的事件推送
                outputMsg = eventMsgHandler.scanCodePush(allTypeMessage.toScanCodePushEventMessage());
            } else if (event.equals(EventType.EVENT_Scancode_Waitmsg.toString())) {
                //扫码推事件且弹出“消息接收中”提示框的事件推送
                outputMsg = eventMsgHandler.scanCodeWaitMsg(allTypeMessage.toScanCodeWaitMsgEventMessage());
            } else if (event.equals(EventType.EVENT_Pic_Sysphoto.toString())) {
                //弹出系统拍照发图的事件推送
                outputMsg = eventMsgHandler.picSysPhoto(allTypeMessage.toPicSysPhotoEventMessage());
            } else if (event.equals(EventType.EVENT_Pic_Photo_OR_Album.toString())) {
                //弹出拍照或者相册发图的事件推送
                outputMsg = eventMsgHandler.picPhotoOrAlbum(allTypeMessage.toPicPhotoOrAlbumEventMessage());
            } else if (event.equals(EventType.EVENT_Pic_Weixin.toString())) {
                //弹出微信相册发图器的事件推送
                outputMsg = eventMsgHandler.picWeixin(allTypeMessage.toPicWeixinEventMessage());
            } else if (event.equals(EventType.EVENT_Location_Select.toString())) {
                //弹出地理位置选择器的事件推送
                outputMsg = eventMsgHandler.locationSelect(allTypeMessage.toLocationSelectEventMessage());
            }
        }
        if (outputMsg != null) {
            //设置收件人消息
            setOutputMsgInfo(outputMsg, allTypeMessage);
            System.out.println("handler处理后的返回消息："+outputMsg);
        }
    } catch (IOException ex) {
        throw new WeixinException("输入流转换错误：", ex);
    } catch (NoSuchMethodException ex) {
        throw new WeixinException("没有找打对应方法：", ex);
    } catch (SecurityException ex) {
        throw new WeixinException("安全错误：", ex);
    } catch (Exception ex) {
        throw new WeixinException("系统错误：", ex);
    }
    if (outputMsg != null) {
        try {
            // 把发送发送对象转换为xml输出
            String xml = XStreamUtil.javaObject2Xml(outputMsg);
            if (Configuration.isDebug()) {
                System.out.println("POST输出消息:");
                System.out.println(xml);
                System.out.println("------------------------");
            }
            return xml;
        } catch (Exception ex) {
            throw new WeixinException("转换回复消息为xml时错误：", ex);
        }
    }
    return "";
}

//设置详细信息
private static void setOutputMsgInfo(OutputMessage oms, AllTypeMessage msg) throws Exception {
    // 设置发送信息
    oms.setCreateTime(new Date().getTime());
    oms.setToUserName(msg.getFromUserName());
    oms.setFromUserName(msg.getToUserName());
}

@Override
public void run() {
	// TODO Auto-generated method stub
	
}

}
