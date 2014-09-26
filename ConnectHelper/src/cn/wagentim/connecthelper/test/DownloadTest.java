package cn.wagentim.connecthelper.test;

import cn.wagentim.connecthelper.core.ConnectManager;
import cn.wagentim.connecthelper.core.URLType;
import cn.wagentim.connecthelper.threads.ICallback;


public class DownloadTest
{
	public static void main(String[] args)
	{
		String link = "http://www.kfc.de/sites/default/files/promotion_201409/KFC_Coupons_2014_09.pdf";
		ConnectManager.getRequiredData(link, URLType.TYPE_STANDARD_DATA, new ICallback()
		{
			
			@Override
			public void processFinished(Object data)
			{
				// TODO Auto-generated method stub
				
			}
		});
	}
}
