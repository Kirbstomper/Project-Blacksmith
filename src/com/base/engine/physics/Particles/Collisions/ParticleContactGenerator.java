package com.base.engine.physics.Particles.Collisions;

public interface ParticleContactGenerator 
{
	public default int addContact(ParticleContact nextContact, int limit){return 0;}
}
