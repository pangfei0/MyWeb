package juli.controller.weixin.handler;




import org.springframework.stereotype.Component;

import suda.wxSDK.message.Base.NormalMessage;
import suda.wxSDK.message.Base.OutputMessage;
import suda.wxSDK.message.handler.INormalMessageHandler;
import suda.wxSDK.message.normal.*;
import suda.wxSDK.message.output.TextOutputMessage;

@Component
public class MyNormalMessageHandler implements INormalMessageHandler{

  private OutputMessage allType(NormalMessage msg) {
  
	  return new TextOutputMessage("您当前无会话状态，不进行处理，请谅解。");
	    }
	@Override
	public OutputMessage imageTypeMsg(ImageInputMessage arg0) {
		// TODO Auto-generated method stub

		return allType(arg0);
	}

	@Override
	public OutputMessage linkTypeMsg(LinkInputMessage arg0) {
		// TODO Auto-generated method stub
		return allType(arg0);
	}

	@Override
	public OutputMessage locationTypeMsg(LocationInputMessage arg0) {
		// TODO Auto-generated method stub
		return allType(arg0);
	}

	@Override
	public OutputMessage shortvideoTypeMsg(ShortVideoInputMessage arg0) {
		// TODO Auto-generated method stub
		return allType(arg0);
	}

	@Override
	public OutputMessage textTypeMsg(TextInputMessage arg0) {
		// TODO Auto-generated method stub
		return allType(arg0);
	
	}

	@Override
	public OutputMessage videoTypeMsg(VideoInputMessage arg0) {
		// TODO Auto-generated method stub
		return allType(arg0);
	}

	@Override
	public OutputMessage voiceTypeMsg(VoiceInputMessage arg0) {
		// TODO Auto-generated method stub
		return allType(arg0);
	}
    
	
	
}
