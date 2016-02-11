package com.base.engine.physics.collision;

import com.base.engine.core.math.Vector3f;

public class BoundingSphere implements BoundingVolume
{
	Vector3f center;
	float radius;
	
	public BoundingSphere(Vector3f center, float radius)
	{
		this.center = center;
		this.radius = radius;
	}
	
	public BoundingSphere(BoundingSphere one, BoundingSphere two)
	{
		
	}
	
	@Override
	public boolean overlaps(BoundingVolume other)
	{
		if(other instanceof BoundingSphere)
		{
			BoundingSphere another = (BoundingSphere)other;
			float distanceSquared = (center.sub(another.center)).magnitude();
			distanceSquared *= distanceSquared;
			return distanceSquared < (radius + another.radius) * (radius + another.radius);
		}
		return false;
	}

	@Override
	public float getSize() {
		// TODO Auto-generated method stub
		return 0;
	}
}
