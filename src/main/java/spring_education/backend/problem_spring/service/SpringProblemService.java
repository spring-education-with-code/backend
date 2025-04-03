package spring_education.backend.problem_spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
