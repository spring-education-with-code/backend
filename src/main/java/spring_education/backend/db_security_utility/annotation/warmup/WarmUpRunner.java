package spring_education.backend.db_security_utility.annotation.warmup;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Component
@RequiredArgsConstructor
@Slf4j
public class WarmUpRunner implements ApplicationRunner{

    private final ApplicationContext applicationContext;

    //내부 클래스
    @Getter
    @AllArgsConstructor
    private static class WarmUpTask {
        private final Object bean;
        private final Method method;
        private final int priority;
        private final String[] args;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<WarmUpTask> tasks = new ArrayList<>();

        //스프링 컨텍스트 내 모든 빈 검색
        Map<String, Object> beans = applicationContext.getBeansOfType(Object.class);

        for(Object bean : beans.values()){
            Method[] methods = bean.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(WarmUp.class)) {
                    WarmUp annotation = method.getAnnotation(WarmUp.class);
                    tasks.add(new WarmUpTask(bean, method, annotation.priority(), annotation.args()));
                }
            }
        }

        Collections.sort(tasks, Comparator.comparingInt(WarmUpTask::getPriority));

        //비동기 방식 적용
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        for (WarmUpTask task : tasks) {
            executorService.submit(() -> {
                try {
                    task.getMethod().setAccessible(true);

                    //함수에 전달할 매개변수 삽입
                    Class<?>[] parameterTypes = task.getMethod().getParameterTypes();
                    Object[] argsForMethod = new Object[parameterTypes.length];

                    for (int i = 0; i < parameterTypes.length; i++) {
                        argsForMethod[i] = convertParameter(task.getArgs()[i], parameterTypes[i]);
                    }

                    task.getMethod().invoke(task.getBean(), argsForMethod);
                    log.info("웜업 완료: " +
                            task.getBean().getClass().getName() + "." + task.getMethod().getName());
                } catch (Exception e) {
                    log.error("웜업 실행 중 오류: " +
                            task.getMethod().getName() + " - " + e.getMessage());
                }
            });
        }

        executorService.shutdown();
    }

    private Object convertParameter(String value, Class<?> targetType) {
        if (targetType.equals(String.class)) {
            return value;
        } else if (targetType.equals(int.class) || targetType.equals(Integer.class)) {
            return Integer.parseInt(value);
        } else if (targetType.equals(long.class) || targetType.equals(Long.class)) {
            return Long.parseLong(value);
        } else if (targetType.equals(boolean.class) || targetType.equals(Boolean.class)) {
            return Boolean.parseBoolean(value);
        } else if (targetType.equals(double.class) || targetType.equals(Double.class)) {
            return Double.parseDouble(value);
        } else if (targetType.equals(float.class) || targetType.equals(Float.class)) {
            return Float.parseFloat(value);
        } else if (targetType.equals(short.class) || targetType.equals(Short.class)) {
            return Short.parseShort(value);
        } else if (targetType.equals(byte.class) || targetType.equals(Byte.class)) {
            return Byte.parseByte(value);
        }
        throw new IllegalArgumentException("지원하지 않는 매개변수 타입: " + targetType.getName());
    }
}
