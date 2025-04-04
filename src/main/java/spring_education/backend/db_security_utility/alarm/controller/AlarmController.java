package spring_education.backend.db_security_utility.alarm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import spring_education.backend.db_security_utility.alarm.service.AlarmService;

@RestController
@RequestMapping("/api/alarm")
@RequiredArgsConstructor
public class AlarmController{

    private final AlarmService alarmService;

    @GetMapping("/connect")
    public SseEmitter connect(){
        String username = "donghyeon"; //TO-DO :
        return alarmService.connect(username);
    }


}
