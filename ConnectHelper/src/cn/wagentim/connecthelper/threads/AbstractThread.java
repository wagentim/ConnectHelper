package cn.wagentim.connecthelper.threads;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.impl.client.BasicCookieStore;

public abstract class AbstractThread extends Thread
{
    protected ICallback callback = null;
    protected List<ICallback> listeners = null;
    private static final int INITIAL_CALLBACK_NUMBER = 10;
    private BasicCookieStore cookie = null;

    public void setCallback(ICallback callback)
    {
        this.callback = callback;
    }

    public void setCookieStore(final BasicCookieStore cookie)
    {
        this.cookie = cookie;
    }

    public BasicCookieStore getCookieStore()
    {
        if( null == cookie )
        {
            cookie = new BasicCookieStore();
        }
        return cookie;
    }

    public void addCallback(ICallback callback)
    {
    	if( null == callback )
    	{
    		return;
    	}

    	if( null == listeners )
    	{
    		listeners = new ArrayList<ICallback>(INITIAL_CALLBACK_NUMBER);
    	}

    	Iterator<ICallback> it = listeners.iterator();

    	while( it.hasNext() )
    	{
    		ICallback item = it.next();

    		// the added call back is already in the list. then we ignore it
    		if( item == callback )
    		{
    			return;
    		}
    	}

    	listeners.add(callback);
    }

    public void removeCallback(ICallback callback)
    {
    	if( null == callback || null == listeners || listeners.isEmpty() )
    	{
    		return;
    	}

    	Iterator<ICallback> it = listeners.iterator();

    	while( it.hasNext() )
    	{
    		ICallback item = it.next();

    		// the added call back is already in the list. then we ignore it
    		if( item == callback )
    		{
    			it.remove();
    			return;
    		}
    	}
    }

    protected void notifyProcessFinished(Object object)
    {
    	if( null == listeners || listeners.isEmpty() )
    	{
    		return;
    	}

    	Iterator<ICallback> it = listeners.iterator();

    	while( it.hasNext() )
    	{
    		it.next().processFinished(object);
    	}
    }
}
