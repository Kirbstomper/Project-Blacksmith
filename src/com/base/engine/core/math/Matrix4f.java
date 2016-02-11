package com.base.engine.core.math;

public class Matrix4f
{
	public float[][] m;
	
	public Matrix4f(){m = new float[4][4];}

	public Matrix4f initIdentity()
	{
		m[0][0] = 1;	m[0][1] = 0;	m[0][2] = 0;	m[0][3] = 0;
		m[1][0] = 0;	m[1][1] = 1;	m[1][2] = 0;	m[1][3] = 0;
		m[2][0] = 0;	m[2][1] = 0;	m[2][2] = 1;	m[2][3] = 0;
		m[3][0] = 0;	m[3][1] = 0;	m[3][2] = 0;	m[3][3] = 1;

		return this;
	}
	
	public Matrix4f biasMatrix()
	{
		m[0][0] = 0.5f;	m[0][1] = 0;	m[0][2] = 0;	m[0][3] = 0;
		m[1][0] = 0;	m[1][1] = 0.5f;	m[1][2] = 0;	m[1][3] = 0;
		m[2][0] = 0;	m[2][1] = 0;	m[2][2] = 0.5f;	m[2][3] = 0;
		m[3][0] = 0.5f;	m[3][1] = 0.5f;	m[3][2] = 0.5f;	m[3][3] = 1;

		return this;
	}
	
	public Matrix4f initTranslation(float x, float y, float z)
	{
		m[0][0] = 1;	m[0][1] = 0;	m[0][2] = 0;	m[0][3] = x;
		m[1][0] = 0;	m[1][1] = 1;	m[1][2] = 0;	m[1][3] = y;
		m[2][0] = 0;	m[2][1] = 0;	m[2][2] = 1;	m[2][3] = z;
		m[3][0] = 0;	m[3][1] = 0;	m[3][2] = 0;	m[3][3] = 1;
		
		return this;
	}
	
	public Matrix4f initRotation(float x, float y, float z)
	{
		Matrix4f rx = new Matrix4f();
		Matrix4f ry = new Matrix4f();
		Matrix4f rz = new Matrix4f();
		
		x = (float)Math.toRadians(x);
		y = (float)Math.toRadians(y);
		z = (float)Math.toRadians(z);
		
		rz.m[0][0] = (float)Math.cos(z);rz.m[0][1] = -(float)Math.sin(z);rz.m[0][2] = 0;				rz.m[0][3] = 0;
		rz.m[1][0] = (float)Math.sin(z);rz.m[1][1] = (float)Math.cos(z);rz.m[1][2] = 0;					rz.m[1][3] = 0;
		rz.m[2][0] = 0;					rz.m[2][1] = 0;					rz.m[2][2] = 1;					rz.m[2][3] = 0;
		rz.m[3][0] = 0;					rz.m[3][1] = 0;					rz.m[3][2] = 0;					rz.m[3][3] = 1;
		
		rx.m[0][0] = 1;					rx.m[0][1] = 0;					rx.m[0][2] = 0;					rx.m[0][3] = 0;
		rx.m[1][0] = 0;					rx.m[1][1] = (float)Math.cos(x);rx.m[1][2] = -(float)Math.sin(x);rx.m[1][3] = 0;
		rx.m[2][0] = 0;					rx.m[2][1] = (float)Math.sin(x);rx.m[2][2] = (float)Math.cos(x);rx.m[2][3] = 0;
		rx.m[3][0] = 0;					rx.m[3][1] = 0;					rx.m[3][2] = 0;					rx.m[3][3] = 1;
		
		ry.m[0][0] = (float)Math.cos(y);ry.m[0][1] = 0;					ry.m[0][2] = -(float)Math.sin(y);ry.m[0][3] = 0;
		ry.m[1][0] = 0;					ry.m[1][1] = 1;					ry.m[1][2] = 0;					ry.m[1][3] = 0;
		ry.m[2][0] = (float)Math.sin(y);ry.m[2][1] = 0;					ry.m[2][2] = (float)Math.cos(y);ry.m[2][3] = 0;
		ry.m[3][0] = 0;					ry.m[3][1] = 0;					ry.m[3][2] = 0;					ry.m[3][3] = 1;
		
		m = rz.mul(ry.mul(rx)).getM();
		
		return this;
	}
	
	public Matrix4f initScale(float x, float y, float z)
	{
		m[0][0] = x;	m[0][1] = 0;	m[0][2] = 0;	m[0][3] = 0;
		m[1][0] = 0;	m[1][1] = y;	m[1][2] = 0;	m[1][3] = 0;
		m[2][0] = 0;	m[2][1] = 0;	m[2][2] = z;	m[2][3] = 0;
		m[3][0] = 0;	m[3][1] = 0;	m[3][2] = 0;	m[3][3] = 1;
		
		return this;
	}
	
	public Matrix4f initPerspective(float fov, float aspectRatio, float zNear, float zFar)
	{
		float tanHalfFOV = (float)Math.tan(fov / 2);
		float zRange = zNear - zFar;
		
		m[0][0] = 1.0f / (tanHalfFOV * aspectRatio);	m[0][1] = 0;					m[0][2] = 0;	m[0][3] = 0;
		m[1][0] = 0;						m[1][1] = 1.0f / tanHalfFOV;	m[1][2] = 0;	m[1][3] = 0;
		m[2][0] = 0;						m[2][1] = 0;					m[2][2] = (-zNear -zFar)/zRange;	m[2][3] = 2 * zFar * zNear / zRange;
		m[3][0] = 0;						m[3][1] = 0;					m[3][2] = 1;	m[3][3] = 0;
		
		
		return this;
	}

	public Matrix4f initOrthographic(float left, float right, float bottom, float top, float near, float far)
	{
		float width = right - left;
		float height = top - bottom;
		float depth = far - near;

		m[0][0] = 2/width;m[0][1] = 0;	m[0][2] = 0;	m[0][3] = -(right + left)/width;
		m[1][0] = 0;	m[1][1] = 2/height;m[1][2] = 0;	m[1][3] = -(top + bottom)/height;
		m[2][0] = 0;	m[2][1] = 0;	m[2][2] = -2/depth;m[2][3] = -(far + near)/depth;
		m[3][0] = 0;	m[3][1] = 0;	m[3][2] = 0;	m[3][3] = 1;

		return this;
	}

	public Matrix4f initRotation(Vector3f forward, Vector3f up)
	{
		Vector3f f = forward.normal();
		
		Vector3f r = up.normal();
		r = r.cross(f);
		
		Vector3f u = f.cross(r);

		return initRotation(f, u, r);
	}

	public Matrix4f initRotation(Vector3f forward, Vector3f up, Vector3f right)
	{
		Vector3f f = forward;
		Vector3f r = right;
		Vector3f u = up;

		m[0][0] = r.getX();	m[0][1] = r.getY();	m[0][2] = r.getZ();	m[0][3] = 0;
		m[1][0] = u.getX();	m[1][1] = u.getY();	m[1][2] = u.getZ();	m[1][3] = 0;
		m[2][0] = f.getX();	m[2][1] = f.getY();	m[2][2] = f.getZ();	m[2][3] = 0;
		m[3][0] = 0;		m[3][1] = 0;		m[3][2] = 0;		m[3][3] = 1;

		return this;
	}

	public Vector3f transform(Vector3f r)
	{
		return new Vector3f(m[0][0] * r.getX() + m[0][1] * r.getY() + m[0][2] * r.getZ() + m[0][3],
		                    m[1][0] * r.getX() + m[1][1] * r.getY() + m[1][2] * r.getZ() + m[1][3],
		                    m[2][0] * r.getX() + m[2][1] * r.getY() + m[2][2] * r.getZ() + m[2][3]);
	}
	
	public Matrix4f mul(Matrix4f r)
	{
		Matrix4f res = new Matrix4f();
		
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				res.set(i, j, m[i][0] * r.get(0, j) +
							  m[i][1] * r.get(1, j) +
							  m[i][2] * r.get(2, j) +
							  m[i][3] * r.get(3, j));
			}
		}
		
		return res;
	}
	
	public Matrix4f mul(float r)
	{
		Matrix4f res = new Matrix4f();
		
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				res.set(i, j, m[i][j] * r);
			}
		}
		
		return res;
	}
	
	public void setInverse(Matrix4f other)
	{
		float det = getDeterminant();
		
		if(det == 0) return;
		det = 1/det;
		
		float n[][] = other.getM();
		
		m[0][0] = (-n[2][1]*n[1][2]+n[1][1]*n[2][2])*det;
		m[1][0] = (n[2][0]*n[1][2]-n[1][0]*n[2][2])*det;
		m[2][0] = (-n[2][0]*n[1][1]+n[1][0]*n[2][1]* n[3][3])*det;
		m[0][1] = (n[2][1]*n[0][2]-n[0][1]*n[2][2])*det;
		m[1][1] = (-n[2][0]*n[0][2]+n[0][0]*n[2][2])*det;
		m[2][1] = (n[2][0]*n[0][1]-n[0][0]*n[2][1]* n[3][3])*det;
		m[0][2] = (-n[1][1]*n[0][2]+n[0][1]*n[1][2]* n[3][3])*det;
		m[1][2] = (+n[1][0]*n[0][2]-n[0][0]*n[1][2]* n[3][3])*det;
		m[2][2] = (-n[1][0]*n[0][1]+n[0][0]*n[1][1]* n[3][3])*det;
		m[0][3] = (n[2][1]*n[1][2]*n[0][3]
					-n[1][1]*n[2][2]*n[0][3]
					-n[2][1]*n[0][2]*n[1][3]
					+n[0][1]*n[2][2]*n[1][3]
					+n[1][1]*n[0][2]*n[2][3]
					-n[0][1]*n[1][2]*n[2][3])*det;
		m[1][3] = (-n[2][0]*n[1][2]*n[0][3]
					+n[1][0]*n[2][2]*n[0][3]
					+n[2][0]*n[0][2]*n[1][3]
					-n[0][0]*n[2][2]*n[1][3]
					-n[1][0]*n[0][2]*n[2][3]
					+n[0][0]*n[1][2]*n[2][3])*det;
		m[2][3] =(n[2][0]*n[1][1]*n[0][3]
					-n[1][0]*n[2][1]*n[0][3]
					-n[2][0]*n[0][1]*n[1][3]
					+n[0][0]*n[2][1]*n[1][3]
					+n[1][0]*n[0][1]*n[2][3]
					-n[0][0]*n[1][1]*n[2][3])*det;
	}
	
	public Matrix4f inverse(){Matrix4f res = new Matrix4f();res.setInverse(this);return res;}
	public void invert(){setInverse(this);}
	
	public float getDeterminant()
	{
		return m[2][0]*m[1][1]*m[0][2]+
				m[1][0]*m[2][1]*m[0][2]+
				m[2][0]*m[0][1]*m[1][2]-
				m[0][0]*m[2][1]*m[1][2]-
				m[1][0]*m[0][1]*m[2][2]+
				m[0][0]*m[1][1]*m[2][2];
	}
	
	public void setOrientationAndPos(Quaternion q, Vector3f pos)
	{
		m[0][0] = 1 - (2*q.getY()*q.getY() + 2*q.getZ()*q.getZ());
		m[0][1] = 2*q.getX()*q.getY() + 2*q.getZ()*q.getW();
		m[0][2] = 2*q.getX()*q.getZ() - 2*q.getY()*q.getW();
		m[0][3] = pos.x;
		
		m[1][0] = 2*q.getX()*q.getY() - 2*q.getZ()*q.getW();
		m[1][1] = 1 - (2*q.getX()*q.getX() + 2*q.getZ()*q.getZ());
		m[1][2] = 2*q.getY()*q.getZ() + 2*q.getX()*q.getW();
		m[1][3] = pos.y;
		
		m[2][0] = 2*q.getX()*q.getZ() + 2*q.getY()*q.getW();
		m[2][1] = 2*q.getY()*q.getZ() - 2*q.getX()*q.getW();
		m[2][2] = 1 - (2*q.getX()*q.getX() + 2*q.getY()*q.getY());
		m[2][3] = pos.z;
	}
	
	public Vector3f transformInverse(Vector3f r)
	{
		Vector3f out = new Vector3f(r);
		out.x -= m[0][3];
		out.y -= m[1][3];
		out.z -= m[2][3];
		
		return new Vector3f(
				out.x * m[0][0] +
				out.y * m[1][0] +
				out.z * m[2][0],
				
				out.x * m[0][1] +
				out.y * m[1][1] +
				out.z * m[2][1],
				
				out.x * m[0][2] +
				out.y * m[1][2] +
				out.z * m[2][2]);
	}
	
	public Vector3f transformDirection(Vector3f r)
	{
		return new Vector3f(
				r.x * m[0][0] +
				r.y * m[0][1] +
				r.z * m[0][2],
				
				r.x * m[1][0] +
				r.y * m[1][1] +
				r.z * m[1][2],
				
				r.x * m[2][0] +
				r.y * m[2][1] +
				r.z * m[2][2]
				);
	}
	
	public Vector3f transformInverseDirection(Vector3f r)
	{
		return new Vector3f(
				r.x * m[0][0] +
				r.y * m[1][0] +
				r.z * m[2][0],
				
				r.x * m[0][1] +
				r.y * m[1][1] +
				r.z * m[2][1],
				
				r.x * m[0][2] +
				r.y * m[1][2] +
				r.z * m[2][2]
				);
	}
	
	public Vector3f localToWorld(Vector3f local, Matrix4f transform)
	{
		return transform.transform(local);
	}
	
	public Vector3f worldToLocal(Vector3f world, Matrix4f transform)
	{
		return transform.transformInverse(world);
	}
	
	public Vector3f localToWorldDir(Vector3f local, Matrix4f transform)
	{
		return transform.transformDirection(local);
	}
	
	public Vector3f worldToLocalDir(Vector3f world, Matrix4f transform)
	{
		return transform.transformInverseDirection(world);
	}
	
	public float[][] getM()
	{
		float[][] res = new float[4][4];
		
		for(int i = 0; i < 4; i++)
			for(int j = 0; j < 4; j++)
				res[i][j] = m[i][j];
		
		return res;
	}
	
	public float get(int x, int y){return m[x][y];}
	public void setM(float[][] m){this.m = m;}
	public void set(int x, int y, float value){m[x][y] = value;}

	public Vector3f getAxisVector(int index) {
		return new Vector3f(m[0][index], m[1][index], m[2][index]);
	}
}
