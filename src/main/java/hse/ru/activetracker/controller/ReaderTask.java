package hse.ru.activetracker.controller;

import hse.ru.activetracker.listeners.KeyboardListener;
import hse.ru.activetracker.listeners.MouseListener;
import hse.ru.activetracker.models.KeyboardEvent;
import hse.ru.activetracker.models.MouseEvent;
import hse.ru.activetracker.queue.InternalSender;
import hse.ru.activetracker.utils.RabbitMessage;
import hse.ru.activetracker.utils.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.TimeoutException;

public class ReaderTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReaderTask.class);
    private static final String filePath = "src/main/resources/producer.properties";
    private final KeyboardListener keyListener;
    private final MouseListener mouseListener;
    private final InternalSender internalSender;
    private boolean isSent = false;

    public ReaderTask(MouseListener mouseListener, KeyboardListener keyListener) {
        this.keyListener = keyListener;
        this.mouseListener = mouseListener;
        this.internalSender = new InternalSender(PropertiesUtils.readProperties(filePath));
    }

    public void send(String project) {
        MouseEvent mouseEvent = mouseListener.collectData();
        KeyboardEvent keyboardEvent = keyListener.collectData();

        RabbitMessage rabbitMessage = new RabbitMessage(mouseEvent, keyboardEvent, LocalDateTime.now(), project);
        while (!isSent) {
            try {
                internalSender.sendInfo(rabbitMessage);
                isSent = true;
            } catch (IOException | TimeoutException e) {
                System.out.println("error on sending: " + e.getMessage());
                LOGGER.error("error while sending queue. repeat....");
            }
        }
        mouseListener.clear();
        keyListener.clear();
        isSent = false;
    }
}
