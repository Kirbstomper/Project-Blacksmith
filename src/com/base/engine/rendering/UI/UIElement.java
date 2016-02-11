package com.base.engine.rendering.UI;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL15;

import com.base.engine.components.GameComponent;
import com.base.engine.components.attachments.UIRenderable;
import com.base.engine.core.Util;
import com.base.engine.core.math.Vector2f;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.Shader;
import com.base.engine.rendering.Texture;

public class UIElement extends GameComponent implements UIRenderable
{
	public static final int UISIZE = 4;
	
	public boolean active = true;
	
	Texture texture;
	
	Vector2f position;
	Vector2f[] vertices;
	Vector2f[] texCoords;
	int[] indices;
	Shader shader;
	Material material;
	
	
	UIElement(float x, float y, String texturePath)
	{
		this(x, y, texturePath, "UI");
	}
		
	UIElement(float x, float y, String texturePath, String shaderPath)
	{
		position = new Vector2f(x, y);
		texture = new Texture(texturePath);
		shader = new Shader(shaderPath);
		material = new Material();
	}
	
	@Override
	public boolean isRendering(){return active;}
	
	@Override
	public int render(RenderingEngine renderingEngine)
	{
		shader.bind();
		shader.updateUniforms(getTransform(), material, renderingEngine);
		texture.bind();
		
		int vbo = glGenBuffers();
		int ibo = glGenBuffers();
		
		FloatBuffer buffer = Util.createFloatBuffer(vertices.length * UISIZE);
		//TODO make cleaner
		for(int i = 0; i < vertices.length; i++)
		{
			buffer.put((float) vertices[i].getX());
			buffer.put((float) vertices[i].getY());
			buffer.put((float) texCoords[i].getX());
			buffer.put((float) texCoords[i].getY());
		}
		
		buffer.flip();
		
		
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		
		glVertexAttribPointer(0, 2, GL_FLOAT, false, UISIZE * 4, 0);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, UISIZE * 4, 8);
		
		
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL_STATIC_DRAW);
		glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		
		glDeleteBuffers(vbo);
		glDeleteBuffers(ibo);
		return 1;
	}
}
