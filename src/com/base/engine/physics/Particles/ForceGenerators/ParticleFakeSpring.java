package com.base.engine.physics.Particles.ForceGenerators;

import com.base.engine.core.math.Vector3f;
import com.base.engine.physics.Particles.Particle;

public class ParticleFakeSpring implements ParticleForceGenerator
{
	Vector3f anchor;
	float k; //the spring constant
	float damping;
	
	
	public ParticleFakeSpring(Vector3f anchor, float k, float damping)
	{
		this.anchor = anchor;
		this.k = k;
		this.damping = damping;
	}
	
	@Override
	public void updateForce(Particle particle, float delta)
	{
		if(!particle.hasFiniteMass()) return;
		
		Vector3f position = particle.getPosition().sub(anchor);
		
		float gamma = (float) (.5 * Math.sqrt(4 * k - damping*damping));
		if(gamma == 0.0f) return;
		Vector3f c = position.mul(damping/(2.0f * gamma)).add(particle.getVelocity().mul(1.0f / gamma));
		
		Vector3f target = position.mul((float)Math.cos(gamma * delta)).add(c.mul((float)Math.sin(gamma*delta)));
		
		target = target.mul((float)Math.exp(-.5f * delta * damping));
		
		Vector3f accel = (target.sub(position)).mul(1.0f/(delta*delta)).sub(particle.getVelocity().mul(delta));
		particle.addForce(accel.mul(particle.getInverseMass()));//Might need to remove particle.getMass()
	}
	
}
