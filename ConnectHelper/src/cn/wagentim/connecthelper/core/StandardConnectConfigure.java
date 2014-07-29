package cn.wagentim.connecthelper.core;

import cn.wagentim.connecthelper.webs.WebSite;

/**
 * Standard Connect Configure will be used to fetch standard web site content
 *
 * @author bihu8398
 *
 */
public class StandardConnectConfigure implements IConnectConfigure
{
	private final WebSite website;

	public StandardConnectConfigure(final WebSite website)
	{
		this.website = website;
	}

	@Override
	public String getWebsiteName() 
	{
		return null;
	}

	@Override
	public String getURL() 
	{
		// TODO Auto-generated method stub
		return null;
	}

}
