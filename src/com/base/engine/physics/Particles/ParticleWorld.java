package com.base.engine.physics.Particles;

import com.base.engine.physics.Particles.Collisions.ParticleContact;
import com.base.engine.physics.Particles.Collisions.ParticleContactGenerator;
import com.base.engine.physics.Particles.Collisions.ParticleContactResolver;
import com.base.engine.physics.Particles.ForceGenerators.ParticleForceRegistry;

public class ParticleWorld 
{
	private class ParticleRegistration
	{
		public Particle particle;
		public ParticleRegistration next;
	}
	
	private class ContactGenRegistration
	{
		public ParticleContactGenerator gen;
		public ContactGenRegistration next;
	}
	
	ParticleRegistration firstParticle;
	ContactGenRegistration firstContactGen;
	
	ParticleForceRegistry registry;
	ParticleContactResolver resolver;
	
	ParticleContact contacts[];
	
	int maxContacts;
	
	
	public ParticleWorld(int maxContacts)
	{
		this(maxContacts, 0);
	}
	
	public ParticleWorld(int maxContacts, int iterations)
	{
		this.maxContacts = maxContacts;
	}
	
	public void startFrame()
	{
		ParticleRegistration reg = firstParticle;
		
		while(reg != null)
		{
			reg.particle.clearAccumulator();
			
			reg = reg.next;
		}
	}
	
	public int generateContacts()
	{
		int limit = maxContacts;
		int i = 0;
		ParticleContact nextContact = contacts[i];
		
		ContactGenRegistration reg = firstContactGen;
		while(reg != null)
		{
			int used = reg.gen.addContact(nextContact, limit);
			limit -=used;
			i+= used;
			nextContact = contacts[i];
			
			if(limit <= 0) break;
			
			reg = reg.next;
		}
		
		return maxContacts - limit;
	}
	
	public void integrate(float delta)
	{
		ParticleRegistration reg = firstParticle;
		while(reg != null)
		{
			reg.particle.integrate(delta);
			reg = reg.next;
		}
	}
	
	public void runPhysics(float delta)
	{
		registry.updateForces(delta);
		
		integrate(delta);
		
		int usedContacts = generateContacts();
		
		resolver.setIterations(usedContacts * 2); // if(calculateIterations) ???
		resolver.resolveContacts(contacts, usedContacts, delta);
	}
}
