package cn.wagentim.connecthelper.utils;

import java.util.AbstractList;
import java.util.ArrayList;
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

	public boolean add(E element)
	{

	}

}
