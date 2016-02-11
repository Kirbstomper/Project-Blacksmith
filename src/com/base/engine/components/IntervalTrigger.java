package com.base.engine.components;

import com.base.engine.components.attachments.Updatable;

public abstract class IntervalTrigger extends GameComponent implements Updatable
{
	private float time;
	private float interval;
	
	public IntervalTrigger(float interval)
	{
		super();
		this.interval = interval;
		this.time = interval;
	}
	
	@Override
	public int update(float delta)
	{
		time -= delta;
		if(time <= 0)
		{
			trigger();
			time = interval;
		}
		return 1;
	}
	
	protected abstract void trigger();
}
