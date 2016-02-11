package com.base.engine.components;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

import com.base.engine.components.attachments.Controlable;
import com.base.engine.core.Input;
import com.base.engine.core.math.Vector3f;
import com.base.engine.physics.RigidBody.RigidBody;

public class JumpMove extends GameComponent implements Controlable
{
	private Vector3f force;
	private RigidBody body;
	private int jumpKey;
	private static final float jumpEpsilon = .0000001f;
	
	public JumpMove(float speed, RigidBody body)
	{
		this(speed, body, GLFW_KEY_SPACE);
	}

	public JumpMove(float speed, RigidBody body, int jumpKey)
	{
		this.jumpKey = jumpKey;
		force = new Vector3f(0,1,0).mul(speed);
		this.body = body;
	}

	@Override
	public int input(float delta)
	{
		if(Input.getKey(jumpKey))
		{
			Vector3f velocity = new Vector3f(0,1,0).mul(body.getVelocity());
			float currentMotion = velocity.dot(velocity);
			
			float bias = (float) Math.pow(0.5f, delta);
			
			float motion = Math.abs((1-bias)*currentMotion);
			if(motion < jumpEpsilon) 
				body.addVelocity(force);
		}
		return 1;
	}
}
