package cn.wagentim.discount.sites;

import java.net.URI;

import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.discount.handlers.ISiteHandler;
import cn.wagentim.discount.handlers.SparHandyHandler;

public class SparHandy implements IWebsite
{
    @Override
    public String getName()
    {
        return "SparHandy";
    }

    @Override
    public String getDomain()
    {
        // TODO Auto-generated method stub
        return "www.sparhandy.de";
    }

    @Override
    public String getEntryPoint()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getAuth()
    {
        return StringConstants.EMPTY_STRING;
    }

    @Override
    public String getUserName()
    {
        return StringConstants.EMPTY_STRING;
    }

    @Override
    public String getPassword()
    {
        return StringConstants.EMPTY_STRING;
    }

    @Override
    public String getData()
    {
        return null;
    }

	@Override
	public ISiteHandler getHandler()
	{
		return new SparHandyHandler(this);
	}

	@Override
	public String getUserNameDefinition()
	{
		return null;
	}

	@Override
	public String getPasswordDefinition()
	{
		return null;
	}

	@Override
	public URI getLoginURI()
	{
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public String getHost()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getPort()
    {
        // TODO Auto-generated method stub
        return 0;
    }

}
