package com.base.engine.core;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.BufferUtils;

import com.base.engine.components.attachments.Controlable;
import com.base.engine.core.math.Vector2f;
import com.base.engine.rendering.Window;

public class Input
{
	static World world = World.world;
	
	static ArrayList<Controlable> inputs;
	
	final static int NUM_KEYS = 338;
	final static int NUM_MOUSE = 8;
	
	static boolean[] currentKeys = new boolean[NUM_KEYS];
	static boolean[] currentMouse = new boolean[NUM_MOUSE];
	
	static boolean[] prevKeys = new boolean[NUM_KEYS];
	
	static long MainWindow;
	
	private static HashMap<String, Integer> keyBindings;
	private static HashMap<String, Integer> mouseBindings;
	
	//private static GLFWKeyCallback keyCallback;
	
	private static DoubleBuffer mx;
	private static DoubleBuffer my;
	
	public static void init(long window)
	{
		MainWindow = window;
		
		mx = BufferUtils.createDoubleBuffer(1);
		my = BufferUtils.createDoubleBuffer(1);
		/*
		glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                    glfwSetWindowShouldClose(window, GL_TRUE); // We will detect this in our rendering loop
            }
        });
        */
		
		keyBindings = new HashMap<String, Integer>();
		mouseBindings = new HashMap<String, Integer>();
		
		setKeyBinding("Interact", GLFW_KEY_E);
		setKeyBinding("Break", GLFW_KEY_SPACE);
	}
	
	public static void gather()
	{
		inputs = world.getControlable();
		//interacts = world.getInteractables();
	}
	
	public static void input(float delta)
	{
		for(Controlable input : inputs)
		{
			input.input(delta);
		}
	}
	
	public static void update()
	{
		for(int i = 0; i < NUM_KEYS; i++)
		{
			currentKeys[i] = glfwGetKey(MainWindow, i) == 1;
		}
		for(int i = 0; i < NUM_MOUSE; i++)
		{
			currentMouse[i] = glfwGetMouseButton(MainWindow, i) == 1;
		}
	}
	
	public static boolean getKey(int key)
	{
		return currentKeys[key];
	}
	
	public static boolean getKeyPressed(int key)
	{
		return !currentKeys[key] && prevKeys[key];
	}
	
	public static boolean getMouse(int mouse)
	{
		return currentMouse[mouse];
	}
	
	
	public static Vector2f getMousePosition()
	{
		glfwGetCursorPos(MainWindow, mx, my);
		return new Vector2f((float)mx.get(0), Window.width - (float)my.get(0));
	}
	
	public static void setMousePosition(Vector2f pos)
	{
		glfwSetCursorPos(MainWindow, (double)pos.getX(), Window.width - (double)pos.getY());
	}
	
	public static void setCursor(boolean enabled)
	{
		if(enabled)
			glfwSetInputMode(MainWindow, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
		else
			glfwSetInputMode(MainWindow, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
	}
	
	public static void setKeyBinding(String action, int key)
	{
		keyBindings.put(action, key);
	}
	
	public static void setMouseBinding(String action, int key)
	{
		mouseBindings.put(action, key);
	}
	
	public static boolean getBindingPressed(String action)
	{
		int k = keyBindings.get(action);
		if(k == 0)
		{
			k = mouseBindings.get(action);
			if(k == 0) return false;
			return getMouse(k);
		}
		return getKey(k);
	}
}
