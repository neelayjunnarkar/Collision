import gui.Panel;

import javax.swing.*;

/**
 * A pro physics simulator.
 *
 * @author Tyler Packard
 * @author Neelay Junnarkar
 */
public class Main {
    public final static JFrame window = new JFrame("Collision");
    public final static Panel panel = new Panel(640, 480);

    public static void main(String[] args) {
        window.add(panel);
        window.pack();
        window.setResizable(false);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
    }
}
