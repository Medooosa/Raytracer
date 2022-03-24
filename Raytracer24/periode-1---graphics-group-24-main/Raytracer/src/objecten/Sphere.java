package objecten;

import wiskunde.Vec3;
import wiskunde.Ray;
import pixelinfo.Color;

public class Sphere extends Object  {
    private float radius;

    public Sphere(Vec3 position, float radius, Color color, float reflectivity) {
        super (position, color, reflectivity);
        this.radius = radius;
    }

    //ToDo: Implement intersection calculation
    @Override
    public Vec3 calcIntersect(Ray ray){
        float t = Vec3.dotPos(position.minus(ray.getOrg()), ray.getDir());
        Vec3 p =  ray.getOrg().sum(ray.getDir().mult(t));

        float y = position.minus(p).length();
        if (y < radius) {
            float x = (float) Math.sqrt(radius*radius - y*y);
            float t1 = t-x;
            if (t1 > 0) return ray.getOrg().sum(ray.getDir().mult(t1));
            else return null;
        } else {
            return null;
        }
    }

    //ToDo: Implement normal calculation
    @Override
    public Vec3 getNormalized(Vec3 point){
        return point.minus(position).normalize();
    }
}
