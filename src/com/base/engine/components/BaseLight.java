package com.base.engine.components;

import com.base.engine.components.attachments.LightAttachment;
import com.base.engine.core.CoreEngine;
import com.base.engine.core.math.Matrix4f;
import com.base.engine.core.math.Vector3f;
import com.base.engine.rendering.Shader;

public class BaseLight extends GameComponent implements LightAttachment
{
	private Matrix4f depthMVP;
	private Vector3f color;
	private float intensity;
	private Shader shader;
	
	public BaseLight(Vector3f color, float intensity)
	{
		this.depthMVP = null;
		this.color = color;
		this.intensity = intensity;
	}

	@Override
	public void addToEngine(CoreEngine engine)
	{
		engine.getRenderingEngine().addLight(this);
	}

	public void setShader(String shader)
	{
		setShader(new Shader(shader));
	}
	
	public void setShader(Shader shader)
	{
		this.shader = shader;
	}

	@Override
	public Shader getShader()
	{
		return shader;
	}

	public Vector3f getColor()
	{
		return color;
	}

	public void setColor(Vector3f color)
	{
		this.color = color;
	}

	public float getIntensity()
	{
		return intensity;
	}

	public void setIntensity(float intensity)
	{
		this.intensity = intensity;
	}
	
	public void setMVP(Matrix4f mvp)
	{
		this.depthMVP = mvp;
	}
	
	public Matrix4f getMVP()
	{
		return this.depthMVP;
	}
}
