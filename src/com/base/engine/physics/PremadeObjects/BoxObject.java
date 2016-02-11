package com.base.engine.physics.PremadeObjects;

import com.base.engine.core.Shapes;
import com.base.engine.core.math.Vector3f;
import com.base.engine.physics.RigidBody.RigidBox;
import com.base.engine.physics.collision.Box;

public class BoxObject extends PremadeObject
{
	
	public BoxObject(){this(1,1,1);}
	public BoxObject(float mass, float ldamping, float adamping){this(mass, ldamping, adamping, new Vector3f(1,1,1));}
	public BoxObject(float mass, float ldamping, float adamping, Vector3f halfSize)
	{
		super(Shapes.box(halfSize, true));
		
		set(new RigidBox(mass, ldamping, adamping, halfSize), new Box(halfSize));
	}
}
