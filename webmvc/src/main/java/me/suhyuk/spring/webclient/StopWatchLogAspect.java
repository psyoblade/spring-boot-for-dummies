package me.suhyuk.spring.webclient;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class StopWatchLogAspect {

    Logger logger = LoggerFactory.getLogger(StopWatchLog.class);

    @Around("@annotation(StopWatchLog)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object object = joinPoint.proceed();
        stopWatch.stop();
        String signature = joinPoint.getSignature().toString();
        logger.info(signature + " --> " + stopWatch.prettyPrint());

        return object;
    }

}
