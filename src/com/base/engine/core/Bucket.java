package com.base.engine.core;

public class Bucket<E>
{
	private Node<E> first;
	
	public Bucket()
	{
		first = null;
	}
	
	public void push(E e)
	{
		if(first == null)
		{
			first = new Node<E>(e);
		}
		else
		{
			Node<E> temp = new Node<E>(e);
			temp.setNext(first);
			first = temp;
		}
	}
	
	public Bucket<E> copy()
	{
		Bucket<E> bucket = new Bucket<E>();
		E element = first.getContents();
		while(element != null)
		{
			//bucket.add
		}
		return null;
	}
	
	public E pop()
	{
		E e = first.getContents();
		first = first.getNext();
		return e;
	}
	
	private class Node<T>
	{
		private T contents;
		private Node<T> next;
		
		public Node(T e)
		{
			contents = e;
		}
		
		public T getContents()
		{
			return contents;
		}
		
		public Node<T> getNext()
		{
			return next;
		}
		
		public void setNext(Node<T> n)
		{
			next = n;
		}
	}
}
