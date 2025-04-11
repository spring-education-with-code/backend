package spring_education.backend.problem_common.dto.problem;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProblemResponseDTO {
    private String title;
    private String level;
    private Integer content_length;
    private List<ProblemContentDTO> content;
    private Integer constraint_length;
    private List<ProblemConstraintDTO> constraint;
    private Integer example_length;
    private List<ProblemExampleDTO> example;
}
