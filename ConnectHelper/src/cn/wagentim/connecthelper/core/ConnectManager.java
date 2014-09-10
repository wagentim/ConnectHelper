package cn.wagentim.connecthelper.core;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.connecthelper.threads.AbstractThread;
import cn.wagentim.connecthelper.threads.GetStandardWebPageContentAsStringThread;
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
			return URLType.TYPE_PAGE;
		}

		int dot = sb.lastIndexOf(StringConstants.DOT);

		// if there is no
		if( dot < 0 )
		{
			return URLType.TYPE_PAGE;
		}

		String extension = sb.substring(dot+1);

		// it means the URL is ended with a link, not a data
		if( extension.length() < 3 )
		{
			return URLType.TYPE_PAGE;
		}
		else if( isSupportedFileType(extension) )
		{
			return URLType.TYPE_DATA;
		}

		return URLType.TYPE_UNKNOWN;
	}

	private static boolean isSupportedFileType(String extension)
	{
		// TODO Auto-generated method stub
		return false;
	}

	public static String getWebPage(String startURL)
	{
		AbstractThread thread = new GetStandardWebPageContentAsStringThread(httpClient, new HttpGet(startURL));

		thread.start();
		try
		{
			thread.join();
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return (String) thread.getResult();

	}
}
