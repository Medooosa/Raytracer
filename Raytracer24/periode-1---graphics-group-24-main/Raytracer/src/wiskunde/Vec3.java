package wiskunde;

public class Vec3 {
    
    private float x;
    private float y;
    private float z;

    public Vec3(float x, float y, float z){

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX() { 

        return x; 

    }

    public float getY() { 

        return y; 

    }

    public float getZ() { 

        return z; 

    }

    public void setX(float x) { 

        this.x = x; 

    }
    
    public void setY(float y) { 

        this.y = y;

    }

    public void setZ(float z) { 

        this.z = z;

    }

    public Vec3 sum(Vec3 vector) { 

        return new Vec3(this.x + vector.x, this.y + vector.y, this.z + vector.z);

    }

    public Vec3 minus(Vec3 vector) {

        return new Vec3(this.x - vector.x, this.y - vector.y, this.z - vector.z);

    }

    public Vec3 mult(Vec3 vector) {

        return new Vec3(this.x * vector.x, this.y * vector.y, this.z * vector.z);

    }

    public Vec3 mult(float scalar) {

        return new Vec3(this.x * scalar, this.y * scalar, this.z * scalar);

    }

    public Vec3 divide(Vec3 vector) {

        return new Vec3(this.x / vector.x, this.y / vector.y, this.z / vector.z);

    }

    public float length() {

        return (float) Math.sqrt(x * x + y * y + z * z);//cast zodat Math.sqrt kommagetallen meeneemt.
        
    }

    public Vec3 normalize() {
        
        float sumlength = length();

        return new Vec3( this.x / sumlength, this.y / sumlength, this.z / sumlength );

    }

    public void translate(Vec3 vector) {

        this.x += vector.x;
        this.y += vector.y;
        this.z += vector.z;

    }

    public static float distance(Vec3 a, Vec3 b) {

        return (float) Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2) + Math.pow(a.z - b.z, 2));

    }

    public static float dotPos(Vec3 v, Vec3 w) {

        return v.x * w.x + v.y * w.y + v.z * w.z;

    }

    public float[] toArray() {

        return new float[]{x,y,z};
        
    }
}