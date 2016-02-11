package com.base.engine.physics.Particles.ForceGenerators;

import com.base.engine.core.math.Vector3f;
import com.base.engine.physics.Particles.Particle;

public class ParticleBuoyancy implements ParticleForceGenerator
{
	float maxDepth;
	float volume;
	float waterHeight;
	float liquidDensity;
	
	
	public ParticleBuoyancy(float maxDepth, float volume, float waterHeight) {this(maxDepth, volume, waterHeight, 1000.0f);}
	
	public ParticleBuoyancy(float maxDepth, float volume, float waterHeight, float liquidDensity) 
	{
		this.maxDepth = maxDepth;
		this.volume = volume;
		this.waterHeight = waterHeight;
		this.liquidDensity = liquidDensity;
	}


	@Override
	public void updateForce(Particle particle, float delta) 
	{
		float depth = particle.getPosition().getY();
		
		if(depth >= waterHeight + maxDepth) return;
		
		Vector3f force = new Vector3f(0,0,0);
		
		if(depth <= waterHeight - maxDepth)
		{
			force = force.addY(liquidDensity * volume);
			particle.addForce(force);
			return;
		}
		
		force = force.addY(liquidDensity * volume * (depth - maxDepth - waterHeight) / 2 * maxDepth);
		particle.addForce(force);
	}
	
	
	
}
