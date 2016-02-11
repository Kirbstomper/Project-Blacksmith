package game;

import java.util.Random;

import com.base.engine.components.Decay;
import com.base.engine.components.IntervalTrigger;
import com.base.engine.components.attachments.Updatable;
import com.base.engine.core.GameObject;
import com.base.engine.core.World;
import com.base.engine.physics.PremadeObjects.SphereObject;

public class BallWaterfall extends GameObject
{
	Random r;
	BallSpawn ballSpawn;
	
	public BallWaterfall(float mass, float ldamping, float adamping, float radius, float interval)
	{
		r = new Random();
		ballSpawn = new BallSpawn(mass, ldamping, adamping, radius, interval);
		this.addComponent(ballSpawn);
	}
	
	private class BallSpawn extends IntervalTrigger implements Updatable
	{
		float mass, ldamping, adamping, radius;
		public BallSpawn(float mass, float ldamping, float adamping, float radius, float interval) 
		{
			super(interval);
			this.mass = mass;
			this.ldamping = ldamping;
			this.adamping = adamping;
			this.radius = radius;
		}

		@Override
		protected void trigger() 
		{
			float l = r.nextFloat()%1 + .001f;
			System.out.println(l);
			SphereObject ball = new SphereObject(mass, l, adamping, radius);
			ball.addComponent(new Decay(1));
			ball.getTransform().setPos(parent.getPosition());
			World.world.add(ball);
		}
		
	}
}
