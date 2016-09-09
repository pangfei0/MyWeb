package juli.controller.weixin.common;

public class RefreshAccessToken {
	private static String ACCESS_TOKEN="";

	public static String getACCESS_TOKEN() {
		return ACCESS_TOKEN;
	}

	public static void setACCESS_TOKEN(String aCCESS_TOKEN) {
		ACCESS_TOKEN = aCCESS_TOKEN;
	}
	

}
