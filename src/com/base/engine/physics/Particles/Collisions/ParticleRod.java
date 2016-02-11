package com.base.engine.physics.Particles.Collisions;

import com.base.engine.core.math.Vector3f;

public class ParticleRod extends ParticleLink
{
	public float length;
	
	@Override
	public int fillContact(ParticleContact contact, int limit)
	{
		float currentLen = currentLength();
		
		if(currentLen == length) return 0;
		
		contact.particle[0] = particle[0];
		contact.particle[1] = particle[1];
		
		Vector3f normal = particle[1].getPosition().sub(particle[0].getPosition()).normal();
		
		
		if(currentLen > length)
		{
			contact.contactNormal = normal;
			contact.penetration = currentLen - length;
		}
		else
		{
			contact.contactNormal = normal.mul(-1);
			contact.penetration = length - currentLen;
		}
		
		contact.restitution = 0;
		
		return 1;
	}
	
	
}
