package com.base.engine.physics.Particles.Collisions;

import com.base.engine.core.math.Vector3f;

public class ParticleCable extends ParticleLink
{
	public float maxLength;
	public float restitution;
	
	@Override
	public int fillContact(ParticleContact contact, int limit)
	{
		float length = currentLength();
		
		if(length < maxLength) return 0;
		
		contact.particle[0] = particle[0];
		contact.particle[1] = particle[1];
		
		Vector3f normal = particle[1].getPosition().sub(particle[0].getPosition()).normal();
		
		contact.contactNormal = normal;
		
		contact.penetration = length - maxLength;
		contact.restitution = restitution;
		
		return 1;
	}

}
