package com.base.engine.physics.RigidBody.ForceGenerators;

import com.base.engine.physics.RigidBody.RigidBody;

public interface ForceGenerator 
{
	public void updateForce(RigidBody body, float delta);
}
