package spring_education.backend.RabbitMQ.dto;

import lombok.Getter;

@Getter
public class JudgeProgressDTO {
    private Long userId;
    private Long problemId;
    private Long submitId;
    private Long solvedTestNum;
    private Long totalTestNum;
}
