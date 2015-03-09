package cn.wagentim.discount.sites;

import cn.wagentim.entities.WebSiteEntity;
import cn.wagentim.webs.IWebTypes;

public final class SiteFactory
{
	public static final WebSiteEntity SPAR_HANDY = new WebSiteEntity();
	
	static
	{
		SPAR_HANDY.setHost("www.sparhandy.de");
		SPAR_HANDY.setName("Spar Handy");
		SPAR_HANDY.setSchema("http");
		SPAR_HANDY.setWebType(IWebTypes.TYPE_TELEKOM);
	}
}	
