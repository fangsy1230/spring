package spring.annotation.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LogAspects {
	
	/**
	 * 抽取公共的切入点表达式
	 * 1、本类引用pointcut()
	 *2、不是本类引用,则写全类名spring.annotation.aop.LogAspects.pointcut()
	 */
	@Pointcut("execution(public int spring.annotation.aop.MathCalculator.*(..))")
	public void pointcut(){};
	
	//在目标方法之前切入，切入点表达式（指定在哪个方法切入）
	@Before("pointcut()")
	public void logStart(JoinPoint joinpoint){
		//参数列表
		Object[] args = joinpoint.getArgs();
		System.out.println(joinpoint.getSignature().getName()+"运行。。。参数列表为"+Arrays.asList(args));
	}
	@After("pointcut()")
	public void logEnd(JoinPoint joinpoint){
		System.out.println(joinpoint.getSignature().getName()+"结束。。。");
	}
	//JoinPoint一定要出现在参数表的第一位
	//returning指定接收的返回值
	@AfterReturning(value="pointcut()",returning="result")
	public void logReturn(JoinPoint joinpoint,Object result){
		System.out.println(joinpoint.getSignature().getName()+"正常返回。。。"+result);
	}
	@AfterThrowing(value="pointcut()",throwing="exception")
	public void logExpection(JoinPoint joinpoint,Exception exception){
		System.out.println(joinpoint.getSignature().getName()+"运行异常。。。"+"{"+exception+"}");
	}

}
