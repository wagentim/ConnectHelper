package cn.wagentim.connecthelper.core;

import cn.wagentim.basicutils.BasicUtils;
import cn.wagentim.basicutils.StringConstants;
import de.wagentim.qlogger.channel.DefaultChannel;
import de.wagentim.qlogger.channel.LogChannel;
import de.wagentim.qlogger.logger.Log;
import de.wagentim.qlogger.service.QLoggerService;

public final class ConnectManager
{
	private static final LogChannel logger = QLoggerService.getChannel(QLoggerService.addChannel(new DefaultChannel("ConnectHelper")));

	public static final String getContent(final IConnectConfigure configure)
	{
		if( !BasicUtils.isNull(configure) )
		{
			logger.log(Log.LEVEL_INFO, "Try to get contents from Website: %1", configure.getWebsiteName());
		}
		return StringConstants.EMPTY_STRING;
	}

	public LogChannel getLogger()
	{
		return logger;
	}

}
