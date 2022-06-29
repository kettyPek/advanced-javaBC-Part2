package ajbc.learn.aspects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import ajbc.learn.dao.DaoException;

@Aspect
@Component
public class MyAspect {
	
	public MyAspect() {
		
	}
	
	// this is an advise method
	// syntax : ? - means optional
	// "execution(modifier? return-type ?method=pattern(arg-type,arg-type,..))"
	
	@Before("execution(* *ajbc.learn.dao.ProductDao.count(..))")
	public void logBeforeCalling(JoinPoint joinPoint) {
		System.out.println("Aspect is writing to logger method name: " + joinPoint.getSignature().getName());
		System.out.println("args are " + Arrays.toString(joinPoint.getArgs()));
	}
	
	
	@Around("execution(* *ajbc.learn.dao.getProductsByPriceRange(Double,Double))")
	public Object handlrMaxMinArgs(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Object[] args = proceedingJoinPoint.getArgs();
		double min = (double)args[0];
		double max = (double)args[1];
		if(min > max) {
			args = new Object[] {max,min};
		}
		return proceedingJoinPoint.proceed(args);
	}
	
	@AfterThrowing(throwing = "ex", pointcut = "execution(* ajbc.learn.dao.*(..))")
	public void convertToDaoException(Throwable ex) throws DaoException {
		throw new DaoException(ex);
	}
}
