package spring_education.backend.db_security_utility.mybatis.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import spring_education.backend.db_security_utility.mybatis.model.ProblemSubmit;
import spring_education.backend.problem_spring.dto.SpringInsertSQLDTO;

import java.util.List;

public interface ProblemSubmitMapper {
    @Select("SELECT * FROM problem_submit WHERE user_id = #{userId} AND problem_id = #{problemId} ORDER BY problem_submit_id DESC")
    List<ProblemSubmit> getProblemSubmit(@Param("userId") Long userId, @Param("problemId") Long problemId);

    @Select("SELECT * FROM problem_submit WHERE user_id = #{userId} AND problem_id = #{problemId} AND controller_code = #{controllerCode} AND service_code = #{serviceCode}")
    List<ProblemSubmit> getExistSubmit(@Param("userId") Long userId, @Param("problemId") Long problemId,
                                       @Param("controllerCode") String controllerCode, @Param("serviceCode") String serviceCode);

    @Insert("INSERT INTO problem_submit(user_id, problem_id, controller_code, service_code, is_correct) " +
            "VALUES(#{userId}, #{problemId}, #{controllerCode}, #{serviceCode}, #{isCorrect})")
    @Options(useGeneratedKeys = true, keyProperty = "problemSubmitId", keyColumn = "problem_submit_id")
    int insertProblemSubmit(SpringInsertSQLDTO springInsertSQLDTO);
}


