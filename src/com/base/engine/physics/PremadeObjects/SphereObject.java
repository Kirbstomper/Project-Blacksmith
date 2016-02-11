package com.base.engine.physics.PremadeObjects;

import com.base.engine.core.Shapes;
import com.base.engine.physics.RigidBody.RigidSphere;
import com.base.engine.physics.collision.Sphere;

public class SphereObject extends PremadeObject
{
	Sphere sphere;
	
	public SphereObject(){this(1,1,1);}
	public SphereObject(float mass, float ldamping, float adamping){this(mass, ldamping, adamping, 1);}
	public SphereObject(float mass, float ldamping, float adamping, float radius)
	{
		super(Shapes.sphere());
		set(new RigidSphere(mass, ldamping, adamping, radius), new Sphere(radius));
		this.getTransform().setScale(radius);
	}
}
