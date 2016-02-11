package game;

import com.base.engine.components.InteractableCollision;
import com.base.engine.components.MeshRenderer;
import com.base.engine.core.GameObject;
import com.base.engine.physics.collision.Sphere;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.Texture;

public class InteractionTest2 extends GameObject
{
	Sphere sphere;
	InteractableCollision ic;
	MeshRenderer renderer;
	
	public InteractionTest2()
	{
		sphere = new Sphere(1);
		sphere.setCheck(false);
		ic = new InteractableCollision(sphere);
		Material mat = new Material();
		mat.addTexture("diffuse", new Texture("test.png"));
		renderer = new MeshRenderer(new Mesh("sphere.obj"), mat);
		
		this.addComponent(sphere);
		this.addComponent(ic);
		this.addComponent(renderer);
	}
}
