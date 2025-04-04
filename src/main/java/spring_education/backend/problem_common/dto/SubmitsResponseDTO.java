package spring_education.backend.problem_common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class SubmitsResponseDTO {
    Integer totalCount;
    List<SubmitResponseDTO> submits;
}
