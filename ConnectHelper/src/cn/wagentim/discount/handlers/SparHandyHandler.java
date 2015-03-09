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

import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.discount.connector.GetFile;
import cn.wagentim.discount.connector.GetPageContent;
import cn.wagentim.discount.core.FileHelper;
import cn.wagentim.discount.utils.FileType;
import cn.wagentim.entities.WebSiteEntity;
import cn.wagentim.managers.IPersistanceManager;
import cn.wagentim.managers.ObjectDBManager;

public class SparHandyHandler extends AbstractSiteHandler
{
	private IPersistanceManager manager = new ObjectDBManager();
	private static Long id = 0L;

	public SparHandyHandler(final WebSiteEntity site)
	{
		super(site);
		manager.connectDB(StringConstants.EMPTY_STRING, 0, "spar_handy");
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
	    builder.setScheme(site.getSchema());
	    builder.setHost(site.getHost());

	    try
        {
            GetPageContent getPage = new GetPageContent(builder.build(), site);
            getPage.run();
            id = manager.addOrUpdateEntity(site);
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
