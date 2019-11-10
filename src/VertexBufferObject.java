import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.*;

public class VertexBufferObject {
    private final int id;

    VertexBufferObject () {
        id = glGenBuffers();
    }

    void bind (int target) {
        // Target is usually GL_ARRAY_BUFFER
        glBindBuffer(target, id);
    }

    void unbind (int target) {
        // Target is usually GL_ARRAY_BUFFER
        glBindBuffer(target, 0);
    }

    void uploadData (int target, FloatBuffer data, int usage) {
        // target should be GL_ARRAY_BUFFER
        // usage should be GL_STATIC_DRAW
        glBufferData(target, data, usage);
    }

    // Upload null data
    void uploadData (int target, long size, int usage) {
        // target should be GL_ARRAY_BUFFER
        // usage should be GL_STATIC_DRAW
        glBufferData(target, size, usage);
    }

    // Upload sub data
    void uploadSubData (int target, long offset, FloatBuffer data) {
        // target should be GL_ARRAY_BUFFER
        glBufferSubData(target, offset, data);
    }

    // Upload element data
    void uploadData (int target, IntBuffer data, int usage) {
        // target should be GL_ELEMENT_ARRAY_BUFFER
        // usage should be GL_STATIC_DRAW
        glBufferData(target, data, usage);
    }

    void delete () {
        glDeleteBuffers(id);
    }

    int getId () {
        return id;
    }
}
