package cn.wagentim.sitecollection.handlers;

import cn.wagentim.sitecollections.sites.IWebsite;

public abstract class AbstractSiteHandler implements ISiteHandler
{
	protected final IWebsite site;

	protected AbstractSiteHandler(final IWebsite site)
	{
		this.site = site;
	}
}
