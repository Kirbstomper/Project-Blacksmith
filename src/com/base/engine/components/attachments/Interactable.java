package com.base.engine.components.attachments;

import com.base.engine.core.Interaction;
import com.base.engine.physics.collision.Collider;

public interface Interactable extends ComponentAttachment
{
	/**
	 * What happens when the object is interacted with
	 */
	public void interact();
	/**
	 * Returns the objects collider for ray tracing if it exists
	 * @return The Collider
	 */
	public default Collider getCollider(){return null;}
	/**
	 * What happens if the object is able to be interacted with
	 */
	public default void interactable(){Interaction.standard();}
}
