package hse.ru.activetracker.listeners;

import hse.ru.activetracker.models.KeyboardEvent;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.HashMap;
import java.util.Map;

public class KeyboardListener implements NativeKeyListener, Listener<KeyboardEvent> {
    private Map<String, Long> keyPressed;
    private Map<String, Long> keyReleased;
    private Map<String, Long> keyTyped;

    public KeyboardListener() {
        this.keyTyped = new HashMap<>();
        this.keyReleased = new HashMap<>();
        this.keyPressed = new HashMap<>();
    }


    public void nativeKeyPressed(NativeKeyEvent e) {
        if (keyPressed.get(NativeKeyEvent.getKeyText(e.getKeyCode())) == null) {
            keyPressed.put(NativeKeyEvent.getKeyText(e.getKeyCode()), 1L);
        } else {
            keyPressed.put(NativeKeyEvent.getKeyText(e.getKeyCode()), keyPressed.get(NativeKeyEvent.getKeyText(e.getKeyCode())) + 1);
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        if (keyReleased.get(NativeKeyEvent.getKeyText(e.getKeyCode())) == null) {
            keyReleased.put(NativeKeyEvent.getKeyText(e.getKeyCode()), 1L);
        } else {
            keyReleased.put(NativeKeyEvent.getKeyText(e.getKeyCode()), keyReleased.get(NativeKeyEvent.getKeyText(e.getKeyCode())) + 1);
        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
        if (keyTyped.get(NativeKeyEvent.getKeyText(e.getKeyCode())) == null) {
            keyTyped.put(NativeKeyEvent.getKeyText(e.getKeyCode()), 1L);
        } else {
            keyTyped.put(NativeKeyEvent.getKeyText(e.getKeyCode()), keyTyped.get(NativeKeyEvent.getKeyText(e.getKeyCode())) + 1);
        }
    }

    public KeyboardEvent collectData() {
        return new KeyboardEvent(keyPressed, keyReleased, keyTyped);
    }

    public void clear() {
        keyTyped.clear();
        keyPressed.clear();
        keyReleased.clear();
    }
}
