package cn.wagentim.connecthelper.threads;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import cn.wagentim.basicutils.StringConstants;

/**
 * Get standard web page source code
 *
 * @author bihu8398
 *
 */
public class GetStandardWebPageContentAsStringThread extends AbstractThread
{
	private final CloseableHttpClient httpClient;
	private final HttpContext context;
	private final HttpGet httpget;
	private String content = StringConstants.EMPTY_STRING;

	public GetStandardWebPageContentAsStringThread(
			CloseableHttpClient httpClient, HttpGet httpget)
	{
		this.httpClient = httpClient;
		this.context = HttpClientContext.create();
		this.httpget = httpget;
	}

	@Override
	public void run()
	{
		try
		{
			CloseableHttpResponse response = httpClient.execute(httpget,
					context);
			try
			{
				if (response.getStatusLine().getStatusCode() < 300)
				{
					HttpEntity entity = response.getEntity();
					content = EntityUtils.toString(entity, ContentType
							.getOrDefault(entity).getCharset());
					EntityUtils.consume(entity);
				}
			} finally
			{
				response.close();
			}
		} catch (ClientProtocolException ex)
		{
			// Handle protocol errors
		} catch (IOException ex)
		{
			// Handle I/O errors
		}
	}

	@Override
	public Object getResult()
	{
		return content;
	}

}