package com.base.engine.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

import java.util.ArrayList;
import java.util.HashMap;

import com.base.engine.components.BaseLight;
import com.base.engine.components.Camera;
import com.base.engine.components.GameComponent;
import com.base.engine.components.attachments.LightAttachment;
import com.base.engine.components.attachments.Renderable;
import com.base.engine.components.attachments.UIRenderable;
import com.base.engine.core.GameObject;
import com.base.engine.core.World;
import com.base.engine.core.math.Transform;
import com.base.engine.core.math.Vector3f;
import com.base.engine.rendering.MeshLoading.ResourceManagement.MappedValues;
import com.base.engine.rendering.UI.UIButtonRect;

public class RenderingEngine extends MappedValues
{
	World world;
	
	public static Camera mainCamera;

	private ArrayList<LightAttachment> lights;
	private LightAttachment activeLight;

	public static GameObject ui;
	private Shader UIShader;
	private Shader straight;
	
	private HashMap<String, Integer> samplerMap;
	private Shader forwardAmbient;
	
	private ArrayList<Renderable> renders;
	private ArrayList<UIRenderable> uiRenders;
	
	private static ArrayList<Renderable> highlight;
	private static ArrayList<UIRenderable> uiHighlight;
	
	//private Shader shadowShader;
	//private ShadowFBO shadowMapFBO;
	
	
	public RenderingEngine()
	{
		super();
		world = World.world;
		
		samplerMap = new HashMap<String, Integer>();
		samplerMap.put("diffuse", 0);
		//samplerMap.put("shadowMap", 0);
		
		addVector3f("ambient", new Vector3f(0.1f,0.1f,0.1f));
		
		forwardAmbient = new Shader("forward-ambient");
		//shadowShader = new Shader("shadowShader");
		straight = new Shader("straight");
		
		//shadowMapFBO = new ShadowFBO();
		
		ui = new GameObject();
		world.addToBucket(ui);
		UIShader = new Shader("UI");
		
		highlight = new ArrayList<Renderable>();
		uiHighlight = new ArrayList<UIRenderable>();
		
		//addUI(new UIText(0,0,"timesNewRoman.png", "Stuff", 64));
		addUI(new UIButtonRect(100,50,"test.png",50,25));
				
		glClearColor(0.0f, 1.0f, 1.0f, 0.0f);

		glFrontFace(GL_CW);
		glCullFace(GL_BACK);
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);

		glEnable(GL_DEPTH_CLAMP);

		glEnable(GL_TEXTURE_2D);
	}

	public void updateUniformStruct(Transform transform, Material material, RenderingEngine renderingEngine, String uniformName, String uniformType)
	{
		throw new IllegalArgumentException(uniformName + " is not a supported type in Rendering Engine");
	}
	
	public void gather()
	{
		lights = world.getLightAttachments();
		renders = world.getRenderable();
		uiRenders = world.getUIRenderable();
	}
	
	public void render()
	{
		//Shadows
//		shadowMapFBO.write();
//		
//		glClear(GL_DEPTH_BUFFER_BIT);
//		DirectionalLight dlight = null;
//		for(BaseLight light : lights)
//		{
//			if(light instanceof DirectionalLight)
//			{
//				dlight = (DirectionalLight) light;
//				break;
//			}
//		}
//		if(dlight != null)
//		{
//			Vector3f lightInvDir = dlight.getDirection();
//			
//			Matrix4f depthProjectionMatrix = new Matrix4f().initOrthographic(-10, 10, -10, 10, -10, 20);
//			Matrix4f depthViewMatrix = new Transform().getLookAtRotation(lightInvDir, new Vector3f(0,1,0)).toRotationMatrix();
//			//Matrix4f depthModelMatrix = new Matrix4f().initIdentity(); //Doesn't matter
//			Matrix4f depthMVP = depthProjectionMatrix.mul(depthViewMatrix);
//			dlight.setMVP(depthMVP);
//			
//			samplerMap.put("shadowMap", shadowMapFBO.getShadowMap());
//			
//			activeLight = dlight;
//			object.renderAll(shadowShader, this);
//		}
		//Rendering
		
		Window.bindAsRenderTarget();
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		renderAll(forwardAmbient);

		
		glBlendFunc(GL_ONE, GL_ONE);
		glDepthMask(false);
		glDepthFunc(GL_EQUAL);

		for(LightAttachment light : lights)
		{
			activeLight = light;
			renderAll(light.getShader());
		}

		
		glDepthFunc(GL_LESS);
		glDepthMask(true);
		glDisable(GL_BLEND);
		
		//Outline
		
		glEnable (GL_BLEND); 
		glBlendFunc (GL_SRC_ALPHA ,GL_ONE_MINUS_SRC_ALPHA);
		glPolygonMode (GL_BACK, GL_LINE); 
		glLineWidth (4); 
		glCullFace (GL_FRONT); 
		glDepthFunc (GL_LEQUAL); 
		glColor3f(255,255,0); 
		
		renderAllHighlight(straight);
		
		glDepthFunc(GL_LESS);
		glCullFace(GL_BACK);
		glPolygonMode(GL_BACK, GL_FILL);
		glDisable(GL_BLEND);
		
		//UI RENDERING
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		glDepthMask(false);
		//glDepthFunc(GL_EQUAL);
		
		//UIShader.bind();
		
		renderAllUI();
	
		//glDepthFunc(GL_LESS);
		glDepthMask(true);
		glDisable(GL_BLEND);
		
		glEnable (GL_BLEND); 
		glBlendFunc (GL_SRC_ALPHA ,GL_ONE_MINUS_SRC_ALPHA);
		glPolygonMode (GL_BACK, GL_LINE); 
		glLineWidth (20); 
		glCullFace (GL_FRONT); 
		glDepthFunc (GL_LEQUAL); 
		glColor3f(255,255,0); 
		
		renderAllUIHighlight(forwardAmbient);
		
		glDepthFunc(GL_LESS);
		glCullFace(GL_BACK);
		glPolygonMode(GL_BACK, GL_FILL);
		glDisable(GL_BLEND);
		
		highlight = new ArrayList<Renderable>();
		uiHighlight = new ArrayList<UIRenderable>();
	}
	
	public void renderAll(Shader shader)
	{
		for(Renderable render : renders)
		{
			if(render.isRendering()) render.render(shader, this);
		}
	}
	
	public void renderAllUI()
	{
		for(UIRenderable render : uiRenders)
		{
			if(render.isRendering()) render.render(this);
		}
	}
	
	public void renderAllHighlight(Shader shader)
	{
		for(Renderable render : highlight)
		{
			if(render.isRendering()) render.render(shader, this);
		}
	}
	
	public void renderAllUIHighlight(Shader shader)
	{
		for(UIRenderable render : uiHighlight)
		{
			if(render.isRendering()) render.render(this);
		}
	}
	
	public static void highlight(Renderable render)
	{
		highlight.add(render);
	}
	
	public static void highlight(UIRenderable render)
	{
		uiHighlight.add(render);
	}

//	private static void clearScreen()
//	{
//		//TODO: Stencil Buffer
//		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
//	}
	
	public static String getOpenGLVersion()
	{
		return glGetString(GL_VERSION);
	}
	
	public static void addUI(GameObject uiObject)
	{
		ui.addChild(uiObject);
	}
	
	public static void addUI(GameComponent uiComponent)
	{
		ui.addComponent(uiComponent);
	}
	
	public void addLight(BaseLight light)
	{
		lights.add(light);
	}

	public void addCamera(Camera camera)
	{
		mainCamera = camera;
	}

	public void putSampler(String samplerName, int samplerSlot)
	{
		samplerMap.put(samplerName, samplerSlot);
	}
	
	public int getSamplerSlot(String samplerName)
	{
		return samplerMap.get(samplerName);
	}
	
	public LightAttachment getActiveLight()
	{
		return activeLight;
	}

	public Camera getMainCamera()
	{
		return mainCamera;
	}

	public static void setMainCamera(Camera mc)
	{
		mainCamera = mc;
	}
}
