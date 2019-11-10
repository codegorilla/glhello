import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import static org.lwjgl.opengl.GL20.*;

public class Shader {

    private int id;

    Shader (int type, String path) {
        id = glCreateShader(type);
        if (id == 0)
            System.out.println("Error: Cannot create shader.");

        // The source is the source code of the shader in string
        // format
        var source = Utils.loadShader(path);
        glShaderSource(id, source);
        glCompileShader(id);
        // need to check status of the shader using glGetShaderi
        if (glGetShaderi(id, GL_COMPILE_STATUS) == 0) {
            System.out.println("Error: Could not compile shader code.");
            throw new RuntimeException(glGetShaderInfoLog(id));
        }
    }

    int getId () {
        return id;
    }

    void bind () {

    }

    void delete () {
        glDeleteShader(id);
    }
}
