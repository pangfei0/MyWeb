package juli;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpUtils;

import juli.controller.weixin.WeiXinFinalValue;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;


import com.alibaba.fastjson.JSONObject;
import suda.wxSDK.base.menu.*;
import suda.wxSDK.base.oauth.AccessTokenFactory;
import suda.wxSDK.weixin.Configuration;
import suda.wxSDK.weixin.Weixin;
import suda.wxSDK.weixin.WeixinException;


public class Access_Token {

	Weixin wx=new Weixin();
	@Test
	public void test111() throws WeixinException, UnsupportedEncodingException
	{
		wx.login(WeiXinFinalValue.APPID, WeiXinFinalValue.APPSECRET);
//		Menu menu=new Menu();
//		SingleButton a1=new SingleButton("主菜单一");
//		SingleButton a2=new SingleButton("主菜单二");
//		SingleButton a3=new ClickButton("主菜单三","0000_1");
//		SingleButton a11=new ClickButton("ck", "0001_1");
//		SingleButton a12=new ViewButton("www.baidu.com", "view");
//		SingleButton a13=new ScancodePushButton("smts","0001_2");
//		SingleButton a14=new ScancodeWaitMsgButton("smtc","0001_3");
//		SingleButton a15=new PicSysPhotoButton("tcpz","0001_4");
//		SingleButton a21=new PicPhotoOrAlbumButton("ppab","0002_1");
//		SingleButton a22=new PicWeixinButton("pwb","0002_2");
//		SingleButton a23=new LocationSelectButton("lsb","0002_3");
//		SingleButton a24=new MediaIdButton("meib","xOlR6ISBKJyVSVxiRVHMqk5g7mrspiu9iV1tSI");
//		SingleButton a25=new ViewLimitedButton("wlim", "xOlR6ISBKJyVSVxiRVHMqhRkJdwuNsFBnGWYB875pzM");
//		a1.getSubButton().add(a11);
//		a1.getSubButton().add(a12);
//		a1.getSubButton().add(a13);
//		a1.getSubButton().add(a14);
//		a1.getSubButton().add(a15);
//		a2.getSubButton().add(a21);
//		a2.getSubButton().add(a22);
//		a2.getSubButton().add(a23);
//		a2.getSubButton().add(a24);
//		a2.getSubButton().add(a25);
//		menu.getButton().add(a1);
//		menu.getButton().add(a2);
//		menu.getButton().add(a3);
//		wx.createMenu(menu);
		
		String authorize_url = "https://open.weixin.qq.com/connect/oauth2/authorize";
		String str1=URLEncoder.encode("https://4d3574f2.ngrok.io/weixin/oAuthInfo", "UTF-8");
		System.out.println(str1);
		String str=authorize_url + "?appid=" +WeiXinFinalValue.APPID+"&redirect_uri=" + str1 + "&response_type=code&scope=" + 
		                                                    "snsapi_userinfo" + "&state=" + "elevator" + "#wechat_redirect";
		System.out.println(str);    
		if(Configuration.isDebug())
		{
			System.out.println(str1);
			System.out.println(str);
		}
		List<SingleButton> wmList=new ArrayList();
		ViewButton wm0=new ViewButton("http://www.baidu.com","百度");
		wm0.setType("view");
		SingleButton wm1=new SingleButton();
		wm1.setName("发图");
		PicSysPhotoButton wm11=new PicSysPhotoButton();
		PicPhotoOrAlbumButton wm12=new PicPhotoOrAlbumButton();
		PicWeixinButton wm13=new PicWeixinButton();
		ViewButton wm14=new ViewButton(str,"电梯查询");
			wm11.setName("系统拍照");
			wm11.setKey("rselfmenu_1_0");
//			wm11.setSub_button(new ArrayList<WeixinMenu>());
			wm12.setName("拍照发图");
			wm12.setKey("rselfmenu_1_1");
			wm13.setName("相册发图");
			wm13.setKey("rselfmenu_1_2");
				List<SingleButton> wmsub1=new ArrayList();
				wmsub1.add(wm11);
				wmsub1.add(wm12);
				wmsub1.add(wm13);
				wmsub1.add(wm14);
	    wm1.setSubButton(wmsub1);
		SingleButton wm2=new SingleButton();
		wm2.setName("扫码");
		ScancodeWaitMsgButton wm21=new ScancodeWaitMsgButton();
		ScancodePushButton wm22=new ScancodePushButton();
			wm21.setName("扫码提示");
			wm21.setKey("rselfmenu_0_0");
			wm22.setName("扫码推事");
			wm22.setKey("rselfmenu_0_1");
				List<SingleButton> wmsub2=new ArrayList();
				wmsub2.add(wm21);
				wmsub2.add(wm22);
		wm2.setSubButton(wmsub2);
		wmList.add(wm1);
		wmList.add(wm0);
		wmList.add(wm2);
        JSONObject json=new JSONObject();
		json.put("button", wmList);
		String url="https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+ AccessTokenFactory.getStringAccess_Token();
		System.out.println(json.toJSONString());
		MenuManager menuManager=new MenuManager();
		Menu menu=new Menu();menu.setButton(wmList);
		menuManager.createMenu(menu);
		
	} 

   

}
