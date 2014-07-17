package cn.wagentim.connecthelper.core;

import cn.wagentim.basicutils.StringConstants;
import de.wagentim.qlogger.channel.DefaultChannel;
import de.wagentim.qlogger.channel.LogChannel;
import de.wagentim.qlogger.logger.Log;
import de.wagentim.qlogger.service.QLoggerService;

public class ConnectManager
{
	private static LogChannel logger = QLoggerService.getChannel(QLoggerService.addChannel(new DefaultChannel("Connect Manager")));

	public static final String getContent(final String url, final String userName, final String password)
	{
		logger.log( Log.LEVEL_INFO, "ConnectManager#getContent: URL: %1; User Name: %2; password: %3 ", url, userName, password );

		return StringConstants.EMPTY_STRING;
	}
}
