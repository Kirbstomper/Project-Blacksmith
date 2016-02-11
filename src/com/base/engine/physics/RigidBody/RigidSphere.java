package com.base.engine.physics.RigidBody;

import com.base.engine.components.attachments.Physical;
import com.base.engine.core.math.Matrix3f;

public class RigidSphere extends RigidBody implements Physical 
{
	public RigidSphere(float mass, float ldamping, float adamping) {this(mass, ldamping, adamping, 1);}
	
	public RigidSphere(float mass, float ldamping, float adamping, float radius) {
		super(mass, ldamping, adamping);
		Matrix3f inertiaTensor = new Matrix3f();
		inertiaTensor.m[0][0] = 2*(mass)*radius*radius/5.0f;
		inertiaTensor.m[1][1] = 2*(mass)*radius*radius/5.0f;
		inertiaTensor.m[2][2] = 2*(mass)*radius*radius/5.0f;
		setInertiaTensor(inertiaTensor);
	}
}
