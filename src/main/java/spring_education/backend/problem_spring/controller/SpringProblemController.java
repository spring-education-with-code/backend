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
import spring_education.backend.problem_spring.dto.SubmitRabbitMQDTO;
import spring_education.backend.problem_spring.dto.SubmitRequestDTO;
import spring_education.backend.problem_spring.service.SpringProblemService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SpringProblemController {

    private final SpringProblemService springProblemService;

    @PostMapping("/api/problem/spring/{problemId}")
    public ResponseEntity<Void> submitCode(@PathVariable String problemId, @RequestBody SubmitRequestDTO submitDTO){

        log.info("/api/problem/spring 진입");
        //submitDTO 형태를 submitRabbitMQDTO 형태로 바꾼다 ( controller, service, user_id, problem_id)
        SubmitRabbitMQDTO submitRabbitMQDTO = new SubmitRabbitMQDTO(submitDTO.getController(), submitDTO.getService(), "1", problemId);

        //submitRabbitMQDTO 를 json 형태로 바꾼다
        String message = "";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            message = objectMapper.writeValueAsString(submitRabbitMQDTO);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

        //message를 서비스 단에 전달
        springProblemService.submitCode(message);

        return ResponseEntity.ok().build();
    }

}
