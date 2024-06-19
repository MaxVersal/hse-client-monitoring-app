package hse.ru.activetracker.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import hse.ru.activetracker.utils.RabbitMessage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

/**
 * todo Document type InternalSender
 */
public class InternalSender {
    private final String queue;
    private final ConnectionFactory factory;
    private final ObjectMapper mapper;

    public InternalSender(Properties properties) {
        this.factory = new ConnectionFactory();
        this.queue = properties.getProperty("queue");
        this.mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
    }

    public void sendInfo(RabbitMessage message) throws IOException, TimeoutException {
        try (Connection connection = factory.newConnection()) {
            Channel channel = connection.createChannel();
            channel.basicPublish("",
                queue,
                null,
                mapper.writeValueAsString(message).getBytes(StandardCharsets.UTF_8));
        }
    }
}
