package cn.wagentim.sitecollection.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import cn.wagentim.sitecollections.sites.EUPackage;
import cn.wagentim.sitecollections.sites.IWebSite;

public class EUPackageHandler
{
    private IWebSite site = new EUPackage();

    public void run()
    {
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT), new UsernamePasswordCredentials(site.getUserName(), site.getPassword()));

        CloseableHttpClient httpclient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).setRedirectStrategy(new DefaultRedirectStrategy()).build();
        CookieStore cookieStore = new BasicCookieStore();
        HttpClientContext context = HttpClientContext.create();
        context.setCookieStore(cookieStore);

        HttpPost post = new HttpPost("http://www.eupackage.com/login.do");
        CloseableHttpResponse response;
        try
        {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("email", site.getUserName()));
            nameValuePairs.add(new BasicNameValuePair("password", site.getPassword()));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8);
            entity.setContentType("application/x-www-form-urlencoded");
            post.setEntity(entity);

            response = httpclient.execute(post, context);
            System.out.println(EntityUtils.toString(response.getEntity()));
            EntityUtils.consume(response.getEntity());

            HttpGet httpget = new HttpGet("http://www.eupackage.com/createPackageCN.do");
            response = httpclient.execute(httpget, context);
            System.out.println(EntityUtils.toString(response.getEntity()));
            EntityUtils.consume(response.getEntity());
        }
        catch ( IOException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
//        EventQueue queue = new DefaultEventQueue();

        EUPackageHandler handler = new EUPackageHandler();
        handler.run();

    }
}
