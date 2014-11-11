
package cn.wagentim.connecthelper.core;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public final class ConnectManager implements IConnType
{
    private static final int MAX_THREADS = 100;
    
    private static final PoolingHttpClientConnectionManager poolingManager;
    
    static
    {
        poolingManager = new PoolingHttpClientConnectionManager();
        poolingManager.setMaxTotal(MAX_THREADS);
    }
    
    public void applyConnection(final ConnectData data)
    {
        if( null == data )
        {
            return;
        }
        
        int connType = data.getConnType();
        
        switch(connType)
        {
            default:
                applyDefaultConnection(data);
        }
    }

    private void applyDefaultConnection(ConnectData data)
    {
        
    }
}
