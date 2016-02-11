package game;

import com.base.engine.components.InteractableCollision;
import com.base.engine.components.MeshRenderer;
import com.base.engine.core.GameObject;
import com.base.engine.core.Shapes;
import com.base.engine.core.math.Vector3f;
import com.base.engine.physics.collision.Box;
import com.base.engine.physics.collision.Sphere;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.Texture;

public class InteractionTest3 extends GameObject
{
	Box box;
	InteractableCollision ic;
	MeshRenderer renderer;
	Vector3f halfSize;
	
	public InteractionTest3()
	{
		halfSize = new Vector3f(1,1,1);
		box = new Box(halfSize);
		box.setCheck(false);
		ic = new InteractableCollision(box);
		Material mat = new Material();
		mat.addTexture("diffuse", new Texture("test.png"));
		renderer = new MeshRenderer(Shapes.box(halfSize), mat);
		
		this.addComponent(box);
		this.addComponent(ic);
		this.addComponent(renderer);
	}
}
