package game;

import com.base.engine.components.GameComponent;
import com.base.engine.components.MeshRenderer;
import com.base.engine.components.SpotLight;
import com.base.engine.components.attachments.Updatable;
import com.base.engine.core.GameObject;
import com.base.engine.core.math.Vector3f;
import com.base.engine.rendering.Attenuation;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.Texture;
import com.base.engine.rendering.UI.UIText;

public class FlashLight extends GameObject// implements Updatable
{
	private int life = 100;
	private int counter = 0;
	private int nsec = 0;
	private FlashLightComponent fc;
	SpotLight light;
	GameObject hand;

	UIText power = new UIText(0,0,"timesNewRoman.png", life + "%", 50);
	
	
	
	public FlashLight()
	{
		RenderingEngine.addUI(power);
		fc = new FlashLightComponent();
		Material mat = new Material();
		mat.addTexture("diffuse", new Texture("FlashLight001.png"));
		MeshRenderer renderer = new MeshRenderer(new Mesh("flashlight001.obj"), mat);
		
		this.addComponent(fc);
		this.addComponent(renderer);
		
		this.getTransform().setPos(new Vector3f(.7f,-.5f,.5f));
		this.getTransform().rotate(new Vector3f(1,0,0), (float)Math.toRadians(90f));
		this.getTransform().setScale(.05f);
		
		hand = new GameObject();
		light = new SpotLight(new Vector3f(1,1,1), 20f, new Attenuation(0,0,1), .001f);
		hand.addComponent(light);
		//
		hand.getTransform().rotate(new Vector3f(1,0,0), (float)Math.toRadians(-90f));
		light.getDirection().set(new Vector3f(0,0,1));
		hand.getTransform().setPos(new Vector3f(1,10,0f));
		//hand.getTransform().setPos(pos)
		
		this.addChild(hand);
	}

	
	
	// 1,000,000,000 ns = 1 sec
	
	public boolean works()
	{
		if (life > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void newBattery()
	{
		//if new battery is put into the flashlight
		life = 100;
	}
	
	public void draw()
	{	
		power.text = life + "%";
		power.generate();
	}
	
	private class FlashLightComponent extends GameComponent implements Updatable
	{
		public int update(float delta)
		{
			nsec++; 
			
			//when one second is hit counter adds one
			if (nsec >= 20)
			{
				counter++;
				nsec = 0;
			}
			
			//after 10 seconds battery life dies. counter resets
			if (counter >= 10)
			{
				life--;
				counter = 0;
				draw();
				works();
			}
			
			hand.getTransform().setRot(this.getTransform().getRot());
			hand.getTransform().rotate(new Vector3f(1,0,0), (float)Math.toRadians(-180f));
			//light.getDirection().set(hand.getTransform().getRot().getForward().normal());
			return 1;
		}
	}
}
