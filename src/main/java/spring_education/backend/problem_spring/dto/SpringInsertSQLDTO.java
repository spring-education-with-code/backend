package spring_education.backend.problem_spring.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SpringInsertSQLDTO {
    private Long problemSubmitId;
    private Long userId;
    private Long problemId;
    private String controllerCode;
    private String serviceCode;
    private Boolean isCorrect;
}



