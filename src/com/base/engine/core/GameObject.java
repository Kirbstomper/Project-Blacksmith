package com.base.engine.core;

import java.util.ArrayList;

import com.base.engine.components.GameComponent;
import com.base.engine.components.attachments.ComponentAttachment;
import com.base.engine.core.math.GameObjectContainer;
import com.base.engine.core.math.Transform;
import com.base.engine.core.math.Vector3f;

public class GameObject implements GameObjectContainer
{
	
	private ArrayList<GameObject> children;
	private ComponentHash components;
	private Transform transform;
	private String name;

	
	public GameObject()
	{
		children = new ArrayList<GameObject>();
		components = new ComponentHash();
		transform = new Transform();
	}
	
	public GameObject(String name)
	{
		children = new ArrayList<GameObject>();
		components = new ComponentHash();
		transform = new Transform();
		this.name = name;
	}

	public void addChild(GameObject child)
	{
		children.add(child);
		//child.setEngine(engine);
		child.getTransform().setParent(transform);
	}

	public GameObject addComponent(GameComponent component)
	{
		components.add(component);
		component.attach(this);

		return this;
	}
	
	public void update()
	{
		transform.update();
	}
	
	public ArrayList<GameObject> getAllAttached()
	{
		ArrayList<GameObject> result = new ArrayList<GameObject>();
		
		for(GameObject child : children)
		{
			result.addAll(child.getAllAttached());
		}
		result.add(this);
		return result;
	}

	public Transform getTransform()
	{
		return transform;
	}
	
	
	public void moveTo(Vector3f pos)
	{
		Vector3f movement = transform.getPos().sub(pos);
		transform.setPos(pos);
		for(GameObject child : children)
		{
			child.move(movement);
		}
	}
	public void move(Vector3f movement)
	{
		transform.setPos(getPosition().add(movement));
		for(GameObject child : children)
		{
			child.move(movement);
		}
	}
	
	public Vector3f getPosition()
	{
		return transform.getPos();
	}
	
//	public void setEngine(CoreEngine engine)
//	{
//		if(this.engine != engine)
//		{
//			this.engine = engine;
//			
//			for(GameComponent component : components)
//				component.addToEngine(engine);
//
//			for(GameObject child : children)
//				child.setEngine(engine);
//		}
//	}
	
	public ArrayList<ComponentAttachment> getComponentsOf(ComponentAttachment ca)
	{
		return components.getBucket(ca);
	}
	
	public ArrayList<ComponentAttachment> getComponentsOf(String ca)
	{
		return components.getBucket(ca);
	}
	
	public ArrayList<ComponentAttachment> getAllComponentsOf(ComponentAttachment ca)
	{
		ArrayList<ComponentAttachment> out = new ArrayList<ComponentAttachment>();
		ArrayList<ComponentAttachment> check = getComponentsOf(ca);
		if(check != null)
		{
			out.addAll(check);
		}
		for(GameObject child : children)
		{
			check = child.getAllComponentsOf(ca);
			if(check != null)
			{
				out.addAll(check);
			}
		}
		return out;
	}
	
	public ArrayList<ComponentAttachment> getAllComponentsOf(String ca)
	{
		ArrayList<ComponentAttachment> out = new ArrayList<ComponentAttachment>();
		ArrayList<ComponentAttachment> check = getComponentsOf(ca);
		if(check != null)
		{
			out.addAll(check);
		}
		for(GameObject child : children)
		{
			check = child.getAllComponentsOf(ca);
			if(check != null)
			{
				out.addAll(check);
			}
		}
		return out;
	}
	
	public void delete()
	{
		World.world.remove(this);
	}
	
	@Override
	public ArrayList<Object> getContents() 
	{
		ArrayList<Object> out = new ArrayList<Object>();
		out.addAll(children);
		out.addAll(components.getAll());
		return out;
	}
	
	@Override
	public String toString()
	{
		if(name == null)
			return this.getClass().getSimpleName() + "->" + components.toString();
		else
			return this.getClass().getSimpleName() + "." + name + "->" + components.toString();
	}
}
