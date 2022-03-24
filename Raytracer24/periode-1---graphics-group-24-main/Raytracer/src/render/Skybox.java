package render;

import wiskunde.Vec3;
import pixelinfo.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Skybox {
    private BufferedImage skybox;
    private boolean checkloaded;

    public Skybox(String nameimage){
        skybox = new BufferedImage(2,2, BufferedImage.TYPE_INT_RGB);
        checkloaded = false;

        new Thread("Loader") {
            @Override
            public void run() {
                try {
                skybox = ImageIO.read(getClass().getResourceAsStream("/images/"+nameimage));
                checkloaded = true;
                }
                catch (IOException e) {
                    System.out.println("error: " + e);
                }
            }        
        }.start();
    }

    public Color getColor(Vec3 a) {
        float b = (float) (0.5+Math.atan2(a.getZ(), a.getX())/(2*Math.PI));
        float c = (float) (0.5 - Math.asin(a.getY())/Math.PI);

        try{
            return Color.fromInt(skybox.getRGB((int)(b*(skybox.getWidth()-1)), (int)(c*(skybox.getHeight()-1))));
        } catch (Exception e) {
            return new pixelinfo.Color(0, 0, 255);
        }
    }
}
