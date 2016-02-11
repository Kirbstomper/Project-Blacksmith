package com.base.engine.rendering.UI;

import com.base.engine.components.attachments.UIRenderable;
import com.base.engine.core.math.Vector2f;

public class UIText extends UIElement implements UIRenderable
{
	public String text;
	public float size;
	
	public UIText(float x, float y, String fontPath, String text, float size)
	{
		super(x, y, fontPath);
		this.text = text;
		this.size = size;
		
		generate();
	}
	
	public void update()
	{
		
	}
	
	public static float distance = 10;
	
	public void generate()
	{
		vertices = new Vector2f[text.length() * 4];
		texCoords = new Vector2f[text.length() * 4];
		indices = new int[text.length() * 6];
		
		int j = 0;
		for(int i = 0; i < text.length() * 4; i+=4)
		{
			vertices[i] = new Vector2f(position.getX() + i*size/distance				, position.getY() + size);	//Upper Left
			vertices[i + 1] = new Vector2f(position.getX() + i*size/distance + size	, position.getY() + size);	//Upper right
			vertices[i + 2] = new Vector2f(position.getX() + i*size/distance			, position.getY());			//Bottom Left
			vertices[i + 3] = new Vector2f(position.getX() + i*size/distance + size	, position.getY());			//Bottom Right
			
			indices[j] = i;
			indices[j + 1] = i + 1;
			indices[j + 2] = i + 2;
			
			indices[j + 3] = i + 3;
			indices[j + 4] = i + 2;
			indices[j + 5] = i + 1;
			
//			indices[j + 0] = i + 0;
//			indices[j + 1] = i + 1;
//			indices[j + 2] = i + 2;
//			
//			indices[j + 3] = i + 3;
//			indices[j + 4] = i + 2;
//			indices[j + 5] = i + 1;
			
			j += 6;
			
			char character = text.charAt(i/4);
			float uvx = (character%16)/16.0f;
			float uvy = (character/16)/16.0f;
					
			texCoords[i + 0] = 		new Vector2f(uvx				,1.0f - uvy);					//Upper Left
			texCoords[i + 1] = 	new Vector2f(uvx + 1.0f/16.0f	,1.0f - uvy);					//Upper Right
			texCoords[i + 2] = 	new Vector2f(uvx				,1.0f - (uvy + 1.0f/16.0f));	//Bottom Left
			texCoords[i + 3] = 	new Vector2f(uvx + 1.0f/16.0f	,1.0f - (uvy + 1.0f/16.0f));	//Bottom Right
		}
	}
}
