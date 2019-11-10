import org.lwjgl.*;
import org.lwjgl.opengl.*;

import org.joml.Math;
import org.joml.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class HelloWorld {

    private Window window;

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        window = new Window("Hello", 800, 600);
        window.init();
        loop();
        window.cleanup();
    }

    private void loop () {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();


        // Set the clear color
        glClearColor(0.25f, 0.25f, 0.25f, 0.0f);

        // Build shader program
        var shaderProg = new ShaderProgram();
        shaderProg.createVertexShader();
        shaderProg.createFragmentShader();
        shaderProg.link();

        try {
            shaderProg.createUniform("projectionMatrix");
            shaderProg.createUniform("worldMatrix");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create projection matrix
        float FOV = (float) Math.toRadians(60.0f);
        float Z_NEAR = 0.1f;
        float Z_FAR  = 1000.0f;
        //Matrix4f projectionMatrix;

        float aspectRatio = (float) window.getWidth() / window.getHeight();
        //projectionMatrix = new Matrix4f().perspective(FOV, aspectRatio, Z_NEAR, Z_FAR);

        try {
            shaderProg.createUniform("projectionMatrix");
        } catch (Exception e) {
            e.printStackTrace();
        }

        var vertices = new float[] {
                 0.5f,  0.5f,
                -0.5f,  0.5f,
                -0.5f, -0.5f,
                 0.5f,  0.5f,
                -0.5f, -0.5f,
                 0.5f, -0.5f
        };

        var colors = new float[] {
                0.0f, 0.5f, 0.5f,
                1.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f,
                0.0f, 0.5f, 0.5f,
                0.0f, 1.0f, 0.0f,
                0.0f, 0.0f, 1.0f
        };

        var mesh = new Mesh(vertices, colors);
        var entity = new Entity(mesh);
        entity.setRotation(0, 0, 45);
        entity.setPosition(10, 0, 0);

        var transformation = new Transformation();

        // Loop until user closes window or presses ESCAPE key
        while (!window.shouldClose()) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            if (window.isResized()) {
                glViewport(0, 0, window.getWidth(), window.getHeight());
                window.setResized(false);
            }

            shaderProg.bind();

            // Update projection Matrix
            Matrix4f projectionMatrix = transformation.getProjectionMatrix(FOV, window.getWidth(), window.getHeight(), Z_NEAR, Z_FAR);
            shaderProg.setUniform("projectionMatrix", projectionMatrix);

            Matrix4f worldMatrix =
                    transformation.getWorldMatrix(
                            entity.getPosition(), entity.getRotation(), entity.getScale());
            shaderProg.setUniform("worldMatrix", worldMatrix);

            mesh.bindVAO();

            glEnableVertexAttribArray(0);
            glEnableVertexAttribArray(1);

            shaderProg.setUniform("projectionMatrix", projectionMatrix);

            // Draw the vertices
            glDrawArrays(GL_TRIANGLES, 0, mesh.getVertexCount());

            // Restore state
            glDisableVertexAttribArray(0);

            mesh.unbindVAO();
            shaderProg.unbind();

            window.update();
        }
    }

    public static void main(String[] args) {
        new HelloWorld().run();
    }
}