package hse.ru.activetracker.utils;

import hse.ru.activetracker.models.KeyboardEvent;
import org.jnativehook.keyboard.NativeKeyEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * todo Document type Collector
 */
public class Collector {
    private static Map<String, Long> keyPressed = new HashMap<>();
    private static Map<String, Long> keyReleased = new HashMap<>();
    private static Map<String, Long> keyTyped = new HashMap<>();

    private Long mouseClicks;
    private Long mousePresses;
    private Double mouseDistance;
    private Integer xAxisPrevious;
    private Integer yAxisPrevious;

    public static void collectKeyPressed(String character) {
        if (keyPressed.get(character) == null) {
            keyPressed.put(character, 1L);
        } else {
            keyPressed.put(character, keyPressed.get(character) + 1);
        }
        System.out.println("trying to add char " + character + " to map " + keyPressed);
    }

    public static void collectKeyReleased(String character) {
        if (keyReleased.get(character) == null) {
            keyReleased.put(character, 1L);
        } else {
            keyReleased.put(character, keyReleased.get(character) + 1);
        }
        keyReleased.put(character, keyReleased.getOrDefault(character, 1L));
    }

    public static void collectKeyTyped(String character) {
        if (keyTyped.get(character) == null) {
            keyTyped.put(character, 1L);
        } else {
            keyTyped.put(character, keyTyped.get(character) + 1);
        }
    }

    public static KeyboardEvent collectKeyBoardEvent() {
        return new KeyboardEvent(keyPressed, keyReleased, keyTyped);
    }
}
