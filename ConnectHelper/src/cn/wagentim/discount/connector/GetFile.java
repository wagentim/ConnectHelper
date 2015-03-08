package cn.wagentim.discount.connector;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import cn.wagentim.basicutils.FileHelper;
import cn.wagentim.basicutils.Validator;
import cn.wagentim.discount.utils.FileType;
import cn.wagentim.entities.ResourceEntity;

public class GetFile extends AbstractThread
{

	private final String[] links;
	private final int type;
	private ResourceEntity resource;

	public GetFile(final String[] links, final int type)
	{
		this.links = links;
		this.type = type;
	}

	public void run()
	{
		if( Validator.isNullOrEmpty(links) || type == FileType.TYPE_UNKNOWN )
		{
			return;
		}
		
		HttpClientContext context = HttpClientContext.create();
        context.setCookieStore(getCookieStore());
        CloseableHttpClient httpclient = HttpClients.createDefault();
		
        for(String link : links)
        {
        	doGetFile(link, context, httpclient);
        }
	}

	private void doGetFile(String link, HttpClientContext context, CloseableHttpClient httpclient)
	{
		try
		{
			CloseableHttpResponse response = httpclient.execute(new HttpGet(link), context);
			try
			{
				if (response.getStatusLine().getStatusCode() < 300)
				{
					HttpEntity entity = response.getEntity();
					long fileSize = entity.getContentLength();
					resource = new ResourceEntity();
					assignValues(resource, entity);
					
					BufferedInputStream bis = new BufferedInputStream(entity.getContent());
					int inByte;
					while((inByte = bis.read()) != -1) 

					EntityUtils.consume(entity);
				}
			} finally
			{
				
				response.close();
			}

		} catch (ClientProtocolException ex)
		{
			ex.printStackTrace();
		} catch (IOException ex)
		{
			ex.printStackTrace();
		}
		
	}

	private void assignValues(ResourceEntity resource, HttpEntity entity)
	{
		if( Validator.isNull(resource) || Validator.isNull(entity) )
		{
			return;
		}
		
		resource.setLength(entity.getContentLength());
		
	}

}
