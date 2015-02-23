package cn.wagentim.sitecollections.sites;


public class VP extends AbstractWebSite
{
	public String getUserName()
	{
		return "wagentim@hotmail.com";
	}

	public String getPassword()
	{
		return "huang78";
	}

	@Override
	public String getHost()
	{
		return "secure.de.vente-privee.com/";
	}

	@Override
	public String getScheme()
	{
		return "https";
	}

	@Override
	public String getPath()
	{
		return "/authentication/portal/DE";
	}

    @Override
    public int getPort()
    {
        return 80;
    }
}
