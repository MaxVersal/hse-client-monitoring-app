package hse.ru.activetracker.utils;

import hse.ru.activetracker.models.KeyboardEvent;
import hse.ru.activetracker.models.MouseEvent;
import lombok.*;

import java.time.LocalDateTime;

/**
 * todo Document type KafkaMessage
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RabbitMessage {
    private MouseEvent mouseEvent;
    private KeyboardEvent keyboardEvent;
    private LocalDateTime createTime;
    private String projectName;
}
