package com.base.engine.components;

import com.base.engine.components.attachments.Renderable;
import com.base.engine.components.attachments.Updatable;
import com.base.engine.core.GameObject;
import com.base.engine.core.math.Quaternion;
import com.base.engine.core.math.Vector3f;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.Shader;

public class LookAtComponent extends GameComponent implements Updatable, Renderable
{
	RenderingEngine renderingEngine;
	GameObject target;
	
	public LookAtComponent()
	{
		
	}
	
	public LookAtComponent(GameObject target)
	{
		this.target = target;
	}
	
	@Override
	public int update(float delta)
	{
		if(target == null)
		{
			if(renderingEngine != null)
			{
				Quaternion newRot = getTransform().getLookAtRotation(renderingEngine.getMainCamera().getTransform().getTransformedPos(),
						new Vector3f(0,1,0));
						//getTransform().getRot().getUp());
	
				getTransform().setRot(getTransform().getRot().nlerp(newRot, delta * 5.0f, true));
				//getTransform().setRot(getTransform().getRot().slerp(newRot, delta * 5.0f, true));
			}
		}
		else
		{
			if(renderingEngine != null)
			{
				Quaternion newRot = getTransform().getLookAtRotation(target.getTransform().getTransformedPos(),
						new Vector3f(0,1,0));
						//getTransform().getRot().getUp());
	
				getTransform().setRot(getTransform().getRot().nlerp(newRot, delta * 5.0f, true));
				//getTransform().setRot(getTransform().getRot().slerp(newRot, delta * 5.0f, true));
			}
		}
		return 1;
	}

	@Override
	public int render(Shader shader, RenderingEngine renderingEngine)
	{
		this.renderingEngine = renderingEngine;
		return 1;
	}
}
