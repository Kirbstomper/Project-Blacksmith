package com.base.engine.physics.RigidBody;

import com.base.engine.components.attachments.Physical;
import com.base.engine.core.math.Matrix3f;
import com.base.engine.core.math.Vector3f;

public class RigidBox extends RigidBody implements Physical
{

	public RigidBox(float mass, float ldamping, float adamping) {this(mass, ldamping, adamping, new Vector3f(1,1,1));}
	
	public RigidBox(float mass, float ldamping, float adamping, Vector3f halfSize) {
		super(mass, ldamping, adamping);
		Matrix3f inertiaTensor = new Matrix3f();
		inertiaTensor.m[0][0] = (mass)*(halfSize.y*halfSize.y + halfSize.z*halfSize.z)/3.0f;
		inertiaTensor.m[1][1] = (mass)*(halfSize.x*halfSize.x + halfSize.z*halfSize.z)/3.0f;
		inertiaTensor.m[2][2] = (mass)*(halfSize.x*halfSize.x + halfSize.y*halfSize.y)/3.0f;
		setInertiaTensor(inertiaTensor);
	}
	
}
