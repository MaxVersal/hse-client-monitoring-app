package hse.ru.activetracker.models;

import lombok.*;

import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
public class KeyboardEvent {
    private Map<String, Long> keyPressed;

    private Map<String, Long> keyReleased;
    private Map<String, Long> keyTyped;
    private Long totalPresses;
    public KeyboardEvent(Map<String, Long> keyPressed, Map<String, Long> keyReleased, Map<String, Long> keyTyped) {
        this.keyPressed = keyPressed;
        this.keyReleased = keyReleased;
        this.keyTyped = keyTyped;
        totalPresses = keyPressed.values().stream().mapToLong(Long::longValue).sum() +
                        keyReleased.values().stream().mapToLong(Long::longValue).sum() +
                            keyTyped.values().stream().mapToLong(Long::longValue).sum();
    }

    @Override
    public String toString() {
        return "KeyboardEvent{" +
            "keyPressed=" + keyPressed +
            ", keyReleased=" + keyReleased +
            ", keyTyped=" + keyTyped +
            '}';
    }
}
