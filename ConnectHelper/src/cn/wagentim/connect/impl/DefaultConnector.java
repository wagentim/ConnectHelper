package cn.wagentim.connect.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.wagentim.basicutils.FileHelper;
import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.basicutils.Validator;
import cn.wagentim.connect.core.IConnector;

/**
 * Execute Internet Basic Connections for getting web page, login, download...
 * <p>
 * Each instance of this should only handle one side. Do not use one instance to handle multiple sites, it will cause some unexpected errors <br>
 * For simulating the web operation please following the sequence, because the cookie should be correctly updated.
 * </p>
 * 
 * @author bihu8398
 *
 */
public class DefaultConnector implements IConnector
{
    private HttpClientContext context = null;
    private CloseableHttpClient httpclient = null;
    private CookieStore cookieStore = null;
    private Logger logger = LogManager.getLogger(DefaultConnector.class);
    
    private String login_username_key = "username";
    private String login_password_key = "password";

    public DefaultConnector()
    {
    	cookieStore = new BasicCookieStore();
    	context = HttpClientContext.create();
    	context.setCookieStore(cookieStore);
    	httpclient = HttpClients.createDefault();
    }
    
    // Get the page content
    @Override
	public String get(String url)
    {
    	if( Validator.isNullOrEmpty(url) )
    	{
    		logger.error("URL is NULL or Empty");
    		return StringConstants.EMPTY_STRING;
    	}
    	
    	URI uri = null;
		
    	try
		{
			uri = new URI(url);
		} 
		catch (URISyntaxException e1)
		{
			uri = null;
			logger.error("GetPageContent#run Cannot create URI object");
			return StringConstants.EMPTY_STRING;
		}
    	
    	logger.info("Process the link: " + url);
    	
        HttpGet httpget = new HttpGet(uri);
        CloseableHttpResponse response = null;
        try
        {
            response = httpclient.execute(httpget, context);
            if( !isHttpResponseOk(response) )
            {
            	logger.error(response.getStatusLine().getReasonPhrase());
            	return StringConstants.EMPTY_STRING;
            }
            
            String pageContent = getPageContent(response);
            EntityUtils.consume(response.getEntity());
            
            return pageContent;
        }
        catch ( IOException e )
        {
        	logger.error("Cannot get required web page!");
        	return StringConstants.EMPTY_STRING;
        }
        
        finally
        {
            try
            {
                response.close();
            }
            catch ( IOException e )
            {
            	logger.error("Close Response Error");
            }
        }
    }
    
    /**
     * Using User Name and Password to login a web page. 
     * 
     * @param url
     * @param username
     * @param password
     * @return
     */
    public String postWithSimpleAuth(String url, String username, String password)
    {
    	if( Validator.isNullOrEmpty(url) )
    	{
    		logger.error("URL is NULL or Empty");
    		return StringConstants.EMPTY_STRING;
    	}
    	
    	if( Validator.isNullOrEmpty(username) )
    	{
    		logger.error("User name is NULL or Empty");
    		return StringConstants.EMPTY_STRING;
    	}
    	
    	if( Validator.isNullOrEmpty(password) )
    	{
    		logger.error("Password is NULL or Empty");
    		return StringConstants.EMPTY_STRING;
    	}
    	
    	URI uri = null;
		
    	try
		{
			uri = new URI(url);
		} 
		catch (URISyntaxException e1)
		{
			uri = null;
			logger.error("GetPageContent#run Cannot create URI object");
			return StringConstants.EMPTY_STRING;
		}
    	
    	logger.info("Login the page: " + url);
    	
    	HttpPost httpPost = new HttpPost(uri);
    	List <NameValuePair> nvps = new ArrayList <NameValuePair>();
    	nvps.add(new BasicNameValuePair(login_username_key, username));
    	nvps.add(new BasicNameValuePair(login_password_key, password));
    	try
		{
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		} 
    	catch (UnsupportedEncodingException e1)
		{
			e1.printStackTrace();
		}
    	CloseableHttpResponse response = null;
        try
        {
            response = httpclient.execute(httpPost, context);
            if( !isHttpResponseOk(response) )
            {
            	logger.error(response.getStatusLine().getReasonPhrase());
            	return StringConstants.EMPTY_STRING;
            }
            
            String pageContent = getPageContent(response);
            EntityUtils.consume(response.getEntity());
            
            return pageContent;
        }
        catch ( IOException e )
        {
        	logger.error("Cannot get required web page!");
        	return StringConstants.EMPTY_STRING;
        }
        
        finally
        {
            try
            {
                response.close();
            }
            catch ( IOException e )
            {
            	logger.error("Close Response Error");
            }
        }
    }
    
    /**
     * Try to get the resource once and according to the header information create {@link DataInfo} to caching required informations
     * 
     * @param url
     * @return
     */
    public DataInfo getDataInfo(String url)
    {
    	if( Validator.isNullOrEmpty(url) )
    	{
    		logger.error("URL is NULL or Empty");
    		return null;
    	}
    	
    	URI uri = null;
		
    	try
		{
			uri = new URI(url);
		} 
		catch (URISyntaxException e1)
		{
			uri = null;
			logger.error("GetPageContent#run Cannot create URI object");
			return null;
		}
    	
    	logger.info("Process the link: " + url);
    	
        HttpGet httpget = new HttpGet(uri);
        CloseableHttpResponse response = null;
        try
        {
            response = httpclient.execute(httpget, context);
            if( !isHttpResponseOk(response) )
            {
            	logger.error(response.getStatusLine().getReasonPhrase());
            	return null;
            }
            
            return parserDataInfo(response.getAllHeaders(), url);
            
        }
        catch ( IOException e )
        {
        	logger.error("Cannot get required web page!");
        	return null;
        }
        
        finally
        {
            try
            {
                response.close();
            }
            catch ( IOException e )
            {
            	logger.error("Close Response Error");
            }
        }
    }
    
    private DataInfo parserDataInfo(Header[] allHeaders, String url)
	{
    	if( null == allHeaders || allHeaders.length <= 0 || Validator.isNullOrEmpty(url) )
    	{
    		return null;
    	}

    	String fileName = handleFileNameFromURL(url);
    	
    	String fileType = null, fileLength = null;
    	
    	for( Header header : allHeaders )
    	{
    		String key = header.getName();
    		
    		if("Content-Type".equals(key))
    		{
    			fileType = handleContentType(header);
    		}
    		else if("Content-Length".equals(key))
    		{
    			fileLength = handleContextLength(header);
    		}
    	}
    	
    	if( null == fileName )
    	{
    		fileName = "tmp." + fileType; 
    	}
    	
    	DataInfo info = new DataInfo(fileName, Long.parseLong(fileLength));
    	
		return info;
	}

	private String handleFileNameFromURL(String url)
	{
		int index = url.lastIndexOf("/");
		String possibleFile = url.substring(index + 1);
		
		if( !possibleFile.contains(".") )
		{
			return null;
		}
		
		return possibleFile;
	}

	private String handleContextLength(Header header)
	{
		return header.getValue();
	}

	private String handleContentType(Header header)
	{
		String value = header.getValue();
		int index = value.indexOf("/");
		if( index >= 0 )
		{
			value = value.substring(index + 1);
		}
		else
		{
			return null;
		}
		
		return value;
		
	}

	/**
     * Download resource from Internet and save it to file
     * 
     * @param uri
     * @param filePath
     * @param thread
     */
    public void donwloadResource(String uri, String filePath, int thread, boolean overwriteFile)
    {
    	// step 1. check the parameters
    	if( Validator.isNullOrEmpty(uri) )
    	{
    		logger.error("The given resource uri is wrong!");
    		return;
    	}
    	
    	if( !FileHelper.checkDirectoryRecusively(filePath) )
    	{
    		logger.error("DefaultConnector#donwloadResource Cannot create local file saving directories");
    		return;
    	}
    	
    	DataInfo dataInfo = getDataInfo(uri);
    	
    	File f = new File(filePath, dataInfo.getName());
    	RandomAccessFile file = null;
    	
    	try
		{
			file = new RandomAccessFile(f, "rw");
		} 
    	catch (FileNotFoundException e2)
		{
			e2.printStackTrace();
		}
    	
    	boolean continueDownload = false;
    	
    	if( !f.exists() )
    	{
    		try
			{
				f.createNewFile();
				
			} catch (IOException e)
			{
				e.printStackTrace();
			}
    	}
    	else if( f.length() == dataInfo.getLength() && !overwriteFile )
		{
			logger.error("DefaultConnector#donwloadResource File already existed. Abort!");
			return;
    	}
    	else
    	{
    		continueDownload = true;
    	}
    	
    	if( thread < 1 || thread > 5 )
    	{
    		thread = 1;
    	}
    	
    	URI link = null;
		
    	try
		{
			link = new URI(uri);
		} 
		catch (URISyntaxException e1)
		{
			link = null;
			logger.error("GetPageContent#run Cannot create URI object");
			return;
		}
    	
    	logger.info("Start download data: " + dataInfo.getName());
    	
        HttpGet httpget = new HttpGet(link);
        CloseableHttpResponse response = null;
        
        if(continueDownload)
        {
        	StringBuffer srange = new StringBuffer("bytes ");
        	srange.append(f.length());
        	srange.append("-");
        	srange.append("/");
        	srange.append(dataInfo.getLength());
        	
        	Header range = new BasicHeader("Content-Range", srange.toString());
        	httpget.addHeader(range);
        }
        
        try
        {
            response = httpclient.execute(httpget, context);
            
            if( !isHttpResponseOk(response) )
            {
            	logger.error(response.getStatusLine().getReasonPhrase());
            	return;
            }
            
            BufferedInputStream bis = new BufferedInputStream(response.getEntity().getContent());
            
            ByteBuffer bb = ByteBuffer.allocate(1024);
        	StringBuffer sb = new StringBuffer();
        	
        	FileChannel fc = file.getChannel();
        	
        	if( continueDownload )
        	{
        		fc.position(f.length());
        	}
        	
        	int inByte;
        	long length = dataInfo.getLength();
        	long downloaded = continueDownload ? f.length() : 0L;
        	
        	while((inByte = bis.read(bb.array())) != -1)
        	{
        		sb.delete(0, sb.length());
        		fc.write(bb);
        		downloaded += inByte;
        		sb.append(downloaded);
        		sb.append(" / ");
        		sb.append(length);
        		bb.clear();
        		System.out.println(sb.toString());
        	}
        	
        	bis.close();
        	fc.close();
        	
        }
        catch ( IOException e )
        {
        	logger.error("Cannot get required web page!");
        }
        
        finally
        {
            try
            {
                response.close();
            }
            catch ( IOException e )
            {
            	logger.error("Close Response Error");
            }
        }
    }
    
	public void close()
    {
    	if( null != context )
    	{
    		context = null;
    	}
    	
    	if( null != httpclient )
    	{
    		try
			{
				httpclient.close();
			} 
    		catch (IOException e)
			{
    			logger.error("Close Http Client Error");
			}
    		
    		httpclient = null;
    	}
    }
    
    public CloseableHttpClient getHttpClient()
    {
    	return httpclient;
    }
    
    public CookieStore getCookieStore()
    {
    	return cookieStore;
    }
    
    public HttpClientContext getClientContext()
    {
    	return context;
    }
    
    public static boolean isHttpResponseOk(CloseableHttpResponse response)
	{
		StatusLine statusLine = response.getStatusLine();

		if (statusLine.getStatusCode() >= 300)
		{
			return false;
		}

		return true;
	}
    
    public static String getPageContent(final CloseableHttpResponse response) throws IllegalStateException, IOException
	{
		HttpEntity entity = response.getEntity();
		
		return EntityUtils.toString(entity);
	}

	public String getLogin_username_key()
	{
		return login_username_key;
	}

	public void setLogin_username_key(String login_username_key)
	{
		this.login_username_key = login_username_key;
	}

	public String getLogin_password_key()
	{
		return login_password_key;
	}

	public void setLogin_password_key(String login_password_key)
	{
		this.login_password_key = login_password_key;
	}

}
