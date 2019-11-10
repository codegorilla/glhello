import org.joml.*;

public class Entity {
    private final Mesh mesh;

    private Vector3f position;
    private float scale;
    private Vector3f rotation;

    Entity (Mesh mesh) {
        this.mesh = mesh;
        position = new Vector3f(0, 0, 0);
        scale = 1;
        rotation = new Vector3f(0, 0,0);
    }

    Vector3f getPosition () {
        return position;
    }

    void setPosition (float x, float y, float z) {
        this.position.set(x, y, z);
    }

    float getScale () {
        return scale;
    }

    void setScale (float scale) {
        this.scale = scale;
    }

    Vector3f getRotation () {
        return rotation;
    }

    void setRotation (float x, float y, float z) {
        this.rotation.set(x, y, z);
    }

    Mesh getMesh () {
        return mesh;
    }
}
