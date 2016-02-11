package game;

import com.base.engine.components.Camera;
import com.base.engine.components.FreeLook;
import com.base.engine.components.FreeMove;
import com.base.engine.components.JumpMove;
import com.base.engine.components.LockedYMove;
import com.base.engine.components.MoveComponent;
import com.base.engine.core.GameObject;
import com.base.engine.core.World;
import com.base.engine.core.math.Vector3f;
import com.base.engine.physics.PhysicsEngine;
import com.base.engine.physics.RigidBody.RigidBody;
import com.base.engine.physics.collision.Box;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.Window;

public class Player extends GameObject
{
	boolean alive = true;
	Camera camera;
	RigidBody body;
	Box collider;
	MoveComponent finalmove;
	
	public Player()
	{
		camera = new Camera((float)Math.toRadians(70.0f), (float)Window.getWidth()/(float)Window.getHeight(), 0.01f, 1000.0f);
		body = new RigidBody(5, 1, 0);
		collider = new Box(new Vector3f(1,2,1));
		//FreeMove move = new FreeMove(25);
		LockedYMove move = new LockedYMove(20);
		finalmove = new MoveComponent(1, 5);
		FreeLook look = new FreeLook(0.5f);
		//StandardLook look = new StandardLook(0.5f);
		//InteractionTest test = new InteractionTest();
		JumpMove jump = new JumpMove(10, body);
		
		body.attach(this);
		collider.attach(body);
		
		
		RenderingEngine.mainCamera = camera;
		World.world.focus = this;
		
		this.addComponent(camera);
		this.addComponent(body);
		this.addComponent(collider);
		this.addComponent(move);
		this.addComponent(look);
		//this.addComponent(test);
		this.addComponent(jump);
		
		PhysicsEngine.addForce(body, "Gravity");
		
		collider.calculateInternals();
	}
	
	public boolean checkPulse()
	{
		return alive;
	}
	
	public int update(float delta)
	{
		
		return 1;
	}
	
	public Camera getCamera()
	{
		return camera;
	}
	
	public void win()
	{
		this.addComponent(finalmove);
	}
}
