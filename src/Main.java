import entities.Entity;
import entities.Polygon2D;
import gui.Panel;
import physics.Vector;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.util.*;

/**
 * A pro physics simulator.
 *
 * @author Tyler Packard
 * @author Neelay Junnarkar
 */
public class Main {
    public final static JFrame window = new JFrame("Space Simulator 2015");
    public final static Panel panel = new Panel(640, 480);

    public final static HashMap<String, Entity> entities = new HashMap<>();

    public static void main(String[] args) {
        window.add(panel);
        window.pack();
        window.setResizable(false);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        entities.put("rocket", new Entity(new Polygon2D(new double[]{0, 0, 22, 20}, new double[]{0, 20, 22, 0}, 4,
                new Point2D.Double(100, 100))));
        entities.get("rocket").addForce("propellant", new Vector(100, Math.PI / 4));

        double prevTime = System.nanoTime();
        while (true) {
            double curTime = System.nanoTime();

            update(curTime - prevTime);
            panel.repaint(entities.values().toArray(new Entity[entities.size()]));

            prevTime = curTime;

            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void update(double nanoDelta) {
        double delta = nanoDelta / 1_000_000_000.0;
        for (Entity entity : entities.values()) {
            entity.update(delta);
        }
    }
}
