package com.base.game;

import com.base.engine.components.DirectionalLight;
import com.base.engine.components.FreeLook;
import com.base.engine.components.FreeMove;
import com.base.engine.components.LookAtComponent;
import com.base.engine.components.MeshRenderer;
import com.base.engine.components.PointLight;
import com.base.engine.components.SpotLight;
import com.base.engine.core.Game;
import com.base.engine.core.GameObject;
import com.base.engine.core.World;
import com.base.engine.core.math.Quaternion;
import com.base.engine.core.math.Vector2f;
import com.base.engine.core.math.Vector3f;
import com.base.engine.physics.PremadeObjects.BoxObject;
import com.base.engine.physics.PremadeObjects.PlaneObject;
import com.base.engine.physics.PremadeObjects.SphereObject;
import com.base.engine.rendering.Attenuation;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.Texture;
import com.base.engine.rendering.Vertex;

import game.BallWaterfall;
import game.InteractionTest3;
import game.Player;

public class TestGame extends Game
{
	public void init()
	{
		world = World.world;
		float fieldDepth = 10.0f;
		float fieldWidth = 10.0f;
		
		Vertex[] vertices = new Vertex[] { 	new Vertex( new Vector3f(-fieldWidth, 0.0f, -fieldDepth), new Vector2f(0.0f, 0.0f)),
				new Vertex( new Vector3f(-fieldWidth, 0.0f, fieldDepth * 3), new Vector2f(0.0f, 1.0f)),
				new Vertex( new Vector3f(fieldWidth * 3, 0.0f, -fieldDepth), new Vector2f(1.0f, 0.0f)),
				new Vertex( new Vector3f(fieldWidth * 3, 0.0f, fieldDepth * 3), new Vector2f(1.0f, 1.0f))};

		int indices[] = { 0, 1, 2,
				2, 1, 3};

		Vertex[] vertices2 = new Vertex[] { 	new Vertex( new Vector3f(-fieldWidth/ 10, 0.0f, -fieldDepth/ 10), new Vector2f(0.0f, 0.0f)),
				new Vertex( new Vector3f(-fieldWidth/ 10, 0.0f, fieldDepth/ 10 * 3), new Vector2f(0.0f, 1.0f)),
				new Vertex( new Vector3f(fieldWidth/ 10 * 3, 0.0f, -fieldDepth/ 10), new Vector2f(1.0f, 0.0f)),
				new Vertex( new Vector3f(fieldWidth/ 10 * 3, 0.0f, fieldDepth/ 10 * 3), new Vector2f(1.0f, 1.0f))};

		int indices2[] = { 0, 1, 2,
				2, 1, 3};
		

		
		Mesh mesh2 = new Mesh(vertices2, indices2, true);

		Mesh mesh = new Mesh(vertices, indices, true);
		Material material = new Material();//new Texture("test.png"), new Vector3f(1,1,1), 1, 8);
		material.addTexture("diffuse", new Texture("test.png"));
		material.addFloat("specularIntensity", 1);
		material.addFloat("specularPower", 8);
		

		Mesh tempMesh = new Mesh("monkey3.obj");
		
		MeshRenderer meshRenderer = new MeshRenderer(mesh, material);

		GameObject planeObject = new GameObject();
		planeObject.addComponent(meshRenderer);
		planeObject.getTransform().getPos().set(0, -1, 5);

		GameObject directionalLightObject = new GameObject();
		DirectionalLight directionalLight = new DirectionalLight(new Vector3f(150f/255f,75f/255f,20f/255f), .5f);
//		RenderingEngine.dlight = directionalLight;

		directionalLightObject.addComponent(directionalLight);

		GameObject pointLightObject = new GameObject();
		pointLightObject.addComponent(new PointLight(new Vector3f(0,1,0), 0.4f, new Attenuation(0,0,1)));

		SpotLight spotLight = new SpotLight(new Vector3f(0,1,1), 0.4f,
				new Attenuation(0,0,0.1f), 0.7f);

		GameObject spotLightObject = new GameObject();
		spotLightObject.addComponent(spotLight);

		spotLightObject.getTransform().getPos().set(5, 0, 5);
		spotLightObject.getTransform().setRot(new Quaternion(new Vector3f(0,1,0), (float)Math.toRadians(90.0f)));

		//addObject(planeObject);
		addObject(directionalLightObject);
		//addObject(pointLightObject);
		//addObject(spotLightObject);

		//getRootObject().addChild(new GameObject().addComponent(new Camera((float)Math.toRadians(70.0f), (float)Window.getWidth()/(float)Window.getHeight(), 0.01f, 1000.0f)));

		GameObject testMesh1 = new GameObject().addComponent(new MeshRenderer(mesh2, material));
		GameObject testMesh2 = new GameObject().addComponent(new MeshRenderer(mesh2, material));
		GameObject testMesh3 = new GameObject().addComponent(new FreeLook(0.5f)).addComponent(new FreeMove(10)).addComponent(new LookAtComponent()).addComponent(new MeshRenderer(tempMesh, material));

		testMesh1.getTransform().getPos().set(0, 2, 0);
		testMesh1.getTransform().setRot(new Quaternion(new Vector3f(0,1,0), 0.4f));

		testMesh2.getTransform().getPos().set(0, 0, 5);

		testMesh1.addChild(testMesh2);
		//getRootObject()
		//GameObject cameraObject = new GameObject();
		//Camera camera = new Camera((float)Math.toRadians(70.0f), (float)Window.getWidth()/(float)Window.getHeight(), 0.01f, 1000.0f);
		//GameComponent freelook = new FreeLook(0.5f);
		//GameComponent freeMove = new FreeMove(10);
		
		
		//world.add(cameraObject);
		
		//RenderingEngine.mainCamera = camera;
		
		//cameraObject.addComponent(freelook).addComponent(freeMove).addComponent(camera);
		//world.focus = cameraObject;
		//world.add(cameraObject);
		//testMesh2.addChild(cameraObject);

		//addObject(testMesh2);
		
		//addObject(testMesh1);
		//addObject(testMesh3);

		testMesh3.getTransform().getPos().set(5, 5, 5);
		testMesh3.getTransform().setRot(new Quaternion(new Vector3f(0,1,0), (float)Math.toRadians(-70.0f)));
		
		//addObject(new GameObject().addComponent(new MeshRenderer(new Mesh("monkey3.obj"), material)));
		
		directionalLight.getTransform().setRot(new Quaternion(new Vector3f(1,0,0), (float)Math.toRadians(-45)));
		
		BoxObject box1 = new BoxObject(5, .2f, .0f);
		BoxObject box2 = new BoxObject(5, .5f, .0f);
		PlaneObject plane = new PlaneObject(new Vector3f(0,1,0), 0);
		SphereObject sphere = new SphereObject(20, .5f, .5f, 1);
		SphereObject sphere2 = new SphereObject(1, 1, 1, 1);
		BallWaterfall waterfall = new BallWaterfall(20, 1, 1, 1, .2f);
		
		box1.getTransform().setPos(new Vector3f(-25,25,1));
		box2.getTransform().setPos(new Vector3f(-25,18,0));
		plane.getTransform().setPos(new Vector3f(-25, 20, 0));
		sphere.getTransform().setPos(new Vector3f(-25, 20, 0));
		sphere2.getTransform().setPos(new Vector3f(-25, 0, 1));
		waterfall.getTransform().setPos(new Vector3f(-25, 20, 0));
		//
		//box1.getRigidBody().addVelocity(new Vector3f(0,0,15));
		//box2.getRigidBody().addVelocity(new Vector3f(5,15,0));
		//sphere.getRigidBody().addVelocity(new Vector3f(-20,0,0));
		//sphere2.getRigidBody().addVelocity(new Vector3f(20,0,0));
		world.add(box1);
		world.add(box2);
		world.add(plane);
		//world.add(sphere);
		//world.add(sphere2);
		//world.add(waterfall);
		
		
		Player player = new Player();
		//player.getTransform().setPos(new Vector3f(mainRoomTopCenterPos.x, Room.roomSize.y * 2.0f, mainRoomTopCenterPos.z));
		
		
		//player.getTransform().setPos(new Vector3f(mainRoomTopCenterPos.x, Room.roomSize.y * 1.5f, mainRoomTopCenterPos.z));
		world.addToBucket(player);
		
		
		InteractionTest3 it = new InteractionTest3();
		it.getTransform().setPos(new Vector3f(1.4f,0,4));
		world.add(it);
	}
}
