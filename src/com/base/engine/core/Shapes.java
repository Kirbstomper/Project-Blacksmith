package com.base.engine.core;

import com.base.engine.core.math.Vector2f;
import com.base.engine.core.math.Vector3f;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.Vertex;

public class Shapes 
{
	public static Mesh box(Vector3f halfSize){return box(halfSize,true);}
	public static Mesh box(Vector3f halfSize, boolean normals)
	{
		float halfX = halfSize.getX(), halfY = halfSize.getY(), halfZ = halfSize.getZ();
		
		Vertex vertices[] = new Vertex[8];
		vertices[0] = new Vertex(new Vector3f(halfX, halfY, halfZ), new Vector2f(1.0f, 0.0f)); //+++
		vertices[1] = new Vertex(new Vector3f(halfX, halfY, -halfZ), new Vector2f(1.0f, 1.0f)); //++-
		vertices[2] = new Vertex(new Vector3f(halfX, -halfY, halfZ), new Vector2f(0.75f, 0.25f)); //+-+
		vertices[3] = new Vertex(new Vector3f(halfX, -halfY, -halfZ), new Vector2f(0.75f, 0.75f)); //+--
		vertices[4] = new Vertex(new Vector3f(-halfX, halfY, halfZ), new Vector2f(0.0f, 0.0f)); //-++
		vertices[5] = new Vertex(new Vector3f(-halfX, halfY, -halfZ), new Vector2f(0.0f, 1.0f)); //-+-
		vertices[6] = new Vertex(new Vector3f(-halfX, -halfY, halfZ), new Vector2f(0.25f, 0.25f)); //--+
		vertices[7] = new Vertex(new Vector3f(-halfX, -halfY, -halfZ), new Vector2f(0.25f, 0.75f)); //---		
		
		//DO NOT CHANGE THESE UNLESS YOU KNOW WHAT YOU'RE DOING
		int indices[] = {
				7, 4, 5,
				4, 7, 6,
				
				3, 0, 2,
				0, 3, 1,
				
				4, 2, 0,
				2, 4, 6,
				
				7, 1, 3,
				1, 7, 5,
				
				5, 0, 1,
				0, 5, 4,
				
				6, 3, 2,
				3, 6, 7,
		};
		
		return new Mesh(vertices, indices, normals);
	}
	
	public static Mesh inverseBox(Vector3f halfSize){return box(halfSize,false);}
	public static Mesh inverseBox(Vector3f halfSize, boolean normals)
	{
		float halfX = halfSize.getX(), halfY = halfSize.getY(), halfZ = halfSize.getZ();
		
		Vertex vertices[] = new Vertex[8];
		vertices[0] = new Vertex(new Vector3f(halfX, halfY, halfZ), new Vector2f(1.0f, 0.0f)); //+++
		vertices[1] = new Vertex(new Vector3f(halfX, halfY, -halfZ), new Vector2f(1.0f, 1.0f)); //++-
		vertices[2] = new Vertex(new Vector3f(halfX, -halfY, halfZ), new Vector2f(0.75f, 0.25f)); //+-+
		vertices[3] = new Vertex(new Vector3f(halfX, -halfY, -halfZ), new Vector2f(0.75f, 0.75f)); //+--
		vertices[4] = new Vertex(new Vector3f(-halfX, halfY, halfZ), new Vector2f(0.0f, 0.0f)); //-++
		vertices[5] = new Vertex(new Vector3f(-halfX, halfY, -halfZ), new Vector2f(0.0f, 1.0f)); //-+-
		vertices[6] = new Vertex(new Vector3f(-halfX, -halfY, halfZ), new Vector2f(0.25f, 0.25f)); //--+
		vertices[7] = new Vertex(new Vector3f(-halfX, -halfY, -halfZ), new Vector2f(0.25f, 0.75f)); //---		
		
		//DO NOT CHANGE THESE UNLESS YOU KNOW WHAT YOU'RE DOING
		int indices[] = {
				7, 5, 4,
				4, 6, 7,
				
				3, 2, 0,
				0, 1, 3,
				
				4, 0, 2,
				2, 6, 4,
				
				7, 3, 1,
				1, 5, 7,
				
				5, 1, 0,
				0, 4, 5,
				
				6, 2, 3,
				3, 7, 6,
		};
		
		return new Mesh(vertices, indices, normals);
	}
	
	public static Mesh sphere()
	{
		return new Mesh("sphere.obj");
	}
	
//	public static Mesh plane(Vector3f direction, Vector2f halfSize){return plane(direction, halfSize, true);}
//	public static Mesh plane(Vector3f direction, Vector2f halfSize, boolean normals)
//	{
//		direction = direction.normal();
//		direction
//		
//		int indices[] = {
//				0, 1, 2,
//				2, 1, 3,
//		};
//	}
}
