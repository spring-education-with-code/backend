package spring_education.backend.problem_spring.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import spring_education.backend.db_security_utility.mybatis.mapper.ProblemSubmitMapper;
import spring_education.backend.db_security_utility.mybatis.model.ProblemSubmit;
import spring_education.backend.db_security_utility.redis.RedisUtility;
import spring_education.backend.problem_spring.dto.SpringInsertSQLDTO;
import spring_education.backend.problem_spring.dto.SpringSubmitRabbitMQDTO;
import spring_education.backend.problem_spring.dto.SpringSubmitRequestDTO;
import spring_education.backend.problem_spring.dto.SpringSubmitResponseDTO;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SpringProblemService {

    private final RabbitTemplate rabbitTemplate;
    private final ProblemSubmitMapper problemSubmitMapper;
    private final RedisUtility redisUtlity;

    String QUEUE_NAME = "spring.education.queue";

    public SpringSubmitResponseDTO submitCode(SpringSubmitRequestDTO springSubmitRequestDTO){

        int isCorrect;
        int isThereSameRedisSubmit = redisUtlity.isThereSameRedisSubmit(springSubmitRequestDTO);
        if(isThereSameRedisSubmit != -1){
            //redis에 기존 제출 내역이 있는 경우
            log.info("레디스에 기존 제출이 있습니다");
            isCorrect = isThereSameRedisSubmit;
            return new SpringSubmitResponseDTO(true, isCorrect == 1?true:false);
        }
        else{
            //redis에 기존 제출 내역이 없는 경우, mysql 에 제출내역이 있는지 확인한다.
            List<ProblemSubmit> existSubmitList = problemSubmitMapper.getExistSubmit(1L, springSubmitRequestDTO.getProblem_id(),
                    springSubmitRequestDTO.getController(), springSubmitRequestDTO.getService());

            if(existSubmitList.size() != 0){
                //mysql에 기존 제출 내역이 있다.
                if(existSubmitList.get(0).getIs_correct()){
                    //mysql 에 기존 제출 내역이 있으면서 - 맞은 경우
                    isCorrect = 1;
                }else{
                    //mysql 에 기존 제출 내역이 있으면서 - 틀린 경우
                    isCorrect = 0;
                }

                return new SpringSubmitResponseDTO(true, isCorrect == 1?true:false);
            }else{
                //mysql에 기존 제출이 없어서 채점 해야함

                SpringInsertSQLDTO springInsertSQLDTO = SpringInsertSQLDTO.builder()
                        .userId(1L)
                        .problemId(springSubmitRequestDTO.getProblem_id())
                        .controllerCode(springSubmitRequestDTO.getController())
                        .serviceCode(springSubmitRequestDTO.getService())
                        .build();

                problemSubmitMapper.insertProblemSubmit(springInsertSQLDTO);

                Long problemSubmitId = springInsertSQLDTO.getProblemSubmitId();

                //springSubmitRequestDTO 에서 springSubmitRabbitMQDTO 로 변환
                SpringSubmitRabbitMQDTO springSubmitRabbitMQDTO = new SpringSubmitRabbitMQDTO(springSubmitRequestDTO.getController(),
                        springSubmitRequestDTO.getService(), "1", String.valueOf(springSubmitRequestDTO.getProblem_id()), String.valueOf(problemSubmitId));

                //submitRabbitMQDTO 를 json 형태로 바꾼다
                String message = "";
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    message = objectMapper.writeValueAsString(springSubmitRabbitMQDTO);
                } catch (JsonProcessingException e) {
                    log.error(e.getMessage());
                }

                rabbitTemplate.convertAndSend(QUEUE_NAME, message);
                log.info(" [x] Sent '" + message + "'");
                return new SpringSubmitResponseDTO(false, false);
            }
        }
    }
}
