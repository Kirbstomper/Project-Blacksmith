package com.base.engine.physics.Particles.Collisions;

import com.base.engine.core.math.Vector3f;
import com.base.engine.physics.Particles.Particle;

public class ParticleLink 
{
	public Particle particle[];
	
	protected float currentLength()
	{
		Vector3f relativePos = particle[0].getPosition().sub(particle[1].getPosition());
		return relativePos.length();
	}
	
	public int fillContact(ParticleContact contact, int limit)
	{
		return 0;
	}
}
