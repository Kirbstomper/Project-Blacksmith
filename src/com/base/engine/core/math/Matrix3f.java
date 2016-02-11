package com.base.engine.core.math;

public class Matrix3f 
{
	public float[][] m;
	
	public Matrix3f()
	{
		m = new float[3][3];
	}
	
	public Matrix3f initIdentity()
	{
		m[0][0] = 1;	m[0][1] = 0;	m[0][2] = 0;
		m[1][0] = 0;	m[1][1] = 1;	m[1][2] = 0;
		m[2][0] = 0;	m[2][1] = 0;	m[2][2] = 1;

		return this;
	}
	
	public Vector3f transform(Vector3f r)
	{
		return new Vector3f(m[0][0] * r.getX() + m[0][1] * r.getY() + m[0][2] * r.getZ(),
		                    m[1][0] * r.getX() + m[1][1] * r.getY() + m[1][2] * r.getZ(),
		                    m[2][0] * r.getX() + m[2][1] * r.getY() + m[2][2] * r.getZ());
	}
	
	public Matrix3f mul(Matrix3f r)
	{
		Matrix3f res = new Matrix3f();
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				res.set(i, j, m[i][0] * r.get(0, j) +
							  m[i][1] * r.get(1, j) +
							  m[i][2] * r.get(2, j));
			}
		}
		
		return res;
	}
	
	public Matrix3f mul(float r)
	{
		Matrix3f res = new Matrix3f();
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				res.set(i, j, m[i][j] * r);
			}
		}
		
		return res;
	}
	
	public void setInverse(Matrix3f other)
	{
		float n[][] = other.getM();
		float t4 = n[0][0] * n[1][1];
		float t6 = n[0][0] * n[1][2];
		float t8 = n[0][1] * n[1][0];
		float t10 = n[0][2] * n[1][0];
		float t12 = n[0][1] * n[2][0];
		float t14 = n[0][2] * n[2][0];
		
		//Determinant
		float t16 = (t4 * n[2][2] - t6 * n[2][1] - t8 * n[2][2] +
					 t10 * n[2][1] + t12 * n[1][2] - t14 * n[1][1]);
		
		if(t16 == 0.0f) return;
		
		float t17 = 1/t16;
		
		m[0][0] = (n[1][1]*n[2][2]-n[1][2]*n[2][1])*t17;
		m[0][1] = -(n[0][1]*n[2][2]-n[0][2]*n[2][1])*t17;
		m[0][2] = (n[0][1]*n[1][2]-n[0][2]*n[1][1])*t17;
		m[1][0] = -(n[1][0]*n[2][2]-n[1][2]*n[2][0])*t17;
		m[1][1] = (n[0][0]*n[2][2]-t14)*t17;
		m[1][2] = -(t6-t10)*t17;
		m[2][0] = (n[1][0]*n[2][1]-n[1][1]*n[2][0])*t17;
		m[2][1] = -(n[0][0]*n[2][1]-t12)*t17;
		m[2][2] = (t4-t8)*t17;
		
	}
	
	public Matrix3f inverse()
	{
		Matrix3f res = new Matrix3f();
		res.setInverse(this);
		return res;
	}
	
	public void invert()
	{
		setInverse(this);
	}
	
	
	public void setTranspose(Matrix3f other)
	{
		float n[][] = other.getM();
		
		m[0][0] = n[0][0];
		m[0][1] = n[1][0];
		m[0][2] = n[2][0];
		m[1][1] = n[1][1];
		m[1][2] = n[2][1];
		m[2][1] = n[1][2];
		m[2][2] = n[2][2];
	}
	
	public Matrix3f transpose()
	{
		Matrix3f res = new Matrix3f();
		res.setTranspose(this);
		return res;
	}
	
	public void setOrientation(Quaternion r)
	{
		m[0][0] = 1 - (2*r.getY()*r.getY() + 2*r.getZ()*r.getZ());
		m[0][1] = 2*r.getX()*r.getY() + 2*r.getZ()*r.getW();
		m[0][2] = 2*r.getX()*r.getZ() - 2*r.getY()*r.getW();
		m[1][0] = 2*r.getX()*r.getY() - 2*r.getZ()*r.getW();
		m[1][1] = 1 - (2*r.getX()*r.getX() + 2*r.getZ()*r.getZ());
		m[1][2] = 2*r.getY()*r.getZ() + 2*r.getX()*r.getW();
		m[2][0] = 2*r.getX()*r.getZ() + 2*r.getY()*r.getW();
		m[2][1] = 2*r.getY()*r.getZ() - 2*r.getX()*r.getW();
		m[2][2] = 1 - (2*r.getX()*r.getX() + 2*r.getY()*r.getY());
	}
	
	public float[][] getM()
	{
		float[][] res = new float[3][3];
		
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++)
				res[i][j] = m[i][j];
		
		return res;
	}
	
	public float get(int x, int y)
	{
		return m[x][y];
	}

	public void setM(float[][] m)
	{
		this.m = m;
	}
	
	public void set(int x, int y, float value)
	{
		m[x][y] = value;
	}
	
	public void setComponents(Vector3f one, Vector3f two, Vector3f three)
	{
		m[0][0] = one.x;
		m[1][0] = one.y;
		m[2][0] = one.z;
		m[0][1] = two.x;
		m[1][1] = two.y;
		m[2][1] = two.z;
		m[0][2] = three.x;
		m[1][2] = three.y;
		m[2][2] = three.z;
	}

	public void setSkewSymmetric(Vector3f v) 
	{
		m[0][0] = m[1][1] = m[2][2] = 0;
		m[0][1] = -v.z;
		m[0][2] = v.y;
		m[1][0] = v.z;
		m[1][2] = -v.x;
		m[2][0] = -v.y;
		m[2][1] = v.x;
		
	}

	public Matrix3f add(Matrix3f a) {
		Matrix3f out = new Matrix3f();
		for(int i = 0; i < 9; i++)
		{
			out.m[i%3][i/3] = m[i%3][i/3] + a.m[i%3][i/3];
		}
		return out;
	}
}
