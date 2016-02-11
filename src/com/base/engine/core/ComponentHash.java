 package com.base.engine.core;

import java.util.ArrayList;

import com.base.engine.components.attachments.ComponentAttachment;

public class ComponentHash 
{
	BucketBST<ComponentAttachment> tree;
	
	public ComponentHash()
	{
		tree = new BucketBST<ComponentAttachment>();
	}
	
	private int hash(String key)
	{
		return key.hashCode();
	}
	
	public void add(ComponentAttachment ca)
	{
		@SuppressWarnings("rawtypes")
		Class[] classes = ca.getClass().getInterfaces();
		for(int i = 0; i < classes.length; i++)
		{
			int key = hash(classes[i].getSimpleName());
			tree.add(key, ca);
		}
	}
	
	public ArrayList<ComponentAttachment> getBucket(ComponentAttachment ca)
	{
		return tree.getBucket(hash(ca.getClass().getSimpleName()));
	}
	
	public ArrayList<ComponentAttachment> getBucket(String ca)
	{
		return tree.getBucket(hash(ca));
	}
	
	public ArrayList<ComponentAttachment> getAll()
	{
		return tree.getAll();
	}
	
	public void remove(ComponentAttachment ca)
	{
		@SuppressWarnings("rawtypes")
		Class[] classes = ca.getClass().getInterfaces();
		for(int i = 0; i < classes.length; i++)
		{
			int key = hash(classes[i].getName());
			tree.remove(key, ca);
		}
	}
	
	@Override
	public String toString()
	{
		return tree.toString();
	}
	
	//TODO: Upgrade BucketBST to a B-tree
	private class BucketBST<E>
	{
		Node<E> first;
		
		public BucketBST()
		{
			first = null;
		}
		
		public void add(Node<E> node)
		{
			if(first == null)
			{
				first = null;
			}
			else
			{
				first.add(node);
			}
		}
		
		public void add(int x, E e)
		{
			if(first == null)
			{
				first = new Node<E>(x, e);
			}
			else
			{
				first.add(x, e);
			}
		}
		
		public ArrayList<E> getAll()
		{
			ArrayList<E> out = new ArrayList<E>();
			ArrayList<Node<E>> nodes = new ArrayList<Node<E>>();
			first.getAll(nodes);
			for(int i = 0; i < nodes.size(); i++)
			{
				out.addAll(nodes.get(i).bucket);
			}
			return out;
		}
		
		public ArrayList<E> getBucket(int x)
		{
			if(first != null)
				return first.getBucket(x);
			return null;
		}
		
		public void remove(int x, E e)
		{
			if(first != null && first.remove(x, e) == 0)
			{
				remove(x);
			}
		}
		
		public void remove(int x)
		{
			if(x == first.value)
				return;
			Node<E> parent = first;
			Node<E> child = null;
			boolean left = false;
			while(true)
			{
				if(parent.value < x)
				{
					if(parent.l.value == x)
					{
						child = parent.l;
						left = true;
						break;
					}
					else
					{
						parent = parent.l;
					}
				}
				else
				{
					if(parent.r.value == x)
					{
						child = parent.r;
						break;
					}
					else
					{
						parent = parent.r;
					}
				}
			}
			if(child != null)
			{
				if(child.l == null && child.r == null)
				{
					if(left)
						parent.l = null;
					parent.r = null;
				}
				else if((child.l != null && child.r == null))
				{
					if(left)
						parent.l = child.l;
					parent.r = child.l;
				}
				else if((child.l == null && child.r != null))
				{
					if(left)
						parent.l = child.r;
					parent.r = child.r;
				}
				else
				{
					ArrayList<Node<E>> hold = new ArrayList<Node<E>>();
					child.getAllBelow(hold);
					for(int i = 0; i < hold.size(); i++)
					{
						this.add(hold.get(i));
					}
				}
			}
		}
		
		@Override
		public String toString()
		{
			String out = "[";
			ArrayList<Node<E>> nodes = new ArrayList<Node<E>>();
			first.getAll(nodes);
			for(Node<E> node : nodes)
			{
				out += node.toString();
			}
			out += "]";
			return out;
		}
		
		private class Node<T>
		{
			int value;
			ArrayList<T> bucket;
			Node<T> l,r;
			
			public Node(int x, T t)
			{
				this.value = x;
				l = r = null;
				bucket = new ArrayList<T>();
				bucket.add(t);
			}
			
			public void add(int x, T t)
			{
				if(value == x)
				{
					bucket.add(t);
				}
				else if(value < x)
				{
					if(l == null)
					{
						l = new Node<T>(x, t);
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
						r = new Node<T>(x, t);
					}
					else
					{
						r.add(x, t);
					}
				}
			}
			
			public void add(Node<T> node)
			{
				if(this.value < node.value)
				{
					if(l == null)
					{
						l = node;
					}
					else
					{
						l.add(node);
					}
				}
				else
				{
					if(r == null)
					{
						r = node;
					}
					else
					{
						r.add(node);
					}
				}
			}
			
			public ArrayList<T> getBucket(int x)
			{
				if(value == x)
				{
					return bucket;
				}
				else if(value < x)
				{
					if(l == null)
					{
						return null;
					}
					else
					{
						return l.getBucket(x);
					}
				}
				else
				{
					if(r == null)
					{
						return null;
					}
					else
					{
						return r.getBucket(x);
					}
				}
			}
			
			public void getAllBelow(ArrayList<Node<T>> nodes)
			{
				l.getAll(nodes);
				r.getAll(nodes);
			}
			
			public void getAll(ArrayList<Node<T>> nodes)
			{
				nodes.add(this);
				if(l != null)
				{
					l.getAll(nodes);
				}
				if(r != null)
				{
					r.getAll(nodes);
				}
			}
			
			//Returns 0 if the "bucket" is empty
			public int remove(int x, T t)
			{
				if(value == x)
				{
					bucket.remove(t);
					if(bucket.isEmpty())
					{
						return 0;
					}
				}
				else if(value < x)
				{
					if(l != null)
					{
						l.remove(x, t);
					}
				}
				else
				{
					if(r != null)
					{
						r.remove(x, t);
					}
				}
				return 1;
			}
			
			@SuppressWarnings("unused")
			public Node<T> find(int x)
			{
				if(value == x)
				{
					return this;
				}
				else if(value < x)
				{
					if(l == null)
					{
						return null;
					}
					else
					{
						return l.find(x);
					}
				}
				else
				{
					if(r == null)
					{
						return null;
					}
					else
					{
						return r.find(x);
					}
				}
			}
			
			@SuppressWarnings("unused")
			public Node<T> findParent(Node<T> node)
			{
				if(node == this)
				{
					return null;
				}
				else if(node.value < this.value)
				{
					if(l == null)
					{
						return null;
					}
					else
					{
						if(l == node)
						{
							return this;
						}
						else
						{
							return l.findParent(node);
						}
					}
				}
				else
				{
					if(r == null)
					{
						return null;
					}
					else
					{
						if(r == node)
						{
							return this;
						}
						else
						{
							return r.findParent(node);
						}
					}
				}
			}
			
			@Override
			public String toString()
			{
				return value + "->" + bucket.toString();
			}
		}
	}
}