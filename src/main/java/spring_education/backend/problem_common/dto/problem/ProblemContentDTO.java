package spring_education.backend.problem_common.dto.problem;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProblemContentDTO {
    private String content_type;
    private String content;
}
