package spring_education.backend.mybatis.model;

import lombok.*;

import java.util.Date;

@Getter
@NoArgsConstructor
public class ProblemSubmit {
    private Long problem_submit_id;
    private Long user_id;
    private Long problem_id;
    private String controller_code;
    private String service_code;
    private Boolean is_correct;
    private String evaluation_progress;
    private Date created_date;
    private Date modified_date;
}
