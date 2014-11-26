package cn.wagentim.connecthelper.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.FutureRequestExecutionService;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import cn.wagentim.sitecollections.sites.WebSite;

/**
 * Holding some basic connection handler 
 * @author bihu8398
 */
public abstract class Connection implements IConnect
{
	protected HttpUriRequest currRequest = null;
	protected HttpClientContext currContext = null;
	protected ResponseHandler<Document> currHandler = null;
	protected FutureCallback<Document> currCallback = null;
	protected final HttpClient client;
	protected final static ExecutorService eService = Executors.newFixedThreadPool(5);
	protected FutureRequestExecutionService reService = null;
	
	public Connection()
	{
		client = getHttpClientBuilder().build();
		reService = new FutureRequestExecutionService(client, eService);
	}
	
	protected HttpClientBuilder getHttpClientBuilder()
	{
		return HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy());
	}
	
	protected URIBuilder getURIBuilder(WebSite site)
	{
		if( null == site )
		{
			return null;
		}
		
		return new URIBuilder().setHost(site.getHost()).setScheme(site.getScheme()).setPath(site.getPath());
	}
	
	protected void run()
	{
		reService.execute(currRequest, currContext, currHandler, currCallback);
	}
	
	protected Document getContentAsDocument(HttpResponse response) throws IllegalStateException, IOException
	{
		String content = printResponseContent(response, false);
		
		DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
		
		try 
		{
			DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
			
			return docBuilder.parse(content);
			
		} catch (ParserConfigurationException ex)
		{
			throw new IllegalStateException(ex);
		} 
		catch (SAXException ex)
		{
			throw new ClientProtocolException("Malformed XML document", ex);
		}
	}
	
	protected String printResponseContent(final HttpResponse response, boolean console) throws ClientProtocolException
	{
		if( null == response )
		{
			return null;
		}
		
		StatusLine statusLine = response.getStatusLine();
		HttpEntity entity = response.getEntity();
		
		if (statusLine.getStatusCode() >= 300) 
		{
			return null;
		}
		
		if (entity == null) 
		{
			throw new ClientProtocolException("Response contains no content");
		}
		
		InputStreamReader sr = null;
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		
		try 
		{
			ContentType contentType = ContentType.getOrDefault(response.getEntity());
			
			String mimeType = contentType.getMimeType();
			
			if (!mimeType.toLowerCase().contains("html"))
			{
				throw new ClientProtocolException("Unexpected content type:"
						+ contentType);
			}
			
			Charset charset = contentType.getCharset();
			
			if (charset == null)
			{
				charset = Charset.forName("utf-8");
			}
			
			sr = new InputStreamReader(response.getEntity().getContent(), charset);
			br = new BufferedReader(sr);
			String s = null;
			
			while( (s = br.readLine() ) != null )
			{
				if( console )
				{
					System.out.println(s);
				}
				sb.append(s);
			}
			
		} 
		catch (IllegalStateException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if( null != br )
			{
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				br = null;
			}
			
			if( null != sr )
			{
				try {
					sr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				sr = null;
			}
		}
		
		return sb.toString();
	}
}
