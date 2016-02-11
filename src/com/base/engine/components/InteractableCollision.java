package com.base.engine.components;

import com.base.engine.components.attachments.Interactable;
import com.base.engine.physics.collision.Collider;

public class InteractableCollision extends GameComponent implements Interactable
{
	Collider collider;
	
	public InteractableCollision(Collider collider)
	{
		this.collider = collider;
	}

	@Override
	public void interact() 
	{
		System.out.println("Works!");
		
	}
	
	@Override
	public Collider getCollider()
	{
		return collider;
	}

}
