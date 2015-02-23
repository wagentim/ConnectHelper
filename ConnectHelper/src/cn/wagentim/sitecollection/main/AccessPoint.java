package cn.wagentim.sitecollection.main;

import cn.wagentim.sitecollection.handlers.EUPackageHandler;


public class AccessPoint
{
    public static void main(String[] args)
    {
//        EventQueue queue = new DefaultEventQueue();

        EUPackageHandler handler = new EUPackageHandler();
        handler.run();

    }
}
