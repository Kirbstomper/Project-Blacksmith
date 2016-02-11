package com.base.engine.physics.collision;

import com.base.engine.components.attachments.Collidable;
import com.base.engine.core.math.Vector3f;

public class Plane extends Primitive implements Collidable
{
	private Vector3f direction;
	private float offset;
	
	public Plane(){this(new Vector3f(0,1,0),0);}
	public Plane(Vector3f direction, float offset)
	{
		this.direction = direction;
		this.offset = offset;
	}
	
	@Override
	public void calculateInternals()
	{
		
	}
	
	public Vector3f getDirection()
	{
		return direction;
	}
	
	public float getOffset()
	{
		return offset;
	}
}
