package spring_education.backend.problem_spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SpringSubmitRabbitMQDTO {
    String controller;
    String service;
    String user_id;
    String problem_id;
    String submit_id;
}
