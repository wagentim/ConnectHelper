package cn.wagentim.discount.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.FutureRequestExecutionService;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Holding some basic connection handler
 * @author bihu8398
 */
public abstract class Connection implements IConnect
{
	protected HttpUriRequest currRequest = null;
	protected HttpClientContext currContext = null;
	protected ResponseHandler<Object> currHandler = null;
	protected FutureCallback<Object> currCallback = null;
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

	protected void run()
	{
		reService.execute(currRequest, currContext, currHandler, currCallback);
	}

	protected Document getContentAsDocument(HttpResponse response) throws IllegalStateException, IOException
	{
		String content = printResponseContent(response, false);

		if( null != content && !content.isEmpty() )
		{
			return Jsoup.parse(content);
		}

		return null;
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