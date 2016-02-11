package com.base.engine.physics.Particles.ForceGenerators;
import com.base.engine.core.math.Vector3f;
import com.base.engine.physics.Particles.Particle;

public class ParticleDrag implements ParticleForceGenerator
{
	float k1;
	float k2;
	
	public ParticleDrag(float k1, float k2)
	{
		this.k1 = k1;
		this.k2 = k2;
	}

	@Override
	public void updateForce(Particle particle, float delta) 
	{
		Vector3f force = new Vector3f(particle.getVelocity());
		
		float drag = force.length();
		drag = k1 * drag + k2 * drag * drag;
		
		force = force.normal().mul(-drag);
		
		particle.addForce(force);
	}
}
