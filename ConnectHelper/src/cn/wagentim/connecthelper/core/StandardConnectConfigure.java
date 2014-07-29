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

}
