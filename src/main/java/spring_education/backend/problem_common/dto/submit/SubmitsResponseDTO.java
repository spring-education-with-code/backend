package spring_education.backend.problem_common.dto.submit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class SubmitsResponseDTO {
    Integer totalCount;
    Integer totalPages;
    List<SubmitResponseDTO> submits;
}
