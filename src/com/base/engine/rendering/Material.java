package com.base.engine.rendering;

import java.util.HashMap;

import com.base.engine.rendering.MeshLoading.ResourceManagement.MappedValues;

public class Material extends MappedValues
{
	private HashMap<String, Texture> textureHashMap;

	/**
	 * Creates a material object. Note: A single material can be used by multiple MeshRenderers
	 */
	public Material()
	{
		super();
		textureHashMap = new HashMap<String, Texture>();
	}

	/**
	 * Sets the texture of the Material
	 * @param name Gives the texture a name in the HashMap (ie. doesn't matter)
	 * @param texture The texture to be set
	 */
	public void addTexture(String name, Texture texture) { textureHashMap.put(name, texture); }

	public Texture getTexture(String name)
	{
		Texture result = textureHashMap.get(name);
		if(result != null)
			return result;

		return new Texture("test.png");
	}
}
