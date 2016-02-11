package com.base.engine.physics.Particles.Collisions;

import com.base.engine.core.math.Vector3f;
import com.base.engine.physics.Particles.Particle;

public class ParticleContact 
{
	Particle particle[];
	
	float restitution;
	
	Vector3f contactNormal;
	
	float penetration;
	
	protected void resolve(float delta)
	{
		resolveVelocity(delta);
		resolveInterpenetration(delta);
	}
	
	protected float calculateSeparatingVelocity()
	{
		Vector3f relativeVelocity = particle[0].getVelocity();
		if(particle[1] != null) relativeVelocity = relativeVelocity.sub(particle[1].getVelocity());
		return relativeVelocity.dot(contactNormal);
	}
	
	private void resolveVelocity(float delta)
	{
		float separatingVelocity = calculateSeparatingVelocity();
		
		if(separatingVelocity > 0) return;
		
		float newSepVelocity = -separatingVelocity * restitution;
		
		Vector3f accCausedVelocity = particle[0].getAcceleration();
		if(particle[1] != null) accCausedVelocity = accCausedVelocity.sub(particle[1].getAcceleration());
		float accCausedSepVelocity = accCausedVelocity.dot(contactNormal) * delta;
		
		if(accCausedSepVelocity < 0)
		{
			newSepVelocity += accCausedSepVelocity * restitution;
			
			if(newSepVelocity < 0) newSepVelocity = 0;
		}
		
		float deltaVelocity = newSepVelocity - separatingVelocity;
		
		float totalInverseMass = particle[0].getInverseMass();
		if(particle[1] != null) totalInverseMass += particle[1].getInverseMass();
		
		if(totalInverseMass <=0) return;
		
		float impulse = deltaVelocity / totalInverseMass;
		
		Vector3f impulsePerIMass = contactNormal.mul(impulse);
		
		particle[0].setVelocity(particle[0].getVelocity().add(impulsePerIMass.mul(particle[0].getInverseMass())));
		
		if(particle[1] != null)
		{
			particle[1].setVelocity(particle[1].getVelocity().add(impulsePerIMass.mul(-particle[1].getInverseMass())));
		}
	}
	
	private void resolveInterpenetration(float delta)
	{
		if(penetration <= 0) return;
		
		float totalInverseMass = particle[0].getInverseMass();
		if(particle[1] != null) totalInverseMass += particle[1].getInverseMass();
		
		if(totalInverseMass <=0) return;
		
		Vector3f movePerIMass = contactNormal.mul(-penetration/totalInverseMass);
		
		particle[0].setPosition(particle[0].getPosition().add(movePerIMass.mul(particle[0].getInverseMass())));
		
		if(particle[1] != null)
		{
			particle[1].setPosition(particle[1].getPosition().add(movePerIMass.mul(particle[1].getInverseMass())));
		}
	}
}
