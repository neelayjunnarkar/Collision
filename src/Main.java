import Entities.Entity;
import gui.Panel;

import javax.swing.*;
import java.awt.*;

/**
 * A pro physics simulator.
 *
 * @author Tyler Packard
 * @author Neelay Junnarkar
 */
public class Main {
    public final static JFrame window = new JFrame("Space Simulator 2015");
    public final static Panel panel = new Panel(640, 480);

    public static void main(String[] args) {
        window.add(panel);
        window.pack();
        window.setResizable(false);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
