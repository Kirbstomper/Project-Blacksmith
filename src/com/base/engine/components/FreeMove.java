package com.base.engine.components;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

import com.base.engine.components.attachments.Controlable;
import com.base.engine.core.Input;
import com.base.engine.core.math.Vector3f;


public class FreeMove extends GameComponent implements Controlable
{
	private float speed;
	private int forwardKey;
	private int backKey;
	private int leftKey;
	private int rightKey;
	Vector3f cf;
	
	public FreeMove(float speed)
	{
		this(speed, GLFW_KEY_W, GLFW_KEY_S, GLFW_KEY_A, GLFW_KEY_D);
	}

	public FreeMove(float speed, int forwardKey, int backKey, int leftKey, int rightKey)
	{
		this.speed = speed;
		this.forwardKey = forwardKey;
		this.backKey = backKey;
		this.leftKey = leftKey;
		this.rightKey = rightKey;
	}

	@Override
	public int input(float delta)
	{
		float movAmt = speed * delta;

		if(Input.getKey(forwardKey))
			move(getTransform().getRot().getForward(), movAmt);
		if(Input.getKey(backKey))
			move(getTransform().getRot().getForward(), -movAmt);
		if(Input.getKey(leftKey))
			move(getTransform().getRot().getLeft(), movAmt);
		if(Input.getKey(rightKey))
			move(getTransform().getRot().getRight(), movAmt);
		return 1;
	}

	private void move(Vector3f dir, float amt)
	{	
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}
}
