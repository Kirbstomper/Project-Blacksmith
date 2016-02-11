package com.base.engine.physics.RigidBody.ForceGenerators;

import com.base.engine.core.math.Vector3f;
import com.base.engine.physics.RigidBody.RigidBody;

public class Gravity implements ForceGenerator
{
	Vector3f gravity;
	
	public Gravity(Vector3f gravity)
	{
		this.gravity = gravity;
	}

	@Override
	public void updateForce(RigidBody body, float delta) 
	{
		body.addForce(gravity.mul(body.getMass()));
	}
}
