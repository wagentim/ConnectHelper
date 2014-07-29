package cn.wagentim.connecthelper.core;

import cn.wagentim.basicutils.StringConstants;
import de.wagentim.qlogger.channel.DefaultChannel;
import de.wagentim.qlogger.channel.LogChannel;
import de.wagentim.qlogger.logger.Log;
import de.wagentim.qlogger.service.QLoggerService;

public final class ConnectManager
{
	private IConnectConfigure configure = null;

	private LogChannel logger = QLoggerService.getChannel(QLoggerService.addChannel(new DefaultChannel("ConnectHelper")));

	public ConnectManager()
	{
	}

	public ConnectManager(IConnectConfigure configure)
	{
		this.configure = configure;
	}

	public final String getContent()
	{
		logger.log( Log.LEVEL_INFO, "ConnectManager#getContent: URL: %1; User Name: %2; password: %3 " );

		if( null != configure )
		{
			logger.log(Log.LEVEL_INFO, "Using %1 to get web content", this.configure.getClass().getName());
		}

		return StringConstants.EMPTY_STRING;
	}

	public LogChannel getLogger()
	{
		return logger;
	}

	public void udpateConnectConfigure(IConnectConfigure configure)
	{
		this.configure = configure;
	}
}
