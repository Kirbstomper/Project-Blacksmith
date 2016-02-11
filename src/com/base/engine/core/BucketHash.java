package com.base.engine.core;

public class BucketHash 
{
	
	public BucketHash()
	{
		
	}
	
	private int hash(String key)
	{
		return key.hashCode();
	}
	
	private class BucketBST<E>
	{
		
		private class Node<T>
		{
			int value;
			Bucket<T> bucket;
			Node<T> l,r;
			
			public Node(int x, T t)
			{
				this.value = x;
				l = r = null;
				bucket = new Bucket<T>();
				bucket.push(t);
			}
			
			public void add(int x, T t)
			{
				if(value == x)
				{
					bucket.push(t);
				}
				else if(value < x)
				{
					if(l == null)
					{
						l = new Node(x, t);
					}
					else
					{
						l.add(x, t);
					}
				}
				else
				{
					if(r == null)
					{
						r = new Node(x, t);
					}
					else
					{
						r.add(x, t);
					}
				}
			}
			
			//public 
		}
	}
}
