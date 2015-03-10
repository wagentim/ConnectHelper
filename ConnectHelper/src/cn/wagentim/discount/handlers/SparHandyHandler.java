package cn.wagentim.discount.handlers;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.discount.connector.GetPageContent;
import cn.wagentim.discount.connector.PostData;
import cn.wagentim.entities.WebSiteEntity;
import cn.wagentim.managers.IPersistanceManager;
import cn.wagentim.managers.ObjectDBManager;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SparHandyHandler extends AbstractSiteHandler
{
    private static final String PATH_REQUIRE_HANDIES = "/include/content/handysuche/suchformular_ajax.inc.php";

	private IPersistanceManager manager = new ObjectDBManager();
	private BasicCookieStore cookie = null;
	private GetPageContent getPage;
	private PostData post;
	private Map<String, String> handies;
	private int handyCounter = 0;

	public SparHandyHandler(final WebSiteEntity site)
	{
		super(site);
		manager.connectDB(StringConstants.EMPTY_STRING, 0, "spar_handy.odb");
	}

	@Override
	public String exec()
	{
//		grabDiscountPicOnMainPage();
//		parserDiscountPicOnMainPage();
		processTarif();
		return formatResult();
	}

	private void processTarif()
	{
//	    Long id = getAllHandies();
	    parserAllHandies(5L);
	    printDevices();
	}

	private void printDevices()
    {
	    if( null != handies )
	    {
	        for(String handy : handies.keySet())
	        {
	            System.out.println(handy);
	        }
	    }
    }

    private void parserAllHandies(final Long id)
    {
	    String page = ((WebSiteEntity)manager.getEntity(WebSiteEntity.class, id)).getPageContent();

        if( null == page || page.length() <= 0 )
        {
            return;
        }

        JsonParser parser = new JsonParser();
        JsonElement json = parser.parse(page);
        if( json.isJsonObject() )
        {
            JsonObject object = (JsonObject)json;
            Set<Entry<String, JsonElement>> entrySet = object.entrySet();
            for(Map.Entry<String, JsonElement> entry : entrySet )
            {
                if( entry.getKey().equals("searchCounter")) // get handy counter
                {
                    handyCounter = entry.getValue().getAsInt();
                    handies = new HashMap<String, String>(handyCounter);
                }
                else if( entry.getKey().equals("devices") ) // get handy list
                {
                    JsonObject devices = (JsonObject)entry.getValue();

                    for(Map.Entry<String, JsonElement> dev : devices.entrySet() )
                    {
                        String handy = dev.getValue().getAsString();

                        if( handy.length() <= 0 )
                        {
                            continue;
                        }

                        handies.put(handy, dev.getKey());
                    }
                }
            }
        }
    }

    private Long getAllHandies()
    {
	    URIBuilder builder = new URIBuilder();
        builder.setScheme(site.getSchema());
        builder.setHost(site.getHost());
        builder.setPath(PATH_REQUIRE_HANDIES);
        List<NameValuePair> values = new ArrayList<NameValuePair>();
        values.add(new BasicNameValuePair("hersteller", "0"));
	    try
        {
            post = new PostData(site, builder.build(), values);
            if( null != cookie )
            {
                post.setCookieStore(cookie);
            }
            post.run();
            cookie = post.getCookieStore();
            return manager.addOrUpdateEntity(site);
        }
        catch ( URISyntaxException e )
        {
            e.printStackTrace();
        }

	    return -1L;
    }

    public Long grabDiscountPicOnMainPage()
	{
	    URIBuilder builder = new URIBuilder();
	    builder.setScheme(site.getSchema());
	    builder.setHost(site.getHost());

	    try
        {
            getPage = new GetPageContent(builder.build(), site);
            getPage.run();
            cookie = getPage.getCookieStore();
            return manager.addOrUpdateEntity(site);
        }
        catch ( URISyntaxException e )
        {
            e.printStackTrace();
        }

	    return -1L;
	}

	public void parserDiscountPicOnMainPage(final Long id)
    {
	    String page = ((WebSiteEntity)manager.getEntity(WebSiteEntity.class, id)).getPageContent();

	    if( null == page || page.length() <= 0 )
	    {
	        return;
	    }

	    Document document = Jsoup.parse(page);
	    Elements suggestionItems = document.getElementsByClass("overview");
	    if( null != suggestionItems && suggestionItems.size() > 0 )
	    {
	    	List<String> imgs = new ArrayList<String>();
	        for(Element e : suggestionItems)
	        {
	            imgs.addAll(getImages(e));
	        }

	        if( !imgs.isEmpty() )
	        {
	            for(String s : imgs)
	            {
	                System.out.println(s);
	            }
	        }
	    }


    }

//	private void downloadImages(List<String> imgs)
//	{
//		if(imgs.isEmpty() )
//		{
//			return;
//		}
//
//		GetFile gf = new GetFile(imgs.toArray(new String[imgs.size()]), FileType.TYPE_IMAGE);
//		gf.run();
//
//	}

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
	                imgLinks.add("http://"+site.getHost()+"/"+imgLink);
	            }
	        }
	    }

        return imgLinks;
    }

    private String formatResult()
	{
		return null;
	}

}
