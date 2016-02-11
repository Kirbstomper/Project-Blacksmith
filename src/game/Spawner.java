package game;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;

public class Spawner 
{
	Class c;
	Object args[];
	
	public Spawner(Class c, Object args[])
	{
		this.args = args;
		if(args == null)
		{
			try {
				c.getConstructor().newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			Constructor[] constructors = c.getConstructors();
			Parameter[] parameters;
			for(int i = 0; i < constructors.length; i++)
			{
				parameters = constructors[i].getParameters();
				if(parameters != null)
				{
					if(parameters.length == args.length)
					{
						for(int j = 0; j < parameters.length; j++)
						{
							if(!args[j].getClass().getName().equals(parameters[j].getType().getName()))
							{
								break;
							}
							else
							{
								if(j == parameters.length - 1)
									try {
										constructors[i].newInstance(args);
									} catch (InstantiationException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IllegalAccessException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IllegalArgumentException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (InvocationTargetException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
							}
						}
					}
				}
			}
		}
	}
}
