package spring_education.backend.problem_spring.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spring_education.backend.problem_spring.dto.SpringSubmitRabbitMQDTO;
import spring_education.backend.problem_spring.dto.SpringSubmitRequestDTO;
import spring_education.backend.problem_spring.dto.SpringSubmitResponseDTO;
import spring_education.backend.problem_spring.service.SpringProblemService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SpringProblemController {

    private final SpringProblemService springProblemService;

    @PostMapping("/api/problem/spring/{problemId}")
    public ResponseEntity<SpringSubmitResponseDTO> submitCode(@PathVariable String problemId, @RequestBody SpringSubmitRequestDTO submitDTO){

        log.info("/api/problem/spring 진입");
        submitDTO.setUser_id(1L);
        submitDTO.setProblem_id(Long.valueOf(problemId));

        //message를 서비스 단에 전달
        SpringSubmitResponseDTO springSubmitResponseDTO = springProblemService.submitCode(submitDTO);

        return ResponseEntity.ok().body(springSubmitResponseDTO);
    }
}
