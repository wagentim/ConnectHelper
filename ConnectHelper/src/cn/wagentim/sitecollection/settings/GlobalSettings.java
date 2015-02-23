package cn.wagentim.sitecollection.settings;

import cn.wagentim.basicutils.StringConstants;

public class GlobalSettings
{
	private static String downloadLocation = StringConstants.EMPTY_STRING;

	public static String getDownloadLocation()
	{
		return downloadLocation;
	}

	public static void setDownloadLocation(String downloadLocation)
	{
		GlobalSettings.downloadLocation = downloadLocation;
	}

}
