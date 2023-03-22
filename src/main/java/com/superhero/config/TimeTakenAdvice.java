package com.superhero.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;
@Aspect
@Component
@Slf4j
@ConditionalOnExpression("${aspect.enabled:true}")
public class TimeTakenAdvice {
    @Around("@annotation(com.superhero.config.TimeTaken)")
    public Object executionTime(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object object = point.proceed();
        long endtime = System.currentTimeMillis();
        log.info("Class : "+ point.getSignature().getDeclaringTypeName() +". Method: "+ point.getSignature().getName() + ". Time taken is : " + (endtime-startTime) +"ms");
        return object;
    }
}
