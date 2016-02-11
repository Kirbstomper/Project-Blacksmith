package com.base.engine.components;

import com.base.engine.components.attachments.Updatable;
import com.base.engine.core.math.Vector3f;

public class MoveComponent extends GameComponent implements Updatable
{
	int axis;
	float move;
	
	public MoveComponent(int axis, float move)
	{
		this.axis = axis;
		this.move = move;
	}
	
	@Override
	public int update(float delta)
	{
		Vector3f direction = new Vector3f(0,0,0);
		switch(axis)
		{
		case 0:
			direction.x = 1;
			break;
		case 1:
			direction.y = 1;
			break;
		case 2:
			direction.z = 1;
			break;
		}
		
		getTransform().setPos(getTransform().getPos().add(direction.mul(move * delta)));
		return 1;
	}
}
