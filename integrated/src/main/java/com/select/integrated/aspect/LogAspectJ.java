package com.select.integrated.aspect;

import com.select.integrated.annotation.RecordLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 日志处理切面
 *
 * @Aspect:作用是把当前类标识为一个切面供容器读取
 * @Pointcut：Pointcut是植入Advice的触发条件。每个Pointcut的定义包括2部分，一是表达式，二是方法签名。方法签名必须是 public及void型。可以将Pointcut中的方法看作是一个被Advice引用的助记符，因为表达式不直观，因此我们可以通过方法签名的方式为 此表达式命名。因此Pointcut中的方法只需要方法签名，而不需要在方法体内编写实际代码。
 * @Around：环绕增强，相当于MethodInterceptor
 * @AfterReturning：后置增强，相当于AfterReturningAdvice，方法正常退出时执行
 * @Before：标识一个前置增强方法，相当于BeforeAdvice的功能，相似功能的还有
 * @AfterThrowing：异常抛出增强，相当于ThrowsAdvice
 * @After: final增强，不管是抛出异常或者正常退出都会执行
 */
@Aspect
@Component
@Slf4j
public class LogAspectJ {

    /**
     * 环绕增强
     *
     * @param point 切点
     */
    @Around("execution(* com.select.integrated.api.controller.*.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        // 获取方法参数值数组
        Object[] args = point.getArgs();
        // 得到其方法签名
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        // 获取方法参数类型数组
        Class[] paramTypeArray = methodSignature.getParameterTypes();
        // 动态修改其参数
        // 注意，如果调用point.proceed()方法，则修改的参数值不会生效，必须调用point.proceed(Object[] args)
        Object result = point.proceed(args);
        // 如果这里不返回result，则目标对象实际返回值会被置为null
        return result;
    }

    /**
     * 后置增强
     *
     * @param joinPoint 切点
     * @param result    注解所在方法的返回值
     */
    @AfterReturning(value = "@annotation(com.select.integrated.annotation.RecordLog)", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        Method method = ms.getMethod();
        RecordLog recordLog = method.getAnnotation(RecordLog.class);
        log.info(recordLog.description());
        log.info(result.toString());
    }
}
