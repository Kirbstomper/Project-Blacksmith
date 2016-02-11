package com.base.engine.components.attachments;

import com.base.engine.core.math.Matrix4f;
import com.base.engine.rendering.Shader;

public interface LightAttachment extends ComponentAttachment
{
	public Shader getShader();
	
	public Matrix4f getMVP();
	
}
