package com.base.engine.physics.Particles.ForceGenerators;

import com.base.engine.physics.Particles.Particle;

public interface ParticleForceGenerator 
{
	public void updateForce(Particle particle, float delta);
}
