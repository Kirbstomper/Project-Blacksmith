package com.base.engine.components;

import com.base.engine.components.attachments.Renderable;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;

public class ToggleRenderer extends MeshRenderer implements Renderable
{
	private boolean rendering = true;
	
	public ToggleRenderer(Mesh mesh, Material material){this(mesh, material, true);}
	public ToggleRenderer(Mesh mesh, Material material, boolean rendering) 
	{
		super(mesh, material);
		this.rendering = rendering;
	}
	
	public void setRendering(boolean is)
	{
		rendering = is;
	}
	
	public void toggle()
	{
		rendering = !rendering;
	}
	
	@Override
	public boolean isRendering()
	{
		return rendering;
	}
}
