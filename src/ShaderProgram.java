import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;


import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {

    private final int id;
    Shader vertexShader;
    Shader fragmentShader;

    private final Map<String, Integer> uniforms;

    ShaderProgram() {
        id = glCreateProgram();
        if (id == 0) {
            //throw new Exception("Could not create shader.");
            System.out.println("Could not create shader.");
        }

        uniforms = new HashMap<>();
    }

    void createVertexShader () {
        vertexShader = new Shader(GL_VERTEX_SHADER, "resources/vertex.shader");
        glAttachShader(id, vertexShader.getId());
    }

    void createFragmentShader () {
        fragmentShader = new Shader(GL_FRAGMENT_SHADER, "resources/fragment.shader");
        glAttachShader(id, fragmentShader.getId());
    }

    Shader getFragmentShader () {
        return fragmentShader;
    }

    Shader getVertexShader () {
        return vertexShader;
    }

    void createUniform (String uniformName) throws Exception {
        var uniformLocation = glGetUniformLocation(id, uniformName);
        if (uniformLocation < 0) {
            throw new Exception("Could not find uniform:" + uniformName);
        }
        uniforms.put(uniformName, uniformLocation);
    }

    void setUniform (String uniformName, Matrix4f value) {
        // Dump the matrix into a float buffer
        try (var stack = MemoryStack.stackPush()) {
            var fb = stack.mallocFloat(16);
            value.get(fb);
            glUniformMatrix4fv(uniforms.get(uniformName), false, fb);
        }
    }

    void bind () {
        glUseProgram(id);
    }

    void unbind () {
        glUseProgram(0);
    }

    void link () {
        glLinkProgram(id);
    }

    void destroy () {
        glDeleteProgram(id);
    }
}
