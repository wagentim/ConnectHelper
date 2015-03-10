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

						byte[] data = getContentAsByteArray(entity.getContent());

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

    private byte[] getContentAsByteArray(final InputStream content)
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] tmp = new byte[4096];
        int ret = 0;

        try
        {
            while((ret = content.read(tmp)) > 0)
            {
                bos.write(tmp, 0, ret);
            }
        }
        catch ( IOException e )
        {
            e.printStackTrace();
            return null;
        }

        byte[] myArray = bos.toByteArray();

        try
        {
            bos.close();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
            return null;
        }

        return myArray;
    }

	public ResourceEntity getResource()
	{
		return resource;
	}
}
