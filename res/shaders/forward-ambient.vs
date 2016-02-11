#version 330 core

in vec3 position;
in vec2 texCoord;

out vec2 texCoord0;

uniform mat4 T_MVP;

void main()
{
    gl_Position = T_MVP * vec4(position, 1.0);
    texCoord0 = texCoord;
}
