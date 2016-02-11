package com.base.engine.components.attachments;

import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.Shader;

public interface Renderable extends ComponentAttachment
{
	public default int render(Shader shader, RenderingEngine rengine){return 1;}
	public default boolean isRendering(){return true;}
}
