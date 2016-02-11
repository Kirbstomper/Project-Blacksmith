package com.base.game.LevelEditor;

import com.base.engine.core.CoreEngine;


public class LevelEditor
{
	
	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		new EditorFrame();
		
		
		CoreEngine engine = new CoreEngine(800, 600, "Test", 60, true);
	}
}