package gui;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
    public Panel(int width, int height) {
        Dimension dimension = new Dimension(width, height);
        setLayout(null);
        setPreferredSize(dimension);
        setBackground(new Color(0x14181A));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
    }
}
