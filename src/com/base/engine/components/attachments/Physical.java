package com.base.engine.components.attachments;

import com.base.engine.physics.RigidBody.RigidBody;

public interface Physical extends ComponentAttachment
{
	public default int integrate(float delta){return 1;};
	public default int simulate(float delta){return 1;};
	public RigidBody getBody();
}
