package com.base.engine.core.math;

public class Vector4f 
{
	public float x;
	public float y;
	public float z;
	public float w;
	//Constructor
	public Vector4f(float x, float y, float z, float w){this.x = x; this.y = y; this.z = z; this.w = w;}
	public Vector4f(Vector4f r) {x = r.x; y = r.y; z = r.z; w = r.w;}
	
	//Getters/Setters for values
	public float getX() {return x;}
	public void setX(float x) {this.x = x;}
	public float getY() {return y;}
	public void setY(float y) {this.y = y;}
	public float getZ() {return z;}
	public void setZ(float z) {this.z = z;}
	public float getW() {return w;}
	public void setW(float w) {this.w = w;}
	public Vector4f set(float x, float y, float z, float w) { this.x = x; this.y = y; this.z = z; this.w = w; return this; }
	public Vector4f set(Vector4f r) { set(r.getX(), r.getY(), r.getZ(), r.getW()); return this; }

	
	//Basic Outputs
	public String toString(){return "(" + x + " " + y + " " + z + ")";}
}