package com.base.engine.physics.Particles;
import java.util.Random;

import com.base.engine.core.math.Vector3f;

public class FireworkRule
	{
		public int type;
		
		public float minAge;
		public float maxAge;
		
		public Vector3f minVelocity;
		public Vector3f maxVelocity;
		
		public float damping;
		
		public class Payload
		{
			public int type;
			public int count;
			
			public Payload(int t, int c)
			{
				type = t;
				count = c;
			}
		}
		
		public int payloadCount;
		
		public Payload[] payloads;
		
		public FireworkRule(int t, float mina, float maxa, Vector3f minv, Vector3f maxv, float damp)
		{
			type = t;
			minAge = mina;
			maxAge = maxa;
			minVelocity = minv;
			maxVelocity = maxv;
			damping = damp;
		}
		
		public void create(Firework firework, Firework parent)
		{
			Random r = new Random();
			
			firework.type = type;
			firework.age = r.nextFloat()%(maxAge - minAge) + minAge;
			
			Vector3f vel = Vector3f.randomVector(minVelocity, maxVelocity);
			
			if(parent != null)
				{
				firework.setPosition(parent.getPosition());
				vel = parent.getVelocity().add(vel);
				}
			
			firework.setVelocity(vel);
			
			firework.setMass(1);
			
			firework.setDamping(damping);
			
			//firework.setAcceleration(Force.GRAVITY.getForce());
			
			firework.clearAccumulator();
		}
	}