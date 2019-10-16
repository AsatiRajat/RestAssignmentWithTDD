package com.yash.rest.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class LoggingAspect {

	private Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

	@Pointcut(value = "execution(* com.yash.rest.controller.EmployeeController.*(..))")
	public void method1() {
	}

	@Pointcut(value = "execution(* com.yash.rest.service.EmployeeService.*(..))")
	public void method2() {
	}

	@Before("method1() or method2()")
	public void loggingBefore(JoinPoint joinPoint) {
		logger.info("==========LoggingAspect loggingBefore called.==========");
		logger.info("Method name : " + joinPoint.getSignature().getName());
		logger.info("Number of method args : " + joinPoint.getArgs().length);
		Object[] args = joinPoint.getArgs();
		for (int i = 0; i < args.length; i++) {
			logger.info("Arguments : " + args[i]);
		}
	}

	@AfterReturning(value = "method1() or method2()", returning = "result")
	public void loggingAfterReturning(JoinPoint joinPoint, Object result) {
		logger.info("==========LoggingAspect loggingAfterReturning called.==========");
		logger.info("Returned value : " + result);
	}

	@AfterThrowing(value = "method1() or method2()", throwing = "exception")
	public void loggingThrowing(JoinPoint joinPoint, Object exception) {
		logger.info("==========LoggingAspect loggingThrowing called.==========");
		logger.info("After throwing : " + exception);
	}

}
