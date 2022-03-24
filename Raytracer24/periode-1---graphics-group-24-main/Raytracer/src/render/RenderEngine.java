package render;

import java.awt.image.BufferedImage;
import java.awt.Graphics;

import wiskunde.Ray;
import wiskunde.RayHit;
import wiskunde.Vec3;
import objecten.Object;

import java.awt.Color;

public class RenderEngine {
    public static final float GLOBAL_LIGHT = 0.3F;
    private static final boolean SHOW_SKYBOX = true;
    private static int MAX_BOUNCES = 5;


    // ToDo: Implement scene and resolution.
    public static void renderScene(BufferedImage image, int width, int height, Scene scene, float resolution, int maxBounces) {
        int density = (int) ((int) 1 / resolution);
        MAX_BOUNCES = maxBounces;

        // For every x pixel in the width do:
        for (int x = 0; x < width; x += density) {
            // For every y pixel in the height do:
            for (int y = 0; y < height; y += density) {
                float[] uv = getNormalizedScreenCoordinates(x, y, width, height);
                // ToDo: Calculate the collor of the pixel by shooting rays into the scene.
                // image.setRGB(x, y, calculatePixelColor(scene, uv[x], uv[y]).getRGB());
                java.awt.Color color = calculateRayHit(scene, uv[0], uv[1]);
                image.setRGB(x, y, color.getRGB());
                // image.setRGB(x, y, calculatePixelColor(scene, x, y).getRGB());
                // image.setRGB(x, y, 254);
            }
        }
    }

    public static void renderScene(Graphics graphics, int width, int height, Scene scene) {

        // For every x pixel in the width do:
        for (int x = 0; x < width; x += 1) {
            // For every y pixel in the height do:
            for (int y = 0; y < height; y += 1) {
                float[] uv = getNormalizedScreenCoordinates(x, y, width, height);
                // ToDo: Calculate the collor of the pixel by shooting rays into the scene.
                graphics.setColor(calculateRayHit(scene, uv[x], uv[y]));
                graphics.fillRect(x, y, 1, 1);
            }
        }
    }

    // Uses the resolution of the image to create normalized screen cordinates that lay somewhere around -1 and 1
    // while keeping the aspect ratio correct.
    public static float[] getNormalizedScreenCoordinates(int x, int y, int width, int height) {
        float u = 0, v = 0;
        if (width > height) {
            u = (float) (x - width / 2 + height / 2) / height * 2 - 1;
            v = -((float) y / height * 2 - 1);
        } else {
            u = (float) x / width * 2 - 1;
            v = -((float) (y - height / 2 + width / 2) / width * 2 - 1);
        }

        return new float[] { u, v };
    }

    // Calculate the ray origin and direction, shoot ray, calculate reflection?,
    public static Color calculateRayHit(Scene scene, float x, float y) {
        java.awt.Color color;
        // Step 1: Get camera from scene.
        Camera camera = scene.getCam();
        // Step 2: Calculate the screenplane distance from the camera position.
        Vec3 screenplane = new Vec3(0, 0, (float) -1.5);
        // Step 3: Calculate the ray direction.
        Vec3 rayDirection = (new Vec3(x, y, 0)).minus(screenplane).normalize();
        // Vec3 rayDirection = (new Vec3(0, 0, 0)).subtract(screenplane).normalize();
        // Step 4: Get the rayhit values.
        Ray ray = new Ray(screenplane.sum(camera.getPos()), rayDirection);
        // Ray ray = new Ray(screenplane.add(camera.getPos()), new Vec3(-5, 0, 0));
        RayHit hit = scene.castRay(ray);
        // Step 5: Calculate color.
        if (hit != null)
        {
            pixelinfo.Color reflectionColor = calculatePixelColor(scene, hit, MAX_BOUNCES);
            color = new java.awt.Color((int) reflectionColor.getR(), (int) reflectionColor.getG(), (int) reflectionColor.getB());
            return color;
        }
        else if (SHOW_SKYBOX){
            pixelinfo.Color sbColor = scene.getSkybox().getColor(rayDirection).multiplyColor(255);
            return new java.awt.Color((int) sbColor.getR(), (int) sbColor.getG(), (int) sbColor.getB());
        }
        else
        {
            return new java.awt.Color(0, 0, 0);
        }
        // throw new UnsupportedOperationException();
    }
    //ComputePixelInfoAtHit
    public static pixelinfo.Color calculatePixelColor(Scene scene, RayHit hit, int maxBounces) {
        // Step 1: get rayhit info needed for reflection.
        pixelinfo.Color reflect;
        Vec3 hitPosition = hit.GetPos();
        Vec3 rayDirection = hit.getRay().getDir();
        Object hitSolid = (Object) hit.getSolid();
        float reflectivity = hitSolid.getReflectivity();
        pixelinfo.Color hitColor = hitSolid.getColor();
        // Step 2: Calculate reflection ray direction.
        Vec3 reflectionVector = rayDirection.minus(hit.GetNorm().mult(2*Vec3.dotPos(rayDirection, hit.GetNorm())));// Vec3.dotPos heeft te maken met dat dotPos een static methode is.
        // Step 3: Calculate the origin so that it doesn't hit the same object again.
        Vec3 reflectionOrg = hitPosition.sum(reflectionVector.mult(0.001F)); //Beetje aan toevoegen zodat je niet hetzelfde object hit
        // Step 4: Shoot reflection ray
        RayHit reflectHit = maxBounces > 0 ? scene.castRay(new Ray(reflectionOrg, reflectionVector)) : null;// ?, : is if else statement in 1 line.

        // Step 5: overlay the reflection color on top of the rayhit color with a
        // reflectivity amount.
        if (reflectHit != null) {
            reflect = calculatePixelColor(scene, reflectHit, maxBounces-1);
        }
        else {
            //Color sbColor = scene.getSkybox().getColor(reflectionVector);
            //reflect = new PixelData(sbColor, Float.POSITIVE_INFINITY, sbColor.getLuminance()*SKY_EMISSION);
            //reflect = new pixelinfo.Color(0, 0, 0);
            //reflectivity = 0; 
            reflect = scene.getSkybox().getColor(reflectionVector).multiplyColor(255);
        }

        // step 6: Return new reflection color.
        float brightness = getDiffuseBrightness(scene, hit);
        pixelinfo.Color dotColor = pixelinfo.Color.lerp(hitColor, reflect.getColor(), reflectivity).multiplyColor(brightness);

        return dotColor;
    }

    private static float getDiffuseBrightness(Scene scene, RayHit hit){
        Light sceneLight = scene.getLight();

        Ray diffuseRay = new Ray(sceneLight.getPos(), hit.GetPos().minus(sceneLight.getPos()).normalize());

        RayHit lightBlocked = scene.castRay(diffuseRay);

        if (lightBlocked != null && lightBlocked.getSolid() != hit.getSolid()) {
            return GLOBAL_LIGHT; // min brightness
        }
        else{
            var dotPosResult =  Vec3.dotPos(hit.GetNorm(), sceneLight.getPos().minus(hit.GetPos()));
            return Math.max(GLOBAL_LIGHT, Math.min(1, dotPosResult));
        }
    }

}
