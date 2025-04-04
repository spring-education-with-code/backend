package spring_education.backend.problem_spring.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SpringSubmitRequestDTO {
    String controller;
    String service;
    Long user_id;
    Long problem_id;
}
