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

import de.wagentim.qlogger.channel.DefaultChannel;
import de.wagentim.qlogger.channel.LogChannel;
import de.wagentim.qlogger.service.QLoggerService;

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
	private LogChannel logger = QLoggerService.getChannel(QLoggerService.addChannel(new DefaultChannel("STD_PAGE")));

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
					String content = EntityUtils.toString(entity, ContentType
							.getOrDefault(entity).getCharset());
					notifyProcessFinished(content);
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
}