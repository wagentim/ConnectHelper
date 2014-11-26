
package cn.wagentim.connecthelper.threads;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import cn.wagentim.connecthelper.core.ConnectData;
import de.wagentim.qlogger.channel.DefaultChannel;
import de.wagentim.qlogger.channel.LogChannel;
import de.wagentim.qlogger.logger.Log;
import de.wagentim.qlogger.service.QLoggerService;

/**
 * Get standard web page source code
 *
 * @author bihu8398
 *
 */
public class StandardConnection implements Runnable
{

	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		
	}
//    private CloseableHttpClient httpClient = null;
//    private HttpContext context = null;
//    private HttpRequestBase httpget = null;
//    private LogChannel logger = QLoggerService.getChannel(QLoggerService
//            .addChannel(new DefaultChannel("STD_PAGE")));
//
//    private CloseableHttpResponse response = null;
//    private ConnectData data = null;
//
//    public StandardConnection(final ConnectData data)
//    {
//        this.data = data;
//        this.httpClient = data.getHttpClient();
//        this.context = HttpClientContext.create();
//        this.httpget = data.getHttpMethod();
//    }
//
//    @Override
//    public void run()
//    {
//        try
//        {
//            response = httpClient.execute(httpget,
//                    context);
//            int status = response.getStatusLine().getStatusCode();
//
//            if ( status > 200 && status < 300 )
//            {
//                HttpEntity entity = response.getEntity();
//                String content = EntityUtils.toString(entity, ContentType
//                        .getOrDefault(entity).getCharset());
//                EntityUtils.consume(entity);
//
//                // handle call back function
//                ICallback callBack;
//                if ( null != data && ( null != ( callBack = data.getCallback() ) ) )
//                {
//                    callBack.processFinished(content);
//                }
//            }
//            else
//            {
//                logger.log(Log.LEVEL_ERROR, "");
//            }
//
//        }
//        catch ( ClientProtocolException ex )
//        {
//            // Handle protocol errors
//        }
//        catch ( IOException ex )
//        {
//            // Handle I/O errors
//        }
//        finally
//        {
//            if ( null != response )
//            {
//                try
//                {
//                    response.close();
//                }
//                catch ( IOException e )
//                {
//                }
//                response = null;
//            }
//        }
//    }
}