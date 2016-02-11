package com.base.engine.physics.collision;

import com.base.engine.components.attachments.Collidable;
import com.base.engine.core.math.Vector3f;

public class AABB extends Primitive implements Collidable
{
	public Vector3f halfSize;
}
