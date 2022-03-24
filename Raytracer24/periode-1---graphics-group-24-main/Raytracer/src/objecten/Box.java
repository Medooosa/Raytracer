package objecten;

import pixelinfo.Color;
import wiskunde.Vec3;
import wiskunde.Ray;



// Adapted from:
// http://ray-tracing-conept.blogspot.com/2015/01/ray-box-intersection-and-normal.html
public class Box extends Object {
    private Vec3 min, max;

    public Box(Vec3 position, Vec3 scale, Color color, float reflectivity, float emission) {
        super(position, color, reflectivity);
        this.max = position.sum(scale.mult(0.5F));
        this.min = position.minus(scale.mult(0.5F));
    }

    
    public Vec3 calcIntersect(Ray ray) {
        float t1,t2,tnear = Float.NEGATIVE_INFINITY,tfar = Float.POSITIVE_INFINITY,temp;
        boolean intersectFlag = true;
        float[] rayDir = ray.getDir().toArray();
        float[] rayOrg = ray.getOrg().toArray();
        float[] b1 = min.toArray();
        float[] b2 = max.toArray();


        for(int i =0 ;i < 3; i++){
            if(rayDir[i] == 0){
                if(rayOrg[i] < b1[i] || rayOrg[i] > b2[i])
                    intersectFlag = false;
            }
            else{
                t1 = (b1[i] - rayOrg[i])/rayDir[i];
                t2 = (b2[i] - rayOrg[i])/rayDir[i];
                if(t1 > t2){
                    temp = t1;
                    t1 = t2;
                    t2 = temp;
                }
                if(t1 > tnear)
                    tnear = t1;
                if(t2 < tfar)
                    tfar = t2;
                if(tnear > tfar)
                    intersectFlag = false;
                if(tfar < 0)
                    intersectFlag = false;
            }
        }
        if(intersectFlag)
            return ray.getOrg().sum(ray.getDir().mult(tnear));
        else
            return null;
    }

    //hiermee bekijkt hij of de ray de box raakt
    public boolean contains(Vec3 point) {
        return point.getX() >= min.getX() && point.getY() >= min.getY() && point.getZ() >= min.getZ() && point.getX() <= max.getX() && point.getY() <= max.getY() && point.getZ() <= max.getZ();
    }

    
    public Vec3 getNormalized(Vec3 point) {
        float[] direction = point.minus(position).toArray();
        float largestVal = Float.NaN;

        for (int i = 0; i < 3; i++) {
            if (Float.isNaN(largestVal) || largestVal < Math.abs(direction[i])) {
                largestVal = Math.abs(direction[i]);
            }
        }

        if (largestVal == 0) {
            return new Vec3(0, 0, 0);
        } else {
            for (int i = 0; i<3; i++) {
                if (Math.abs(direction[i]) == largestVal) {
                    float[] normal = new float[] {0,0,0};
                    normal[i] = direction[i] > 0 ? 1 : -1;

                    return new Vec3(normal[0], normal[1], normal[2]);
                }
            }
        }

        return new Vec3(0, 0, 0);
    }
}
