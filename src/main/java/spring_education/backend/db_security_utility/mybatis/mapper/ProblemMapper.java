package spring_education.backend.db_security_utility.mybatis.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import spring_education.backend.db_security_utility.mybatis.model.Problem;
import spring_education.backend.db_security_utility.mybatis.model.ProblemConstraint;
import spring_education.backend.db_security_utility.mybatis.model.ProblemContent;
import spring_education.backend.db_security_utility.mybatis.model.ProblemExample;

import java.util.List;

public interface ProblemMapper {

    @Select("SELECT * FROM problem WHERE problem_id = #{problemId}")
    Problem getProblem(@Param("problemId") Long problemId);

    @Select("SELECT * FROM problem JOIN problem_content ON problem.problem_id = problem_content.problem_id WHERE problem.problem_id = #{problemId}")
    List<ProblemContent> getProblemContent(@Param("problemId") Long problemId);

    @Select("SELECT * FROM problem JOIN problem_constraint ON problem.problem_id = problem_constraint.problem_id WHERE problem.problem_id = #{problemId}")
    List<ProblemConstraint> getProblemConstraint(@Param("problemId") Long problemId);

    @Select("SELECT * FROM problem JOIN problem_example ON problem.problem_id = problem_example.problem_id WHERE problem.problem_id = #{problemId}")
    List<ProblemExample> getProblemExample(@Param("problemId") Long problemId);
}
