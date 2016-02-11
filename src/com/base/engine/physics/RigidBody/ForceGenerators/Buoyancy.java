package com.base.engine.physics.RigidBody.ForceGenerators;

import com.base.engine.core.math.Vector3f;
import com.base.engine.physics.RigidBody.RigidBody;

public class Buoyancy implements ForceGenerator
{
	float maxDepth;
	float volume;
	float waterHeight;
	float liquidDensity;
	Vector3f centerOfBuoyancy;
	
	public Buoyancy(Vector3f CoB, float maxDepth, float volume, float waterHeight)
	{
		this(CoB, maxDepth, volume, waterHeight, 1000.0f);
	}
	
	public Buoyancy(Vector3f CoB, float maxDepth, float volume, float waterHeight, float liquidDensity)
	{
		this.centerOfBuoyancy = CoB;
		this.maxDepth = maxDepth;
		this.volume = volume;
		this.waterHeight = waterHeight;
		this.liquidDensity = liquidDensity;
	}
	
	@Override
	public void updateForce(RigidBody body, float delta) {
		// TODO Auto-generated method stub
		
	}

}
