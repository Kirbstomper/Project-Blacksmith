package game;

import com.base.engine.components.GameComponent;
import com.base.engine.components.attachments.Interactable;

public class InteractionTest extends GameComponent implements Interactable
{

	@Override
	public void interact() {
		System.out.println("Interact");
		
	}

}
