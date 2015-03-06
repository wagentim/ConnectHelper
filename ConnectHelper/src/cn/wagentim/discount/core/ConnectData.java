package cn.wagentim.discount.core;

import cn.wagentim.discount.connector.ICallback;

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
