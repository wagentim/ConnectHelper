package cn.wagentim.discount.handlers;

import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.utils.URIBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.wagentim.basicutils.Validator;
import cn.wagentim.discount.connector.GetFile;
import cn.wagentim.discount.connector.GetPageContent;
import cn.wagentim.discount.core.FileHelper;
import cn.wagentim.discount.sites.IWebsite;
import cn.wagentim.discount.sites.SparHandy;
import cn.wagentim.discount.utils.FileType;

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
		processTarif();
		return formatResult();
	}

	private void processTarif()
	{
		
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
	    	List<String> imgs = new ArrayList<String>();
	        for(Element e : suggestionItems)
	        {
	            imgs.addAll(getImages(e));
	        }
	        downloadImages(imgs);
	    }
	    

    }

	private void downloadImages(List<String> imgs)
	{
		if(imgs.isEmpty() )
		{
			return;
		}
		
		GetFile gf = new GetFile(imgs.toArray(new String[imgs.size()]), FileType.TYPE_IMAGE);
		gf.run();
		
	}

	public List<String> getImages(Element e)
    {
	    Elements imgs = e.getElementsByTag("img");
	    List<String> imgLinks = new ArrayList<String>();

	    if( null != imgs && imgs.size() > 0)
	    {
	        for(Element pic: imgs)
	        {
	            String imgLink = pic.attr("src");
	            
	            
	            if( !imgLink.contains("mini"))
	            {
	                imgLinks.add("http://"+site.getDomain()+"/"+imgLink);
	            }
	        }
	    }

        return imgLinks;
    }

    public static void main(String[] args)
	{
	    SparHandyHandler handler = new SparHandyHandler(new SparHandy());

//	    handler.grabDiscountPicOnMainPage();
//	    handler.parserDiscountPicOnMainPage();
	}

    private String formatResult()
	{
		return null;
	}

}
