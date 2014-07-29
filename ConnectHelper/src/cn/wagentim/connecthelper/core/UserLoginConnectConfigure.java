package cn.wagentim.connecthelper.core;

import cn.wagentim.connecthelper.webs.WebSite;

public class UserLoginConnectConfigure implements IConnectConfigure
{
	private final WebSite website;
	
	public UserLoginConnectConfigure(final WebSite website)
	{
		this.website = website;
	}

	@Override
	public String getWebsiteName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getURL()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
