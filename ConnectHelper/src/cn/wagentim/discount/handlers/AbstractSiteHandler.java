package cn.wagentim.discount.handlers;

import cn.wagentim.entities.WebSiteEntity;


public abstract class AbstractSiteHandler implements ISiteHandler
{
	protected final WebSiteEntity site;

	protected AbstractSiteHandler(final WebSiteEntity site)
	{
		this.site = site;
	}
}
