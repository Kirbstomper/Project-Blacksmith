package com.base.engine.components.attachments;

public interface Controlable extends ComponentAttachment
{
	public default int input(float delta){return 1;}
}
