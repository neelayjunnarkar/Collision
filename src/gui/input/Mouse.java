package gui.input;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class Mouse {
    private int x = 0;
    private int y = 0;
    private int totalScroll = 0;
    private boolean leftDown = false;
    private boolean rightDown = false;
    private boolean mouseIn = true;

    private final MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                leftDown = true;
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                rightDown = true;
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                leftDown = false;
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                rightDown = false;
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            mouseIn = true;
        }

        @Override
        public void mouseExited(MouseEvent e) {
            mouseIn = false;
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            x = e.getX();
            y = e.getY();
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            totalScroll += e.getWheelRotation();
        }
    };

    public Mouse(JComponent component) {
        component.addMouseListener(mouseAdapter);
        component.addMouseMotionListener(mouseAdapter);
        component.addMouseWheelListener(mouseAdapter);
        component.setFocusable(true);
        component.requestFocus();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getTotalScroll() {
        return totalScroll;
    }

    public boolean isLeftDown() {
        return leftDown;
    }

    public boolean isRightDown() {
        return rightDown;
    }

    public boolean isMouseIn() {
        return mouseIn;
    }
}
