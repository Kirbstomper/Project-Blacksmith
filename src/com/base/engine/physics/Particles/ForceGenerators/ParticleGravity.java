package com.base.engine.physics.Particles.ForceGenerators;
import com.base.engine.core.math.Vector3f;
import com.base.engine.physics.Particles.Particle;

public class ParticleGravity implements ParticleForceGenerator
{
	Vector3f gravity;
	
	public ParticleGravity(Vector3f gravity)
	{
		this.gravity = gravity;
	}
	
	@Override
	public void updateForce(Particle particle, float delta) 
	{
		if(!particle.hasFiniteMass()) return;
		
		particle.addForce(gravity.mul(particle.getMass()));//Might need to remove particle.getMass()
	}

}
