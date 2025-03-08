package spring_education.backend.spring_problem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SubmitRabbitMQDTO {
    String controller;
    String service;
    String user_id;
    String problem_id;
}
