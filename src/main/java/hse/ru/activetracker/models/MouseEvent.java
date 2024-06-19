package hse.ru.activetracker.models;

import lombok.*;

/**
 * todo Document type MouseEvents
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MouseEvent {
    private Long mouseClicks;
    private Double mouseDistance;
    private Long mousePresses;

}
