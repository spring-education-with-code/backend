package spring_education.backend.RabbitMQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Queue;

@Component
public class RabbitMQReceiver {

    @RabbitListener(queues = "spring.education.result")
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
    }
}
