package cn.wagentim.connecthelper.core;

import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;

import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.connecthelper.threads.ICallback;

public final class ConnectData
{
    private String url = StringConstants.EMPTY_STRING;
    private CloseableHttpClient httpClient = null;
    private HttpRequestBase httpMethod = null;
    private ICallback callBack = null;

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
    
}
