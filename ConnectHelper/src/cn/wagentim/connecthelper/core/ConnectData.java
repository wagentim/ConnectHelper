package cn.wagentim.connecthelper.core;

import cn.wagentim.connecthelper.threads.ICallback;

public final class ConnectData
{
    private ICallback callBack = null;
    
    public ICallback getCallback()
    {
	return callBack;
    }
    
    public void setCallback(ICallback callBack)
    {
	this.callBack = callBack;
    }

}
