#version 330 core
#include "lighting.glh"

in vec2 texCoord0;
in vec3 normal0;
in vec3 worldPos0;

uniform sampler2D diffuse;

uniform SpotLight R_spotLight;

out vec4 fragColor;

void main()
{
	//gl_FragColor = texture2D(diffuse, texCoord0.xy) * calcPointLight(diffuse.pointLight, normalized(normal0));
    fragColor = texture2D(diffuse, vec2(texCoord0.x, 1 - texCoord0.y)) * calcSpotLight(R_spotLight, normalize(normal0), worldPos0);
}
