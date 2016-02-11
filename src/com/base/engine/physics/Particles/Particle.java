package com.base.engine.physics.Particles;
import com.base.engine.core.math.Vector3f;

public class Particle 
{
	private Vector3f position;
	private Vector3f velocity;
	private Vector3f acceleration;
	
	private Vector3f forceAccum;
	
	private float damping;
	private float inverseMass;
	
	private boolean finite;
	
	public Particle(){this(true);}
	
	public Particle(boolean finite)
	{
		position = new Vector3f(0,0,0);
		velocity = new Vector3f(0,0,0);
		acceleration = new Vector3f(0,0,0);
		
		forceAccum = new Vector3f(0,0,0);
		
		damping = 0;
		inverseMass = 0;
		
		this.finite = finite;
	}
	
	public void integrate(float delta)
	{
		position = position.add(velocity.mul(delta));
		
		Vector3f acc = new Vector3f(acceleration);
		acc = acc.add(forceAccum.mul(inverseMass));
		
		velocity = velocity.add(acc.mul(delta)).mul((float)Math.pow(damping, delta));
		
		clearAccumulator();
	}
	
	public void setPosition(Vector3f v){position = v;}
	public Vector3f getPosition(){return position;}
	
	public Vector3f getVelocity() {return velocity;}
	public void setVelocity(Vector3f velocity) {this.velocity = velocity;}

	public Vector3f getAcceleration() {	return acceleration;}
	public void setAcceleration(Vector3f acceleration) {this.acceleration = acceleration;}

	public float getDamping() {return damping;}
	public void setDamping(float damping) {this.damping = damping;}

	public float getInverseMass() {return inverseMass;}
	public float getMass() {return 1/inverseMass;}
	public void setMass(float mass) {this.inverseMass = 1/mass;}
	
	public void addForce(Vector3f v){forceAccum = forceAccum.add(v);}
	
	public void clearAccumulator(){forceAccum = new Vector3f(0,0,0);}

	public String toString()
	{
		return "Position: " + position.toString() + " Velocity: " + velocity.toString()  + " Acceleration: " + acceleration.toString();
	}

	public boolean hasFiniteMass() {return finite;}
}
