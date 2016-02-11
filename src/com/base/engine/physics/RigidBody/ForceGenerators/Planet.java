package com.base.engine.physics.RigidBody.ForceGenerators;

import com.base.engine.core.math.Vector3f;
import com.base.engine.physics.RigidBody.RigidBody;

public class Planet implements ForceGenerator
{
	Vector3f position;
	float power;
	
	public Planet(Vector3f pos, float p)
	{
		position = pos;
		power = p;
	}
	
	@Override
	public void updateForce(RigidBody body, float delta) {
		Vector3f direction = position.sub(body.getParent().getPosition());
		direction.normalize();
		body.addForce(direction.mul(power));
	}
	
}
