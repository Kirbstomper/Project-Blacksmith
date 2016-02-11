package com.base.engine.components.attachments;

import com.base.engine.physics.collision.Primitive;

public interface Collidable extends ComponentAttachment
{
	public default int checkCollisions(){return 1;}
	public boolean check(); 
	//public default Rigid getCollider(){return null;}
	//public RigidBody getBody();
	Primitive getPrimitive();
}
