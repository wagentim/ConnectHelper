package cn.wagentim.connect.impl;


public class Main
{
	public static void main(String[] args)
	{
		DefaultConnector dc = new DefaultConnector();
		String filePath = "C:\\temp";
		String url = "http://download.thinkbroadband.com/1GB.zip";
		dc.donwloadResource(url, filePath, 1, true);
	}
}
