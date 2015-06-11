package cn.wagentim.connect.impl;

/**
 * 
 * @author bihu8398
 *
 */
public class DataInfo
	{
		private String name;
		private long length;
		
		public DataInfo()
		{}
		
		public DataInfo(String name, long length)
		{
			this.name = name;
			this.length = length;
		}

		public String getName()
		{
			return name;
		}
		
		public void setName(String name)
		{
			this.name = name;
		}

		public long getLength()
		{
			return length;
		}
		
		public void setLength(long length)
		{
			this.length = length;
		}

		@Override
		public String toString()
		{
			return "DataInfo [name=" + name + ", length=" + length + "]";
		}
	}