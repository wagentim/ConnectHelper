package cn.wagentim.connecthelper.core;

import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;

import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.connecthelper.threads.ICallback;

public final class ConnectData
{
    private String url = StringConstants.EMPTY_STRING;
    private CloseableHttpClient httpClient = null;
    private HttpRequestBase httpMethod = null;
    private ICallback callBack = null;
    private BasicCookieStore cookieStore = null;
    private int connType = IConnType.DEFAULT;
    
    public String getUrl()
    {
	return url;
    }

    public void setUrl(String url)
    {
	this.url = url;
    }

    public CloseableHttpClient getHttpClient()
    {
	return httpClient;
    }
    
    public void setHttpClient(CloseableHttpClient httpClient)
    {
	this.httpClient = httpClient;
    }

    public HttpRequestBase getHttpMethod()
    {
	return httpMethod;
    }

    public void setHttpMethod(HttpRequestBase httpMethod)
    {
	this.httpMethod = httpMethod;
    }

    public ICallback getCallback()
    {
	return callBack;
    }
    
    public void setCallback(ICallback callBack)
    {
	this.callBack = callBack;
    }

    public BasicCookieStore getCookieStore()
    {
        return cookieStore;
    }

    public void setCookieStore(BasicCookieStore cookieStore)
    {
        this.cookieStore = cookieStore;
    }

    public int getConnType()
    {
        return connType;
    }

    public void setConnType(int connType)
    {
        this.connType = connType;
    }
}
