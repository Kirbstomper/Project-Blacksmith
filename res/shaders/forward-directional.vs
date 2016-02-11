#version 330 core

in vec3 position;
in vec2 texCoord;
in vec3 normal;

uniform mat4 T_model;
uniform mat4 T_MVP;

out vec2 texCoord0;
out vec3 normal0;
out vec3 worldPos0;

void main()
{
    gl_Position = T_MVP * vec4(position, 1.0);
    texCoord0 = texCoord;
    normal0 = (T_model * vec4(normal, 0.0)).xyz;
    worldPos0 = (T_model * vec4(position, 1.0)).xyz;
}
