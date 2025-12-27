package org.km.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(public * org.km..*(..))")
    public Object logMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        log.debug("Вызван метод {} с аргументами {}", joinPoint.getSignature(), joinPoint.getArgs());
        var result = joinPoint.proceed();
        log.debug("Возвращён результат {}", result);
        return result;
    }
}
