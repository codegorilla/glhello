import static org.lwjgl.opengl.GL30.*;

public class VertexArrayObject {
    private final int id;

    VertexArrayObject () {
        id = glGenVertexArrays();
    }

    void bind () {
        glBindVertexArray(id);
    }

    void unbind () {
        glBindVertexArray(0);
    }

    void delete () {
        glDeleteVertexArrays(id);
    }

    int getId () {
        return id;
    }
}
