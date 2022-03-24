package render;

import wiskunde.Vec3;

public class Light {

    private Vec3 position;

    public Light(Vec3 position) {
        this.position = position;
    }

    public Vec3 getPos() {
        return position;
    }

    public void setPos(Vec3 position) {
        this.position = position;
    }

}