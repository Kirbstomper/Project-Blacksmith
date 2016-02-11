package com.base.engine.physics.collision;

import com.base.engine.components.attachments.Collidable;

public class Sphere extends Primitive implements Collidable
{
	public float radius;
	
	public Sphere(float radius)
	{
		this.radius = radius;
	}
}
