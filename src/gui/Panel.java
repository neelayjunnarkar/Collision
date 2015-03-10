package gui;

import entities.Entity;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class Panel extends JPanel {
    private Entity[] toDraw = {};
    private Point2D.Double[] SIDE;


    public Panel(int width, int height) {
        Dimension dimension = new Dimension(width, height);
        setLayout(null);
        setPreferredSize(dimension);
        setBackground(new Color(0x14181A));
    }

    public void repaint(Entity[] entities, Point2D.Double[] SIDE) {
        toDraw = entities;
        this.SIDE = SIDE;
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

        try {
            g2d.setColor(new Color(0x44DD77));
            g2d.drawLine((int) SIDE[0].x, (int) SIDE[0].y, (int) SIDE[1].x, (int) SIDE[1].y);
        } catch (Exception e) {}
    }
}
