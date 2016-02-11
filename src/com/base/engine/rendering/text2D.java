package com.base.engine.rendering;

import com.base.engine.core.math.Vector2f;

public class text2D 
{
	public Texture font;
	
	public void init(String fontTexturePath)
	{
		font = new Texture(fontTexturePath);
	}
	
	public void print(String text, int x, int y, int size)
	{
		Vector2f[] vertices = new Vector2f[text.length() * 4];
		Vector2f[] uvs = new Vector2f[text.length() * 4];
		int[] indices = new int[text.length() * 6];
		//int[] uvindices = new int[text.length() * 6];
		
		int j = 0;
		for(int i = 0; i < text.length() * 4; i+=4)
		{
			vertices[i] = new Vector2f(x + i*size				, y + size);	//Upper Left
			vertices[i + 1] = new Vector2f(x + i*size + size	, y + size);	//Upper right
			vertices[i + 2] = new Vector2f(x + i*size + size	, y);			//Bottom Left
			vertices[i + 3] = new Vector2f(x + i*size			, y);			//Bottom Right
			
			indices[j] = i;
			indices[j + 1] = i + 2;
			indices[j + 2] = i + 1;
			
			indices[j + 3] = i + 3;
			indices[j + 4] = i + 1;
			indices[j + 5] = i + 2;
			
			j += 6;
			
			char character = text.charAt(i);
			float uvx = (character%16)/16.0f;
			float uvy = (character/16)/16.0f;
					
			uvs[i] = 		new Vector2f(uvx				,1.0f - uvy);					//Upper Left
			uvs[i + 1] = 	new Vector2f(uvx + 1.0f/16.0f	,1.0f - uvy);					//Upper Right
			uvs[i + 2] = 	new Vector2f(uvx + 1.0f/16.0f	,1.0f - (uvy + 1.0f/16.0f));	//Bottom Left
			uvs[i + 3] = 	new Vector2f(uvx				,1.0f - (uvy + 1.0f/16.0f));	//Bottom Right
			
		}
	}
	
	public void cleanup()
	{
		font = null;
	}
}
