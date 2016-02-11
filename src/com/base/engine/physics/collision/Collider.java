package com.base.engine.physics.collision;

import com.base.engine.components.GameComponent;
import com.base.engine.components.attachments.Collidable;

public abstract class Collider extends GameComponent implements Collidable
{
	Contact collision;
}
