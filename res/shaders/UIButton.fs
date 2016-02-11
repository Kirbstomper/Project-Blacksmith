#version 330 core

in vec2 texCoord0;

uniform sampler2D diffuse;
uniform float pressedColorEffect;
uniform bool isPressed;

out vec4 fragColor;

void main()
{
	if(isPressed)
	{
		fragColor = texture2D(diffuse, vec2(texCoord0.x, 1 - texCoord0.y))/pressedColorEffect;
	}
	else
	{
		fragColor = texture2D(diffuse, vec2(texCoord0.x, 1 - texCoord0.y));
	}
}