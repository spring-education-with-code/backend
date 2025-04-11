package spring_education.backend.db_security_utility.mybatis.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class ProblemExample {
    private Long problem_example_id;
    private Long problem_id;
    private Integer example_order;
    private String input_example;
    private String output_example;
    private String description;
    private Date created_date;
    private Date modified_date;
}
