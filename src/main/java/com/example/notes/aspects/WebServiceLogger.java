package com.example.notes.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class WebServiceLogger {

    private static Logger LOG = LoggerFactory.getLogger(WebServiceLogger.class);

    @Pointcut(value = "execution(public * com.example.notes.service.NoteService.*(..))")
    public void serviceMethod() {}

    @Pointcut("@annotation(com.example.notes.annotation.Loggable)")
    public void loggableMethod() {}

    @Around(value = "serviceMethod() && loggableMethod()")
    public Object logWebServiceCall(ProceedingJoinPoint thisJoinPoint) throws Throwable {
        String methodName = thisJoinPoint.getSignature().getName();
        Object[] methodArgs = thisJoinPoint.getArgs();

        LOG.info("Call method {} with args {}", methodName, Arrays.toString(methodArgs));

        Object result = thisJoinPoint.proceed();

        LOG.info("Method {} returns {}", methodName, result);

        return result;
    }
}
