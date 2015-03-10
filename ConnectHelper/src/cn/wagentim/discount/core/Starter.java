package cn.wagentim.discount.core;

import java.util.List;

import cn.wagentim.discount.handlers.HandlerManager;
import cn.wagentim.discount.handlers.ISiteHandler;

public class Starter
{
    public static void main(String[] args)
    {
        List<ISiteHandler> handlers = HandlerManager.getHandlers();

        if( !handlers.isEmpty() )
        {
            for(ISiteHandler handler : handlers)
            {
                handler.exec();
            }
        }
    }
}
