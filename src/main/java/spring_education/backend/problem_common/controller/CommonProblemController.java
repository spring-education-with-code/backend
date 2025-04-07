package spring_education.backend.problem_common.controller;

import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring_education.backend.db_security_utility.mybatis.model.ProblemSubmit;
import spring_education.backend.problem_common.dto.SubmitResponseDTO;
import spring_education.backend.problem_common.dto.SubmitsResponseDTO;
import spring_education.backend.problem_common.service.CommonProblemService;

@RestController
@RequestMapping("/api/problem")
@RequiredArgsConstructor
public class CommonProblemController {

    private final CommonProblemService commonProblemService;

    @GetMapping("/submit/{problemId}")
    public ResponseEntity<SubmitsResponseDTO> getSubmitProblem(@PathVariable Long problemId, @RequestParam(defaultValue = "1") int pageNum){
        return ResponseEntity.status(HttpStatus.OK).body(commonProblemService.getSubmitProblem(1L, problemId, pageNum));
    }
}
