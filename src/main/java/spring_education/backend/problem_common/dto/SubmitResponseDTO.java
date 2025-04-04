package spring_education.backend.problem_common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class SubmitResponseDTO {
    Long submitId;
    Long problemId;
    String evaluation_progress;
    String submit_date;
}
