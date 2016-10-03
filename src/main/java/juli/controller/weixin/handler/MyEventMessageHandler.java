package juli.controller.weixin.handler;


import java.sql.Date;
import java.util.List;


import javax.annotation.Resource;

import juli.controller.weixin.common.CommonUtils;
import juli.domain.Role;
import juli.domain.Visitor;
import juli.infrastructure.exception.JuliException;
import juli.repository.RoleRepository;
import juli.repository.UserRepository;
import juli.repository.VisitorRepository;
import juli.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



import suda.wxSDK.base.userGroupManager.User;
import suda.wxSDK.base.userGroupManager.UserManager;
import suda.wxSDK.message.Base.OutputMessage;
import suda.wxSDK.message.event.ClickEventMessage;
import suda.wxSDK.message.event.LocationEventMessage;
import suda.wxSDK.message.event.LocationSelectEventMessage;
import suda.wxSDK.message.event.PicPhotoOrAlbumEventMessage;
import suda.wxSDK.message.event.PicSysPhotoEventMessage;
import suda.wxSDK.message.event.PicWeixinEventMessage;
import suda.wxSDK.message.event.QrsceneScanEventMessage;
import suda.wxSDK.message.event.QrsceneSubscribeEventMessage;
import suda.wxSDK.message.event.ScanCodePushEventMessage;
import suda.wxSDK.message.event.ScanCodeWaitMsgEventMessage;
import suda.wxSDK.message.event.SubscribeEventMessage;
import suda.wxSDK.message.event.UnSubscribeEventMessage;
import suda.wxSDK.message.event.ViewEventMessage;
import suda.wxSDK.message.handler.IEventMessageHandler;

import suda.wxSDK.message.output.TextOutputMessage;
import suda.wxSDK.weixin.WeixinException;

@Component
public class MyEventMessageHandler implements IEventMessageHandler{
    @Autowired
    private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserService userService;
	private OutputMessage out;
	public OutputMessage getOutputMessage(String text)
	{
		return new TextOutputMessage(text);
	}
	@Override
	public OutputMessage subscribe(SubscribeEventMessage msg) {
		// TODO Auto-generated method stub
		String openid=msg.getFromUserName();
		UserManager um=new UserManager();
		User user = null;
		try {
			user=um.getUserInfo(openid, null);
			juli.domain.User vs=userRepository.findByOpenid(openid);
			if(vs!=null)
			{
				vs.setInUse("1");
				userRepository.save(vs);
			}
			else
			{
				vs= CommonUtils.changeToMyUser(user);
				List<Role> list=vs.getRoles();
				Role role=roleRepository.findById("role-06bab60d-e3b0-4cb2-be06-df573eb00031");
				if (role!=null)
					System.out.println(role.getName());
				list.add(role);
				vs.setRoles(list);
				userService.createUser(vs);
				System.out.println(vs.getPassword());
			}
			
		} catch (WeixinException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JuliException e) {
			e.printStackTrace();
		}


		return getOutputMessage("欢迎："+user.getNickname()+"关注我微信");
	}

	@Override
	public OutputMessage unSubscribe(UnSubscribeEventMessage msg) {
		// TODO Auto-generated method stub
		String openid=msg.getFromUserName();
		juli.domain.User vs=userRepository.findByOpenid(openid);
		vs.setInUse("0");
		userRepository.save(vs);
		return getOutputMessage("尊敬的  "+vs.getNick()+"！ 期待下次再见！");
	}

	@Override
	public OutputMessage qrsceneSubscribe(QrsceneSubscribeEventMessage msg) {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public OutputMessage qrsceneScan(QrsceneScanEventMessage msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OutputMessage location(LocationEventMessage msg) {
		// TODO Auto-generated method stub
		userService.updateUserLatAndlng(msg.getLatitude(),msg.getLongitude(),msg.getFromUserName());
		return null;
	}

	@Override
	public OutputMessage click(ClickEventMessage msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OutputMessage view(ViewEventMessage msg) {
		// TODO Auto-generated method stub
//		try {
//			userService.login(msg.getFromUserName(),msg.getFromUserName());
//		} catch (JuliException e) {
//			e.printStackTrace();
//		}
		return null;
	}

	@Override
	public OutputMessage scanCodePush(ScanCodePushEventMessage msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OutputMessage scanCodeWaitMsg(ScanCodeWaitMsgEventMessage msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OutputMessage picSysPhoto(PicSysPhotoEventMessage msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OutputMessage picPhotoOrAlbum(PicPhotoOrAlbumEventMessage msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OutputMessage picWeixin(PicWeixinEventMessage msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OutputMessage locationSelect(LocationSelectEventMessage msg) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
