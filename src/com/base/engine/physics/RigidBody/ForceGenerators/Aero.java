package com.base.engine.physics.RigidBody.ForceGenerators;

import com.base.engine.core.math.Matrix3f;
import com.base.engine.core.math.Vector3f;
import com.base.engine.physics.RigidBody.RigidBody;

public class Aero implements ForceGenerator
{
	Matrix3f tensor;
	Vector3f position;
	Vector3f windspeed;
	
	public Aero(Matrix3f tensor, Vector3f position, Vector3f windspeed)
	{
		this.tensor = tensor;
		this.position = position;
		this.windspeed = windspeed;
	}
	
	@Override
	public void updateForce(RigidBody body, float delta) {
		// TODO Auto-generated method stub
		
	}

}
