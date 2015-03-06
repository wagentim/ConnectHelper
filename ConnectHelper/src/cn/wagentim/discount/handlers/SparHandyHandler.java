package cn.wagentim.discount.handlers;

import java.net.URISyntaxException;
import java.nio.charset.Charset;

import org.apache.http.client.utils.URIBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.wagentim.discount.connector.GetPageContent;
import cn.wagentim.discount.core.FileHelper;
import cn.wagentim.discount.sites.IWebsite;
import cn.wagentim.discount.sites.SparHandy;
import cn.wagentim.discount.utils.Validator;

public class SparHandyHandler extends AbstractSiteHandler
{
    private static final String tmpPath = "C:/tmp/bin/tmp.txt";

	public SparHandyHandler(final IWebsite site)
	{
		super(site);
	}

	@Override
	public String exec()
	{
		grabDiscountPicOnMainPage();
		return formatResult();
	}

	public void grabDiscountPicOnMainPage()
	{
	    URIBuilder builder = new URIBuilder();
	    builder.setScheme("http");
	    builder.setHost(site.getDomain());

	    try
        {
            GetPageContent getPage = new GetPageContent(builder.build());
            getPage.run();
            String content = getPage.getPageContent();

            if( !Validator.isNullOrEmpty(content) )
            {
                FileHelper.writeToFile(content, tmpPath, Charset.forName("utf-8"));
            }
        }
        catch ( URISyntaxException e )
        {
            e.printStackTrace();
        }
	}

	public void parserDiscountPicOnMainPage()
    {
	    String page = FileHelper.readFile(tmpPath, Charset.forName("utf-8"));
	    Document document = Jsoup.parse(page);
	    Elements suggestionItems = document.getElementsByClass("overview");
	    if( null != suggestionItems && suggestionItems.size() > 0 )
	    {
	        for(Element e : suggestionItems)
	        {
	            getImage(e);
	        }
	    }

    }

	private String getImage(Element e)
    {
	    Elements imgs = e.getElementsByTag("img");

	    if( null != imgs && imgs.size() > 0)
	    {
	        for(Element pic: imgs)
	        {
	            String imgLink = pic.attr("src");
	            
	            
	            if( !imgLink.contains("mini"))
	            {
	                System.out.println(site.getDomain()+"/"+imgLink);
	            }
	        }
	    }

        return null;
    }

    public static void main(String[] args)
	{
	    SparHandyHandler handler = new SparHandyHandler(new SparHandy());

//	    handler.grabDiscountPicOnMainPage();
	    handler.parserDiscountPicOnMainPage();
	}

    private String formatResult()
	{
		return null;
	}

}
