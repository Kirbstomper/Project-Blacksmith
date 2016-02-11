package com.base.engine.physics.collision;

import com.base.engine.core.math.Vector3f;

public class ContactResolver 
{
	protected int velocityIterations;
	protected int positionIterations;
	protected float velocityEpsilon;
	protected float positionEpsilon;
	public int velocityIterationsUsed;
	public int positionIterationsUsed;
	//TODO valid Settings
	//private boolean validSettings;
	
	public ContactResolver(int iterations)
	{
		this(iterations, .01f, .01f);
	}
	
	public ContactResolver(int iterations, float velocityEpsilon, float positionEpsilon)
	{
		setIterations(iterations, iterations);
		setEpsilon(velocityEpsilon, positionEpsilon);
	}
	
	public ContactResolver(int velocityIterations, int positionIterations)
	{
		this(velocityIterations, positionIterations, .01f, .01f);
	}
	
	public ContactResolver(int velocityIterations, int positionIterations, float velocityEpsilon, float positionEpsilon)
	{
		setIterations(velocityIterations);
		setEpsilon(velocityEpsilon, positionEpsilon);
	}
	
	public boolean isValid()
	{
		return (velocityIterations > 0) && 
				(positionEpsilon >= 0.0f) && 
				(positionEpsilon >= 0.0f); 

	}
	
	public void setIterations(int velocityIterations, int positionIterations)
	{
		this.velocityIterations = velocityIterations;
		this.positionIterations = positionIterations;
	}
	
	public void setIterations(int iterations)
	{
		setIterations(iterations, iterations);
	}
	
	public void setEpsilon(float velocityEpsilon, float positionEpsilon)
	{
		this.velocityEpsilon = velocityEpsilon;
		this.positionEpsilon = positionEpsilon;
	}
	
	public void resolveContacts(Contact contacts[], int numContacts, float delta)
	{
		if(numContacts == 0) return;
		if(!isValid()) return;
		
		Contact realContacts[] = new Contact[numContacts];
		int x = 0;
		
		for(int i = 0; i < contacts.length; i++)
		{
			if(contacts[i] != null)
			{
				realContacts[x] = contacts[i];
				x++;
				if(x >= numContacts)
				{
					break;
				}
			}
		}
			
		prepareContacts(realContacts, numContacts, delta);
		
		adjustPositions(realContacts, numContacts, delta);
		
		adjustVelocities(realContacts, numContacts, delta);
	}
	
	protected void prepareContacts(Contact contacts[], int numContacts, float delta)
	{
		for(Contact contact : contacts)
		{
			if(contact != null)
				contact.calculateInternals(delta);
		}
	}
	
	protected void adjustPositions(Contact contacts[], int numContacts, float delta)
	{
		int i, index;
		Vector3f linearChange[] = new Vector3f[2];
		linearChange[0] = new Vector3f(0,0,0);
		linearChange[1] = new Vector3f(0,0,0);
		Vector3f angularChange[] = new Vector3f[2];
		angularChange[0] = new Vector3f(0,0,0);
		angularChange[1] = new Vector3f(0,0,0);
		float max;
		Vector3f deltaPosition;
		
		positionIterationsUsed = 0;
		while(positionIterationsUsed < positionIterations)
		{
			max = positionEpsilon;
			index = numContacts;
			for(i = 0; i < numContacts; i++)
			{
				if(contacts[i] != null && contacts[i].penetration > max)
				{
					max = contacts[i].penetration;
					index = i;
				}
			}
			if(index == numContacts) break;
			
			contacts[index].matchAwakeState();
			
			contacts[index].applyPositionChange(linearChange, angularChange, max);
			
			//NO MORE ANGLES!!!
			//angularChange[0] = new Vector3f(0,0,0);
			//angularChange[1] = new Vector3f(0,0,0);
			
			for(i = 0; i <numContacts; i++)
			{
				for(int b = 0; b < 2; b++) 
				{
					if(contacts[i] != null && contacts[i].body[b] != null)
					{
						for(int d = 0; d < 2; d++)
						{
							if(contacts[i].body[d] != null)
							{
								deltaPosition = linearChange[d].add(angularChange[d].cross(contacts[i].relativeContactPosition[b]));
								
								contacts[i].penetration += deltaPosition.dot(contacts[i].contactNormal) * ((b==1)?1:-1);
							}
						}
					}
				}
			}
			positionIterationsUsed++;
		}
	}
	
	protected void adjustVelocities(Contact contacts[], int numContacts, float delta)
	{
		Vector3f velocityChange[] = new Vector3f[2];
		velocityChange[0] = new Vector3f(0,0,0);
		velocityChange[1] = new Vector3f(0,0,0);
		Vector3f rotationChange[] = new Vector3f[2];
		rotationChange[0] = new Vector3f(0,0,0);
		rotationChange[1] = new Vector3f(0,0,0);
		Vector3f deltaVel = new Vector3f(0,0,0);
		
		
		velocityIterationsUsed = 0;
		while(velocityIterationsUsed < velocityIterations)
		{
			float max = velocityEpsilon;
			int index = numContacts;
			for(int i = 0; i < numContacts; i++)
			{
				if(contacts[i] != null && contacts[i].desiredDeltaVelocity > max)
				{
					max = contacts[i].desiredDeltaVelocity;
					index = i;
				}
			}
			if(index == numContacts) break;
			
			contacts[index].matchAwakeState();
			
			contacts[index].applyVelocityChange(velocityChange, rotationChange);
			//rotationChange[0] = new Vector3f(0,0,0);
			//rotationChange[1] = new Vector3f(0,0,0);
			
			for(int i = 0; i <numContacts; i++)
			{
				for(int b = 0; b < 2; b++) 
				{
					if(contacts[i] != null && contacts[i].body[b] != null)
					{
						for(int d = 0; d < 2; d++)
						{
							if(contacts[i].body[d] != null)
							{
								deltaVel = velocityChange[d].add(rotationChange[d].cross(contacts[i].relativeContactPosition[b]));
								
								contacts[i].contactVelocity = contacts[i].contactVelocity.add(contacts[i].contactToWorld.transpose().transform(deltaVel).mul((b==1)?-1:1));
								contacts[i].calculateDesiredDeltaVelocity(delta);
							}
						}
					}
				}
			}
			velocityIterationsUsed++;
		}
		
	}
}
