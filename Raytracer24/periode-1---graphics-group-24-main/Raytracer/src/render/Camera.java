package render;

import wiskunde.Vec3;

public class Camera {

    private Vec3 position;

    public Camera() {
        this.position = new Vec3(0, 0, 2);
    }

    public Vec3 getPos() {
        return position;
    }

    public void setPos(Vec3 position) {
        this.position = position;
    }

}