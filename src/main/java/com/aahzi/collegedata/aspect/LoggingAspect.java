package com.aahzi.collegedata.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * AOP Aspect for logging method entry, exit, execution time, and exceptions
 * for all Service and Controller methods.
 */
@Aspect
@Component
@Slf4j
public class LoggingAspect {

    /**
     * Pointcut for all methods in Service classes
     */
    @Pointcut("within(com.aahzi.collegedata.service..*)")
    public void serviceLayer() {
    }

    /**
     * Pointcut for all methods in Controller classes
     */
    @Pointcut("within(com.aahzi.collegedata.controller..*)")
    public void controllerLayer() {
    }

    /**
     * Around advice that logs method entry, exit, execution time, arguments, and
     * exceptions
     * for both Service and Controller layers.
     *
     * @param joinPoint the proceeding join point
     * @return the result of the method execution
     * @throws Throwable if the method throws an exception
     */
    @Around("serviceLayer() || controllerLayer()")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();
        Object[] args = joinPoint.getArgs();

        // Get correlation ID from MDC (if available)
        String correlationId = org.slf4j.MDC.get("correlationId");
        String correlationInfo = correlationId != null ? " [correlationId=" + correlationId + "]" : "";

        // Log method entry with arguments
        log.info("→ Entering {}.{}() with arguments: {}{}",
                className, methodName, summarizeArgs(args), correlationInfo);

        long startTime = System.currentTimeMillis();
        Object result = null;

        try {
            // Execute the actual method
            result = joinPoint.proceed();

            long executionTime = System.currentTimeMillis() - startTime;

            // Log method exit with execution time
            log.info("← Exiting {}.{}() | Execution time: {}ms | Return type: {}{}",
                    className, methodName, executionTime,
                    result != null ? result.getClass().getSimpleName() : "void", correlationInfo);

            return result;

        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - startTime;

            // Log exception with stack trace
            log.error("✗ Exception in {}.{}() | Execution time: {}ms | Exception: {} | Message: {}{}",
                    className, methodName, executionTime,
                    e.getClass().getSimpleName(), e.getMessage(), correlationInfo, e);

            // Re-throw the exception to maintain normal exception handling
            throw e;
        }
    }

    private String summarizeArgs(Object[] args) {
        if (args == null)
            return "[]";
        return Arrays.stream(args)
                .map(arg -> {
                    if (arg instanceof String && ((String) arg).length() > 200) {
                        return ((String) arg).substring(0, 200) + "... (truncated " + ((String) arg).length()
                                + " chars)";
                    }
                    return String.valueOf(arg);
                })
                .collect(java.util.stream.Collectors.toList())
                .toString();
    }
}
