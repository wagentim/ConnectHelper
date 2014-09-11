package cn.wagentim.connecthelper.threads;

public abstract class AbstractThread extends Thread
{
	protected ICallback callback = null;
	
	public void setCallback(ICallback callback)
	{
		this.callback = callback;
	}
}