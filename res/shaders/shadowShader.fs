#version 330 core

in vec2 texCoord0;

uniform sampler2D shadowMap;

out vec4 fragColor;

void main()
{
	float depth = texture(shadowMap, texCoord0).x;
	depth = 1.0 - (1.0 - depth) * 25;
	fragColor = vec4(depth);
}