package com.base.engine.components;

import com.base.engine.components.attachments.Updatable;

public class Decay extends TimeTrigger implements Updatable
{

	public Decay(float time) 
	{
		super(time);
	}
	
	protected void trigger()
	{
		this.getParent().delete();
	}
}
