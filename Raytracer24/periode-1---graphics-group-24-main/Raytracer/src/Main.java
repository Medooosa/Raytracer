import javax.swing.*;

import ui.*;

public class Main extends JPanel {

    // Setup window and start render loop.
    public static void main(String[] args) {
        JFrame frame = new JFrame("Project Raytracing - By Jur Stedehouder, Mark Hooijberg, Martijn Koning en Ian Bervoets");
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        Viewport viewport = new Viewport(frame);
        frame.add(viewport);

        frame.setVisible(true);
        viewport.mainLoop();
    }
}
