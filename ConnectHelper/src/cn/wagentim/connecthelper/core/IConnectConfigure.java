package cn.wagentim.connecthelper.core;

public interface IConnectConfigure
{
	public static final int STANDARD = 0x00;
	public static final int USER_LOGIN = 0x01;
	
	/** The web site name */
	String getWebsiteName();
	
	/** The URL of this web site */
	String getURL();

}
