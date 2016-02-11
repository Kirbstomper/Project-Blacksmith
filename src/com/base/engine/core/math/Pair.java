package com.base.engine.core.math;

public class Pair <A,B>
{
	public A first;
	public B second;
	
	public Pair(A first, B second)
	{
		this.first = first;
		this.second = second;
	}
	
	public void delete()
	{
		first = null;
		second = null;
	}
}
