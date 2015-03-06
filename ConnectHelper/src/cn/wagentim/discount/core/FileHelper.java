package cn.wagentim.discount.core;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import cn.wagentim.basicutils.BasicUtils;
import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.discount.utils.Validator;

public final class FileHelper
{
	public static String readFile(final String filePath, final Charset charSet)
	{
		if( Validator.isNullOrEmpty(filePath) )
		{
			return StringConstants.EMPTY_STRING;
		}

		FileInputStream fin = null;
		InputStreamReader reader = null;
		BufferedReader br = null;

		try
		{
			fin = new FileInputStream(filePath);
			reader = new InputStreamReader(fin, charSet);
			br = new BufferedReader(reader);

			String str;
			StringBuffer sb = new StringBuffer();

			while ((str = br.readLine()) != null)
			{
			    sb.append(str);
			}

			br.close();
			reader.close();
			fin.close();

			return sb.toString();

		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if( null != fin )
			{
				fin = null;
			}

			if( null != reader )
			{
				reader = null;
			}

			if( null != br )
			{
				br = null;
			}
		}

		return StringConstants.EMPTY_STRING;
	}

    public static final boolean isFileReady(final String filePath)
    {
        final Path path = Paths.get(filePath);

        if( !Files.exists(path) )
        {
            try
            {
                Files.createFile(path);
                // TODO check if the directory is not there, the create file method can also create the directory?
            }
            catch (IOException e)
            {
                return false;
            }
        }

        return true;
    }

    public static final boolean writeToFile(final String content, final String filePath, final Charset charset)
    {
        if( !isFileReady(filePath) || BasicUtils.isNullOrEmpty(content) )
        {
            return false;
        }

        final Path path = Paths.get(filePath);

        try
        {
            Files.write(path, content.getBytes(charset), StandardOpenOption.WRITE);
        }
        catch (IOException e)
        {
            return false;
        }


        return true;
    }
}
