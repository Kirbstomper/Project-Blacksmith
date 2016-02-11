package com.base.engine.core.math;


public class Tuple 
{
	public int x, y, z;
	public Tuple(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public boolean equals(Tuple other)
	{
		return (x == other.x && y == other.y && z == other.z);
	}
	
	public String toString()
	{
		return "Tuple(" + x + "," + y + "," + z + ")";
	}
}
