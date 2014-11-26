package cn.wagentim.connecthelper.connections;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
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
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.jsoup.nodes.Document;

import cn.wagentim.connecthelper.core.Connection;
import cn.wagentim.sitecollections.sites.VP;
import cn.wagentim.sitecollections.sites.WebSite;

public class VPConnection extends Connection
{
	private WebSite vp = new VP();

	@Override
	public void exec()
	{
		step1();
	}

	protected HttpClientBuilder getHttpClientBuilder()
	{
		CookieSpecProvider easySpecProvider = new CookieSpecProvider()
		{

			public CookieSpec create(HttpContext context)
			{

				return new BrowserCompatSpec()
				{
					@Override
					public void validate(Cookie cookie, CookieOrigin origin)
							throws MalformedCookieException
					{
						// Oh, I am easy
					}
				};
			}
		};

		Registry<CookieSpecProvider> r = RegistryBuilder
				.<CookieSpecProvider> create()
				.register(CookieSpecs.BEST_MATCH, new BestMatchSpecFactory())
				.register(CookieSpecs.BROWSER_COMPATIBILITY,
						new BrowserCompatSpecFactory())
				.register("easy", easySpecProvider).build();

		RequestConfig requestConfig = RequestConfig.custom()
				.setCookieSpec("easy").build();

		return super.getHttpClientBuilder().setDefaultCookieSpecRegistry(r)
				.setDefaultRequestConfig(requestConfig);
	}

	// get the main page
	private void step1()
	{
		currCallback = new FutureCallback<Document>()
		{

			@Override
			public void completed(Document document)
			{
				// System.out.println(document.toString());
				step2();
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

		URI uri = null;

		try
		{
			uri = getURIBuilder(vp).build();
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}

		if (null == uri)
		{
			return;
		}

		currRequest = new HttpGet(uri);

		run();

	}

	private void step2()
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return null;
			}
		};

		URI uri = null;

		try
		{
			uri = getURIBuilder(vp).build();
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}

		if (null == uri)
		{
			return;
		}
		currRequest = new HttpPost(uri);
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		postParams.add(new BasicNameValuePair("PortalTheme.CountryTheme.CouleurTexte", "black"));
		postParams.add(new BasicNameValuePair("Email", vp.getUserName()));
		postParams.add(new BasicNameValuePair("Password", vp.getPassword()));
		
		try
		{
			((HttpPost)currRequest).setEntity(new UrlEncodedFormEntity(postParams));
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		
		run();

	}

	public static void main(String[] args)
	{
		VPConnection vp = new VPConnection();
		vp.exec();
	}
}
