package com.base.engine.core;

import com.base.engine.physics.PhysicsEngine;
import com.base.engine.rendering.RenderingEngine;

public abstract class Game
{
	private GameObject root;
	public World world;

	public void init() {}

	public void input(float delta)
	{
		//getRootObject().inputAll(delta);
	}

	public void update(float delta, PhysicsEngine physicsEngine)
	{
		//getRootObject().updateAll(delta, physicsEngine);
	}

	public void render(RenderingEngine renderingEngine)
	{
		//renderingEngine.render(getRootObject());
	}

	public void addObject(GameObject object)
	{
		world.add(object);
	}

	//TODO Improve Game
	@SuppressWarnings("unused")
	private GameObject getRootObject()
	{
		if(root == null)
			root = new GameObject();

		return root;
	}
	
	public void setEngine(CoreEngine engine)
	{
		//getRootObject().setEngine(engine);
	}
	//TODO: Fix Game.java
}
