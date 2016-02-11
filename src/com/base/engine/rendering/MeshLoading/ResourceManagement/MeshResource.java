package com.base.engine.rendering.MeshLoading.ResourceManagement;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;

import com.base.engine.core.ReferenceCounter;

public class MeshResource extends ReferenceCounter
{
	private int vbo;
	private int ibo;
	private int size;
	
	public MeshResource(int size)
	{
		super();
		vbo = glGenBuffers();
		ibo = glGenBuffers();
		this.size = size;
	}
	
	@Override
	protected void finalize()
	{
		glDeleteBuffers(vbo);
		glDeleteBuffers(ibo);
	}
	
	public int getVbo() {
		return vbo;
	}

	public int getIbo() {
		return ibo;
	}

	public int getSize() {
		return size;
	}

}
