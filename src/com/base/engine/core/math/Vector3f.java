package com.base.engine.core.math;
import java.util.Random;

public class Vector3f 
{
	public float x;
	public float y;
	public float z;
	//Constructor
	public Vector3f(float x, float y, float z){this.x = x; this.y = y; this.z = z;}
	public Vector3f(Vector3f r) {x = r.x; y = r.y; z = r.z;}

	//Basic Vector Functions
	public Vector3f normal(){float length = length();return new Vector3f(x / length, y / length, z / length);}
	public void normalize(){float length = length();set(x / length, y / length, z / length);}
	public float distance(Vector3f r){return (float) Math.sqrt(Math.pow(x - r.x, 2) + Math.pow(y - r.y, 2) + Math.pow(z - r.z, 2));}
	public float length(){return (float)Math.sqrt(x * x + y * y + z * z);} //Also known as the magnitude
	public float magnitude(){return (float)Math.sqrt(x * x + y * y + z * z);} //Also known as the length
	public float max(){return Math.max(x, Math.max(y, z));}
	public Vector3f max(Vector3f r){return new Vector3f(x>r.x ? x : r.x, y>r.y ? y : r.y, z>r.z ? z : r.z);}
	public float min(){return Math.min(x, Math.min(y, z));}
	public Vector3f min(Vector3f r){return new Vector3f(x<r.x ? x : r.x, y<r.y ? y : r.y, z<r.z ? z : r.z);}
	public float dot(Vector3f r){return x * r.x + y * r.y + z * r.z;}
	public Vector3f cross(Vector3f r){return new Vector3f(y * r.z - z * r.y, z * r.x - x * r.z, x * r.y - y * r.x);}
	public Vector3f projection(Vector3f onto){return onto.mul(onto.dot(this)/onto.length());}

	//Basic Arithmetic Operations (Addition, Subtraction, Multiplication, Division)
	public Vector3f add(Vector3f r){return new Vector3f(x + r.getX(), y + r.getY(), z + r.getZ());}
	public Vector3f add(float r){return new Vector3f(x + r, y + r, z + r);}
	public Vector3f addX(float r){return new Vector3f(x + r, y, z);}
	public Vector3f addY(float r){return new Vector3f(x, y + r, z);}
	public Vector3f addZ(float r){return new Vector3f(x, y, z + r);}
	public Vector3f sub(Vector3f r){return new Vector3f(x - r.getX(), y - r.getY(), z - r.getZ());}
	public Vector3f sub(float r){return new Vector3f(x - r, y - r, z - r);}
	public Vector3f subX(float r){return new Vector3f(x - r, y, z);}
	public Vector3f subY(float r){return new Vector3f(x, y - r, z);}
	public Vector3f subZ(float r){return new Vector3f(x, y, z - r);}
	public Vector3f mul(Vector3f r){return new Vector3f(x * r.getX(), y * r.getY(), z * r.getZ());}
	public Vector3f mul(float r){return new Vector3f(x * r, y * r, z * r);}
	public Vector3f div(Vector3f r){return new Vector3f(x / r.getX(), y / r.getY(), z / r.getZ());}
	public Vector3f div(float r){return new Vector3f(x / r, y / r, z / r);}
	
	//Basic Algebra Operations
	public Vector3f abs(){return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));}
	public Vector3f floor(){return new Vector3f((float) Math.floor(x), (float) Math.floor(y), (float) Math.floor(z));}
	public Vector3f ceil(){return new Vector3f((float) Math.ceil(x), (float) Math.ceil(y), (float) Math.ceil(z));}
	
	//Rotations
	public Vector3f rotate(Vector3f axis, float angle)
	{
		float sinAngle = (float)Math.sin(-angle);
		float cosAngle = (float)Math.cos(-angle);
		return this.cross(axis.mul(sinAngle)).add(           //Rotation on local X
				(this.mul(cosAngle)).add(                     //Rotation on local Z
						axis.mul(this.dot(axis.mul(1 - cosAngle))))); //Rotation on local Y
	}

	public Vector3f rotate(Quaternion rotation)
	{
		Quaternion conjugate = rotation.conjugate();
		Quaternion w = rotation.mul(this).mul(conjugate);
		return new Vector3f(w.getX(), w.getY(), w.getZ());
	}
	
	//Basic Game Operations
	public Vector3f lerp(Vector3f dest, float lerpFactor){return dest.sub(this).mul(lerpFactor).add(this);}
	
	//Conversion to Vector2f Methods
	public Vector2f getXY() { return new Vector2f(x, y); }
	public Vector2f getYZ() { return new Vector2f(y, z); }
	public Vector2f getZX() { return new Vector2f(z, x); }
	public Vector2f getYX() { return new Vector2f(y, x); }
	public Vector2f getZY() { return new Vector2f(z, y); }
	public Vector2f getXZ() { return new Vector2f(x, z); }

	//Getters/Setters for values
	public float getX() {return x;}
	public void setX(float x) {this.x = x;}
	public float getY() {return y;}
	public void setY(float y) {this.y = y;}
	public float getZ() {return z;}
	public void setZ(float z) {this.z = z;}
	public Vector3f set(float x, float y, float z) { this.x = x; this.y = y; this.z = z; return this; }
	public Vector3f set(Vector3f r) { set(r.getX(), r.getY(), r.getZ()); return this; }
	
	public void set(int i, float value)
	{
		switch(i)
		{
		case 0:
			this.x = value;
			break;
		case 1:
			this.y = value;
			break;
		case 2:
			this.z = value;
			break;
		}
	}
	
	public float get(int i) {
		switch(i)
		{
		case 0:
			return this.x;
		case 1:
			return this.y;
		case 2:
			return this.z;
		}
		return 0;
	}
	
	//Randomizer
	public static Vector3f randomVector(Vector3f minv, Vector3f maxv)
	{
		Random r = new Random();
		return new Vector3f(r.nextFloat()%(maxv.x - minv.x) + minv.x,
							r.nextFloat()%(maxv.y - minv.y) + minv.y,
							r.nextFloat()%(maxv.z - minv.z) + minv.z);
	}
	
	//Compare Methods
	public boolean equals(Vector3f r){if(r == null) return false; return x == r.getX() && y == r.getY() && z == r.getZ();}
	public boolean equalsOr(Vector3f r){return x == r.getX() || y == r.getY() || z == r.getZ(); }
	public int compareTo(Vector3f r)
	{
		if(x < r.x && y < r.y && z < r.z)return -1;
		else if(x > r.x && y > r.y && z > r.z)return 1;
		return 0; //CAREFUL, this does not mean they are equal!!!!!
	}
	//Basic Outputs
	public String toString(){return "(" + x + " " + y + " " + z + ")";}
}