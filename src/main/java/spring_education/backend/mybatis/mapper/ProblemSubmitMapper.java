package spring_education.backend.mybatis.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import spring_education.backend.mybatis.model.ProblemSubmit;

import java.util.List;

public interface ProblemSubmitMapper {
    @Select("SELECT * FROM problem_submit WHERE user_id = #{userId} AND problem_id = #{problemId} ORDER BY problem_submit_id")
    List<ProblemSubmit> getProblemSubmit(@Param("userId") Long userId, @Param("problemId") Long problemId);
}


