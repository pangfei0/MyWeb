package juli.controller.weixin.common;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import juli.domain.Role;
import suda.wxSDK.base.userGroupManager.User;

public class CommonUtils {
	
	public static juli.domain.User changeToMyUser(User user1)
	{
		juli.domain.User user=new juli.domain.User();
		user.setOpenid(user1.getOpenid());
		user.setUserName(user1.getOpenid());
		user.setPassword(user1.getOpenid());
		user.setNick(user1.getNickname());
		user.setInUse("1");
		user.setIsDemand("0");
		List<Role> list=new ArrayList<>();
		user.setRoles(list);
		return user;
		
	}

}
