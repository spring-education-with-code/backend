package spring_education.backend.db_security_utility.RabbitMQ.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class JudgeProgressAlarmDTO {
    Long submitId;
    String evaluation_progress;
}
