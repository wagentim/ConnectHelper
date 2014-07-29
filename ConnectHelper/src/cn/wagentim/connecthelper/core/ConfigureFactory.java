package cn.wagentim.connecthelper.core;

import cn.wagentim.basicutils.BasicUtils;
import cn.wagentim.connecthelper.webs.WebSite;

/**
 * According to different web site it returns different configures {@link IConnectConfigure#STANDARD} and {@link IConnectConfigure#USER_LOGIN}
 * 
 * @author wagentim
 *
 */
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
				return new UserLoginConnectConfigure(website);

			default:
				return null;
		}
	}
}
