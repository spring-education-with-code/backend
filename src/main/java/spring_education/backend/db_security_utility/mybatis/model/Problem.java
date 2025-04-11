package spring_education.backend.db_security_utility.mybatis.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class Problem {
    private Long problem_id;
    private String title;
    private String level;
    private Date created_date;
    private Date modified_date;
}
