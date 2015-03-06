package cn.wagentim.discount.handlers;

import cn.wagentim.discount.sites.IWebsite;

public abstract class AbstractSiteHandler implements ISiteHandler
{
	protected final IWebsite site;

	protected AbstractSiteHandler(final IWebsite site)
	{
		this.site = site;
	}
}
