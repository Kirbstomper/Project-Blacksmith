package com.base.engine.physics.Particles.ForceGenerators;

import com.base.engine.core.math.Vector3f;
import com.base.engine.physics.Particles.Particle;

public class ParticleBungee implements ParticleForceGenerator
{
	Particle other;
	float k; //the spring constant
	float rest; //Rest length
	
	
	public ParticleBungee(Particle other, float k, float rest)
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
		if(magnitude <= rest) return;
		
		magnitude = k * (rest - magnitude);
		
		particle.addForce(force.normal().mul(-magnitude));
	}
	
}
