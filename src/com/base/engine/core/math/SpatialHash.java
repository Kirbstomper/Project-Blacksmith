package com.base.engine.core.math;

import java.util.ArrayList;
import java.util.HashMap;

import com.base.engine.core.GameObject;

public class SpatialHash 
{
	int cellSize;
	ArrayList<Tuple> cells;
	HashMap<Tuple, ArrayList<Point>> contents;
	
	public SpatialHash(int cellSize)
	{
		this.cellSize = cellSize;
		cells = new ArrayList<Tuple>();
		contents = new HashMap<Tuple, ArrayList<Point>>();
	}
	
	public int cellLocation(Tuple cell)
	{
		for(int i = 0; i < cells.size(); i++)
		{
			if(cells.get(i).equals(cell))
				return i;
		}
		return -1;
	}
	
	public Tuple hash(Vector3f point)
	{
		Tuple cell = new Tuple((int)(point.x/cellSize), (int)(point.x/cellSize), (int)(point.z/cellSize));
		int cellLocation = cellLocation(cell);
		if(cellLocation == -1)
		{
			return cell;
		}
		else
		{
			return cells.get(cellLocation(cell));
		}
	}
	
	public Tuple hash(Point point)
	{
		Tuple cell = new Tuple((int)(point.x/cellSize), (int)(point.x/cellSize), (int)(point.z/cellSize));
		int cellLocation = cellLocation(cell);
		if(cellLocation == -1)
		{
			return cell;
		}
		else
		{
			return cells.get(cellLocation(cell));
		}
	}
	
	public void insert(GameObjectContainer container, Vector3f location)
	{
		Point p = new Point(location);
		p.add(container);
		insert(p);
	}
	
	public void insert(Point point)
	{
		Tuple hashKey = hash(point);
		ArrayList<Point> hashLocation = contents.get(hashKey);
		if(hashLocation == null)
		{
			hashLocation = new ArrayList<Point>();
			cells.add(hashKey);
			contents.put(hashKey, hashLocation);
		}
		
		hashLocation.add(point);
	}
	
	public void insert(ArrayList<Point> points)
	{
		for(int i = 0; i < points.size(); i++)
		{
			insert(points.get(i));
		}
	}
	
	
	
	public ArrayList<Point> get(Tuple cell)
	{
		if(cellLocation(cell) != -1)
			return contents.get(cells.get(cellLocation(cell)));
		return null;
	}
	
	public ArrayList<Point> get(ArrayList<Tuple> cells)
	{
		ArrayList<Point> out = new ArrayList<Point>();
		for(int i = 0; i < cells.size(); i++)
		{
			ArrayList<Point> next = get(cells.get(i));
			if(next != null)
				out.addAll(next);
		}
		
		return out;
	}
	
	public ArrayList<Object> get(Point location)
	{
		ArrayList<Point> cell = get(hash(location));
		ArrayList<Object> out = new ArrayList<Object>();
		for(int i = 0; i < cell.size(); i++)
		{
			Point current = cell.get(i);
			if(current.x == location.x && current.y == location.y && current.z == location.z)
			{
				//out.addAll(current);
			}
		}
		
		return out;
	}
	
	public ArrayList<Point> getContentsOfCellOf(Point point)
	{
		return get(hash(point));
	}
	
	public ArrayList<Point> getAll()
	{
		ArrayList<Point> out = new ArrayList<Point>();
		for(int i = 0; i < cells.size(); i++)
		{
			out.addAll(get(cells.get(i)));
		}
		
		return out;
	}
	
	public ArrayList<Point> getInBox(Point bmin, Point bmax)
	{
		ArrayList<Point> out = new ArrayList<Point>();
		ArrayList<Tuple> insideCells = new ArrayList<Tuple>();
		
		Point max = bmax.floor();
		
		Point min = bmin.ceil();
		
		Point inMax = bmax.floor().floor(-.1f);
		Point inMin = bmin.ceil().ceil(.1f);
		if(bmax.equals(max) && bmin.equals(min))
		{
			insideCells.addAll(getCellsInBetween(hash(min), hash(inMax)));
		}
		else
			insideCells.addAll(getCellsInBetween(hash(inMin), hash(inMax)));
		ArrayList<Tuple> borderCells = getCellsInFrame(hash(min), hash(max));
		for(int i = 0; i < borderCells.size(); i++)
		{
			out.addAll(getPointsInBox(borderCells.get(i), bmin, bmax));
		}
		out.addAll(get(insideCells));
		
		return out;
	}
	
	public ArrayList<Point> getInSphere(Point center, float radius)
	{
		ArrayList<Point> out = getInBox(center.add(new Point(-radius, -radius, -radius)), center.add(new Point(radius, radius, radius)));
		
		for(int i = out.size() - 1; i >= 0; i--)
		{
			if(out.get(i).distance(center) > radius)
			{
				out.remove(i);
			}
		}
		
		return out;
	}
	
	public ArrayList<Tuple> getCellsInBetween(Tuple bmin, Tuple bmax)
	{
		ArrayList<Tuple> out = new ArrayList<Tuple>();
		
		if(bmin.equals(bmax))
		{
			out.add(bmax);
		}
		else
		{
			for(int x = bmax.x; x >= bmin.x; x--)
			{
				for(int y = bmax.y; y >= bmin.y; y--)
				{
					for(int z = bmax.z; z >= bmin.z; z--)
					{
						out.add(new Tuple(x,y,z));
					}
				}
			}
		}
		
		return out;
	}
	
	public ArrayList<Tuple> getCellsAdjacent(Tuple cell)
	{
		return getCellsInBetween(new Tuple(cell.x - 1, cell.y - 1, cell.z - 1), new Tuple(cell.x + 1, cell.y + 1, cell.z + 1));
	}
	
	public ArrayList<Tuple> getCellsInFrame(Tuple bmin, Tuple bmax)
	{
		ArrayList<Tuple> out = new ArrayList<Tuple>();
		
		
		if(bmin.equals(bmax))
		{
			out.add(bmax);
		}
		else
		{
			for(int x = bmax.x; x >= bmin.x; x--)
			{
				for(int y = bmax.y; y >= bmin.y; y--)
				{
					for(int z = bmax.z; z >= bmin.z; z--)
					{
						if(x == bmax.x || x == bmin.x || y == bmax.y || y == bmin.y || z == bmax.z || z == bmin.z)
							out.add(new Tuple(x,y,z));
					}
				}
			}
		}
		
		return out;
	}
	
	public ArrayList<Point> getPointsInBox(Tuple cell, Point bmin, Point bmax)
	{
		ArrayList<Point> cellPoints = get(cell);
		ArrayList<Point> out = new ArrayList<Point>();
		
		if(cellPoints != null)
		{
			for(int i = 0; i < cellPoints.size(); i++)
			{
				Point p = cellPoints.get(i);
				if(p.x <= bmax.x && p.y <= bmax.y && p.z <= bmax.z && p.x >= bmin.x && p.y >= bmin.y && p.z >= bmin.z)
				{
					out.add(p);
				}
			}
		}
		
		return out;
	}
	
	
	
	public <E> ArrayList<E> getAllOfType(String type)
	{
		ArrayList<Point> points = getAll();
		ArrayList<E> out = new ArrayList<E>();
		for(Point point : points)
		{
			out.addAll(point.getAllOfType(type));
		}
		return out;
	}
	
	public <E> ArrayList<E> getAllOfType(E type)
	{
		ArrayList<Point> points = getAll();
		ArrayList<E> out = new ArrayList<E>();
		for(Point point : points)
		{
			out.addAll(point.getAllOfType(type));
		}
		return out;
	}
	
	public void rehash(Tuple cell)
	{
		ArrayList<Point> cellContents = get(cell);
		for(int i = 0; i < cellContents.size(); i++)
		{
			Point point = cellContents.get(i);
			if(!hash(point).equals(cell))
			{
				cellContents.remove(i);
				insert(point);
			}
		}
	}
	
	public void rehash(ArrayList<Tuple> cells)
	{
		for(int i = 0; i < cells.size(); i++)
		{
			rehash(cells.get(i));
		}
	}
	
	public void rehash()
	{
		rehash(cells);
	}
	
	public boolean remove(Point point)
	{
		ArrayList<Point> cell = getContentsOfCellOf(point);
		for(int i = 0; i < cell.size(); i++)
			if(cell.get(i) == point)
			{
				cell.remove(i);
				return true;
			}
		return false;
	}
	
	public boolean remove(GameObject object)
	{
		ArrayList<Point> cell = getContentsOfCellOf(new Point(object.getPosition()));
		if(cell == null) return false;
		Point p;
		for(int i = 0; i < cell.size(); i++)
		{
			p = cell.get(i);
			for(Object o : p.contents)
			{
				if(o == object)
				{
					cell.remove(i);
					return true;
				}
			}
		}
		return false;
	}
	
	public void move(Point point, Point location)
	{
		if(!hash(point).equals(hash(location)))
		{
			remove(point);
			
			point.set(location);
			
			insert(point);
		}
		else
		{
			point.set(location);
		}
	}
}