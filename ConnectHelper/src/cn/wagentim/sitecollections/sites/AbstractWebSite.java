package cn.wagentim.sitecollections.sites;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.utils.URIBuilder;

import cn.wagentim.basicutils.StringConstants;

public abstract class AbstractWebSite implements IWebSite
{
    protected String path = StringConstants.EMPTY_STRING;

    @Override
    public URI getURI() throws URISyntaxException
    {
        URIBuilder builder = new URIBuilder();
        return builder.setHost(getHost()).setPort(getPort()).setScheme(getScheme()).setPath(getPath()).build();
    }

    @Override
    public void setPath(String path)
    {
        this.path = path;
    }
}
