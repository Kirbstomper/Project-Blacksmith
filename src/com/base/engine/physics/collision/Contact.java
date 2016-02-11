package com.base.engine.physics.collision;

import com.base.engine.core.math.Matrix3f;
import com.base.engine.core.math.Quaternion;
import com.base.engine.core.math.Vector3f;
import com.base.engine.physics.RigidBody.RigidBody;

public class Contact 
{
	Vector3f contactPoint;
	Vector3f contactNormal;
	float penetration;
	RigidBody body[];
	
	float friction;
	float restitution;
	
	protected Matrix3f contactToWorld;
	protected Vector3f contactVelocity;
	protected float desiredDeltaVelocity = 0;
	protected Vector3f relativeContactPosition[];
	
	protected void setBodyData(RigidBody one, RigidBody two, float friction, float restitution)
	{
		body = new RigidBody[2];
		body[0] = one;
		body[1] = two;
		this.friction = friction;
		this.restitution = restitution;
		contactVelocity = new Vector3f(0,0,0);
		relativeContactPosition = new Vector3f[]{new Vector3f(0,0,0), new Vector3f(0,0,0)};
		contactToWorld = new Matrix3f();
	}
	
	protected void calculateInternals(float delta)
	{
		if(body[0] == null) swapBodies();
		
		calculateContactBasis();
		
		relativeContactPosition[0] = contactPoint.sub(body[0].getPosition());
		if(body[1] != null)
		{
			relativeContactPosition[1] = contactPoint.sub(body[1].getPosition());
		}
		
		contactVelocity = calculateLocalVelocity(0, delta);
		if(body[1] != null)
		{
			contactVelocity = contactVelocity.sub(calculateLocalVelocity(1, delta));
		}
		
		calculateDesiredDeltaVelocity(delta);
	}
	
	protected void swapBodies()
	{
		contactNormal = contactNormal.mul(-1);
		
		RigidBody temp = body[0];
		body[0] = body[1];
		body[1] = temp;
	}
	
	protected void matchAwakeState()
	{
		if(body[1] == null) return;
		
		boolean body0awake = body[0].getAwake();
		boolean body1awake = body[1].getAwake();
		
		if(body0awake ^ body1awake)
		{
			if(body0awake) body[1].setAwake();
			else body[0].setAwake();
		}
	}
	
	protected void calculateDesiredDeltaVelocity(float delta)
	{
		final float velocityLimit = .25f;
		
		float velocityFromAcc = 0;
		
		if(body[0].getAwake())
		{
			velocityFromAcc += body[0].getLastFrameAcceleration().mul(delta).dot(contactNormal);
		}
		
		if(body[1] != null && body[1].getAwake())
		{
			velocityFromAcc -= body[1].getLastFrameAcceleration().mul(delta).dot(contactNormal);
		}
		
		float thisRestitution = restitution;
		if(Math.abs(contactVelocity.x) < velocityLimit)
		{
			thisRestitution = 0;
		}
		
		desiredDeltaVelocity = -contactVelocity.x - thisRestitution * (contactVelocity.x - velocityFromAcc);
	}
	
	protected Vector3f calculateLocalVelocity(int bodyIndex, float delta)
	{
		RigidBody thisBody = body[bodyIndex];
		
		Vector3f velocity = thisBody.getRotation().cross(relativeContactPosition[bodyIndex]);
		velocity = velocity.add(thisBody.getVelocity());
		
		Vector3f contactVelocity = contactToWorld.transpose().transform(velocity);
		
		Vector3f accVelocity = thisBody.getLastFrameAcceleration().mul(delta);
		
		accVelocity = contactToWorld.transpose().transform(accVelocity);
		accVelocity.x = 0;
		
		contactVelocity = contactVelocity.add(accVelocity);
		
		return contactVelocity;
	}
	
	protected void calculateContactBasis()
	{
		Vector3f contactTangent[] = new Vector3f[]{new Vector3f(0,0,0), new Vector3f(0,0,0)};
		
		if(Math.abs(contactNormal.x) > Math.abs(contactNormal.y))
		{
			float s = (float)(1.0f/Math.sqrt(contactNormal.z*contactNormal.z + contactNormal.x*contactNormal.x));
			
			contactTangent[0].x = contactNormal.z*s;
			contactTangent[0].y = 0;
			contactTangent[0].z = -contactNormal.x*s;
			
			contactTangent[1].x = contactNormal.y*contactTangent[0].x;
			contactTangent[1].y = contactNormal.z*contactTangent[0].x - contactNormal.x*contactTangent[0].z;
			contactTangent[1].z = -contactNormal.y*contactTangent[0].x;
		}
		else
		{
			float s = (float)(1.0f/Math.sqrt(contactNormal.z*contactNormal.z + contactNormal.y*contactNormal.y));
			
			contactTangent[0].x = 0;
			contactTangent[0].y = -contactNormal.z*s;
			contactTangent[0].z = contactNormal.x*s;
			
			contactTangent[1].x = contactNormal.y*contactTangent[0].z - contactNormal.z*contactTangent[0].y;
			contactTangent[1].y = -contactNormal.x*contactTangent[0].z;
			contactTangent[1].z = contactNormal.x*contactTangent[0].y;
		}
		
		contactToWorld.setComponents(contactNormal, contactTangent[0], contactTangent[1]);
	}
	
	protected void applyImpulse(Vector3f impulse, RigidBody body, Vector3f velocityChange, Vector3f rotationChange)
	{
		
	}
	
	protected void applyVelocityChange(Vector3f velocityChange[], Vector3f rotationChange[])
	{
		Matrix3f inverseInertiaTensor[] = new Matrix3f[]{new Matrix3f(), new Matrix3f()};
		
		body[0].getInverseInertiaTensorWorld(inverseInertiaTensor[0]);
		if(body[1] != null)
		{
			body[1].getInverseInertiaTensorWorld(inverseInertiaTensor[1]);
		}
		
		Vector3f impulseContact;
		
		if(friction == 0)
		{
			impulseContact = calculateFrictionlessImpulse(inverseInertiaTensor);
		}
		else
		{
			impulseContact = calculateFrictionImpulse(inverseInertiaTensor);
		}
		
		Vector3f impulse = contactToWorld.transform(impulseContact);
		
		Vector3f impulsiveTorque = relativeContactPosition[0].cross(impulse);
		rotationChange[0] = inverseInertiaTensor[0].transform(impulsiveTorque);
		velocityChange[0] = impulse.mul(body[0].getInverseMass());
		
		body[0].addVelocity(velocityChange[0]);
		body[0].addRotation(rotationChange[0]);
		
		if(body[1] != null)
		{
			impulsiveTorque = impulse.cross(relativeContactPosition[1]);
			rotationChange[1] = inverseInertiaTensor[1].transform(impulsiveTorque);
			velocityChange[1] = impulse.mul(-body[1].getInverseMass());
			
			body[1].addVelocity(velocityChange[1]);
			body[1].addRotation(rotationChange[1]);
		}
	}
	
	protected void applyPositionChange(Vector3f linearChange[], Vector3f angularChange[], float penetration)
	{
		float angularLimit = .2f;
		float angularMove[] = new float[2];
		float linearMove[] = new float[2];
		
		float totalInertia = 0;
		float linearInertia[] = new float[2];
		float angularInertia[] = new float[2];
		
		for(int i = 0; i < 2; i++)
		{
			if(body[i] != null)
			{
				Matrix3f inverseInertiaTensor = new Matrix3f();
				body[i].getInverseInertiaTensorWorld(inverseInertiaTensor);
				
				Vector3f angularInertiaWorld = relativeContactPosition[i].cross(contactNormal);
				angularInertiaWorld = inverseInertiaTensor.transform(angularInertiaWorld);
				angularInertiaWorld = angularInertiaWorld.cross(relativeContactPosition[i]);
				angularInertia[i] = angularInertiaWorld.dot(contactNormal);
				
				linearInertia[i] = body[i].getInverseMass();
				
				totalInertia += linearInertia[i] + angularInertia[i];
			}
		}
		
		for(int i = 0; i < 2; i++)
		{
			if(body[i] != null)
			{
				float sign = (i==0)?1:-1;
				angularMove[i] = sign * penetration * (angularInertia[i] / totalInertia);
				linearMove[i] = sign * penetration * (linearInertia[i] / totalInertia);
				
				Vector3f projection = relativeContactPosition[i];
				projection = projection.add(contactNormal.mul(-relativeContactPosition[i].dot(contactNormal)));
				
				float maxMagnitude = angularLimit * projection.magnitude();
				
				if(angularMove[i] < -maxMagnitude)
				{
					float totalMove = angularMove[i] + linearMove[i];
					angularMove[i] = -maxMagnitude;
					linearMove[i] = totalMove - angularMove[i];
				}
				else if(angularMove[i] > maxMagnitude)
				{
					float totalMove = angularMove[i] + linearMove[i];
					angularMove[i] = maxMagnitude;
					linearMove[i] = totalMove - angularMove[i];
				}
				
				if(angularMove[i] == 0)
				{
					angularChange[i] = new Vector3f(0,0,0);
				}
				else
				{
					Vector3f targetAngularDirection = relativeContactPosition[i].cross(contactNormal);
					
					Matrix3f inverseInertiaTensor = new Matrix3f();
					body[i].getInverseInertiaTensorWorld(inverseInertiaTensor);
					
					angularChange[i] = inverseInertiaTensor.transform(targetAngularDirection).mul(angularMove[i] / angularInertia[i]);
				}
				
				linearChange[i] = contactNormal.mul(linearMove[i]);
				
				Vector3f pos = body[i].getPosition();
				pos = pos.add(contactNormal.mul(linearMove[i]));
				body[i].setPosition(pos);
				
				Quaternion q = body[i].getOrientation();
				q.addScaledVector(angularChange[i], 1.0f);
				body[i].setOrientation(q);
				
				//System.out.println(angularChange[i]);
				
				if(!body[i].getAwake()) body[i].calculateDerivedData();
			}
		}
		
	}
	
	protected Vector3f calculateFrictionlessImpulse(Matrix3f[] inverseInertiaTensor)
	{
		Vector3f impulseContact = new Vector3f(0,0,0);
		
		Vector3f deltaVelWorld = relativeContactPosition[0].cross(contactNormal);
		deltaVelWorld = inverseInertiaTensor[0].transform(deltaVelWorld);
		deltaVelWorld = deltaVelWorld.cross(relativeContactPosition[0]);
		
		float deltaVelocity = deltaVelWorld.dot(contactNormal);
		
		deltaVelocity += body[0].getInverseMass();
		
		if(body[1] != null)
		{
			deltaVelWorld = relativeContactPosition[1].cross(contactNormal);
			deltaVelWorld = inverseInertiaTensor[1].transform(deltaVelWorld);
			deltaVelWorld = deltaVelWorld.cross(relativeContactPosition[1]);
			
			deltaVelocity += deltaVelWorld.dot(contactNormal);
			
			deltaVelocity += body[1].getInverseMass();
		}
		
		impulseContact.x = desiredDeltaVelocity / deltaVelocity;
		impulseContact.y = 0;
		impulseContact.z = 0;
		
		return impulseContact;
	}
	
	protected Vector3f calculateFrictionImpulse(Matrix3f[] inverseInertiaTensor)
	{
		Vector3f impulseContact = new Vector3f(0,0,0);
		float inverseMass = body[0].getInverseMass();
		
		Matrix3f impulseToTorque = new Matrix3f();
		impulseToTorque.setSkewSymmetric(relativeContactPosition[0]);
		
		Matrix3f deltaVelWorld = impulseToTorque;
		deltaVelWorld = deltaVelWorld.mul(inverseInertiaTensor[0]);
		deltaVelWorld = deltaVelWorld.mul(impulseToTorque).mul(-1);
		
		if(body[1] != null)
		{
			impulseToTorque.setSkewSymmetric(relativeContactPosition[1]);
			
			Matrix3f deltaVelWorld2 = impulseToTorque;
			deltaVelWorld2 = deltaVelWorld2.mul(inverseInertiaTensor[1]);
			deltaVelWorld2 = deltaVelWorld2.mul(impulseToTorque).mul(-1);
			
			deltaVelWorld = deltaVelWorld.add(deltaVelWorld2);
			
			inverseMass += body[1].getInverseMass();
		}
		
		Matrix3f deltaVelocity = contactToWorld.transpose();
		deltaVelocity = deltaVelocity.mul(deltaVelWorld);
		deltaVelocity = deltaVelocity.mul(contactToWorld);
		
		deltaVelocity.m[0][0] += inverseMass;
		deltaVelocity.m[1][1] += inverseMass;
		deltaVelocity.m[2][2] += inverseMass;
		
		Matrix3f impulseMatrix = deltaVelocity.inverse();
		
		Vector3f velKill = new Vector3f(desiredDeltaVelocity, -contactVelocity.y, -contactVelocity.z);
		
		impulseContact = impulseMatrix.transform(velKill);
		
		float planarImpulse = (float) Math.sqrt(impulseContact.y*impulseContact.y + impulseContact.z*impulseContact.z);
		
		if(planarImpulse > impulseContact.x * friction)
		{
			impulseContact.y /= planarImpulse;
			impulseContact.z /= planarImpulse;
			
			impulseContact.x = deltaVelocity.m[0][0] + deltaVelocity.m[0][1]*friction*impulseContact.y + deltaVelocity.m[0][2]*friction*impulseContact.z;
			impulseContact.x = desiredDeltaVelocity / impulseContact.x;
			impulseContact.y *= friction * impulseContact.x;
			impulseContact.z *= friction * impulseContact.x;
		}
		
		return impulseContact;
	}
	
}
