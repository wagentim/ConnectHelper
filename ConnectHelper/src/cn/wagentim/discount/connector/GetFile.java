package cn.wagentim.discount.connector;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import de.wagentim.qlogger.channel.DefaultChannel;
import de.wagentim.qlogger.channel.LogChannel;
import de.wagentim.qlogger.logger.Log;
import de.wagentim.qlogger.service.QLoggerService;

public class GetFile extends AbstractThread
{

	private CloseableHttpClient httpClient;
	private HttpContext context;
	private HttpGet httpget;
	private LogChannel logger = QLoggerService.getChannel(QLoggerService.addChannel(new DefaultChannel("STD_DL")));
	private final String[] links;

	public GetFile(final String[] links, final int type)
	{
		this.links = links;
	}

	public void run()
	{
		logger.log(Log.LEVEL_INFO, "DL start");
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
					String filePath = getFilePath();
					String fileName = "a";
					if( fileSize > 0 )
					{
						logger.log(Log.LEVEL_INFO, "Data Size is 1% and will be downloaded to 2% with the name 3%", String.valueOf(fileSize), filePath, fileName);
						InputStream is = entity.getContent();
						File file = new File(filePath);
						FileOutputStream fos = new FileOutputStream(file);
						byte[] cache = new byte[1024];
						int read;
						while( (read = is.read(cache)) > 0 )
						{
							fos.write(cache);
						}


					}
					else
					{
						logger.log(Log.LEVEL_WARN, "DL Data Size is 0");
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

	private String getFilePath()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
