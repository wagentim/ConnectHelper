package cn.wagentim.connecthelper.threads;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import cn.wagentim.connecthelper.utils.SelfList;

public class GetFileThread extends AbstractThread
{

	private final CloseableHttpClient httpClient;
	private final HttpContext context;
	private final HttpGet httpget;
	private File content = null;

	private List<IDownloadListener> listeners = null;

	public GetFileThread(
			CloseableHttpClient httpClient, HttpGet httpget)
	{
		this.httpClient = httpClient;
		this.context = HttpClientContext.create();
		this.httpget = httpget;
	}

	public void addListener(IDownloadListener listener)
	{
		if( null == listeners )
		{
			listeners = new SelfList<IDownloadListener>();
		}

		listeners.add(listener);
	}

	public void removeListener(IDownloadListener listener)
	{

	}
	
	public void releaseListeners()
	{
		listeners.clear();
	}

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
					long fileSize = entity.getContentLength();
					if( fileSize > 0 )
					{
						
					}
					else
					{
						
					}
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
