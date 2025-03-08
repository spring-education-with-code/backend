package spring_education.backend.spring_problem.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j
public class SpringProblemService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    String QUEUE_NAME = "spring.education.queue";

    public void submitCode(String message){
        rabbitTemplate.convertAndSend(QUEUE_NAME, message);
        log.info(" [x] Sent '" + message + "'");
    }
}
