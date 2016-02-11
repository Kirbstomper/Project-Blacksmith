package com.base.engine.components;

import com.base.engine.components.attachments.Updatable;

public abstract class TimeTrigger extends GameComponent implements Updatable
{
	private float time;
	
	public TimeTrigger(float time)
	{
		super();
		this.time = time;
	}
	
	@Override
	public int update(float delta)
	{
		time -= delta;
		if(time <= 0)
		{
			trigger();
		}
		return 1;
	}
	
	protected abstract void trigger();
}
