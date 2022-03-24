package render;

import java.util.concurrent.CopyOnWriteArrayList;

import objecten.Object;
import wiskunde.Ray;
import wiskunde.RayHit;
import wiskunde.Vec3;

public class Scene {

    private int skyboxIndex = 0;
    private Camera camera;
    private Light light;
    private CopyOnWriteArrayList<Object> solids;
    private Skybox skybox;

    public Scene() {
        this.solids = new CopyOnWriteArrayList<Object>();
        this.camera = new Camera();
        this.light = new Light(new Vec3(5, 5, 5));
        this.skybox = new Skybox("Sky.jpg");
    }

    public void addObject(Object solid) {
        this.solids.add(solid);
    }

    public RayHit castRay(Ray ray) {
        RayHit closestHit = null;
        for (Object solid : solids) {
            if (solid == null)
                continue;

            Vec3 hitPos = solid.calcIntersect(ray);
            if (hitPos != null && (closestHit == null || Vec3.distance(closestHit.GetPos(), ray.getOrg()) > Vec3.distance(hitPos, ray.getOrg()))) {
                closestHit = new RayHit(ray, solid, hitPos, hitPos.normalize());
            }
        }
        return closestHit;

    }

    public Camera getCam() {
        return camera;
    }

    public Light getLight() {
        return light;
    }

    public Skybox getSkybox() {
        return skybox;
    }

    public void setSkyboxIndex(int index){
        skyboxIndex = index;
        setSkybox();
    }

    public void setSkybox(){
        switch(skyboxIndex){
            case 0:
                skybox = new Skybox("Sky.jpg");
                break;
            case 1:
                skybox = new Skybox("Town.jpg");
                break;
            case 2:
                skybox = new Skybox("Field.jpg");
                break;
        }
    }
}