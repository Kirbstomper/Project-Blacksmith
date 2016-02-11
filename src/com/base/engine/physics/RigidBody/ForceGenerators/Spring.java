package com.base.engine.physics.RigidBody.ForceGenerators;

import com.base.engine.core.math.Vector3f;
import com.base.engine.physics.RigidBody.RigidBody;

public class Spring implements ForceGenerator
{
	Vector3f connectionPoint;
	Vector3f otherConnectionPoint;
	
	RigidBody other;
	float k; //the spring constant
	float rest; //Rest length
	
	
	public Spring(Vector3f localConnectionPt, RigidBody other, Vector3f otherConnectionPt, float k, float rest)
	{
		this.connectionPoint = localConnectionPt;
		this.otherConnectionPoint = otherConnectionPt;
		this.other = other;
		this.k = k;
		this.rest = rest;
	}
	
	@Override
	public void updateForce(RigidBody body, float delta)
	{
		Vector3f lws = body.getPointInWorldSpace(connectionPoint);
		Vector3f ows = other.getPointInWorldSpace(otherConnectionPoint);
		
		Vector3f force = lws.sub(ows);
		
		float magnitude = force.length();
		magnitude = Math.abs(magnitude - rest) * k;
		
		body.addForceAtPoint(force.normal().mul(-magnitude), lws);
	}
	
}
