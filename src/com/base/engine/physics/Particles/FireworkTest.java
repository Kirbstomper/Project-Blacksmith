package com.base.engine.physics.Particles;
import java.util.Scanner;

import com.base.engine.core.math.Vector3f;

public class FireworkTest {
	public static FireworkRule[] rules;
	public static Firework[] fireworks;
	public static int nextFirework;
	public static int maxFireworks;
	
	public static void main(String [] args)
	{
		Scanner k = new Scanner(System.in);
		
		initRules();
		maxFireworks = 1;
		fireworks = new Firework[1];
		fireworks[0] = new Firework(0, 0);
		
		create(0, null);
		float delta = 1;
		while(!k.nextLine().equals("hi"))
		{
			for(Firework firework : fireworks)
			{
				if(firework.type != -1)
				{
					if(firework.update(delta))
					{
						FireworkRule rule = rules[firework.type];
						
						firework.type = -1;
						
						for(int i = 0; i < rule.payloadCount; i++)
						{
							FireworkRule.Payload payload = rule.payloads[i];
							create(payload.type, firework);
						}
					}
				}
				System.out.println(firework);
			}
		}
	}
	
	public static void create(int type, Firework parent)
	{
		FireworkRule rule = rules[type];
		rule.create(fireworks[nextFirework], parent);
		nextFirework = (nextFirework + 1) % maxFireworks;
	}
	
	public static void initRules()
	{
		rules = new FireworkRule[10];
		
		rules[0] = new FireworkRule(0, 3, 5, new Vector3f(-5,-5,-5), new Vector3f(5,5,5), 0.1f);
	}
}
