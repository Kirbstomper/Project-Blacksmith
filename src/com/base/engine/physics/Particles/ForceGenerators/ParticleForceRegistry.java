package com.base.engine.physics.Particles.ForceGenerators;
import java.util.ArrayList;

import com.base.engine.core.math.Pair;
import com.base.engine.physics.Particles.Particle;

public class ParticleForceRegistry 
{
	private class Registry extends Pair<Particle,ParticleForceGenerator>
	{public Registry(Particle first, ParticleForceGenerator second) {super(first, second);}}
	
	ArrayList<Registry> registry;
	
	public ParticleForceRegistry()
	{
		registry = new ArrayList<Registry>();
	}
	
	public void add(Particle p, ParticleForceGenerator fg)
	{
		registry.add(new Registry(p,fg));
	}
	
	public void remove(Particle p, ParticleForceGenerator fg)
	{
		for(int i = registry.size()-1; i >=0; i--)
		{
			if(registry.get(i).first == p && registry.get(i).second == fg)
			{
				registry.get(i).delete();
				registry.remove(i);
			}
		}
	}
	
	public void clear()
	{
		for(int i = registry.size()-1; i >=0; i--)
		{
			registry.get(i).delete();
			registry.remove(i);
		}
	}
	
	public void updateForces(float delta)
	{
		for(int i = registry.size()-1; i >=0; i--)
		{
			registry.get(i).second.updateForce(registry.get(i).first, delta);
		}
	}
}
