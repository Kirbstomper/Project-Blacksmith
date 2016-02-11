package com.base.engine.core.math;
import java.util.ArrayList;

public class Point implements GameObjectContainer
{
	public float x, y, z;
	public ArrayList<Object> contents;
	public Point(float x, float y, float z) 
	{
		this.x = x;
		this.y = y;
		this.z = z;
		contents = new ArrayList<Object>();
	}
	
	public Point(Vector3f r)
	{
		this(r.getX(), r.getY(), r.getZ());
	}
	
	public void set(Point p)
	{
		x = p.x;
		y = p.y;
		z = p.z;
	}
	
	public Point floor(){return new Point((float) Math.floor(x), (float) Math.floor(y), (float) Math.floor(z));}
	public Point floor(float amt){return new Point((float) Math.floor(x + amt), (float) Math.floor(y + amt), (float) Math.floor(z + amt));}
	public Point ceil(){return new Point((float) Math.ceil(x), (float) Math.ceil(y), (float) Math.ceil(z));}
	public Point ceil(float amt){return new Point((float) Math.ceil(x + amt), (float) Math.ceil(y + amt), (float) Math.ceil(z + amt));}
	
	public Point add(Point other){return new Point(x + other.x, y + other.y, z + other.z);}
	
	
	public void add(Object object){contents.add(object);}
	
	@Override
	public ArrayList<Object> getContents() {
		return contents;
	}
	
//	@SuppressWarnings("unchecked")
//	public <E> ArrayList<E> getAllOfType(E type)
//	{
//		ArrayList<E> out = new ArrayList<E>();
//		for(Object object : contents)
//		{
//			if(object instanceof GameObjectContainer)
//			{
//				out.addAll(((GameObjectContainer)object).getAllOfType(type));
//			}
//			else if(object.getClass().isInstance(type))
//			{
//				out.add((E)object);
//			}
//		}
//		return out;
//	}
	
	public float distance(Point other)
	{
		return (float) Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2) + Math.pow(z - other.z, 2));
	}
	
	public boolean equals(Tuple other){return (x == other.x && y == other.y && z == other.z);}
	public boolean equals(Point other){return (x == other.x && y == other.y && z == other.z);}
	
	public String toString(){return "Point(" + x + "," + y + "," + z + ")";}
}
