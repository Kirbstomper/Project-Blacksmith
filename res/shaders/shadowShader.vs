#version 330 core

layout(location=0) in vec3 position;
layout(location=1) in vec2 texCoord;
layout(location=2) in vec3 normal;

uniform mat4 depthMVP;

out vec2 texCoord0;

void main(){
	gl_Position = depthMVP * vec4(position, 1);
	texCoord0 = texCoord;
}