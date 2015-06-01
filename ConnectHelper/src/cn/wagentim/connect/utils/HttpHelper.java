package cn.wagentim.connect.utils;

import java.net.URI;

import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.basicutils.Validator;

public final class HttpHelper
{
	public static final String getFileName(final URI uri)
	{
		if( Validator.isNull(uri) )
		{
			return StringConstants.EMPTY_STRING;
		}
		
		StringBuffer sb = new StringBuffer(uri.getPath());
		
		if( sb.length() <= 0 )
		{
			return StringConstants.EMPTY_STRING;
		}
		
		sb.substring(sb.lastIndexOf("/"));
		sb.subSequence(0, sb.lastIndexOf("."));
		return sb.toString();
	}
}
