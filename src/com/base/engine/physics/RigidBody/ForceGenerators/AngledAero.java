package com.base.engine.physics.RigidBody.ForceGenerators;

import com.base.engine.core.math.Matrix3f;
import com.base.engine.core.math.Quaternion;
import com.base.engine.core.math.Vector3f;
import com.base.engine.physics.RigidBody.RigidBody;

public class AngledAero extends Aero
{
	Quaternion orientation;
	
	public AngledAero(Matrix3f tensor, Vector3f position, Vector3f windspeed) 
	{
		super(tensor, position, windspeed);
	}
	
	public void setOrientation(Quaternion q)
	{
		orientation = q;
	}
	
	@Override
	public void updateForce(RigidBody body, float duraton)
	{
		
	}
}
