package com.base.engine.components;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

import com.base.engine.components.attachments.Controlable;
import com.base.engine.core.Input;
import com.base.engine.core.math.Quaternion;
import com.base.engine.core.math.Vector2f;
import com.base.engine.core.math.Vector3f;
import com.base.engine.rendering.Window;

public class StandardLook extends GameComponent implements Controlable
{
	private static final Vector3f yAxis = new Vector3f(0,1,0);

	private boolean mouseLocked = false;
	private float sensitivity;
	private int unlockMouseKey;

	public StandardLook(float sensitivity)
	{
		this(sensitivity, GLFW_KEY_ESCAPE);
	}

	public StandardLook(float sensitivity, int unlockMouseKey)
	{
		this.sensitivity = sensitivity;
		this.unlockMouseKey = unlockMouseKey;
	}

	@Override
	public int input(float delta)
	{
		Vector2f centerPosition = new Vector2f(Window.getWidth()/2, Window.getHeight()/2);

		if(Input.getKey(unlockMouseKey))
		{
			Input.setCursor(true);
			mouseLocked = false;
		}
		if(Input.getMouse(0))
		{
			Input.setMousePosition(centerPosition);
			Input.setCursor(false);
			mouseLocked = true;
		}

		if(mouseLocked)
		{
			Vector2f deltaPos = Input.getMousePosition().sub(centerPosition);

			boolean rotY = deltaPos.getX() != 0;
			boolean rotX = deltaPos.getY() != 0;

			if(rotY)
				getTransform().rotate(yAxis, (float) Math.toRadians(deltaPos.getX() * sensitivity));
			if(rotX)
			{
				getTransform().rotate(getTransform().getRot().getRight(), (float) Math.toRadians(deltaPos.getY() * sensitivity));
				if(getTransform().getRot().x > 2)
				{
					Quaternion q = getTransform().getRot();
					q.setX(2);
					getTransform().setRot(q);
				}
			}

			if(rotY || rotX)
				Input.setMousePosition(centerPosition);
		}
		
		return 1;
	}

}
