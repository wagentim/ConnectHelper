package cn.wagentim.connecthelper.connections;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BestMatchSpecFactory;
import org.apache.http.impl.cookie.BrowserCompatSpec;
import org.apache.http.impl.cookie.BrowserCompatSpecFactory;
import org.apache.http.protocol.HttpContext;
import org.w3c.dom.Document;

import cn.wagentim.connecthelper.core.Connection;
import cn.wagentim.sitecollections.sites.VP;
import cn.wagentim.sitecollections.sites.WebSite;

public class VPConnection extends Connection
{
	private WebSite vp = new VP();

	@Override
	public void exec()
	{
		URI uri = null;
		
		try
		{
			uri = getURIBuilder(vp).build();
		} 
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
		
		if( null == uri )
		{
			return;
		}
		
		step1(uri);
	}
	
	protected HttpClientBuilder getHttpClientBuilder()
	{
		CookieSpecProvider easySpecProvider = new CookieSpecProvider() {
			
			public CookieSpec create(HttpContext context) {
				
				return new BrowserCompatSpec() {
					@Override
					public void validate(Cookie cookie, CookieOrigin origin) throws MalformedCookieException 
					{
						// Oh, I am easy
					}
				};
			}
		};
		
		Registry<CookieSpecProvider> r = RegistryBuilder.<CookieSpecProvider>create().
											register(CookieSpecs.BEST_MATCH, new BestMatchSpecFactory()).
											register(CookieSpecs.BROWSER_COMPATIBILITY, new BrowserCompatSpecFactory()).
											register("easy", easySpecProvider).
											build();
		
		RequestConfig requestConfig = RequestConfig.custom().
										setCookieSpec("easy").
										build();
		
		return super.getHttpClientBuilder().setDefaultCookieSpecRegistry(r).setDefaultRequestConfig(requestConfig);
	}
	
	// get the main page
	private void step1(URI uri)
	{
		currCallback = new FutureCallback<Document>()
		{

			@Override
			public void completed(Document document)
			{
				System.out.println(document.toString());
			}

			@Override
			public void failed(Exception paramException)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void cancelled()
			{
				// TODO Auto-generated method stub
				
			}
		};
		
		currHandler = new ResponseHandler<Document>()
		{
			
			@Override
			public Document handleResponse(HttpResponse response)
			{
				try
				{
					return getContentAsDocument(response);
				} 
				catch (IllegalStateException | IOException e)
				{
					e.printStackTrace();
				}
				
				return null;
			}
		};
		
		currRequest = new HttpGet(uri);
		
		run();
		
	}
	
	public static void main(String[] args)
	{
		VPConnection vp = new VPConnection();
		vp.exec();
	}
}
