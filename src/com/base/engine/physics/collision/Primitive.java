package com.base.engine.physics.collision;

import com.base.engine.components.attachments.Collidable;
import com.base.engine.core.math.Matrix4f;
import com.base.engine.core.math.Vector3f;
import com.base.engine.physics.RigidBody.RigidBody;

public class Primitive extends Collider implements Collidable
{
	RigidBody body;
	Matrix4f offset;
	boolean check = true;
	
	public void calculateInternals()
	{
		if(offset == null)
		{
			offset = new Matrix4f();
			offset = offset.initIdentity();
		}
	}
	
	
	@Override
	public Primitive getPrimitive()
	{
		return this;
	}
	
	public void attach(RigidBody body)
	{
		this.body = body;
	}
	
	public Vector3f getAxis(int index)
	{
		return parent.getTransform().getTransformation().mul(offset).getAxisVector(index);
	}

	public void setCheck(boolean c)
	{
		check = c;
	}
	
	@Override
	public boolean check() {
		return check;
	}
	
	
}
