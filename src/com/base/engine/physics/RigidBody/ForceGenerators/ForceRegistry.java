package com.base.engine.physics.RigidBody.ForceGenerators;
import java.util.ArrayList;

import com.base.engine.core.math.Pair;
import com.base.engine.physics.RigidBody.RigidBody;

public class ForceRegistry 
{
	private class Registry extends Pair<RigidBody,ForceGenerator>
	{public Registry(RigidBody first, ForceGenerator second) {super(first, second);}}
	
	ArrayList<Registry> registry;
	
	public ForceRegistry()
	{
		registry = new ArrayList<Registry>();
	}
	
	public void add(RigidBody b, ForceGenerator fg)
	{
		registry.add(new Registry(b,fg));
	}
	
	public void remove(RigidBody b, ForceGenerator fg)
	{
		for(int i = registry.size()-1; i >=0; i--)
		{
			if(registry.get(i).first == b && registry.get(i).second == fg)
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
