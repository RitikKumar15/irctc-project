package com.irctc.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger LOG = LogManager.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.irctc.service.*.*(..))")
    public void allServiceMethodCall() {}

    @Before("allServiceMethodCall()")
    public void beforeLog(JoinPoint joinPoint) {
        LOG.debug("Before Execution of Method: {}", joinPoint.getSignature().toLongString());
    }

    @After("allServiceMethodCall()")
    public void afterLog(JoinPoint joinPoint) {
        LOG.debug("After Execution of Method: {}", joinPoint.getSignature().toLongString());
    }
}
