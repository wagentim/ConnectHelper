package cn.wagentim.discount.handlers;

import java.util.ArrayList;
import java.util.List;

import cn.wagentim.discount.sites.SiteFactory;

public final class HandlerManager
{
    public static List<ISiteHandler> getHandlers()
    {
        List<ISiteHandler> result = new ArrayList<ISiteHandler>();

        result.add(new SparHandyHandler(SiteFactory.SPAR_HANDY));

        return result;
    }
}
