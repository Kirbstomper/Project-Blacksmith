package com.base.engine.physics.Particles.ForceGenerators;

import com.base.engine.core.math.Vector3f;
import com.base.engine.physics.Particles.Particle;

public class ParticleSpring implements ParticleForceGenerator
{
	Particle other;
	float k; //the spring constant
	float rest; //Rest length
	
	
	public ParticleSpring(Particle other, float k, float rest)
	{
		this.other = other;
		this.k = k;
		this.rest = rest;
	}
	
	@Override
	public void updateForce(Particle particle, float delta)
	{
		Vector3f force = other.getPosition().sub(particle.getPosition());
		
		float magnitude = force.length();
		magnitude = Math.abs(magnitude - rest) * k;
		
		particle.addForce(force.normal().mul(-magnitude));
	}
	
}
