package objecten;

import wiskunde.Ray;
import wiskunde.Vec3;
import pixelinfo.Color;

//ToDo: Implement colors
public abstract class Object {
    protected Vec3 position;
    private Color color;
    private float reflectivity;

    public Object(Vec3 position, Color color, float reflectivity){
        this.position = position;
        this.color = color;
        this.reflectivity = reflectivity;
    }
    
    public abstract Vec3 calcIntersect(Ray ray);

    public abstract Vec3 getNormalized(Vec3 point);

    public float getReflectivity(){
        return reflectivity;
    }

    public Color getColor(){
        return color;
    }
    
    public Color getTextureColor(Vec3 point) {
        return getColor();
    }

    public Vec3 getPos() {
        return position;
    }
}
