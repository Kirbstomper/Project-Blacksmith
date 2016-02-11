package com.base.engine.physics.Particles.Collisions;

public class ParticleContactResolver 
{
	protected int iterations; //Most iterations allows
	protected int iterationsUsed;
	
	public ParticleContactResolver(int iterations)
	{
		this.iterations = iterations;
	}
	
	public void setIterations(int iterations)
	{
		this.iterations = iterations;
	}
	
	public void resolveContacts(ParticleContact[] contactArray, int numContacts, float delta)
	{
		iterationsUsed = 0;
		while(iterationsUsed < iterations)
		{
			//Find the contact with the largest closing velocity
			float max = 0;
			
			int maxIndex = numContacts;
			for(int i = 0; i < numContacts; i++)
			{
				float sepVel = contactArray[i].calculateSeparatingVelocity();
				if(sepVel < max)
				{
					max = sepVel;
					maxIndex = i;
				}
			}
			
			//Resolve this contact
			contactArray[maxIndex].resolve(delta);
			iterationsUsed++;
		}
	}
	
}
