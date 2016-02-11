#version 330 core

in vec2 position;
in vec2 texCoord;

out vec2 texCoord0;

void main()
{
	vec2 screenPos = position - vec2(400,300);
	screenPos /= vec2(400,300);
	gl_Position = vec4(screenPos,0,1);
	
	texCoord0 = texCoord;
}