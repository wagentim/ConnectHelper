
package cn.wagentim.connecthelper.core;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public final class ConnectManager
{
    private static final int MAX_THREADS = 100;
    
    private static final PoolingHttpClientConnectionManager poolingManager;
    
    static
    {
        poolingManager = new PoolingHttpClientConnectionManager();
        poolingManager.setMaxTotal(MAX_THREADS);
    }
}
