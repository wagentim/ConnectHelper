package cn.wagentim.discount.connector;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import cn.wagentim.basicutils.Validator;
import cn.wagentim.discount.utils.HttpHelper;
import cn.wagentim.entities.ResourceEntity;

public class GetFile extends AbstractThread
{
	private ResourceEntity resource;
	private final URI uri;

	public GetFile(final URI uri)
	{
		this.uri = uri;
	}

	public void run()
	{
		if( Validator.isNull(uri) )
		{
			return;
		}
		
		HttpClientContext context = HttpClientContext.create();
        context.setCookieStore(getCookieStore());
        CloseableHttpClient httpclient = HttpClients.createDefault();
        
		try
		{
			CloseableHttpResponse response = httpclient.execute(new HttpGet(uri), context);
			try
			{
				if (response.getStatusLine().getStatusCode() < 300)
				{
					HttpEntity entity = response.getEntity();
					long fileSize = entity.getContentLength();
					
					if( fileSize > 0 )
					{
						resource = new ResourceEntity();
						resource.setLength(fileSize);
						resource.setName(HttpHelper.getFileName(uri));
						
						byte[] data = saveByteArray(entity);
						
						if( null == data )
						{
							resource = null;
						}
						else
						{
							resource.setData(data);
						}
						
					}
					else
					{
						resource = null;
					}
					EntityUtils.consume(entity);
				}
				else
				{
					resource = null;
				}
			} finally
			{
				
				response.close();
			}

		} 
		catch (ClientProtocolException ex)
		{
			ex.printStackTrace();
		} 
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		
	}
	
	private byte[] saveByteArray(final HttpEntity entity)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		final byte[] buffer = new byte[1024];
		InputStream ins;
		try
		{
			ins = entity.getContent();
			int number = 0;
			while( ( number = ins.read(buffer) ) != -1 )
			{
				bos.write(buffer, 0, number);
			}
			
			byte[] result = bos.toByteArray();
			
			bos.close();
			ins.close();
			
			return result;
		} 
		catch (IllegalStateException | IOException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ResourceEntity getResource()
	{
		return resource;
	}
}
