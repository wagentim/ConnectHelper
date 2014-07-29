package cn.wagentim.connecthelper.core;

import cn.wagentim.basicutils.BasicUtils;
import cn.wagentim.connecthelper.webs.WebSite;

public class ConfigureFactory
{
	public static final IConnectConfigure getConfigure(final WebSite website)
	{
		if( BasicUtils.isNull(website) )
		{
			return null;
		}

		int type = website.getConnectType();

		switch(type)
		{
			case IConnectConfigure.STANDARD:
				return new StandardConnectConfigure(website);

			case IConnectConfigure.USER_LOGIN:
				return new UserLoginConnectConfigure();

			default:
				return null;
		}
	}
}
