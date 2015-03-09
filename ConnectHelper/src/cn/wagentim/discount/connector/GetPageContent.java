
package cn.wagentim.discount.connector;

import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.entities.WebSiteEntity;

public class GetPageContent extends AbstractThread
{
    private final URI uri;
    private final WebSiteEntity site;
    
    public GetPageContent(URI uri, WebSiteEntity site)
    {
        this.uri = uri;
        this.site = site;
    }

    public void run()
    {

        HttpClientContext context = HttpClientContext.create();
        context.setCookieStore(getCookieStore());
        CloseableHttpClient httpclient = HttpClients.createDefault();

        try
        {
            HttpGet httpget = new HttpGet(uri);
            CloseableHttpResponse response = null;

            try
            {
                response = httpclient.execute(httpget, context);

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
