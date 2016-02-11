package com.base.engine.components;

import com.base.engine.components.attachments.ComponentAttachment;
import com.base.engine.core.CoreEngine;
import com.base.engine.core.GameObject;
import com.base.engine.core.math.Transform;

public abstract class GameComponent implements ComponentAttachment
{
	protected GameObject parent;

	public int attach(GameObject parent)
	{
		this.parent = parent;
		return 1;
	}

	public Transform getTransform()
	{
		return parent.getTransform();
	}
	
	public GameObject getParent()
	{
		return parent;
	}
	
	public void addToEngine(CoreEngine engine)
	{
		
	}

	@Override
	public String toString()
	{
		return this.getClass().getSimpleName();
	}
}