package spring_education.backend.db_security_utility.alarm.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class AlarmService {

    // TO_DO :
    // 지금은 단일 인스턴스라 지장 없는데 여러 인스턴스로 확장되면 수정이 필요함
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter connect(String username) {

        SseEmitter emitter = new SseEmitter(3600000L); //1시간

        emitters.put(username, emitter);

        //emitter 가 완료 되거나 타임아웃 되면 리스트에서 제거
        emitter.onCompletion(() -> {
            log.info("emitter.onCompletion 발생");
            emitters.remove(username);
        });

        emitter.onTimeout(() -> {
            log.info("emitter.onTimeout 발생");
            emitter.complete();
            emitters.remove(username);
        });

        emitter.onError((e) -> {
            log.info("emitter.onError 발생 : {}", e.getMessage());
            emitters.remove(username);
        });

        // 초기 1회는 답을 해주어야 함
        try{
            emitter.send(SseEmitter.event()
                    .name("CONNECTED")
                    .data("SSE connect"));
        }catch(IOException e){
            log.info("초기 연결 메세지 못보냈음");
            emitter.completeWithError(e);
            emitters.remove(username);
        }

        return emitter;

    }


    public void judgeProgressAlarm(String msg){
        SseEmitter emitter = emitters.get("donghyeon");

        try{
            emitter.send(SseEmitter.event()
                    .name("message")
                    .data(msg));
        } catch (IOException e) {
            log.info("알람 메세지 못보냈음");
            emitters.remove("donghyeon");
        }
    }
}
