#version 330

//layout (location=0)
in vec2 position;
//layout (location=1)
in vec3 inColor;

out vec3 exColor;

uniform mat4 worldMatrix;
uniform mat4 projectionMatrix;

void main()
{
    gl_Position = projectionMatrix * worldMatrix * vec4(position, -20.0, 1.0);
    exColor = inColor;
}
