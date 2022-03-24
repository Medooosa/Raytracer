package wiskunde;

public class Ray {

    private Vec3 origin;
    private Vec3 direction;


    
    public Ray(Vec3 origin, Vec3 direction){
        this.origin = origin;
        this.direction = direction;
    }

    public Vec3 getOrg(){
        return origin;
    }

    public Vec3 getDir(){
        return direction;
    }       
}
