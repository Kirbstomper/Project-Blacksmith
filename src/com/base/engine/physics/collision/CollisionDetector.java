package com.base.engine.physics.collision;

import com.base.engine.core.math.Vector2f;
import com.base.engine.core.math.Vector3f;

public class CollisionDetector 
{
	public int checkCollision(Primitive one, Primitive two, CollisionData data)
	{
		if(!one.check() || !two.check()) return 1;
		
		String s1 = one.getClass().getSimpleName();
		String s2 = two.getClass().getSimpleName();
		
		if(s1.equals("Sphere") && s2.equals("Sphere"))
		{
			return sphereAndSphere((Sphere)one, (Sphere)two, data);
		}
		if(s1.equals("Sphere") && s2.equals("Plane"))
		{
			return sphereAndHalfSpace((Sphere)one, (Plane)two, data);
		}
		if(s1.equals("Plane") && s2.equals("Sphere"))
		{
			return sphereAndHalfSpace((Sphere)two, (Plane)one, data);
		}
		if(s1.equals("Box") && s2.equals("Box"))
		{
			return boxAndBox((Box)one, (Box)two, data);
		}
			
		if(s1.equals("Box") && s2.equals("Plane"))
		{
			return boxAndHalfSpace((Box)one, (Plane)two, data);
		}
		if(s1.equals("Plane") && s2.equals("Box"))
		{
			return boxAndHalfSpace((Box)two, (Plane)one, data);
		}
		return 0;
	}
	
	int sphereAndSphere(Sphere one, Sphere two, CollisionData data)
	{
		if(data.contactsLeft <= 0) return 0;
		
		Vector3f pos1 = one.getAxis(3);
		Vector3f pos2 = two.getAxis(3);
		
		Vector3f midline = pos1.sub(pos2);
		float size = midline.magnitude();
		
		if(size <= 0.0f | size >= one.radius + two.radius)
		{
			return 0;
		}
		
		Vector3f normal = midline.mul(1.0f/size);
		
		Contact contact = data.nextContact();
		if(contact == null) return 0;
		contact.contactNormal = normal;
		contact.contactPoint = pos1.add(midline.mul(.5f));
		contact.penetration = one.radius + two.radius - size;
		
		contact.setBodyData(one.body, two.body, data.friction, data.restitution);
		
		return 1;
	}
	
	//USE THIS ONE WHEN ONE-SIDED COLLISION IS NEEDED
	int sphereAndHalfSpace(Sphere sphere, Plane plane, CollisionData data)
	{
		if(data.contactsLeft <= 0) return 0;
		
		Vector3f pos = sphere.getAxis(3);
		
		float ballDistance = plane.getDirection().dot(pos) - sphere.radius - plane.getOffset();
		
		if(ballDistance >= 0) return 0;
		
		Contact contact = data.nextContact();
		contact.contactNormal = plane.getDirection();
		contact.penetration = -ballDistance;
		contact.contactPoint = pos.sub(plane.getDirection().mul(ballDistance + sphere.radius));
		
		contact.setBodyData(sphere.body, null, data.friction, data.restitution);
		
		return 1;
	}
	
	//USE THIS ONE WHEN TWO-SIDED COLLISION IS NEEDED
	int sphereAndTruePlane(Sphere sphere, Plane plane, CollisionData data)
	{
		if(data.contactsLeft <= 0) return 0;
		
		Vector3f pos = sphere.getAxis(3);
		
		float centerDistance = plane.getDirection().dot(pos) - plane.getOffset();
		
		if(centerDistance*centerDistance > sphere.radius*sphere.radius) return 0;
		
		Vector3f normal = plane.getDirection();
		float penetration = -centerDistance;
		if(centerDistance < 0)
		{
			normal = normal.mul(-1);
			penetration = -penetration;
		}
		
		penetration += sphere.radius;
		
		Contact contact = data.nextContact();
		contact.contactNormal = normal;
		contact.penetration = penetration;
		contact.contactPoint = pos.sub(plane.getDirection().mul(centerDistance));
		
		contact.setBodyData(sphere.body, null, data.friction, data.restitution);
		
		return 1;
	}
	
	
//	int boxAndSphere(Box box, Sphere sphere, CollisionData data)
//	{
//		Vector3f center = sphere.getAxis(3);
//		Vector3f relCenter = box.transform.trasformInverse(center);
//	}
	
	public void fillPointFaceBoxBox(Box one, Box two, Vector3f toCentre, CollisionData data, int best, float pen)
	{
		Contact contact = data.nextContact();
		
		Vector3f normal = one.getAxis(best);
		if(one.getAxis(best).dot(toCentre) > 0)
		{
			normal = normal.mul(-1);
		}
		
		Vector3f vertex = two.getHalfSize();
		if(two.getAxis(0).dot(normal) < 0) vertex.x = -vertex.x;
		if(two.getAxis(1).dot(normal) < 0) vertex.y = -vertex.y;
		if(two.getAxis(2).dot(normal) < 0) vertex.z = -vertex.z;
		
		contact.contactNormal = normal;
		contact.penetration = pen;
		contact.contactPoint = two.getTransform().getTransformation().transform(vertex);
		contact.setBodyData(one.body, two.body, data.friction, data.restitution);
	}
	
	public int boxAndBox(Box one, Box two, CollisionData data)
	{
		Vector3f toCentre = two.getAxis(3).sub(one.getAxis(3));
		
		float pen = Float.MAX_VALUE;
		int best = 0xffffff;
		
		Vector2f penstuff = new Vector2f(pen,best);
		
		if (!IntersectionTests.tryAxis(one, two, (one.getAxis(0)), toCentre, (0), penstuff)) return 0;
		if (!IntersectionTests.tryAxis(one, two, (one.getAxis(1)), toCentre, (1), penstuff)) return 0;
		if (!IntersectionTests.tryAxis(one, two, (one.getAxis(2)), toCentre, (2), penstuff)) return 0;
		
		if (!IntersectionTests.tryAxis(one, two, (two.getAxis(0)), toCentre, (3), penstuff)) return 0;
		if (!IntersectionTests.tryAxis(one, two, (two.getAxis(1)), toCentre, (4), penstuff)) return 0;
		if (!IntersectionTests.tryAxis(one, two, (two.getAxis(2)), toCentre, (5), penstuff)) return 0;
		
		int bestSingleAxis = (int) penstuff.y;
		
		if (!IntersectionTests.tryAxis(one, two, (one.getAxis(0).cross(two.getAxis(0))), toCentre, (6), penstuff)) return 0;
		if (!IntersectionTests.tryAxis(one, two, (one.getAxis(0).cross(two.getAxis(1))), toCentre, (7), penstuff)) return 0;
		if (!IntersectionTests.tryAxis(one, two, (one.getAxis(0).cross(two.getAxis(2))), toCentre, (8), penstuff)) return 0;
		if (!IntersectionTests.tryAxis(one, two, (one.getAxis(1).cross(two.getAxis(0))), toCentre, (9), penstuff)) return 0;
		if (!IntersectionTests.tryAxis(one, two, (one.getAxis(1).cross(two.getAxis(1))), toCentre, (10), penstuff)) return 0;
		if (!IntersectionTests.tryAxis(one, two, (one.getAxis(1).cross(two.getAxis(2))), toCentre, (11), penstuff)) return 0;
		if (!IntersectionTests.tryAxis(one, two, (one.getAxis(2).cross(two.getAxis(0))), toCentre, (12), penstuff)) return 0;
		if (!IntersectionTests.tryAxis(one, two, (one.getAxis(2).cross(two.getAxis(1))), toCentre, (13), penstuff)) return 0;
		if (!IntersectionTests.tryAxis(one, two, (one.getAxis(2).cross(two.getAxis(2))), toCentre, (14), penstuff)) return 0;
		
		best = (int) penstuff.y;
		
		if(best == 0xffffff) return 0;
		
		if(best < 3)
		{
			fillPointFaceBoxBox(one, two, toCentre, data, (int)penstuff.y, penstuff.x);
			return 1;
		}
		else if(best < 6)
		{
			fillPointFaceBoxBox(two, one, toCentre.mul(-1f), data, (int)penstuff.y-3, penstuff.x);
			return 1;
		}
		else
		{
			best -= 6;
			int oneAxisIndex = best / 3;
			int twoAxisIndex = best % 3;
			Vector3f oneAxis = one.getAxis(oneAxisIndex);
			Vector3f twoAxis = two.getAxis(twoAxisIndex);
			Vector3f axis = oneAxis.cross(twoAxis);
			axis.normalize();
			
			if(axis.dot(toCentre) > 0) axis = axis.mul(-1f);
			
			Vector3f ptOnOneEdge = one.getHalfSize();
			Vector3f ptOnTwoEdge = two.getHalfSize();
			for(int i = 0; i < 3; i++)
			{
				if(i == oneAxisIndex) ptOnOneEdge.set(i, 0);
				else if(one.getAxis(i).dot(axis) > 0) ptOnOneEdge.set(i, -ptOnOneEdge.get(i));
				
				if(i == twoAxisIndex) ptOnTwoEdge.set(i, 0);
				else if(two.getAxis(i).dot(axis) < 0) ptOnTwoEdge.set(i, -ptOnTwoEdge.get(i));
			}
			
			ptOnOneEdge = one.getTransform().getTransformation().transform(ptOnOneEdge);
			ptOnTwoEdge = two.getTransform().getTransformation().transform(ptOnTwoEdge);
			
			Vector3f vertex = IntersectionTests.contactPoint(
					ptOnOneEdge, oneAxis, one.getHalfSize().get(oneAxisIndex),
					ptOnTwoEdge, twoAxis, two.getHalfSize().get(twoAxisIndex),
					bestSingleAxis > 2
							);
			
			Contact contact = data.nextContact();
			
			contact.penetration = pen;
			contact.contactNormal = axis;
			contact.contactPoint = vertex;
			contact.setBodyData(one.body, two.body, data.friction, data.restitution);
			return 1;
		}
	}
	
	public int boxAndPoint(Box box, Vector3f point, CollisionData data)
	{
		Vector3f relPt = box.getTransform().getTransformation().transformInverse(point);
		
		Vector3f normal = new Vector3f(0,0,0);
		
		float mindepth = box.getHalfSize().x - Math.abs(relPt.x);
		if(mindepth < 0) return 0;
		normal = box.getAxis(0).mul((relPt.x < 0)?-1:1);
		
		float depth = box.getHalfSize().y - Math.abs(relPt.y);
		if(depth < 0) return 0;
		else if(depth < mindepth)
		{
			mindepth = depth;
			normal = box.getAxis(1).mul((relPt.y < 0)?-1:1);
		}
		
		depth = box.getHalfSize().z - Math.abs(relPt.y);
		if(depth < 0) return 0;
		else if(depth < mindepth)
		{
			mindepth = depth;
			normal = box.getAxis(2).mul((relPt.z < 0)?-1:1);
		}
		
		Contact contact = data.nextContact();
		contact.contactNormal = normal;
		contact.contactPoint = point;
		contact.penetration = mindepth;
		
		contact.setBodyData(box.body, null, data.friction, data.restitution);
		
		return 1;
	}
	
	public int boxAndSphere(Box box, Sphere sphere, CollisionData data)
	{
		Vector3f centre = sphere.getAxis(3);
		Vector3f relCentre = box.getTransform().getTransformation().transformInverse(centre);
		
		if(
				Math.abs(relCentre.x) - sphere.radius > box.getHalfSize().x ||
				Math.abs(relCentre.y) - sphere.radius > box.getHalfSize().y ||
				Math.abs(relCentre.z) - sphere.radius > box.getHalfSize().z
				) return 0;
		
		Vector3f closestPt = new Vector3f(0,0,0);
		float dist;
		
		dist = relCentre.x;
		if(dist > box.getHalfSize().x) dist = box.getHalfSize().x;
		if(dist < -box.getHalfSize().x) dist = -box.getHalfSize().x;
		closestPt.x = dist;
		
		dist = relCentre.y;
		if(dist > box.getHalfSize().y) dist = box.getHalfSize().y;
		if(dist < -box.getHalfSize().y) dist = -box.getHalfSize().y;
		closestPt.y = dist;
		
		dist = relCentre.z;
		if(dist > box.getHalfSize().z) dist = box.getHalfSize().z;
		if(dist < -box.getHalfSize().z) dist = -box.getHalfSize().z;
		closestPt.z = dist;
		
		Vector3f s = closestPt.sub(relCentre);
		dist = (s.magnitude()*s.magnitude());
		if(dist > sphere.radius * sphere.radius) return 0;
		
		Vector3f closestPtWorld = box.getTransform().getTransformation().transform(closestPt);
		
		Contact contact = data.nextContact();
		contact.contactNormal = (closestPtWorld.sub(centre));
		contact.contactNormal.normalize();
		contact.contactPoint = closestPtWorld;
		contact.penetration = (float) (sphere.radius - Math.sqrt(dist));
		contact.setBodyData(box.body, sphere.body, data.friction, data.restitution);
		
		return 1;
	}
	
	public int boxAndHalfSpace(Box box, Plane plane, CollisionData data)
	{
		if(data.contactsLeft <= 0) return 0;
		
		if(!IntersectionTests.boxAndHalfSpace(box, plane))
		{
			return 0;
		}
		
		float mults[][] = {{1,1,1},{-1,1,1},{1,-1,1},{-1,-1,1},{1,1,-1},{-1,1,-1},{1,-1,-1},{-1,-1,-1}};
		
		Contact contact;
		int contactsUsed = 0;
		for(int i = 0; i < 8; i++)
		{
			Vector3f vertexPos = new Vector3f(mults[i][0], mults[i][1], mults[i][2]);
			
			vertexPos = vertexPos.mul(box.getHalfSize());
			vertexPos = box.getTransform().getTransformation().transform(vertexPos);
			
			float vertexDistance = vertexPos.dot(plane.getDirection());
			if(vertexDistance <= plane.getOffset())
			{
				contact = data.nextContact();
				contact.contactPoint = plane.getDirection().mul((vertexDistance - plane.getOffset()));
				contact.contactPoint = contact.contactPoint.add(vertexPos);
				contact.contactNormal = plane.getDirection();
				contact.penetration = plane.getOffset() - vertexDistance;
				
				contact.setBodyData(box.body, null, data.friction, data.restitution);
				contactsUsed++;
				if(contactsUsed == data.contactsLeft) return contactsUsed;
			}
			
		}
			
		return contactsUsed;
	}
	
//	public int AABBAndHalfSpace(AABB aabb, Plane plane, CollisionData data)
//	{
//		//System.out.println("Collide");
//		int axis = -1;
//		float center = 0;
//		float edge = 0;
//		float planepos = plane.getOffset();
//		Vector3f move = new Vector3f(0,0,0);
//		
//		if(plane.getDirection().x != 0)
//		{
//			axis = 0;
//			center = aabb.body.getPosition().x;
//			edge = aabb.getHalfSize().x;
//			move.x = 1;
//			//edge = center + aabb.getHalfSize().x * plane.getDirection().x * -1;
//			//planepos *= plane.getDirection().x;
//		}
//		else if(plane.getDirection().y != 0)
//		{
//			axis = 1;
//			center = aabb.body.getPosition().y;
//			edge = aabb.getHalfSize().y;
//			move.y = 1;
//			//planepos *= plane.getDirection().y;
//		}
//		else if(plane.getDirection().z != 0)
//		{
//			axis = 2;
//			center = aabb.body.getPosition().z;
//			edge = aabb.getHalfSize().z;
//			move.z = 1;
//			//edge = center + aabb.getHalfSize().z * plane.getDirection().z * -1;
//			//planepos *= plane.getDirection().z;
//		}
//		
//		if(axis == -1) return 0;
//		
//		float penetration = 0;
//		boolean right = false;
//		
//		if(center < planepos) right = true;
//		
//		if(right)
//		{
//			edge = center + edge;
//			penetration = edge - planepos;
//			if(penetration < 0) return 0;
//			move.mul(penetration);
//			aabb.body.getTransform().setPos(aabb.body.getPosition().sub(move));
//		}
//		else
//		{
//			edge = center - edge;
//			penetration = planepos - edge;
//			if(penetration < 0) return 0;
//			move.mul(penetration);
//			aabb.body.getTransform().setPos(aabb.body.getPosition().add(move));
//		}
//		
//		aabb.body.setVelocity(new Vector3f(0,0,0));
//		
//		return 1;
//	}
}
