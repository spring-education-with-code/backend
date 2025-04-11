package spring_education.backend.db_security_utility.mybatis.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class ProblemConstraint {
    private Long problem_constraint_id;
    private Long problem_id;
    private Integer constraint_order;
    private String constraint_text;
    private Date create_date;
    private Date modified_date;
}
