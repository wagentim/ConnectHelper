package cn.wagentim.connecthelper.core;

import org.apache.http.auth.BasicUserPrincipal;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import cn.wagentim.basicutils.BasicUtils;
import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.connecthelper.threads.AbstractThread;
import cn.wagentim.connecthelper.threads.GetFileThread;
import cn.wagentim.connecthelper.threads.StandardConnection;
import cn.wagentim.connecthelper.threads.ICallback;
import de.wagentim.qlogger.channel.DefaultChannel;
import de.wagentim.qlogger.channel.LogChannel;
import de.wagentim.qlogger.logger.Log;
import de.wagentim.qlogger.service.QLoggerService;

public final class ConnectManager
{
    private static final PoolingHttpClientConnectionManager poolingManager;
    private static final CloseableHttpClient httpClient;
    public static final LogChannel logger;

    static
    {
	poolingManager = new PoolingHttpClientConnectionManager();
	httpClient = HttpClients.custom().setConnectionManager(poolingManager)
		.build();
	logger = QLoggerService.getChannel(QLoggerService
		.addChannel(new DefaultChannel("ConnectManager")));
    }

    public static boolean applyStandardConnection(final ConnectData data)
    {
	if(BasicUtils.isNull(data))
	{
	    logger.log(Log.LEVEL_ERROR, "Connect Data is Null");
	    return false;
	}
	
	Thread t = new Thread(new StandardConnection(data));
	
	return true;
    }

}
