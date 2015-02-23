package cn.wagentim.sitecollection.main;

import cn.wagentim.basicutils.Validator;
import cn.wagentim.job.AbstractJob;
import cn.wagentim.sitecollection.handlers.basic.ISiteHandler;

public class SiteParserJob extends AbstractJob
{
    private final ISiteHandler handler;

    public SiteParserJob(final ISiteHandler handler )
    {
        this.handler = handler;
    }

    @Override
    public void doJob()
    {
        if( Validator.assertNull(handler) )
        {
            return;
        }

        handler.run();
    }

}
