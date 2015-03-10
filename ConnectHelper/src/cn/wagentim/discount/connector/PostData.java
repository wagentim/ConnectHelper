
package cn.wagentim.discount.connector;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.entities.WebSiteEntity;


/**
 * Get standard web page source code
 *
 * @author bihu8398
 *
 */
public class PostData extends AbstractThread
{
    private final WebSiteEntity site;
    private final URI uri;
    private final List<NameValuePair> values;

    public PostData(final WebSiteEntity site, final URI uri, final List<NameValuePair> values)
    {
        this.site = site;
        this.uri = uri;
        this.values = values;
    }

	@Override
	public void run()
	{
	    HttpClientContext context = HttpClientContext.create();
        context.setCookieStore(getCookieStore());
        CloseableHttpClient httpclient = HttpClients.createDefault();

        try
        {

            CloseableHttpResponse response = null;

            try
            {
                HttpPost httppost = new HttpPost(uri);
                httppost.setEntity(new UrlEncodedFormEntity(values, "utf-8"));
                response = httpclient.execute(httppost, context);

                if ( HttpStatus.SC_OK == response.getStatusLine().getStatusCode() )
                {
                    site.setPageContent(EntityUtils.toString(response.getEntity()));
                }
                else
                {
                    site.setPageContent(StringConstants.EMPTY_STRING);
                }

                EntityUtils.consume(response.getEntity());
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    response.close();
                }
                catch ( IOException e )
                {
                    e.printStackTrace();
                }
            }
        }
        finally
        {
            try
            {
                httpclient.close();
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
        }

	}
}