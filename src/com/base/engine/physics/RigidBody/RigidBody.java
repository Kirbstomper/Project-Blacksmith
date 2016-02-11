package com.base.engine.physics.RigidBody;

import com.base.engine.components.PhysicsComponent;
import com.base.engine.components.attachments.Physical;
import com.base.engine.core.GameObject;
import com.base.engine.core.math.Matrix3f;
import com.base.engine.core.math.Matrix4f;
import com.base.engine.core.math.Quaternion;
import com.base.engine.core.math.Vector3f;

public class RigidBody extends PhysicsComponent implements Physical
{
	private static final float sleepEpsilon = .01f;
	
	float inverseMass;
	float linearDamping;
	float angularDamping;
	
	Vector3f velocity;
	Vector3f rotationVelocity;
	Vector3f acceleration;
	Vector3f lastFrameAcceleration;
	
	Matrix4f transformMatrix;
	Matrix3f inverseInertiaTensor;
	Matrix3f inverseInertiaTensorWorld;
	
	Vector3f forceAccum;
	Vector3f torqueAccum;
	
	public float motion;
	boolean isAwake;
	boolean canSleep = true;
	
	
	public RigidBody(float mass, float ldamping, float adamping)
	{
		inverseMass = 1/mass;
		linearDamping = ldamping;
		angularDamping = adamping;
		
		velocity = new Vector3f(0,0,0);
		rotationVelocity = new Vector3f(0,0,0);
		acceleration = new Vector3f(0,0,0);
		lastFrameAcceleration = new Vector3f(0,0,0);
		
		transformMatrix = new Matrix4f();
		inverseInertiaTensor = new Matrix3f();
		inverseInertiaTensorWorld = new Matrix3f();
		
		forceAccum = new Vector3f(0,0,0);
		torqueAccum = new Vector3f(0,0,0);
	}
	
	@Override
	public int attach(GameObject parent)
	{
		this.parent = parent;
		return 1;
	}
	
	public void calculateDerivedData()
	{
		
		calculateTransformMatrix(transformMatrix, parent.getTransform().getPos(), parent.getTransform().getRot());
		transformInertiaTensor(inverseInertiaTensorWorld, parent.getTransform().getRot(), inverseInertiaTensor, transformMatrix);
	}
	
	public void setInertiaTensor(Matrix3f inertiaTensor)
	{
		inverseInertiaTensor.setInverse(inertiaTensor);
		//checkInverseInertiaTensor(inverseInertiaTensor);
	}
	
	public static void calculateTransformMatrix(Matrix4f transformMatrix, Vector3f position, Quaternion orientation)
	{
		transformMatrix.m[0][0] = 1-2*orientation.getY()*orientation.getY()-
				2*orientation.getZ()*orientation.getZ();
		transformMatrix.m[0][1] = 2*orientation.getX()*orientation.getY() -
				2*orientation.getW()*orientation.getZ();
		transformMatrix.m[0][2] = 2*orientation.getX()*orientation.getZ() +
				2*orientation.getW()*orientation.getY();
		transformMatrix.m[0][3] = position.getX();
		
		transformMatrix.m[1][0] = 2*orientation.getX()*orientation.getY() +
				2*orientation.getW()*orientation.getZ();
		transformMatrix.m[1][1] = 1-2*orientation.getX()*orientation.getX()-
				2*orientation.getZ()*orientation.getZ();
		transformMatrix.m[1][2] = 2*orientation.getY()*orientation.getZ() -
				2*orientation.getW()*orientation.getX();
		transformMatrix.m[1][3] = position.getY();
		
		transformMatrix.m[2][0] = 2*orientation.getX()*orientation.getZ() -
				2*orientation.getW()*orientation.getY();
		transformMatrix.m[2][1] = 2*orientation.getY()*orientation.getZ() +
				2*orientation.getW()*orientation.getX();
		transformMatrix.m[2][2] = 1-2*orientation.getX()*orientation.getX()-
				2*orientation.getY()*orientation.getY();
		transformMatrix.m[2][3] = position.getZ();
	}
	
	public static void transformInertiaTensor(Matrix3f iitWorld, Quaternion q, Matrix3f iitBody, Matrix4f rotmat)
	{
		float t4 = rotmat.m[0][0]*iitBody.m[0][0]+
				rotmat.m[0][1]*iitBody.m[0][2]+
				rotmat.m[0][2]*iitBody.m[1][2];
		float t9 = rotmat.m[0][0]*iitBody.m[0][1]+
				rotmat.m[0][1]*iitBody.m[1][0]+
				rotmat.m[0][2]*iitBody.m[2][1];
		float t14 = rotmat.m[0][0]*iitBody.m[0][2]+
				rotmat.m[0][1]*iitBody.m[1][1]+
				rotmat.m[0][2]*iitBody.m[2][2];
		float t28 = rotmat.m[1][0]*iitBody.m[0][0]+
				rotmat.m[1][1]*iitBody.m[0][2]+
				rotmat.m[1][2]*iitBody.m[1][2];
		float t33 = rotmat.m[1][0]*iitBody.m[0][1]+
				rotmat.m[1][1]*iitBody.m[1][0]+
				rotmat.m[1][2]*iitBody.m[2][1];
		float t38 = rotmat.m[1][0]*iitBody.m[0][2]+
				rotmat.m[1][1]*iitBody.m[1][1]+
				rotmat.m[1][2]*iitBody.m[2][2];
		float t52 = rotmat.m[2][0]*iitBody.m[0][0]+
				rotmat.m[2][1]*iitBody.m[0][2]+
				rotmat.m[2][2]*iitBody.m[1][2];
		float t57 = rotmat.m[2][0]*iitBody.m[0][1]+
				rotmat.m[2][1]*iitBody.m[1][0]+
				rotmat.m[2][2]*iitBody.m[1][2];
		float t62 = rotmat.m[2][0]*iitBody.m[0][2]+
				rotmat.m[2][1]*iitBody.m[1][1]+
				rotmat.m[2][2]*iitBody.m[2][2];
		
		iitWorld.m[0][0] = t4*rotmat.m[0][0]+
				t9*rotmat.m[0][1]+
				t14*rotmat.m[0][2];
		iitWorld.m[0][1] = t4*rotmat.m[1][0]+
				t9*rotmat.m[1][1]+
				t14*rotmat.m[1][2];
		iitWorld.m[0][2] = t4*rotmat.m[2][0]+
				t9*rotmat.m[2][1]+
				t14*rotmat.m[2][2];
		iitWorld.m[0][2] = t28*rotmat.m[0][0]+
				t33*rotmat.m[0][1]+
				t38*rotmat.m[0][2];
		iitWorld.m[1][0] = t28*rotmat.m[1][0]+
				t33*rotmat.m[1][1]+
				t38*rotmat.m[1][2];
		iitWorld.m[1][1] = t28*rotmat.m[2][0]+
				t33*rotmat.m[2][1]+
				t38*rotmat.m[2][2];
		iitWorld.m[1][2] = t52*rotmat.m[0][0]+
				t57*rotmat.m[0][1]+
				t62*rotmat.m[0][2];
		iitWorld.m[2][1] = t52*rotmat.m[1][0]+
				t57*rotmat.m[1][1]+
				t62*rotmat.m[1][2];
		iitWorld.m[2][2] = t52*rotmat.m[2][0]+
				t57*rotmat.m[2][1]+
				t62*rotmat.m[2][2];
	}
	
	public void setPosition(Vector3f v){parent.getTransform().setPos(v);}
	public Vector3f getPosition(){return parent.getTransform().getPos();}
	
	public void setOrientation(Quaternion q){parent.getTransform().setRot(q);}
	public Quaternion getOrientation(){return parent.getTransform().getRot();}
	
	public Vector3f getVelocity() {return velocity;}
	public void setVelocity(Vector3f velocity) {this.velocity = velocity;}

	public Vector3f getAcceleration() {	return acceleration;}
	public void setAcceleration(Vector3f acceleration) {this.acceleration = acceleration;}

	public float getInverseMass() {return inverseMass;}
	public float getMass() {return 1/inverseMass;}
	public void setMass(float mass) {this.inverseMass = 1/mass;}
	
	public void addForce(Vector3f v){forceAccum = forceAccum.add(v); isAwake = true;}
	public void addTorque(Vector3f v){torqueAccum = torqueAccum.add(v); isAwake = true;}
	public void addVelocity(Vector3f v){velocity = velocity.add(v); isAwake = true;}
	public void addRotation(Vector3f v){rotationVelocity = rotationVelocity.add(v); isAwake = true;}
	
	
	public void addForceAtBodyPoint(Vector3f force, Vector3f point)
	{
		Vector3f pt = getPointInWorldSpace(point);
		addForceAtPoint(force, pt);
	}
	
	public void addForceAtPoint(Vector3f force, Vector3f point)
	{
		Vector3f distance = point.sub(parent.getTransform().getPos());
		addForce(force);
		addTorque(distance.cross(force));
		isAwake = true;
	}
	
	public Vector3f getPointInLocalSpace(Vector3f point)
	{
		return transformMatrix.transformInverse(point);
	}
	
	public Vector3f getPointInWorldSpace(Vector3f point)
	{
		return transformMatrix.transform(point);
	}
	
	public Vector3f getDirectionInLocalSpace(Vector3f direction)
	{
		return transformMatrix.transformInverseDirection(direction);
	}
	
	public Vector3f getDirectionInWorldSpace(Vector3f direction)
	{
		return transformMatrix.transformDirection(direction);
	}
	
	public void clearAccumulators()
	{
		forceAccum = new Vector3f(0,0,0);
		torqueAccum = new Vector3f(0,0,0);
	}
	
	public int integrate(float delta)
	{
		if (!isAwake) return 0;
		
		lastFrameAcceleration = acceleration.add(forceAccum.mul(inverseMass));
		
		Vector3f angularAcceleration = inverseInertiaTensorWorld.transform(torqueAccum);
		
		velocity = velocity.add(lastFrameAcceleration.mul(delta)).mul((float)Math.pow(linearDamping, delta));
		rotationVelocity = rotationVelocity.add(angularAcceleration.mul(delta)).mul((float)Math.pow(angularDamping, delta));
		return 1;
	}
		
	public int simulate(float delta)
	{
		if (!isAwake) return 0;
		
		//System.out.println(rotationVelocity);
		//System.out.println(parent.getTransform().getRot());
		
		parent.getTransform().setPos(parent.getTransform().getPos().add(velocity.mul(delta)));
		
		parent.getTransform().getRot().addScaledVector(rotationVelocity, delta);
		
		parent.getTransform().getRot().normalize();
		
		calculateDerivedData();
		
		clearAccumulators();
		
		if(canSleep)
		{
			float currentMotion = velocity.dot(velocity) + rotationVelocity.dot(rotationVelocity);
			
			float bias = (float) Math.pow(0.5f, delta);
			
			motion = bias*motion + (1-bias)*currentMotion;
			
			if(motion < sleepEpsilon) setAwake(false);
			else if(motion > 10 * sleepEpsilon) motion = 10 * sleepEpsilon;
		}
		
		return 1;
	}
	
	public void getInverseInertiaTensorWorld(Matrix3f inverseInertiaTensor)
	{
		inverseInertiaTensor.setM(this.inverseInertiaTensor.m);
	}
	
	public Matrix3f getInverseInertiaTensorWorld()
	{
		return inverseInertiaTensor;
	}
	
	public boolean getAwake()
	{
		return isAwake;
	}
	
	public void setAwake()
	{
		setAwake(true);
	}
	
	public void setAwake(boolean awake)
	{
		if(awake)
		{
			isAwake = true;
			
			motion = sleepEpsilon*2.0f;
		}
		else
		{
			isAwake = false;
			velocity = new Vector3f(0,0,0);
			rotationVelocity = new Vector3f(0,0,0);
		}
	}
	
	public boolean getCanSleep()
	{
		return canSleep;
	}
	
	public void setCanSleep()
	{
		setCanSleep(true);
	}
	
	public void setCanSleep(boolean canSleep)
	{
		this.canSleep = canSleep;
	}
	
	public Vector3f getRotation()
	{
		return rotationVelocity;
	}

	public Vector3f getLastFrameAcceleration() {
		return lastFrameAcceleration;
	}
	
	@Override
	public RigidBody getBody()
	{
		return this;
	}
}
