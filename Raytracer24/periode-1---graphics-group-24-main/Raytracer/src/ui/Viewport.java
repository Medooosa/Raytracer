package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Random;

import render.*;
import wiskunde.Vec3;
import objecten.*;
import objecten.Box;

public class Viewport extends JPanel {
    // private JFrame frame;
    private Scene scene;
    private Scene[] sceneList;
    private long deltaTime;
    private BufferedImage bufferedFrame;
    private DecimalFormat decimalFormat = new DecimalFormat("#.##");
    private float resolution = 0.5f;
    private int bounces = 5;

    // UI
    private JComboBox <String> selectBoxScene;
    private JComboBox <String> selectBoxSkybox;
    private JSlider inputResolution;
    private JSlider inputBounces;

    // Fill private variables, setup default settings, scene.
    public Viewport(JFrame frame) {
        deltaTime = 1;
        decimalFormat.setRoundingMode(RoundingMode.HALF_EVEN);

        selectBoxScene = new JComboBox<String>();
        selectBoxScene.addItem("Default");
        selectBoxScene.addItem("CloseUp");
        selectBoxScene.addItem("Random");

        selectBoxSkybox = new JComboBox<String>();
        selectBoxSkybox.addItem("Sky");
        selectBoxSkybox.addItem("Town");
        selectBoxSkybox.addItem("Field");

        inputResolution = new JSlider();
        inputResolution.setMinimum(1);
        inputResolution.setMaximum(100);
        inputResolution.setValue(50);
        
        inputBounces = new JSlider();
        inputBounces.setMinimum(0);
        inputBounces.setMaximum(10);
        inputBounces.setValue(5);

        add(selectBoxScene);
        add(selectBoxSkybox);
        add(inputResolution);
        add(inputBounces);

        selectBoxScene.addItemListener(e -> setScene(selectBoxScene.getSelectedIndex()));
        selectBoxSkybox.addItemListener(e -> setSkybox(selectBoxSkybox.getSelectedIndex()));
        inputResolution.addChangeListener(e -> setResolution(inputResolution.getValue() / 100f));
        inputBounces.addChangeListener(e -> setBounces(inputBounces.getValue()));


        Scene scene1 = new Scene();
        Scene scene2 = new Scene();
        Scene scene3 = new Scene();

        scene1.addObject(
            new Box(
                new Vec3(-2, 0, 5),
                new Vec3(2, 2, 2),
                new pixelinfo.Color(255, 0, 0),
                1f,
                0f
            )
        );
        scene1.addObject(
            new Sphere(
                new Vec3(-0, 0, 5),
                1f,
                new pixelinfo.Color(0, 255, 0),
                0.25f
            )
        );
        scene1.addObject(
            new Sphere(
                new Vec3(2, 0, 5),
                1f,
                new pixelinfo.Color(0, 0, 255),
                0.5f
            )
        );
        scene1.addObject(
            new Sphere(
                new Vec3(4, 0, 5),
                1f,
                new pixelinfo.Color(255, 0, 255),
                0.75f
            )
        );
        scene1.addObject(
            new Plane(
        true, 
        -2,
        new pixelinfo.Color(0, 144, 44),
        0.2f
        ));

        scene2.addObject(
            new Sphere(
                new Vec3(-5, 0, 0),
                10f,
                new pixelinfo.Color(0, 0, 254),
                0
            )
        );
        

        scene2.addObject(
            new Box(
                new Vec3(5, 8, 5),
                new Vec3(4, 4, 4),
                new pixelinfo.Color(0, 250, 0),
                0f,
                0f
            )
        );

        scene2.addObject(
            new Box(
                new Vec3(15,0, 1),
                new Vec3(10, 10, 10),
                new pixelinfo.Color(150, 0, 0),
                0f,
                0f
            )
        );
scene2.addObject(
        new Plane(
        true, 
        -2,
        new pixelinfo.Color(0, 144, 44),
        0.2f
        ));

        Random rand = new Random();
        for (int i = 0; i < 6; i++){
            scene3.addObject(
                new Sphere(
                    new Vec3(rand.nextInt(10), rand.nextInt(10), rand.nextInt(10)+15),
                    rand.nextInt(5),
                    new pixelinfo.Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)),
                    rand.nextFloat()
                )
            );
        }
        scene3.addObject(
        new Plane(
        true, 
        -2,
        new pixelinfo.Color(0, 144, 44),
        0.2f
        ));
        
        sceneList = new Scene[]{scene1, scene2, scene3};
        scene = sceneList[0];
        resolution = 0.5f;
    }
    
    

    private void setBounces(int value) {
        bounces = value;
    }



    private void setSkybox(int selectedIndex) {
        scene.setSkyboxIndex(selectedIndex);
    }



    private void setScene(int selectedScene) {
        scene = sceneList[selectedScene];
    }



    private void setResolution(float f) {
        resolution = f;
    }



    // Calculate framerate, render scene, repaint
    // Framerate: 1 second divided time it takes to render a frame.
    public void mainLoop() {
        while (true) {
            long startTime = System.currentTimeMillis();

            BufferedImage tmpBufferedFrame = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
            RenderEngine.renderScene(tmpBufferedFrame, getWidth(), getHeight(), scene, resolution, bounces);
            bufferedFrame = tmpBufferedFrame;

            repaint();
            deltaTime = System.currentTimeMillis() - startTime;
        }
    }

    public void paintComponent(Graphics graphics) {
        graphics.drawImage(bufferedFrame, 0, 0, this);
        graphics.setColor(Color.WHITE);
        FontMetrics fontMetrics = graphics.getFontMetrics();
        double fps = 1000f / deltaTime;
        String fpsString = "Framerate: " + decimalFormat.format(fps) + " FPS";
        graphics.drawString(fpsString, 0, (int) fontMetrics.getStringBounds(fpsString, graphics).getHeight());
    }
}
