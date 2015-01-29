package gui;

import entities.Entity;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
    private Entity[] toDraw = {};


    public Panel(int width, int height) {
        Dimension dimension = new Dimension(width, height);
        setLayout(null);
        setPreferredSize(dimension);
        setBackground(new Color(0x14181A));
    }

    public void repaint(Entity[] entities) {
        toDraw = entities;
        super.repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Entity entity : toDraw) {
            entity.draw(g2d);
        }
    }
}
