package objecten;

import wiskunde.Vec3;
import wiskunde.Ray;
import pixelinfo.Color;

public class Plane extends Object {

    private boolean checkered;

    public Plane(boolean checkered, float height, Color color, float reflectivity) {
        super(new Vec3(0, height, 0), color, reflectivity);
        this.checkered = checkered;
    }

    @Override
    public Vec3 calcIntersect(Ray ray) {
        float t = -(ray.getOrg().getY()-position.getY()) / ray.getDir().getY();
        if (t > 0 && Float.isFinite(t))
        {
            return ray.getOrg().sum(ray.getDir().mult(t));
        }

        return null;

    }
    @Override
    public Vec3 getNormalized(Vec3 point) {
    return new Vec3(0, 1, 0);

    }

    @Override
    public Color getTextureColor(Vec3 point) {
        if (checkered) {
            // in first or third quadrant of the checkerplane
            if (((point.getX() > 0) & (point.getZ() > 0)) || ((point.getX() < 0) & (point.getZ() < 0))) {
                if ((int)point.getX() % 2 == 0 ^ (int)point.getZ() % 2 != 0) {
                    return Color.GRAY;
                } else {
                    return Color.DARK_GRAY;
                }
            } else {
                // in second or fourth quadrant of the checkerplane
                if ((int)point.getX() % 2 == 0 ^ (int)point.getZ() % 2 != 0) {
                    return Color.DARK_GRAY;
                } else {
                    return Color.GRAY;
                }
            }
        } else {
            return getColor();
        }
    }

}