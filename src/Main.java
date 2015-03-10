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
    public final static JFrame WINDOW = new JFrame("Space Simulator 2016");
    public final static Panel PANEL = new Panel(640, 480);
    public final static Keyboard KEYBOARD = new Keyboard(PANEL);
    public final static Mouse MOUSE = new Mouse(PANEL);

    public final static HashMap<String, Entity> ENTITIES = new HashMap<>();

    public final static double updateTime = 1_000_000_000 / 60.0;

    public static Point2D.Double[] SIDE = {new Point2D.Double(0, 0), new Point2D.Double(0, 0)};

    public static void main(String[] args) {
        WINDOW.add(PANEL);
        WINDOW.pack();
        WINDOW.setResizable(false);
        WINDOW.setVisible(true);
        WINDOW.setLocationRelativeTo(null);
        WINDOW.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        ENTITIES.put("asteroid 1", new Entity(new Polygon2D(new double[]{0, 0, 30, 30}, new double[]{0, 30, 30, 0}),
                                              new Point2D.Double(0, 00), 100, true));
        ENTITIES.get("asteroid 1").addVelocity("MATH.PI OVER FOR", new Vector(2, Math.PI/4.0));

        ENTITIES.put("asteroid 2", new Entity(new Polygon2D(new double[]{30, 30, 0, 0}, new double[]{0, 30, 30, 0}), new Point2D.Double(200, 100), 100, true));
        ENTITIES.get("asteroid 2").addVelocity("init", new Vector(-2, 0));

        ENTITIES.put("SPACESHIP!", new Entity(new Polygon2D(new double[]{0, 30, 30}, new double[]{10, 10, 0})));

        while (true) {
            long start = System.nanoTime();

            update();
            PANEL.repaint(ENTITIES.values().toArray(new Entity[ENTITIES.size()]), SIDE);

            try {
                Thread.sleep(Math.max(Math.round((updateTime - System.nanoTime() + start) / 1_000_000), 0));
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    static boolean hit = false;
    public static void update() {
        for (Entity entity : ENTITIES.values()) {
            entity.update();
        }

        ENTITIES.get("SPACESHIP!").rotate(0.1);

        Point2D.Double[] overlapSide = ENTITIES.get("asteroid 1").overlaps(ENTITIES.get("asteroid 2"));
        if (!hit && overlapSide.length == 2) {
            System.out.println(Arrays.toString(SIDE));
            SIDE[0].x = ENTITIES.get("asteroid 2").getPos().getX() + overlapSide[0].x;
            SIDE[1].x = ENTITIES.get("asteroid 2").getPos().getX() + overlapSide[1].x;
            SIDE[0].y = ENTITIES.get("asteroid 2").getPos().getY() + overlapSide[0].y;
            SIDE[1].y = ENTITIES.get("asteroid 2").getPos().getY() + overlapSide[1].y;
            System.out.println(Arrays.toString(SIDE) + "\n");

            hit = true;

            Point2D.Double a1 = new Point2D.Double(overlapSide[0].getX()+ENTITIES.get("asteroid 2").getPos().getX(), overlapSide[0].getY()+ENTITIES.get("asteroid 2").getPos().getY());
            Point2D.Double a2 = new Point2D.Double(overlapSide[1].getX()+ENTITIES.get("asteroid 2").getPos().getX(), overlapSide[1].getY()+ENTITIES.get("asteroid 2").getPos().getY());
            System.out.println("points: "+a1+"   "+a2);
            
            //make vector
            double magnitude = a1.distance(a2);
            double angle = Math.atan2(a1.getY()-a2.getY(), a1.getX()-a2.getX());
            Vector torejecton = new Vector(magnitude, angle);
            System.out.println("ang: "+torejecton.getAngle()*180.0/Math.PI+"   mag: "+torejecton.getMagnitude());
            
            Vector rejection = ENTITIES.get("asteroid 1").getVelocity().reject(torejecton);
            System.out.println("rejection preangle: "+rejection.getAngle()+"  premag: "+rejection.getMagnitude());
            if (rejection.getAngle() >= Math.PI ) {
                rejection.setAngle(rejection.getAngle()-Math.PI);
                rejection.setMagnitude(-rejection.getMagnitude());
            }
            if (rejection.getAngle() < 0) {
                rejection.setAngle(rejection.getAngle()+Math.PI);
                rejection.setMagnitude(-rejection.getMagnitude());
            }
            System.out.println("rejection postangle: "+rejection.getAngle()+"  postmag: "+rejection.getMagnitude());

            double ma = ENTITIES.get("asteroid 1").getMass();
            double vai = rejection.getMagnitude();

            double mb = ENTITIES.get("asteroid 2").getMass();
            double vbi = ENTITIES.get("asteroid 2").getVelocity().getMagnitude();

            double vaf = (ma*vai - mb*(vai-2*vbi)) / (ma + mb);
            double vbf = (ma*vai + mb*vbi - ma*vaf) / (mb);
            System.out.println("final velocitites: " + (vaf - vai) + " " + (vbf - vbi));
            ENTITIES.get("asteroid 1").addVelocity("rejection", new Vector(vaf-vai, rejection.getAngle()));
            ENTITIES.get("asteroid 2").addVelocity("veocity", new Vector(vbf-vbi, rejection.getAngle()));
        }

        if (KEYBOARD.keyDown("D")) {
            ENTITIES.get("SPACESHIP!").addVelocity("key left", new Vector(2, 0));
        } else {
            ENTITIES.get("SPACESHIP!").addVelocity("key left", new Vector(0, 0));
        }
        if (KEYBOARD.keyDown("S")) {
            ENTITIES.get("SPACESHIP!").addVelocity("key down", new Vector(2, Math.PI * 0.5));
        } else {
            ENTITIES.get("SPACESHIP!").addVelocity("key down", new Vector(0, 0));
        }
        if (KEYBOARD.keyDown("A")) {
            ENTITIES.get("SPACESHIP!").addVelocity("key right", new Vector(2, Math.PI));
        } else {
            ENTITIES.get("SPACESHIP!").addVelocity("key right", new Vector(0, 0));
        }
        if (KEYBOARD.keyDown("W")) {
            ENTITIES.get("SPACESHIP!").addVelocity("key up", new Vector(2, Math.PI * 1.5));
        } else {
            ENTITIES.get("SPACESHIP!").addVelocity("key up", new Vector(0, 0));
        }
    }
}

