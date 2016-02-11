package com.base.engine.physics.RigidBody.ForceGenerators;

import com.base.engine.core.math.Matrix3f;
import com.base.engine.core.math.Vector3f;

public class AeroControl extends Aero
{
	Matrix3f maxTensor;
	Matrix3f minTensor;
	float controlSetting;
	
	public AeroControl(Matrix3f base, Matrix3f min, Matrix3f max, Vector3f position, Vector3f windspeed) 
	{
		super(null/*getTensor()*/, position, windspeed);
	}
	
	public void setControl(float x)
	{
		controlSetting = x;
	}
	
	private Matrix3f getTensor()
	{
		return null;
	}
}
