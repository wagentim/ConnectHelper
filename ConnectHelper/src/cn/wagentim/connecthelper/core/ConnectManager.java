package cn.wagentim.connecthelper.core;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.connecthelper.threads.AbstractThread;
import cn.wagentim.connecthelper.threads.GetFileThread;
import cn.wagentim.connecthelper.threads.GetStandardWebPageContentAsStringThread;
import cn.wagentim.connecthelper.threads.ICallback;
import de.wagentim.qlogger.channel.DefaultChannel;
import de.wagentim.qlogger.channel.LogChannel;
import de.wagentim.qlogger.service.QLoggerService;


public final class ConnectManager implements URLType
{
	private static final PoolingHttpClientConnectionManager poolingManager;
	private static final CloseableHttpClient httpClient;
	public static final LogChannel logger;

	static
	{
		poolingManager = new PoolingHttpClientConnectionManager();
		httpClient = HttpClients.custom().setConnectionManager(poolingManager).build();
		logger = QLoggerService.getChannel(QLoggerService.addChannel(new DefaultChannel("ConnectManager")));
	}

	public static int analyzeURL(String url)
	{
		StringBuffer sb = new StringBuffer(url);

		// check if the URL ended with "/", then we just try to get the web page content
		char lastChar = sb.charAt(sb.length() - 1);
		if( StringConstants.CHAR_SLASH == lastChar )
		{
			return URLType.TYPE_STANDARD_PAGE;
		}

		int dot = sb.lastIndexOf(StringConstants.DOT);

		// if there is no
		if( dot < 0 )
		{
			return URLType.TYPE_STANDARD_PAGE;
		}

		String extension = sb.substring(dot+1);

		// it means the URL is ended with a link, not a data
		if( extension.length() < 3 )
		{
			return URLType.TYPE_STANDARD_PAGE;
		}
		else if( isSupportedFileType(extension) )
		{
			return URLType.TYPE_STANDARD_DATA;
		}

		return URLType.TYPE_UNKNOWN;
	}

	private static boolean isSupportedFileType(String extension)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Get URL resource. It will be handled by different handlers, that a defined by type
	 * 
	 * @param url the resource location
	 * @param type	the required resource type
	 * @return	Object required object
	 */
	public static void getRequiredData(final String url, final int type, final ICallback callback)
	{
		AbstractThread thread = getRequiredThread(type, url);
		thread.setCallback(callback);
		thread.start();
	}

	private static AbstractThread getRequiredThread(final int type, final String url)
	{
		switch(type)
		{
			case URLType.TYPE_STANDARD_PAGE:
				return new GetStandardWebPageContentAsStringThread(httpClient, new HttpGet(url));
			case URLType.TYPE_STANDARD_DATA:
				return new GetFileThread(httpClient, new HttpGet(url));
			default:
				return null;
		}
	}
}
