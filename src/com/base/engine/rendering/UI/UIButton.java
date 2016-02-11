package com.base.engine.rendering.UI;

import com.base.engine.components.attachments.Controlable;
import com.base.engine.components.attachments.UIRenderable;
import com.base.engine.rendering.RenderingEngine;

public abstract class UIButton extends UIElement implements UIRenderable, Controlable
{
	float pressedColorEffect = 2;
	boolean isPressed = false;
	boolean hover = false;
	
	UIButton(float x, float y, String texturePath)
	{
		super(x, y, texturePath, "UIButton");
	}
	
	@Override
	public int input(float delta)
	{
		return 1;
	}
	
	public void hover()
	{
		RenderingEngine.highlight(this);
	}
	
	public abstract void press();
}
