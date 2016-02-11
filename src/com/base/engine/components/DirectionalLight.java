package com.base.engine.components;

import com.base.engine.components.attachments.LightAttachment;
import com.base.engine.core.math.Vector3f;
import com.base.engine.rendering.Shader;

public class DirectionalLight extends BaseLight implements LightAttachment
{
	public DirectionalLight(Vector3f color, float intensity)
	{
		super(color, intensity);

		setShader(new Shader("forward-directional"));
	}

	public Vector3f getDirection()
	{
		return getTransform().getTransformedRot().getForward();
	}
}
