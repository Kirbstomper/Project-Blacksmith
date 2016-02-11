package com.base.engine.physics.collision;

import com.base.engine.components.attachments.Collidable;
import com.base.engine.core.math.Vector3f;

public class Box extends Primitive implements Collidable
{
	private Vector3f halfSize;
	
	public Box()
	{
		halfSize = new Vector3f(1,1,1);
	}
	
	public Box(Vector3f halfSize)
	{
		this.halfSize = halfSize;
	}
	
	public Vector3f getHalfSize()
	{
		return halfSize;
	}
}
