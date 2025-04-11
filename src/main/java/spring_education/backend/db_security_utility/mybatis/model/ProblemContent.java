package spring_education.backend.db_security_utility.mybatis.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class ProblemContent {
    private Long problem_content_id;
    private Long problem_id;
    private Integer content_order;
    private String content_type;
    private String content;
    private Date created_date;
    private Date modified_date;
}
