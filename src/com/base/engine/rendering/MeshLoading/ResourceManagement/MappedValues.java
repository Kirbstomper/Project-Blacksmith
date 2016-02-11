package com.base.engine.rendering.MeshLoading.ResourceManagement;

import java.util.HashMap;

import com.base.engine.core.math.Vector3f;

public abstract class MappedValues
{
	HashMap<String, Vector3f> vector3fHashMap;
	HashMap<String, Float> floatHashMap;
	
	public MappedValues()
	{
		vector3fHashMap = new HashMap<String, Vector3f>();
		floatHashMap = new HashMap<String, Float>();
	}
	
	public void addVector3f(String name, Vector3f vector3f) { vector3fHashMap.put(name, vector3f); }
	public void addFloat(String name, float floatValue) { floatHashMap.put(name, floatValue); }
	public void addInt(String name, int intValue) { floatHashMap.put(name, (float) intValue); }
	public void addBoolean(String name, boolean boolValue) { floatHashMap.put(name, (float) (boolValue?1:0)); }
	
	public Vector3f getVector3f(String name)
	{
		Vector3f result = vector3fHashMap.get(name);
		if(result != null)
			return result;

		return new Vector3f(0,0,0);
	}

	public float getFloat(String name)
	{
		Float result = floatHashMap.get(name);
		if(result != null)
			return result;

		return 0;
	}
	
	public int getInt(String name)
	{
		Float result = floatHashMap.get(name);
		if(result != null)
			return (int)(float)result;

		return 0;
	}
	
	public int getBoolean(String name)
	{
		Float result = floatHashMap.get(name);
		if(result != null)
			return (((int)(float)result) == 1)?1:0;

		return 0;
	}
}
