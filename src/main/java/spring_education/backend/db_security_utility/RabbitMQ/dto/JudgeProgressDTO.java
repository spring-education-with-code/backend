package spring_education.backend.db_security_utility.RabbitMQ.dto;

import lombok.Getter;

@Getter
public class JudgeProgressDTO {
    private Long userId;
    private Long problemId;
    private Long submitId;
    private Long solvedTestNum;
    private Long totalTestNum;
}
