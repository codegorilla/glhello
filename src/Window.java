import org.lwjgl.glfw.*;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private String title;

    private int width;
    private int height;

    private long handle;

    private boolean resized;

    private GLFWErrorCallback errorCallback;

    Window (String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.resized = false;
    }

    void init () {
        // Setup an error callback
        errorCallback = GLFWErrorCallback.createPrint(System.err);
        glfwSetErrorCallback(errorCallback);

        // Initialize GLFW
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

        handle = glfwCreateWindow(width, height, title, NULL, NULL);
        if (handle == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup resize callback
        glfwSetFramebufferSizeCallback(handle, (window, width, height) -> {
            this.width = width;
            this.height = height;
            this.setResized(true);
        });

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(handle, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(handle, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    handle,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(handle);

        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(handle);

        // Hide cursor
        //glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
    }

    int getWidth () {
        return width;
    }

    int getHeight () {
        return height;
    }

    boolean shouldClose () {
        return glfwWindowShouldClose(handle);
    }

    public void setResized (boolean resized) {
        this.resized = resized;
    }

    public boolean isResized () {
        return resized;
    }

    void update () {
        glfwSwapBuffers(handle);
        glfwPollEvents();
    }

    void cleanup () {
        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(handle);
        glfwDestroyWindow(handle);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        errorCallback.free();
    }
}
