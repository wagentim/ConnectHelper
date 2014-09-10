package cn.wagentim.connecthelper.threads;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;

public class GetFileThread extends AbstractThread
{

	private final CloseableHttpClient httpClient;
	private final HttpContext context;
	private final HttpGet httpget;
	private File content = null;

	private List<IProcessListener> listeners = null;

	public GetFileThread(
			CloseableHttpClient httpClient, HttpGet httpget)
	{
		this.httpClient = httpClient;
		this.context = HttpClientContext.create();
		this.httpget = httpget;
	}

	public void addListener(IProcessListener listener)
	{
		if( null == listeners )
		{
			listeners = new ArrayList<IProcessListener>();
			listeners.add(listener);
			return;
		}

		Iterator<IProcessListener> it = listeners.iterator();

		while(it.hasNext())
		{
			IProcessListener item = it.next();

			if( item == listener )
			{
				return;
			}
		}

		listeners.add(listener);
	}

	public void removeListener(IProcessListener listener)
	{

	}

	public void run()
	{

	}

	@Override
	public Object getResult()
	{
		return content;
	}

}
