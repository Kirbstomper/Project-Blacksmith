package com.base.engine.physics.Particles.ForceGenerators;

import com.base.engine.core.math.Vector3f;
import com.base.engine.physics.Particles.Particle;

public class ParticleAnchoredSpring implements ParticleForceGenerator
{
	Vector3f anchor;
	float k; //the spring constant
	float rest; //Rest length
	
	
	public ParticleAnchoredSpring(Vector3f anchor, float k, float rest)
	{
		this.anchor = anchor;
		this.k = k;
		this.rest = rest;
	}
	
	@Override
	public void updateForce(Particle particle, float delta)
	{
		Vector3f force = particle.getPosition().sub(anchor);
		
		float magnitude = force.length();
		magnitude = Math.abs(magnitude - rest) * k;
		
		particle.addForce(force.normal().mul(-magnitude));
	}
	
}
