package spring_education.backend.db_security_utility.annotation.warmup;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface WarmUp {
    //값이 작을 수록 우선순위가 높음
    int priority() default 50;
    //전달할 인자
    String[] args() default {};
}
