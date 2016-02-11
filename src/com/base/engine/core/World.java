package com.base.engine.core;

import java.util.ArrayList;
import java.util.Collection;

import com.base.engine.components.attachments.Collidable;
import com.base.engine.components.attachments.Controlable;
import com.base.engine.components.attachments.Interactable;
import com.base.engine.components.attachments.LightAttachment;
import com.base.engine.components.attachments.Physical;
import com.base.engine.components.attachments.Renderable;
import com.base.engine.components.attachments.UIRenderable;
import com.base.engine.components.attachments.Updatable;
import com.base.engine.core.math.Point;
import com.base.engine.core.math.SpatialHash;
import com.base.engine.core.math.Tuple;
import com.base.game.TestGame;

public class World 
{
	public static World world = new World();
	private SpatialHash spatialHash;
	private ArrayList<GameObject> bucket;
	private ArrayList<Tuple> activeCells;
	public GameObject focus;
	private Game map;
	ArrayList<Updatable> updates;
	ArrayList<GameObject> removeList;
	
	public World()
	{
		spatialHash = new SpatialHash(100);
		bucket = new ArrayList<GameObject>();
		removeList = new ArrayList<GameObject>();
		map = new TestGame();
	}
	
	public void init()
	{
		map.init();
	}
	
	public void add(GameObject object)
	{
		spatialHash.insert(object, object.getPosition());
	}
	
	public void addToBucket(GameObject object)
	{
		bucket.add(object);
	}
	
	public void refreshActives()
	{
		activeCells = spatialHash.getCellsAdjacent(spatialHash.hash(focus.getPosition()));
	}
	
	public void remove(GameObject object)
	{
		removeList.add(object);
	}
	
	public void remove()
	{
		for(GameObject object : removeList)
			if(!bucket.remove(object))
				spatialHash.remove(object);
	}
	
	//Convenience Methods!
	public void updateObjects()
	{
		ArrayList<GameObject> objects = getGameObjects(activeCells);
		for(GameObject object : objects)
		{
			object.update();
		}
	}
	
	public void gather()
	{
		updates = this.getUpdatable();
	}
	
	public void update(float delta)
	{
		for(Updatable update : updates)
		{
			update.update(delta);
		}
	}
	
	public ArrayList<GameObject> getGameObjects()
	{
		ArrayList<GameObject> out = new ArrayList<GameObject>();
		ArrayList<Point> points = spatialHash.getAll();
		for(Point point : points)
		{
			out.addAll(point.getType("GameObject"));
		}
		out.addAll(bucket);
		return out;
	}
	
	public ArrayList<GameObject> getGameObjects(ArrayList<Tuple> cells)
	{
		ArrayList<GameObject> out = new ArrayList<GameObject>();
		ArrayList<Point> points = spatialHash.getAll();//get(cells);
		for(Point point : points)
		{
			out.addAll(point.getType("GameObject"));
		}
		out.addAll(bucket);
		return out;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Renderable> getRenderable()
	{
		ArrayList<Renderable> out = new ArrayList<Renderable>();
		ArrayList<GameObject> objects = getGameObjects(activeCells);
		for(GameObject object : objects)
		{
			out.addAll((Collection<? extends Renderable>) object.getAllComponentsOf("Renderable"));
		}
		return out;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<UIRenderable> getUIRenderable()
	{
		ArrayList<UIRenderable> out = new ArrayList<UIRenderable>();
		ArrayList<GameObject> objects = getGameObjects(activeCells);
		for(GameObject object : objects)
		{
			out.addAll((Collection<? extends UIRenderable>) object.getAllComponentsOf("UIRenderable"));
		}
		return out;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Controlable> getControlable()
	{
		ArrayList<Controlable> out = new ArrayList<Controlable>();
		ArrayList<GameObject> objects = getGameObjects(activeCells);
		for(GameObject object : objects)
		{
			out.addAll((Collection<? extends Controlable>) object.getAllComponentsOf("Controlable"));
		}
		return out;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Physical> getPhysical()
	{
		ArrayList<Physical> out = new ArrayList<Physical>();
		ArrayList<GameObject> objects = getGameObjects(activeCells);
		for(GameObject object : objects)
		{
			out.addAll((Collection<? extends Physical>) object.getAllComponentsOf("Physical"));
		}
		return out;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Collidable> getCollidable()
	{
		ArrayList<Collidable> out = new ArrayList<Collidable>();
		ArrayList<GameObject> objects = getGameObjects(activeCells);
		for(GameObject object : objects)
		{
			out.addAll((Collection<? extends Collidable>) object.getAllComponentsOf("Collidable"));
		}
		return out;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<LightAttachment> getLightAttachments() {
		ArrayList<LightAttachment> out = new ArrayList<LightAttachment>();
		ArrayList<GameObject> objects = getGameObjects(activeCells);
		for(GameObject object : objects)
		{
			out.addAll((Collection<? extends LightAttachment>) object.getAllComponentsOf("LightAttachment"));
		}
		return out;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Updatable> getUpdatable() {
		ArrayList<Updatable> out = new ArrayList<Updatable>();
		ArrayList<GameObject> objects = getGameObjects(activeCells);
		for(GameObject object : objects)
		{
			out.addAll((Collection<? extends Updatable>) object.getAllComponentsOf("Updatable"));
		}
		return out;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Interactable> getInteractables() {
		ArrayList<Interactable> out = new ArrayList<Interactable>();
		ArrayList<GameObject> objects = getGameObjects(activeCells);
		for(GameObject object : objects)
		{
			out.addAll((Collection<? extends Interactable>) object.getAllComponentsOf("Interactable"));
		}
		return out;
	}
}
