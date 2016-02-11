package com.base.engine.core.math;
import java.util.ArrayList;

public interface GameObjectContainer 
{
	public ArrayList<Object> getContents();
	
	@SuppressWarnings("unchecked")
	public default <E> ArrayList<E> getType(String type)
	{
		ArrayList<E> out = new ArrayList<E>();
		for(Object object : getContents())
		{
			if(object.getClass().getSimpleName().equals(type))
			{
				out.add((E)object);
			}
			else 
			{
				@SuppressWarnings("rawtypes")
				Class supclass = object.getClass().getSuperclass();
				while(supclass.getSimpleName() != "Object")
				{
					if(supclass.getSimpleName().equals(type))
					{
						out.add((E)object);
						break;
					}
					supclass = supclass.getSuperclass();
				}
			}
		}
		return out;
	}
	
	@SuppressWarnings("unchecked")
	public default <E> ArrayList<E> getAllOfType(String type)
	{
		ArrayList<E> out = new ArrayList<E>();
		for(Object object : getContents())
		{
			if(object instanceof GameObjectContainer)
			{
				out.addAll(((GameObjectContainer)object).getAllOfType(type));
			}
			else if(object.getClass().getSimpleName().equals(type))
			{
				out.add((E)object);
			}
		}
		return out;
	}
	
	@SuppressWarnings("unchecked")
	public default <E> ArrayList<E> getType(E type)
	{
		ArrayList<E> out = new ArrayList<E>();
		for(Object object : getContents())
		{
			if(object.getClass().isInstance(type))
			{
				out.add((E)object);
			}
		}
		return out;
	}
	
	@SuppressWarnings("unchecked")
	public default <E> ArrayList<E> getAllOfType(E type)
	{
		ArrayList<E> out = new ArrayList<E>();
		for(Object object : getContents())
		{
			if(object instanceof GameObjectContainer)
			{
				out.addAll(((GameObjectContainer)object).getAllOfType(type));
			}
			else if(object.getClass().isInstance(type))
			{
				out.add((E)object);
			}
		}
		return out;
	}
}
