
package cn.wagentim.connecthelper.threads;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import cn.wagentim.sitecollections.sites.IWebSite;


/**
 * Get standard web page source code
 *
 * @author bihu8398
 *
 */
public class PostAuthentication extends AbstractThread
{
    private final IWebSite site;

    public PostAuthentication(final IWebSite site)
    {
        this.site = site;
    }

	@Override
	public void run()
	{
	    CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(site.getHost(), site.getPort()),
                new UsernamePasswordCredentials(site.getUserName(), site.getPassword()));

        CloseableHttpClient httpclient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();

        HttpClientContext context = HttpClientContext.create();
        context.setCookieStore(getCookieStore());

        try
        {

            CloseableHttpResponse response = null;

            try
            {
                HttpGet httpget = new HttpGet(site.getURI());
                response = httpclient.execute(httpget, context);
                EntityUtils.consume(response.getEntity());
            }
            catch ( IOException | URISyntaxException e )
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