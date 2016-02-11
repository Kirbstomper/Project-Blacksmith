package com.base.engine.components;

import com.base.engine.components.attachments.Collidable;
import com.base.engine.components.attachments.Physical;
import com.base.engine.physics.RigidBody.RigidBody;
import com.base.engine.physics.collision.Primitive;

public class PhysicsComponent extends GameComponent implements Physical, Collidable
{
	boolean check = true;
	@Override
	public Primitive getPrimitive() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RigidBody getBody() {
		// TODO Auto-generated method stub
		return null;
	}
	//Collider colliders[];

	@Override
	public boolean check() {
		return check;
	}
}
