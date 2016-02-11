package com.base.engine.physics.PremadeObjects;

import com.base.engine.components.MeshRenderer;
import com.base.engine.core.GameObject;
import com.base.engine.physics.RigidBody.RigidBody;
import com.base.engine.physics.collision.Primitive;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.Texture;

public abstract class PremadeObject extends GameObject
{
	private Mesh mesh;
	private Material mat;
	private MeshRenderer renderer;
	private RigidBody body;
	private Primitive primitive;
	
	public PremadeObject()
	{
		
	}
	public PremadeObject(Mesh mesh)
	{
		if(mesh != null)
		{
			this.mesh = mesh;
			mat = new Material();
			mat.addTexture("diffuse", new Texture("test.png"));
			renderer = new MeshRenderer(mesh, mat);
			this.addComponent(renderer);
		}
	}
	
	public RigidBody getRigidBody()
	{
		return body;
	}
	
	public void setRigidBody(RigidBody body)
	{
		this.body = body;
		body.attach(this);
		this.addComponent(body);
	}
	
	public Primitive getPrimitive()
	{
		return primitive;
	}
	
	public void setPrimitive(Primitive p)
	{
		primitive = p;
		this.addComponent(primitive);
		if(body != null)
		{
			primitive.attach(this.body);
			primitive.calculateInternals();
		}
	}
	
	public void set(RigidBody body, Primitive p)
	{
		this.body = body;
		body.attach(this);
		this.addComponent(body);
		primitive = p;
		if(body != null)
		{
			primitive.attach(this.body);
			this.addComponent(primitive);
			primitive.calculateInternals();
		}
	}
	
	public void setMesh(Mesh mesh)
	{
		this.mesh = mesh;
		if(mat == null)
		{
			mat = new Material();
			mat.addTexture("diffuse", new Texture("test.png"));
		}
		if(renderer == null)
		{
			renderer = new MeshRenderer(mesh, mat);
			this.addComponent(renderer);
		}
		else
		{
			renderer.set(mesh, mat);
		}
	}
	
	public void setTexture(Texture texture)
	{
		mat = new Material();
		mat.addTexture("diffuse", texture);
		renderer.set(mesh, mat);
	}
}
