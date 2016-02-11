package com.base.engine.rendering;


import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.base.engine.core.Util;
import com.base.engine.rendering.MeshLoading.ResourceManagement.TextureResource;



public class Texture
{
	private static HashMap<String, TextureResource> loadedTextures = new HashMap<String, TextureResource>();
	private TextureResource resource;
	private String fileName;
	
	public Texture(int width, int height)
	{
		this.fileName = null;
		resource = new TextureResource(width, height);
		resource.renderPrep();
	}
	
	public Texture(String fileName)
	{
		this.fileName = fileName;
		TextureResource oldResource = loadedTextures.get(fileName);
		
		if(oldResource != null)
		{
			resource = oldResource;
			resource.addReference();
		}
		else
		{
			resource = loadTexture(fileName);
			loadedTextures.put(fileName, resource);
		}
	}
	
	@Override
	protected void finalize()
	{
		if(resource.removeReference() && !fileName.isEmpty())
			loadedTextures.remove(fileName);
	}
	
	public void bind()
	{
		bind(0);
	}
	
	public void bind(int samplerSlot)
	{
		assert(samplerSlot >= 0 && samplerSlot <= 31);
		glActiveTexture(GL_TEXTURE0 + samplerSlot);
		glBindTexture(GL_TEXTURE_2D, resource.getId());
	}
	
	public int getID()
	{
		return resource.getId();
	}
	
	
	public static TextureResource loadTexture(String fileName)
	{
		try
		{
		
			BufferedImage image = ImageIO.read(new File("./res/textures/" + fileName));
			int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
			
	
	        ByteBuffer buffer = Util.createByteBuffer(image.getWidth() * image.getHeight() * 4);
	        boolean hasAlpha = image.getColorModel().hasAlpha();
	
	        for(int y = 0; y < image.getHeight(); y++)
	        {
	            for(int x = 0; x < image.getWidth(); x++)
	            {
	                int pixel = pixels[y * image.getWidth() + x];
	                
	                buffer.put((byte) ((pixel >> 16) & 0xFF));
	                buffer.put((byte) ((pixel >> 8) & 0xFF));
	                buffer.put((byte) ((pixel) & 0xFF));
	                if(hasAlpha)
	                	buffer.put((byte) ((pixel >> 24) & 0xFF));
	                else
	                	buffer.put((byte)(0xFF));
	            }
	        }
	
	        buffer.flip();
	
	
	        TextureResource textureResource = new TextureResource(image.getWidth(), image.getHeight()); //Generate texture ID
	        
	        glBindTexture(GL_TEXTURE_2D, textureResource.getId()); //Bind texture ID
	
	        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
	        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
	
	        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
	        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	
	        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
	
	         
	        return textureResource;
		}
        catch(Exception e)
        {
        	e.printStackTrace();
        	System.exit(1);
        }
		
		return null;
	}
	
	public void renderToTexture()
	{
		glBindFramebuffer(GL_FRAMEBUFFER, resource.getFramebuffer());
		glViewport(0,0,resource.getWidth(),resource.getHeight());
	}
}