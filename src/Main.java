import entities.Entity;
import entities.Polygon2D;
import gui.Panel;
import gui.input.Keyboard;
import gui.input.Mouse;
import physics.Vector;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.util.*;

/**░░▒▒▓▓██████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████▓▓▒▒░░
 *    ______ ______ ___    ______ ______      ______ ___ _______ ______ ___    ___  ________ ______ ______      ______ ______ ___ ______
 *   /  ___//  _  //   |  /  ___//  ___/     /  ___//  //   /  //  /  //  /   /   |/__   __//     //  _  /     /___  //     //  //  ___/
 *  /___  //  ___//  _ | /  /__ /  ___/     /___  //  //      //  /  //  /__ /  _ |  /  /  /  /  //    _/     /  ___//  /  //  //  _  /
 * /_____//__/   /__/__|/_____//_____/     /_____//__//__/_/_//_____//_____//__/__| /__/  /_____//__/__\     /_____//_____//__//_____/
 *
 * ░░▒▒▓▓██████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████▓▓▒▒░░
 * A pro space simulator.
 *
 * @author Tyler Packard
 * @author Neelay Junnarkar
 */
public class Main {
    public final static JFrame window = new JFrame("Space Simulator 2016");
    public final static Panel panel = new Panel(640, 480);
    public final static Keyboard keyboard = new Keyboard(panel);
    public final static Mouse mouse = new Mouse(panel);

    public final static HashMap<String, Entity> entities = new HashMap<>();

    public static void main(String[] args) {
        window.add(panel);
        window.pack();
        window.setResizable(false);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        entities.put("asteroid 1", new Entity(new Polygon2D(new double[]{0, 0, 30, 30}, new double[]{0, 20, 20, 0}),
                                              new Point2D.Double(0, 0), 100));
        entities.get("asteroid 1").addVelocity("MATH.PI OVER FOR", new Vector(100, Math.PI / 4));

        entities.put("asteroid 2", new Entity(new Polygon2D(new double[]{0, 30, 30}, new double[]{20, 20, 0})));
        entities.get("asteroid 2").setPos(100, 100);

        entities.put("SPACESHIP!", new Entity(new Polygon2D(new double[]{0, 30, 30}, new double[]{20, 20, 0})));

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
                break;
            }
        }
    }

    static boolean hit = false;
    public static void update(double nanoDelta) {
        double delta = nanoDelta / 1_000_000_000.0;
        for (Entity entity : entities.values()) {
            entity.update(delta);
        }

        Point2D.Double[] overlapSide;
        if ((overlapSide = entities.get("asteroid 1").overlaps(entities.get("asteroid 2"))).length == 2) {
            hit = true;

            Vector rejection = entities.get("asteroid 1").getVelocity().reject(entities.get("asteroid 2").getVelocity());
            double ma = entities.get("asteroid 1").getMass();
            double vai = rejection.getMagnitude();
            System.out.println(rejection);
            double mb = entities.get("asteroid 2").getMass();
            double vbi = entities.get("asteroid 2").getVelocity().getMagnitude();

            double vaf = (ma*vai - mb*(vai-2*vbi)) / (ma + mb);
            double vbf = (ma*vai + mb*vbi - ma*vaf) / (mb);
            System.out.println(vai + " " + vbf);
            entities.get("asteroid 1").addVelocity("rejection", new Vector(vaf-vai, Math.PI + rejection.getAngle()));
            entities.get("asteroid 2").addVelocity("veocity", new Vector(vbf-vbi, rejection.getAngle()));
        }

        if (keyboard.keyDown("D")) {
            entities.get("SPACESHIP!").addVelocity("key left", new Vector(100, 0));
        }
        if (keyboard.keyDown("S")) {
            entities.get("SPACESHIP!").addVelocity("key down", new Vector(100, Math.PI * 0.5));
        }
        if (keyboard.keyDown("A")) {
            entities.get("SPACESHIP!").addVelocity("key right", new Vector(100, Math.PI));
        }
        if (keyboard.keyDown("W")) {
            entities.get("SPACESHIP!").addVelocity("key up", new Vector(100, Math.PI * 1.5));
        }
    }
}

