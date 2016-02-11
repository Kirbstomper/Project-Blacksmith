package com.base.engine.components.attachments;

public interface Updatable extends ComponentAttachment
{
	public default int update(float delta){return 1;}
}
