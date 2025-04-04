package spring_education.backend.RabbitMQ;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import spring_education.backend.RabbitMQ.dto.JudgeProgressAlarmDTO;
import spring_education.backend.RabbitMQ.dto.JudgeProgressDTO;
import spring_education.backend.alarm.service.AlarmService;

import java.util.Queue;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMQReceiver {

    private final AlarmService alarmService;

    @RabbitListener(queues = "spring.education.result")
    public void receiveMessage(String message) {
        //sse 이벤트 전송
        log.info("채점결과 Rabbitmq 에서 받음 - " + message);

        String tempMsg = "";
        JudgeProgressDTO judgeProgressDTO = null;
        ObjectMapper objectMapper = new ObjectMapper();

        try{
            judgeProgressDTO = objectMapper.readValue(message, JudgeProgressDTO.class);

            if(judgeProgressDTO.getSolvedTestNum() == -1){
                tempMsg = "틀렸습니다!";
            }else if(judgeProgressDTO.getSolvedTestNum() == judgeProgressDTO.getTotalTestNum()){
                tempMsg = "맞았습니다!";
            }else{
                int percent = (int)(((double) judgeProgressDTO.getSolvedTestNum() / judgeProgressDTO.getTotalTestNum()) * 100);
                tempMsg = "채점중(" + percent + "%)";
            }
        }catch (JsonMappingException e) {
            log.error("JSON 파싱 오류: ", e);
        } catch (JsonProcessingException e) {
            log.error("JSON 파싱 오류: ", e);
        }

        JudgeProgressAlarmDTO judgeProgressAlarmDTO = new JudgeProgressAlarmDTO(judgeProgressDTO.getSubmitId(), tempMsg);
        String sendMsg = "";
        try {
            sendMsg = objectMapper.writeValueAsString(judgeProgressAlarmDTO);
        }catch (JsonProcessingException e) {
            log.error("JSON 생성 오류: ", e);
        }
        log.info("채점결과 sse 전송 - " + sendMsg);
        alarmService.judgeProgressAlarm(sendMsg);
    }
}
