package gui.input;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 * An object that records keyboard input. When a key is pressed, the key is mapped to the time it was pressed in
 * nanoseconds. When a key is released, it is mapped to 0.
 * 5 methods
 * @author Tyler Packard
 */
public class Keyboard {
    private final HashMap<String, Long> keys = new HashMap<>();
    private final KeyAdapter keyAdapter = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            String key = KeyEvent.getKeyText(e.getKeyCode());
            if (!keys.containsKey(key) || keys.get(key) == 0) keys.put(key, System.nanoTime());
        }

        @Override
        public void keyReleased(KeyEvent e) {
            keys.put(KeyEvent.getKeyText(e.getKeyCode()), 0l);
        }
    };

    public Keyboard(JComponent component) {
        component.addKeyListener(keyAdapter);
        component.setFocusable(true);
        component.requestFocus();
    }

    public boolean keyDown(String key) {
        return keys.containsKey(key) && keys.get(key) != 0;
    }

    /**
     * Returns the time a key has been pressed in nanoseconds, or -1 if it is not currently down.
     *
     * @param key The key to get the press duration of
     * @return The time in nanoseconds the key has been down or -1 if it is up
     */
    public int timeDown(String key) {
        if (keys.containsKey(key) && keys.get(key) != 0) {
            return (int) (System.nanoTime() - keys.get(key));
        } else {
            return -1;
        }
    }
}
