package com.base.engine.physics.PremadeObjects;

import com.base.engine.core.math.Vector3f;
import com.base.engine.physics.collision.Plane;

public class PlaneObject extends PremadeObject
{
	
	public PlaneObject(){this(new Vector3f(0,1,0),0);}
	public PlaneObject(Vector3f direction, float offset)
	{
		setPrimitive(new Plane(direction.normal(), offset));
	}
}
