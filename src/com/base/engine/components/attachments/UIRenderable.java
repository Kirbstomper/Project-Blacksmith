package com.base.engine.components.attachments;

import com.base.engine.rendering.RenderingEngine;

public interface UIRenderable extends ComponentAttachment
{
	public default int render(RenderingEngine renderingEngine){return 1;}
	public default boolean isRendering(){return true;}
}
