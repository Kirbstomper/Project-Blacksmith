package com.base.engine.physics.collision;

public interface BoundingVolume 
{
	public default boolean overlaps(BoundingVolume other){return false;};
	public default float getSize(){return 0;};
	public default float getGrowth(BoundingVolume other){return 0;};
}
