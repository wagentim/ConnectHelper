package cn.wagentim.connecthelper.utils;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SelfList<E> extends AbstractList<E>
{
	private List<E> innerList = new ArrayList<E>();

	@Override
	public E get(int index)
	{
		return innerList.get(index);
	}

	@Override
	public int size()
	{
		return innerList.size();
	}
	
	@Override
	public void add(int paramInt, E paramE)
	{
		if( !contains(paramE))
		{
			innerList.add(paramInt, paramE);
		}
	}
	
	public boolean contains(Object paramObject)
	{
		return (indexOf(paramObject) >= 0);
	}

	public int indexOf(Object paramObject)
	{
		int i;
		if (paramObject == null)
		{
			for (i = 0; i < innerList.size(); ++i)
			{
				if (innerList.get(i) == null)
				{
					return i;
				}
			}
		}
		else
		{
			Iterator<E> it = innerList.iterator();
			i = 0;
			while(it.hasNext())
			{
				if( it.next() == paramObject )
				{
					return	i;
				}
				i++;
			}
			
		}
		return -1;
	}
}
