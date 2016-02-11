package com.base.engine.rendering;

import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL30.GL_DRAW_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

import com.base.engine.core.math.Vector2f;

public class Window
{
	public static long mainWindow;
	public static float width;
	public static float height;
	
//	public static long WindowList[] = new long[10];
//	public static float WidthList[] = new float[10];
//	public static float HeightList[] = new float[10];
	
	public static long createMainWindow(int width, int height, String title)
	{
		mainWindow = glfwCreateWindow(width, height, title, NULL, NULL);
		generateSize(mainWindow);
		return mainWindow;
	}
	
//	public static long createSubWindow(int width, int height, String title, long MainWindow)
//	{
//		WindowList[WindowList.length] = glfwCreateWindow(width, height, title, NULL, MainWindow);
//		return WindowList.length-1;
//	}
//	
//	public static long createSubWindow(int width, int height, String title, int MainWindow)
//	{
//		WindowList[WindowList.length] = glfwCreateWindow(width, height, title, NULL, WindowList[MainWindow]);
//		return WindowList.length-1;
//	}
	
	public static void bindAsRenderTarget()
	{
		glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
		glViewport(0, 0, (int)getWidth(), (int)getHeight());
	}
	
	public static void bindRenderTarget(int render)
	{
		glBindFramebuffer(GL_DRAW_FRAMEBUFFER, render);
		glViewport(0, 0, (int)getWidth(), (int)getHeight());
	}
	
	public static boolean isCloseRequested()
	{
		return glfwWindowShouldClose(mainWindow) == GL_TRUE;
	}
	
	public static float getWidth()
	{
		return width;
	}
	
	public static float getHeight()
	{
		return height;
	}
	
	public static void render(int Window)
	{
		render(mainWindow);
	}
	
	public static void render(long Window)
	{
		
		glfwSwapBuffers(Window);
	}
	
//	public static void renderAll()
//	{
//		
//		for(int i = 0; i < WindowList.length; i++)
//		{
//			render(WindowList[i]);
//		}
//	}
	
	public static void clearScreen()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	
	public static void dispose(long Window)
	{
		glfwDestroyWindow(Window);
	}
//	
//	public static void dispose(int Window)
//	{
//		glfwDestroyWindow(WindowList[Window]);
//	}
	
	
//	
//	public static long getWindow(int address)
//	{
//		return WindowList[address];
//	}
	
	private static void generateSize(long window)
	{
		ByteBuffer w = BufferUtils.createByteBuffer(4);
		ByteBuffer h = BufferUtils.createByteBuffer(4);
		glfwGetWindowSize(window, w, h);
		width = (float)w.getInt(0);
		height = (float)h.getInt(0);
	}
	
	public Vector2f getCenter()
	{
		return new Vector2f(width/2, height/2);
	}
	
	/*
	public static Vector2f getScreenResolution()
	{
		ByteBuffer w = BufferUtils.createByteBuffer(4);
		ByteBuffer h = BufferUtils.createByteBuffer(4);
		glfwGetMonitorSize((int)glfwGetMonitors(0), w, h);
		width = (int)w.getInt(0);
		height = (int)h.getInt(0);
		
		return new Vector2f(width,height);
	}
	*/
}
