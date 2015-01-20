package gui;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
    public Panel(int width, int height) {
        Dimension dimension = new Dimension(width, height);
        setLayout(null);
        setPreferredSize(dimension);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(0x0099FF));
        g2d.fillRect(0, 0, 640, 480);
    }
}
