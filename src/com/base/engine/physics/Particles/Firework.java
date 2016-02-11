package com.base.engine.physics.Particles;
import java.util.Random;

public class Firework extends Particle
{
	public int type;
	public float age;
	public Firework(int t, float a)
	{
		type = t;
		age = a;
	}
	
	public boolean update(float delta)
	{
		integrate(delta);
		
		age-=delta;
		return (age < 0);
	}
}
