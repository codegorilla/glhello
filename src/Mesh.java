import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class Mesh {

    private VertexArrayObject vao;

    private VertexBufferObject positionVbo;
    private VertexBufferObject colorVbo;

    private final int vertexCount;

    Mesh (float[] vertices, float[] colors) {

        vao = new VertexArrayObject();

        vao.bind();

        // Allocate memory for positions
        var verticesBuffer = MemoryUtil.memAllocFloat(vertices.length);
        verticesBuffer.put(vertices).flip();
        vertexCount = vertices.length / 2;

        // Position VBO
        positionVbo = new VertexBufferObject();
        positionVbo.bind(GL_ARRAY_BUFFER);
        positionVbo.uploadData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);
        positionVbo.unbind(GL_ARRAY_BUFFER);
        MemoryUtil.memFree(verticesBuffer);

        // Allocate memory for colors
        var colorBuffer = MemoryUtil.memAllocFloat(colors.length);
        colorBuffer.put(colors).flip();

        // Color VBO
        colorVbo = new VertexBufferObject();
        colorVbo.bind(GL_ARRAY_BUFFER);
        colorVbo.uploadData(GL_ARRAY_BUFFER, colorBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
        colorVbo.unbind(GL_ARRAY_BUFFER);
        MemoryUtil.memFree(colorBuffer);

        // Unbind the VAO
        vao.unbind();

    }

    void bindVAO () {
        vao.bind();
    }

    void unbindVAO () {
        vao.unbind();
    }

    int getVertexCount () {
        return vertexCount;
    }

    void delete () {
        glDisableVertexAttribArray(0);

        // Delete VBOs
        positionVbo.unbind(GL_ARRAY_BUFFER);
        positionVbo.delete();
        colorVbo.unbind(GL_ARRAY_BUFFER);
        colorVbo.delete();

        // Delete VAO
        vao.unbind();
        vao.delete();
    }
}
