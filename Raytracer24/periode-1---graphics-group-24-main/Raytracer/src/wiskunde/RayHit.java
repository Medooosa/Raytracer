package wiskunde;

import objecten.Object;

public class RayHit {
    private Ray ray;
    private Object hitSolid;
    private Vec3 hitPos;
    private Vec3 normal;

    public RayHit(Ray ray, Object hitSolid, Vec3 hitPos, Vec3 normal) {
        this.ray = ray;
        this.hitSolid = hitSolid;
        this.hitPos = hitPos;
        this.normal = hitSolid.getNormalized(hitPos);
    }

    public Ray getRay() {
        return ray;
    }

    public Object getSolid() {
        return hitSolid;
    }

    public Vec3 GetPos() {
        return hitPos;
    }

    public Vec3 GetNorm() {
        return normal;
    }
}
